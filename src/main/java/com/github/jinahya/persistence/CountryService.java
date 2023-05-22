package com.github.jinahya.persistence;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CountryService
        extends _BaseEntityService<Country, Integer> {

    protected CountryService() {
        super(Country.class, Integer.class);
    }

    @NotNull
    public List<@Valid @NotNull Country> findAllByCountry(@NotBlank final String country) {
//        Objects.requireNonNull(country, "country is null");
        return applyEntityManager(
                em -> em.createNamedQuery("Country_findAllByCountry", Country.class)
                        .setParameter("country", country)
                        .getResultList()
        );
    }

    @Valid
    @NotNull
    public Country locateByByCountry(@NotBlank final String country) {
//        Objects.requireNonNull(country, "country is null");
        return findAllByCountry(country)
                .stream()
                .findFirst()
                .orElseGet(() -> persist(Country.of(country)));
    }
}
