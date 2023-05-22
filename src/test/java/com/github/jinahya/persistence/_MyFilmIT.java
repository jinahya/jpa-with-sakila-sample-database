package com.github.jinahya.persistence;

import org.junit.jupiter.api.Test;

import java.util.Locale;

class _MyFilmIT
        extends _PersistenceIT {

    @Test
    void 나에게오라() {
        final var title = "나에게 오라";
        final var description = "서울에서 이용만 당하고 내려온 춘근(박상민)은 역전 여관에서 옥희(윤수진)를 만나고 서로에게 관심을 갖는다.";
        final var releaseYear = 1996;
        final var film = new Film_Randomizer().getRandomValue();
        final var languageLocale = Locale.KOREA;
        film.setTitle(title);
        film.setDescription(description);
        film.setReleaseYear(releaseYear);
//        film.setLanguage();
    }
}
