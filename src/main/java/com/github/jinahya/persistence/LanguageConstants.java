package com.github.jinahya.persistence;

/**
 * A class defines constants related to {@link Language} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class LanguageConstants {

    /**
     * The name of the named query for finding all entities. The value is {@value}.
     */
    public static final String NAMED_QUERY_FIND_ALL = "Language_findAll";

    public static final String NAMED_QUERY_FIND_BY_LANGUAGE_ID = "Language_findByLanguageId";

    public static final String NAMED_QUERY_FIND_ALL_BY_LANGUAGE_ID_GREATER_THAN = "Language_findAllByLanguageIdGreaterThan";

    public static final String NAMED_QUERY_FIND_ALL_BY_NAME = "Language_findAllByName";

    private LanguageConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
