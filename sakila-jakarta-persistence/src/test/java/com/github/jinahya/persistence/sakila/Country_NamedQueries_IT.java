package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static com.github.jinahya.persistence.sakila.CountryConstants.PARAMETER_COUNTRY_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila.CountryConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.CountryConstants.QUERY_FIND_BY_COUNTRY_ID;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * A class for testing named queries defined on {@link Country} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class Country_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

    @DisplayName(QUERY_FIND_BY_COUNTRY_ID)
    @Nested
    class FindByCountryIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            // GIVEN
            final var countryId = 0;
            // WHEN / THEN
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_COUNTRY_ID, Country.class)
                                    .setParameter(Country_.countryId.getName(), countryId)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void __1() {
            // GIVEN
            final var countryId = 1; // Afghanistan
            // WHEN
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_COUNTRY_ID, Country.class)
                            .setParameter(Country_.countryId.getName(), countryId)
                            .getSingleResult()
            );
            // THEN
            assertThat(found)
                    .isNotNull()
                    .extracting(Country::getCountryId)
                    .isEqualTo(countryId);
            log.debug("country: " + found.getCountry());
        }

        @DisplayName("(86)!null")
        @Test
        void __86() {
            // GIVEN
            final var countryId = 86; // South Korea
            // WHEN
            // THEN
        }
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var maxResults = 64;
            for (var i = 0; ; ) {
                final var countryIdMinExclusive = i;
                // WHEN
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL, Country.class)
                                .setParameter(PARAMETER_COUNTRY_ID_MIN_EXCLUSIVE, countryIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                // THEN
                assertThat(list)
                        .isNotNull()
                        // not asserting the emptiness; the last page may be empty.
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
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
