package pl.michalmarciniec.loyalty.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    Long id;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "edited_at", nullable = false)
    @UpdateTimestamp
    @Version
    LocalDateTime editedAt; // must remain null as it's a version attribute hibernate uses to decide whether entity is transient

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
