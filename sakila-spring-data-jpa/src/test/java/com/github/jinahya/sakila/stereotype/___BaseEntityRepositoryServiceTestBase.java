package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.persistence.sakila.__BaseEntity;
import com.github.jinahya.sakila.data.jpa.repository.__BaseEntityRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ResolvableType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Objects;
import java.util.function.Function;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

//@EntityScan(
//        basePackageClasses = {
//                com.github.jinahya.persistence.sakila._NoOp.class
//        }
//)
//@EnableJpaRepositories(
//        basePackageClasses = {
//                com.github.jinahya.sakila.data.jpa.repository._NoOp.class
//        }
//)
@SpringBootTest
abstract class ___BaseEntityRepositoryServiceTestBase<
        SERVICE extends __BaseEntityRepositoryService<REPOSITORY, ENTITY, ID>,
        REPOSITORY extends __BaseEntityRepository<ENTITY, ID>,
        ENTITY extends __BaseEntity<ID>,
        ID extends Comparable<? super ID>
        > {

    private static final Logger log = getLogger(lookup().lookupClass());

    @SuppressWarnings({"unchecked"})
    ___BaseEntityRepositoryServiceTestBase(final Class<SERVICE> serviceClass) {
        super();
        this.serviceClass = Objects.requireNonNull(serviceClass, "serviceClass is null");
        final var resolvableType = ResolvableType.forClass(this.serviceClass).as(__BaseEntityRepositoryService.class);
        repositoryClass = (Class<REPOSITORY>) resolvableType.getGeneric(0).resolve();
        entityClass = (Class<ENTITY>) resolvableType.getGeneric(1).resolve();
        idClass = (Class<ID>) resolvableType.getGeneric(2).resolve();
    }

    @Test
    void removeMe() {
        log.debug("serviceInstance: {}", serviceInstance);
    }

    <R> R applyServiceInstance(final Function<? super SERVICE, ? extends R> function) {
        return Objects.requireNonNull(function, "function is null")
                .apply(serviceInstance);
    }

    final Class<SERVICE> serviceClass;

    final Class<REPOSITORY> repositoryClass;

    final Class<ENTITY> entityClass;

    final Class<ID> idClass;

    @Autowired
    private SERVICE serviceInstance;
}
