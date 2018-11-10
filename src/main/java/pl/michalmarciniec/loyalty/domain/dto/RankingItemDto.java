package pl.michalmarciniec.loyalty.domain.dto;

import pl.michalmarciniec.loyalty.domain.dto.MemberDto.MemberDtoBuilder;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import pl.michalmarciniec.loyalty.mapper.DtoMapper;
import lombok.Value;

@Value
public class RankingItemDto {
    MemberDto member;
    Long points;

    public RankingItemDto(Member member, Long points) {
        this.member = DtoMapper.map(member, MemberDtoBuilder.class).build();
        this.points = points == null ? 0 : points;
    }
}
