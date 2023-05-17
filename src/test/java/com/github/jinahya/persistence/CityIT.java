package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CityIT
        extends _BaseEntityIT<City, Integer> {

    static City newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new CityRandomizer().getRandomValue();
        instance.setCountry(CountryIT.newPersistedInstance(entityManager));
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    CityIT() {
        super(City.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(CityIT::newPersistedInstance);
        assertThat(instance)
                .isNotNull()
                .extracting(City::getCity)
                .isNotNull();
    }
}
