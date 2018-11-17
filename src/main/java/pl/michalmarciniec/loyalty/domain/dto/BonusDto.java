package pl.michalmarciniec.loyalty.domain.dto;

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
    String description;
    LocalDateTime givenAt;
    LocalDateTime createdAt;
}
