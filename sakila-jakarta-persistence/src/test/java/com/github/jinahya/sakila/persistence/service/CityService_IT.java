package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.City;
import com.github.jinahya.sakila.persistence._DomainConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CityService_IT
        extends _BaseEntityServiceIT<CityService, City, Integer> {

    CityService_IT() {
        super(CityService.class, City.class, Integer.class);
    }

    @DisplayName("findByCityId(cityId)")
    @Nested
    class FindByCityIdTest {

        @Test
        void _NotEmpty_1() {
            final var cityId = 1;
            final var found = applyServiceInstance(s -> s.findByCityId(cityId));
            assertThat(found)
                    .hasValueSatisfying(v -> {
                        assertThat(v.getCityId()).isEqualTo(cityId);
                    });
        }

        @Test
        void _NotEmpty_65536() {
            final var cityId = _DomainConstants.MAX_SMALLINT_UNSIGNED;
            final var found = applyServiceInstance(s -> s.findByCityId(cityId));
            assertThat(found).isEmpty();
        }
    }
}
