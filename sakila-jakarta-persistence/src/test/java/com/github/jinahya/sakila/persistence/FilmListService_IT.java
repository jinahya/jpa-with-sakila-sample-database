package com.github.jinahya.sakila.persistence;

import com.github.jinahya.sakila.persistence.service.FilmListService;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@AddPackages({___EntityManagerProducer.class, FilmListService.class})
@EnableAutoWeld
class FilmListService_IT {

    @Nested
    class FindByFidTest {

        @Test
        void __1009() {
            final var fid = 1009;
            final var found = filmListService.findByFid(fid);
            Assertions.assertThat(found).hasValueSatisfying(v -> {
                Assertions.assertThat(v.getCategories())
                        .containsOnly("Drama", "Action");
            });
        }
    }

    @Inject
    private FilmListService filmListService;
}
