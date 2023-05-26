package com.github.jinahya.persistence;

public class FilmCategoryService
        extends _BaseEntityService<FilmCategory, FilmCategoryId> {

    FilmCategoryService() {
        super(FilmCategory.class, FilmCategoryId.class);
    }
}
