package com.github.jinahya.sakila.persistence.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.Year;
import java.time.YearMonth;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

class TemporalUtilsTest {

    private static final Logger log = getLogger(lookup().lookupClass());

    @Nested
    class ApplyStartEndDateOfYearTest {

        @Test
        void __() {
            TemporalUtils.applyStartEndDateOf(
                    Year.now(),
                    (s, e) -> {
                        log.debug("s: {}", s);
                        log.debug("e: {}", e);
                        return null;
                    }
            );
        }
    }

    @Nested
    class ApplyStartEndDateOfYearMonth {

        @Test
        void __() {
            TemporalUtils.applyStartEndDateOf(
                    YearMonth.now(),
                    (s, e) -> {
                        log.debug("s: {}", s);
                        log.debug("e: {}", e);
                        return null;
                    }
            );
        }
    }

    @Nested
    class ApplyStartEndTimeOfYearTest {

        @Test
        void __() {
            TemporalUtils.applyStartEndTimeOf(
                    Year.now(),
                    (s, e) -> {
                        log.debug("s: {}", s);
                        log.debug("e: {}", e);
                        return null;
                    }
            );
        }
    }

    @Nested
    class ApplyStartEndTimeOfYearMonthTest {

        @Test
        void __() {
            TemporalUtils.applyStartEndTimeOf(
                    YearMonth.now(),
                    (s, e) -> {
                        log.debug("s: {}", s);
                        log.debug("e: {}", e);
                        return null;
                    }
            );
        }
    }
}
