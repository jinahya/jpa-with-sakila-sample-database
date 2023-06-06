package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.service.FilmListService;
import jakarta.inject.Inject;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@AddPackages({___EntityManagerProducer.class, FilmListService.class})
@EnableAutoWeld
class FilmListService_IT {

    @Nested
    class FindByFidTest {

        @Test
        void __1009() {
            final var fid = 1009;
            final var found = filmListService.findByFid(fid);
            assertThat(found).hasValueSatisfying(v -> {
                assertThat(v.getCategories())
                        .containsOnly("Drama", "Action");
            });
        }
    }

    @Inject
    private FilmListService filmListService;
}
