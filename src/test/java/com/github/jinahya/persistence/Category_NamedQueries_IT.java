package com.github.jinahya.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Category_NamedQueries_IT
        extends __BaseEntityIT<Category, Integer> {

    Category_NamedQueries_IT() {
        super(Category.class, Integer.class);
    }

    @DisplayName("Category_findAll")
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final int maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("Category_findAll", Category.class)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(found)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @DisplayName("Category_findByCategoryId")
    @Nested
    class FindByCategoryIdTest {

        @Test
        void findByCategoryId__0() {
            final var categoryId = 0;
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery("Category_findByCategoryId", Category.class)
                                    .setParameter("categoryId", categoryId)
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @Test
        void findByCategoryId__1() {
            final var categoryId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery("Category_findByCategoryId", Category.class)
                            .setParameter("categoryId", categoryId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Category::getCategoryId)
                    .isEqualTo(categoryId);
        }
    }
}
