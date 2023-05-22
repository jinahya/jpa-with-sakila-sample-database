package com.github.jinahya.persistence;

/**
 * An abstract base class for subclasses of {@link _BaseEntity}.
 *
 * @param <T> entity type parameter
 * @param <U> id type parameter
 */
abstract class _BaseEntityService<T extends _BaseEntity<U>, U extends Comparable<? super U>>
        extends __BaseEntityService<T, U> {

    /**
     * Creates a new instance with specified entity class and id class.
     *
     * @param entityClass the entity class.
     * @param idClass     the id class.
     */
    _BaseEntityService(final Class<T> entityClass, final Class<U> idClass) {
        super(entityClass, idClass);
    }
}
