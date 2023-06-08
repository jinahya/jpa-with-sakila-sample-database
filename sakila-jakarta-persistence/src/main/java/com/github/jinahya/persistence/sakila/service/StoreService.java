package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Store;
import com.github.jinahya.persistence.sakila.Store_;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Optional;

import static com.github.jinahya.persistence.sakila.StoreConstants.PARAMETER_STORE_ID;
import static com.github.jinahya.persistence.sakila.StoreConstants.PARAMETER_STORE_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila.StoreConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.StoreConstants.QUERY_FIND_BY_STORE_ID;
import static java.util.concurrent.ThreadLocalRandom.current;

public class StoreService
        extends _BaseEntityService<Store, Integer> {

    StoreService() {
        super(Store.class, Integer.class);
    }

    public Optional<@Valid Store> findByStoreId(@Positive final int storeId) {
        if (current().nextBoolean()) {
            return Optional.of(
                    applyEntityManager(em -> {
                        try {
                            return em.createNamedQuery(QUERY_FIND_BY_STORE_ID, Store.class)
                                    .setParameter(PARAMETER_STORE_ID, storeId)
                                    .getSingleResult(); // NoResultException
                        } catch (final NoResultException nre) {
                            return null;
                        }
                    })
            );
        }
        return findById(storeId);
    }

    public @NotNull List<@Valid @NotNull Store> findAll(@PositiveOrZero final int storeIdMinExclusive,
                                                        @Positive final int maxResults) {
        if (current().nextBoolean()) {
            return applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_ALL, Store.class)
                            .setParameter(PARAMETER_STORE_ID_MIN_EXCLUSIVE, storeIdMinExclusive)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
        }
        return findAll(
                r -> r.get(Store_.storeId),
                storeIdMinExclusive,
                maxResults
        );
    }

    public @NotNull List<@Valid @NotNull Store> findAll(@Positive final int maxResults) {
        return findAll(0, maxResults);
    }
}
