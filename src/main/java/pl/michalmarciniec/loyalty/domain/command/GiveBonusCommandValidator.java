package pl.michalmarciniec.loyalty.domain.command;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class GiveBonusCommandValidator extends CommandValidator {

    @Autowired
    private MembersRepository membersRepository;

    public void validate(GiveBonusCommand giveBonusCommand) {
        Optional<Member> giver = membersRepository.findById(giveBonusCommand.getGiverId());
        Optional<Member> receiver = membersRepository.findById(giveBonusCommand.getReceiverId());
        Map<Boolean, String> validations = new HashMap<>();
        validations.put(!giver.isPresent(), "Giver of the bonus must exist");
        validations.put(!receiver.isPresent(), "Giver of the bonus must exist");
        validations.put(giveBonusCommand.getPoints() <= 0, "Points amount should be a positive number");
        validate(validations);
    }
}
