package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.api.validation.EditBonusCommandValidator;
import pl.michalmarciniec.loyalty.api.validation.GiveBonusCommandValidator;
import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.domain.command.EditBonusCommand;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import pl.michalmarciniec.loyalty.domain.dto.BonusDto;
import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import pl.michalmarciniec.loyalty.domain.service.EditBonusService;
import pl.michalmarciniec.loyalty.domain.service.GiveBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@LoyaltyProgramApi
@RequestMapping(path = "/bonuses", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BonusesEndpoint {
    private final static String DATE_PARAM_FORMAT = "yyyy-MM-dd";
    private final GiveBonusService giveBonusService;
    private final EditBonusService editBonusService;
    private final BonusesRepository bonusesRepository;

    private final GiveBonusCommandValidator giveBonusCommandValidator;
    private final EditBonusCommandValidator editBonusCommandValidator;

    @GetMapping
    public ResponseEntity<List<BonusDto>> getAllBonuses(@RequestParam @DateTimeFormat(pattern = DATE_PARAM_FORMAT) LocalDate startDate,
                                                       @RequestParam @DateTimeFormat(pattern = DATE_PARAM_FORMAT) LocalDate endDate,
                                                       @RequestParam(required = false) Long memberId) {
        List<Bonus> bonuses = bonusesRepository.findByCreatedAtBetweenAndReceiverId(
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX),
                memberId);

        return ResponseEntity.ok(bonuses.stream()
                .map(BonusDto::of)
                .collect(Collectors.toList()));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<BonusDto> createBonus(@RequestBody @Validated GiveBonusCommand giveBonusCommand) {
        BonusDto commandResult = giveBonusService.giveBonus(giveBonusCommand);
        return ResponseEntity.ok(commandResult);
    }

    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<BonusDto> editBonus(@RequestBody @Validated EditBonusCommand editBonusCommand) {
        BonusDto commandResult = editBonusService.editBonus(editBonusCommand);
        return ResponseEntity.ok(commandResult);
    }

    @InitBinder("giveBonusCommand")
    private void initGiveBonusCommandBinder(WebDataBinder binder) {
        binder.setValidator(giveBonusCommandValidator);
    }

    @InitBinder("editBonusCommand")
    private void initEditBonusCommandBinder(WebDataBinder binder) {
        binder.setValidator(editBonusCommandValidator);
    }

}
