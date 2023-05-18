package com.github.jinahya.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public final class FilmListQueries {

    public static Optional<FilmList> findByFid(final EntityManager entityManager, final int fid) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var query = entityManager.createNamedQuery("FilmList_findAllByFid", FilmList.class);
        query.setParameter("fid", fid);
        final var list = query.getResultList();
        list.forEach(e -> {
            if (e == null) {
                log.error("nul?????? {} {}", fid, list);
            }
        });
//        final var result = list.stream().findFirst();
        final var result = list.stream().filter(Objects::nonNull).findFirst();
        return result.map(r -> {
            r.setCategories(
                    list.stream()
                            .map(FilmList::getCategory)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet())
            );
            return r;
        });
    }

    private FilmListQueries() {
        throw new AssertionError("instantiation is not allowed");
    }
}
