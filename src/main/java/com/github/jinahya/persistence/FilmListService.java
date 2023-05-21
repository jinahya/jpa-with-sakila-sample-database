package com.github.jinahya.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@ApplicationScoped
@Slf4j
public class FilmListService {

    public Optional<FilmList_> findByFid(final int fid) throws SQLException {
        final var connection = entityManager.unwrap(Connection.class);
        try (var statement = connection.prepareStatement(
                "SELECT * FROM " + FilmList.VIEW_NAME + " WHERE " + FilmList.COLUMN_NAME_FID + " = ?")) {
            statement.setInt(1, fid);
            try (var result = statement.executeQuery()) {
                while (result.next()) {
                    break;
                }
            }
        }
        return null;
    }

    @Inject
    private EntityManager entityManager;
}
