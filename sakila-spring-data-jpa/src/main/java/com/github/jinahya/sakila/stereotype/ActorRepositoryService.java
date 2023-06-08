package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.persistence.sakila.Actor;
import com.github.jinahya.persistence.sakila.Actor_;
import com.github.jinahya.sakila.data.jpa.repository.ActorRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
class ActorRepositoryService
        extends _BaseEntityRepositoryService<ActorRepository, Actor, Integer> {

    void testEachPage(@Positive final int size, @NotNull final Predicate<? super Page<Actor>> predicate) {
        applyRepositoryInstance(r -> {
            var actorIdMinExclusive = 0;
            final var sort = Sort.by(Sort.Order.asc(Actor_.actorId.getName()));
            for (var pageable = PageRequest.of(0, size, sort); ; pageable = pageable.next()) {
                final var page = r.findAllByActorIdGreaterThan(actorIdMinExclusive, pageable);
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
