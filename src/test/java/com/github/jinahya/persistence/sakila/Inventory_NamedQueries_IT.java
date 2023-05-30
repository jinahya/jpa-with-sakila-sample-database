package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * A class for testing named queries defined on {@link Inventory} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class Inventory_NamedQueries_IT
        extends __PersistenceIT {

    Inventory_NamedQueries_IT() {
        super();
    }

    @DisplayName(InventoryConstants.QUERY_FIND_BY_INVENTORY_ID)
    @Nested
    class FindByInventoryIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            final var inventoryId = 0;
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(InventoryConstants.QUERY_FIND_BY_INVENTORY_ID, Inventory.class)
                                    .setParameter(InventoryConstants.QUERY_PARAM_INVENTORY_ID, inventoryId)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("()!null")
        @Test
        void __1() {
            final var inventoryId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(InventoryConstants.QUERY_FIND_BY_INVENTORY_ID, Inventory.class)
                            .setParameter(InventoryConstants.QUERY_PARAM_INVENTORY_ID, inventoryId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .satisfies(v -> {
                        assertThat(v.getInventoryId()).isEqualTo(inventoryId);
                    });
        }
    }

    @DisplayName(InventoryConstants.QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __WithoutMaxResults() {
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(InventoryConstants.QUERY_FIND_ALL, Inventory.class)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull();
        }

        @Test
        void __WithMaxResults() {
            final var maxResults = ThreadLocalRandom.current().nextInt(8, 16);
            final var list = applyEntityManager(
                    em -> em.createNamedQuery(InventoryConstants.QUERY_FIND_ALL, Inventory.class)
                            .setMaxResults(maxResults)
                            .getResultList()
            );
            assertThat(list)
                    .isNotNull()
                    .isNotEmpty()
                    .doesNotContainNull()
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @DisplayName(InventoryConstants.QUERY_FIND_ALL_INVENTORY_ID_GREATER_THAN)
    @Nested
    class FindAllInventoryIdGreaterThanTest {

        @Test
        void __() {
            final var maxResults = ThreadLocalRandom.current().nextInt(64, 128);
            for (final var i = new AtomicInteger(0); ; ) {
                final var inventoryIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(InventoryConstants.QUERY_FIND_ALL_INVENTORY_ID_GREATER_THAN, Inventory.class)
                                .setParameter(InventoryConstants.QUERY_PARAM_INVENTORY_ID_MIN_EXCLUSIVE, inventoryIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .isSortedAccordingTo(Comparator.comparing(Inventory::getInventoryId));
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getInventoryId());
            }
        }
    }

    @DisplayName(InventoryConstants.QUERY_FIND_ALL_BY_STORE_ID_AND_FILM_ID)
    @Nested
    class FindAllByStoreIdAndFilmIdTest {

        @Test
        void __() {
            final var map = Map.of(
                    1, List.of(1, 4, 10, 11),
                    2, List.of(1, 3, 8, 12)
            );
            map.forEach((storeId, filmIds) -> {
                for (final var filmId : filmIds) {
                    for (final var i = new AtomicInteger(0); ; ) {
                        final var inventoryIdMinExclusive = i.get();
                        final var list = applyEntityManager(
                                em -> em.createNamedQuery(
                                                InventoryConstants.QUERY_FIND_ALL_BY_STORE_ID_AND_FILM_ID,
                                                Inventory.class
                                        )
                                        .setParameter(InventoryConstants.QUERY_PARAM_STORE_ID, storeId)
                                        .setParameter(InventoryConstants.QUERY_PARAM_FILM_ID, filmId)
                                        .setParameter(InventoryConstants.QUERY_PARAM_INVENTORY_ID_MIN_EXCLUSIVE,
                                                      inventoryIdMinExclusive)
                                        .getResultList()
                        );
                        assertThat(list)
                                .isNotNull()
                                .doesNotContainNull()
                                .allSatisfy(e -> {
                                    assertThat(e.getStoreId()).isEqualTo(storeId);
                                    assertThat(e.getFilmId()).isEqualTo(filmId);
                                    assertThat(e.getInventoryId()).isGreaterThan(inventoryIdMinExclusive);
                                });
                        if (list.isEmpty()) {
                            break;
                        }
                        i.set(list.get(list.size() - 1).getInventoryId());
                    }
                }
            });
        }
    }

    private static List<Integer> getFilmIdList() {
        return List.of(1, 31, 69, 73, 86, 91, 103, 109);
    }

    @DisplayName(InventoryConstants.QUERY_FIND_ALL_BY_FILM_ID)
    @Nested
    class FindAllByFilmIdTest {

        private static List<Integer> getFilmIdList() {
            return Inventory_NamedQueries_IT.getFilmIdList();
        }

        @ExplicitParamInjection
        @MethodSource({"getFilmIdList"})
        @ParameterizedTest
        void __(final int filmId) {
            for (final var i = new AtomicInteger(0); ; ) {
                final var inventoryIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(InventoryConstants.QUERY_FIND_ALL_BY_FILM_ID, Inventory.class)
                                .setParameter(InventoryConstants.QUERY_PARAM_FILM_ID, filmId)
                                .setParameter(InventoryConstants.QUERY_PARAM_INVENTORY_ID_MIN_EXCLUSIVE, inventoryIdMinExclusive)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .isSortedAccordingTo(Comparator.comparing(Inventory::getInventoryId))
                        .allSatisfy(e -> {
                            assertThat(e.getFilmId()).isEqualTo(filmId);
                            assertThat(e.getInventoryId()).isGreaterThan(inventoryIdMinExclusive);
                        });
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getInventoryId());
            }
        }
    }

    @DisplayName(InventoryConstants.QUERY_FIND_ALL_BY_FILM_ID)
    @Nested
    class FindAllByFilmTest {

        private static List<Film> getFilmList() {
            return Inventory_NamedQueries_IT.getFilmIdList()
                    .stream()
                    .map(Film::ofFilmId)
                    .toList();
        }

        @ExplicitParamInjection
        @MethodSource({"getFilmList"})
        @ParameterizedTest
        void __(final Film film) {
            for (final var i = new AtomicInteger(0); ; ) {
                final var inventoryIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(InventoryConstants.QUERY_FIND_ALL_BY_FILM, Inventory.class)
                                .setParameter(InventoryConstants.QUERY_PARAM_FILM, film)
                                .setParameter(InventoryConstants.QUERY_PARAM_INVENTORY_ID_MIN_EXCLUSIVE,
                                              inventoryIdMinExclusive)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .isSortedAccordingTo(Comparator.comparing(Inventory::getInventoryId))
                        .allSatisfy(e -> {
                            assertThat(e.getFilmId()).isEqualTo(film.getFilmId());
                            assertThat(e.getInventoryId()).isGreaterThan(inventoryIdMinExclusive);
                        });
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getInventoryId());
            }
        }
    }

    private static List<Integer> getStoreIdList() {
        return List.of(2, 1);
    }

    @DisplayName(InventoryConstants.QUERY_FIND_ALL_BY_STORE_ID)
    @Nested
    class FindAllByStoreIdTest {

        private static List<Integer> getStoreIdList() {
            return Inventory_NamedQueries_IT.getStoreIdList();
        }

        @ExplicitParamInjection
        @MethodSource({"getStoreIdList"})
        @ParameterizedTest
        void __(final int storeId) {
            for (final var i = new AtomicInteger(0); ; ) {
                final var inventoryIdMinExclusive = i.get();
                // TODO: implement!
                return;
            }
        }
    }

    @DisplayName(InventoryConstants.QUERY_FIND_ALL_BY_FILM_ID)
    @Nested
    class FindAllByStoreTest {

        private static List<Store> getStoreList() {
            return Inventory_NamedQueries_IT.getStoreIdList()
                    .stream()
                    .map(Store::ofStoreId)
                    .toList();
        }

        @ExplicitParamInjection
        @MethodSource({"getStoreList"})
        @ParameterizedTest
        void __(final Store store) {
            for (final var i = new AtomicInteger(0); ; ) {
                final var inventoryIdMinExclusive = i.get();
                // TODO: implement!
                return;
            }
        }
    }

    @DisplayName(InventoryConstants.QUERY_FIND_DISTINCT_FILMS_BY_STORE_ID)
    @Nested
    class FindDistinctFilmsByStoreIdTest {

    }

    @DisplayName(InventoryConstants.QUERY_FIND_DISTINCT_FILMS_BY_STORE)
    @Nested
    class FindDistinctFilmsByStoreTest {

    }
}
