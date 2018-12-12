package pl.michalmarciniec.loyalty.api.validation;

import pl.michalmarciniec.loyalty.db.EntityNotFoundException;
import pl.michalmarciniec.loyalty.domain.entity.InsufficientPointsException;
import pl.michalmarciniec.loyalty.domain.entity.PointsAlreadySpentException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        List<String> errors = Stream.concat(
                validationException.getBindingResult().getGlobalErrors().stream()
                        .map(DefaultMessageSourceResolvable::getCode),
                validationException.getBindingResult().getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        ).collect(Collectors.toList());

        ErrorDto errorInfo = new ErrorDto("Command validation failed", errors);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFound(EntityNotFoundException entityNotFoundException) {
        ErrorDto errorInfo = new ErrorDto(
                "Entity not found",
                Collections.singletonList(entityNotFoundException.getMessage())
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorInfo);
    }

    @ExceptionHandler(InsufficientPointsException.class)
    public ResponseEntity<ErrorDto> handleInsufficientPoints(InsufficientPointsException insufficientPointsException) {
        ErrorDto errorInfo = new ErrorDto(
                "Insufficient points",
                Collections.singletonList(insufficientPointsException.getMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorInfo);
    }

    @ExceptionHandler(PointsAlreadySpentException.class)
    public ResponseEntity<ErrorDto> handlePointsAlreadySpent(PointsAlreadySpentException pointsAlreadySpentException) {
        ErrorDto errorInfo = new ErrorDto(
                "Insufficient points",
                Collections.singletonList(pointsAlreadySpentException.getMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorInfo);
    }
}
