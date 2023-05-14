package com.github.jinahya.persistence;

/**
 * A class for integration-testing for {@link Actor} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class ActorIT
        extends _BaseEntityIT<Actor, Integer> {

    ActorIT() {
        super(Actor.class, Integer.class);
    }
}
