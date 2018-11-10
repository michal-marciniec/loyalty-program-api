package pl.michalmarciniec.loyalty.domain.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MemberDto {
    Long id;
    String email;
    String name;
    String avatarPath;
}
