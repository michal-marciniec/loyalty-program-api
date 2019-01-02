package pl.michalmarciniec.loyalty.domain.entity.exceptions;

import pl.michalmarciniec.loyalty.common.ClientBadRequestException;

public class RewardSoldOutException extends ClientBadRequestException {
    public RewardSoldOutException() {
        super("Reward cannot be bought, because its amount is 0 or is expired");
    }
}
