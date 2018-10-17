package pl.michalmarciniec.loyalty.domain.dto;

import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class BonusDto {
    Long id;
    Long points;
    Long giverId;
    Long receiverId;
    LocalDateTime givenAt;

    public static BonusDto of(Bonus bonus) {
        return BonusDto.builder()
                .id(bonus.getId())
                .givenAt(bonus.getGivenAt())
                .giverId(bonus.getGiverId())
                .points(bonus.getPoints())
                .receiverId(bonus.getReceiverId())
                .build();
    }
}
