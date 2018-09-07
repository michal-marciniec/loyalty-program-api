package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Member, Long> {
}
