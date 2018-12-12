package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.dto.MemberDto;
import pl.michalmarciniec.loyalty.domain.dto.MemberDto.MemberDtoBuilder;
import pl.michalmarciniec.loyalty.domain.dto.ClaimedRewardDto;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;
import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@LoyaltyProgramApi
@RequestMapping(path = "/members", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MembersEndpoint {
    private final MembersRepository membersRepository;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return ResponseEntity.ok(membersRepository.findAll().stream()
                .map(member -> ModelMapper.map(asList(member, member.getWallet()), MemberDtoBuilder.class).build())
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/{memberId}")
    public ResponseEntity<MemberDto> getMember(@PathVariable Long memberId) {
        Member member = getEntityOrFail(() -> membersRepository.findById(memberId));
        MemberDto memberDto = ModelMapper.map(asList(member, member.getWallet()), MemberDtoBuilder.class).build();
        return ResponseEntity.ok(memberDto);
    }

    @GetMapping(path = "/me/rewards")
    public ResponseEntity<List<ClaimedRewardDto>> getClaimedRewards() {
        Member member = authenticationService.getCurrentMember();
        return ResponseEntity.ok(member.getRewards().stream()
                .map(claimedReward -> ModelMapper.map(asList(claimedReward, claimedReward.getRewardInfo()), ClaimedRewardDto.ClaimedRewardDtoBuilder.class).build())
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<MemberDto> getCurrentMember() {
        Member member = authenticationService.getCurrentMember();
        MemberDto memberDto = ModelMapper.map(asList(member, member.getWallet()), MemberDtoBuilder.class).build();
        return ResponseEntity.ok(memberDto);
    }

    @GetMapping(path = "/{memberId}/permissions")
    public ResponseEntity<List<String>> getMemberPermissions(@PathVariable Long memberId) {
        Member member = getEntityOrFail(() -> membersRepository.findById(memberId));
        return ResponseEntity.ok(member.getPermissions().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
    }


}
