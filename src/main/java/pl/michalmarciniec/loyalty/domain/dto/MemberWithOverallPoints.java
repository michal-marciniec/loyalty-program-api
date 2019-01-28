package pl.michalmarciniec.loyalty.domain.dto;

import pl.michalmarciniec.loyalty.domain.entity.Member;
import lombok.Value;

@Value
public class MemberWithOverallPoints {
    Member member;
    Long overallPoints;
}
