package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.RewardsRepository;
import pl.michalmarciniec.loyalty.domain.command.AddRewardCommand;
import pl.michalmarciniec.loyalty.domain.command.EditRewardCommand;
import pl.michalmarciniec.loyalty.domain.entity.Reward;
import pl.michalmarciniec.loyalty.domain.entity.Reward.RewardBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManageRewardsService {
    private final RewardsRepository rewardsRepository;

    @PreAuthorize("hasRole(T(RoleName).ROLE_ADMIN.name())")
    @Transactional
    public Reward addReward(AddRewardCommand addRewardCommand) {
        Reward reward = ModelMapper.map(addRewardCommand, RewardBuilder.class).build();
        log.debug("Adding reward: {}", reward);
        Reward addedReward = rewardsRepository.save(reward);
        log.debug("Reward {} added", reward);
        return addedReward;
    }

    @Transactional
    @PreAuthorize("hasRole(T(RoleName).ROLE_ADMIN.name())")
    public Reward editReward(EditRewardCommand editRewardCommand) {
        Reward reward = getEntityOrFail(() -> rewardsRepository.findById(editRewardCommand.getId()));
        reward.edit(editRewardCommand);
        return reward;
    }

}
