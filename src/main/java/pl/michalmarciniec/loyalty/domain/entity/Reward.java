package pl.michalmarciniec.loyalty.domain.entity;

import pl.michalmarciniec.loyalty.domain.command.EditRewardCommand;
import pl.michalmarciniec.loyalty.domain.entity.exceptions.RewardSoldOutException;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "rewards")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class Reward extends BaseEntity {

    @Embedded
    RewardInfo rewardInfo;

    @Column(name = "amount", nullable = false)
    @Builder.Default
    Long amount = 1L;

    @Column(name = "expiration_date", nullable = false)
    @Builder.Default
    LocalDateTime expirationDate = LocalDateTime.MAX;

    public void edit(EditRewardCommand editRewardCommand) {
        this.amount = editRewardCommand.getAmount();
        this.rewardInfo.description = editRewardCommand.getDescription();
        this.expirationDate = editRewardCommand.getExpirationDate();
        this.rewardInfo.logoPath = editRewardCommand.getLogoPath();
        this.rewardInfo.price = editRewardCommand.getPrice();
    }

    public void sell() {
        if (LocalDateTime.now().isAfter(expirationDate) || amount < 1) {
            throw new RewardSoldOutException();
        }

        amount--;
    }

}
