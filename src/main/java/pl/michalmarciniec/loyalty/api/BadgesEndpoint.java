package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.BadgesRepository;
import pl.michalmarciniec.loyalty.domain.command.CreateBadgeCommand;
import pl.michalmarciniec.loyalty.domain.dto.BadgeDto;
import pl.michalmarciniec.loyalty.domain.entity.Badge;
import pl.michalmarciniec.loyalty.domain.service.ManageBadgesService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static pl.michalmarciniec.loyalty.db.JpaRepositoryWrapper.getEntityOrFail;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@LoyaltyProgramApi
@RequestMapping(path = "/api/badges", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BadgesEndpoint {
    private final BadgesRepository badgesRepository;
    private final ManageBadgesService manageBadgesService;

    @GetMapping
    public List<BadgeDto> getAllBadges() {
        return badgesRepository.findAll()
                .stream()
                .map(badge -> ModelMapper.map(badge, BadgeDto.BadgeDtoBuilder.class).build())
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{badgeId}")
    public BadgeDto getSingleBadge(@PathVariable Long badgeId) {
        Badge badge = getEntityOrFail(() -> badgesRepository.findById(badgeId));
        return ModelMapper.map(badge, BadgeDto.BadgeDtoBuilder.class).build();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public BadgeDto createBadge(@Validated @RequestBody CreateBadgeCommand createBadgeCommand) {
        Badge badge = manageBadgesService.createBadge(createBadgeCommand);
        return ModelMapper.map(badge, BadgeDto.BadgeDtoBuilder.class).build();
    }

    @PutMapping(path = "/{badgeId}", consumes = APPLICATION_JSON_VALUE)
    public BadgeDto editBadge(@PathVariable Long badgeId, @Validated @RequestBody CreateBadgeCommand createBadgeCommand) {
        Badge badge = manageBadgesService.editBadge(badgeId, createBadgeCommand);
        return ModelMapper.map(badge, BadgeDto.BadgeDtoBuilder.class).build();
    }

    @DeleteMapping(path = "/{badgeId}")
    public void deleteBadge(@PathVariable Long badgeId) {
        manageBadgesService.deleteBadge(badgeId);
    }

    @PostMapping(path = "/{badgeId}/members/{memberId}")
    public void assignBadgeToMember(@PathVariable Long badgeId, @PathVariable Long memberId) {
        manageBadgesService.assignBadgeToMember(badgeId, memberId);
    }

    @DeleteMapping(path = "/{badgeId}/members/{memberId}")
    public void removeBadgeFromMember(@PathVariable Long badgeId, @PathVariable Long memberId) {
        manageBadgesService.removeBadgeFromMember(badgeId, memberId);
    }

}
