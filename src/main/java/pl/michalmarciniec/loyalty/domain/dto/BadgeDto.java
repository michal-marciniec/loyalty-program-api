package pl.michalmarciniec.loyalty.domain.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BadgeDto {
    Long id;
    String description;
    String imagePath;
}
