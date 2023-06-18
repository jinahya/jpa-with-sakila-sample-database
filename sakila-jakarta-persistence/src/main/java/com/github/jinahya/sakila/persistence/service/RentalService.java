package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.Rental;
import com.github.jinahya.sakila.persistence.RentalConstants;
import com.github.jinahya.sakila.persistence.Rental_;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service class for {@link Rental} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class RentalService
        extends _BaseEntityService<Rental, Integer> {

    /**
     * Creates a new instance.
     */
    RentalService() {
        super(Rental.class, Integer.class);
    }

    // -------------------------------------------------------------------------------------------------------- rentalId

    /**
     * Finds the entity identified by specified value of {@link Rental_#rentalId rentalId} attribute.
     *
     * @param rentalId the value of the {@link Rental_#rentalId rentalId} attribute to match.
     * @return an optional of found entity; may be {@link Optional#isEmpty() empty} if not found.
     */
    public Optional<@Valid Rental> findByRentalId(final @Positive int rentalId) {
        if (current().nextBoolean()) {
            return findById(rentalId);
        }
        return ofNullable(
                applyEntityManager(em -> {
                    try {
                        return em.createNamedQuery(RentalConstants.QUERY_FIND_BY_RENTAL_ID, Rental.class)
                                .setParameter(RentalConstants.PARAMETER_RENTAL_ID, rentalId)
                                .getSingleResult();
                    } catch (final NoResultException nre) {
                        return null;
                    }
                })
        );
    }

    public @NotNull List<@Valid @NotNull Rental> findAll(final @PositiveOrZero int rentalIdMinExclusive,
                                                         final @Positive int maxResults) {
        if (current().nextBoolean()) {
            return findAll(
                    r -> r.get(Rental_.rentalId),
                    rentalIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(
                em -> em.createNamedQuery(RentalConstants.QUERY_FIND_ALL, Rental.class)
                        .setParameter(RentalConstants.PARAMETER_RENTAL_ID_MIN_EXCLUSIVE, rentalIdMinExclusive)
                        .setMaxResults(maxResults)
                        .getResultList()
        );
    }

    // ------------------------------------------------------------------------------------------------------ rentalDate
    private Predicate rentalDateIn(final CriteriaBuilder b, Path<? extends Rental> r, final LocalDate dateOfRentalDate,
                                   final Integer rentalIdMinExclusive) {
        final var predicate = ofNullable(rentalIdMinExclusive)
                .map(v -> b.greaterThan(r.get(Rental_.rentalId), v)) // e.rentalId > rentalIdMinExclusive
                .orElseGet(b::conjunction);                  // TRUE
        return b.and(
                ___PersistenceServiceUtils.dateTimeIn(b, r.get(Rental_.rentalDate), dateOfRentalDate),       // WHERE e.rentalDate > s AND e.rentalDate < e
                predicate                                                 //   AND ...
        );
    }

    @PositiveOrZero long countByRentalDateIn(final @NotNull LocalDate dateOfRentalDate) {
        return applyEntityManager(em -> {
            final var b = em.getCriteriaBuilder();
            final var q = b.createQuery(Long.class); // !!!
            final var r = q.from(Rental.class);                                                      // FROM Rental AS e
            q.select(b.count(r));                                                                    // SELECT COUNT(e)
            q.where(rentalDateIn(b, r, dateOfRentalDate, null));                                     // WHERE ...
            return em.createQuery(q).getSingleResult();
        });
    }

    @NotNull List<@Valid @NotNull Rental> findAllByRentalDateIn(final @NotNull LocalDate dateOfRentalDate,
                                                                final @PositiveOrZero int rentalIdMinExclusive,
                                                                final @Positive int maxResults) {
        return applyEntityManager(em -> {
            final var b = em.getCriteriaBuilder();
            final var q = b.createQuery(Rental.class);
            final var r = q.from(Rental.class);                                               // FROM Rental AS e
            q.select(r);                                                                      // SELECT e
            q.where(rentalDateIn(b, r, dateOfRentalDate, rentalIdMinExclusive));              // WHERE ...
            q.orderBy(b.asc(r.get(Rental_.rentalId)));                                                // ORDER BY e.rentalId ASC
            return em.createQuery(q).setMaxResults(maxResults).getResultList();
        });
    }

    private Predicate rentalDateIn(final CriteriaBuilder b, Path<? extends Rental> r, final YearMonth monthOfRentalDate,
                                   final Integer rentalIdMinExclusive) {
        final var predicate = ofNullable(rentalIdMinExclusive)
                .map(v -> b.greaterThan(r.get(Rental_.rentalId), v))  // e.rentalId > rentalIdMinExclusive
                .orElseGet(b::conjunction);                   // TRUE
        return b.and(
                ___PersistenceServiceUtils.dateTimeIn(b, r.get(Rental_.rentalDate), monthOfRentalDate),      // WHERE e.rentalDate > s AND e.rentalDate < e
                predicate                                                 //   AND ...
        );
    }

    @PositiveOrZero long countByRentalDateIn(final @NotNull YearMonth monthOfRentalDate) {
        return applyEntityManager(em -> {
            final var b = em.getCriteriaBuilder();
            final var q = b.createQuery(Long.class);
            final var r = q.from(Rental.class);                                                      // FROM Rental AS e
            q.select(b.count(r));                                                                    // SELECT COUNT(e)
            q.where(rentalDateIn(b, r, monthOfRentalDate, null));                                    // WHERE ...
            return em.createQuery(q).getSingleResult();
        });
    }

    @NotNull List<@Valid @NotNull Rental> findAllByRentalDateIn(final @NotNull YearMonth monthOfRentalDate,
                                                                final @PositiveOrZero int rentalIdMinExclusive,
                                                                final @Positive int maxResults) {
        return applyEntityManager(em -> {
            final var b = em.getCriteriaBuilder();
            final var q = b.createQuery(Rental.class);
            final var r = q.from(Rental.class);                                               // FROM Rental AS e
            q.select(r);                                                                      // SELECT e
            q.where(rentalDateIn(b, r, monthOfRentalDate, rentalIdMinExclusive));             // WHERE ...
            q.orderBy(b.asc(r.get(Rental_.rentalId)));                                                // ORDER BY e.rentalId ASC
            return em.createQuery(q).setMaxResults(maxResults).getResultList();
        });
    }

    private Predicate rentalDateIn(final CriteriaBuilder b, Path<? extends Rental> r, final Year yearOfRentalDate,
                                   final Integer rentalIdMinExclusive) {
        final var predicate = ofNullable(rentalIdMinExclusive)
                .map(v -> b.greaterThan(r.get(Rental_.rentalId), v))  // e.rentalId > rentalIdMinExclusive
                .orElseGet(b::conjunction);                   // TRUE
        return b.and(
                ___PersistenceServiceUtils.dateTimeIn(b, r.get(Rental_.rentalDate), yearOfRentalDate),       // WHERE e.rentalDate > s AND e.rentalDate < e
                predicate                                                 //   AND ...
        );
    }

    @PositiveOrZero long countByRentalDateIn(final @NotNull Year yearOfRentalDate) {
        return applyEntityManager(em -> {
            final var b = em.getCriteriaBuilder();
            final var q = b.createQuery(Long.class);
            final var r = q.from(Rental.class);                                                      // FROM Rental AS e
            q.select(b.count(r));                                                                    // SELECT COUNT(e)
            q.where(rentalDateIn(b, r, yearOfRentalDate, null));                                     // WHERE ...
            return em.createQuery(q).getSingleResult();
        });
    }

    @NotNull List<@Valid @NotNull Rental> findAllByRentalDateIn(final @NotNull Year yearOfRentalDate,
                                                                final @PositiveOrZero int rentalIdMinExclusive,
                                                                final @Positive int maxResults) {
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var query = builder.createQuery(Rental.class);
            final var root = query.from(Rental.class);                                        // FROM Rental AS e
            query.select(root);                                                               // SELECT e
            query.where(rentalDateIn(builder, root, yearOfRentalDate, rentalIdMinExclusive)); // WHERE ...
            query.orderBy(builder.asc(root.get(Rental_.rentalId)));                                   // ORDER BY e.rentalId ASC
            return em.createQuery(query).setMaxResults(maxResults).getResultList();
        });
    }
}
