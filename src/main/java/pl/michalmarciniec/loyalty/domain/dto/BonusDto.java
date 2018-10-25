package pl.michalmarciniec.loyalty.domain.dto;

import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BonusDto {
    Long id;
    Long points;
    Long giverId;
    Long receiverId;

    public static BonusDto of(Bonus bonus) {
        return BonusDto.builder()
                .id(bonus.getId())
                .giverId(bonus.getGiverId())
                .points(bonus.getPoints())
                .receiverId(bonus.getReceiverId())
                .build();
    }
}
