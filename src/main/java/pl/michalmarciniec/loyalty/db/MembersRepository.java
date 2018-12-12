package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.Member;

import java.util.Optional;

public interface MembersRepository extends JpaRepositoryWrapper<Member, Long> {

    Optional<Member> findOneByEmail(String email);

}
