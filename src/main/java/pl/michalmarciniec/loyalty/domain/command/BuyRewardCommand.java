package pl.michalmarciniec.loyalty.domain.command;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class BuyRewardCommand {
    @NotNull
    Long rewardId;
}
