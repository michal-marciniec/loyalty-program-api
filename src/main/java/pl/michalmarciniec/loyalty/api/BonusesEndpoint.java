package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.api.dto.BonusDto;
import pl.michalmarciniec.loyalty.domain.Bonus;
import pl.michalmarciniec.loyalty.service.BonusesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@LoyaltyProgramApi
@RequestMapping(path = "/bonuses")
public class BonusesEndpoint {

    @Autowired
    private BonusesService bonusesService;

    @RequestMapping(method = POST, consumes = "application/json")
    @Transactional
    public ResponseEntity<Bonus> createBonus(@RequestBody BonusDto bonusDto) {
        return bonusesService.giveBonus(bonusDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
