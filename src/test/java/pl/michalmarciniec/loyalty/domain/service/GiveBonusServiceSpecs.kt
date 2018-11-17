package pl.michalmarciniec.loyalty.domain.service

import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository
import pl.michalmarciniec.loyalty.db.BonusesRepository
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand
import pl.michalmarciniec.loyalty.domain.entity.Bonus
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName.OVERTIME
import pl.michalmarciniec.loyalty.security.AuthenticationService
import pl.michalmarciniec.loyalty.test.commons.mockOvertimeCategory10Days100Points
import pl.michalmarciniec.loyalty.test.commons.mockMemberAsModerator
import pl.michalmarciniec.loyalty.test.commons.mockMemberWithNoPermissions
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
class GiveBonusServiceSpecs : Spek({
    describe("Responsible for giving bonus to a user") {
        val bonusesRepository = mock<BonusesRepository>(BonusesRepository::class.java)
        val authenticationService = mock<AuthenticationService>(AuthenticationService::class.java)
        val bonusCategoryRepository = mock<BonusesCategoriesRepository>(BonusesCategoriesRepository::class.java)
        val giveBonusService = GiveBonusService(bonusesRepository, authenticationService, bonusCategoryRepository)

        it("Give valid points number to another user") {
            val command = GiveBonusCommand(1, 2, OVERTIME, "Thanks")
            val mockedBonus = mockBonus(command)
            _when(bonusCategoryRepository.findByName(OVERTIME)).thenReturn(Optional.of(mockOvertimeCategory10Days100Points()))
            _when(bonusesRepository.save(any<Bonus>())).thenReturn(mockedBonus)
            _when(authenticationService.currentMember).thenReturn(mockMemberAsModerator())

            val givenBonus = giveBonusService.giveBonus(command)

            assertThat(givenBonus).isEqualToComparingFieldByField(mockedBonus)
            verify(bonusesRepository).save(any<Bonus>())
        }

        it("Permit member to give bonus") {
            _when(bonusCategoryRepository.findByName(OVERTIME)).thenReturn(Optional.of(mockOvertimeCategory10Days100Points()))
            _when(authenticationService.currentMember).thenReturn(mockMemberAsModerator())
            _when(bonusesRepository.getGivenPointsForBonusesOfType(any(), any(), any(), any())).thenReturn(10)

            assertThat(giveBonusService.hasPermissionToGiveBonus(OVERTIME)).isTrue()
        }

        it("Deny member to give bonus, because of used points limits") {
            _when(bonusCategoryRepository.findByName(OVERTIME)).thenReturn(Optional.of(mockOvertimeCategory10Days100Points()))
            _when(authenticationService.currentMember).thenReturn(mockMemberAsModerator())
            _when(bonusesRepository.getGivenPointsForBonusesOfType(any(), any(), any(), any())).thenReturn(150)

            assertThat(giveBonusService.hasPermissionToGiveBonus(OVERTIME)).isFalse()
        }

        it("Deny member to give bonus, because of invalid permissions") {
            _when(bonusCategoryRepository.findByName(OVERTIME)).thenReturn(Optional.of(mockOvertimeCategory10Days100Points()))
            _when(authenticationService.currentMember).thenReturn(mockMemberWithNoPermissions())
            _when(bonusesRepository.getGivenPointsForBonusesOfType(any(), any(), any(), any())).thenReturn(10)

            assertThat(giveBonusService.hasPermissionToGiveBonus(OVERTIME)).isFalse()
        }
    }
})

fun mockBonus(giveBonusCommand: GiveBonusCommand): Bonus {
    return Bonus.builder()
            .giverId(1)
            .receiverId(giveBonusCommand.receiverId)
            .points(giveBonusCommand.points)
            .description(giveBonusCommand.description)
            .build()
}


