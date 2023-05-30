package com.github.jinahya.persistence.sakila;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FilmActor_Test
        extends _BaseEntityTest<FilmActor, FilmActorId> {

    FilmActor_Test() {
        super(FilmActor.class, FilmActorId.class);
    }

    @DisplayName("(get|set)Actor")
    @Nested
    class ActorTest {

        @DisplayName("new.getActor()null")
        @Test
        void getActor_Null_() {
            // GIVEN
            final var filmActor = newEntityInstance();
            // WHEN / THEN
            assertThat(filmActor.getActor()).isNull();
        }

        @DisplayName("setActor(null) -> setActorId(null)")
        @Test
        void setActor_SetActorIdNull_Null() {
            // GIVEN
            final var filmActor = newEntitySpy();
            // WHEN
            filmActor.setActor(null);
            // THEN
            verify(filmActor, times(1)).setActorId(null);
        }

        @DisplayName("setActor(actorIdWithNullActorId) -> setActorId(null)")
        @Test
        void setActor_SetActorIdNull_ActorWithNullActorId() {
            // GIVEN
            final var filmActor = newEntitySpy();
            final var actor = spy(Actor.class);
            when(actor.getActorId()).thenReturn(null);
            // WHEN
            filmActor.setActor(actor);
            // THEN
            verify(filmActor, times(1)).setActorId(null);
        }
    }
}
