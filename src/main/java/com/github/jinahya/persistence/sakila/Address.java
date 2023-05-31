package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

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
@NamedQuery(name = AddressConstants.QUERY_FIND_ALL_BY_CITY,
            query = """
                    SELECT e
                    FROM Address AS e
                    WHERE e.city = :city
                          AND e.addressId > :addressIdMinExclusive
                    ORDER BY e.addressId ASC""")
@NamedQuery(name = AddressConstants.QUERY_FIND_ALL_BY_CITY_ID,
            query = """
                    SELECT e
                    FROM Address AS e
                    WHERE e.cityId = :cityId
                          AND e.addressId > :addressIdMinExclusive
                    ORDER BY e.addressId ASC""")
@NamedQuery(name = AddressConstants.QUERY_FIND_ALL_ADDRESS_ID_GREATER_THAN,
            query = """
                    SELECT e
                    FROM Address AS e
                    WHERE e.addressId > :addressIdMinExclusive
                    ORDER BY e.addressId ASC""")
@NamedQuery(name = AddressConstants.QUERY_FIND_ALL,
            query = """
                    SELECT e
                    FROM Address AS e""")
@NamedQuery(name = AddressConstants.QUERY_FIND_BY_ADDRESS_ID,
            query = """
                    SELECT e
                    FROM Address AS e
                    WHERE e.addressId = :addressId""")
@Entity
@Table(name = Address.TABLE_NAME)
public class Address
        extends _BaseEntity<Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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

    public Integer getCityId() {
        return cityId;
    }

    protected void setCityId(final Integer cityId) {
        this.cityId = cityId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    /**
     * Returns current value of {@link Address_#location location} attribute.
     *
     * @return current value of the {@link Address_#location location} attribute.
     */
    byte[] getLocation() {
        return location;
    }

    /**
     * Replaces current value of {@link Address_#location location} attribute with specified value.
     *
     * @param location new value for the {@link Address_#location location} attribute.
     */
    void setLocation(final byte[] location) {
        this.location = Optional.ofNullable(location).map(v -> Arrays.copyOf(v, v.length)).orElse(null);
    }

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
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LOCATION, nullable = false)
    private byte[] location;

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
                Optional.ofNullable(city)
                        .map(City::getCityId)
                        .orElse(null)
        );
    }

    /**
     * 이 주소(Address)를 포함하는 도시(City).
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_CITY_ID, nullable = false, insertable = false, updatable = false)
    private City city;

    public _DomainTypes.Geometry getLocationGeometry() {
        return locationGeometry;
    }

    public void setLocationGeometry(final _DomainTypes.Geometry locationGeometry) {
        this.locationGeometry = locationGeometry;
        setLocation(
                Optional.ofNullable(this.locationGeometry)
                        .map(_DomainTypes.Geometry::toByteArray)
                        .orElse(null)

        );
    }

    @Convert(converter = _DomainConverters.GeometryConverter.class)
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LOCATION, nullable = false, insertable = false, updatable = false)
    private _DomainTypes.Geometry locationGeometry;

    /**
     * Applies current value of {@link Address_#locationGeometry locationGeometry} attribute, as a
     * {@link _DomainTypes.Wkb.Type#POINT} type, to specified function, and returns the result.
     *
     * @param function the function applies with coordinates.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    public <R> R getLocationGeometryAsPoint(final BiFunction<? super Double, ? super Double, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return Optional.ofNullable(getLocationGeometry())
                .map(lg -> {
                    final var buffer = lg.toByteBuffer();
                    final var srid = buffer.getInt();
                    assert srid == 0;
                    buffer.order(_DomainTypes.Wkb.endianToOrder(buffer.get()));
                    final var type = buffer.getInt();
                    if (type != _DomainTypes.Wkb.Type.POINT.type()) {
                        throw new IllegalArgumentException("not a point type: " + type);
                    }
                    final var xCoordinate = buffer.getDouble();
                    final var yCoordinate = buffer.getDouble();
                    assert !buffer.hasRemaining();
                    return function.apply(xCoordinate, yCoordinate);
                })
                .orElse(null);
    }

    public void setLocationGeometryAsPoint(final int srid, final double xCoordinate, final double yCoordinate) {
        final var buffer = ByteBuffer.allocate(Byte.BYTES + Integer.BYTES + Double.BYTES + Double.BYTES);
        buffer.order(ByteOrder.LITTLE_ENDIAN)
                .put(_DomainTypes.Wkb.orderToEndian(buffer.order()))
                .putInt(_DomainTypes.Wkb.Type.POINT.type())
                .putDouble(xCoordinate)
                .putDouble(yCoordinate);
        assert !buffer.hasRemaining();
        setLocationGeometry(
                _DomainTypes.Geometry.of(
                        srid,
                        _DomainTypes.Wkb.from(buffer.flip())
                )
        );
    }

    public void setLocationGeometryAsPoint(final double xCoordinate, final double yCoordinate) {
        setLocationGeometryAsPoint(0, xCoordinate, yCoordinate);
    }

    /**
     * Applies coordinates(<em>latitude</em> and <em>longitude</em>), parsed from current value of
     * {@link Address_#locationGeometry locationGeometry} attribute, to specified function, and returns the result.
     *
     * @param function the function to be applied with <em>latitude</em> and <em>longitude</em>.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    public <R> R getLatitudeLongitude(final BiFunction<? super Double, ? super Double, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return getLocationGeometryAsPoint((x, y) -> function.apply(y, x));
    }

    /**
     * Replaces current value of {@link Address_#locationGeometry locationGeometry} attribute with specified values.
     *
     * @param latitude  a value of <em>latitude</em>.
     * @param longitude a value of <em>longitude</em>.
     */
    public void setLatitudeLongitude(final double latitude, final double longitude) {
        setLocationGeometryAsPoint(longitude, latitude);
    }
}
