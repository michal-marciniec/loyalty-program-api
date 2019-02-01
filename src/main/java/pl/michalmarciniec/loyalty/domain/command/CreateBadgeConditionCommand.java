package pl.michalmarciniec.loyalty.domain.command;

import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName;
import pl.michalmarciniec.loyalty.domain.entity.BadgeConditionType;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CreateBadgeConditionCommand {

    @NotNull
    Long badgeId;

    BonusCategoryName categoryName;

    @NotNull
    BadgeConditionType conditionType;
}
