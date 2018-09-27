package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.Role;

import java.util.Optional;

public interface RolesRepository extends JpaRepositoryWithOptionals<Role, Long> {
    Optional<Role> findByName(String name);
}
