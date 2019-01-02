package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.db.ClaimedRewardsRepository;
import pl.michalmarciniec.loyalty.domain.command.ChangeRewardStatusCommand;
import pl.michalmarciniec.loyalty.domain.entity.RewardStatus;
import pl.michalmarciniec.loyalty.domain.entity.ClaimedReward;
import pl.michalmarciniec.loyalty.domain.service.exceptions.InvalidChangeRewardStatusStrategyException;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;
import static pl.michalmarciniec.loyalty.domain.entity.RewardStatus.*;

@Component
@RequiredArgsConstructor
public class ChangeRewardStatusStrategies {
    private Map<StatusTransition, Consumer<ClaimedReward>> changeRewardStatusStrategies = ImmutableMap.of(
            StatusTransition.of(PENDING, REALIZED), ClaimedReward::realize,
            StatusTransition.of(PENDING, REJECTED), ClaimedReward::reject
    );

    private final ClaimedRewardsRepository claimedRewardsRepository;

    void changeStatus(ChangeRewardStatusCommand changeRewardStatusCommand) {
        ClaimedReward claimedReward = getEntityOrFail(() -> claimedRewardsRepository.findById(changeRewardStatusCommand.getClaimedRewardId()));
        StatusTransition statusTransition = StatusTransition.of(claimedReward.getStatus(), changeRewardStatusCommand.getStatus());
        Consumer<ClaimedReward> strategy = Optional.ofNullable(changeRewardStatusStrategies.get(statusTransition))
                .orElseThrow(InvalidChangeRewardStatusStrategyException::new);

        strategy.accept(claimedReward);
    }

    @Value
    @AllArgsConstructor(staticName = "of")
    private static class StatusTransition {
        RewardStatus from;
        RewardStatus to;
    }
}
