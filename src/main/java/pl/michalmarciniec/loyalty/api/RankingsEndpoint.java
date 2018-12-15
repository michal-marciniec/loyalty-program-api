package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.dto.RankingItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@LoyaltyProgramApi
@RequestMapping(value = "/rankings", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RankingsEndpoint {
    private final static String DATE_PARAM_FORMAT = "yyyy-MM-dd";
    private final MembersRepository membersRepository;

    @GetMapping
    public ResponseEntity<List<RankingItemDto>> getRankings(@RequestParam @DateTimeFormat(pattern = DATE_PARAM_FORMAT) LocalDate startDate,
                                                            @RequestParam @DateTimeFormat(pattern = DATE_PARAM_FORMAT) LocalDate endDate) {
        List<RankingItemDto> rankings = membersRepository.getRankings(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        return ResponseEntity.ok(rankings);
    }

    @GetMapping(path = "/{memberId}")
    public ResponseEntity<RankingItemDto> getMemberRanking(@PathVariable("memberId") Long memberId,
                                                           @RequestParam @DateTimeFormat(pattern = DATE_PARAM_FORMAT) LocalDate startDate,
                                                           @RequestParam @DateTimeFormat(pattern = DATE_PARAM_FORMAT) LocalDate endDate) {

        RankingItemDto memberRanking = membersRepository.getMemberRanking(memberId, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        return ResponseEntity.ok(memberRanking);
    }


}
