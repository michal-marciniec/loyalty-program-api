package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.domain.BonusDto;
import pl.michalmarciniec.loyalty.domain.GiveBonusService;
import pl.michalmarciniec.loyalty.domain.command.CommandValidationException;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@LoyaltyProgramApi
@RequestMapping(path = "/bonuses")
public class BonusesEndpoint {

    @Autowired
    private GiveBonusService giveBonusService;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BonusDto> createBonus(@RequestBody GiveBonusCommand giveBonusCommand) {
        BonusDto commandResult = giveBonusService.giveBonus(giveBonusCommand);
        return ResponseEntity.ok(commandResult);
    }

    @ExceptionHandler(CommandValidationException.class)
    public ResponseEntity<ErrorDto> commandNotValid(CommandValidationException ex) {
        ErrorDto errorInfo = new ErrorDto(ex.getFailReasons(), "Command validation error");
        return ResponseEntity.badRequest().body(errorInfo);
    }


}
