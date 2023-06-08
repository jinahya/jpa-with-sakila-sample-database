package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.persistence.sakila.__BaseEntity;
import com.github.jinahya.sakila.data.jpa.repository.__BaseEntityRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Objects;
import java.util.function.Function;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

abstract class __BaseEntityRepositoryServiceTest<
        SERVICE extends __BaseEntityRepositoryService<REPOSITORY, ENTITY, ID>,
        REPOSITORY extends __BaseEntityRepository<ENTITY, ID>,
        ENTITY extends __BaseEntity<ID>,
        ID extends Comparable<? super ID>
        >
        extends ___BaseEntityRepositoryServiceTestBase<SERVICE, REPOSITORY, ENTITY, ID> {

    private static final Logger log = getLogger(lookup().lookupClass());

    __BaseEntityRepositoryServiceTest(final Class<SERVICE> serviceClass) {
        super(serviceClass);
    }

    @Test
    @Override
    void removeMe() {
        super.removeMe();
        log.debug("repositorySpy: {}", repositorySpy);
    }

    <R> R applyRepositorySpy(final Function<? super REPOSITORY, ? extends R> function) {
        return Objects.requireNonNull(function, "function is null")
                .apply(repositorySpy);
    }

    @MockBean
    private REPOSITORY repositorySpy;
}
