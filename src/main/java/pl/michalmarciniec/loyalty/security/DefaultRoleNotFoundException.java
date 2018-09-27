package pl.michalmarciniec.loyalty.security;

public class DefaultRoleNotFoundException extends RuntimeException {
    DefaultRoleNotFoundException() {
        super("Default role not found in DB. Invalid DB initialization");
    }
}
