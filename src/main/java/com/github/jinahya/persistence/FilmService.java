package com.github.jinahya.persistence;

public class FilmService
        extends _BaseEntityService<Film, Integer> {

    FilmService() {
        super(Film.class, Integer.class);
    }
}
