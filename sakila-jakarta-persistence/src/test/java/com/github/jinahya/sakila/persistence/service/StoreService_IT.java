package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.Store;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A class for integration-testing {@link StoreService} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class StoreService_IT
        extends __BaseEntityServiceIT<StoreService, Store, Integer> {

    StoreService_IT() {
        super(StoreService.class, Store.class, Integer.class);
    }

    @DisplayName("findByStoreId")
    @Nested
    class FindByStoreIdTest {

        @DisplayName("(0)empty")
        @Test
        void _Empty_0() {
            assertThatThrownBy(
                    () -> applyServiceInstance(s -> s.findByStoreId(0))
            ).isInstanceOf(ConstraintViolationException.class);
        }

        @DisplayName("(1)!empty")
        @Test
        void _NotEmpty_1() {
            final var storeId = 1;
            final var found = applyServiceInstance(s -> s.findByStoreId(storeId));
            assertThat(found)
                    .hasValueSatisfying(v -> {
                        assertThat(v.getStoreId()).isEqualTo(storeId);
                    });
        }
    }

    @DisplayName("findAll")
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var maxResults = ThreadLocalRandom.current().nextInt(2, 4);
            for (final var i = new AtomicInteger(0); ; ) {
                final var storeIdMinExclusive = i.get();
                final var list = applyServiceInstance(
                        s -> s.findAll(storeIdMinExclusive, maxResults)
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .extracting(Store::getStoreId)
                        .allMatch(v -> v > storeIdMinExclusive)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getStoreId());
            }
        }
    }
}
