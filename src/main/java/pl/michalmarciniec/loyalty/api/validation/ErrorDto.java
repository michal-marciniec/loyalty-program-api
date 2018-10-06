package pl.michalmarciniec.loyalty.api.validation;

import lombok.Value;

import java.util.List;

@Value
public class ErrorDto {
    String message;
    List<String> errors;
}
