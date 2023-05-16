package com.github.jinahya.persistence;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnitUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.Objects;
import java.util.function.Function;

final class _PersistenceUtils {

    static <R> R applyEntityManagerFactory(final Function<? super EntityManagerFactory, ? extends R> function) {
        try (var emf = Persistence.createEntityManagerFactory(_PersistenceConstants.PERSISTENCE_UNIT_NAME)) {
            return function.apply(emf);
        }
    }

    static <R> R applyPersistenceUnitUtil(final Function<? super PersistenceUnitUtil, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return applyEntityManagerFactory(emf -> function.apply(emf.getPersistenceUnitUtil()));
    }

    static Object getIdentifier(final __BaseEntity<?> entity) {
        return applyPersistenceUnitUtil(puu -> {
            if (!puu.isLoaded(entity)) {
                return null;
            }
            return puu.getIdentifier(entity);
        });
    }

    private static String digest(final String algorithm, final byte[] bytes) {
        Objects.requireNonNull(algorithm, "algorithm is null");
        Objects.requireNonNull(bytes, "bytes is null");
        try {
            final var digest = MessageDigest.getInstance(algorithm).digest(bytes);
            return HexFormat.of().withLowerCase().formatHex(digest);
        } catch (final NoSuchAlgorithmException nsae) {
            throw new RuntimeException(nsae);
        }
    }

    static String sha1(final byte[] password) {
        if (Objects.requireNonNull(password, "password is null").length == 0) {
            throw new IllegalArgumentException("empty password");
        }
        return digest("SHA-1", password);
    }

    static String sha2(final byte[] password) {
        if (Objects.requireNonNull(password, "password is null").length == 0) {
            throw new IllegalArgumentException("empty password");
        }
        return digest("SAH-256", password);
    }

    private _PersistenceUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
