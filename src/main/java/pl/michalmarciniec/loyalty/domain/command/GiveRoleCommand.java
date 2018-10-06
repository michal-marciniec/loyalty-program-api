package pl.michalmarciniec.loyalty.domain.command;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
public class GiveRoleCommand {
    @NotNull
    Long memberId;
    @NotNull
    @Pattern(regexp = "^ROLE_.+$")
    String roleName;
}
