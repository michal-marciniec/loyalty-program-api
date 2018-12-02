package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.RewardsRepository;
import pl.michalmarciniec.loyalty.domain.command.AddRewardCommand;
import pl.michalmarciniec.loyalty.domain.command.EditRewardCommand;
import pl.michalmarciniec.loyalty.domain.dto.RewardDto;
import pl.michalmarciniec.loyalty.domain.entity.Reward;
import pl.michalmarciniec.loyalty.domain.service.ManageRewardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@LoyaltyProgramApi
@RequestMapping(path = "/rewards", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RewardsEndpoint {
    private final RewardsRepository rewardsRepository;
    private final ManageRewardsService manageRewardsService;

    @GetMapping
    public List<RewardDto> getAll() {
        return rewardsRepository.findAll().stream()
                .map(reward -> ModelMapper.map(asList(reward, reward.getRewardInfo()), RewardDto.RewardDtoBuilder.class).build())
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public RewardDto addReward(@Validated @RequestBody AddRewardCommand addRewardCommand) {
        Reward reward = manageRewardsService.addReward(addRewardCommand);
        return ModelMapper.map(reward, RewardDto.RewardDtoBuilder.class).build();
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public RewardDto editReward(@Validated @RequestBody EditRewardCommand editRewardCommand) {
        Reward reward = manageRewardsService.editReward(editRewardCommand);
        return ModelMapper.map(reward, RewardDto.RewardDtoBuilder.class).build();
    }
}
