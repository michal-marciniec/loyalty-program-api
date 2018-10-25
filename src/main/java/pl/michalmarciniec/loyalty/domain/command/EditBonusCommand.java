package pl.michalmarciniec.loyalty.domain.command;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class EditBonusCommand {
    @NotNull
    Long id;
    @Min(1)
    @Max(10)
    Long points;
    @Length(max = 300)
    String description;

    public boolean isEmpty() {
        return points == null && description == null;
    }
}
