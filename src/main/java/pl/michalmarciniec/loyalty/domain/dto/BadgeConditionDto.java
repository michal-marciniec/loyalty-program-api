package pl.michalmarciniec.loyalty.domain.dto;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.domain.entity.BadgeCondition;
import pl.michalmarciniec.loyalty.domain.entity.BadgeConditionType;
import lombok.Value;

@Value
public class BadgeConditionDto {
    Long id;
    BadgeDto badge;
    BonusCategoryDto category;
    BadgeConditionType conditionType;

    public BadgeConditionDto(BadgeCondition badgeCondition) {
        this.id = badgeCondition.getId();
        this.badge = ModelMapper.map(badgeCondition.getBadge(), BadgeDto.BadgeDtoBuilder.class).build();
        this.category = badgeCondition.getBonusCategory()
                .map(c -> ModelMapper.map(c, BonusCategoryDto.BonusCategoryDtoBuilder.class).build())
                .orElse(null);
        this.conditionType = badgeCondition.getConditionType();
    }
}
