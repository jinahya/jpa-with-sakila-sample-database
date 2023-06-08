package com.github.jinahya.sakila.data.jpa.repository;

import com.github.jinahya.persistence.sakila.Actor;
import com.github.jinahya.persistence.sakila.Actor_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository
        extends _BaseEntityRepository<Actor, Integer> {

    /**
     * Finds entities by {@link Actor_#lastName lastName} attribute.
     *
     * @param lastName a value of {@link Actor_#lastName lastName} attribute to match.
     * @param pageable pagination info.
     * @return a page of found entities.
     */
    Page<Actor> findAllByLastName(String lastName, Pageable pageable);

    Page<Actor> findAllByActorIdGreaterThan(int actorIdMinExclusive, Pageable pageable);
}
