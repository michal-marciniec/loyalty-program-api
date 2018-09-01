package pl.michalmarciniec.loyalty.service.db;

import pl.michalmarciniec.loyalty.domain.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusesRepository extends JpaRepository<Bonus, Long> {
}
