package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "badge_condition")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class BadgeCondition extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "badge_id", nullable = false)
    Badge badge;

    @ManyToOne
    @JoinColumn(name = "category_id")
    BonusCategory bonusCategory;

    @Column(name = "condition_type", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    BadgeConditionType conditionType;

    public Optional<BonusCategory> getBonusCategory() {
        return Optional.ofNullable(bonusCategory);
    }
}
