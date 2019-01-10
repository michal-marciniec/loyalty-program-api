package pl.michalmarciniec.loyalty.domain.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentDto {
    Long commentId;
    MemberDto member;
    String body;
}
