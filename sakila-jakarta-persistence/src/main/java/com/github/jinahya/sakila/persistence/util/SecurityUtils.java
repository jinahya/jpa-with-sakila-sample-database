package com.github.jinahya.sakila.persistence.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;

/**
 * Utilities related to {@link javax.security} package.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class SecurityUtils {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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

    public static String sha1(final byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes is null");
        return digest("SHA-1", bytes);
    }

    public static String sha2(final byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes is null");
        return digest("SHA-256", bytes);
    }

    private SecurityUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
