package com.github.jinahya.sakila.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

import static com.github.jinahya.sakila.persistence.InventoryConstants.QUERY_FIND_ALL_BY_STORE_ID_AND_FILM_ID;
import static com.github.jinahya.sakila.persistence.InventoryConstants.QUERY_FIND_DISTINCT_FILMS_BY_STORE;
import static com.github.jinahya.sakila.persistence.InventoryConstants.QUERY_FIND_DISTINCT_FILMS_BY_STORE_ID;

/**
 * An entity class for mapping {@value Inventory#TABLE_NAME} table.
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-inventory.html">
 * The {@value #TABLE_NAME} table contains one row for each copy of a given film in a given store.<br/>The
 * {@value #TABLE_NAME} table refers to the {@value Film#TABLE_NAME} and {@value Store#TABLE_NAME} tables using foreign
 * keys and is referred to by the {@value Rental#TABLE_NAME} table.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-inventory.html">5.1.11 The inventory
 * Table</a>
 * @see InventoryConstants
 */
@NamedQuery(
        name = QUERY_FIND_DISTINCT_FILMS_BY_STORE,
        query = """
                SELECT DISTINCT e.film
                FROM Inventory AS e
                WHERE e.store = :store
                      AND e.filmId > :filmIdMinExclusive
                ORDER BY e.film.filmId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_DISTINCT_FILMS_BY_STORE_ID,
        query = """
                SELECT DISTINCT e.film
                FROM Inventory AS e
                WHERE e.storeId = :storeId
                      AND e.filmId > :filmIdMinExclusive
                ORDER BY e.film.filmId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_ALL_BY_STORE_ID_AND_FILM_ID,
        query = """
                SELECT e
                FROM Inventory AS e
                WHERE e.storeId = :storeId
                      AND e.filmId = :filmId
                      AND e.inventoryId > :inventoryIdMinExclusive
                ORDER BY e.inventoryId ASC"""
)
@NamedQuery(
        name = InventoryConstants.QUERY_FIND_ALL_BY_STORE,
        query = """
                SELECT e
                FROM Inventory AS e
                WHERE e.store = :store
                      AND e.inventoryId > :inventoryIdMinExclusive
                ORDER BY e.inventoryId ASC"""
)
@NamedQuery(
        name = InventoryConstants.QUERY_FIND_ALL_BY_STORE_ID,
        query = """
                SELECT e
                FROM Inventory AS e
                WHERE e.storeId = :storeId
                      AND e.inventoryId > :inventoryIdMinExclusive
                ORDER BY e.inventoryId ASC"""
)
@NamedQuery(name = InventoryConstants.QUERY_FIND_ALL_BY_FILM,
            query = """
                    SELECT e
                    FROM Inventory AS e
                    WHERE e.film = :film
                          AND e.inventoryId > :inventoryIdMinExclusive
                    ORDER BY e.inventoryId ASC"""
)
@NamedQuery(
        name = InventoryConstants.QUERY_FIND_ALL_BY_FILM_ID,
        query = """
                SELECT e
                FROM Inventory AS e
                WHERE e.filmId = :filmId
                      AND e.inventoryId > :inventoryIdMinExclusive
                ORDER BY e.inventoryId ASC"""
)
@NamedQuery(
        name = InventoryConstants.QUERY_FIND_ALL,
        query = """
                SELECT e
                FROM Inventory AS e
                WHERE e.inventoryId > :inventoryIdMinExclusive
                ORDER BY e.inventoryId ASC"""
)
@NamedQuery(
        name = InventoryConstants.QUERY_FIND_BY_INVENTORY_ID,
        query = """
                SELECT e
                FROM Inventory AS e
                WHERE e.inventoryId = :inventoryId"""
)
@Entity
@Table(name = Inventory.TABLE_NAME,
       indexes = {
               @Index(columnList = Inventory.COLUMN_NAME_FILM_ID),
               @Index(columnList = Inventory.COLUMN_NAME_STORE_ID + ',' + Inventory.COLUMN_NAME_FILM_ID)
       }
)
public class Inventory
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "inventory";

    /**
     * The name of the table column to which {@link Inventory_#inventoryId inventoryId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_INVENTORY_ID = "inventory_id";

    public static final String COLUMN_NAME_FILM_ID = Film.COLUMN_NAME_FILM_ID;

    public static final String COLUMN_NAME_STORE_ID = Store.COLUMN_NAME_STORE_ID;

    public static Inventory ofInventoryId(final int inventoryId) {
        final var instance = new Inventory();
        instance.inventoryId = inventoryId;
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public Inventory() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "inventoryId=" + inventoryId +
               ",filmId=" + filmId +
               ",storeId=" + storeId +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Inventory)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    Integer identifier() {
        return getInventoryId();
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    @Deprecated
    private void setInventoryId(final Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getFilmId() {
        return filmId;
    }

    void setFilmId(final Integer filmId) {
        this.filmId = filmId;
    }

    /**
     * Returns current value of {@link Inventory_#storeId storeId} attribute.
     *
     * @return current value of the {@link Inventory_#storeId storeId} attribute.
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * Replaces current value of {@link Inventory_#storeId storeId} attribute with specified value.
     *
     * @param storeId new value for the {@link Inventory_#storeId storeId} attribute.
     */
    void setStoreId(final Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-inventory.html">
     * A surrogate primary key used to uniquely identify each item in inventory.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_MEDIUMINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_INVENTORY_ID, nullable = false)
    private Integer inventoryId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-inventory.html">
     * A foreign key pointing to the film this item represents.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FILM_ID, nullable = false)
    private Integer filmId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-inventory.html">
     * A foreign key pointing to the store stocking this item.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_STORE_ID, nullable = false)
    private Integer storeId;

    /**
     * Returns current value of {@link Inventory_#film film} attribute.
     *
     * @return current value of the {@link Inventory_#film film} attribute.
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Replaces current value of {@link Inventory_#film film} attribute with specified value.
     *
     * @param film new value for the {@link Inventory_#film film} attribute.
     * @apiNote This method also replaces {@link Inventory#filmId filmId} attribute with {@code film?.filmId}.
     * @see Film#ofFilmId(int)
     */
    public void setFilm(final Film film) {
        this.film = film;
        setFilmId(
                Optional.ofNullable(this.film)
                        .map(Film::getFilmId)
                        .orElse(null)
        );
    }

    /**
     * 이 재고(Inventory) 항목의 영화.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_FILM_ID, nullable = false, insertable = false, updatable = false)
    private Film film;

    /**
     * Returns current value of {@link Inventory_#store store} attribute.
     *
     * @return current value of the {@link Inventory_#store store} attribute.
     */
    public Store getStore() {
        return store;
    }

    /**
     * Replaces current value of {@link Inventory_#store store} attribute with specified value.
     *
     * @param store new value for the {@link Inventory_#store store} attribute.
     * @apiNote This method also replaces {@link Inventory#storeId storeId} attribute with {@code store?.storeId}.
     * @see Store#ofStoreId(int)
     */
    public void setStore(final Store store) {
        this.store = store;
        setStoreId(
                Optional.ofNullable(this.store)
                        .map(Store::getStoreId)
                        .orElse(null)
        );
    }

    /**
     * 이 재고(Inventory) 항목 점포(Store).
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_STORE_ID, nullable = false, insertable = false, updatable = false)
    private Store store;
}
