package pl.michalmarciniec.loyalty.domain.entity;

public class InsufficientPointsException extends RuntimeException {
    InsufficientPointsException() {
        super("Attempt to grant more points than available");
    }
}
