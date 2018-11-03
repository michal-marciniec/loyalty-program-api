package pl.michalmarciniec.loyalty.domain.entity;

import com.google.common.base.Preconditions;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bonuses")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class Bonus extends BaseEntity {

    @Column(name = "points", nullable = false)
    Long points;

    @Column(name = "giver_id", nullable = false)
    Long giverId;

    @Column(name = "receiver_id", nullable = false)
    Long receiverId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    BonusCategory category;

    @Column(name = "description", length = 300, nullable = false)
    String description;

    public void changeDescriptionAndPoints(String description, Long points) {
        Preconditions.checkNotNull(description, points);
        this.description = description;
        this.points = points;
    }
}
