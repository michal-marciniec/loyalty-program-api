package pl.michalmarciniec.loyalty.domain.command;

import pl.michalmarciniec.loyalty.domain.entity.RewardStatus;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ChangeRewardStatusCommand {
    @NotNull
    Long claimedRewardId;
    @NotNull
    RewardStatus status;
}
