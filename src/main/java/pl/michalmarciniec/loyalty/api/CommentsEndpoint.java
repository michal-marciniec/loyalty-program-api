package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.domain.command.AddCommentCommand;
import pl.michalmarciniec.loyalty.domain.command.EditCommentCommand;
import pl.michalmarciniec.loyalty.domain.dto.CommentDto;
import pl.michalmarciniec.loyalty.domain.dto.MemberDto;
import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import pl.michalmarciniec.loyalty.domain.entity.Comment;
import pl.michalmarciniec.loyalty.domain.service.ManageCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@LoyaltyProgramApi
@RequestMapping(path = "/api/bonuses/{bonusId}/comments", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CommentsEndpoint {
    private final BonusesRepository bonusesRepository;
    private final ManageCommentService manageCommentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllBonusComments(@PathVariable Long bonusId) {
        Bonus bonus = getEntityOrFail(() -> bonusesRepository.getById(bonusId));
        return ResponseEntity.ok(bonus.getComments().stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@PathVariable Long bonusId, @RequestBody @Validated AddCommentCommand addCommentCommand) {
        addCommentCommand.setBonusId(bonusId);
        Comment comment = manageCommentService.addComment(addCommentCommand);
        return ResponseEntity.ok(toCommentDto(comment));
    }

    @PatchMapping(path = "/{commentId}")
    public ResponseEntity<CommentDto> editComment(@PathVariable Long commentId, @RequestBody @Validated EditCommentCommand editCommentCommand) {
        editCommentCommand.setCommentId(commentId);
        Comment comment = manageCommentService.editComment(editCommentCommand);
        return ResponseEntity.ok(toCommentDto(comment));
    }

    @DeleteMapping(path = "/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long commentId) {
        manageCommentService.deleteComment(commentId);
    }

    private CommentDto toCommentDto(Comment comment) {
        MemberDto member = ModelMapper.map(comment.getMember(), MemberDto.MemberDtoBuilder.class).build();
        return ModelMapper.map(comment, CommentDto.CommentDtoBuilder.class)
                .member(member)
                .build();
    }

}
