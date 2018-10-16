package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.BonusCategory;
import pl.michalmarciniec.loyalty.domain.BonusCategoryName;

import java.util.Optional;

public interface BonusCategoryRepository extends JpaRepositoryWrapper<BonusCategory, Long> {
    Optional<BonusCategory> findByName(BonusCategoryName name);
}
