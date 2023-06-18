package com.github.jinahya.sakila.persistence.service;

import com.github.jinahya.sakila.persistence.Actor;
import com.github.jinahya.sakila.persistence.Film;
import com.github.jinahya.sakila.persistence.FilmActor;
import com.github.jinahya.sakila.persistence.FilmCategory;
import com.github.jinahya.sakila.persistence.Film_Randomizer;
import com.github.jinahya.sakila.persistence.__PersistenceIT;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Locale;

class _MyFilmIT
        extends __PersistenceIT {

    /**
     * 영화 '나에게 오라 (1996, 김영빈)' 대한 정보를 추가한다.
     *
     * @see <a href="https://movie.daum.net/moviedb/main?movieId=196">나에게 오라 (1996, 김영빈)</a>
     */
    @Test
    void 나에게오라() {
        applyEntityManager(em -> {
            final var film = new Film_Randomizer().getRandomValue();
            {
                film.setTitle("나에게 오라");
                film.setDescription("서울에서 이용만 당하고 내려온 춘근(박상민)은 역전 여관에서 옥희(윤수진)를 만나고 서로에게 관심을 갖는다.");
                film.setReleaseYear(1996);
                film.setLanguage(languageService.locateByLocale(Locale.KOREAN));
                film.setOriginalLanguage(null);
                film.setLengthAsDuration(Duration.ofMinutes(91));
                film.setRating(Film.Rating.NC_17);
                film.setSpecialFeatures(null);
                filmService.persist(film);
            }
            {
                final var actor = actorService.persist(Actor.of("상민", "박"));
                final var filmActor = filmActorService.persist(FilmActor.of(actor, film));
            }
            {
                final var actor = actorService.persist(Actor.of("정현", "김"));
                final var filmActor = filmActorService.persist(FilmActor.of(actor, film));
            }
            {
                final var actor = actorService.persist(Actor.of("민수", "최"));
                final var filmActor = filmActorService.persist(FilmActor.of(actor, film));
            }
            {
                final var category = categoryService.locateByName("액션");
                final var filmCategory = filmCategoryService.persist(FilmCategory.of(film, category));
            }
            return null;
        });
    }

    @Inject
    private ActorService actorService;

    @Inject
    private CategoryService categoryService;

    @Inject
    private FilmService filmService;

    @Inject
    private FilmActorService filmActorService;

    @Inject
    private FilmCategoryService filmCategoryService;

    @Inject
    private LanguageService languageService;
}
