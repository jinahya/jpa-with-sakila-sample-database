package com.github.jinahya.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class StaffList_NamedQueries_IT
        extends __BaseEntityIT<StaffList, Integer> {

    StaffList_NamedQueries_IT() {
        super(StaffList.class, Integer.class);
    }

    @DisplayName(StaffListConstants.NAMED_QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(StaffListConstants.NAMED_QUERY_FIND_ALL, StaffList.class)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @Test
        void __WithMaxResults() {
            final int maxResults = ThreadLocalRandom.current().nextInt(1, 8);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(StaffListConstants.NAMED_QUERY_FIND_ALL, StaffList.class)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }
}
