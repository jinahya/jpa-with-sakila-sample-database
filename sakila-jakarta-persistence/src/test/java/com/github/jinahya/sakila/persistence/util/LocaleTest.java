package com.github.jinahya.sakila.persistence.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

class LocaleTest {

    private static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void __() {
        final Map<String, List<Locale>> map = Stream.of(Locale.getAvailableLocales())
                .filter(v -> !v.getDisplayCountry(Locale.ENGLISH).isBlank())
                .collect(Collectors.groupingBy(v -> v.getDisplayCountry(Locale.ENGLISH)));
        map.entrySet().stream().filter(e -> e.getValue().size() > 1).forEach(e -> {
            log.debug("{}: {}", e.getKey(), e.getValue());
        });
    }
}
