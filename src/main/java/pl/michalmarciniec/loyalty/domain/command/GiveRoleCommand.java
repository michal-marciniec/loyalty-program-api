package pl.michalmarciniec.loyalty.domain.command;

import lombok.Value;

@Value
public class GiveRoleCommand {
    Long memberId;
    String roleName;
}
