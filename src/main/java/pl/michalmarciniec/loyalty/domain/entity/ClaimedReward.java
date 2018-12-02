package pl.michalmarciniec.loyalty.domain.entity;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "member_rewards")
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class ClaimedReward extends BaseEntity {

    @Column(name = "member_id", nullable = false)
    Long memberId;

    @Embedded
    RewardInfo rewardInfo;

    @Column(name = "status", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardStatus status;

    public void changeStatus(RewardStatus newStatus) {
        this.status = newStatus;
    }
}
