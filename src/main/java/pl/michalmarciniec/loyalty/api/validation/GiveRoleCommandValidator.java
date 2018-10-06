package pl.michalmarciniec.loyalty.api.validation;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.db.RolesRepository;
import pl.michalmarciniec.loyalty.domain.Member;
import pl.michalmarciniec.loyalty.domain.Role;
import pl.michalmarciniec.loyalty.domain.command.GiveRoleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GiveRoleCommandValidator extends CommandValidator implements Validator {
    private final RolesRepository rolesRepository;
    private final MembersRepository membersRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return GiveRoleCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GiveRoleCommand command = (GiveRoleCommand) target;
        Optional<Role> role = rolesRepository.findByName(command.getRoleName());
        Optional<Member> member = membersRepository.findById(command.getMemberId());
        validate(Arrays.asList(
                new Condition(role.isPresent(), "The role must exist in database"),
                new Condition(member.isPresent(), "Member must exist in database")),
                errors
        );
    }
}
