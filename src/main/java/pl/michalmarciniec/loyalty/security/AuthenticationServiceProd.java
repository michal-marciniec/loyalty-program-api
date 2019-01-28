package pl.michalmarciniec.loyalty.security;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.db.RolesRepository;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import pl.michalmarciniec.loyalty.domain.entity.Role;
import pl.michalmarciniec.loyalty.domain.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import static pl.michalmarciniec.loyalty.domain.entity.Role.DEFAULT_ROLE_NAME;

@Profile("prod")
@Service
@RequiredArgsConstructor
public class AuthenticationServiceProd implements AuthenticationService {

    protected static final String AUTH_DETAILS_NAME = "name";
    protected static final String AUTH_DETAILS_EMAIL = "email";
    protected static final String AUTH_DETAILS_PICTURE = "picture";

    @Value("${loyalty-program.initial-gained-points-pool}")
    private long initialGainedPointsPool;
    @Value("${loyalty-program.walletGainedPointsPool}")
    private long walletGainedPointsPool;

    @Value("${loyalty-program.initial-give-away-points-pool}")
    private long initialGiveAwayPointsPool;
    @Value("${loyalty-program.walletGiveAwayPointsPool}")
    private long walletGiveAwayPointsPool;

    private final MembersRepository membersRepository;
    private final RolesRepository rolesRepository;

    @Override
    public Object extractPrincipal(Map<String, Object> authenticationDetails) {
        return getOrCreateMember(authenticationDetails);
    }

    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> authenticationDetails) {
        String login = toLogin(extractAuthDetail(authenticationDetails, AUTH_DETAILS_EMAIL));
        return membersRepository.findOneByLogin(login)
                .map(Member::getAuthorities)
                .orElse(Collections.emptyList());
    }

    @Override
    public Member getCurrentMember() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> (Member) authentication.getPrincipal())
                .map(member -> membersRepository.findOne(member.getId()))
                .orElseThrow(NoMemberInSessionException::new);
    }

    private Member getOrCreateMember(Map<String, Object> authenticationDetails) {
        String login = toLogin(extractAuthDetail(authenticationDetails, AUTH_DETAILS_EMAIL));
        return membersRepository.findOneByLogin(login).orElseGet(() -> createMember(authenticationDetails));
    }

    private Member createMember(Map<String, Object> authenticationDetails) {
        Role defaultRole = rolesRepository.findByName(DEFAULT_ROLE_NAME).orElseThrow(DefaultRoleNotFoundException::new);
        String login = toLogin(extractAuthDetail(authenticationDetails, AUTH_DETAILS_EMAIL));
        Member member = Member.builder()
                .login(login)
                .name(extractAuthDetail(authenticationDetails, AUTH_DETAILS_NAME))
                .avatarPath(extractAuthDetail(authenticationDetails, AUTH_DETAILS_PICTURE))
                .roles(new HashSet<>(Collections.singletonList(defaultRole)))
                .badges(new HashSet<>())
                .wallet(Wallet.builder().gainedPoints(walletGainedPointsPool).giveAwayPool(walletGiveAwayPointsPool).build())
                .build();
        return membersRepository.save(member);
    }

    private String toLogin(String email) {
        return email.split("@")[0];
    }

    private String extractAuthDetail(Map<String, Object> authenticationDetails, String detailName) {
        return (String) authenticationDetails.get(detailName);
    }

    protected final static class NoMemberInSessionException extends AuthenticationException {
        private NoMemberInSessionException() {
            super("There is no current member data in session.");
        }
    }
}
