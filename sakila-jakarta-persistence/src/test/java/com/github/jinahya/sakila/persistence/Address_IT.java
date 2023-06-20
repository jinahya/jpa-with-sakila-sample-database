package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static com.github.jinahya.sakila.persistence.City_IT.newPersistedCity;
import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

class Address_IT
        extends _BaseEntityIT<Address, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    static Address newPersistedAddress(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new Address_Randomizer().getRandomValue();
        instance.setCity(newPersistedCity(entityManager));
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    Address_IT() {
        super(Address.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Address_IT::newPersistedAddress);
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
