package com.github.jinahya.persistence.sakila;

/**
 * A class for unit-testing {@link Store} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see Store_IT
 */
class Store_Test
        extends _BaseEntityTest<Store, Integer> {

    Store_Test() {
        super(Store.class, Integer.class);
    }
}
