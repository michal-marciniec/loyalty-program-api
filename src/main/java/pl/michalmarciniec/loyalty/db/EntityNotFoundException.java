package pl.michalmarciniec.loyalty.db;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Queried entity not found. Query parameters should be validated.");
    }
}
