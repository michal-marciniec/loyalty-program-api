package pl.michalmarciniec.loyalty.security;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.db.RolesRepository;
import pl.michalmarciniec.loyalty.domain.command.GiveRoleCommand;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import pl.michalmarciniec.loyalty.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Service
@RequiredArgsConstructor
public class AuthorizationManager {

    private final RolesRepository rolesRepository;
    private final MembersRepository membersRepository;

    @Transactional
    @PreAuthorize("hasRole(T(RoleName).ROLE_ADMIN.name())")
    public void giveRole(GiveRoleCommand giveRoleCommand) {
        Role role = getEntityOrFail(() -> rolesRepository.findByName(giveRoleCommand.getRoleName()));
        Member member = getEntityOrFail(() -> membersRepository.findById(giveRoleCommand.getMemberId()));
        member.addRole(role);
    }

}
