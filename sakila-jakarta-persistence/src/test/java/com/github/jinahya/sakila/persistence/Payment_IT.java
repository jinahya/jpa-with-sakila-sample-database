package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;

class Payment_IT
        extends _BaseEntityIT<Payment, Integer> {

    static Payment newPersistedInstance(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Payment_Randomizer().getRandomValue();
        instance.setCustomer(Customer_IT.newPersistedInstance(entityManager, store));
        instance.setStaff(Staff_IT.newPersistedInstance(entityManager, store));
        if (current().nextBoolean()) {
            final var rental = new Rental_Randomizer().getRandomValue();
            rental.setInventory(Inventory_IT.newPersistedInstance(entityManager, store));
            rental.setCustomer(instance.getCustomer());
            rental.setStaff(instance.getStaff());
            entityManager.persist(rental);
        }
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static Payment newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var store = Store_IT.newPersistedInstance(entityManager);
        return newPersistedInstance(entityManager, store);
    }

    Payment_IT() {
        super(Payment.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Payment_IT::newPersistedInstance);
        assertThat(instance).isNotNull().satisfies(r -> {
            assertThat(r.getPaymentId()).isNotNull();
            assertThatBean(r).isValid();
        });
    }
}
