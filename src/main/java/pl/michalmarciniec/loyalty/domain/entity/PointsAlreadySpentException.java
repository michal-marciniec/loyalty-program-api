package pl.michalmarciniec.loyalty.domain.entity;

public class PointsAlreadySpentException extends RuntimeException {
    public PointsAlreadySpentException() {
        super("Cannot subtract points from the receiver, because points have been already spent");
    }
}
