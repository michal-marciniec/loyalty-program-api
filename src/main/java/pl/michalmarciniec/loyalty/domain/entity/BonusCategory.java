package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bonus_categories")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class BonusCategory {

    @Builder
    private BonusCategory(BonusCategoryName name, Permission permission, Long pointsLimit, Long limitPeriodInDays) {
        this.name = name;
        this.permission = permission;
        this.pointsLimit = pointsLimit;
        this.limitPeriodInDays = limitPeriodInDays;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    BonusCategoryName name;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    Permission permission;

    @Column(name = "points_limit", nullable = false)
    Long pointsLimit;

    @Column(name = "limit_period", nullable = false)
    Long limitPeriodInDays;
}
