package pl.michalmarciniec.loyalty.service;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.Member;
import pl.michalmarciniec.loyalty.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements PrincipalExtractor {

    private static final String AUTH_DETAILS_NAME = "name";
    private static final String AUTH_DETAILS_EMAIL = "email";
    private static final String AUTH_DETAILS_PICTURE = "picture";

    private final MembersRepository membersRepository;

    @Override
    public Object extractPrincipal(Map<String, Object> authenticationDetails) {
        Member member = getOrCreateMember(authenticationDetails);
        return User.builder()
                .id(member.getId())
                .email(member.getEmail())
                .avatarPath(member.getAvatarPath())
                .name(member.getName())
                .build();
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return Optional.ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .map(principal -> (principal instanceof User ? (User)principal : null));
    }

    private Member getOrCreateMember(Map<String, Object> authenticationDetails) {
        return membersRepository.findOneByEmail(extractAuthDetail(authenticationDetails, AUTH_DETAILS_EMAIL))
                .orElseGet(() -> createMember(authenticationDetails));
    }

    private Member createMember(Map<String, Object> authenticationDetails) {
        Member member = Member.builder()
                .email(extractAuthDetail(authenticationDetails, AUTH_DETAILS_EMAIL))
                .name(extractAuthDetail(authenticationDetails, AUTH_DETAILS_NAME))
                .avatarPath(extractAuthDetail(authenticationDetails, AUTH_DETAILS_PICTURE))
                .build();
        return membersRepository.save(member);
    }

    private String extractAuthDetail(Map<String, Object> authenticationDetails, String detailName) {
        return (String)authenticationDetails.get(detailName);
    }
}
