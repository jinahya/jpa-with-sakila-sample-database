package com.github.jinahya.sakila.persistence;

import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

abstract class ___BaseEntityTestBase<ENTITY extends __BaseEntity<ID>, ID extends Comparable<? super ID>>
        extends ____BaseEntityTestBase<ENTITY> {

    private static final Logger log = getLogger(lookup().lookupClass());

    ___BaseEntityTestBase(final Class<ENTITY> entityClass, final Class<ID> idClass) {
        super(entityClass);
        this.idClass = requireNonNull(idClass, "idClass is null");
    }

    /**
     * The type of id of the {@link #entityClass}
     */
    final Class<ID> idClass;
}
