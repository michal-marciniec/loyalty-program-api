package pl.michalmarciniec.loyalty.domain.command;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class CommandValidator {

    protected void validate(Map<Boolean, String> validations) {
        Set<String> errors = validations.entrySet().stream()
                .filter(Map.Entry::getKey)
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());

        if (!errors.isEmpty()) {
            throw new CommandValidationException(errors);
        }
    }
}
