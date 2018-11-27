package pl.michalmarciniec.loyalty.api.validation;

import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class GiveBonusCommandValidator extends CommandValidator implements Validator {
    private final AuthenticationService authenticationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return GiveBonusCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GiveBonusCommand command = (GiveBonusCommand) target;
        Long giverId = authenticationService.getCurrentMember().getId();
        validate(Collections.singletonList(
                new Condition(!giverId.equals(command.getReceiverId()), "Cannot give bonus to oneself")),
                errors
        );
    }
}
