package pl.michalmarciniec.loyalty.api.validation;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.Member;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GiveBonusCommandValidator extends CommandValidator implements Validator {
    private final MembersRepository membersRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return GiveBonusCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GiveBonusCommand command = (GiveBonusCommand) target;
        Optional<Member> giver = membersRepository.findById(command.getGiverId());
        Optional<Member> receiver = membersRepository.findById(command.getReceiverId());
        validate(Arrays.asList(
                new Condition(giver.isPresent(), "Giver of the bonus must exist"),
                new Condition(receiver.isPresent(), "Receiver of the bonus must exist"),
                new Condition(!command.getGiverId().equals(command.getReceiverId()), "Cannot give bonus to oneself")),
                errors
        );
    }
}
