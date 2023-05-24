package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;

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
@ValidateOnExecution(type = {ExecutableType.ALL})
@ApplicationScoped
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
     * Flushes current entity manager.
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
     * Persist specified entity.
     *
     * @param entity the entity to persist.
     * @return given {@code entity}.
     * @see EntityManager#persist(Object)
     */
    public T persist(final T entity) {
        Objects.requireNonNull(entity, "entity is null");
        return applyEntityManagerInTransaction(em -> {
            em.persist(entity);
//            em.flush();
            return entity;
        });
    }

    /**
     * Merges specified entity.
     *
     * @param entity the entity to merge.
     * @return a merged instance.
     * @see EntityManager#merge(Object)
     */
    public T merge(final T entity) {
        Objects.requireNonNull(entity, "entity is null");
        return applyEntityManagerInTransaction(em -> em.merge(entity));
    }

    /**
     * Finds the entity identified by specified value.
     *
     * @param id the value identifies the entity.
     * @return an optional of entity identified by {@code id}; empty if not found.
     */
    public Optional<T> findById(final U id) {
        Objects.requireNonNull(id, "id is null");
        return Optional.ofNullable(
                applyEntityManager(
                        em -> em.find(entityClass, id) // null if not found
                )
        );
    }

    @NotNull
    public List<@Valid @NotNull T> findAll(@Positive final Integer maxResults) {
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var criteria = builder.createQuery(entityClass);
            final var root = criteria.from(entityClass);
            criteria.select(root);
            final var typed = getEntityManager().createQuery(criteria);
            if (maxResults != null) {
                typed.setMaxResults(maxResults);
            }
            return typed.getResultList();
        });
    }

    @NotNull
    public List<@Valid @NotNull T> findAllByIdGreaterThan(
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
            final var typed = getEntityManager().createQuery(criteria);
            if (maxResults != null) {
                typed.setMaxResults(maxResults);
            }
            return typed.getResultList();
        });
    }

    /**
     * Returns the name of the target table/view of {@link #entityClass}.
     *
     * @return the name of the target table/view of {@link #entityClass}.
     */
    String getTableName() {
        var result = tableName;
        if (result == null) {
            result = tableName = ____Utils.findTableName(entityClass);
        }
        return result;
    }

    /**
     * The entity class.
     */
    protected final Class<T> entityClass;

    /**
     * The type of id of {@link #entityClass}.
     */
    protected final Class<U> idClass;

    private String tableName;
}
