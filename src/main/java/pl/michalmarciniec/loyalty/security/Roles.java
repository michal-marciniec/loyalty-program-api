package pl.michalmarciniec.loyalty.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Roles {
    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN");

    @Getter
    private final String roleName;
}
