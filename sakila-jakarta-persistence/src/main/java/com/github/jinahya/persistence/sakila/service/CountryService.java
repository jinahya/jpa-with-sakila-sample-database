package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Country;
import com.github.jinahya.persistence.sakila.Country_;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.jinahya.persistence.sakila.CountryConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.CountryConstants.QUERY_FIND_BY_COUNTRY_ID;
import static com.github.jinahya.persistence.sakila.CountryConstants.PARAMETER_COUNTRY_ID;
import static com.github.jinahya.persistence.sakila.CountryConstants.PARAMETER_COUNTRY_ID_MIN_EXCLUSIVE;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * A service for querying {@link Country} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class CountryService
        extends _BaseEntityService<Country, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    /**
     * Creates a new instance.
     */
    CountryService() {
        super(Country.class, Integer.class);
    }

    /**
     * Finds the entity whose {@link Country_#countryId countryId} attribute matches specified value.
     *
     * @param countryId the value of the {@link Country_#countryId countryId} attribute to match.
     * @return an optional of found entity; {@link Optional#empty() empty} if not found.
     */
    public Optional<@Valid Country> findByCountryId(@Positive final int countryId) {
        if (current().nextBoolean()) {
            return super.findById(countryId);
        }
        return Optional.ofNullable(
                applyEntityManager(em -> {
                    try {
                        return em.createNamedQuery(QUERY_FIND_BY_COUNTRY_ID, Country.class)
                                .setParameter(PARAMETER_COUNTRY_ID, countryId)
                                .getSingleResult(); // NoResultException
                    } catch (final NoResultException nre) {
                        return null;
                    }
                })
        );
    }

    // https://hibernate.atlassian.net/browse/HV-770
    public @NotNull List<@Valid @NotNull Country> findAll(final @PositiveOrZero int countryIdMinExclusive,
                                                          final @Positive int maxResults) {
        if (current().nextBoolean()) {
            return super.findAll(
                    r -> r.get(Country_.countryId),
                    countryIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(
                em -> em.createNamedQuery(QUERY_FIND_ALL, Country.class)
                        .setParameter(PARAMETER_COUNTRY_ID_MIN_EXCLUSIVE, countryIdMinExclusive)
                        .setMaxResults(maxResults)
                        .getResultList()
        );
    }

    @NotNull List<@Valid @NotNull Country> findAllByCountry(final @PositiveOrZero int countryIdMinExclusive,
                                                            final @Positive int maxResults,
                                                            final @NotBlank String country) {
        return super.findAllBy(
                r -> r.get(Country_.countryId),
                countryIdMinExclusive,
                maxResults,
                r -> r.get(Country_.country),
                country
        );
    }

    private static final Map<String, Country> LOCATED_COUNTRIES = new ConcurrentHashMap<>(new WeakHashMap<>());

    @Valid @NotNull Country locateByByCountry(@NotBlank final String country) {
        log.debug("LOCATED_COUNTRIES: {}", LOCATED_COUNTRIES);
        return LOCATED_COUNTRIES.computeIfAbsent(
                new String(country),
                k -> findAllByCountry(0, 1, k)
                        .stream()
                        .findFirst()
                        .orElseGet(() -> persist(Country.of(country)))
        );
    }
}
