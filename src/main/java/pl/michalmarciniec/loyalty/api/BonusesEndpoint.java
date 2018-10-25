package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.api.validation.EditBonusCommandValidator;
import pl.michalmarciniec.loyalty.api.validation.GiveBonusCommandValidator;
import pl.michalmarciniec.loyalty.domain.command.EditBonusCommand;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import pl.michalmarciniec.loyalty.domain.dto.BonusDto;
import pl.michalmarciniec.loyalty.domain.service.EditBonusService;
import pl.michalmarciniec.loyalty.domain.service.GiveBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@LoyaltyProgramApi
@RequestMapping(path = "/bonuses", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BonusesEndpoint {
    private final GiveBonusService giveBonusService;
    private final EditBonusService editBonusService;

    private final GiveBonusCommandValidator giveBonusCommandValidator;
    private final EditBonusCommandValidator editBonusCommandValidator;

    @RequestMapping(method = POST)
    public ResponseEntity<BonusDto> createBonus(@RequestBody @Validated GiveBonusCommand giveBonusCommand) {
        BonusDto commandResult = giveBonusService.giveBonus(giveBonusCommand);
        return ResponseEntity.ok(commandResult);
    }

    @RequestMapping(method = PATCH)
    public ResponseEntity<BonusDto> editBonus(@RequestBody @Validated EditBonusCommand editBonusCommand) {
        BonusDto commandResult = editBonusService.editBonus(editBonusCommand);
        return ResponseEntity.ok(commandResult);
    }

    @InitBinder(value = {"giveBonusCommand", "editBonusCommand"})
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(
                giveBonusCommandValidator,
                editBonusCommandValidator
        );
    }


}
