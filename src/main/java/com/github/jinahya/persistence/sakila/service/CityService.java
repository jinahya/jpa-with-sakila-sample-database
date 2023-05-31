package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.City;
import com.github.jinahya.persistence.sakila.CityConstants;
import com.github.jinahya.persistence.sakila.City_;
import com.github.jinahya.persistence.sakila._DomainConstants;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class CityService
        extends _BaseEntityService<City, Integer> {

    CityService() {
        super(City.class, Integer.class);
    }

    public Optional<@Valid City> findByCityId(
            @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED) @Positive final int cityId) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return super.findById(cityId);
        }
        return Optional.ofNullable(
                applyEntityManager(em -> {
                    try {
                        return em.createNamedQuery(CityConstants.QUERY_FIND_BY_CITY_ID, City.class)
                                .setParameter(City_.cityId.getName(), cityId)
                                .getSingleResult(); // NoResultException
                    } catch (final NoResultException nre) {
                        return null;
                    }
                })
        );
    }

    @NotNull
    public List<@Valid @NotNull City> findAllByCity(@NotBlank final String city) {
        Objects.requireNonNull(city, "city is null");
        return applyEntityManager(
                em -> em.createNamedQuery(CityConstants.QUERY_FIND_ALL_BY_CITY, City.class)
                        .setParameter("city", city)
                        .getResultList()
        );
    }

    @Valid
    @NotNull
    public City locateByByCity(@NotBlank final String city) {
        Objects.requireNonNull(city, "city is null");
        return findAllByCity(city)
                .stream()
                .findFirst()
                .orElseGet(() -> persist(City.ofCity(city)));
    }
}
