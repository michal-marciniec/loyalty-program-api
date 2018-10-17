package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BonusesRepository extends JpaRepositoryWrapper<Bonus, Long> {

    @Query(value = "SELECT SUM(a.points) FROM Bonus a " +
            "WHERE a.giverId = :giverId " +
            "AND a.category.name = :category " +
            "AND a.givenAt BETWEEN :beginDate AND :endDate")
    Long getGivenPointsForBonusesOfType(@Param("giverId") Long giverId,
                                       @Param("category") BonusCategoryName category,
                                       @Param("beginDate") LocalDateTime beginDate,
                                       @Param("endDate") LocalDateTime endDate);
}
