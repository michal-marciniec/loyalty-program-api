package pl.michalmarciniec.loyalty.domain.command;

import pl.michalmarciniec.loyalty.domain.BonusCategoryName;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class GiveBonusCommand {
    @NotNull
    Long receiverId;
    @NotNull
    @Min(1)
    @Max(10)
    Long points;
    @NotNull
    BonusCategoryName category;
}
