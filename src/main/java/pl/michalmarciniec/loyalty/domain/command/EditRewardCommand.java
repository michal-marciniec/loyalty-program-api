package pl.michalmarciniec.loyalty.domain.command;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = true)
public class EditRewardCommand extends AddRewardCommand {
    @NotNull
    Long id;
}
