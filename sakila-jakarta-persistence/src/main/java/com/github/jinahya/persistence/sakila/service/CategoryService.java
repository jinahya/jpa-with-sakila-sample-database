package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class CategoryService
        extends _BaseEntityService<Category, Integer> {

    /**
     * Creates a new instance.
     */
    CategoryService() {
        super(Category.class, Integer.class);
    }

    /**
     * Selects all categories.
     *
     * @return a list of all categories.
     */
    @NotNull
    public List<@Valid @NotNull Category> findAll() {
        return applyEntityManager(
                em -> em.createNamedQuery("Category_findAll", Category.class)
                        .getResultList()
        );
    }

    @NotNull
    public List<@Valid @NotNull Category> findAll(@Positive final int maxResults) {
        return applyEntityManager(
                em -> em.createNamedQuery("Category_findAll", Category.class)
                        .setMaxResults(maxResults)
                        .getResultList()
        );
    }

    public void findAll(@Positive final int maxResults, @NotNull final Predicate<? super List<Category>> predicate,
                        @PositiveOrZero final int startingCategoryIdMinExclusive) {
        for (final var i = new AtomicInteger(startingCategoryIdMinExclusive); ; ) {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery("Category_findAllByCategoryIdGreaterThan", Category.class)
                            .setParameter("categoryIdMinExclusive", i.get())
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            if (!predicate.test(list) || list.isEmpty()) {
                break;
            }
            i.set(i.get() + list.get(list.size() - 1).getCategoryId());
        }
    }

    public void findAll(@Positive final int maxResults, @NotNull final Predicate<? super List<Category>> predicate) {
        findAll(maxResults, predicate, 0);
    }

    @NotNull
    public List<@Valid @NotNull Category> findAllByName(@NotBlank final String name) {
        return applyEntityManager(
                em -> em.createQuery("SELECT e FROM Category AS e WHERE e.name = :name", Category.class)
                        .setParameter("name", name)
                        .getResultList()
        );
    }

    @Valid
    @NotNull
    public Category locateByName(@NotBlank final String name) {
        return findAllByName(name)
                .stream()
                .findFirst()
                .orElseGet(() -> persist(Category.of(name)))
                ;
    }
}
