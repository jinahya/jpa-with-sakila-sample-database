package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Staff;
import com.github.jinahya.persistence.sakila.StaffConstants;
import com.github.jinahya.persistence.sakila.Staff_;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class StaffService
        extends _BaseEntityService<Staff, Integer> {

    StaffService() {
        super(Staff.class, Integer.class);
    }

    public /*@NotNull*/ Optional<@Valid /*@NotNull*/ Staff> findByStaffId(@Positive final int staffId) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return Optional.of(
                    applyEntityManager(em -> {
                        try {
                            return em.createNamedQuery(StaffConstants.QUERY_FIND_BY_STAFF_ID, Staff.class)
                                    .setParameter(StaffConstants.PARAMETER_STAFF_ID, staffId)
                                    .getSingleResult(); // NoResultException
                        } catch (final NoResultException nre) {
                            return null;
                        }
                    })
            );
        }
        return findById(staffId);
    }

    public List<@Valid @NotNull Staff> findAll(@PositiveOrZero final int staffIdMinExclusive,
                                               @Positive final int maxResults) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return applyEntityManager(
                    em -> em.createNamedQuery(StaffConstants.QUERY_FIND_ALL, Staff.class)
                            .setParameter(StaffConstants.PARAMETER_STAFF_ID_MIN_EXCLUSIVE, staffIdMinExclusive)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
        }
        return findAll(
                r -> r.get(Staff_.staffId),
                staffIdMinExclusive,
                maxResults
        );
    }

    public List<@Valid @NotNull Staff> findAll(@Positive final int maxResults) {
        return findAll(0, maxResults);
    }
}
