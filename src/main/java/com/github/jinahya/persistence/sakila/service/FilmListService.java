package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.FilmList;
import com.github.jinahya.persistence.sakila.util.____Utils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.Optional;

@ApplicationScoped
public class FilmListService
        extends ___PersistenceService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public Optional<@Valid FilmList> findByFid(final int fid) {
        return applyConnection(c -> {
            try (var statement = c.prepareStatement(
                    "SELECT * FROM " + FilmList.VIEW_NAME + " WHERE " + FilmList.COLUMN_NAME_FID + " = ?")) {
                statement.setInt(1, fid);
                try (var result = statement.executeQuery()) {
                    if (!result.next()) {
                        return Optional.empty();
                    }
                    final var instance = new FilmList();
                    for (final var field : ____Utils.getFieldsAnnotatedWithColumn(FilmList.class)) {
                        if (!field.canAccess(instance)) {
                            field.setAccessible(true);
                        }
                        final var value = result.getObject(field.getAnnotation(Column.class).name());
                        field.set(instance, value);
                    }
                    while (result.next()) {
                        final var category = result.getString(FilmList.COLUMN_NAME_CATEGORY);
                        if (category != null && !category.isBlank()) {
                            instance.getCategories().add(category.strip());
                        }
                    }
                    return Optional.of(instance);
                }
            } catch (SQLException | ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
