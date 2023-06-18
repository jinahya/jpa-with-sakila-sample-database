package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.City;
import com.github.jinahya.sakila.persistence.__BaseEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface __HasAddress<ENTITY extends __BaseEntity<ID>, ID extends Comparable<? super ID>>
        extends ____PersistenceService {

    default List<ENTITY> findAllByCity(final @NotNull City city, final @Positive int maxResults) {
//        applyEntityManager(em -> {
//            final var builder = em.getCriteriaBuilder();
//        });
        return null;
    }
}
