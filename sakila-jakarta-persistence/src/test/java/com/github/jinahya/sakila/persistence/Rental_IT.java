package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static com.github.jinahya.sakila.persistence.Customer_IT.newPersistedCustomer;
import static com.github.jinahya.sakila.persistence.Inventory_IT.newPersistedInventory;
import static com.github.jinahya.sakila.persistence.Staff_IT.newPersistedStaff;
import static com.github.jinahya.sakila.persistence.Store_IT.newPersistedStore;
import static org.assertj.core.api.Assertions.assertThat;

class Rental_IT
        extends _BaseEntityIT<Rental, Integer> {

    static Rental newPersistedRental(final EntityManager entityManager, final Customer customer) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(customer, "customer is null");
        final var instance = new Rental_Randomizer().getRandomValue();
        final var store = customer.getStore();
        instance.setInventory(newPersistedInventory(entityManager, store));
        instance.setCustomer(customer);
        instance.setStaff(newPersistedStaff(entityManager, store));
        assertThatBean(instance).isValid();
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static Rental newPersistedRental(final EntityManager entityManager, final Store store) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(store, "store is null");
        final var instance = new Rental_Randomizer().getRandomValue();
        instance.setInventory(newPersistedInventory(entityManager, store));
        instance.setCustomer(newPersistedCustomer(entityManager, store));
        instance.setStaff(newPersistedStaff(entityManager, store));
        assertThatBean(instance).isValid();
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static Rental newPersistedRental(final EntityManager entityManager) {
        return newPersistedRental(entityManager, newPersistedStore(entityManager));
    }

    Rental_IT() {
        super(Rental.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Rental_IT::newPersistedRental);
        assertThat(instance).isNotNull();
        assertThatBean(instance).isValid();
    }

    @Test
    void merge__() {
        final var persisted = applyEntityManager(Rental_IT::newPersistedRental);
        persisted.setReturnDate(LocalDateTime.now());
        final var merged = applyEntityManager(em -> em.merge(persisted));
    }
}
