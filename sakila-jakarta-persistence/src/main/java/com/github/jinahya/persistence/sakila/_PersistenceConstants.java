package com.github.jinahya.persistence.sakila;

/**
 * Constants related to the Jakarta Persistence.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html">Jakarta
 * Persistence</a>
 */
public final class _PersistenceConstants {

    /**
     * The constant for {@value} persistence property.
     *
     * @see <a
     * href="https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#fetch-graph-semantics">3.7.4.1.
     * Fetch Graph Semantics</a>
     */
    public static final String PERSISTENCE_FETCHGRAPH = "javax.persistence.fetchgraph";

    /**
     * The constant for {@value} persistence property.
     *
     * @see <a
     * href="https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#load-graph-semantics">3.7.4.2.
     * Load Graph Semantics</a>
     */
    public static final String PERSISTENCE_LOADGRAPH = "javax.persistence.loadgraph";

    private _PersistenceConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
