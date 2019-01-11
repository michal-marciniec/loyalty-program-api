package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.BadgesRepository;
import pl.michalmarciniec.loyalty.domain.command.CreateBadgeCommand;
import pl.michalmarciniec.loyalty.domain.entity.Badge;
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

    @Transactional
    public Badge createBadge(CreateBadgeCommand createBadgeCommand) {
        Badge badge = ModelMapper.map(createBadgeCommand, Badge.BadgeBuilder.class).build();
        return badgesRepository.save(badge);
    }

    @Transactional
    public Badge editBadge(Long badgeId, CreateBadgeCommand createBadgeCommand) {
        Badge existingBadge = getEntityOrFail(() -> badgesRepository.findById(badgeId))
                .edit(createBadgeCommand);
        return badgesRepository.save(existingBadge);
    }

}
