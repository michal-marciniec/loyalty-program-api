package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "permissions")
@NoArgsConstructor(access = PRIVATE)
@ToString
@EqualsAndHashCode(of = {"id"})
@Getter
public class Permission implements GrantedAuthority {

    @Builder
    private Permission(PermissionName name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, name = "name", length = 100, unique = true)
    @Enumerated(EnumType.STRING)
    PermissionName name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}
