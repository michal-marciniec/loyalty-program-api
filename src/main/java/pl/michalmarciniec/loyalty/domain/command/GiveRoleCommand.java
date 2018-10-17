package pl.michalmarciniec.loyalty.domain.command;

import pl.michalmarciniec.loyalty.domain.entity.RoleName;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class GiveRoleCommand {
    @NotNull
    Long memberId;
    @NotNull
    RoleName roleName;
}
