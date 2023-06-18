package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.sakila.data.jpa.repository.__BaseEntityRepository;
import com.github.jinahya.sakila.persistence.__BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.function.Function;

abstract class __BaseEntityRepositoryService<
        REPOSITORY extends __BaseEntityRepository<ENTITY, ID>,
        ENTITY extends __BaseEntity<ID>,
        ID extends Comparable<? super ID>
        > {

    /**
     * Applies an autowired instance of {@link REPOSITORY} to specified function, and returns the result.
     *
     * @param function the function to apply.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    <R> R applyRepositoryInstance(final Function<? super REPOSITORY, ? extends R> function) {
        return Objects.requireNonNull(function, "function is null")
                .apply(repositoryInstance);
    }

    @Autowired
    private REPOSITORY repositoryInstance;
}
