package pl.michalmarciniec.loyalty.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "bonus_categories")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class BonusCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    BonusCategoryName name;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    @Getter
    Permission permission;

    @Column(name = "points_limit", nullable = false)
    @Getter
    Long pointsLimit;

    @Column(name = "limit_period", nullable = false)
    @Getter
    Long limitPeriodInDays;
}
