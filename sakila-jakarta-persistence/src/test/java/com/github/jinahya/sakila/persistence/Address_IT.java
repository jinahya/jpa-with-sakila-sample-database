package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

class Address_IT
        extends _BaseEntityIT<Address, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    static Address newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new Address_Randomizer().getRandomValue();
        instance.setCity(City_IT.newPersistedInstance(entityManager));
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    Address_IT() {
        super(Address.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Address_IT::newPersistedInstance);
        assertThat(instance).isNotNull();
        assertThatBean(instance).isValid();
    }

    @DisplayName("find(0)null")
    @Test
    void _Null_0() {
        final var found = applyEntityManager(em -> em.find(Address.class, 0));
        assertThat(found).isNull();
    }

    @DisplayName("find(1)!null")
    @Test
    void _NotNull_1() {
        final var found = applyEntityManager(em -> em.find(Address.class, 1));
        assertThat(found).isNotNull();
        assertThatBean(found).isValid();
    }
}
