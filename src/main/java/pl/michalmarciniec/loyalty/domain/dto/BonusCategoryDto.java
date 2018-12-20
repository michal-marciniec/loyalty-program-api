package pl.michalmarciniec.loyalty.domain.dto;

import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BonusCategoryDto {
    BonusCategoryName name;
    Long pointsPool;
    Long editPeriodInHours;
}
