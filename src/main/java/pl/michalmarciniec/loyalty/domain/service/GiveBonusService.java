package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository;
import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import pl.michalmarciniec.loyalty.domain.entity.*;
import pl.michalmarciniec.loyalty.domain.entity.Bonus.BonusBuilder;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Service("giveBonusService")
@Slf4j
@RequiredArgsConstructor
public class GiveBonusService {

    private final BonusesRepository bonusesRepository;
    private final AuthenticationService authenticationService;
    private final BonusesCategoriesRepository bonusesCategoriesRepository;
    private final GiveBonusStrategies giveBonusStrategies;

    @Transactional
    @PreAuthorize("@giveBonusService.hasPermissionToGiveBonus(#giveBonusCommand.getCategory())")
    public Bonus giveBonus(GiveBonusCommand giveBonusCommand) {
        log.debug("Attempting to give bonus: {}", giveBonusCommand);
        giveBonusStrategies.giveBonus(giveBonusCommand);
        Bonus savedBonus = bonusesRepository.save(buildBonus(giveBonusCommand));
        log.debug("Bonus {} given", savedBonus);
        return savedBonus;
    }

    public boolean hasPermissionToGiveBonus(BonusCategoryName categoryName) {
        BonusCategory bonusCategory = getEntityOrFail(() -> bonusesCategoriesRepository.findByName(categoryName));
        Permission requiredPermission = bonusCategory.getPermission();
        Member currentMember = authenticationService.getCurrentMember();
        return currentMember.hasAuthority(requiredPermission);
    }

    private Bonus buildBonus(GiveBonusCommand giveBonusCommand) {
        BonusCategoryName categoryName = giveBonusCommand.getCategory();
        BonusCategory bonusCategory = getEntityOrFail(() -> bonusesCategoriesRepository.findByName(categoryName));
        Long giverId = authenticationService.getCurrentMember().getId();
        return ModelMapper.map(giveBonusCommand, BonusBuilder.class)
                .giverId(giverId)
                .category(bonusCategory)
                .build();
    }

}
