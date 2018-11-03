package pl.michalmarciniec.loyalty.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
@EqualsAndHashCode(of = "id")
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id = UUID.randomUUID().getLeastSignificantBits();

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "edited_at", nullable = false)
    @UpdateTimestamp
    LocalDateTime editedAt = LocalDateTime.now();
}
