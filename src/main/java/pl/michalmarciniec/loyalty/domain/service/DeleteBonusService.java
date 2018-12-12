package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import pl.michalmarciniec.loyalty.domain.entity.Wallet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteBonusService {
    private final BonusesRepository bonusesRepository;
    private final MembersRepository membersRepository;

    @Transactional
    @PreAuthorize("@editBonusService.hasPermissionToEditBonus(#bonusId)")
    public void deleteBonus(Long bonusId) {
        Bonus bonus = getEntityOrFail(() -> bonusesRepository.findById(bonusId));
        Wallet receiverWallet = getEntityOrFail(() -> membersRepository.findById(bonus.getReceiverId())).getWallet();
        Wallet giverWallet = getEntityOrFail(() -> membersRepository.findById(bonus.getGiverId())).getWallet();
        giverWallet.cancelBonus(bonus, receiverWallet);
        bonusesRepository.delete(bonus);
        log.debug("Bonus {} deleted", bonus);
    }

}
