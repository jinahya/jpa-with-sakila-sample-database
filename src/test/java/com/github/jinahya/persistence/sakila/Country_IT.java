package com.github.jinahya.persistence.sakila;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;

class Country_IT
        extends _BaseEntityIT<Country, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    static Country newPersistedInstance(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new Country_Randomizer().getRandomValue();
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
        assertThat(instance).isNotNull();
        assertThatBean(instance).isValid();
    }

    @DisplayName("find(0)null")
    @Test
    void _Null_0() {
        final var found = applyEntityManager(em -> em.find(Country.class, 0));
        assertThat(found).isNull();
    }

    @DisplayName("find(1)!null")
    @Test
    void _NotNull_1() {
        final var found = applyEntityManager(em -> em.find(Country.class, 1));
        assertThat(found).isNotNull();
        assertThatBean(found).isValid();
    }

    @Nested
    class CountryAsLocalePropertyTest {

        /**
         * {@value Country#TABLE_NAME} 테이블에 저장된 모든 {@link Country_#country country} 컬럼값과 {@link java.util.Locale} 에 정의된
         * 값들을 비교한다.
         */
        @Test
        void __() {
            final var all = applyEntityManager(
                    em -> em.createQuery("SELECT e FROM Country AS e", Country.class)
                            .getResultList()
            );
            all.forEach(e -> {
                final var locale = e.getCountryAsLocale();
                if (locale == null) {
                    log.debug("no locale for '{}'", e.getCountry());
                }
            });
        }
    }
}
