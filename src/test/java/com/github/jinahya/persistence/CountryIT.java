package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

@Slf4j
class CountryIT
        extends _BaseEntityIT<Country, Integer> {

    CountryIT() {
        super(Country.class, Integer.class);
    }

    @Nested
    class CountryAsLocaleTest {

        /**
         * {@value Country#TABLE_NAME} 테이블에 저장된 모든 {@link Country_#country country} 컬럼값과 {@link java.util.Locale} 에 정의된
         * 값들을 비교한다.
         */
        @Test
        void __() {
            final List<Country> found = applyEntityManager(
                    em -> em.createQuery("SELECT e FROM Country AS e").getResultList()
            );
            for (final var e : found) {
                final Locale locale;
                try {
                    locale = e.getCountryAsLocale();
                } catch (final UnsupportedOperationException uoe) {
                    log.warn("unsupported: {}", uoe.getMessage());
                    continue;
                }
                if (locale == null) {
                    log.debug("no locale for '{}'", e.getCountry());
                }
            }
        }
    }
}
