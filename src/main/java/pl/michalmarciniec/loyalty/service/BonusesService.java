package pl.michalmarciniec.loyalty.service;

import pl.michalmarciniec.loyalty.api.dto.BonusDto;
import pl.michalmarciniec.loyalty.domain.Bonus;
import pl.michalmarciniec.loyalty.service.db.BonusesRepository;
import pl.michalmarciniec.loyalty.service.db.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BonusesService {

    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private BonusesRepository bonusesRepository;

    public Optional<Bonus> giveBonus(BonusDto bonusDto) {
        return membersRepository.findById(bonusDto.getReceiverId())
                .map(receiver -> receiver.receiveBonus(bonusDto.getGiverId(), bonusDto.getPoints()))
                .map(bonus -> bonusesRepository.save(bonus));
    }
}
