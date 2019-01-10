package pl.michalmarciniec.loyalty.domain.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditCommentCommand {
    @NotNull
    @Length(max = 500)
    String body;
    Long commentId;
}
