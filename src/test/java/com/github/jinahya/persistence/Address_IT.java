package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Address_IT
        extends _BaseEntityIT<Address, Integer> {

    static Address newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new Address_Randomizer().getRandomValue();
        instance.setCity(CityIT.newPersistedInstance(entityManager));
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
        assertThat(instance).isNotNull().satisfies(a -> {
            assertThatBean(a).isValid();
            assertThat(a.getAddressId()).isNotNull();
            assertThatBean(a.getCity()).isNotNull().isValid();
            assertThatBean(a.getCity().getCountry()).isNotNull().isValid();
        });
    }
}
