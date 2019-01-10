package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

public interface BonusesRepository extends JpaRepositoryWrapper<Bonus, Long> {

    @EntityGraph(value = "bonusWithComments", type = FETCH)
    Optional<Bonus> getById(Long bonusId);
}
