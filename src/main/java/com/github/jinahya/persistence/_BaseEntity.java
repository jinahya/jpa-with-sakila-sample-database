package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public abstract class _BaseEntity<U>
        extends __BaseEntity<U> {

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
