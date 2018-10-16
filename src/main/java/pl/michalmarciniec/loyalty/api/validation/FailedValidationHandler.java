package pl.michalmarciniec.loyalty.api.validation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class FailedValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException validationException,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> errors = validationException.getBindingResult().getGlobalErrors().stream()
                .map(DefaultMessageSourceResolvable::getCode)
                .collect(Collectors.toList());

        ErrorDto errorInfo = new ErrorDto("Command validation failed", errors);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
