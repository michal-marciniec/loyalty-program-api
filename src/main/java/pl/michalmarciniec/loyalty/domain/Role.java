package pl.michalmarciniec.loyalty.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import pl.michalmarciniec.loyalty.security.RoleName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "roles")
@NoArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of = {"id"})
@ToString
public class Role implements GrantedAuthority {

    public final static RoleName DEFAULT_ROLE_NAME = RoleName.ROLE_MEMBER;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, name = "name", length = 100, unique = true)
    @Getter
    @Enumerated(EnumType.STRING)
    RoleName name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    @Getter
    List<Permission> permissions = new ArrayList<>();

    @Override
    public String getAuthority() {
        return name.name();
    }
}
