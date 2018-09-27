package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.domain.command.GiveRoleCommand;
import pl.michalmarciniec.loyalty.security.RoleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@LoyaltyProgramApi
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolesEndpoint {
    private final RoleManager roleManager;

    @RequestMapping(method = PUT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void giveRoleToMember(@RequestBody GiveRoleCommand giveRoleCommand) {
        roleManager.giveRole(giveRoleCommand);
    }

}
