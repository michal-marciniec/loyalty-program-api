package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bonus_categories")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "name", callSuper = false)
@ToString
@Getter
@Builder
public class BonusCategory extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    BonusCategoryName name;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    Permission permission;

    @Column(name = "points_limit", nullable = false)
    Long pointsLimit;

    @Column(name = "limit_period", nullable = false)
    Long limitPeriodInDays;

    @Column(name = "edit_period", nullable = false)
    Long editPeriodInHours;
}
