package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

public interface BonusesRepository extends JpaRepositoryWrapper<Bonus, Long> {

    @EntityGraph(value = "bonusWithComments", type = FETCH)
    Optional<Bonus> getById(Long bonusId);

    @Query(value = "SELECT COALESCE(SUM(a.points), 0)" +
            " FROM Bonus a WHERE a.receiverId = :memberId")
    Long getMemberOverallPoints(@Param("memberId") Long memberId);
}
