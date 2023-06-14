package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static com.github.jinahya.persistence.sakila.CountryConstants.NATIVE_QUERY_SELECT_ALL_KEYSET;
import static com.github.jinahya.persistence.sakila.CountryConstants.NATIVE_QUERY_SELECT_ALL_ROWSET;
import static com.github.jinahya.persistence.sakila.CountryConstants.NATIVE_QUERY_SELECT_BY_COUNTRY_ID;
import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * A class for testing named native queries defined on {@link Country} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class Country_NamedNativeQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

    @DisplayName(NATIVE_QUERY_SELECT_BY_COUNTRY_ID)
    @Nested
    class SelectByCountryIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(NATIVE_QUERY_SELECT_BY_COUNTRY_ID, Country.class)
                                    .setParameter(1, 0)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void _NotNull_1() {
            // GIVEN
            final var countryId = 1;
            final var expectedCountry = "Afghanistan";
            // WHEN
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(NATIVE_QUERY_SELECT_BY_COUNTRY_ID, Country.class)
                            .setParameter(1, countryId)
                            .getSingleResult()
            );
            // THEN
            assertThat(found)
                    .isNotNull()
                    .satisfies(e -> {
                        assertThat(e.getCountryId()).isEqualTo(countryId);
                        assertThat(e.getCountry()).isEqualTo(expectedCountry);
                    });
        }

        @DisplayName("(86)!null")
        @Test
        void _NotNull_86() {
            // GIVEN
            final var countryId = 86;
            final var expectedCountry = "South Korea";
            // WHEN
            // THEN
        }
    }

    @DisplayName(NATIVE_QUERY_SELECT_ALL_ROWSET)
    @Nested
    class SelectAllRowsetTest {

        @Test
        void __() {
            // GIVEN
            final var limit = 64;
            for (var i = 0; ; ) {
                final var offset = i;
                // WHEN
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(NATIVE_QUERY_SELECT_ALL_ROWSET, Country.class)
                                .setParameter(1, offset)
                                .setParameter(2, limit)
                                .getResultList()
                );
                // THEN
                assertThat(list)
                        .isNotNull()
                        // not asserting the emptiness; the last page may be empty.
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(limit)
                        .extracting(Country::getCountryId)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                i += list.size();
            }
        }
    }

    @DisplayName(NATIVE_QUERY_SELECT_ALL_KEYSET)
    @Nested
    class SelectAllKeysetTest {

        @Test
        void __() {
            // GIVEN
            final var limit = 64;
            for (var i = 0; ; ) {
                final var countryIdMinExclusive = i;
                // WHEN
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(NATIVE_QUERY_SELECT_ALL_KEYSET, Country.class)
                                .setParameter(1, countryIdMinExclusive)
                                .setParameter(2, limit)
                                .getResultList()
                );
                // THEN
                assertThat(list)
                        .isNotNull()
                        // not asserting the emptiness; the last page may be empty.
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(limit)
                        .extracting(Country::getCountryId)
                        .allMatch(v -> v > countryIdMinExclusive)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                i = list.get(list.size() - 1).getCountryId();
            }
        }
    }
}
