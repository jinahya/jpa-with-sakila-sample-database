package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.FilmCategory;
import com.github.jinahya.persistence.sakila.FilmCategoryId;

public class FilmCategoryService
        extends _BaseEntityService<FilmCategory, FilmCategoryId> {

    FilmCategoryService() {
        super(FilmCategory.class, FilmCategoryId.class);
    }
}
