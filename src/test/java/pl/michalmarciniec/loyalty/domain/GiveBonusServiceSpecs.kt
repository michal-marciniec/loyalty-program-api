package pl.michalmarciniec.loyalty.domain

import pl.michalmarciniec.loyalty.db.BonusesRepository
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommandValidator
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class GiveBonusServiceSpec : Spek({
    describe("Responsible for giving bonus to a user") {
        val commandValidator = mock<GiveBonusCommandValidator>(GiveBonusCommandValidator::class.java)
        val bonusesRepository = mock<BonusesRepository>(BonusesRepository::class.java)

        it("Give valid points number to another user") {
            val command = GiveBonusCommand(1, 2, 10)
            val giveBonusService = GiveBonusService(commandValidator, bonusesRepository)
            _when(bonusesRepository.save(any<Bonus>())).thenReturn(mockBonus(command))

            val givenBonus = giveBonusService.giveBonus(command)
            assertThat(givenBonus).isEqualToComparingFieldByField(mockBonus(command))

            verify(commandValidator).validate(command)
            verify(bonusesRepository).save(any<Bonus>())
        }
    }
})

fun mockBonus(giveBonusCommand: GiveBonusCommand): Bonus {
    return Bonus.builder()
            .giverId(giveBonusCommand.giverId)
            .receiverId(giveBonusCommand.receiverId)
            .points(giveBonusCommand.points)
            .build()
}
