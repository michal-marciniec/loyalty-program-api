package pl.michalmarciniec.loyalty.domain.command;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GiveBonusCommandValidator extends CommandValidator {

    private final MembersRepository membersRepository;

    public void validate(GiveBonusCommand giveBonusCommand) {
        Optional<Member> giver = membersRepository.findById(giveBonusCommand.getGiverId());
        Optional<Member> receiver = membersRepository.findById(giveBonusCommand.getReceiverId());
        Map<Boolean, String> validations = new HashMap<>();
        validations.put(!giver.isPresent(), "Giver of the bonus must exist");
        validations.put(!receiver.isPresent(), "Receiver of the bonus must exist");
        validations.put(giveBonusCommand.getGiverId().equals(giveBonusCommand.getReceiverId()), "Cannot give bonus to oneself");
        validations.put(giveBonusCommand.getPoints() <= 0, "Points amount should be a positive number");
        validate(validations);
    }
}
