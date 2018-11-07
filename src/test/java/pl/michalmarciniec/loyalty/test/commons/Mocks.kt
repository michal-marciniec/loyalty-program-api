package pl.michalmarciniec.loyalty.test.commons

import pl.michalmarciniec.loyalty.domain.entity.BonusCategory
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName.OVERTIME
import pl.michalmarciniec.loyalty.domain.entity.Permission
import pl.michalmarciniec.loyalty.domain.entity.PermissionName.MANAGE_OVERTIME
import pl.michalmarciniec.loyalty.domain.entity.Member
import pl.michalmarciniec.loyalty.domain.entity.Role
import pl.michalmarciniec.loyalty.domain.entity.RoleName.ROLE_MODERATOR
import pl.michalmarciniec.loyalty.domain.entity.RoleName.ROLE_MEMBER
import java.util.Collections.emptyList
import java.util.Collections.singletonList

fun mockOvertimeCategory10Days100Points(): BonusCategory {
    return BonusCategory.builder()
            .name(OVERTIME)
            .limitPeriodInDays(10)
            .pointsLimit(100)
            .permission(Permission.builder().name(MANAGE_OVERTIME).build())
            .build()
}

fun mockMemberAsModerator(): Member {
    val moderator: Role = Role.builder()
            .name(ROLE_MODERATOR)
            .permissions(ArrayList(singletonList(Permission.builder().name(MANAGE_OVERTIME).build())))
            .build()

    return Member.builder()
            .avatarPath("moderator.png")
            .email("moderator@sample.com")
            .name("moderator")
            .roles(ArrayList(singletonList(moderator))).build()
}

fun mockMemberWithNoPermissions(): Member {
    val memberRole: Role = Role.builder().name(ROLE_MEMBER).permissions(emptyList()).build()

    return Member.builder()
            .avatarPath("default-avatar.png")
            .email("member@sample.com")
            .name("member")
            .roles(ArrayList(singletonList(memberRole))).build()
}