package pl.michalmarciniec.loyalty.test.commons

import pl.michalmarciniec.loyalty.domain.entity.*
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName.*
import pl.michalmarciniec.loyalty.domain.entity.PermissionName.MANAGE_OVERTIME
import pl.michalmarciniec.loyalty.domain.entity.PermissionName.REGULAR_MEMBER
import pl.michalmarciniec.loyalty.domain.entity.RoleName.ROLE_MODERATOR
import pl.michalmarciniec.loyalty.domain.entity.RoleName.ROLE_MEMBER
import java.time.LocalDateTime
import java.util.*
import java.util.Collections.emptyList
import java.util.Collections.singletonList
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

fun mockOvertimeCategory100Points(): BonusCategory {
    val bonusCategory = BonusCategory.builder()
            .name(OVERTIME)
            .permission(Permission.builder().name(MANAGE_OVERTIME).build())
            .pointsPool(100)
            .editPeriodInHours(10)
            .build()
    bonusCategory.id = UUID.randomUUID().leastSignificantBits
    return bonusCategory
}

fun mockNoLimitCategory(): BonusCategory {
    val bonusCategory = BonusCategory.builder()
            .editPeriodInHours(1)
            .name(NO_LIMIT)
            .permission(Permission.builder().name(REGULAR_MEMBER).build())
            .pointsPool(null)
            .build()
    bonusCategory.id = UUID.randomUUID().leastSignificantBits
    return bonusCategory
}

fun mockMemberAsModerator(): Member {
    val moderator: Role = Role.builder()
            .name(ROLE_MODERATOR)
            .permissions(ArrayList(singletonList(Permission.builder().name(MANAGE_OVERTIME).build())))
            .build()
    moderator.id = UUID.randomUUID().leastSignificantBits

    val member = Member.builder()
            .avatarPath("moderator.png")
            .login("moderator")
            .rewards(HashSet())
            .name("moderator")
            .wallet(Wallet.builder().giveAwayPool(10L).gainedPoints(0L).build())
            .roles(HashSet(singletonList(moderator))).build()
    member.id = UUID.randomUUID().leastSignificantBits
    return member
}

fun mockMemberWithNoPermissions(): Member {
    val memberRole: Role = Role.builder().name(ROLE_MEMBER).permissions(emptyList()).build()
    memberRole.id = UUID.randomUUID().leastSignificantBits

    val member = Member.builder()
            .avatarPath("default-avatar.png")
            .login("member")
            .name("member")
            .rewards(HashSet())
            .wallet(Wallet.builder().giveAwayPool(20L).gainedPoints(10L).build())
            .roles(HashSet(singletonList(memberRole))).build()
    member.id = UUID.randomUUID().leastSignificantBits
    return member
}

fun mockReward(price: Long, amount: Long, expirationDate: LocalDateTime): Reward {
    val reward = Reward.builder()
            .rewardInfo(RewardInfo.builder()
                    .description("A reward")
                    .logoPath("path/to/logo")
                    .price(price)
                    .build())
            .amount(amount)
            .expirationDate(expirationDate)
            .build()
    reward.id = UUID.randomUUID().leastSignificantBits
    return reward
}

fun mockBonus(): Bonus {
    val bonus = Bonus.builder()
            .category(mockNoLimitCategory())
            .comments(ArrayList())
            .description("Nice")
            .giverId(1L)
            .receiverId(1L)
            .build()
    bonus.id = UUID.randomUUID().leastSignificantBits
    return bonus
}

fun mockComment(member: Member = mockMemberWithNoPermissions()): Comment {
    val comment = Comment.builder()
            .bonusId(1L)
            .body("This is a comment")
            .member(member)
            .build()
    comment.id = UUID.randomUUID().leastSignificantBits
    return comment
}

fun mockBadge(): Badge {
    val badge = Badge.builder()
            .description("description")
            .imagePath("image path")
            .build()

    badge.id = UUID.randomUUID().leastSignificantBits
    return badge
}