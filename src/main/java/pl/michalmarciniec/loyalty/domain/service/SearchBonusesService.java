package pl.michalmarciniec.loyalty.domain.service;

import pl.michalmarciniec.loyalty.db.BonusesRepository;
import pl.michalmarciniec.loyalty.domain.command.SearchBonusesCommand;
import pl.michalmarciniec.loyalty.domain.entity.Bonus;
import pl.michalmarciniec.loyalty.domain.entity.QBonus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;

@Service
@RequiredArgsConstructor
public class SearchBonusesService {
    private final BonusesRepository bonusesRepository;

    public List<Bonus> getBonuses(SearchBonusesCommand command) {
        QBonus bonus = QBonus.bonus;
        LocalDateTime startDate = command.getStartDate().atStartOfDay();
        LocalDateTime endDate = command.getStartDate().atTime(LocalTime.MAX);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        addCondition(startDate, bonus.createdAt::after, booleanBuilder);
        addCondition(endDate, bonus.createdAt::before, booleanBuilder);
        addCondition(command.getMemberId(), bonus.receiverId::eq, booleanBuilder);

        return newArrayList(bonusesRepository.findAll(booleanBuilder.getValue()));
    }

    private <T> void addCondition(T value, Function<T, BooleanExpression> condition, BooleanBuilder booleanBuilder) {
        if (value != null) {
            booleanBuilder.and(condition.apply(value));
        }
    }
}
