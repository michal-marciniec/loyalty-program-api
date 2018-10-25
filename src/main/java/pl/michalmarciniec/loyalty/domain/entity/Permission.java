package pl.michalmarciniec.loyalty.domain.entity;

import lombok.Builder;
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
@Getter
public class Permission extends BaseEntity implements GrantedAuthority {

    @Builder
    private Permission(PermissionName name) {
        this.name = name;
    }

    @Column(nullable = false, name = "name", length = 100, unique = true)
    @Enumerated(EnumType.STRING)
    PermissionName name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}
