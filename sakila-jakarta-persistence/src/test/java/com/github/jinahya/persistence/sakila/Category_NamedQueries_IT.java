package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Category_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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

    @DisplayName("Category_findAllByCategoryIdGreaterThan")
    @Nested
    class SelectCategoryIdGreaterThanTest {

        @Test
        void __() {
            final int maxResults = ThreadLocalRandom.current().nextInt(4, 8);
            for (var categoryIdMinExclusive = new AtomicInteger(); ; ) {
                final var list = applyEntityManager(
                        em -> em.createNamedQuery("Category_findAllByCategoryIdGreaterThan", Category.class)
                                .setParameter("categoryIdMinExclusive", categoryIdMinExclusive.get())
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                log.debug("categoryIds: {}", list.stream().map(Category::getCategoryId).toList());
                assertThat(list)
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(Comparator.comparing(Category::getCategoryId));
                if (list.isEmpty()) {
                    break;
                }
                categoryIdMinExclusive.set(list.get(list.size() - 1).getCategoryId());
            }
        }
    }
}
