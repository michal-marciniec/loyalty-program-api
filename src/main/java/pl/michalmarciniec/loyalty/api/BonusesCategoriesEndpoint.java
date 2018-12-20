package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.common.ModelMapper;
import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository;
import pl.michalmarciniec.loyalty.domain.dto.BonusCategoryDto;
import pl.michalmarciniec.loyalty.domain.entity.Permission;
import pl.michalmarciniec.loyalty.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@LoyaltyProgramApi
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class BonusesCategoriesEndpoint {
    private final BonusesCategoriesRepository bonusesCategoriesRepository;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<List<BonusCategoryDto>> getAllCategoriesNames() {
        return ResponseEntity.ok(bonusesCategoriesRepository.findAll().stream()
                .map(bonusCategory -> ModelMapper.map(bonusCategory, BonusCategoryDto.BonusCategoryDtoBuilder.class).build())
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<BonusCategoryDto>> getCategoriesAvailableForCurrentUser() {
        List<Permission> currentMemberPermissions = authenticationService.getCurrentMember().getPermissions().stream()
                .map(grantedAuthority -> (Permission) grantedAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bonusesCategoriesRepository.findByPermissionIn(currentMemberPermissions)
                .stream()
                .map(bonusCategory -> ModelMapper.map(bonusCategory, BonusCategoryDto.BonusCategoryDtoBuilder.class).build())
                .collect(Collectors.toList()));
    }

}
