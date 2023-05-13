package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

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
     * Returns current value of {@value _BaseEntity_#LAST_UPDATE} attribute.
     *
     * @return current value of {@value _BaseEntity_#LAST_UPDATE} attribute.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Replaces current value of {@value _BaseEntity_#LAST_UPDATE} attribute with specified value.
     *
     * @param lastUpdate new value for {@value _BaseEntity_#LAST_UPDATE} attribute.
     * @deprecated for removal
     */
    @Deprecated(forRemoval = true)
    private void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Basic(optional = false)
    @Column(name = _PersistenceConstants.COLUMN_NAME_LAST_UPDATE, nullable = false, insertable = false,
            updatable = false)
    @Setter(AccessLevel.NONE)
    private Timestamp lastUpdate;

    @Transient
    public LocalDateTime getLastUpdateAsLocalDateTime() {
        return Optional.ofNullable(getLastUpdate()).map(Timestamp::toLocalDateTime).orElse(null);
    }
}
