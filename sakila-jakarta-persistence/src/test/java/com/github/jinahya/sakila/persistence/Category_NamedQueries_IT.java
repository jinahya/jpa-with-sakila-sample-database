package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

class Category_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

    @DisplayName(CategoryConstants.QUERY_FIND_BY_CATEGORY_ID)
    @Nested
    class FindByCategoryIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void __0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(CategoryConstants.QUERY_FIND_BY_CATEGORY_ID, Category.class)
                                    .setParameter(CategoryConstants.PARAMETER_CATEGORY_ID, 0)
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void __1() {
            final var categoryId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(CategoryConstants.QUERY_FIND_BY_CATEGORY_ID, Category.class)
                            .setParameter(CategoryConstants.PARAMETER_CATEGORY_ID, categoryId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Category::getCategoryId)
                    .isEqualTo(categoryId);
        }
    }

    @DisplayName(CategoryConstants.QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final int maxResults = current().nextInt(8, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var categoryIdMinExclusive = i.get();
                final var found = applyEntityManager(
                        em -> em.createNamedQuery(CategoryConstants.QUERY_FIND_ALL, Category.class)
                                .setParameter(CategoryConstants.PARAMETER_CATEGORY_ID_MIN_EXCLUSIVE, categoryIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(found)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .extracting(Category::getCategoryId)
                        .allMatch(v -> v > categoryIdMinExclusive)
                        .isSorted();
                if (found.isEmpty()) {
                    break;
                }
                i.set(found.get(found.size() - 1).getCategoryId());
            }
        }
    }
}
