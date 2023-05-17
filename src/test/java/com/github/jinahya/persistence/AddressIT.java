package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class AddressIT
        extends _BaseEntityIT<Address, Integer> {

    static Address newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new AddressRandomizer().getRandomValue();
        instance.setCity(CityIT.newPersistedInstance(entityManager));
        entityManager.persist(instance);
        entityManager.flush();
        assertThatBean(instance).isValid();
        return instance;
    }

    AddressIT() {
        super(Address.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(AddressIT::newPersistedInstance);
        assertThat(instance.getAddressId()).isNotNull();
        assertThat(instance.getCity()).isNotNull();
    }
}
