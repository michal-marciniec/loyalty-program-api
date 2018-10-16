package pl.michalmarciniec.loyalty.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Supplier;

@NoRepositoryBean
public interface JpaRepositoryWrapper<T, ID extends Serializable> extends JpaRepository<T, ID> {
    Optional<T> findById(ID id);

    static <E> E getEntityOrFail(Supplier<Optional<E>> find) {
        return find.get().orElseThrow(EntityNotFoundException::new);
    }
}
