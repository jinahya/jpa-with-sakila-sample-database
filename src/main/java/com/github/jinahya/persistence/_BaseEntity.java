package com.github.jinahya.persistence;

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
 * @param <U> id type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@MappedSuperclass
public abstract class _BaseEntity<U>
        extends __BaseEntity<U> {

    /**
     * Creates a new instance.
     */
    protected _BaseEntity() {
        super();
    }

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
    private void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * When the row was created or most recently updated.
     */
    @Basic(optional = false)
    @Column(name = _PersistenceConstants.COLUMN_NAME_LAST_UPDATE, nullable = false, insertable = false,
            updatable = false)
    private Timestamp lastUpdate;

    /**
     * Returns current value of {@link _BaseEntity_#lastUpdate lastUpdate} attribute as an instance of
     * {@link LocalDateTime}.
     *
     * @return current value of {@link _BaseEntity_#lastUpdate lastUpdate} attribute as an instance of
     * {@link LocalDateTime}.
     * @see Timestamp#toLocalDateTime()
     */
    @Transient
    public final LocalDateTime getLastUpdateAsLocalDateTime() {
        return Optional.ofNullable(getLastUpdate())
                .map(Timestamp::toLocalDateTime)
                .orElse(null);
    }
}
