package pl.michalmarciniec.loyalty.domain.service

import pl.michalmarciniec.loyalty.db.BadgesRepository
import pl.michalmarciniec.loyalty.db.MembersRepository
import pl.michalmarciniec.loyalty.domain.command.CreateBadgeCommand
import pl.michalmarciniec.loyalty.domain.entity.Badge
import pl.michalmarciniec.loyalty.test.commons.mockBadge
import pl.michalmarciniec.loyalty.test.commons.mockMemberWithNoPermissions
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class ManageBadgesServiceSpecs : Spek({
    describe("Responsible for handling assigning and removal members badges") {
        val badgesRepository = mock<BadgesRepository>(BadgesRepository::class.java)
        val membersRepository = mock<MembersRepository>(MembersRepository::class.java)
        val manageBadgesService = ManageBadgesService(badgesRepository, membersRepository)

        it("Creates new badge") {

            val createBadgeCommand = CreateBadgeCommand("description", "image")

            _when(badgesRepository.save(ArgumentMatchers.any(Badge::class.java))).then(returnsFirstArg<Badge>())

            val badge = manageBadgesService.createBadge(createBadgeCommand)

            assertThat(badge.description).isEqualTo(createBadgeCommand.description)
            assertThat(badge.imagePath).isEqualTo(createBadgeCommand.imagePath)
            Mockito.verify(badgesRepository).save(badge)
        }

        it("Updates existing badge") {
            val createBadgeCommand = CreateBadgeCommand("updated description", "updated image")
            val existingBadge = mockBadge()
            val badgeId = 1L

            _when(badgesRepository.findById(badgeId)).thenReturn(Optional.of(existingBadge))

            manageBadgesService.editBadge(badgeId, createBadgeCommand)

            assertThat(existingBadge.description).isEqualTo(createBadgeCommand.description)
            assertThat(existingBadge.imagePath).isEqualTo(createBadgeCommand.imagePath)
        }

        it("Deletes existing badge") {
            val badgeId = 1L

            manageBadgesService.deleteBadge(badgeId)

            Mockito.verify(badgesRepository).delete(badgeId)
        }

        it("Assigns badge to member") {
            val badgeId = 1L
            val memberId = 1L
            val existingBadge = mockBadge()
            val existingMember = mockMemberWithNoPermissions()

            _when(badgesRepository.findById(badgeId)).thenReturn(Optional.of(existingBadge))
            _when(membersRepository.findById(memberId)).thenReturn(Optional.of(existingMember))

            manageBadgesService.assignBadgeToMember(badgeId, memberId)

            assertThat(existingMember.badges).containsExactly(existingBadge)
        }

        it("Removes badge to member") {
            val badgeId = 1L
            val memberId = 1L
            val existingBadge = mockBadge()
            val existingMember = mockMemberWithNoPermissions()
            existingMember.badges.add(existingBadge)

            _when(badgesRepository.findById(badgeId)).thenReturn(Optional.of(existingBadge))
            _when(membersRepository.findById(memberId)).thenReturn(Optional.of(existingMember))

            manageBadgesService.removeBadgeFromMember(badgeId, memberId)

            assertThat(existingMember.badges).isEmpty()
        }
    }
})