package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CategoryIT
        extends _BaseEntityIT<Category, Integer> {

    CategoryIT() {
        super(Category.class, Integer.class);
    }

    @Nested
    class NamedQueryTest {

        @Test
        void findAll__() {
            acceptEntityManager(em -> {
                final var query = em.createNamedQuery("Category_findAll", Category.class);
                final var list = query.getResultList();
                assertThat(list).isNotEmpty().doesNotContainNull().allSatisfy(e -> {
                    log.debug("category: {}", e);
                    assertThat(e).isNotNull();
                });
            });
        }

        @Test
        void findByName__() {
            acceptEntityManager(em -> {
                final var query = em.createNamedQuery("Category_findByName", Category.class);
                query.setParameter("name", "Action");
                final var list = query.getResultList();
                assertThat(list).isNotEmpty().doesNotContainNull().allSatisfy(e -> {
                    log.debug("category: {}", e);
                    assertThat(e).isNotNull();
                });
            });
        }
    }
}
