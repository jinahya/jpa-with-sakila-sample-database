package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.FilmActor;
import com.github.jinahya.persistence.sakila.FilmActorId;

public class FilmActorService
        extends _BaseEntityService<FilmActor, FilmActorId> {

    FilmActorService() {
        super(FilmActor.class, FilmActorId.class);
    }
}
