package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.api.validation.GiveBonusCommandValidator;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import pl.michalmarciniec.loyalty.domain.dto.BonusDto;
import pl.michalmarciniec.loyalty.domain.service.GiveBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@LoyaltyProgramApi
@RequestMapping(path = "/bonuses")
@RequiredArgsConstructor
public class BonusesEndpoint {
    private final GiveBonusService giveBonusService;
    private final GiveBonusCommandValidator giveBonusCommandValidator;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BonusDto> createBonus(@RequestBody @Validated GiveBonusCommand giveBonusCommand) {
        BonusDto commandResult = giveBonusService.giveBonus(giveBonusCommand);
        return ResponseEntity.ok(commandResult);
    }

    @InitBinder("giveBonusCommand")
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(giveBonusCommandValidator);
    }


}
