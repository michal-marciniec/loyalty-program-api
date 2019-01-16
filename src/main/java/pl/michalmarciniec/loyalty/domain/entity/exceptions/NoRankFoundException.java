package pl.michalmarciniec.loyalty.domain.entity.exceptions;

public class NoRankFoundException extends RuntimeException {
    public NoRankFoundException() {
        super("Invalid DB initialization. Cannot found any rank.");
    }
}
