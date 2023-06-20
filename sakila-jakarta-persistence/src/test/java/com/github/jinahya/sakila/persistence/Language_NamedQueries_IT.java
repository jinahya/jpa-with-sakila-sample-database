package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jinahya.sakila.persistence.LanguageConstants.PARAMETER_LANGUAGE_ID;
import static com.github.jinahya.sakila.persistence.LanguageConstants.QUERY_FIND_ALL;
import static com.github.jinahya.sakila.persistence.LanguageConstants.QUERY_FIND_BY_LANGUAGE_ID;
import static com.github.jinahya.sakila.persistence.LanguageConstants.QUERY_PARAM_LANGUAGE_ID_MIN_EXCLUSIVE;
import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

class Language_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

    @DisplayName(QUERY_FIND_BY_LANGUAGE_ID)
    @Nested
    class FindByLanguageIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void __0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_LANGUAGE_ID, Language.class)
                                    .setParameter(PARAMETER_LANGUAGE_ID, 0)
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void __1() {
            final var languageId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_LANGUAGE_ID, Language.class)
                            .setParameter(PARAMETER_LANGUAGE_ID, languageId)
                            .getSingleResult()
            );
            assertThat(found)
                    .extracting(Language::getLanguageId)
                    .isEqualTo(languageId);
        }
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var maxResults = ThreadLocalRandom.current().nextInt(16, 32);
            for (final var i = new AtomicInteger(0); ; ) {
                final var languageIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL, Language.class)
                                .setParameter(QUERY_PARAM_LANGUAGE_ID_MIN_EXCLUSIVE, languageIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .extracting(Language::getLanguageId)
                        .isSorted()
                        .allMatch(v -> v > languageIdMinExclusive);
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getLanguageId());
            }
        }
    }
}
