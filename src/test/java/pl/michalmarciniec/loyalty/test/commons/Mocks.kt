package pl.michalmarciniec.loyalty.test.commons

import pl.michalmarciniec.loyalty.domain.entity.*
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName.OVERTIME
import pl.michalmarciniec.loyalty.domain.entity.PermissionName.MANAGE_OVERTIME
import pl.michalmarciniec.loyalty.domain.entity.RoleName.ROLE_MODERATOR
import pl.michalmarciniec.loyalty.domain.entity.RoleName.ROLE_MEMBER
import java.util.Collections.emptyList
import java.util.Collections.singletonList

fun mockOvertimeCategory100Points(): BonusCategory {
    return BonusCategory.builder()
            .name(OVERTIME)
            .permission(Permission.builder().name(MANAGE_OVERTIME).build())
            .pointsPool(100)
            .build()
}

fun mockMemberAsModerator(): Member {
    val moderator: Role = Role.builder()
            .name(ROLE_MODERATOR)
            .permissions(ArrayList(singletonList(Permission.builder().name(MANAGE_OVERTIME).build())))
            .build()

    return Member.builder()
            .avatarPath("moderator.png")
            .login("moderator")
            .name("moderator")
            .wallet(Wallet.builder().giveAwayPool(10L).gainedPoints(0L).build())
            .roles(HashSet(singletonList(moderator))).build()
}

fun mockMemberWithNoPermissions(): Member {
    val memberRole: Role = Role.builder().name(ROLE_MEMBER).permissions(emptyList()).build()

    return Member.builder()
            .avatarPath("default-avatar.png")
            .login("member")
            .name("member")
            .wallet(Wallet.builder().giveAwayPool(20L).gainedPoints(10L).build())
            .roles(HashSet(singletonList(memberRole))).build()
}