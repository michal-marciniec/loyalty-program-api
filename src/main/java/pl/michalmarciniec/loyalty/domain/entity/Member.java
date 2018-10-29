package pl.michalmarciniec.loyalty.domain.entity;

import com.google.common.base.Preconditions;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = PRIVATE)
@ToString
@Getter
public class Member extends BaseEntity {

    @Builder
    private Member(String email, String name, String avatarPath, List<Role> roles) {
        this.name = name;
        this.avatarPath = avatarPath;
        this.email = email;
        this.roles = roles;
    }

    @Column(name = "name", nullable = false, length = 100)
    String name;

    @Column(name = "avatar_path", nullable = false, length = 100)
    String avatarPath;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "members_roles",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    List<Role> roles = new ArrayList<>();

    @Column(name = "email", nullable = false, length = 100)
    String email;

    public void addRole(Role role) {
        Preconditions.checkNotNull(role);
        roles.add(role);
    }

    public List<GrantedAuthority> getAuthorities() {
        return Stream.concat(
                getRoles().stream(),
                getRoles().stream().flatMap(role -> role.getPermissions().stream())
        ).collect(Collectors.toList());
    }

    public boolean hasAuthority(GrantedAuthority grantedAuthority) {
        return getAuthorities().contains(grantedAuthority);
    }

}
