package pl.michalmarciniec.loyalty.domain.entity;

import pl.michalmarciniec.loyalty.domain.command.EditRewardCommand;
import lombok.*;

import javax.persistence.Column;
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
    @Column(name = "price", nullable = false)
    Long price;

    @Column(name = "description", nullable = false, length = 300)
    String description;

    @Column(name = "logo_path", nullable = false, length = 100)
    String logoPath;

    @Column(name = "amount", nullable = false)
    Long amount = 1L;

    @Column(name = "expiration_date", nullable = false)
    LocalDateTime expirationDate = LocalDateTime.MAX;

    public void edit(EditRewardCommand editRewardCommand) {
        this.amount = editRewardCommand.getAmount();
        this.description = editRewardCommand.getDescription();
        this.expirationDate = editRewardCommand.getExpirationDate();
        this.logoPath = editRewardCommand.getLogoPath();
        this.price = editRewardCommand.getPrice();
    }
}
