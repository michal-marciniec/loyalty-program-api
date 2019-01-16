package pl.michalmarciniec.loyalty.domain.service

import pl.michalmarciniec.loyalty.db.BonusesRepository
import pl.michalmarciniec.loyalty.db.MembersRepository
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

val bonusId = 1L
val currentMember = mockMemberWithNoPermissions()
val receiverId = 2L

@RunWith(JUnitPlatform::class)
class EditBonusServiceSpecs : Spek({
    describe("Responsible for editing a given bonus") {
        val bonusesRepository = mock<BonusesRepository>(BonusesRepository::class.java)
        val authenticationService = mock<AuthenticationService>(AuthenticationService::class.java)
        val membersRepository = mock<MembersRepository>(MembersRepository::class.java)
        val editBonusService = EditBonusService(bonusesRepository, authenticationService, membersRepository)

        it("Edit a given bonus") {
            val editBonusCommand = EditBonusCommand(bonusId, 10, "New description")
            val receiver = mockMemberWithNoPermissions()

            _when(bonusesRepository.findById(bonusId)).thenReturn(Optional.of(mockBonusBeforeEdition(10)))
            _when(membersRepository.findById(receiverId)).thenReturn(Optional.of(receiver))
            _when(authenticationService.currentMember).thenReturn(currentMember)

            val commandResult = editBonusService.editBonus(editBonusCommand)

            assertThat(commandResult.points).isEqualTo(10)
            assertThat(commandResult.description).isEqualTo("New description")

            assertThat(currentMember.wallet.giveAwayPool).isEqualTo(15)
            assertThat(currentMember.wallet.gainedPoints).isEqualTo(10)

            assertThat(receiver.wallet.giveAwayPool).isEqualTo(20)
            assertThat(receiver.wallet.gainedPoints).isEqualTo(15)
        }

        it("Permit member to edit bonus") {
            _when(bonusesRepository.findById(bonusId)).thenReturn(Optional.of(mockBonusBeforeEdition(10)))
            _when(authenticationService.currentMember).thenReturn(currentMember)
            assertThat(editBonusService.hasPermissionToEditBonus(bonusId)).isTrue()
        }

        it("Deny member to edit bonus, because he is not the giver") {
            _when(bonusesRepository.findById(bonusId)).thenReturn(Optional.of(mockBonusBeforeEdition(10)))
            _when(authenticationService.currentMember).thenReturn(mockMemberWithNoPermissions())
            assertThat(editBonusService.hasPermissionToEditBonus(bonusId)).isFalse()
        }

        it("Deny member to edit bonus, because edit period has been exceeded") {
            _when(bonusesRepository.findById(bonusId)).thenReturn(Optional.of(mockBonusBeforeEdition(0)))
            _when(authenticationService.currentMember).thenReturn(currentMember)
            assertThat(editBonusService.hasPermissionToEditBonus(bonusId)).isFalse()
        }
    }
})

fun mockBonusBeforeEdition(editPeriodInHours: Long): Bonus {
    return Bonus.builder()
            .points(5L)
            .description("Old description")
            .category(BonusCategory.builder().editPeriodInMinutes(editPeriodInHours).build())
            .giverId(currentMember.id)
            .receiverId(receiverId)
            .build()
}