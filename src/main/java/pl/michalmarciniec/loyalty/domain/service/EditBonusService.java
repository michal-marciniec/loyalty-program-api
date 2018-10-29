package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.domain.command.EditBonusCommand;
import pl.michalmarciniec.loyalty.domain.dto.BonusDto;
import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;
import static java.time.temporal.ChronoUnit.SECONDS;

@Service("editBonusService")
@Slf4j
@RequiredArgsConstructor
public class EditBonusService {
    private final BonusesRepository bonusesRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    @PreAuthorize("@editBonusService.hasPermissionToEditBonus(#editBonusCommand.getId())")
    public BonusDto editBonus(EditBonusCommand editBonusCommand) {
        Bonus bonus = getEntityOrFail(() -> bonusesRepository.findById(editBonusCommand.getId()));
        log.debug("Editing bonus: {}", bonus);
        bonus.changeDescriptionAndPoints(editBonusCommand.getDescription(), editBonusCommand.getPoints());
        return BonusDto.of(bonus);
    }

    public boolean hasPermissionToEditBonus(Long bonusId) {
        Bonus bonus = getEntityOrFail(() -> bonusesRepository.findById(bonusId));
        return isCurrentMemberTheGiver(bonus) && isFirstEditionOf(bonus) && editPeriodNotExceeded(bonus);
    }

    private boolean isCurrentMemberTheGiver(Bonus bonus) {
        return authenticationService.getCurrentMember().getId().equals(bonus.getGiverId());
    }

    private boolean isFirstEditionOf(Bonus bonus) {
        return bonus.getEditedAt().truncatedTo(SECONDS)
                .isEqual(bonus.getCreatedAt().truncatedTo(SECONDS));
    }

    private boolean editPeriodNotExceeded(Bonus bonus) {
        Long editPeriodInHours = bonus.getCategory().getEditPeriodInHours();
        return LocalDateTime.now().isBefore(bonus.getEditedAt().plusHours(editPeriodInHours));
    }

}
