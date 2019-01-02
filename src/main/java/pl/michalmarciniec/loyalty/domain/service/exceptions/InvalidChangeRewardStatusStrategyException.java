package pl.michalmarciniec.loyalty.domain.service.exceptions;

import pl.michalmarciniec.loyalty.common.ClientBadRequestException;

public class InvalidChangeRewardStatusStrategyException extends ClientBadRequestException {
    public InvalidChangeRewardStatusStrategyException() {
        super("Invalid change reward status attempt. Invalid transition between statuses");
    }
}
