package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bonuses")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class Bonus {

    @Builder
    private Bonus(Long points, Long receiverId, Long giverId, BonusCategory category) {
        this.points = points;
        this.receiverId = receiverId;
        this.giverId = giverId;
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "points", nullable = false)
    Long points;

    @Column(name = "giver_id", nullable = false)
    Long giverId;

    @Column(name = "receiver_id", nullable = false)
    Long receiverId;

    @Column(name = "given_at", nullable = false)
    @CreationTimestamp
    LocalDateTime givenAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    BonusCategory category;
}
