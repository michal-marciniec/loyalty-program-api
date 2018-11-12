package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BonusesRepository extends JpaRepositoryWrapper<Bonus, Long> {


    List<Bonus> findByCreatedAtBetweenAndReceiverId(LocalDateTime beginDate, LocalDateTime endDate, Long receiverId);

    @Query(value = "SELECT COALESCE(SUM(a.points), 0) FROM Bonus a " +
            "WHERE a.giverId = :giverId " +
            "AND a.category.name = :category " +
            "AND a.createdAt BETWEEN :beginDate AND :endDate")
    Long getGivenPointsForBonusesOfType(@Param("giverId") Long giverId,
                                       @Param("category") BonusCategoryName category,
                                       @Param("beginDate") LocalDateTime beginDate,
                                       @Param("endDate") LocalDateTime endDate);
}
