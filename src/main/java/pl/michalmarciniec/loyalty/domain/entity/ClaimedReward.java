package pl.michalmarciniec.loyalty.domain.entity;

import com.google.common.base.Preconditions;
import lombok.*;

import javax.persistence.*;

import static pl.michalmarciniec.loyalty.domain.entity.RewardStatus.*;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "member_rewards")
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Builder
public class ClaimedReward extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @Embedded
    RewardInfo rewardInfo;

    @Column(name = "status", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardStatus status;

    public void reject() {
        Preconditions.checkArgument(status == PENDING);
        member.getWallet().gainedPoints += rewardInfo.price;
        status = REJECTED;
    }

    public void realize() {
        Preconditions.checkArgument(status == PENDING);
        status = REALIZED;
    }

}
