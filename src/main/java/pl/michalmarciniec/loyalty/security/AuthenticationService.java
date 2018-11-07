package pl.michalmarciniec.loyalty.security;

import pl.michalmarciniec.loyalty.domain.entity.Member;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface AuthenticationService extends PrincipalExtractor, AuthoritiesExtractor {
    Object extractPrincipal(Map<String, Object> authenticationDetails);

    List<GrantedAuthority> extractAuthorities(Map<String, Object> authenticationDetails);

    Member getCurrentMember();
}
