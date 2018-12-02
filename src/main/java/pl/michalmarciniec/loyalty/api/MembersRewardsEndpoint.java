package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.ClaimedRewardsRepository;
import pl.michalmarciniec.loyalty.db.SearchQuery;
import pl.michalmarciniec.loyalty.domain.command.ChangeRewardStatusCommand;
import pl.michalmarciniec.loyalty.domain.dto.ClaimedRewardDto;
import pl.michalmarciniec.loyalty.domain.entity.RewardStatus;
import pl.michalmarciniec.loyalty.domain.entity.ClaimedReward;
import pl.michalmarciniec.loyalty.domain.service.ManageRewardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static pl.michalmarciniec.loyalty.domain.entity.QClaimedReward.claimedReward;
import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@LoyaltyProgramApi
@RequestMapping(path = "/members-rewards", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MembersRewardsEndpoint {
    private final ClaimedRewardsRepository claimedRewardsRepository;
    private final ManageRewardsService manageRewardsService;

    @GetMapping
    @PreAuthorize("hasRole(T(RoleName).ROLE_ADMIN.name())")
    public List<ClaimedRewardDto> getMembersRewards(@RequestParam(required = false) RewardStatus status) {
        return new SearchQuery<>(claimedRewardsRepository)
                .addPredicate(status, (builder, value) -> builder.and(claimedReward.status.eq(status)))
                .find()
                .stream()
                .map(claimedReward -> ModelMapper.map(asList(claimedReward, claimedReward.getRewardInfo()), ClaimedRewardDto.ClaimedRewardDtoBuilder.class).build())
                .collect(Collectors.toList());
    }

    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    public ClaimedRewardDto changeRewardStatus(@Validated @RequestBody ChangeRewardStatusCommand changeRewardStatusCommand) {
        ClaimedReward claimedReward = manageRewardsService.changeRewardStatus(changeRewardStatusCommand);
        return ModelMapper.map(asList(claimedReward, claimedReward.getRewardInfo()), ClaimedRewardDto.ClaimedRewardDtoBuilder.class).build();
    }
}
