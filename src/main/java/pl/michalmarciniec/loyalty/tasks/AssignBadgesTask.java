package pl.michalmarciniec.loyalty.tasks;

import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository;
import pl.michalmarciniec.loyalty.db.BadgeConditionsRepository;
import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.dto.RankingItemDto;
import pl.michalmarciniec.loyalty.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.TemporalAdjusters.*;

@Component
@RequiredArgsConstructor
public class AssignBadgesTask {
    private final BadgeConditionsRepository badgeConditionsRepository;
    private final BonusesCategoriesRepository bonusesCategoriesRepository;
    private final MembersRepository membersRepository;

    @Scheduled(cron = "${tasks.assignBadges.memberOfTheYear}")
    @Transactional
    public void assignToMembersOfTheYear() {
        List<BadgeCondition> conditions = badgeConditionsRepository
                .findByConditionType(BadgeConditionType.MEMBER_OF_THE_YEAR);

        LocalDateTime now  = LocalDateTime.now();
        assignBadgesToBestMembers(now.with(firstDayOfYear()), now.with(lastDayOfYear()), conditions);
    }

    @Scheduled(cron = "${tasks.assignBadges.memberOfTheMonth}")
    @Transactional
    public void assignToMembersOfTheMonth() {
        List<BadgeCondition> conditions = badgeConditionsRepository
                .findByConditionType(BadgeConditionType.MEMBER_OF_THE_MONTH);

        LocalDateTime now  = LocalDateTime.now();
        assignBadgesToBestMembers(now.with(firstDayOfMonth()), now.with(lastDayOfMonth()), conditions);
    }

    @Scheduled(cron = "${tasks.assignBadges.memberOfTheDay}")
    @Transactional
    public void assignToMembersOfTheDay() {
        List<BadgeCondition> conditions = badgeConditionsRepository
                .findByConditionType(BadgeConditionType.MEMBER_OF_THE_DAY);

        LocalDateTime now  = LocalDateTime.now();
        assignBadgesToBestMembers(now.minusDays(1L), now, conditions);
    }

    private void assignBadgesToBestMembers(LocalDateTime startDate, LocalDateTime endDate, List<BadgeCondition> conditions) {
        List<BonusCategory> categories = bonusesCategoriesRepository.findAll();

        categories.forEach(category -> {
            List<Badge> categoryBadges = getConditionalBadgesForCategory(conditions, category);
            List<RankingItemDto> categoryMemberRankings = membersRepository.getCategoryRankings(startDate, endDate, category);
            addBadgesToBestMembers(categoryBadges, categoryMemberRankings);
        });

        List<Badge> badges = getConditionalBadges(conditions);
        List<RankingItemDto> memberRankings = membersRepository.getRankings(startDate, endDate);
        addBadgesToBestMembers(badges, memberRankings);
    }

    private void addBadgesToBestMembers(List<Badge> badges, List<RankingItemDto> memberRankings) {
        Long maxPoints = getMaxPoints(memberRankings);
        List<Optional<Member>> bestMembers = memberRankings
                .stream()
                .filter(memberRanking -> memberRanking.getPoints() > 0 && memberRanking.getPoints().equals(maxPoints))
                .map(RankingItemDto::getMember)
                .map(memberDto -> membersRepository.findById(memberDto.getId()))
                .collect(Collectors.toList());

        bestMembers.forEach(bestMember -> {
            bestMember.ifPresent(member -> member.getBadges().addAll(badges));
        });
    }

    private Long getMaxPoints(List<RankingItemDto> memberRankings) {
        return memberRankings
                .stream()
                .mapToLong(RankingItemDto::getPoints)
                .max()
                .orElse(0L);
    }

    private List<Badge> getConditionalBadgesForCategory(List<BadgeCondition> conditions, BonusCategory category) {
        return conditions.stream()
                .filter(condition -> condition.getBonusCategory().equals(Optional.of(category)))
                .map(BadgeCondition::getBadge)
                .collect(Collectors.toList());
    }

    private List<Badge> getConditionalBadges(List<BadgeCondition> conditions) {
        return conditions.stream()
                .filter(condition -> !condition.getBonusCategory().isPresent())
                .map(BadgeCondition::getBadge)
                .collect(Collectors.toList());
    }
}
