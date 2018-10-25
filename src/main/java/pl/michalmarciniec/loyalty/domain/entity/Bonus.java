package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bonuses")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class Bonus extends BaseEntity {

    @Builder
    private Bonus(Long points, Long receiverId, Long giverId, BonusCategory category, String description) {
        this.points = points;
        this.receiverId = receiverId;
        this.giverId = giverId;
        this.category = category;
        this.description = description;
    }

    @Column(name = "points", nullable = false)
    Long points;

    @Column(name = "giver_id", nullable = false)
    Long giverId;

    @Column(name = "receiver_id", nullable = false)
    Long receiverId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    BonusCategory category;

    @Column(name = "description")
    String description;

    public void changeDescriptionAndPoints(String description, Long points) {
        if (points != null) {
            this.points = points;
        }
        this.description = description;
    }
}
