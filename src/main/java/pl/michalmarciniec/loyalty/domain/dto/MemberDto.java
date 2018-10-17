package pl.michalmarciniec.loyalty.domain.dto;

import pl.michalmarciniec.loyalty.domain.entity.Member;
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
                .id(member.getId())
                .email(member.getEmail())
                .avatarPath(member.getAvatarPath())
                .name(member.getName())
                .build();
    }


}
