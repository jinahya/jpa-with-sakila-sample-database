package com.github.jinahya.persistence;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public class CategoryService
        extends _BaseEntityService<Category, Integer> {

    protected CategoryService() {
        super(Category.class);
    }

    public List<Category> findAll() {
        return applyEntityManager(
                em -> em.createNamedQuery("Category_findAll", Category.class)
                        .getResultList()
        );
    }

    public List<Category> findAll(@PositiveOrZero final int maxResults) {
        return applyEntityManager(
                em -> em.createNamedQuery("Category_findAll", Category.class)
                        .setMaxResults(maxResults)
                        .getResultList()
        );
    }

    public List<Category> findAllByName(@NotBlank final String name) {
        return applyEntityManager(
                em -> em.createQuery("SELECT e FROM Category AS e WHERE e.name = :name", Category.class)
                        .setParameter("name", name)
                        .getResultList()
        );
    }

    public Category locateByName(@NotBlank final String name) {
        return findAllByName(name)
                .stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElseGet(() -> {
                    final var instance = new Category();
                    instance.setName(name);
                    return persist(instance);
                });
    }
}
