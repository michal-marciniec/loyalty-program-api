package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.RankingItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@LoyaltyProgramApi
@RequestMapping("/rankings")
public class RankingsEndpoint {
    private final static String DATE_PARAM_FORMAT = "yyyy-MM-dd";

    @Autowired
    private MembersRepository membersRepository;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RankingItemDto>> getRankings(@RequestParam @DateTimeFormat(pattern = DATE_PARAM_FORMAT) LocalDate startDate,
                                                            @RequestParam @DateTimeFormat(pattern = DATE_PARAM_FORMAT) LocalDate endDate) {
        List<RankingItemDto> rankings = membersRepository.getRankings(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        return ResponseEntity.ok(rankings);
    }

    @RequestMapping(path = "/{memberId}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RankingItemDto> getMemberRanking(@PathVariable("memberId") Long memberId,
                                                           @RequestParam @DateTimeFormat(pattern = DATE_PARAM_FORMAT) LocalDate startDate,
                                                           @RequestParam @DateTimeFormat(pattern = DATE_PARAM_FORMAT) LocalDate endDate) {

        RankingItemDto memberRanking = membersRepository.getMemberRanking(memberId, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        return ResponseEntity.ok(memberRanking);
    }


}
