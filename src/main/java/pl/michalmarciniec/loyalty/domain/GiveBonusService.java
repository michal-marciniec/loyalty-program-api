package pl.michalmarciniec.loyalty.domain;

import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class GiveBonusService {

    @Autowired
    private GiveBonusCommandValidator commandValidator;
    @Autowired
    private BonusesRepository bonusesRepository;

    @Transactional
    public BonusDto giveBonus(GiveBonusCommand giveBonusCommand) {
        log.debug("Attempting to give bonus: {}", giveBonusCommand);
        commandValidator.validate(giveBonusCommand);
        Bonus savedBonus = bonusesRepository.save(buildBonus(giveBonusCommand));
        log.debug("Bonus {} given", savedBonus);
        return BonusDto.of(savedBonus);
    }

    private Bonus buildBonus(GiveBonusCommand giveBonusCommand) {
        return Bonus.builder()
                .giverId(giveBonusCommand.getGiverId())
                .receiverId(giveBonusCommand.getReceiverId())
                .points(giveBonusCommand.getPoints())
                .build();
    }

}
