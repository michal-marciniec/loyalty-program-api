package pl.michalmarciniec.loyalty.domain.dto;

import pl.michalmarciniec.loyalty.domain.entity.RewardStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClaimedRewardDto {
    Long id;
    String description;
    Long price;
    String logoPath;
    RewardStatus status;
    Long memberId;
}
