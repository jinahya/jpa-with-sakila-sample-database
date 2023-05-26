package com.github.jinahya.persistence;

public class FilmActorService
        extends _BaseEntityService<FilmActor, FilmActorId> {

    FilmActorService() {
        super(FilmActor.class, FilmActorId.class);
    }
}
