package pl.michalmarciniec.loyalty.domain;

import lombok.Value;

@Value
public class RankingItemDto {
    MemberDto member;
    Long points;

    public RankingItemDto(Member member, Long points) {
        this.member = MemberDto.of(member);
        this.points = points == null ? 0 : points;
    }
}
