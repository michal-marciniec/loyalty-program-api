package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.RewardsRepository;
import pl.michalmarciniec.loyalty.domain.entity.*;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Service
@RequiredArgsConstructor
public class BuyRewardService {
    private final RewardsRepository rewardsRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    @PreAuthorize("hasRole(T(RoleName).ROLE_MEMBER.name())")
    public ClaimedReward buyReward(Long rewardId) {
        Reward reward = getEntityOrFail(() -> rewardsRepository.findById(rewardId));
        Member currentMember = authenticationService.getCurrentMember();

        currentMember.getWallet().spendPoints(reward.getRewardInfo().getPrice());
        reward.sell();
        ClaimedReward claimedReward = createClaimedReward(reward, currentMember);
        currentMember.addReward(claimedReward);
        return claimedReward;
    }

    private ClaimedReward createClaimedReward(Reward reward, Member member) {
        RewardInfo rewardInfo = ModelMapper.map(reward.getRewardInfo(), RewardInfo.RewardInfoBuilder.class).build();
        return ClaimedReward.builder()
                .rewardInfo(rewardInfo)
                .member(member)
                .status(RewardStatus.PENDING)
                .build();
    }

}
