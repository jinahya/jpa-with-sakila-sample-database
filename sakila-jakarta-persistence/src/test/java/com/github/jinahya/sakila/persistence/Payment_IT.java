package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static com.github.jinahya.sakila.persistence.Customer_IT.newPersistedCustomer;
import static com.github.jinahya.sakila.persistence.Inventory_IT.newPersistedInventory;
import static com.github.jinahya.sakila.persistence.Staff_IT.newPersistedStaff;
import static com.github.jinahya.sakila.persistence.Store_IT.newPersistedStore;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;

class Payment_IT
        extends _BaseEntityIT<Payment, Integer> {

    static Payment newPersistedPayment(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Payment_Randomizer().getRandomValue();
        instance.setCustomer(newPersistedCustomer(entityManager, store));
        instance.setStaff(newPersistedStaff(entityManager, store));
        if (current().nextBoolean()) {
            final var rental = new Rental_Randomizer().getRandomValue();
            rental.setInventory(newPersistedInventory(entityManager, store));
            rental.setCustomer(instance.getCustomer());
            rental.setStaff(instance.getStaff());
            entityManager.persist(rental);
        }
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static Payment newPersistedPayment(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var store = newPersistedStore(entityManager);
        return newPersistedPayment(entityManager, store);
    }

    Payment_IT() {
        super(Payment.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Payment_IT::newPersistedPayment);
        assertThat(instance).isNotNull().satisfies(r -> {
            assertThat(r.getPaymentId()).isNotNull();
            assertThatBean(r).isValid();
        });
    }
}
