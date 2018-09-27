package pl.michalmarciniec.loyalty.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "roles")
@NoArgsConstructor(access = PRIVATE)
@ToString
public class Role {
    public final static String DEFAULT_ROLE_NAME = "ROLE_MEMBER";

    @Builder
    private Role(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, name = "name", length = 100, unique = true)
    @Getter
    String name;
}
