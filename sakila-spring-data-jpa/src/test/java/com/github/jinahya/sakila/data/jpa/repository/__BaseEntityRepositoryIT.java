package com.github.jinahya.sakila.data.jpa.repository;

import com.github.jinahya.persistence.sakila.__BaseEntity;
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
import static org.assertj.core.api.Assertions.assertThat;
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
abstract class __BaseEntityRepositoryIT<
        REPOSITORY extends __BaseEntityRepository<ENTITY, ID>,
        ENTITY extends __BaseEntity<ID>,
        ID extends Comparable<? super ID>> {

    private static final Logger log = getLogger(lookup().lookupClass());

    @SuppressWarnings({"unchecked"})
    __BaseEntityRepositoryIT(final Class<REPOSITORY> repositoryClass) {
        super();
        this.repositoryClass = Objects.requireNonNull(repositoryClass, "repositoryClass is null");
        final var resolvableType = ResolvableType.forClass(this.repositoryClass).as(__BaseEntityRepository.class);
        entityClass = (Class<ENTITY>) resolvableType.getGeneric(0).resolve();
        idClass = (Class<ID>) resolvableType.getGeneric(1).resolve();
    }

    <R> R applyRepositoryInstance(final Function<? super REPOSITORY, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(repositoryInstance);
    }

    @Test
    void __removeMe() {
        log.debug("repositoryInstance: {}", repositoryInstance);
        assertThat(repositoryInstance).isNotNull();
    }

    final Class<REPOSITORY> repositoryClass;

    final Class<ENTITY> entityClass;

    final Class<ID> idClass;

    @Autowired
    private REPOSITORY repositoryInstance;
}
