package pl.michalmarciniec.loyalty.test.commons

import pl.michalmarciniec.loyalty.domain.entity.BonusCategory
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName.OVERTIME
import pl.michalmarciniec.loyalty.domain.entity.Permission
import pl.michalmarciniec.loyalty.domain.entity.PermissionName.MANAGE_OVERTIME
import pl.michalmarciniec.loyalty.domain.entity.Member
import pl.michalmarciniec.loyalty.domain.entity.Role
import pl.michalmarciniec.loyalty.domain.entity.RoleName.OVERTIME_MANAGER
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

fun mockMemberAsOvertimeManager(): Member {
    val overtimeManagerRole: Role = Role.builder()
            .name(OVERTIME_MANAGER)
            .permissions(singletonList(Permission.builder().name(MANAGE_OVERTIME).build()))
            .build()

    return Member.builder()
            .avatarPath("overtime.png")
            .email("overtime-manager@sample.com")
            .name("overtime-manager")
            .roles(singletonList(overtimeManagerRole)).build()
}

fun mockMemberWithNoPermissions(): Member {
    val memberRole: Role = Role.builder().name(ROLE_MEMBER).permissions(emptyList()).build()

    return Member.builder()
            .avatarPath("default-avatar.png")
            .email("member@sample.com")
            .name("member")
            .roles(singletonList(memberRole)).build()
}