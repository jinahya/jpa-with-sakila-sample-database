package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Actor;
import com.github.jinahya.persistence.sakila.ActorConstants;
import com.github.jinahya.persistence.sakila.Actor_;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service class related to {@link Actor} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class ActorService
        extends _BaseEntityService<Actor, Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Creates a new instance.
     */
    ActorService() {
        super(Actor.class, Integer.class);
    }

    @Override
    // HV000131: A method return value must not be marked for cascaded validation more than once in a class hierarchy, ...
    public List<Actor> findAll(@Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            return super.findAll(maxResults);
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(ActorConstants.QUERY_FIND_ALL, Actor.class);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }

    /**
     * Finds the entity whose value of {@link Actor_#actorId actorId} attribute matches specified value.
     *
     * @param actorId the value of the {@link Actor_#actorId actorId} attribute to match.
     * @return an optional of found entity; {@code empty} if not found.
     */
    public Optional<@Valid Actor> findByActorId(@Positive final int actorId) {
        if (current().nextBoolean()) {
            return findById(actorId);
        }
        return Optional.ofNullable(
                applyEntityManager(em -> {
                    try {
                        return em.createNamedQuery(
                                        ActorConstants.QUERY_FIND_BY_ACTOR_ID,
                                        Actor.class
                                )
                                .setParameter(Actor_.actorId.getName(), actorId)
                                .getSingleResult(); // NoResultException
                    } catch (final NoResultException nre) {
                        return null;
                    }
                })
        );
    }

    /**
     * Finds all entities whose values of {@link Actor_#actorId actorId} attribute is greater than specified value.
     *
     * @param actorIdMinExclusive the lower exclusive value of the {@link Actor_#actorId actorId} attribute to compare.
     * @param maxResults          a number of maximum results to limit; {@code null} for an unlimited results.
     * @return a list of found entities.
     */
    @NotNull
    public List<@Valid @NotNull Actor> findAllByActorIdGreaterThan(@PositiveOrZero final int actorIdMinExclusive,
                                                                   @Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            return findAllByIdGreaterThan(
                    r -> r.get(Actor_.actorId),
                    actorIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(
                    ActorConstants.QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN,
                    Actor.class
            );
            query.setParameter("actorIdMinExclusive", actorIdMinExclusive);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }

    /**
     * Finds the entity whose value of {@link Actor_#lastName lastName} attribute matches specified value.
     *
     * @param lastName   the value of {@link Actor_#lastName lastName} attribute to match.
     * @param maxResults a number of maximum results to limit; {@code null} for an unlimited results.
     * @return a list of found entities;
     */
    @NotNull
    public List<@Valid @NotNull Actor> findAllByLastName(@NotBlank final String lastName,
                                                         @Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            return findAllByAttribute(
                    r -> r.get(Actor_.lastName),
                    lastName,
                    maxResults
            );
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(
                    ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME,
                    Actor.class
            );
            query.setParameter(Actor_.lastName.getName(), lastName);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }

    /**
     * Finds all entities whose values of {@link Actor_#lastName lastName} attribute match specified value and whose
     * values of {@link Actor_#actorId} attribute are greater than specified value, sorted by
     * {@link Actor_#actorId actorId} attribute in ascending order.
     *
     * @param lastName            the value of {@link Actor_#lastName lastName} attribute to match.
     * @param actorIdMinExclusive the lower exclusive value of {@link Actor_#actorId actorId} attribute.
     * @param maxResults          a number of maximum results to limit; {@code null} for an unlimited results.
     * @return a list of found entities;
     */
    @NotNull
    public List<@Valid @NotNull Actor> findAllByLastNameActorIdGreaterThan(
            @NotBlank final String lastName, @PositiveOrZero final int actorIdMinExclusive,
            @Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            return findAllByAttributeIdGreaterThan(
                    r -> r.get(Actor_.lastName),
                    lastName,
                    r -> r.get(Actor_.actorId),
                    actorIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(
                    ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME_ACTOR_ID_GREATER_THAN,
                    Actor.class
            );
            query.setParameter(Actor_.lastName.getName(), lastName);
            query.setParameter("actorIdMinExclusive", actorIdMinExclusive);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }
}
