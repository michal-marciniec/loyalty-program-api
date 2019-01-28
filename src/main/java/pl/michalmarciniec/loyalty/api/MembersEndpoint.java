package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.dto.BadgeDto;
import pl.michalmarciniec.loyalty.domain.dto.MemberDto;
import pl.michalmarciniec.loyalty.domain.dto.ClaimedRewardDto;
import pl.michalmarciniec.loyalty.domain.dto.MemberWithOverallPoints;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import pl.michalmarciniec.loyalty.domain.entity.Rank;
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
@RequestMapping(path = "/api/members", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MembersEndpoint {
    private final MembersRepository membersRepository;
    private final AuthenticationService authenticationService;
    private final RankResolver rankResolver;

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        List<MemberWithOverallPoints> membersWithOverallPoints = membersRepository.getAllMembersWithOverallPoints();
        return ResponseEntity.ok(membersWithOverallPoints.stream()
                .map(this::toMemberDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/{memberId}")
    public ResponseEntity<MemberDto> getMember(@PathVariable Long memberId) {
        Member member = getEntityOrFail(() -> membersRepository.findById(memberId));
        MemberDto memberDto = MemberDto.basic(member, rankResolver.getMemberRank(member)).build();
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
        MemberDto memberDto = MemberDto.withWallet(member, rankResolver.getMemberRank(member)).build();
        return ResponseEntity.ok(memberDto);
    }

    @GetMapping(path = "/{memberId}/permissions")
    public ResponseEntity<List<String>> getMemberPermissions(@PathVariable Long memberId) {
        Member member = getEntityOrFail(() -> membersRepository.findById(memberId));
        return ResponseEntity.ok(member.getPermissions().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/{memberId}/badges")
    public ResponseEntity<List<BadgeDto>> getMemberBadges(@PathVariable Long memberId) {
        Member member = getEntityOrFail(() -> membersRepository.findById(memberId));
        return ResponseEntity.ok(member.getBadges().stream()
                .map(badge -> ModelMapper.map(badge, BadgeDto.BadgeDtoBuilder.class).build())
                .collect(Collectors.toList()));
    }

    private MemberDto toMemberDto(MemberWithOverallPoints memberWithOverallPoints) {
        Rank matchingRank = rankResolver.findMemberRankByOverallPoints(memberWithOverallPoints.getOverallPoints());
        return MemberDto.basic(memberWithOverallPoints.getMember(), matchingRank).build();
    }

}
