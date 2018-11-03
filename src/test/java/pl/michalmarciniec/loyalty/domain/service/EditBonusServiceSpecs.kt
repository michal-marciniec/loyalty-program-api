package pl.michalmarciniec.loyalty.domain.service

import pl.michalmarciniec.loyalty.db.BonusesRepository
import pl.michalmarciniec.loyalty.domain.command.EditBonusCommand
import pl.michalmarciniec.loyalty.domain.entity.Bonus
import pl.michalmarciniec.loyalty.domain.entity.BonusCategory
import pl.michalmarciniec.loyalty.security.AuthenticationService
import pl.michalmarciniec.loyalty.test.commons.mockMemberWithNoPermissions
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import java.util.*
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class EditBonusServiceSpecs : Spek({
    describe("Responsible for editing a given bonus") {
        val bonusesRepository = mock<BonusesRepository>(BonusesRepository::class.java)
        val authenticationService = mock<AuthenticationService>(AuthenticationService::class.java)
        val editBonusService = EditBonusService(bonusesRepository, authenticationService)
        val currentMember = mockMemberWithNoPermissions()

        val editBonusCommand = EditBonusCommand(1, 10, "New description")
        _when(authenticationService.currentMember).thenReturn(currentMember)

        it("Edit a given bonus") {
            _when(bonusesRepository.findById(1)).thenReturn(Optional.of(mockBonus(editBonusCommand, currentMember.id)))

            val commandResult = editBonusService.editBonus(editBonusCommand)

            assertThat(commandResult.points).isEqualTo(10)
            assertThat(commandResult.description).isEqualTo("New description")
        }

        it("Permit member to edit bonus") {
            _when(bonusesRepository.findById(1)).thenReturn(Optional.of(mockBonus(editBonusCommand, currentMember.id)))

            assertThat(editBonusService.hasPermissionToEditBonus(1)).isTrue()
        }

        it("Deny member to edit bonus, because he is not the giver") {
            _when(bonusesRepository.findById(1)).thenReturn(Optional.of(mockBonus(editBonusCommand, 4)))

            assertThat(editBonusService.hasPermissionToEditBonus(1)).isFalse()
        }

        it("Deny member to edit bonus, because edit period has been exceeded") {
            _when(bonusesRepository.findById(1)).thenReturn(Optional.of(mockBonus(editBonusCommand, currentMember.id, 0)))

            assertThat(editBonusService.hasPermissionToEditBonus(1)).isFalse()
        }

    }
})

fun mockBonus(editBonusCommand: EditBonusCommand, giverId: Long, editPeriodInHours: Long = 5): Bonus {
    return Bonus.builder()
            .giverId(giverId)
            .description(editBonusCommand.description)
            .points(editBonusCommand.points)
            .category(BonusCategory.builder().editPeriodInHours(editPeriodInHours).build())
            .build()
}