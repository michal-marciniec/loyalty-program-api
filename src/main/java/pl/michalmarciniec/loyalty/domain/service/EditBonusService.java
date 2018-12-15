package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.command.EditBonusCommand;
import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import pl.michalmarciniec.loyalty.domain.entity.Wallet;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Service("editBonusService")
@Slf4j
@RequiredArgsConstructor
public class EditBonusService {
    private final BonusesRepository bonusesRepository;
    private final AuthenticationService authenticationService;
    private final MembersRepository membersRepository;

    @Transactional
    @PreAuthorize("@editBonusService.hasPermissionToEditBonus(#editBonusCommand.getId())")
    public Bonus editBonus(EditBonusCommand editBonusCommand) {
        Bonus bonus = getEntityOrFail(() -> bonusesRepository.findById(editBonusCommand.getId()));
        log.debug("Editing bonus: {}", bonus);
        balanceWallets(bonus, editBonusCommand.getPoints());
        bonus.changeDescriptionAndPoints(editBonusCommand.getDescription(), editBonusCommand.getPoints());
        log.debug("Bonus {} changed", bonus);
        return bonus;
    }

    public boolean hasPermissionToEditBonus(Long bonusId) {
        Bonus bonus = getEntityOrFail(() -> bonusesRepository.findById(bonusId));
        return isCurrentMemberTheGiver(bonus) && editPeriodNotExceeded(bonus);
    }

    private void balanceWallets(Bonus bonus, Long points) {
        Wallet giverWallet = authenticationService.getCurrentMember().getWallet();
        Wallet receiverWallet = getEntityOrFail(() -> membersRepository.findById(bonus.getReceiverId())).getWallet();
        giverWallet.cancelBonus(bonus, receiverWallet);
        giverWallet.transferPoints(receiverWallet, points);
    }

    private boolean isCurrentMemberTheGiver(Bonus bonus) {
        return authenticationService.getCurrentMember().getId().equals(bonus.getGiverId());
    }

    private boolean editPeriodNotExceeded(Bonus bonus) {
        Long editPeriodInHours = bonus.getCategory().getEditPeriodInHours();
        return LocalDateTime.now().isBefore(bonus.getCreatedAt().plusHours(editPeriodInHours));
    }

}
