package com.github.jinahya.sakila.data.jpa.repository;

import com.github.jinahya.persistence.sakila.Actor;
import com.github.jinahya.persistence.sakila.Actor_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository
        extends _BaseEntityRepository<Actor, Integer> {

    /**
     * Finds entities whose {@link Actor_#actorId actorId} attributes are greater than specified value.
     *
     * @param actorIdMinExclusive the minimum lower exclusive value of the {@link Actor_#lastName lastName} attribute to
     *                            limit.
     * @param pageable            pagination info.
     * @return a page of found entities.
     */
    Page<Actor> findAllByActorIdGreaterThan(int actorIdMinExclusive, Pageable pageable);

    /**
     * Finds entities whose {@link Actor_#actorId actorId} attributes are greater than specified value, ordered by
     * {@link Actor_#actorId actorId} attribute in ascending order.
     *
     * @param actorIdMinExclusive the minimum lower exclusive value of the {@link Actor_#lastName lastName} attribute to
     *                            limit.
     * @param size                number of entities to retrieve.
     * @return a page of found entities.
     */
    default Page<Actor> findAllByActorIdGreaterThanOrderByActorIdAsc(final int actorIdMinExclusive,
                                                                     final int size) {
        final var sort = Sort.by(Sort.Order.asc(Actor_.actorId.getName()));
        return findAllByActorIdGreaterThan(actorIdMinExclusive, PageRequest.of(0, size, sort));
    }

    /**
     * Finds entities by {@link Actor_#lastName lastName} attribute.
     *
     * @param lastName a value of {@link Actor_#lastName lastName} attribute to match.
     * @param pageable pagination info.
     * @return a page of found entities.
     */
    Page<Actor> findAllByLastName(String lastName, Pageable pageable);

//    default Page<Actor> findAllByLastNameActorIdGreaterThanOrderByActorIdAsc(final int actorIdMinExclusive,
//                                                                     final int page, final int size) {
//        final var sort = Sort.by(Sort.Order.asc(Actor_.actorId.getName()));
//        return findAllByActorIdGreaterThan(actorIdMinExclusive, PageRequest.of(page, size, sort));
//    }
}
