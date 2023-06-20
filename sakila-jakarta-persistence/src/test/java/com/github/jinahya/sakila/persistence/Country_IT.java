package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.Objects;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Locale.ENGLISH;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

class Country_IT
        extends _BaseEntityIT<Country, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    static Country newPersistedCountry(final EntityManager entityManager) {
        requireNonNull(entityManager, "entityManager is null");
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
        final var instance = applyEntityManager(Country_IT::newPersistedCountry);
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
    class GetLocalesForCountryTest {

        @Test
        void __() {
            final var all = applyEntityManager(
                    em -> em.createQuery("SELECT e FROM Country AS e", Country.class)
                            .getResultList()
            );
            all.forEach(e -> {
                final var localesForCountry = e.getLocalesForCountry();
                assertThat(localesForCountry).satisfiesAnyOf(
                        l -> assertThat(l).isNull(),
                        l -> assertThat(l).isEmpty(),
                        l -> assertThat(l)
                                .extracting(v -> v.getDisplayCountry(ENGLISH))
                                .contains(e.getCountry())
                );
            });
        }
    }
}
