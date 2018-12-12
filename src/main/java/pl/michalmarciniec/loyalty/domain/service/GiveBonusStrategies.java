package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository;
import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.command.GiveBonusCommand;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategory;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName;
import pl.michalmarciniec.loyalty.domain.entity.Wallet;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;
import static pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName.NO_LIMIT;
import static pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName.OVERTIME;

@Component
@RequiredArgsConstructor
public class GiveBonusStrategies {
    private Map<BonusCategoryName, Consumer<GiveBonusCommand>> giveBonusStrategies = ImmutableMap.of(
            OVERTIME, this::specialPermissionStrategy,
            NO_LIMIT, this::noLimitStrategy
    );

    private final MembersRepository membersRepository;
    private final BonusesCategoriesRepository bonusesCategoriesRepository;
    private final AuthenticationService authenticationService;


    public Consumer<GiveBonusCommand> getStrategy(BonusCategoryName categoryName) {
        return giveBonusStrategies.get(categoryName);
    }

    private void specialPermissionStrategy(GiveBonusCommand giveBonusCommand) {
        BonusCategory bonusCategory = getEntityOrFail(() -> bonusesCategoriesRepository.findByName(giveBonusCommand.getCategory()));
        Wallet receiverWallet = getEntityOrFail(() -> membersRepository.findById(giveBonusCommand.getReceiverId())).getWallet();
        bonusCategory.transferPoints(receiverWallet, giveBonusCommand.getPoints());
    }

    private void noLimitStrategy(GiveBonusCommand giveBonusCommand) {
        Wallet giverWallet = authenticationService.getCurrentMember().getWallet();
        Wallet receiverWallet = getEntityOrFail(() -> membersRepository.findById(giveBonusCommand.getReceiverId())).getWallet();
        giverWallet.transferPoints(receiverWallet, giveBonusCommand.getPoints());
    }

}
