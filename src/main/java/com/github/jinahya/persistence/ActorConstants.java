package com.github.jinahya.persistence;

public final class ActorConstants {

    public static final String NAMED_QUERY_FIND_ALL = "Actor_findAll";

    public static final String NAMED_QUERY_FIND_BY_ACTOR_ID = "Actor_findByActorId";

    public static final String NAMED_QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN = "Actor_findAllByActorIdGreaterThan";

    public static final String NAMED_QUERY_FIND_ALL_BY_LAST_NAME = "Actor_findAllByLastName";

    public static final String NAMED_QUERY_FIND_ALL_BY_LAST_NAME_ACTOR_ID_GREATER_THAN = "Actor_findAllByLastNameActorIdGreaterThan";

    private ActorConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
