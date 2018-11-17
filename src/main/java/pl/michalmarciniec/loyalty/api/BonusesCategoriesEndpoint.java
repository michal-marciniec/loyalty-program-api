package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategory;
import pl.michalmarciniec.loyalty.domain.entity.BonusCategoryName;
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

    @GetMapping
    public ResponseEntity<List<BonusCategoryName>> getAll() {
        return ResponseEntity.ok(bonusesCategoriesRepository.findAll().stream()
                .map(BonusCategory::getName)
                .collect(Collectors.toList()));
    }

}
