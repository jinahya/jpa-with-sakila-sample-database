package com.github.jinahya.persistence.sakila;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Category_IT
        extends _BaseEntityIT<Category, Integer> {

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
