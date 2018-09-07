package pl.michalmarciniec.loyalty.domain;

import lombok.Value;

@Value
public class RankItemDto {
    MemberDto member;
    Long points;

    public RankItemDto(Member member, Long points) {
        this.member = MemberDto.of(member);
        this.points = points == null ? 0 : points;
    }
}
