package pl.michalmarciniec.loyalty.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Member, Long> {
}
