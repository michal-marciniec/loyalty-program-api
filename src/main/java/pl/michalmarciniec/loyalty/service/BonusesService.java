package pl.michalmarciniec.loyalty.service;

import pl.michalmarciniec.loyalty.api.dto.BonusDto;
import pl.michalmarciniec.loyalty.domain.Bonus;
import pl.michalmarciniec.loyalty.service.db.BonusesRepository;
import pl.michalmarciniec.loyalty.service.db.MembersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BonusesService {
    private Logger log = LoggerFactory.getLogger(BonusesService.class);

    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private BonusesRepository bonusesRepository;

    @Transactional
    public Optional<Bonus> giveBonus(BonusDto bonusDto) {
        log.debug("Attempting to give bonus: {}", bonusDto);
        Optional<Bonus> givenBonus = membersRepository.findById(bonusDto.getReceiverId())
                .map(receiver -> receiver.receiveBonus(bonusDto.getGiverId(), bonusDto.getPoints()))
                .map(bonus -> bonusesRepository.save(bonus));
        log.debug("Bonus {} given", bonusDto);
        return givenBonus;
    }
}
