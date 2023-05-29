package com.github.jinahya.persistence.sakila;

/**
 * Constants for the sakila persistence.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class _PersistenceConstants {

    /**
     * The name of the sakila persistence unit. The value is {@value}.
     */
    static final String PERSISTENCE_UNIT_NAME = "sakilaPU";

    static final int COLUMN_LENGTH_TEXT = 65535;

    static final String COLUMN_NAME_LAST_UPDATE = "last_update";

    /**
     * The maximum value for columns of {@code MEDIUMINT UNSIGNED} type.
     *
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/integer-types.html">11.1.2 Integer Types (Exact Value) -
     * INTEGER, INT, SMALLINT, TINYINT, MEDIUMINT, BIGINT</a>
     */
    static final int MAX_MEDIUMINT_UNSIGNED = 8388607;

    public static final int MAX_SMALLINT_UNSIGNED = 65536;

    static final int MAX_TINYINT_UNSIGNED = 256;

    private _PersistenceConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
