package pl.michalmarciniec.loyalty.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MemberDto {

    Long id;
    String email;
    String name;
    String avatarPath;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.id)
                .email(member.email)
                .avatarPath(member.avatarPath)
                .name(member.name)
                .build();
    }


}
