package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.FilmCategory;
import com.github.jinahya.sakila.persistence.FilmCategoryId;

public class FilmCategoryService
        extends _BaseEntityService<FilmCategory, FilmCategoryId> {

    FilmCategoryService() {
        super(FilmCategory.class, FilmCategoryId.class);
    }
}
