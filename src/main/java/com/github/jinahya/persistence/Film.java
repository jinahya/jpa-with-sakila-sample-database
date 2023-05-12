package com.github.jinahya.persistence;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * An abstract mapped super class for mapping {@value Film#TABLE_NAME} table.
 * <blockquote>
 * The film table is a list of all films potentially in stock in the stores. The actual in-stock copies of each film are
 * represented in the {@value MappedInventory#TABLE_NAME} table.
 * <p>
 * The film table refers to the {@value MappedLanguage#TABLE_NAME} table and is referred to by the
 * {@value MappedFilmCategory#TABLE_NAME}, {@value FilmActor#TABLE_NAME}, and {@value MappedInventory#TABLE_NAME}
 * tables.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-film.html">5.1.7 The film Table</a>
 */
@Entity
@Table(name = Film.TABLE_NAME)
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class Film
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "film";

    public static final String COLUMN_NAME_FILM_ID = "film_id";

    public static final String COLUMN_NAME_RENTAL_DURATION = "rental_duration";

    public static final int COLUMN_VALUE_RENTAL_DURATION_3 = 3;

    public static final String COLUMN_NAME_RENTAL_RATE = "rental_rate";

    public static final int COLUMN_PRECISION_RENTAL_RATE = 4;

    public static final int COLUMN_SCALE_RENTAL_RATE = 2;

    public static final BigDecimal COLUMN_DEFAULT_RENTAL_RATE = BigDecimal.valueOf(4.99d);

    public static final String COLUMN_NAME_REPLACEMENT_COST = "replacement_cost";

    public static final int COLUMN_PRECISION_REPLACEMENT_COST = 5;

    public static final int COLUMN_SCALE_REPLACEMENT_COST = 2;

    public static final String COLUMN_NAME_RATING = "rating";

    public enum Rating {
        G,
        PG,
        PG_13("PG-13"),
        R,
        NC_17("NC-17");

        static Rating valueOfColumnValue(final String columnValue) {
            Objects.requireNonNull(columnValue, "columnValue is null");
            for (final Rating value : values()) {
                if (value.columnValue().equals(columnValue)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("no value for " + columnValue);
        }

        Rating(final String columnValue) {
            this.columnValue = columnValue;
        }

        Rating() {
            this(null);
        }

        public String columnValue() {
            return Objects.requireNonNullElseGet(columnValue, this::name);
        }

        private final String columnValue;
    }

    static class RatingConverter
            implements AttributeConverter<Rating, String> {

        @Override
        public String convertToDatabaseColumn(final Rating attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.columnValue();
        }

        @Override
        public Rating convertToEntityAttribute(final String dbData) {
            if (dbData == null) {
                return null;
            }
            return Rating.valueOfColumnValue(dbData);
        }
    }

    public static final String COLUMN_NAME_SPECIAL_FEATURES = "special_features";

    public enum SpecialFeature {
        TRAILERS("Trailers"),
        COMMENTARIES("Commentaries"),
        DELETED_SCENES("Deleted Scenes"),
        BEHIND_THE_SCENES("Behind the Scenes");

        static SpecialFeature valueOfColumnValue(final String columnValue) {
            Objects.requireNonNull(columnValue, "columnValue is null");
            for (final SpecialFeature value : values()) {
                if (value.columnValue().equals(columnValue)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("no value for " + columnValue);
        }

        SpecialFeature(final String columnValue) {
            this.columnValue = Objects.requireNonNull(columnValue, "columnValue is null");
        }

        public String columnValue() {
            return columnValue;
        }

        private final String columnValue;
    }

    static class SpecialFeaturesConverter
            implements AttributeConverter<Set<SpecialFeature>, String> {

        @Override
        public String convertToDatabaseColumn(final Set<SpecialFeature> attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.stream()
                    .map(SpecialFeature::columnValue)
                    .collect(Collectors.joining(","));
        }

        @Override
        public Set<SpecialFeature> convertToEntityAttribute(final String dbData) {
            if (dbData == null) {
                return null;
            }
            return Arrays.stream(dbData.split(","))
                    .map(SpecialFeature::valueOfColumnValue)
                    .collect(Collectors.toCollection(() -> EnumSet.noneOf(SpecialFeature.class)));
        }
    }

    public static final BigDecimal COLUMN_DEFAULT_REPLACEMENT_COST = BigDecimal.valueOf(19.99d);

    @Override
    public String toString() {
        return super.toString() + '{' +
               "filmId=" + filmId +
               ",title=" + title +
               ",description=" + description +
               ",releaseYear=" + releaseYear +
               ",languageId=" + languageId +
               ",originalLanguageId=" + originalLanguageId +
               ",rentalDuration=" + rentalDuration +
               ",rentalRate=" + rentalRate +
               ",length=" + length +
               ",replacementCost=" + replacementCost +
               ",rating=" + rating +
               ",specialFeatures=" + specialFeatures +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Film)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * A surrogate primary key used to uniquely identify each film in the table.
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_FILM_ID, nullable = false, insertable = false, updatable = false)
    private Integer filmId;

    /**
     * The title of the film.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "title", nullable = false, length = 128)
    private String title;

    /**
     * A short description or plot summary of the film.
     */
    @Basic(optional = true)
    @Column(name = "description", nullable = true, length = _PersistenceConstants.COLUMN_LENGTH_TEXT)
    private String description;

    /**
     * The year in which the movie was released.
     */
    @Max(_PersistenceConstants.MAX_COLUMN_YEAR)
    @Min(_PersistenceConstants.MIN_COLUMN_YEAR)
    @Basic(optional = true)
    @Column(name = "release_year", nullable = true)
    private Integer releaseYear;

    /**
     * A foreign key pointing at the {@value MappedLanguage#TABLE_NAME} table; identifies the language of the film.
     */
    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = "language_id", nullable = false)
    private Integer languageId;

    /**
     * A foreign key pointing at the {@value MappedLanguage#TABLE_NAME} table; identifies the original language of the
     * film. Used when a film has been dubbed into a new language.
     */
    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = true)
    @Column(name = "original_language_id", nullable = true)
    private Integer originalLanguageId;

    /**
     * The length of the rental period, in days.
     */
    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_RENTAL_DURATION, nullable = false)
    @Builder.Default
    private Integer rentalDuration = COLUMN_VALUE_RENTAL_DURATION_3;

    /**
     * The cost to rent the film for the period specified in the {@value #COLUMN_NAME_RENTAL_DURATION} column.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_RENTAL_RATE, nullable = false, precision = COLUMN_PRECISION_RENTAL_RATE,
            scale = COLUMN_SCALE_RENTAL_RATE)
    @Builder.Default
    private BigDecimal rentalRate = COLUMN_DEFAULT_RENTAL_RATE;

    /**
     * The duration of the film, in minutes.
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Basic(optional = true)
    @Column(name = "length", nullable = true)
    private Integer length;

    /**
     * The amount charged to the customer if the film is not returned or is returned in a damaged state.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_REPLACEMENT_COST, nullable = false, precision = COLUMN_PRECISION_REPLACEMENT_COST,
            scale = COLUMN_SCALE_REPLACEMENT_COST)
    @Builder.Default
    private BigDecimal replacementCost = COLUMN_DEFAULT_REPLACEMENT_COST;

    /**
     * The rating assigned to the film. Can be one of: {@code G}, {@code PG}, {@code PG-13}, {@code R}, or
     * {@code NC-17}.
     */
    @Convert(converter = RatingConverter.class)
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_RATING, nullable = true)
    private Rating rating;

    /**
     * Lists which common special features are included on the DVD. Can be zero or more of: {@code Trailers},
     * {@code Commentaries}, {@code Deleted Scenes}, {@code Behind the Scenes}.
     */
    @Convert(converter = SpecialFeaturesConverter.class)
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_SPECIAL_FEATURES, nullable = true)
    private Set<SpecialFeature> specialFeatures;

    /**
     * Returns current value of {@value MappedFilm_#RELEASE_YEAR} attribute as an instance of specified temporal
     * accessor type.
     *
     * @param mapper a function for mapping current value of {@value MappedFilm_#RELEASE_YEAR} attribute into an
     *               instance of {@link T}.
     * @param <T>    type of temporal accessor
     * @return an instance of {@link T} mapped from current value of {@value MappedFilm_#RELEASE_YEAR} attribute;
     * {@code null} if current value of {@value MappedFilm_#RELEASE_YEAR} attribute is {@code null}.
     */
    public <T extends TemporalAccessor> T getReleaseYearAsTemporalAccessor(final IntFunction<? extends T> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return Optional.ofNullable(getReleaseYear())
                .map(mapper::apply)
                .orElse(null);
    }

    /**
     * Replaces current value of {@value MappedFilm_#RELEASE_YEAR} attribute with the {@link ChronoField#YEAR} field
     * value of specified temporal accessor value.
     *
     * @param releaseYearAsTemporalAccessor the temporal accessor value whose {@link ChronoField#YEAR} field value is
     *                                      fetched.
     * @see TemporalAccessor#get(TemporalField)
     */
    public void setReleaseYearAsTemporalAccessor(final TemporalAccessor releaseYearAsTemporalAccessor) {
        setReleaseYear(
                Optional.ofNullable(releaseYearAsTemporalAccessor)
                        .map(ta -> ta.get(ChronoField.YEAR))
                        .orElse(null)
        );
    }

    /**
     * Returns current value of {@value MappedFilm_#RENTAL_RATE} attribute as an instance of specified temporal amount
     * type.
     *
     * @param mapper a function for mapping current value of {@value MappedFilm_#RENTAL_DURATION} attribute into an
     *               instance of {@link T}.
     * @param <T>    type of temporal amount
     * @return an instance of {@link T} mapped from current value of {@value MappedFilm_#RENTAL_DURATION} attribute;
     * {@code null} if current value of {@value MappedFilm_#RENTAL_DURATION} attribute is {@code null}.
     */
    @Transient
    public <T extends TemporalAmount> T getRentalDurationAsTemporalAmount(final IntFunction<? extends T> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        // TODO: implement!
        return null;
    }

    /**
     * Replaces current value of {@value MappedFilm_#RENTAL_DURATION} attribute with specified temporal amount in
     * {@link java.time.temporal.ChronoUnit#DAYS} unit.
     *
     * @param rentalDurationAsTemporalAmount the temporal amount value for the
     *                                       {@link java.time.temporal.ChronoUnit#DAYS} unit.
     * @see TemporalAmount#get(TemporalUnit)
     */
    public void setRentalDurationAsTemporalAmount(final TemporalAmount rentalDurationAsTemporalAmount) {
        // TODO: implement!
    }

    /**
     * Returns current value of {@value MappedFilm_#LENGTH} attribute as an instance of specified temporal amount type.
     *
     * @param mapper a function for mapping current value of {@value MappedFilm_#LENGTH} attribute into an instance of
     *               {@link T}.
     * @param <T>    type of temporal amount
     * @return an instance of {@link T} mapped from current value of {@value MappedFilm_#LENGTH} attribute; {@code null}
     * if current value of {@value MappedFilm_#LENGTH} attribute is {@code null}.
     */
    @Transient
    public <T extends TemporalAmount> T getLengthAsTemporalAmount(final IntFunction<? extends T> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        // TODO: implement!
        return null;
    }

    /**
     * Replaces current value of {@value MappedFilm_#LENGTH} attribute with specified temporal amount in
     * {@link java.time.temporal.ChronoUnit#MINUTES} unit.
     *
     * @param lengthAsTemporalAmount the temporal amount value for the {@link java.time.temporal.ChronoUnit#MINUTES}
     *                               unit.
     * @see TemporalAmount#get(TemporalUnit)
     */
    public void setLengthAsTemporalAmount(final TemporalAmount lengthAsTemporalAmount) {
        // TODO: implement!
    }
}
