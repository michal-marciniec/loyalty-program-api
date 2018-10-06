package pl.michalmarciniec.loyalty.api.validation;

public final class CommandValidationException extends RuntimeException {
    public CommandValidationException() {
        super("Invalid command surpassed controller. It needs to be validated.");
    }
}
