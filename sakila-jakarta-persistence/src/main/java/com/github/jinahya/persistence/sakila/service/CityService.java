package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.City;
import com.github.jinahya.persistence.sakila.CityConstants;
import com.github.jinahya.persistence.sakila.City_;
import com.github.jinahya.persistence.sakila._DomainConstants;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

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
}
