package pl.michalmarciniec.loyalty.api;

import lombok.Value;

import java.util.Collection;

@Value
class ErrorDto {
    Collection<String> errors;
    String message;
}
