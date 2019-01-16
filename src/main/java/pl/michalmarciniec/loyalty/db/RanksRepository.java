package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.Rank;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface RanksRepository extends JpaRepositoryWrapper<Rank, Long> {

    @Cacheable("ranks")
    List<Rank> findAllByOrderByPointsThresholdDesc();
}
