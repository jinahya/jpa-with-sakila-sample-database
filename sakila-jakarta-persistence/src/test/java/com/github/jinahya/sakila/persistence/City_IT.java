package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static com.github.jinahya.sakila.persistence.Country_IT.newPersistedCountry;
import static org.assertj.core.api.Assertions.assertThat;

class City_IT
        extends _BaseEntityIT<City, Integer> {

    static City newPersistedCity(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new City_Randomizer().getRandomValue();
        instance.setCountry(newPersistedCountry(entityManager));
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    City_IT() {
        super(City.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(City_IT::newPersistedCity);
        assertThat(instance).isNotNull();
        assertThatBean(instance).isValid();
    }

    @DisplayName("find(0)null")
    @Test
    void _Null_0() {
        final var found = applyEntityManager(em -> em.find(City.class, 0));
        assertThat(found).isNull();
    }

    @DisplayName("find(1)!null")
    @Test
    void _NotNull_1() {
        final var found = applyEntityManager(em -> em.find(City.class, 1));
        assertThat(found).isNotNull();
        assertThatBean(found).isValid();
    }
}
