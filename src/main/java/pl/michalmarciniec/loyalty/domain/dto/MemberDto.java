package pl.michalmarciniec.loyalty.domain.dto;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import pl.michalmarciniec.loyalty.domain.entity.Rank;
import pl.michalmarciniec.loyalty.domain.entity.Wallet;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MemberDto {
    Long id;
    String login;
    String name;
    String avatarPath;
    Long giveAwayPool;
    Long gainedPoints;
    String rankName;

    public static MemberDtoBuilder basic(Member member, Rank memberRank) {
        return ModelMapper.map(member, MemberDtoBuilder.class)
                .rankName(memberRank.getName());
    }

    public static MemberDtoBuilder withWallet(Member member, Rank memberRank) {
        Wallet wallet = member.getWallet();
        return basic(member, memberRank)
                .gainedPoints(wallet.getGainedPoints())
                .giveAwayPool(wallet.getGiveAwayPool());
    }
}
