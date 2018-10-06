package pl.michalmarciniec.loyalty.security;

import pl.michalmarciniec.loyalty.api.validation.CommandValidationException;
import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.db.RolesRepository;
import pl.michalmarciniec.loyalty.domain.Member;
import pl.michalmarciniec.loyalty.domain.Role;
import pl.michalmarciniec.loyalty.domain.User;
import pl.michalmarciniec.loyalty.domain.command.GiveRoleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleManager")
@RequiredArgsConstructor
public class RoleManager {

    private final AuthenticationService authenticationService;
    private final RolesRepository rolesRepository;
    private final MembersRepository membersRepository;

    public boolean currentUserHasRole(String role) {
        return authenticationService.getCurrentUser()
                .map(User::getRoles)
                .map(userRoles -> userRoles.contains(role))
                .orElse(false);
    }

    @Transactional
    @PreAuthorize("@roleManager.currentUserHasRole(T(pl.michalmarciniec.loyalty.security.Roles).ADMIN)")
    public void giveRole(GiveRoleCommand giveRoleCommand) {
        Role role = rolesRepository.findByName(giveRoleCommand.getRoleName()).orElseThrow(CommandValidationException::new);
        Member member = membersRepository.findById(giveRoleCommand.getMemberId()).orElseThrow(CommandValidationException::new);
        member.addRole(role);
    }

}
