package pl.michalmarciniec.loyalty.domain;

import pl.michalmarciniec.loyalty.security.PermissionName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "permissions")
@NoArgsConstructor(access = PRIVATE)
@ToString
@EqualsAndHashCode(of = {"id"})
public class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, name = "name", length = 100, unique = true)
    @Enumerated(EnumType.STRING)
    @Getter
    PermissionName name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}
