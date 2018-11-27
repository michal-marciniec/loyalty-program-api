package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.domain.command.GiveRoleCommand;
import pl.michalmarciniec.loyalty.domain.dto.RoleDto;
import pl.michalmarciniec.loyalty.domain.dto.RoleDto.RoleDtoBuilder;
import pl.michalmarciniec.loyalty.domain.entity.Role;
import pl.michalmarciniec.loyalty.security.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@LoyaltyProgramApi
@RequestMapping(value = "/roles", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RolesEndpoint {
    private final AuthorizationService authorizationService;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public RoleDto giveRoleToMember(@RequestBody @Validated GiveRoleCommand giveRoleCommand) {
        Role role = authorizationService.giveRole(giveRoleCommand);
        return ModelMapper.map(role, RoleDtoBuilder.class).build();
    }
}
