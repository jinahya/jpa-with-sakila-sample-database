package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Actor;
import com.github.jinahya.persistence.sakila.ActorConstants;
import com.github.jinahya.persistence.sakila.Actor_;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.github.jinahya.persistence.sakila.ActorConstants.PARAMETER_ACTOR_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila.ActorConstants.QUERY_FIND_ALL;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * A service class related to {@link Actor} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class ActorService
        extends _BaseEntityService<Actor, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    /**
     * Creates a new instance.
     */
    ActorService() {
        super(Actor.class, Integer.class);
    }

    public @NotNull List<@Valid @NotNull Actor> findAll(final @PositiveOrZero int actorIdMinExclusive,
                                                        final @Positive int maxResults) {
        if (current().nextBoolean()) {
            return findAll(
                    r -> r.get(Actor_.actorId),
                    actorIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(
                em -> em.createNamedQuery(QUERY_FIND_ALL, Actor.class)
                        .setParameter(PARAMETER_ACTOR_ID_MIN_EXCLUSIVE, actorIdMinExclusive)
                        .setMaxResults(maxResults)
                        .getResultList()
        );
    }

    /**
     * Finds the entity whose value of {@link Actor_#actorId actorId} attribute matches specified value.
     *
     * @param actorId the value of the {@link Actor_#actorId actorId} attribute to match.
     * @return an optional of found entity; {@code empty} if not found.
     */
    public Optional<@Valid Actor> findByActorId(@Positive final int actorId) {
        if (current().nextBoolean()) {
            return findById(actorId);
        }
        return Optional.ofNullable(
                applyEntityManager(em -> {
                    try {
                        return em.createNamedQuery(
                                        ActorConstants.QUERY_FIND_BY_ACTOR_ID,
                                        Actor.class
                                )
                                .setParameter(Actor_.actorId.getName(), actorId)
                                .getSingleResult(); // NoResultException
                    } catch (final NoResultException nre) {
                        return null;
                    }
                })
        );
    }

    public @NotNull List<@Valid @NotNull Actor> findAllByLastName(final @PositiveOrZero int actorIdMinExclusive,
                                                                  final @Positive int maxResults,
                                                                  final @NotBlank String lastName) {
        if (current().nextBoolean()) {
            return findAllBy(
                    r -> r.get(Actor_.actorId),
                    actorIdMinExclusive,
                    maxResults,
                    r -> r.get(Actor_.lastName),
                    lastName
            );
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(
                    ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME,
                    Actor.class
            );
            query.setParameter(ActorConstants.PARAMETER_LAST_NAME, lastName);
            query.setMaxResults(maxResults);
            return query.getResultList();
        });
    }
}
