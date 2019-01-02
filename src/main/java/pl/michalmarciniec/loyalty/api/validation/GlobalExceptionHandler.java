package pl.michalmarciniec.loyalty.api.validation;

import pl.michalmarciniec.loyalty.common.ClientBadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException validationException,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        BindingResult bindingResult = validationException.getBindingResult();
        List<String> errors = Stream.concat(
                bindingResult.getGlobalErrors().stream().map(DefaultMessageSourceResolvable::getCode),
                bindingResult.getFieldErrors().stream().map(this::parseFieldError)
        ).collect(Collectors.toList());

        ErrorDto errorInfo = new ErrorDto("Command validation failed", errors);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientBadRequestException.class)
    public ResponseEntity<ErrorDto> handleClientError(ClientBadRequestException clientErrorException) {
        ErrorDto errorInfo = new ErrorDto(
                "Client error",
                Collections.singletonList(clientErrorException.getMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorInfo);
    }

    private String parseFieldError(FieldError fieldError) {
        return fieldError.getField() + " " + fieldError.getDefaultMessage();
    }
}
