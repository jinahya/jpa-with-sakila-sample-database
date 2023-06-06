package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.City;
import com.github.jinahya.persistence.sakila.__BaseEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface ___HasAddress<ENTITY extends __BaseEntity<ID>, ID extends Comparable<? super ID>>
        extends ____PersistenceService {

    default List<ENTITY> findAllByCity(final @NotNull City city, final @Positive int maxResults) {
//        applyEntityManager(em -> {
//            final var builder = em.getCriteriaBuilder();
//        });
        return null;
    }
}
