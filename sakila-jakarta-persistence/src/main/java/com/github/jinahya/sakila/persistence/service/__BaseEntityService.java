package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.__BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * An abstract base class for subclasses of {@link __BaseEntity}.
 *
 * @param <ENTITY> entity type parameter
 * @param <ID>     id type parameter
 */
abstract class __BaseEntityService<ENTITY extends __BaseEntity<ID>, ID extends Comparable<? super ID>>
        extends ___PersistenceService {

    /**
     * Creates a new instance with specified entity class and id class.
     *
     * @param entityClass the entity class.
     * @param idClass     the id class.
     */
    __BaseEntityService(final Class<ENTITY> entityClass, final Class<ID> idClass) {
        super();
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
        this.idClass = requireNonNull(idClass, "idClass is null");
    }

    /**
     * Invokes {@link EntityManager#flush() flush()} method on an injected instance of {@link EntityManager}.
     *
     * @see EntityManager#flush()
     */
    public void flush() {
        applyEntityManager(em -> {
            em.flush();
            return null;
        });
    }

    /**
     * Invokes {@link EntityManager#persist(Object)} method, on an injected instance of {@link EntityManager}, with
     * specified entity instance, and returns the specified value.
     *
     * @param entity the entity instance to persist.
     * @return given {@code entity}.
     * @see EntityManager#persist(Object)
     */
    public @Valid @NotNull ENTITY persist(final @Valid @NotNull ENTITY entity) {
        requireNonNull(entity, "entity is null");
        return applyEntityManagerInTransaction(em -> {
            em.persist(entity);
            return entity;
        });
    }

    /**
     * Invokes {@link EntityManager#merge(Object) merge(Object)} method, on an injected instance of
     * {@link EntityManager}, with specified entity instance, and returns the result.
     *
     * @param entity the entity instance to merge.
     * @return the result of the {@link EntityManager#merge(Object) merge} invocation.
     * @see EntityManager#merge(Object)
     */
    public @Valid @NotNull ENTITY merge(final @Valid @NotNull ENTITY entity) {
        requireNonNull(entity, "entity is null");
        return applyEntityManagerInTransaction(em -> em.merge(entity));
    }

    /**
     * Finds the entity identified by specified value.
     *
     * @param id the value identifies the entity.
     * @return an optional of found entity identified by {@code id}; empty if not found.
     * @see EntityManager#find(Class, Object)
     */
    Optional<@Valid ENTITY> findById(final @NotNull ID id) {
        requireNonNull(id, "id is null");
        return ofNullable(
                applyEntityManager(
                        em -> em.find(entityClass, id) // null if not found
                )
        );
    }

    <A extends Comparable<? super A>> @NotNull List<@Valid @NotNull ENTITY> findAllByUniqueOrderable(
            @NotNull final Function<? super Root<ENTITY>, ? extends Expression<? extends A>> attributeExpressionMapper,
            final A attributeValueMinExclusive, @Positive final int maxResults) {
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var query = builder.createQuery(entityClass);
            final var root = query.from(entityClass);                          // FROM ENTITY AS e
            query.select(root);                                                // SELECT e
            final var attributeExpression = attributeExpressionMapper.apply(root);
            if (attributeValueMinExclusive != null) {                          // WHERE e.A > attributeValueMinExclusive
                query.where(builder.greaterThan(attributeExpression, attributeValueMinExclusive));
            }
            query.orderBy(builder.asc(attributeExpression));                   // ORDER BY e.A ASC
            return em.createQuery(query)
                    .setMaxResults(maxResults)
                    .getResultList();
        });
    }

    /**
     * Finds all entities whose {@link ID} attributes are greater than specified value, ordered by the {@link ID}
     * attribute in ascending order.
     *
     * @param idExpressionMapper  a function for evaluating the path to the {@link ID} attribute.
     * @param idValueMinExclusive the lower exclusive value of the {@link ID} attribute to limit.
     * @param maxResults          a number of maximum results to limit
     * @return a list of found entities, ordered by the <em>id</em> attribute in ascending order.
     */
    @NotNull List<@Valid @NotNull ENTITY> findAll(
            @NotNull final Function<? super Root<ENTITY>, ? extends Expression<? extends ID>> idExpressionMapper,
            final ID idValueMinExclusive, @Positive final int maxResults) {
        if (current().nextBoolean()) {
            return findAllByUniqueOrderable(idExpressionMapper, idValueMinExclusive, maxResults);
        }
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var query = builder.createQuery(entityClass);
            final var root = query.from(entityClass);                               // FROM ENTITY AS e
            query.select(root);                                                     // SELECT e
            final var idExpression = idExpressionMapper.apply(root);
            if (idValueMinExclusive != null) {
                query.where(builder.greaterThan(idExpression, idValueMinExclusive));// WHERE e.ID > :idValueMinExclusive
            }
            query.orderBy(builder.asc(idExpression));                               // ORDER BY e.ID ASC
            return em.createQuery(query)
                    .setMaxResults(maxResults)
                    .getResultList();
        });
    }

    <A> @NotNull List<@Valid @NotNull ENTITY> findAllBy(
            @NotNull final Function<? super Root<ENTITY>, ? extends Expression<? extends ID>> idExpressionMapper,
            final ID idValueMinExclusive, @Positive final int maxResults,
            @NotNull final Function<? super Root<ENTITY>, ? extends Expression<? extends A>> attributeExpressionMapper,
            @NotNull final A attributeValue) {
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var query = builder.createQuery(entityClass);
            final var root = query.from(entityClass);                               // FROM ENTITY AS e
            query.select(root);                                                     // SELECT e
            final var idExpression = idExpressionMapper.apply(root);
            final var idPredicate = ofNullable(idValueMinExclusive)
                    .map(v -> builder.greaterThan(idExpression, v))
                    .orElseGet(builder::conjunction);
            final var attributeExpression = attributeExpressionMapper.apply(root);
            query.where(builder.and(
                    builder.equal(attributeExpression, attributeValue),             // WHERE e.A = :attributeValue
                    idPredicate                                                     //   AND e.ID > :idValueMinExclusive
            ));
            query.orderBy(builder.asc(idExpression));                               // ORDER BY e.ID ASC
            return em.createQuery(query)
                    .setMaxResults(maxResults)
                    .getResultList();
        });
    }

    /**
     * The entity class.
     */
    final Class<ENTITY> entityClass;

    /**
     * The type of id of {@link #entityClass}.
     */
    final Class<ID> idClass;
}
