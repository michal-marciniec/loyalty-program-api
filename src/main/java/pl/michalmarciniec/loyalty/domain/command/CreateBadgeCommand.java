package pl.michalmarciniec.loyalty.domain.command;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Value
public class CreateBadgeCommand {
    @NotNull
    @Length(max = 300)
    String description;
    @NotNull
    @Length(max = 500)
    String imagePath;
}
