package pl.michalmarciniec.loyalty.db;

import com.querydsl.core.BooleanBuilder;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;

public class SearchQuery<E> {
    private final JpaRepositoryWrapper<E> repository;
    private final BooleanBuilder booleanBuilder = new BooleanBuilder();

    public SearchQuery(JpaRepositoryWrapper<E> repository) {
        this.repository = repository;
    }

    public SearchQuery<E> addPredicate(Function<BooleanBuilder, BooleanBuilder> operation) {
        operation.apply(booleanBuilder);
        return this;
    }

    public <T> SearchQuery<E> addPredicate(T value, BiFunction<BooleanBuilder, T, BooleanBuilder> operation) {
        if (value != null) {
            operation.apply(booleanBuilder, value);
        }
        return this;
    }

    public List<E> find() {
        return newArrayList(repository.findAll(booleanBuilder.getValue()));
    }

}
