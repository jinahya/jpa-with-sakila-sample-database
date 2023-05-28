package com.github.jinahya.persistence;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service class related to {@link Actor} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
public class ActorService
        extends _BaseEntityService<Actor, Integer> {

    /**
     * Creates a new instance.
     */
    ActorService() {
        super(Actor.class, Integer.class);
    }

    @Override
    // HV000131: A method return value must not be marked for cascaded validation more than once in a class hierarchy, ...
    public List<Actor> findAll(@Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            return applyConnection(c -> {
                final var builder = new StringBuilder("SELECT * FROM " + Actor.TABLE_NAME);
                if (maxResults != null) {
                    builder.append(" LIMIT ?");
                }
                log.debug("sql: {}", builder);
                try (var statement = c.prepareStatement(builder.toString())) {
                    if (maxResults != null) {
                        statement.setInt(1, maxResults);
                    }
                    final var list = new ArrayList<Actor>();
                    try (var resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            list.add(____Utils.bind(Actor.class, resultSet));
                        }
                    }
                    return list;
                } catch (final SQLException sqle) {
                    throw new RuntimeException("failed to findAll", sqle);
                }
            });
        }
        if (current().nextBoolean()) {
            return super.findAll(maxResults);
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(ActorConstants.QUERY_FIND_ALL, Actor.class);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }

    /**
     * Finds the entity whose value of {@link Actor_#actorId actorId} attribute matches specified value.
     *
     * @param actorId the value of the {@link Actor_#actorId actorId} attribute to match.
     * @return an optional of found entity; {@code empty} if not found.
     */
    @NotNull
    public Optional<@Valid @NotNull Actor> findByActorId(@Positive final int actorId) {
        if (current().nextBoolean()) {
            return applyConnection(c -> {
                final String sql = "SELECT *" +
                                   " FROM " + Actor.TABLE_NAME +
                                   " WHERE " + Actor.COLUMN_NAME_ACTOR_ID + " = ?";
                log.debug("sql: {}", sql);
                try (var statement = c.prepareStatement(sql)) {
                    statement.setInt(1, actorId);
                    try (var resultSet = statement.executeQuery()) {
                        if (!resultSet.next()) {
                            return Optional.empty();
                        }
                        return Optional.of(____Utils.bind(Actor.class, resultSet));
                    }
                } catch (final SQLException sqle) {
                    throw new RuntimeException("failed to findById(" + actorId + ")", sqle);
                }
            });
        }
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

    /**
     * Finds all entities whose values of {@link Actor_#actorId actorId} attribute is greater than specified value.
     *
     * @param actorIdMinExclusive the lower exclusive value of the {@link Actor_#actorId actorId} attribute to compare.
     * @param maxResults          a number of maximum results to limit; {@code null} for an unlimited results.
     * @return a list of found entities.
     */
    @NotNull
    public List<@Valid @NotNull Actor> findAllByActorIdGreaterThan(@PositiveOrZero final int actorIdMinExclusive,
                                                                   @Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            return applyConnection(c -> {
                final var builder = new StringBuilder(
                        "SELECT * " +
                        " FROM " + Actor.TABLE_NAME +
                        " WHERE " + Actor.COLUMN_NAME_ACTOR_ID + " > ?" +
                        " ORDER BY " + Actor.COLUMN_NAME_ACTOR_ID + " ASC");
                if (maxResults != null) {
                    builder.append(" LIMIT ?");
                }
                log.debug("sql: {}", builder);
                try (var statement = c.prepareStatement(builder.toString())) {
                    var index = 0;
                    statement.setInt(++index, actorIdMinExclusive);
                    if (maxResults != null) {
                        statement.setInt(++index, maxResults);
                    }
                    try (var resultSet = statement.executeQuery()) {
                        final var list = new ArrayList<Actor>();
                        while (resultSet.next()) {
                            list.add(____Utils.bind(Actor.class, resultSet));
                        }
                        return list;
                    }
                } catch (final SQLException sqle) {
                    throw new RuntimeException(
                            "failed to findAllByActorIdGreaterThan(" + actorIdMinExclusive + ", " + maxResults + ")",
                            sqle);
                }
            });
        }
        if (current().nextBoolean()) {
            return findAllByIdGreaterThan(
                    r -> r.get(Actor_.actorId),
                    actorIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(
                    ActorConstants.QUERY_FIND_ALL_BY_ACTOR_ID_GREATER_THAN,
                    Actor.class
            );
            query.setParameter("actorIdMinExclusive", actorIdMinExclusive);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }

    /**
     * Finds the entity whose value of {@link Actor_#lastName lastName} attribute matches specified value.
     *
     * @param lastName   the value of {@link Actor_#lastName lastName} attribute to match.
     * @param maxResults a number of maximum results to limit; {@code null} for an unlimited results.
     * @return a list of found entities;
     */
    @NotNull
    public List<@Valid @NotNull Actor> findAllByLastName(@NotBlank final String lastName,
                                                         @Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            return applyConnection(c -> {
                final var builder = new StringBuilder(
                        "SELECT * " +
                        " FROM " + Actor.TABLE_NAME +
                        " WHERE " + Actor.COLUMN_NAME_LAST_NAME + " = ?");
                if (maxResults != null) {
                    builder.append(" LIMIT ?");
                }
                log.debug("sql: {}", builder);
                try (var statement = c.prepareStatement(builder.toString())) {
                    var index = 0;
                    statement.setString(++index, lastName);
                    if (maxResults != null) {
                        statement.setInt(++index, maxResults);
                    }
                    try (var resultSet = statement.executeQuery()) {
                        final var list = new ArrayList<Actor>();
                        while (resultSet.next()) {
                            list.add(____Utils.bind(Actor.class, resultSet));
                        }
                        return list;
                    }
                } catch (final SQLException sqle) {
                    throw new RuntimeException(
                            "failed to findAllByLastName(" + lastName + ", " + maxResults + ")",
                            sqle);
                }
            });
        }
        if (current().nextBoolean()) {
            return findAllByAttribute(
                    r -> r.get(Actor_.lastName),
                    lastName,
                    maxResults
            );
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(
                    ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME,
                    Actor.class
            );
            query.setParameter(Actor_.lastName.getName(), lastName);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }

    /**
     * Finds all entities whose values of {@link Actor_#lastName lastName} attribute match specified value and whose
     * values of {@link Actor_#actorId} attribute are greater than specified value, sorted by
     * {@link Actor_#actorId actorId} attribute in ascending order.
     *
     * @param lastName            the value of {@link Actor_#lastName lastName} attribute to match.
     * @param actorIdMinExclusive the lower exclusive value of {@link Actor_#actorId actorId} attribute.
     * @param maxResults          a number of maximum results to limit; {@code null} for an unlimited results.
     * @return a list of found entities;
     */
    @NotNull
    public List<@Valid @NotNull Actor> findAllByLastNameActorIdGreaterThan(
            @NotBlank final String lastName, @PositiveOrZero final int actorIdMinExclusive,
            @Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            return applyConnection(c -> {
                final var builder = new StringBuilder(
                        "SELECT *" +
                        " FROM " + Actor.TABLE_NAME +
                        " WHERE " + Actor.COLUMN_NAME_LAST_NAME + " = ?" +
                        " AND " + Actor.COLUMN_NAME_ACTOR_ID + " > ? " +
                        " ORDER BY " + Actor.COLUMN_NAME_ACTOR_ID + " ASC");
                if (maxResults != null) {
                    builder.append(" LIMIT ?");
                }
                log.debug("sql: {}", builder);
                try (var statement = c.prepareStatement(builder.toString())) {
                    var index = 0;
                    statement.setString(++index, lastName);
                    statement.setInt(++index, actorIdMinExclusive);
                    if (maxResults != null) {
                        statement.setInt(++index, maxResults);
                    }
                    try (var resultSet = statement.executeQuery()) {
                        final var list = new ArrayList<Actor>();
                        while (resultSet.next()) {
                            list.add(____Utils.bind(Actor.class, resultSet));
                        }
                        return list;
                    }
                } catch (final SQLException sqle) {
                    throw new RuntimeException(
                            "failed to findAllByLastName("
                            + lastName + ", " + actorIdMinExclusive + ", " + maxResults + ")",
                            sqle);
                }
            });
        }
        if (current().nextBoolean()) {
            return findAllByAttributeIdGreaterThan(
                    r -> r.get(Actor_.lastName),
                    lastName,
                    r -> r.get(Actor_.actorId),
                    actorIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(em -> {
            final var query = em.createNamedQuery(
                    ActorConstants.QUERY_FIND_ALL_BY_LAST_NAME_ACTOR_ID_GREATER_THAN,
                    Actor.class
            );
            query.setParameter(Actor_.lastName.getName(), lastName);
            query.setParameter("actorIdMinExclusive", actorIdMinExclusive);
            if (maxResults != null) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        });
    }
}
