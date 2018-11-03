package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "permissions")
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "name", callSuper = false)
@ToString
@Getter
@Builder
public class Permission extends BaseEntity implements GrantedAuthority {

    @Column(nullable = false, name = "name", length = 100, unique = true)
    @Enumerated(EnumType.STRING)
    PermissionName name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}
