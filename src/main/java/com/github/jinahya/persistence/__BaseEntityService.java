package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@ValidateOnExecution(type = {ExecutableType.ALL})
@ApplicationScoped
abstract class __BaseEntityService<T extends __BaseEntity<U>, U> {

    __BaseEntityService(final Class<T> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
    }

    public void flush() {
        applyEntityManager(em -> {
            em.flush();
            return null;
        });
    }

    public T persist(final T entity) {
        Objects.requireNonNull(entity, "entity is null");
        return applyEntityManagerInTransaction(em -> {
            em.persist(entity);
            em.flush();
            return entity;
        });
    }

    public T merge(final T entity) {
        Objects.requireNonNull(entity, "entity is null");
        return applyEntityManagerInTransaction(em -> em.merge(entity));
    }

    public Optional<T> findById(final U id) {
        Objects.requireNonNull(id, "id is null");
        return Optional.ofNullable(
                applyEntityManager(em -> em.find(entityClass, id))
        );
    }

    protected <R> R lockTableAndApplyEntityManager(final _JdbcConstants.LockType lockType,
                                                   final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(lockType, "lockType is null");
        Objects.requireNonNull(function, "function is null");
        return applyEntityManagerInTransaction(em -> {
            try {
                return _JdbcUtils.lockTableAndGet(
                        em.unwrap(Connection.class),
                        getTableName(),
                        lockType,
                        () -> function.apply(em)
                );
            } catch (final SQLException sqle) {
                throw new RuntimeException("failed to lock the table('" + getTableName() + "')", sqle);
            }
        });
    }

    protected <R> R applyEntityManagerInTransaction(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        final var manager = getEntityManager();
        final var transaction = manager.getTransaction();
        transaction.begin();
        try {
            return applyEntityManager(function);
        } finally {
            transaction.commit();
        }
    }

    protected <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return function.apply(getEntityManager());
    }

    protected String getTableName() {
        var result = tableName;
        if (result == null) {
            result = tableName = __BaseEntityUtils.findTableName(entityClass);
        }
        return result;
    }

    private EntityManager getEntityManager() {
        var result = entityManagerProxy;
        if (result == null) {
            result = entityManagerProxy = _LangUtils.uncloseableProxy(EntityManager.class, entityManager);
        }
        return result;
    }

    protected final Class<T> entityClass;

    private String tableName;

    @Inject
    private EntityManager entityManager;

    private EntityManager entityManagerProxy;
}
