package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FilmActor_Test
        extends _BaseEntityTest<FilmActor, FilmActorId> {

    FilmActor_Test() {
        super(FilmActor.class, FilmActorId.class);
    }

    @DisplayName("getActor")
    @Nested
    class GetActorTest {

        @DisplayName("()null")
        @Test
        void _Null_New() {
            // GIVEN
            final var instance = newEntityInstance();
            // WHEN / THEN
            assertThat(instance.getActor()).isNull();
        }
    }

    @DisplayName("setActor")
    @Nested
    class SetActorTest {

        @DisplayName("(null) -> setActorId(null)")
        @Test
        void setActor_SetActorIdNull_Null() {
            // GIVEN
            final var filmActor = newEntitySpy();
            // WHEN
            filmActor.setActor(null);
            // THEN
            verify(filmActor, times(1)).setActorId(null);
        }

        @DisplayName("(actor_withNullActorId) -> setActorId(null)")
        @Test
        void setActor_SetActorIdWithNull_ActorWithNullActorId() {
            // GIVEN
            final var filmActor = newEntitySpy();
            final var actor = Actor.ofActorId(null);
            // WHEN
            filmActor.setActor(actor);
            // THEN
            verify(filmActor, times(1)).setActorId(null);
        }

        @DisplayName("(actor_withNonNullActorId) -> setActorId(actor.actorId)")
        @Test
        void setActor_SetActorIdWithNonNull_ActorWithNonNullActorId() {
            // GIVEN
            final var instance = newEntitySpy();
            final var actorId = 1;
            final var actor = Actor.ofActorId(actorId);
            // WHEN
            instance.setActor(actor);
            // THEN
            verify(instance, times(1)).setActorId(actorId);
        }
    }
}
