package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.Film;

public class FilmService
        extends _BaseEntityService<Film, Integer> {

    FilmService() {
        super(Film.class, Integer.class);
    }
}
