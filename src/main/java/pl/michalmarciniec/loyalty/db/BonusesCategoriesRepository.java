package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.BonusCategory;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName;

import java.util.Optional;

public interface BonusesCategoriesRepository extends JpaRepositoryWrapper<BonusCategory> {
    Optional<BonusCategory> findByName(BonusCategoryName name);
}
