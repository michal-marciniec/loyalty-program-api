package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.BadgesRepository;
import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.command.CreateBadgeCommand;
import pl.michalmarciniec.loyalty.domain.entity.Badge;
import pl.michalmarciniec.loyalty.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasRole(T(RoleName).ROLE_ADMIN.name())")
public class ManageBadgesService {
    private final BadgesRepository badgesRepository;
    private final MembersRepository membersRepository;

    @Transactional
    public Badge createBadge(CreateBadgeCommand createBadgeCommand) {
        Badge badge = ModelMapper.map(createBadgeCommand, Badge.BadgeBuilder.class).build();
        return badgesRepository.save(badge);
    }

    @Transactional
    public Badge editBadge(Long badgeId, CreateBadgeCommand createBadgeCommand) {
        return getEntityOrFail(() -> badgesRepository.findById(badgeId))
                .edit(createBadgeCommand);
    }

    @Transactional
    public void deleteBadge(Long badgeId) {
        badgesRepository.delete(badgeId);
    }

    @Transactional
    public void assignBadgeToMember(Long badgeId, Long memberId) {
        Badge badge = getEntityOrFail(() -> badgesRepository.findById(badgeId));
        Member member = getEntityOrFail(() -> membersRepository.findById(memberId));
        member.getBadges().add(badge);
    }

    @Transactional
    public void removeBadgeFromMember(Long badgeId, Long memberId) {
        Badge badge = getEntityOrFail(() -> badgesRepository.findById(badgeId));
        Member member = getEntityOrFail(() -> membersRepository.findById(memberId));
        member.getBadges().remove(badge);
    }
}
