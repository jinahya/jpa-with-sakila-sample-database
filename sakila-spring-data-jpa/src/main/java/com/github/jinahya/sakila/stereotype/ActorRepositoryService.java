package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.persistence.sakila.Actor;
import com.github.jinahya.sakila.data.jpa.repository.ActorRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
class ActorRepositoryService
        extends _BaseEntityRepositoryService<ActorRepository, Actor, Integer> {

    void testEachPage(final @Positive int size, final @NotNull Predicate<? super Page<Actor>> predicate) {
        applyRepositoryInstance(r -> {
            for (var actorIdMinExclusive = 0; ; ) {
                final var page = r.findAllByActorIdGreaterThanOrderByActorIdAsc(actorIdMinExclusive, size);
                if (page.isEmpty() || !predicate.test(page)) {
                    break;
                }
                final var content = page.getContent();
                actorIdMinExclusive = content.get(content.size() - 1).getActorId();
            }
            return null;
        });
    }
}
