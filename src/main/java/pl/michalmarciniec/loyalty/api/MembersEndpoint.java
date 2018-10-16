package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.MemberDto;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@LoyaltyProgramApi
@RequestMapping(path = "/members", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MembersEndpoint {

    private final MembersRepository membersRepository;
    private final AuthenticationService authenticationService;

    @RequestMapping(method = GET)
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return ResponseEntity.ok(membersRepository.findAll().stream()
                .map(MemberDto::of)
                .collect(Collectors.toList()));
    }

    @RequestMapping(method = GET, path = "/me")
    public ResponseEntity<MemberDto> getCurrentMember() {
        return ResponseEntity.ok(MemberDto.of(authenticationService.getCurrentMember()));
    }

}
