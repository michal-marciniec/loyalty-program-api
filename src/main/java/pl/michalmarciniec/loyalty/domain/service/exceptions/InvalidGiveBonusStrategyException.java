package pl.michalmarciniec.loyalty.domain.service.exceptions;

import pl.michalmarciniec.loyalty.common.ClientBadRequestException;

public class InvalidGiveBonusStrategyException extends ClientBadRequestException {
    public InvalidGiveBonusStrategyException() {
        super("No strategy for bonus giving of requested type found");
    }
}
