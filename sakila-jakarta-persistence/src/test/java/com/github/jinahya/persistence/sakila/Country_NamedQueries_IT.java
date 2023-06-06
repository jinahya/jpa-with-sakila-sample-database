package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jinahya.persistence.sakila.CountryConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.CountryConstants.QUERY_FIND_BY_COUNTRY_ID;
import static com.github.jinahya.persistence.sakila.CountryConstants.QUERY_PARAM_COUNTRY_ID_MIN_EXCLUSIVE;
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

        @DisplayName("findByCountryId(1)")
        @Test
        void __1() {
            // GIVEN
            final var countryId = 1;
            // WHEN
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_COUNTRY_ID, Country.class)
                            .setParameter(Country_.countryId.getName(), countryId)
                            .getSingleResult() // NoResultException
            );
            // THEN
            assertThat(found)
                    .isNotNull()
                    .extracting(Country::getCountryId)
                    .isEqualTo(countryId);
        }

        @DisplayName("findByCountryId(86)")
        @Test
        void __86() {
            // GIVEN
            final var countryId = 86;
            // TODO: implement!
            // WHEN
            // THEN
        }
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var maxResults = current().nextInt(8, 16);
            for (final var i = new AtomicInteger(0); ; ) {
                final var countryIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL, Country.class)
                                .setParameter(QUERY_PARAM_COUNTRY_ID_MIN_EXCLUSIVE, countryIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .extracting(Country::getCountryId)
                        .allMatch(v -> v > countryIdMinExclusive)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getCountryId());
            }
        }
    }
}
