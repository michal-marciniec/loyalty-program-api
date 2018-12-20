package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.BonusCategory;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName;
import pl.michalmarciniec.loyalty.domain.entity.Permission;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BonusesCategoriesRepository extends JpaRepositoryWrapper<BonusCategory, Long> {
    Optional<BonusCategory> findByName(BonusCategoryName name);

    List<BonusCategory> findByPermissionIn(Collection<Permission> permissions);
}
