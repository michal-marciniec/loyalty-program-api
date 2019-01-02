package pl.michalmarciniec.loyalty.domain.entity;

import pl.michalmarciniec.loyalty.domain.entity.exceptions.InsufficientPointsException;
import pl.michalmarciniec.loyalty.domain.entity.exceptions.PointsAlreadySpentException;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.PRIVATE;

@Embeddable
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class Wallet {
    @Column(name = "give_away_pool", nullable = false)
    Long giveAwayPool;

    @Column(name = "gained_points", nullable = false)
    Long gainedPoints;

    public void transferPoints(Wallet to, Long points) {
        subtractPoints(points);
        to.gainedPoints += points;
    }

    public void cancelBonus(Bonus bonus, Wallet receiverWallet) {
        if (receiverWallet.gainedPoints < bonus.points) {
            throw new PointsAlreadySpentException();
        }

        receiverWallet.gainedPoints -= bonus.points;
        giveAwayPool += bonus.points;
    }

    public void spendPoints(Long points) {
        if (points > gainedPoints) {
            throw new PointsAlreadySpentException();
        }
        gainedPoints -= points;
    }

    public void resetGiveAwayPool(Long startingPool) {
        this.giveAwayPool = startingPool;
    }

    private void subtractPoints(Long points) {
        if (points > giveAwayPool) {
            throw new InsufficientPointsException();
        }

        this.giveAwayPool -= points;
    }
}
