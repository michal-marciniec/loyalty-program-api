package pl.michalmarciniec.loyalty.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = PRIVATE)
@ToString
public class Member {

    @Builder
    private Member(String name, String avatarPath) {
        this.name = name;
        this.avatarPath = avatarPath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "avatar_path", nullable = false)
    String avatarPath;
}
