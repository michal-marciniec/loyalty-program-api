package pl.michalmarciniec.loyalty.common;

public class ClientBadRequestException extends RuntimeException {
    public ClientBadRequestException(String message) {
        super(message);
    }
}
