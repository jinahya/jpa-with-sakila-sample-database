package com.github.jinahya.sakila.persistence;

/**
 * Constants related to the (My)SQL.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class _SqlConstants {

    /**
     * A paraemter name for the first row to return. The value is {@value}.
     *
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/select.html">13.2.13 SELECT Statement (MySQL Reference
     * Manual)</a>
     */
    @Deprecated(forRemoval = true)
    public static final String PARAMETER_OFFSET = "offset";

    /**
     * A parameter name for the maximum number of rows to return. The value is {@value}.
     *
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/select.html">13.2.13 SELECT Statement (MySQL Reference
     * Manual)</a>
     */
    @Deprecated(forRemoval = true)
    public static final String PARAMETER_LIMIT = "limit";

    private _SqlConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
