package com.github.jinahya.persistence.sakila;

import jakarta.persistence.NoResultException;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.jinahya.persistence.sakila.InventoryConstants.PARAMETER_FILM;
import static com.github.jinahya.persistence.sakila.InventoryConstants.PARAMETER_FILM_ID;
import static com.github.jinahya.persistence.sakila.InventoryConstants.PARAMETER_FILM_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila.InventoryConstants.PARAMETER_INVENTORY_ID;
import static com.github.jinahya.persistence.sakila.InventoryConstants.PARAMETER_INVENTORY_ID_MIN_EXCLUSIVE;
import static com.github.jinahya.persistence.sakila.InventoryConstants.PARAMETER_STORE_ID;
import static com.github.jinahya.persistence.sakila.InventoryConstants.QUERY_FIND_ALL;
import static com.github.jinahya.persistence.sakila.InventoryConstants.QUERY_FIND_ALL_BY_FILM;
import static com.github.jinahya.persistence.sakila.InventoryConstants.QUERY_FIND_ALL_BY_FILM_ID;
import static com.github.jinahya.persistence.sakila.InventoryConstants.QUERY_FIND_ALL_BY_STORE;
import static com.github.jinahya.persistence.sakila.InventoryConstants.QUERY_FIND_ALL_BY_STORE_ID;
import static com.github.jinahya.persistence.sakila.InventoryConstants.QUERY_FIND_ALL_BY_STORE_ID_AND_FILM_ID;
import static com.github.jinahya.persistence.sakila.InventoryConstants.QUERY_FIND_BY_INVENTORY_ID;
import static com.github.jinahya.persistence.sakila.InventoryConstants.QUERY_FIND_DISTINCT_FILMS_BY_STORE;
import static com.github.jinahya.persistence.sakila.InventoryConstants.QUERY_FIND_DISTINCT_FILMS_BY_STORE_ID;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Comparator.comparing;
import static java.util.Map.entry;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * A class for testing named queries defined on {@link Inventory} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class Inventory_NamedQueries_IT
        extends __PersistenceIT {

    private static final Logger log = getLogger(lookup().lookupClass());

    private static List<Integer> getStoreIdList() {
        return List.of(2, 1);
    }

    private static List<Store> getStoreList() {
        return getStoreIdList()
                .stream()
                .map(Store::ofStoreId)
                .toList();
    }

    private static List<Integer> getFilmIdList() {
        return List.of(1, 31, 69, 73, 86, 91, 103, 109);
    }

    private static List<Film> getFilmList() {
        return getFilmIdList()
                .stream()
                .map(Film::ofFilmId)
                .toList();
    }

    Inventory_NamedQueries_IT() {
        super();
    }

    @DisplayName(QUERY_FIND_BY_INVENTORY_ID)
    @Nested
    class FindByInventoryIdTest {

        @DisplayName("(0)NoResultException")
        @Test
        void _NoResultException_0() {
            final var inventoryId = 0;
            assertThatThrownBy(
                    () -> applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_BY_INVENTORY_ID, Inventory.class)
                                    .setParameter(PARAMETER_INVENTORY_ID, inventoryId)
                                    .getSingleResult() // NoResultException
                    )
            ).isInstanceOf(NoResultException.class);
        }

        @DisplayName("(1)!null")
        @Test
        void __1() {
            final var inventoryId = 1;
            final var found = applyEntityManager(
                    em -> em.createNamedQuery(QUERY_FIND_BY_INVENTORY_ID, Inventory.class)
                            .setParameter(PARAMETER_INVENTORY_ID, inventoryId)
                            .getSingleResult()
            );
            assertThat(found)
                    .isNotNull()
                    .satisfies(v -> {
                        assertThat(v.getInventoryId()).isEqualTo(inventoryId);
                    });
        }
    }

    @DisplayName(QUERY_FIND_ALL)
    @Nested
    class FindAllTest {

        @Test
        void __() {
            final var maxResults = current().nextInt(64, 128);
            for (final var i = new AtomicInteger(0); ; ) {
                final var inventoryIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL, Inventory.class)
                                .setParameter(PARAMETER_INVENTORY_ID_MIN_EXCLUSIVE, inventoryIdMinExclusive)
                                .setMaxResults(maxResults)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .hasSizeLessThanOrEqualTo(maxResults)
                        .extracting(Inventory::getInventoryId)
                        .allMatch(v -> v > inventoryIdMinExclusive)
                        .isSorted();
                if (list.isEmpty()) {
                    break;
                }
                i.set(list.get(list.size() - 1).getInventoryId());
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_FILM_ID)
    @Nested
    class FindAllByFilmIdTest {

        @ExplicitParamInjection
        @MethodSource({"com.github.jinahya.persistence.sakila.Inventory_NamedQueries_IT#getFilmIdList"})
        @ParameterizedTest
        void __(final int filmId) {
            for (final var i = new AtomicInteger(0); ; ) {
                final var inventoryIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_FILM_ID, Inventory.class)
                                .setParameter(PARAMETER_FILM_ID, filmId)
                                .setParameter(PARAMETER_INVENTORY_ID_MIN_EXCLUSIVE, inventoryIdMinExclusive)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .isSortedAccordingTo(comparing(Inventory::getInventoryId))
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

    @DisplayName(QUERY_FIND_ALL_BY_FILM)
    @Nested
    class FindAllByFilmTest {

        @ExplicitParamInjection
        @MethodSource({"com.github.jinahya.persistence.sakila.Inventory_NamedQueries_IT#getFilmList"})
        @ParameterizedTest
        void __(final Film film) {
            for (final var i = new AtomicInteger(0); ; ) {
                final var inventoryIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_ALL_BY_FILM, Inventory.class)
                                .setParameter(PARAMETER_FILM, film)
                                .setParameter(PARAMETER_INVENTORY_ID_MIN_EXCLUSIVE, inventoryIdMinExclusive)
                                .getResultList()
                );
                assertThat(list)
                        .isNotNull()
                        .doesNotContainNull()
                        .isSortedAccordingTo(comparing(Inventory::getInventoryId))
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

    @DisplayName(QUERY_FIND_ALL_BY_STORE_ID)
    @Nested
    class FindAllByStoreIdTest {

        @ExplicitParamInjection
        @MethodSource({"com.github.jinahya.persistence.sakila.Inventory_NamedQueries_IT#getStoreIdList"})
        @ParameterizedTest
        void __(final int storeId) {
            final var maxResults = current().nextInt(32, 64);
            for (final var i = new AtomicInteger(0); ; ) {
                final var inventoryIdMinExclusive = i.get();
                // TODO: implement!
                return;
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_STORE)
    @Nested
    class FindAllByStoreTest {

        @ExplicitParamInjection
        @MethodSource({"com.github.jinahya.persistence.sakila.Inventory_NamedQueries_IT#getStoreList"})
        @ParameterizedTest
        void __(final Store store) {
            final var maxResults = current().nextInt(32, 64);
            for (final var i = new AtomicInteger(0); ; ) {
                final var inventoryIdMinExclusive = i.get();
                // TODO: implement!
                return;
            }
        }
    }

    @DisplayName(QUERY_FIND_ALL_BY_STORE_ID_AND_FILM_ID)
    @Nested
    class FindAllByStoreIdAndFilmIdTest {

        @Test
        void __() {
            final var map = Map.of(
                    1, List.of(1, 4, 10, 11, Integer.MAX_VALUE),
                    2, List.of(1, 3, 8, 12, Integer.MAX_VALUE)
            );
            map.entrySet().stream().flatMap(e -> e.getValue().stream().map(v -> entry(e.getKey(), v))).forEach(e -> {
                final var storeId = e.getKey();
                final var filmId = e.getValue();
                final var maxResults = current().nextInt(3, 6);
                for (final var i = new AtomicInteger(0); ; ) {
                    final var inventoryIdMinExclusive = i.get();
                    final var list = applyEntityManager(
                            em -> em.createNamedQuery(QUERY_FIND_ALL_BY_STORE_ID_AND_FILM_ID, Inventory.class)
                                    .setParameter(PARAMETER_STORE_ID, storeId)
                                    .setParameter(PARAMETER_FILM_ID, filmId)
                                    .setParameter(PARAMETER_INVENTORY_ID_MIN_EXCLUSIVE, inventoryIdMinExclusive)
                                    .setMaxResults(maxResults)
                                    .getResultList()
                    );
                    assertThat(list)
                            .isNotNull()
                            .doesNotContainNull()
                            .hasSizeLessThanOrEqualTo(maxResults)
                            .allSatisfy(v -> {
                                assertThat(v.getStoreId()).isEqualTo(storeId);
                                assertThat(v.getFilmId()).isEqualTo(filmId);
                                assertThat(v.getInventoryId()).isGreaterThan(inventoryIdMinExclusive);
                            });
                    if (list.isEmpty()) {
                        break;
                    }
                    i.set(list.get(list.size() - 1).getInventoryId());
                }
            });
        }
    }

    @DisplayName(QUERY_FIND_DISTINCT_FILMS_BY_STORE_ID)
    @Nested
    class FindDistinctFilmsByStoreIdTest {

        @ExplicitParamInjection
        @MethodSource({"com.github.jinahya.persistence.sakila.Inventory_NamedQueries_IT#getStoreIdList"})
        @ParameterizedTest
        void __(final int storeId) {
            final var maxResults = current().nextInt(32, 64);
            for (final var i = new AtomicInteger(0); ; ) {
                final var filmIdMinExclusive = i.get();
                final var list = applyEntityManager(
                        em -> em.createNamedQuery(QUERY_FIND_DISTINCT_FILMS_BY_STORE_ID, Film.class)
                                .setParameter(PARAMETER_STORE_ID, storeId)
                                .setParameter(PARAMETER_FILM_ID_MIN_EXCLUSIVE, filmIdMinExclusive)
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

    @DisplayName(QUERY_FIND_DISTINCT_FILMS_BY_STORE)
    @Nested
    class FindDistinctFilmsByStoreTest {

        @ExplicitParamInjection
        @MethodSource({"com.github.jinahya.persistence.sakila.Inventory_NamedQueries_IT#getStoreList"})
        @ParameterizedTest
        void __(final Store store) {
            final var maxResults = current().nextInt(32, 64);
            for (final var i = new AtomicInteger(0); ; ) {
                final var filmIdMinExclusive = i.get();
                // TODO: implement!
                break;
            }
        }
    }
}
