package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.__BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * An abstract base class for subclasses of {@link __BaseEntity}.
 *
 * @param <T> entity type parameter
 * @param <U> id type parameter
 */
abstract class __BaseEntityService<T extends __BaseEntity<U>, U extends Comparable<? super U>>
        extends ___PersistenceService {

    /**
     * Creates a new instance with specified entity class and id class.
     *
     * @param entityClass the entity class.
     * @param idClass     the id class.
     */
    __BaseEntityService(final Class<T> entityClass, final Class<U> idClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
        this.idClass = Objects.requireNonNull(idClass, "idClass is null");
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
    @NotNull
    public T persist(@NotNull final T entity) {
        Objects.requireNonNull(entity, "entity is null");
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
    @NotNull
    public T merge(@NotNull final T entity) {
        Objects.requireNonNull(entity, "entity is null");
        return applyEntityManagerInTransaction(em -> em.merge(entity));
    }

    /**
     * Finds the entity instance identified by specified value.
     *
     * @param id the value identifies the entity.
     * @return an optional of the entity instance identified by {@code id}; empty if not found.
     * @see EntityManager#find(Class, Object)
     */
    public Optional<@Valid T> findById(@NotNull final U id) {
        Objects.requireNonNull(id, "id is null");
        return Optional.ofNullable(
                applyEntityManager(
                        em -> em.find(entityClass, id) // null if not found
                )
        );
    }

    /**
     * Finds all entity instances of {@link #entityClass} optionally limiting the number of results.
     *
     * @param maxResults an optional value for limiting the number of results; {@code null} for an unlimited number of
     *                   results.
     * @return a list of found entity instances.
     */
    @NotNull
    public List<@Valid @NotNull T> findAll(@Positive final Integer maxResults) {
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var criteria = builder.createQuery(entityClass);
            final var root = criteria.from(entityClass);
            criteria.select(root);
            final var typed = em.createQuery(criteria);
            if (maxResults != null) {
                typed.setMaxResults(maxResults);
            }
            return typed.getResultList();
        });
    }

    /**
     * Finds all entity instances whose <em>id</em> attributes are greater than specified value.
     *
     * @param idExpressionMapper  a function for evaluating the path to the <em>id</em> attribute.
     * @param idValueMinExclusive the lower <em>exclusive</em> value of the <em>id</em> attribute to compare.
     * @param maxResults          an optional value for limiting the number of results; {@code null} for an unlimited
     *                            number of results.
     * @return a list of entity instances, ordered by the <em>id</em> attribute in ascending order.
     */
    @NotNull
    List<@Valid @NotNull T> findAllByIdGreaterThan(
            @NotNull final Function<? super Root<T>, ? extends Expression<? extends U>> idExpressionMapper,
            @NotNull final U idValueMinExclusive, @Positive final Integer maxResults) {
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var criteria = builder.createQuery(entityClass);
            final var root = criteria.from(entityClass);
            criteria.select(root);
            final var idExpression = idExpressionMapper.apply(root);
            criteria.where(
                    builder.greaterThan(
                            idExpression,
                            idValueMinExclusive
                    )
            );
            criteria.orderBy(builder.asc(idExpression));
            final var typed = em.createQuery(criteria);
            if (maxResults != null) {
                typed.setMaxResults(maxResults);
            }
            return typed.getResultList();
        });
    }

    /**
     * Finds all entities by an attribute matching specified value.
     *
     * @param expressionMapper a function for evaluating the attribute.
     * @param attributeValue   the value of the attribute to match.
     * @param maxResults       a number of results to limit; {@code null} for an unlimited result.
     * @param <V>              attribute type parameter
     * @return a list of found entities.
     */
    @NotNull <V> List<@Valid @NotNull T> findAllByAttribute(
            @NotNull final Function<? super Root<T>, ? extends Expression<? extends V>> expressionMapper,
            @NotNull final V attributeValue, @Positive final Integer maxResults) {
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var criteria = builder.createQuery(entityClass);
            final var root = criteria.from(entityClass);
            criteria.select(root);
            final var expression = expressionMapper.apply(root);
            criteria.where(
                    builder.equal(
                            expression,
                            attributeValue
                    )
            );
            final var typed = em.createQuery(criteria);
            if (maxResults != null) {
                typed.setMaxResults(maxResults);
            }
            return typed.getResultList();
        });
    }

    /**
     * Finds all entities whose specific attribute matches specified value and whose <em>id</em> attribute is greater
     * than specified value.
     *
     * @param attributeExpressionMapper   a function for mapping a path to the attribute to match.
     * @param attributeValue              the value of the target attribute to match.
     * @param idAttributeExpressionMapper a function for mapping a path the <em>id</em> attribute.
     * @param idValueMinExclusive         the lower exclusive <em>id</em> attribute value.
     * @param maxResults                  a number of results to limit; {@code null} for an unlimited result.
     * @param <V>                         attribute type parameter
     * @return a list of found entities ordered by the <em>id</em> attribute in ascending order.
     */
    @NotNull <V> List<@Valid @NotNull T> findAllByAttributeIdGreaterThan(
            @NotNull final Function<? super Root<T>, ? extends Expression<? extends V>> attributeExpressionMapper,
            @NotNull final V attributeValue,
            @NotNull final Function<? super Root<T>, ? extends Expression<? extends U>> idAttributeExpressionMapper,
            @NotNull final U idValueMinExclusive, @Positive final Integer maxResults) {
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var criteria = builder.createQuery(entityClass);
            final var root = criteria.from(entityClass);
            criteria.select(root);
            final var attributeExpression = attributeExpressionMapper.apply(root);
            final var idAttributeExpression = idAttributeExpressionMapper.apply(root);
            criteria.where(
                    builder.and(
                            builder.equal(attributeExpression, attributeValue),
                            builder.greaterThan(idAttributeExpression, idValueMinExclusive)
                    )
            );
            criteria.orderBy(builder.asc(idAttributeExpression));
            final var typed = em.createQuery(criteria);
            if (maxResults != null) {
                typed.setMaxResults(maxResults);
            }
            return typed.getResultList();
        });
    }

    /**
     * The entity class.
     */
    final Class<T> entityClass;

    /**
     * The type of id of {@link #entityClass}.
     */
    final Class<U> idClass;
}
