package pl.michalmarciniec.loyalty.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.michalmarciniec.loyalty.domain.dto.MemberWithOverallPoints;
import pl.michalmarciniec.loyalty.domain.dto.RankingItemDto;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategory;
import pl.michalmarciniec.loyalty.domain.entity.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MembersRepository extends JpaRepositoryWrapper<Member, Long> {

    Optional<Member> findOneByLogin(String login);

    @Query(value = "SELECT new pl.michalmarciniec.loyalty.domain.dto.RankingItemDto(m, SUM(b.points))" +
            " FROM Member m LEFT JOIN Bonus b ON m.id = b.receiverId " +
            " AND b.createdAt BETWEEN :startDate AND :endDate" +
            " AND b.category = :bonusCategory" +
            " GROUP BY m.id ORDER BY SUM(b.points) DESC")
    List<RankingItemDto> getCategoryRankings(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate,
                                             @Param("bonusCategory") BonusCategory bonusCategory);

    @Query(value = "SELECT new pl.michalmarciniec.loyalty.domain.dto.RankingItemDto(m, SUM(b.points))" +
            " FROM Member m LEFT JOIN Bonus b ON m.id = b.receiverId " +
            " AND b.createdAt BETWEEN :startDate AND :endDate" +
            " GROUP BY m.id ORDER BY SUM(b.points) DESC")
    List<RankingItemDto> getRankings(@Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT new pl.michalmarciniec.loyalty.domain.dto.RankingItemDto(m, SUM(b.points))" +
            " FROM Member m LEFT JOIN Bonus b ON m.id = b.receiverId " +
            " WHERE m.id = :memberId AND b.createdAt BETWEEN :startDate AND :endDate" +
            " GROUP BY m.id")
    RankingItemDto getMemberRanking(@Param("memberId") Long memberId,
                                    @Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT new pl.michalmarciniec.loyalty.domain.dto.MemberWithOverallPoints(m, COALESCE(SUM(b.points), 0))" +
            " FROM Member m LEFT JOIN Bonus b ON m.id = b.receiverId GROUP BY m.id")
    List<MemberWithOverallPoints> getAllMembersWithOverallPoints();

}
