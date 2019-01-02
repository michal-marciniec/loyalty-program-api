package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.common.ClientBadRequestException;

public class EntityNotFoundException extends ClientBadRequestException {
    public EntityNotFoundException() {
        super("Queried entity not found.");
    }
}
