package pl.michalmarciniec.loyalty.db;

import com.querydsl.core.BooleanBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;

public class SearchQuery<E, ID extends Serializable> {
    private final JpaRepositoryWrapper<E, ID> repository;
    private final BooleanBuilder booleanBuilder = new BooleanBuilder();

    public SearchQuery(JpaRepositoryWrapper<E, ID> repository) {
        this.repository = repository;
    }

    public SearchQuery<E, ID> addPredicate(Function<BooleanBuilder, BooleanBuilder> operation) {
        operation.apply(booleanBuilder);
        return this;
    }

    public <T> SearchQuery<E, ID> addPredicate(T value, BiFunction<BooleanBuilder, T, BooleanBuilder> operation) {
        if (value != null) {
            operation.apply(booleanBuilder, value);
        }
        return this;
    }

    public List<E> find() {
        return newArrayList(repository.findAll(booleanBuilder.getValue()));
    }

}
