package pl.michalmarciniec.loyalty.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MemberDto {

    Long id;
    String name;
    String avatarPath;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.id)
                .avatarPath(member.avatarPath)
                .name(member.name)
                .build();
    }


}
