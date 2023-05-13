package com.github.jinahya.persistence;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class Category_FindById_IT
        extends _BaseEntityIT<Category, Integer> {

    Category_FindById_IT() {
        super(Category.class, Integer.class);
    }

    @DisplayName("findByCategoryId(0) -> NoResultException")
    @Test
    void findByCategoryId_NotNull_0() {
        assertThatThrownBy(
                () -> {
                    applyEntityManager(em -> {
                        final var query = em.createNamedQuery("Category_findByCategoryId", Category.class);
                        query.setParameter(Category_.CATEGORY_ID, 0);
                        return query.getSingleResult(); // NoResultException
                    });
                }
        ).isInstanceOf(NoResultException.class);
    }

    @DisplayName("findByCategoryId(1)")
    @Test
    void findByCategoryId_NotNull_1() {
        // GIVEN
        final var categoryId = 1;
        // WHEN
        final Category found = applyEntityManager(em -> {
            final var query = em.createNamedQuery("Category_findByCategoryId", Category.class);
            query.setParameter(Category_.CATEGORY_ID, categoryId);
            return query.getSingleResult();
        });
        // THEN
        assertThat(found)
                .isNotNull()
                .extracting(Category::getCategoryId)
                .isEqualTo(categoryId);
    }

    @DisplayName("findByCategoryId(2)")
    @Test
    void findByCategoryId_NotNull_2() {
        // GIVEN
        final var categoryId = 2;
        // TODO: implement!
        // WHEN
        // THEN
    }
}
