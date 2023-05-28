package com.github.jinahya.persistence;

/**
 * A class for uni-testing {@link Actor} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class Actor_Test
        extends _BaseEntityTest<Actor, Integer> {

    /**
     * Creates a new instance.
     */
    Actor_Test() {
        super(Actor.class, Integer.class);
    }
}
