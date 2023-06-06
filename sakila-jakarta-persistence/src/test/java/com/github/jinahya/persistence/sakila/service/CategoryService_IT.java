package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;

class CategoryService_IT
        extends _BaseEntityServiceIT<CategoryService, Category, Integer> {

    CategoryService_IT() {
        super(CategoryService.class, Category.class, Integer.class);
    }

    @DisplayName("findByCategoryId(categoryId)")
    @Nested
    class FindByCategoryIdTest {

        @DisplayName("(1)!empty")
        @Test
        void _NotEmpty_1() {
            final var categoryId = 1;
            final var found = applyServiceInstance(s -> s.findByCategoryIdId(categoryId));
            assertThat(found).hasValueSatisfying(v -> {
                assertThat(v.getCategoryId()).isEqualTo(categoryId);
            });
        }
    }

    @DisplayName("findAll(categoryIdMinExclusive, maxResults)")
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var maxResults = current().nextInt(16, 32);
            for (final var i = new AtomicInteger(0); ; ) {
                final var categoryIdMinExclusive = i.get();
                final var list = applyServiceInstance(s -> s.findAll(categoryIdMinExclusive, maxResults));
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .extracting(Category::getCategoryId)
                        .allMatch(v -> v > categoryIdMinExclusive)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getCategoryId());
            }
        }
    }

    @DisplayName("locateByName(name)")
    @Nested
    class LocateByNameTest {

        @DisplayName("('Action')!null")
        @Test
        void _NotNull_Action() {
            final var name = "Action";
            final var located = applyServiceInstance(s -> s.locateByName(name));
            assertThat(located)
                    .isNotNull()
                    .extracting(Category::getName)
                    .isEqualTo(name);
        }

        @DisplayName("('Unknown')!null")
        @Test
        void _NotNull_Unknown() {
            final var name = "Unknown";
            final var located = applyServiceInstance(s -> s.locateByName(name));
            assertThat(located)
                    .isNotNull()
                    .extracting(Category::getName)
                    .isEqualTo(name);
        }
    }
}
