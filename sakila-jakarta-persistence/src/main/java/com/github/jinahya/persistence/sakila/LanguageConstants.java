package com.github.jinahya.persistence.sakila;

import jakarta.persistence.metamodel.Attribute;

import java.util.Optional;

/**
 * A class defines constants related to {@link Language} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class LanguageConstants {

    /**
     * The name of the query which selects an entity whose {@link Language_#languageId languageId} attribute matches a
     * specific value. The value is {@value}.
     *
     * @see #PARAMETER_LANGUAGE_ID
     */
    public static final String QUERY_FIND_BY_LANGUAGE_ID = "Language_findByLanguageId";

    public static final String PARAMETER_LANGUAGE_ID = "languageId";

    static {
        Optional.ofNullable(Language_.languageId).map(Attribute::getName).ifPresent(v -> {
            assert v.equals(PARAMETER_LANGUAGE_ID);
        });
    }

    /**
     * The name of the query which selects entities whose {@link Language_#languageId languageId} attributes are greater
     * than a specific value, ordered by {@link Language_#languageId languageId} attribute in ascending order. The value
     * is {@value}.
     */
    public static final String QUERY_FIND_ALL = "Language_findAll";

    public static final String QUERY_PARAM_LANGUAGE_ID_MIN_EXCLUSIVE = "languageIdMinExclusive";

    public static final String NAMED_QUERY_FIND_ALL_BY_LANGUAGE_ID_GREATER_THAN = "Language_findAllByLanguageIdGreaterThan";

    private LanguageConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
