package com.github.jinahya.persistence;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

class CityService
        extends _BaseEntityService<City, Integer> {

    CityService() {
        super(City.class, Integer.class);
    }

    @NotNull
    public List<@Valid @NotNull City> findAllByCity(@NotBlank final String city) {
        Objects.requireNonNull(city, "city is null");
        return applyEntityManager(
                em -> em.createNamedQuery("City_findAllByCity", City.class)
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
                .orElseGet(() -> persist(City.of(city)));
    }
}
