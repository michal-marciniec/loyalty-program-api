package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.db.RanksRepository;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import pl.michalmarciniec.loyalty.domain.entity.Rank;
import pl.michalmarciniec.loyalty.domain.entity.exceptions.NoRankFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankResolver {
    private final BonusesRepository bonusesRepository;
    private final RanksRepository ranksRepository;

    public Rank getMemberRank(Member member) {
        Long memberOverallPoints = bonusesRepository.getMemberOverallPoints(member.getId());
        return ranksRepository.findAllByOrderByPointsThresholdDesc().stream()
                .filter(rank -> rank.getPointsThreshold() <= memberOverallPoints)
                .findFirst()
                .orElseThrow(NoRankFoundException::new);
    }
}
