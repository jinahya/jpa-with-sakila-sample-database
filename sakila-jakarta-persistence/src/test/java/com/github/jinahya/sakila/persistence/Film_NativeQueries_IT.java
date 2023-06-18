package com.github.jinahya.sakila.persistence;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

class Film_NativeQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

    Film_NativeQueries_IT() {
        super();
    }

    @DisplayName(FilmConstants.QUERY_FIND_BY_FILM_ID)
    @Nested
    class FindByFilmIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(FilmConstants.QUERY_FIND_BY_FILM_ID, Film.class)
                                    .setParameter(FilmConstants.PARAMETER_FILM_ID, 0)
                                    .getSingleResult()
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void _NotNull_1() {
            final var filmId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(FilmConstants.QUERY_FIND_BY_FILM_ID, Film.class)
                            .setParameter(FilmConstants.PARAMETER_FILM_ID, filmId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .extracting(Film::getFilmId)
                    .isEqualTo(filmId);
        }
    }

    @DisplayName(FilmConstants.QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var maxResults = 256;
            for (final var i = new AtomicInteger(0); ; ) {
                final var filmIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(FilmConstants.QUERY_FIND_ALL, Film.class)
                                .setParameter(FilmConstants.PARAMETER_FILM_ID_MIN_EXCLUSIVE, filmIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .extracting(Film::getFilmId)
                        .allMatch(v -> v > filmIdMinExclusive)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getFilmId());
            }
        }
    }

    @DisplayName(FilmConstants.QUERY_FIND_ALL_BY_LANGUAGE_ID)
    @Nested
    class FindAllByLanguageIdTest {

        @Test
        void __() {
            final var maxResults = 256;
            final var languageId = 1; // English
            for (final var i = new AtomicInteger(0); ; ) {
                final var filmIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(FilmConstants.QUERY_FIND_ALL_BY_LANGUAGE_ID, Film.class)
                                .setParameter(FilmConstants.PARAMETER_LANGUAGE_ID, languageId)
                                .setParameter(FilmConstants.PARAMETER_FILM_ID_MIN_EXCLUSIVE, filmIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Film::getFilmId))
                        .extracting(Film::getLanguageId)
                        .allMatch(v -> v == languageId);
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getFilmId());
            }
        }
    }

    @DisplayName(FilmConstants.QUERY_FIND_ALL_BY_LANGUAGE)
    @Nested
    class FindAllByLanguageTest {

        @Test
        void __() {
            final var maxResults = 256;
            final var language = Language.ofLanguageId(1); // English
            for (final var i = new AtomicInteger(0); ; ) {
                final var filmIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(FilmConstants.QUERY_FIND_ALL_BY_LANGUAGE, Film.class)
                                .setParameter(FilmConstants.PARAMETER_LANGUAGE, language)
                                .setParameter(FilmConstants.PARAMETER_FILM_ID_MIN_EXCLUSIVE, filmIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Film::getFilmId))
                        .extracting(Film::getLanguage)
                        .extracting(Language::getLanguageId)
                        .allMatch(v -> Objects.equals(v, language.getLanguageId()));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getFilmId());
            }
        }
    }

    @DisplayName(FilmConstants.QUERY_FIND_ALL_BY_ORIGINAL_LANGUAGE_ID)
    @Nested
    class FindAllByOriginalLanguageIdTest {

        @Test
        void __() {
            final var maxResults = 256;
            final var languageId = 1; // English
            for (final var i = new AtomicInteger(0); ; ) {
                final var filmIdMinExclusive = i.get();
                // TODO: implement!
                break;
            }
        }
    }

    @DisplayName(FilmConstants.QUERY_FIND_ALL_BY_ORIGINAL_LANGUAGE)
    @Nested
    class FindAllByOriginalLanguageTest {

        @Test
        void __() {
            final var maxResults = 256;
            final var originalLanguage = Language.ofLanguageId(1); // English
            for (final var i = new AtomicInteger(0); ; ) {
                final var filmIdMinExclusive = i.get();
                // TODO: implement!
                break;
            }
        }
    }

    @DisplayName(FilmConstants.QUERY_FIND_ALL_BY_TITLE)
    @Nested
    class FindAllByTitleTest {

        @DisplayName("('citizen kane')")
        @Test
        void __CitizenKane() {
            final var maxResults = 1;
            final var title = "citizen kane";
            for (final var i = new AtomicInteger(0); ; ) {
                final var filmIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(FilmConstants.QUERY_FIND_ALL_BY_TITLE, Film.class)
                                .setParameter(FilmConstants.PARAMETER_TITLE, title)
                                .setParameter(FilmConstants.PARAMETER_FILM_ID_MIN_EXCLUSIVE, filmIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Film::getFilmId))
                        .extracting(Film::getTitle)
                        .allMatch(v -> v.equalsIgnoreCase(title));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getFilmId());
            }
        }
    }

    @DisplayName(FilmConstants.QUERY_FIND_ALL_BY_TITLE_LIKE)
    @Nested
    class FindAllByTitleLikeTest {

        @DisplayName("('citizen')")
        @Test
        void __Citizen() {
            final var maxResults = 1;
            final var titlePattern = "%citizen%";
            for (final var i = new AtomicInteger(0); ; ) {
                final var filmIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(FilmConstants.QUERY_FIND_ALL_BY_TITLE_LIKE, Film.class)
                                .setParameter(FilmConstants.PARAMETER_TITLE_PATTERN, titlePattern)
                                .setParameter(FilmConstants.PARAMETER_FILM_ID_MIN_EXCLUSIVE, filmIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(comparing(Film::getFilmId))
                        .extracting(Film::getTitle)
                        .allMatch(v -> v.toLowerCase().contains(titlePattern.substring(1, titlePattern.length() - 1)));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getFilmId());
            }
        }
    }
}
