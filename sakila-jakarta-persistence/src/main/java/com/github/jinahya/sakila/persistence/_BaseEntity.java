package com.github.jinahya.sakila.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * An abstract mapped superclass with {@link _BaseEntity_#lastUpdate lastUpdate} attribute.
 *
 * @param <ID> the type of <em>id</em> of this entity class.
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@MappedSuperclass
public abstract class _BaseEntity<ID extends Comparable<? super ID>>
        extends __BaseEntity<ID> {

    /**
     * Creates a new instance.
     */
    _BaseEntity() {
        super();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + '{' +
               "lastUpdate=" + lastUpdate +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof _BaseEntity<?>)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns current value of {@link _BaseEntity_#lastUpdate lastUpdate} attribute.
     *
     * @return current value of {@link _BaseEntity_#lastUpdate lastUpdate} attribute.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Replaces current value of {@link _BaseEntity_#lastUpdate lastUpdate} attribute with specified value.
     *
     * @param lastUpdate new value for {@link _BaseEntity_#lastUpdate lastUpdate} attribute.
     * @deprecated for removal
     */
    @Deprecated(forRemoval = true)
    private void setLastUpdate(final Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure.html">
     * When the row was created or most recently updated.
     * </blockquote>
     */
    @Basic(optional = false)
    @Column(name = _DomainConstants.COLUMN_NAME_LAST_UPDATE, nullable = false,
            insertable = false, // default CURRENT_TIMESTAMP
            updatable = false) // on update CURRENT_TIMESTAMP
    private Timestamp lastUpdate;

    /**
     * Returns current value of {@link _BaseEntity_#lastUpdate lastUpdate} attribute as an instance of
     * {@link LocalDateTime}.
     *
     * @return an instance of {@link LocalDateTime} represents current value of the
     * {@link _BaseEntity_#lastUpdate lastUpdate}.
     * @see #getLastUpdate()
     * @see Timestamp#toLocalDateTime()
     */
    @Transient
    public LocalDateTime getLastUpdateAsLocalDateTime() {
        return Optional.ofNullable(getLastUpdate())
                .map(Timestamp::toLocalDateTime)
                .orElse(null);
    }
}
