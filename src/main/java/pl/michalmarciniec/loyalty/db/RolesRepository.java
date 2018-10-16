package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.Role;
import pl.michalmarciniec.loyalty.security.RoleName;

import java.util.Optional;

public interface RolesRepository extends JpaRepositoryWrapper<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
