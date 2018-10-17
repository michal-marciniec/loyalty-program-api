package pl.michalmarciniec.loyalty.api.validation;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.db.RolesRepository;
import pl.michalmarciniec.loyalty.domain.command.GiveRoleCommand;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Collections;
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
        Optional<Member> member = membersRepository.findById(command.getMemberId());
        validate(Collections.singletonList(
                new Condition(member.isPresent(), "Member must exist in database")),
                errors
        );
    }
}
