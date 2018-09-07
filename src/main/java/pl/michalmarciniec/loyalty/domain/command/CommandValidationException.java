package pl.michalmarciniec.loyalty.domain.command;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Value
public final class CommandValidationException extends RuntimeException {
    Collection<String> failReasons;

    CommandValidationException(Collection<String> failReasons) {
        Preconditions.checkArgument(!failReasons.isEmpty());
        this.failReasons = failReasons;
    }
}
