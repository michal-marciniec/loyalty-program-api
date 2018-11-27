package pl.michalmarciniec.loyalty.domain.command;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Getter
public class AddRewardCommand {
    @Min(1)
    Long price;
    @NotNull
    String description;
    @NotNull
    String logoPath;
    Long amount = 1L;
    LocalDateTime expirationDate = LocalDateTime.MAX;
}
