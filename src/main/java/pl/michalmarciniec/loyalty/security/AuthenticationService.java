package pl.michalmarciniec.loyalty.security;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.db.RolesRepository;
import pl.michalmarciniec.loyalty.domain.Member;
import pl.michalmarciniec.loyalty.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements PrincipalExtractor, AuthoritiesExtractor {

    private static final String AUTH_DETAILS_NAME = "name";
    private static final String AUTH_DETAILS_EMAIL = "email";
    private static final String AUTH_DETAILS_PICTURE = "picture";

    private final MembersRepository membersRepository;
    private final RolesRepository rolesRepository;

    @Override
    public Object extractPrincipal(Map<String, Object> authenticationDetails) {
        return getOrCreateMember(authenticationDetails);
    }

    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> authenticationDetails) {
        return membersRepository.findOneByEmail(extractAuthDetail(authenticationDetails, AUTH_DETAILS_EMAIL))
                .map(Member::getAuthorities)
                .orElse(Collections.emptyList());
    }

    public Member getCurrentMember() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> (Member) authentication.getPrincipal())
                .orElseThrow(NoMemberInSessionException::new);
    }

    private Member getOrCreateMember(Map<String, Object> authenticationDetails) {
        return membersRepository.findOneByEmail(extractAuthDetail(authenticationDetails, AUTH_DETAILS_EMAIL))
                .orElseGet(() -> createMember(authenticationDetails));
    }

    private Member createMember(Map<String, Object> authenticationDetails) {
        Role defaultRole = rolesRepository.findByName(Role.DEFAULT_ROLE_NAME).orElseThrow(DefaultRoleNotFoundException::new);
        Member member = Member.builder()
                .email(extractAuthDetail(authenticationDetails, AUTH_DETAILS_EMAIL))
                .name(extractAuthDetail(authenticationDetails, AUTH_DETAILS_NAME))
                .avatarPath(extractAuthDetail(authenticationDetails, AUTH_DETAILS_PICTURE))
                .roles(new ArrayList<>(Collections.singletonList(defaultRole)))
                .build();
        return membersRepository.save(member);
    }

    private String extractAuthDetail(Map<String, Object> authenticationDetails, String detailName) {
        return (String) authenticationDetails.get(detailName);
    }

    private final static class NoMemberInSessionException extends AuthenticationException {
        private NoMemberInSessionException() {
            super("There is no current member data in session.");
        }
    }

}
