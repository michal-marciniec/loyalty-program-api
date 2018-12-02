package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.Role;
import pl.michalmarciniec.loyalty.domain.entity.RoleName;

import java.util.Optional;

public interface RolesRepository extends JpaRepositoryWrapper<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
