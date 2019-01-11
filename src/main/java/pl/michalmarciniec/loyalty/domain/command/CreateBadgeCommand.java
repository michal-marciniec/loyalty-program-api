package pl.michalmarciniec.loyalty.domain.command;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
@Getter
public class CreateBadgeCommand {
    @NotNull
    String description;
    @NotNull
    String imagePath;
}
