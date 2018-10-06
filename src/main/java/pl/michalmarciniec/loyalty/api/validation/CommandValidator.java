package pl.michalmarciniec.loyalty.api.validation;

import lombok.Value;
import org.springframework.validation.Errors;

import java.util.List;

public abstract class CommandValidator {
    protected void validate(List<Condition> conditions, Errors errors) {
        conditions.stream()
                .filter(condition -> !condition.isMet)
                .forEach(condition -> errors.reject(condition.errorMessage));
    }

    @Value
    class Condition {
        Boolean isMet;
        String errorMessage;
    }
}


