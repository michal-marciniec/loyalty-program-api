package pl.michalmarciniec.loyalty.domain.entity.exceptions;

import pl.michalmarciniec.loyalty.common.ClientBadRequestException;

public class PointsAlreadySpentException extends ClientBadRequestException {
    public PointsAlreadySpentException() {
        super("Cannot subtract points from the receiver, because points have been already spent");
    }
}
