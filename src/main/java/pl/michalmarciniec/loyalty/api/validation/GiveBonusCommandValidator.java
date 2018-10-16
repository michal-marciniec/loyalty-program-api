package pl.michalmarciniec.loyalty.api.validation;

import pl.michalmarciniec.loyalty.db.BonusCategoryRepository;
import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.BonusCategory;
import pl.michalmarciniec.loyalty.domain.Member;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
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
    private final BonusCategoryRepository bonusCategoryRepository;
    private final AuthenticationService authenticationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return GiveBonusCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GiveBonusCommand command = (GiveBonusCommand) target;
        Optional<Member> receiver = membersRepository.findById(command.getReceiverId());
        Optional<BonusCategory> bonusCategory = bonusCategoryRepository.findByName(command.getCategory());

        Long giverId = authenticationService.getCurrentMember().getId();
        validate(Arrays.asList(
                new Condition(receiver.isPresent(), "Receiver of the bonus must exist"),
                new Condition(!giverId.equals(command.getReceiverId()), "Cannot give bonus to oneself"),
                new Condition(bonusCategory.isPresent(), "Category of the bonus must exist")),
                errors
        );
    }
}
