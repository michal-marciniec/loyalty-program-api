package pl.michalmarciniec.loyalty.domain.entity;

import com.google.common.base.Preconditions;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class Member extends BaseEntity {

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
    Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "members_rewards",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "reward_id", referencedColumnName = "id")
    )
    Set<Reward> rewards = new HashSet<>();

    @Column(name = "email", nullable = false, length = 100)
    String email;

    public void addRole(Role role) {
        Preconditions.checkNotNull(role);
        roles.add(role);
    }

    public List<GrantedAuthority> getAuthorities() {
        return Stream.concat(
                getRoles().stream(),
                getPermissions().stream()
        ).collect(Collectors.toList());
    }

    public List<GrantedAuthority> getPermissions() {
        return getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toList());
    }

    public boolean hasAuthority(GrantedAuthority grantedAuthority) {
        return getAuthorities().contains(grantedAuthority);
    }

}
