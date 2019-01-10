package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.db.CommentsRepository;
import pl.michalmarciniec.loyalty.domain.command.AddCommentCommand;
import pl.michalmarciniec.loyalty.domain.command.EditCommentCommand;
import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import pl.michalmarciniec.loyalty.domain.entity.Comment;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Service("manageCommentService")
@RequiredArgsConstructor
public class ManageCommentService {
    private final CommentsRepository commentsRepository;
    private final BonusesRepository bonusesRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public Comment addComment(AddCommentCommand addCommentCommand) {
        Member currentMember = authenticationService.getCurrentMember();
        Bonus bonus = getEntityOrFail(() -> bonusesRepository.findById(addCommentCommand.getBonusId()));
        Comment comment = buildComment(addCommentCommand, currentMember);
        bonus.addComment(comment);
        return comment;
    }

    @Transactional
    @PreAuthorize("@manageCommentService.hasCurrentMemberWrittenComment(#editCommentCommand.getCommentId())")
    public Comment editComment(EditCommentCommand editCommentCommand) {
        Comment comment = getEntityOrFail(() -> commentsRepository.findById(editCommentCommand.getCommentId()));
        comment.changeBody(editCommentCommand.getBody());
        return comment;
    }

    @Transactional
    @PreAuthorize("@manageCommentService.hasCurrentMemberWrittenComment(#commentId)")
    public void deleteComment(Long commentId) {
        Comment toDelete = getEntityOrFail(() -> commentsRepository.findById(commentId));
        commentsRepository.delete(toDelete);
    }

    public boolean hasCurrentMemberWrittenComment(Long commentId) {
        Member commentOwner = getEntityOrFail(() -> commentsRepository.findById(commentId)).getMember();
        Member currentMember = authenticationService.getCurrentMember();
        return commentOwner.equals(currentMember);
    }

    private Comment buildComment(AddCommentCommand addCommentCommand, Member member) {
        return Comment.builder()
                .body(addCommentCommand.getBody())
                .bonusId(addCommentCommand.getBonusId())
                .member(member)
                .build();
    }
}
