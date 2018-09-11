package pl.michalmarciniec.loyalty.domain.command

import pl.michalmarciniec.loyalty.db.MembersRepository
import pl.michalmarciniec.loyalty.domain.Member
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import java.util.*
import kotlin.test.assertFailsWith
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class GiveBonusCommandValidatorSpecs : Spek({
    describe("Responsible for validation of give bonus command") {
        val membersRepo = mock<MembersRepository>(MembersRepository::class.java)
        val commandValidator = GiveBonusCommandValidator(membersRepo)
        _when(membersRepo.findById(1)).thenReturn(Optional.of(mockMember()))
        _when(membersRepo.findById(2)).thenReturn(Optional.of(mockMember()))

        it("Should pass silently") {
            commandValidator.validate(GiveBonusCommand(1, 2, 10))
        }

        it("Should fail, because of invalid giver") {
            assertFailsWith<CommandValidationException> {
                commandValidator.validate(GiveBonusCommand(5, 2, 10))
            }
        }

        it("Should fail, because of invalid receiver") {
            assertFailsWith<CommandValidationException> {
                commandValidator.validate(GiveBonusCommand(1, 5, 10))
            }
        }

        it("Should fail, because of invalid points number") {
            assertFailsWith<CommandValidationException> {
                commandValidator.validate(GiveBonusCommand(1, 2, -10))
            }
        }

        it("Should fail, because of the same giver and receiver") {
            assertFailsWith<CommandValidationException> {
                commandValidator.validate(GiveBonusCommand(1, 1, 10))
            }
        }
    }
})

fun mockMember(): Member {
    return Member.builder()
            .avatarPath("default.svg")
            .name("The member")
            .build()
}