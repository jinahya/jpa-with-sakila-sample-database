package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence._BaseEntity;

/**
 * An abstract base class for subclasses of {@link _BaseEntity}.
 *
 * @param <ENTITY> entity type parameter
 * @param <ID>     id type parameter
 */
public abstract class _BaseEntityService<ENTITY extends _BaseEntity<ID>, ID extends Comparable<? super ID>>
        extends __BaseEntityService<ENTITY, ID> {

    /**
     * Creates a new instance with specified entity class and id class.
     *
     * @param entityClass the entity class.
     * @param idClass     the id class.
     */
    _BaseEntityService(final Class<ENTITY> entityClass, final Class<ID> idClass) {
        super(entityClass, idClass);
    }
}
