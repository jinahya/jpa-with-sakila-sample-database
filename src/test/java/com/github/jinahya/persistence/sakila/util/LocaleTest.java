package com.github.jinahya.persistence.sakila.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class LocaleTest {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
