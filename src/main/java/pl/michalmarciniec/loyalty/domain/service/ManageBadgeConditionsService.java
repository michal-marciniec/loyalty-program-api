package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository;
import pl.michalmarciniec.loyalty.db.BadgeConditionsRepository;
import pl.michalmarciniec.loyalty.db.BadgesRepository;
import pl.michalmarciniec.loyalty.domain.command.CreateBadgeConditionCommand;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategory;
import pl.michalmarciniec.loyalty.domain.entity.Badge;
import pl.michalmarciniec.loyalty.domain.entity.BadgeCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasRole(T(RoleName).ROLE_ADMIN.name())")
public class ManageBadgeConditionsService {
    private final BadgesRepository badgesRepository;
    private final BadgeConditionsRepository badgeConditionsRepository;
    private final BonusesCategoriesRepository bonusesCategoriesRepository;

    public BadgeCondition createBadgeCondition(CreateBadgeConditionCommand createCommand) {
        Badge badge = getEntityOrFail(() -> badgesRepository.findById(createCommand.getBadgeId()));
        BonusCategory bonusCategory = bonusesCategoriesRepository.findByName(createCommand.getCategoryName())
                .orElse(null);

        BadgeCondition badgeCondition = BadgeCondition.builder()
                .badge(badge)
                .bonusCategory(bonusCategory)
                .conditionType(createCommand.getConditionType())
                .build();

        return badgeConditionsRepository.save(badgeCondition);
    }

    public List<BadgeCondition> getAllBadgeConditions() {
        return badgeConditionsRepository.findAll();
    }

    public void deleteBadgeCondition(Long badgeConditionId) {
        badgeConditionsRepository.delete(badgeConditionId);
    }
}
