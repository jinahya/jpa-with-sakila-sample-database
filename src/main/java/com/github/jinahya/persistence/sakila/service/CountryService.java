package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Country;
import com.github.jinahya.persistence.sakila.CountryConstants;
import com.github.jinahya.persistence.sakila.Country_;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A service for querying {@link Country} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class CountryService
        extends _BaseEntityService<Country, Integer> {

    /**
     * Creates a new instance.
     */
    CountryService() {
        super(Country.class, Integer.class);
    }

    /**
     * Finds the entity whose {@link Country_#countryId countryId} attribute matches specified value.
     *
     * @param countryId the value for the {@link Country_#countryId countryId} attribute to match.
     * @return an optional of found entity; {@code empty} if not found.
     */
    Optional<@Valid Country> findByCountryId(@Positive final int countryId) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return super.findById(countryId);
        }
        return Optional.ofNullable(
                applyEntityManager(em -> {
                    try {
                        return em.createNamedQuery(CountryConstants.QUERY_FIND_BY_COUNTRY_ID, Country.class)
                                .setParameter(CountryConstants.QUERY_PARAM_COUNTRY_ID, countryId)
                                .getSingleResult(); // NoResultException
                    } catch (final NoResultException nre) {
                        return null;
                    }
                })
        );
    }

    @Override
    // https://hibernate.atlassian.net/browse/HV-770
    public @NotNull List</*@Valid @NotNull*/ Country> findAll(@Positive final Integer maxResults) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return super.findAll(maxResults);
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(CountryConstants.QUERY_FIND_ALL, Country.class);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }

    public @NotNull List<@Valid @NotNull Country> findAllByCountryIdGreaterThan(
            @PositiveOrZero int countryIdMinExclusive,
            @Positive final Integer maxResults) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return super.findAllByIdGreaterThan(
                    r -> r.get(Country_.countryId),
                    countryIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(CountryConstants.QUERY_FIND_ALL_BY_COUNTRY_ID_GREATER_THAN, Country.class);
            query.setParameter(CountryConstants.QUERY_PARAM_COUNTRY_ID_MIN_EXCLUSIVE, countryIdMinExclusive);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }

    public List<@Valid @NotNull Country> findAllByCountry(@NotBlank final String country,
                                                          @Positive final Integer maxResults) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return super.findAllByAttribute(
                    r -> r.get(Country_.country),
                    country,
                    maxResults
            );
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(CountryConstants.QUERY_FIND_ALL_BY_COUNTRY, Country.class)
                    .setParameter(CountryConstants.QUERY_PARAM_COUNTRY, country);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }

    public @Valid @NotNull Country locateByByCountry(@NotBlank final String country) {
        return findAllByCountry(country, 1)
                .stream()
                .findFirst()
                .orElseGet(() -> persist(Country.of(country)));
    }
}
