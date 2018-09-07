package pl.michalmarciniec.loyalty.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class BonusDto {
    Long id;
    int points;
    Long giverId;
    Long receiverId;
    LocalDateTime givenAt;

    public static BonusDto of(Bonus bonus) {
        return BonusDto.builder()
                .id(bonus.id)
                .givenAt(bonus.givenAt)
                .giverId(bonus.giverId)
                .points(bonus.points)
                .receiverId(bonus.receiverId)
                .build();
    }
}
