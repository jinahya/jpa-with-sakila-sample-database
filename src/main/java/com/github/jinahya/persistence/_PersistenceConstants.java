package com.github.jinahya.persistence;

/**
 * Constants for the sakila persistence.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
final class _PersistenceConstants {

    /**
     * The name of the sakila persistence unit. The value is {@value}.
     */
    static final String PERSISTENCE_UNIT_NAME = "sakilaPU";

    static final int COLUMN_LENGTH_TEXT = 65535;

    static final int COLUMN_LENGTH_GEOMETRY = 25;

    static final String COLUMN_NAME_LAST_UPDATE = "last_update";

    static final int MAX_SMALLINT_UNSIGNED = 65536;

    static final int MAX_TINYINT_UNSIGNED = 256;

    static final int MIN_COLUMN_YEAR = 1901;

    static final int MAX_COLUMN_YEAR = 2155;

    private _PersistenceConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
