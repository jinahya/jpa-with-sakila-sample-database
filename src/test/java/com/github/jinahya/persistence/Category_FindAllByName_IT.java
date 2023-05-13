package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Category_FindAllByName_IT
        extends _BaseEntityIT<Category, Integer> {

    Category_FindAllByName_IT() {
        super(Category.class, Integer.class);
    }

    @DisplayName("findAllByName(Action)")
    @Test
    void findAllByName_NotEmpty_Action() {
        // GIVEN
        final var name = "Action";
        // WHEN
        final List<Category> found = applyEntityManager(em -> {
            final var query = em.createNamedQuery("Category_findAllByName", Category.class);
            query.setParameter("name", name);
            return query.getResultList();
        });
        // THEN
        assertThat(found)
                .isNotEmpty()
                .doesNotContainNull()
                .extracting(Category::getName)
                .containsOnly(name);
    }

    @DisplayName("findAllByName(Animation)")
    @Test
    void findAllByName_NotEmpty_Animation() {
        // GIVEN
        final var name = "Animation";
        // TODO: implement!
        // WHEN
        // THEN
    }
}
