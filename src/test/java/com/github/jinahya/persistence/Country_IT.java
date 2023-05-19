package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Country_IT
        extends _BaseEntityIT<Country, Integer> {

    static Country newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new CountryRandomizer().getRandomValue();
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    Country_IT() {
        super(Country.class, Integer.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(Country_IT::newPersistedInstance);
        assertThat(instance).isNotNull().satisfies(c -> {
            assertThat(c.getCountryId()).isNotNull();
            assertThatBean(c).isValid();
        });
    }

    @Nested
    class CountryAsLocalePropertyTest {

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
