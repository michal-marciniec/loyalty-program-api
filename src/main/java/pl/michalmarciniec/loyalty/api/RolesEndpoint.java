package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.api.validation.GiveRoleCommandValidator;
import pl.michalmarciniec.loyalty.domain.command.GiveRoleCommand;
import pl.michalmarciniec.loyalty.security.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@LoyaltyProgramApi
@RequestMapping(value = "/roles", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RolesEndpoint {
    private final AuthorizationService authorizationService;
    private final GiveRoleCommandValidator giveRoleCommandValidator;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void giveRoleToMember(@RequestBody @Validated GiveRoleCommand giveRoleCommand) {
        authorizationService.giveRole(giveRoleCommand);
    }

    @InitBinder("giveRoleCommand")
    private void setupBinder(WebDataBinder binder) {
        binder.addValidators(giveRoleCommandValidator);
    }
}
