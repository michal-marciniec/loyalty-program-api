package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.BonusCategory;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName;

import java.util.Optional;

public interface BonusCategoryRepository extends JpaRepositoryWrapper<BonusCategory, Long> {
    Optional<BonusCategory> findByName(BonusCategoryName name);
}
