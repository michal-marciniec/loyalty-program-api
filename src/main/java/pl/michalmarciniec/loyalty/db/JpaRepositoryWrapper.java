package pl.michalmarciniec.loyalty.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.function.Supplier;

@NoRepositoryBean
public interface JpaRepositoryWrapper<T> extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {
    Optional<T> findById(Long id);

    static <E> E getEntityOrFail(Supplier<Optional<E>> find) {
        return find.get().orElseThrow(EntityNotFoundException::new);
    }
}
