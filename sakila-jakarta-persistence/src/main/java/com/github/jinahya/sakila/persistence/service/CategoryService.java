package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.Category;
import com.github.jinahya.sakila.persistence.CategoryConstants;
import com.github.jinahya.sakila.persistence.Category_;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Optional;

import static java.util.concurrent.ThreadLocalRandom.current;

public class CategoryService
        extends _BaseEntityService<Category, Integer> {

    /**
     * Creates a new instance.
     */
    CategoryService() {
        super(Category.class, Integer.class);
    }

    /**
     * Finds the entity whose value of {@link Category_#categoryId categoryId} attribute matches specified value.
     *
     * @param categoryId the value of the {@link Category_#categoryId categoryId} attribute to match.
     * @return an optional of found entity; {@link Optional#empty() empty} if not found.
     */
    public Optional<@Valid Category> findByCategoryIdId(final @Positive int categoryId) {
        if (current().nextBoolean()) {
            return super.findById(categoryId);
        }
        return Optional.ofNullable(
                applyEntityManager(em -> {
                    try {
                        return em.createNamedQuery(CategoryConstants.QUERY_FIND_BY_CATEGORY_ID, Category.class)
                                .setParameter(CategoryConstants.PARAMETER_CATEGORY_ID, categoryId)
                                .getSingleResult(); // NoResultException
                    } catch (final NoResultException nre) {
                        return null;
                    }
                })
        );
    }

    /**
     * Finds all categories whose values of {@link Category_#categoryId categoryId} attribute is less than specified
     * value, ordered by the {@link Category_#categoryId categoryId} attribute in ascending order.
     *
     * @param categoryIdMinExclusive the lower exclusive value of the {@link Category_#categoryId category} to limit.
     * @param maxResults             the maximum number of results to limit.
     * @return a list of all categories.
     */
    public @NotNull List<@Valid @NotNull Category> findAll(final @PositiveOrZero int categoryIdMinExclusive,
                                                           final @Positive int maxResults) {
        if (current().nextBoolean()) {
            return findAll(
                    r -> r.get(Category_.categoryId),
                    categoryIdMinExclusive,
                    maxResults
            );
        }
        return applyEntityManager(
                em -> em.createNamedQuery(CategoryConstants.QUERY_FIND_ALL, Category.class)
                        .setParameter(CategoryConstants.PARAMETER_CATEGORY_ID_MIN_EXCLUSIVE, categoryIdMinExclusive)
                        .setMaxResults(maxResults)
                        .getResultList()
        );
    }

    @NotNull
    List<@Valid @NotNull Category> findAllByName(final @PositiveOrZero int categoryIdMinExclusive,
                                                 final @NotBlank String name,
                                                 final @Positive int maxResults) {
        if (current().nextBoolean()) {
            return findAllBy(
                    r -> r.get(Category_.categoryId),
                    categoryIdMinExclusive,
                    maxResults,
                    r -> r.get(Category_.name),
                    name
            );
        }
        return applyEntityManager(
                em -> em.createQuery(
                                """
                                        SELECT e
                                        FROM Category AS e
                                        WHERE e.name = :name
                                              AND e.categoryId > :categoryIdMinExclusive
                                        ORDER BY e.categoryId ASC""",
                                Category.class
                        )
                        .setParameter(CategoryConstants.PARAMETER_CATEGORY_ID_MIN_EXCLUSIVE, categoryIdMinExclusive)
                        .setParameter("name", name)
                        .setMaxResults(maxResults)
                        .getResultList()
        );
    }

    @NotNull
    List<@Valid @NotNull Category> findAllByName(final @NotBlank String name,
                                                 final @Positive int maxResults) {
        return findAllByName(0, name, maxResults);
    }

    /**
     * Locate the first, or a newly persisted, entity whose {@link Category_#name name} attribute matches to specified
     * values.
     *
     * @param name the value of {@link Category_#name name} attribute to match.
     * @return the entity for {@code name}.
     */
    @Valid
    @NotNull
    public Category locateByName(final @NotBlank String name) {
        return findAllByName(name, 1)
                .stream()
                .findFirst()
                .orElseGet(() -> persist(Category.of(name)))
                ;
    }
}
