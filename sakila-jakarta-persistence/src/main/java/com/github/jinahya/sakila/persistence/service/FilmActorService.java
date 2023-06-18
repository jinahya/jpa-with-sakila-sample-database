package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.FilmActor;
import com.github.jinahya.sakila.persistence.FilmActorId;

public class FilmActorService
        extends _BaseEntityService<FilmActor, FilmActorId> {

    FilmActorService() {
        super(FilmActor.class, FilmActorId.class);
    }
}
