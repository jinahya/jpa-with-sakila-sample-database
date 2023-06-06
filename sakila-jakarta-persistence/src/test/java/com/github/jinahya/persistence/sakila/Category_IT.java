package com.github.jinahya.persistence.sakila;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

class Category_IT
        extends _BaseEntityIT<Category, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    static Category newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new Category_Randomizer().getRandomValue();
        assertThatBean(instance).isValid();
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    Category_IT() {
        super(Category.class, Integer.class);
    }

    @Test
    void __() {
        final var instance = applyEntityManager(Category_IT::newPersistedInstance);
        assertThat(instance.getCategoryId()).isNotNull();
        assertThatBean(instance).isValid();
    }
}
