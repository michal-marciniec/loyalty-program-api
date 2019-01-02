package pl.michalmarciniec.loyalty.domain.dto;

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
}
