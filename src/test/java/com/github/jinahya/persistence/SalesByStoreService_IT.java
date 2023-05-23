package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@AddBeanClasses({SalesByStoreService.class})
@Slf4j
class SalesByStoreService_IT
        extends ___PersistenceServiceIT<SalesByStoreService> {

    SalesByStoreService_IT() {
        super(SalesByStoreService.class);
    }

    @DisplayName("findAll")
    @Nested
    class FindAllTest {

        @DisplayName("findAll(null)")
        @Test
        void __MaxResultsNull() {
            final var list = applyServiceInstance(s -> s.findAll(null));
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @DisplayName("findAll(!null)")
        @Test
        void __() {
            final int maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            final var list = applyServiceInstance(s -> s.findAll(maxResults));
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }
}
