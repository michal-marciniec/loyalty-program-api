package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.Member;
import pl.michalmarciniec.loyalty.domain.RankItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MembersRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT new pl.michalmarciniec.loyalty.domain.RankItemDto(p, SUM(a.points))" +
            " FROM Member p LEFT JOIN Bonus a ON p.id = a.receiverId " +
            " WHERE a.givenAt BETWEEN :startDate AND :endDate" +
            " GROUP BY p.id ORDER BY SUM(a.points) DESC")
    List<RankItemDto> getRank(@Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);
}
