package pl.michalmarciniec.loyalty.tasks;

import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository;
import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;
import static pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName.OVERTIME;

@Component
@RequiredArgsConstructor
public class ResetPointsPoolsTask {
    private final BonusesCategoriesRepository bonusesCategoriesRepository;
    private final MembersRepository membersRepository;

    @Value("${loyalty-program.walletGiveAwayPointsPool}")
    private Long walletGiveAwayPointsPool;
    @Value("${loyalty-program.specialPermissionPointsPool}")
    private Long specialPermissionPointsPool;

    @Scheduled(cron = "${tasks.resetPoints.walletCron}")
    @Transactional
    public void resetWalletsPools() {
        membersRepository.findAll().stream()
                .map(Member::getWallet)
                .forEach(wallet -> wallet.resetGiveAwayPool(walletGiveAwayPointsPool));
    }

    @Scheduled(cron = "${tasks.resetPoints.specialPermissionCron}")
    @Transactional
    public void resetSpecialPools() {
        getEntityOrFail(() -> bonusesCategoriesRepository.findByName(OVERTIME)).resetPointsPool(specialPermissionPointsPool);
    }
}
