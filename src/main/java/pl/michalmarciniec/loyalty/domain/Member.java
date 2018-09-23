package pl.michalmarciniec.loyalty.domain;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = PRIVATE)
@ToString
public class Member {

    @Builder
    private Member(String email, String name, String avatarPath) {
        this.name = name;
        this.avatarPath = avatarPath;
        this.email = email;
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

    @Column(name = "email", nullable = false)
    @Getter
    String email;
}
