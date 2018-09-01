package pl.michalmarciniec.loyalty.service.db;

import pl.michalmarciniec.loyalty.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Member, Long> {
}
