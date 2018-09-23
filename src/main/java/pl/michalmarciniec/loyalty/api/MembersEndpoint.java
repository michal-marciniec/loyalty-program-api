package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.domain.MemberDto;
import pl.michalmarciniec.loyalty.domain.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@LoyaltyProgramApi
@RequestMapping(path = "/members", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MembersEndpoint {

    private final MemberService memberService;

    @RequestMapping(method = GET)
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @RequestMapping(method = GET, path = "/me")
    public ResponseEntity<MemberDto> getCurrentMember() {
        return memberService.getCurrentMember()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

}
