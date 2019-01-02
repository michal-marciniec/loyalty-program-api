package pl.michalmarciniec.loyalty.domain.entity.exceptions;

import pl.michalmarciniec.loyalty.common.ClientBadRequestException;

public class InsufficientPointsException extends ClientBadRequestException {
    public InsufficientPointsException() {
        super("Attempt to grant more points than available");
    }
}
