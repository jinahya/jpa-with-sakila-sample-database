package com.github.jinahya.sakila.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import java.util.function.BiFunction;

import static com.github.jinahya.sakila.persistence.AddressConstants.GRAPH_CITY;
import static com.github.jinahya.sakila.persistence.AddressConstants.GRAPH_CITY_COUNTRY;
import static com.github.jinahya.sakila.persistence.AddressConstants.GRAPH_NODE_CITY;
import static com.github.jinahya.sakila.persistence.AddressConstants.QUERY_FIND_ALL;
import static com.github.jinahya.sakila.persistence.AddressConstants.QUERY_FIND_ALL_BY_CITY;
import static com.github.jinahya.sakila.persistence.AddressConstants.QUERY_FIND_ALL_BY_CITY_ID;
import static com.github.jinahya.sakila.persistence.AddressConstants.QUERY_FIND_BY_ADDRESS_ID;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Arrays.copyOf;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-address.html">
 * The {@value #TABLE_NAME} table contains address information for customers, staff, and stores.<br/>The
 * {@value TABLE_NAME} table primary key appears as a foreign key in the {@value Customer#TABLE_NAME},
 * {@value Staff#TABLE_NAME}, and {@value Store#TABLE_NAME} tables.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-address.html">5.1.2 The address Table</a>
 * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/spatial-type-overview.html">11.4.1 Spatial Data Types</a>
 * @see Address_
 * @see AddressConstants
 */
@NamedEntityGraph(
        name = GRAPH_CITY_COUNTRY,
        attributeNodes = {
                @NamedAttributeNode(
                        value = GRAPH_NODE_CITY,
                        subgraph = CityConstants.GRAPH_COUNTRY
                )
        },
        subgraphs = {
                @NamedSubgraph(
                        name = CityConstants.GRAPH_COUNTRY,
                        attributeNodes = {
                                @NamedAttributeNode(CityConstants.GRAPH_NODE_COUNTRY)
                        }
                )
        }
)
@NamedEntityGraph(
        name = GRAPH_CITY,
        attributeNodes = {
                @NamedAttributeNode(GRAPH_NODE_CITY)
        }
)
@NamedQuery(
        name = QUERY_FIND_ALL_BY_CITY,
        query = """
                SELECT e
                FROM Address AS e
                WHERE e.city = :city
                      AND e.addressId > :addressIdMinExclusive
                ORDER BY e.addressId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_ALL_BY_CITY_ID,
        query = """
                SELECT e
                FROM Address AS e
                WHERE e.cityId = :cityId
                      AND e.addressId > :addressIdMinExclusive
                ORDER BY e.addressId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_ALL,
        query = """
                SELECT e
                FROM Address AS e
                WHERE e.addressId > :addressIdMinExclusive
                ORDER BY e.addressId ASC"""
)
@NamedQuery(
        name = QUERY_FIND_BY_ADDRESS_ID,
        query = """
                SELECT e
                FROM Address AS e
                WHERE e.addressId = :addressId"""
)
@Entity
@Table(name = Address.TABLE_NAME)
public class Address
        extends _BaseEntity<Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "address";

    /**
     * The name of the database column to which {@link Address_#addressId addressId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_ADDRESS_ID = "address_id";

    /**
     * The name of the database column to which {@link Address_#cityId cityId} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_CITY_ID = City.COLUMN_NAME_CITY_ID;

    /**
     * The name of the database column to which {@link Address_#location location} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_LOCATION = "location";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Address() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "addressId=" + addressId +
               ",address='" + address +
               ",address2='" + address2 +
               ",district='" + district +
               ",cityId=" + cityId +
               ",postalCode='" + postalCode +
               ",phone='" + phone +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Address)) return false;
        return equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    Integer identifier() {
        return getAddressId();
    }

    // ------------------------------------------------------------------------------------------------------- addressId

    /**
     * Returns current value of {@link Address_#addressId addressId} attribute.
     *
     * @return current value of the {@link Address_#addressId addressId} attribute.
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     * Replaces current value of {@link Address_#addressId addressId} attribute with specified value.
     *
     * @param addressId new value for the {@link Address_#addressId addressId} attribute.
     * @deprecated for removal; the column is an <em>auto-increment</em> column.
     */
    // TODO: remove!
    @Deprecated(forRemoval = true)
    private void setAddressId(final Integer addressId) {
        this.addressId = addressId;
    }

    // --------------------------------------------------------------------------------------------------------- address

    /**
     * Returns current value of {@link Address_#address address} attribute.
     *
     * @return current value of the {@link Address_#address address} attribute.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Replaces current value of {@link Address_#address address} attribute with specified value.
     *
     * @param address new value for the {@link Address_#address address} attribute.
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    // -------------------------------------------------------------------------------------------------------- address2

    /**
     * Returns current value of {@link Address_#address2 address2} attribute.
     *
     * @return current value of the {@link Address_#address2 address2} attribute.
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Replaces current value of {@link Address_#address2 address2} attribute with specified value.
     *
     * @param address2 new value for the {@link Address_#address2 address2} attribute.
     */
    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    // -------------------------------------------------------------------------------------------------------- district

    /**
     * Returns current value of {@link Address_#district district} attribute.
     *
     * @return current value of the {@link Address_#district district} attribute.
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Replaces current value of {@link Address_#district district} attribute with specified value.
     *
     * @param district new value for the {@link Address_#district district} attribute.
     */
    public void setDistrict(final String district) {
        this.district = district;
    }

    // ----------------------------------------------------------------------------------------------------- cityId/city
    public Integer getCityId() {
        return cityId;
    }

    protected void setCityId(final Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * Returns current value of {@link Address_#city city} attribute.
     *
     * @return current value of {@link Address_#city city} attribute.
     */
    public City getCity() {
        return city;
    }

    /**
     * Replaces current value of {@link Address_#city city} attribute with specified value.
     *
     * @param city new value for the {@link Address_#city city} attribute.
     * @apiNote This method also replaces current value of {@link Address_#cityId cityId} attribute with
     * {@code city?.cityId}.
     */
    public void setCity(final City city) {
        this.city = city;
        setCityId(
                ofNullable(city)
                        .map(City::getCityId)
                        .orElse(null)
        );
    }

    // ------------------------------------------------------------------------------------------------------ postalCode

    /**
     * Returns current value of {@link Address_#postalCode postalCode} attribute.
     *
     * @return current value of the {@link Address_#postalCode postalCode} attribute.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Replaces current value of {@link Address_#postalCode postalCode} attribute with specified value.
     *
     * @param postalCode new value for the {@link Address_#postalCode postalCode} attribute.
     */
    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    // ----------------------------------------------------------------------------------------------------------- phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    // -------------------------------------------------------------------------------------------------------- location

    /**
     * Returns current value of {@link Address_#location location} attribute.
     *
     * @return current value of the {@link Address_#location location} attribute.
     */
    @_VisibleForTesting
    byte[] getLocation() {
        return location;
    }

    /**
     * Replaces current value of {@link Address_#location location} attribute with specified value.
     *
     * @param location new value for the {@link Address_#location location} attribute.
     */
    @_VisibleForTesting
    void setLocation(final byte[] location) {
        this.location = ofNullable(location).map(v -> copyOf(v, v.length)).orElse(null);
    }

    /**
     * Returns current value of {@link Address_#locationGeometry locationGeometry} attribute.
     *
     * @return current value of the {@link Address_#locationGeometry locationGeometry} attribute.
     */
    public _DomainTypes.Geometry getLocationGeometry() {
        return locationGeometry;
    }

    /**
     * Replaces current value of {@link Address_#locationGeometry locationGeometry} attribute with specified value.
     *
     * @param locationGeometry new value for the {@link Address_#locationGeometry locationGeometry} attribute.
     */
    public void setLocationGeometry(final _DomainTypes.Geometry locationGeometry) {
        this.locationGeometry = locationGeometry;
        setLocation(
                ofNullable(this.locationGeometry)
                        .map(_DomainTypes.Geometry::toByteArray)
                        .orElse(null)

        );
    }

    /**
     * Applies current value of {@link Address_#locationGeometry locationGeometry} attribute, as a
     * {@link _DomainTypes.Wkb.Type#POINT} type, to specified function, and returns the result.
     *
     * @param function the function to be applied with coordinates.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    public <R> R applyLocationGeometryAsPoint(final BiFunction<? super Double, ? super Double, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return ofNullable(getLocationGeometry())
                .map(g -> {
                    final var buffer = g.toByteBuffer();
                    final var srid = buffer.getInt();
                    assert srid == 0;
                    buffer.order(_DomainTypes.Wkb.endianToOrder(buffer.get()));
                    final var type = buffer.getInt();
                    if (type != _DomainTypes.Wkb.Type.POINT.type()) {
                        throw new IllegalArgumentException("locationGeometry is not a point; type: " + type);
                    }
                    final var x = buffer.getDouble();
                    final var y = buffer.getDouble();
                    assert !buffer.hasRemaining();
                    return function.apply(x, y);
                })
                .orElse(null);
    }

    /**
     * Replaces current value of {@link Address_#locationGeometry} attribute with a point of specified coordinates and
     * srid.
     *
     * @param x    a value of x coordinate.
     * @param y    a value of y coordinate.
     * @param srid a value of srid.
     */
    public void setLocationGeometryAsPoint(final double x, final double y, final int srid) {
        final var buffer = ByteBuffer.allocate(Byte.BYTES + Integer.BYTES + Double.BYTES + Double.BYTES);
        buffer.order(ByteOrder.LITTLE_ENDIAN)
                .put(_DomainTypes.Wkb.orderToEndian(buffer.order()))
                .putInt(_DomainTypes.Wkb.Type.POINT.type())
                .putDouble(x)
                .putDouble(y);
        assert !buffer.hasRemaining();
        setLocationGeometry(
                _DomainTypes.Geometry.of(
                        srid,
                        _DomainTypes.Wkb.from(buffer.flip())
                )
        );
    }

    /**
     * Replaces current value of {@link Address_#locationGeometry} attribute with a point of specified coordinates.
     *
     * @param x a value of x coordinate.
     * @param y a value of y coordinate.
     * @apiNote This method invokes {@link #setLocationGeometryAsPoint(double, double, int)} with {@code x}, {@code y},
     * and {@code 0}.
     */
    public void setLocationGeometryAsPoint(final double x, final double y) {
        setLocationGeometryAsPoint(x, y, 0);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-address.html">
     * A surrogate primary key used to uniquely identify each address in the table.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_ADDRESS_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer addressId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-address.html">
     * The first line of an address.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "address", nullable = false, length = 50)
    private String address;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-address.html">
     * An optional second line of an address.
     * </blockquote>
     */
    @Basic(optional = true)
    @Column(name = "address2", nullable = true, length = 50)
    private String address2;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-address.html">
     * The region of an address, this may be a state, province, prefecture, etc.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "district", nullable = false, length = 20)
    private String district;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-address.html">
     * A foreign key pointing to the {@link City#TABLE_NAME} table.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_CITY_ID, nullable = false)
    private Integer cityId;

    /**
     * 이 주소(Address)를 포함하는 도시(City).
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_CITY_ID, nullable = false, insertable = false, updatable = false)
    private City city;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-address.html">
     * The postal code or ZIP code of the address (where applicable).
     * </blockquote>
     */
    @Basic(optional = true)
    @Column(name = "postal_code", nullable = true, length = 10)
    private String postalCode;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-address.html">
     * The telephone number for the address.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-address.html">
     * A Geometry column with a spatial index on it.
     * </blockquote>
     *
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/spatial-type-overview.html">11.4.1 Spatial Data Types</a>
     */
    @NotNull
    @Lob
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LOCATION, nullable = false)
    private byte[] location;

    @Convert(converter = _DomainConverters.GeometryConverter.class)
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LOCATION, nullable = false, insertable = false, updatable = false)
    private _DomainTypes.Geometry locationGeometry;
}
