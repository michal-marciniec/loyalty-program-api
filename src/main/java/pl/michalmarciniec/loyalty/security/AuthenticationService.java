package pl.michalmarciniec.loyalty.security;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.db.RolesRepository;
import pl.michalmarciniec.loyalty.domain.Member;
import pl.michalmarciniec.loyalty.domain.Role;
import pl.michalmarciniec.loyalty.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements PrincipalExtractor {

    private static final String AUTH_DETAILS_NAME = "name";
    private static final String AUTH_DETAILS_EMAIL = "email";
    private static final String AUTH_DETAILS_PICTURE = "picture";

    private final MembersRepository membersRepository;
    private final RolesRepository rolesRepository;

    @Override
    public Object extractPrincipal(Map<String, Object> authenticationDetails) {
        Member member = getOrCreateMember(authenticationDetails);
        return User.builder()
                .id(member.getId())
                .email(member.getEmail())
                .avatarPath(member.getAvatarPath())
                .name(member.getName())
                .roles(member.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
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
        Role defaultRole = rolesRepository.findByName(Role.DEFAULT_ROLE_NAME).orElseThrow(DefaultRoleNotFoundException::new);
        Member member = Member.builder()
                .email(extractAuthDetail(authenticationDetails, AUTH_DETAILS_EMAIL))
                .name(extractAuthDetail(authenticationDetails, AUTH_DETAILS_NAME))
                .avatarPath(extractAuthDetail(authenticationDetails, AUTH_DETAILS_PICTURE))
                .roles(new HashSet<>(Collections.singletonList(defaultRole)))
                .build();
        return membersRepository.save(member);
    }

    private String extractAuthDetail(Map<String, Object> authenticationDetails, String detailName) {
        return (String)authenticationDetails.get(detailName);
    }
}
