package pl.michalmarciniec.loyalty.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bonuses")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Bonus {

    @Builder
    private Bonus(int points, Long receiverId, Long giverId) {
        this.points = points;
        this.receiverId = receiverId;
        this.giverId = giverId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "points", nullable = false)
    int points;

    @Column(name = "giver_id", nullable = false)
    Long giverId;

    @Column(name = "receiver_id", nullable = false)
    Long receiverId;

    @Column(name = "given_at", nullable = false)
    @CreationTimestamp
    LocalDateTime givenAt;

}
