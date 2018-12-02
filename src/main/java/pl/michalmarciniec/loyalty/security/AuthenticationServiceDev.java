package pl.michalmarciniec.loyalty.security;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Profile("dev")
@Service
@RequiredArgsConstructor
public class AuthenticationServiceDev implements AuthenticationService {
    private final static Long ADMIN_ID = 1L;
    private final MembersRepository membersRepository;

    @Override
    public Object extractPrincipal(Map<String, Object> authenticationDetails) {
        throw new DevAuthenticationException();
    }

    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> authenticationDetails) {
        throw new DevAuthenticationException();
    }

    @Override
    public Member getCurrentMember() {
        return getEntityOrFail(() -> membersRepository.findById(ADMIN_ID));
    }

    private final class DevAuthenticationException extends RuntimeException {
        private DevAuthenticationException() {
            super("Tried to extract principal or authorities in dev profile");
        }
    }
}
