package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.BadgeCondition;
import pl.michalmarciniec.loyalty.domain.entity.BadgeConditionType;

import java.util.List;

public interface BadgeConditionsRepository extends JpaRepositoryWrapper<BadgeCondition, Long> {

    List<BadgeCondition> findByConditionType(BadgeConditionType conditionType);
}
