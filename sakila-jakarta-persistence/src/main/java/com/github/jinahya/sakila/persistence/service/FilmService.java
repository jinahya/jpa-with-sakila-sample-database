package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.Film;

public class FilmService
        extends _BaseEntityService<Film, Integer> {

    FilmService() {
        super(Film.class, Integer.class);
    }
}
