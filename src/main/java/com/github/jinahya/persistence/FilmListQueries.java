package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class FilmListQueries {

    public static Optional<FilmList> findByFid(final EntityManager entityManager, final int fid) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var query = entityManager.createNamedQuery("FilmList_findAllByFid", FilmList.class);
        query.setParameter("fid", fid);
        final var list = query.getResultList();
        final var result = list.stream().findFirst();
        result.map(r -> {
            r.setCategories(
                    list.stream()
                            .map(FilmList::getCategory)
                            .collect(Collectors.toSet())
            );
            return r;
        });
        return result;
    }

    private FilmListQueries() {
        throw new AssertionError("instantiation is not allowed");
    }
}
