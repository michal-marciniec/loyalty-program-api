package pl.michalmarciniec.loyalty.domain;

import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GiveBonusService {

    private final BonusesRepository bonusesRepository;

    @Transactional
    public BonusDto giveBonus(GiveBonusCommand giveBonusCommand) {
        log.debug("Attempting to give bonus: {}", giveBonusCommand);
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
