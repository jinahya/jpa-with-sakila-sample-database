package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Defines methods for querying {@link Actor} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class ActorQueries {

    /**
     * Finds an actor whose {@value Actor_#ACTOR_ID} attribute matches to specified value.
     *
     * @param entityManager an entity manager.
     * @param actorId       the {@value Actor_#ACTOR_ID} attribute to match.
     * @return an optional of found actor.
     */
    public static Optional<Actor> findByActorId(final EntityManager entityManager, final int actorId) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        if (ThreadLocalRandom.current().nextBoolean()) {
            final TypedQuery<Actor> typedQuery = entityManager.createQuery(
                    "SELECT e FROM Actor AS e WHERE e.actorId = :actorId",
                    Actor.class
            );
            typedQuery.setParameter("actorId", actorId);
            try {
                return Optional.of(typedQuery.getSingleResult());
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Actor> criteriaQuery = builder.createQuery(Actor.class);
        final Root<Actor> root = criteriaQuery.from(Actor.class);                                     // FROM Actor AS e
        criteriaQuery.select(root);                                                                          // SELECT e
        criteriaQuery.where(builder.equal(root.get(Actor_.actorId), actorId));             // WHERE e.actorId = :actorId
        final TypedQuery<Actor> typedQuery = entityManager.createQuery(criteriaQuery);
        final Actor singleResult;
        try {
            singleResult = typedQuery.getSingleResult();
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
        return Optional.of(singleResult);
    }

    /**
     * Finds all actors whose {@value Actor_#LAST_NAME} attributes match to specified value.
     *
     * @param entityManager an entity manager.
     * @param lastName      the {@value Actor_#LAST_NAME} attribute to match.
     * @param maxResults    maximum number of results to retrieve.
     * @return a list of found actors.
     */
    public static List<Actor> findAllByLastName(final EntityManager entityManager, final String lastName,
                                                final int maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(lastName, "lastName is null");
        if (ThreadLocalRandom.current().nextBoolean()) {
            final TypedQuery<Actor> typedQuery = entityManager.createQuery(
                    "SELECT e FROM Actor AS e WHERE e.lastName = :lastName",
                    Actor.class
            );
            typedQuery.setParameter("lastName", lastName);
            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Actor> criteriaQuery = builder.createQuery(Actor.class);
        final Root<Actor> root = criteriaQuery.from(Actor.class);                                     // FROM Actor AS e
        criteriaQuery.select(root);                                                                          // SELECT e
        criteriaQuery.where(builder.equal(root.get(Actor_.lastName), lastName));         // WHERE e.lastName = :lastName
        final TypedQuery<Actor> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setMaxResults(maxResults);
        return typedQuery.getResultList();
    }

    public static List<Actor> findAllByLastNameLimit(final EntityManager entityManager, final String lastName,
                                                     final int offset, final int rowCount) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(lastName, "lastName is null");
        if (offset < 0) {
            throw new IllegalArgumentException("negative offset: " + offset);
        }
        if (rowCount < 0) {
            throw new IllegalArgumentException("negative rowCount: " + rowCount);
        }
        final Query query = entityManager.createNativeQuery(
                "SELECT *" +
                " FROM " + Actor.TABLE_NAME +
                " WHERE " + Actor.COLUMN_NAME_LAST_NAME + " = ?1" +
                " LIMIT ?2,?3",
                Actor.class
        );
        query.setParameter(1, lastName);
        query.setParameter(2, offset);
        query.setParameter(3, rowCount);
        return query.getResultList();
    }

    /**
     * @param entityManager an entity manager.
     * @param lastName      the value of {@value Actor_#LAST_NAME} attribute to match.
     * @param page          the page number.
     * @param size          a number of element per page.
     * @return a list of found entities.
     */
    public static List<Actor> findAllByLastNamePage(final EntityManager entityManager, final String lastName,
                                                    final int page, final int size) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(lastName, "lastName is null");
        if (page < 0) {
            throw new IllegalArgumentException("negative page: " + page);
        }
        if (size <= 0) {
            throw new IllegalArgumentException("non-positive size: " + size);
        }
        return findAllByLastNameLimit(entityManager, lastName, size * page, size);
    }

    private ActorQueries() {
        throw new AssertionError("instantiation is not allowed");
    }
}
