package pl.michalmarciniec.loyalty.domain

import pl.michalmarciniec.loyalty.db.BonusCategoryRepository
import pl.michalmarciniec.loyalty.db.BonusesRepository
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand
import pl.michalmarciniec.loyalty.domain.entity.*
import pl.michalmarciniec.loyalty.domain.service.GiveBonusService
import pl.michalmarciniec.loyalty.security.AuthenticationService
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class GiveBonusServiceSpec : Spek({
    describe("Responsible for giving bonus to a user") {
        val bonusesRepository = mock<BonusesRepository>(BonusesRepository::class.java)
        val authenticationService = mock<AuthenticationService>(AuthenticationService::class.java)
        val bonusCategoryRepository = mock<BonusCategoryRepository>(BonusCategoryRepository::class.java)
        val giveBonusService = GiveBonusService(bonusesRepository, authenticationService, bonusCategoryRepository)

        it("Give valid points number to another user") {
            val command = GiveBonusCommand(1, 2, BonusCategoryName.OVERTIME, "Thanks for staying late")
            _when(bonusCategoryRepository.findByName(BonusCategoryName.OVERTIME)).thenReturn(Optional.of(mockBonusCategory()))
            _when(bonusesRepository.save(any<Bonus>())).thenReturn(mockBonus(command))
            _when(authenticationService.currentMember).thenReturn(mockCurrentMember())

            val givenBonus = giveBonusService.giveBonus(command)
            assertThat(givenBonus).isEqualToComparingFieldByField(mockBonus(command))

            verify(bonusesRepository).save(any<Bonus>())
        }
    }
})

fun mockBonus(giveBonusCommand: GiveBonusCommand): Bonus {
    return Bonus.builder()
            .receiverId(giveBonusCommand.receiverId)
            .points(giveBonusCommand.points)
            .build()
}

fun mockBonusCategory(): BonusCategory {
    return BonusCategory.builder()
            .name(BonusCategoryName.OVERTIME)
            .limitPeriodInDays(10)
            .pointsLimit(100)
            .permission(Permission.builder().name(PermissionName.OVERTIME_MANAGER).build())
            .build()
}

fun mockCurrentMember(): Member {
    return Member.builder()
            .avatarPath("default-avatar.png")
            .email("member@sample.com")
            .name("member")
            .roles(emptyList())
            .build()
}
