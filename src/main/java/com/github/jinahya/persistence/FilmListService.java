package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Optional;

@ApplicationScoped
@Slf4j
public class FilmListService {

    public Optional<FilmList> findByFid(final int fid) {
        return _EntityManagerUtils.applyConnection(entityManager, c -> {
            try (var statement = c.prepareStatement(
                    "SELECT * FROM " + FilmList.VIEW_NAME + " WHERE " + FilmList.COLUMN_NAME_FID + " = ?")) {
                statement.setInt(1, fid);
                try (var result = statement.executeQuery()) {
                    if (!result.next()) {
                        return Optional.empty();
                    }
                    final var instance = new FilmList();
                    for (final var field : _ReflectionUtils.getFieldsAnnotatedWithColumn(FilmList.class)) {
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

    @Inject
    private EntityManager entityManager;
}
