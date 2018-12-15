package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bonus_categories")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "name", callSuper = false)
@ToString
@Getter
@Builder
public class BonusCategory extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    BonusCategoryName name;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    Permission permission;

    @Column(name = "points_pool")
    Long pointsPool;

    @Column(name = "edit_period", nullable = false)
    Long editPeriodInHours;

    public void resetPointsPool(Long startingPool) {
        pointsPool = startingPool;
    }

    public void transferPoints(Wallet receiverWallet, Long points) {
        subtractPoints(points);
        receiverWallet.addPoints(points);
    }

    private void subtractPoints(Long points) {
        if (points > pointsPool) {
            throw new InsufficientPointsException();
        }
        pointsPool -= points;
    }
}
