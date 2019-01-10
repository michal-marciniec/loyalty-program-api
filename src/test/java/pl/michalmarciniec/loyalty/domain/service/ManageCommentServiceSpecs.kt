package pl.michalmarciniec.loyalty.domain.service

import pl.michalmarciniec.loyalty.db.BonusesRepository
import pl.michalmarciniec.loyalty.db.CommentsRepository
import pl.michalmarciniec.loyalty.domain.command.AddCommentCommand
import pl.michalmarciniec.loyalty.domain.command.EditCommentCommand
import pl.michalmarciniec.loyalty.security.AuthenticationService
import pl.michalmarciniec.loyalty.test.commons.mockBonus
import pl.michalmarciniec.loyalty.test.commons.mockComment
import pl.michalmarciniec.loyalty.test.commons.mockMemberWithNoPermissions
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class ManageCommentServiceSpecs : Spek({
    describe("Responsible for managing comments to bonuses") {
        val commentsRepository = mock<CommentsRepository>(CommentsRepository::class.java)
        val bonusesRepository = mock<BonusesRepository>(BonusesRepository::class.java)
        val authenticationService = mock<AuthenticationService>(AuthenticationService::class.java)
        val manageCommentService = ManageCommentService(commentsRepository, bonusesRepository, authenticationService)

        it("Add comment to bonus") {
            val addCommentCommand = AddCommentCommand("This is a comment", 5L)
            val bonus = mockBonus()
            _when(bonusesRepository.findById(5L)).thenReturn(Optional.of(bonus))
            val addedComment = manageCommentService.addComment(addCommentCommand)
            assertThat(bonus.comments).containsExactly(addedComment)
        }

        it("Edit bonus's comment") {
            val editCommentCommand = EditCommentCommand("Changed my mind", 1L)
            val comment = mockComment()
            _when(commentsRepository.findById(1L)).thenReturn(Optional.of(comment))
            val editedComment = manageCommentService.editComment(editCommentCommand)
            assertThat(editedComment.body).isEqualTo("Changed my mind")
        }

        it("Delete comment") {
            val comment = mockComment()
            _when(commentsRepository.findById(1L)).thenReturn(Optional.of(comment))
            manageCommentService.deleteComment(1L)
            verify(commentsRepository).delete(comment)
        }

        it("Permit operation on comment") {
            val currentMember = mockMemberWithNoPermissions()
            _when(authenticationService.currentMember).thenReturn(currentMember)
            val comment = mockComment(currentMember)
            _when(commentsRepository.findById(1L)).thenReturn(Optional.of(comment))

            val hasPermission = manageCommentService.hasCurrentMemberWrittenComment(1L)
            assertThat(hasPermission).isTrue()
        }

        it("Refuse operation on comment, because current member is not the comment's owner") {
            val currentMember = mockMemberWithNoPermissions()
            _when(authenticationService.currentMember).thenReturn(currentMember)
            val comment = mockComment()
            _when(commentsRepository.findById(1L)).thenReturn(Optional.of(comment))

            val hasPermission = manageCommentService.hasCurrentMemberWrittenComment(1L)
            assertThat(hasPermission).isFalse()
        }
    }
})