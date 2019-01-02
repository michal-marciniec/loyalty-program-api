package pl.michalmarciniec.loyalty.domain.service

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository
import pl.michalmarciniec.loyalty.db.BonusesRepository
import pl.michalmarciniec.loyalty.db.MembersRepository
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand
import pl.michalmarciniec.loyalty.domain.entity.Bonus
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName.NO_LIMIT
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName.OVERTIME
import pl.michalmarciniec.loyalty.security.AuthenticationService
import pl.michalmarciniec.loyalty.test.commons.mockMemberAsModerator
import pl.michalmarciniec.loyalty.test.commons.mockMemberWithNoPermissions
import pl.michalmarciniec.loyalty.test.commons.mockOvertimeCategory100Points
import pl.michalmarciniec.loyalty.test.commons.mockNoLimitCategory
import java.util.*
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class GiveBonusServiceSpecs : Spek({
    describe("Responsible for giving bonus to a user") {
        val bonusesRepository = mock<BonusesRepository>(BonusesRepository::class.java)
        val authenticationService = mock<AuthenticationService>(AuthenticationService::class.java)
        val bonusCategoryRepository = mock<BonusesCategoriesRepository>(BonusesCategoriesRepository::class.java)
        val membersRepository = mock<MembersRepository>(MembersRepository::class.java)

        val giveBonusStrategies = GiveBonusStrategies(membersRepository, bonusCategoryRepository, authenticationService)
        val giveBonusService = GiveBonusService(bonusesRepository, authenticationService, bonusCategoryRepository, giveBonusStrategies)

        it("Give valid points number to another user") {
            val command = GiveBonusCommand(2, 2, NO_LIMIT, "That was great")
            val mockedBonus = mockBonus(command)
            val giver = mockMemberWithNoPermissions()
            val receiver = mockMemberWithNoPermissions()

            _when(membersRepository.findOne(1L)).thenReturn(giver)
            _when(membersRepository.findById(1L)).thenReturn(Optional.of(giver))
            _when(membersRepository.findById(2L)).thenReturn(Optional.of(receiver))

            _when(bonusCategoryRepository.findByName(NO_LIMIT)).thenReturn(Optional.of(mockNoLimitCategory()))
            _when(bonusesRepository.save(any<Bonus>())).thenReturn(mockedBonus)
            _when(authenticationService.currentMember).thenReturn(giver)

            val givenBonus = giveBonusService.giveBonus(command)

            assertThat(givenBonus).isEqualToComparingFieldByField(mockedBonus)
            assertThat(giver.wallet.giveAwayPool).isEqualTo(18)
            assertThat(receiver.wallet.gainedPoints).isEqualTo(12)
            verify(bonusesRepository).save(any<Bonus>())
        }

        it("Permit member to give bonus") {
            _when(bonusCategoryRepository.findByName(OVERTIME)).thenReturn(Optional.of(mockOvertimeCategory100Points()))
            _when(authenticationService.currentMember).thenReturn(mockMemberAsModerator())

            assertThat(giveBonusService.hasPermissionToGiveBonus(OVERTIME)).isTrue()
        }

        it("Deny member to give bonus, because of invalid permissions") {
            _when(bonusCategoryRepository.findByName(OVERTIME)).thenReturn(Optional.of(mockOvertimeCategory100Points()))
            _when(authenticationService.currentMember).thenReturn(mockMemberWithNoPermissions())

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


