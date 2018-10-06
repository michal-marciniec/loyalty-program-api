package pl.michalmarciniec.loyalty.domain.command;

import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class GiveBonusCommand {
    @NotNull
    Long giverId;
    @NotNull
    Long receiverId;
    @NotNull
    @Min(1)
    @Max(10)
    Integer points;
}
