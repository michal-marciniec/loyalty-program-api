package pl.michalmarciniec.loyalty.domain.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class RewardDto {
    Long id;
    String description;
    String logoPath;
    Long amount;
    LocalDateTime expirationDate;
}

