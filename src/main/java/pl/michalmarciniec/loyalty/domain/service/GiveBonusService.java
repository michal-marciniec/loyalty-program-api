package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository;
import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import pl.michalmarciniec.loyalty.domain.dto.BonusDto;
import pl.michalmarciniec.loyalty.domain.dto.BonusDto.BonusDtoBuilder;
import pl.michalmarciniec.loyalty.domain.entity.*;
import pl.michalmarciniec.loyalty.mapper.DtoMapper;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Service("giveBonusService")
@Slf4j
@RequiredArgsConstructor
public class GiveBonusService {

    private final BonusesRepository bonusesRepository;
    private final AuthenticationService authenticationService;
    private final BonusesCategoriesRepository bonusesCategoriesRepository;

    @Transactional
    @PreAuthorize("@giveBonusService.hasPermissionToGiveBonus(#giveBonusCommand.getCategory())")
    public BonusDto giveBonus(GiveBonusCommand giveBonusCommand) {
        log.debug("Attempting to give bonus: {}", giveBonusCommand);
        Bonus savedBonus = bonusesRepository.save(buildBonus(giveBonusCommand));
        log.debug("Bonus {} given", savedBonus);
        return DtoMapper.map(savedBonus, BonusDtoBuilder.class).build();
    }

    public boolean hasPermissionToGiveBonus(BonusCategoryName categoryName) {
        BonusCategory bonusCategory = getEntityOrFail(() -> bonusesCategoriesRepository.findByName(categoryName));
        Permission requiredPermission = bonusCategory.getPermission();
        Member currentMember = authenticationService.getCurrentMember();
        Long givenPointsForBonusesOfType = bonusesRepository.getGivenPointsForBonusesOfType(
                currentMember.getId(),
                categoryName,
                LocalDateTime.now().minusDays(bonusCategory.getLimitPeriodInDays()),
                LocalDateTime.now());

        return currentMember.hasAuthority(requiredPermission) && givenPointsForBonusesOfType < bonusCategory.getPointsLimit();
    }

    private Bonus buildBonus(GiveBonusCommand giveBonusCommand) {
        BonusCategoryName categoryName = giveBonusCommand.getCategory();
        BonusCategory bonusCategory = getEntityOrFail(() -> bonusesCategoriesRepository.findByName(categoryName));
        Long giverId = authenticationService.getCurrentMember().getId();
        return Bonus.builder()
                .giverId(giverId)
                .receiverId(giveBonusCommand.getReceiverId())
                .points(giveBonusCommand.getPoints())
                .category(bonusCategory)
                .description(giveBonusCommand.getDescription())
                .build();
    }

}
