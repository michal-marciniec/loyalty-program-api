package pl.michalmarciniec.loyalty.api.validation;

import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.domain.command.EditBonusCommand;
import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EditBonusCommandValidator extends CommandValidator implements Validator {
    private final BonusesRepository bonusesRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return EditBonusCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditBonusCommand command = (EditBonusCommand) target;
        Optional<Bonus> bonus = bonusesRepository.findById(command.getId());
        validate(Arrays.asList(
                new Condition(bonus.isPresent(), "Bonus should already exist"),
                new Condition(!command.isEmpty(), "Command cannot be empty")
        ), errors);
    }
}
