package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ranks")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class Rank extends BaseEntity {

    @Column(nullable = false, name = "name", length = 100, unique = true)
    String name;

    @Column(nullable = false, name = "points_threshold")
    Long pointsThreshold;
}
