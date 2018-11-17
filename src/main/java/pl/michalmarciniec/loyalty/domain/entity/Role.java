package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "roles")
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "name", callSuper = false)
@ToString
@Getter
@Builder
public class Role extends BaseEntity implements GrantedAuthority {

    public final static RoleName DEFAULT_ROLE_NAME = RoleName.ROLE_MEMBER;

    @Column(nullable = false, name = "name", length = 100, unique = true)
    @Enumerated(EnumType.STRING)
    RoleName name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    @Builder.Default
    List<Permission> permissions = new ArrayList<>();

    @Override
    public String getAuthority() {
        return name.name();
    }
}
