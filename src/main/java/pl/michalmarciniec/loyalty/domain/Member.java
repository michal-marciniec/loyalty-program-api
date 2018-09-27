package pl.michalmarciniec.loyalty.domain;

import com.google.common.base.Preconditions;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = PRIVATE)
@ToString
public class Member {

    @Builder
    private Member(String email, String name, String avatarPath, Set<Role> roles) {
        this.name = name;
        this.avatarPath = avatarPath;
        this.email = email;
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    Long id;

    @Column(name = "name", nullable = false, length = 100)
    @Getter
    String name;

    @Column(name = "avatar_path", nullable = false, length = 100)
    @Getter
    String avatarPath;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "members_roles",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @Getter
    Set<Role> roles = new HashSet<>();

    @Column(name = "email", nullable = false)
    @Getter
    String email;

    public void addRole(Role role) {
        Preconditions.checkNotNull(role);
        roles.add(role);
    }

}
