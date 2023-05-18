package com.github.jinahya.persistence;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Optional;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote>
 * The {@value #TABLE_NAME} table contains address information for customers, staff, and stores.<br/>The
 * {@value TABLE_NAME} table primary key appears as a foreign key in the {@value Customer#TABLE_NAME},
 * {@value Staff#TABLE_NAME}, and {@value Store#TABLE_NAME} tables.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/spatial-type-overview.html">11.4.1 Spatial Data Types</a>
 */
@Entity
@Table(name = Address.TABLE_NAME)
@Slf4j
public class Address
        extends _BaseEntity<Integer> {

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
//               ",location=" + Arrays.toString(location) +
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
    protected Integer identifier() {
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
     * @deprecated for removal.
     */
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

    public String getDistrict() {
        return district;
    }

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

    protected byte[] getLocation() {
        return location;
    }

    protected void setLocation(final byte[] location) {
        this.location = location;
    }

    /**
     * 대체 기본 키 - <blockquote>A surrogate primary key used to uniquely identify each address in the table.</blockquote>
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_ADDRESS_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer addressId;

    /**
     * 주소 첫번 째 줄 - <blockquote>The first line of an address.</blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "address", nullable = false, length = 50)
    private String address;

    /**
     * (선택) 주소 두번 째 줄 - <blockquote>An optional second line of an address.</blockquote>
     */
    @Basic(optional = true)
    @Column(name = "address2", nullable = true, length = 50)
    private String address2;

    /**
     * 주소 구역 - <blockquote>The region of an address, this may be a state, province, prefecture, etc.</blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "district", nullable = false, length = 20)
    private String district;

    /**
     * {@link City#TABLE_NAME 도시} 테이블 외래키 - <blockquote>A foreign key pointing to the {@link City#TABLE_NAME}
     * table.</blockquote>
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_CITY_ID, nullable = false)
    private Integer cityId;

    /**
     * 우편 번호 - <blockquote>The postal code or ZIP code of the address (where applicable).</blockquote>
     */
    @Basic(optional = true)
    @Column(name = "postal_code", nullable = true, length = 10)
    private String postalCode;

    /**
     * 전화 번호 - <blockquote>The telephone number for the address.</blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    /**
     * {@code GEOMETRY} 컬럼 형으로 저장된 위치 정보 - <blockquote>A Geometry column with a spatial index on it.</blockquote>
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_CITY_ID, nullable = false, insertable = false, updatable = false)
    private City city;

    public _PersistenceTypes.Geometry getLocationGeometry() {
        return locationGeometry;
    }

    public void setLocationGeometry(final _PersistenceTypes.Geometry locationGeometry) {
        this.locationGeometry = locationGeometry;
        setLocation(
                Optional.ofNullable(this.locationGeometry)
                        .map(_PersistenceTypes.Geometry::toByteArray)
                        .orElse(null)

        );
    }

    @Convert(converter = _PersistenceConverters.GeometryConverter.class)
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LOCATION, nullable = false, insertable = false, updatable = false)
    private _PersistenceTypes.Geometry locationGeometry;

    public void setLocationGeometryAsPoint(final int srid, final double xCoordinate, final double yCoordinate) {
        final var buffer = ByteBuffer.allocate(Byte.BYTES + Integer.BYTES + Double.BYTES + Double.BYTES);
        buffer.order(ByteOrder.LITTLE_ENDIAN)
                .put(_PersistenceTypes.Wkb.endianValue(buffer.order()))
                .putInt(_PersistenceTypes.Wkb.Type.POINT.getValue())
                .putDouble(xCoordinate)
                .putDouble(yCoordinate);
        assert !buffer.hasRemaining();
        setLocationGeometry(
                _PersistenceTypes.Geometry.of(
                        srid,
                        _PersistenceTypes.Wkb.from(buffer.flip())
                )
        );
    }

    public void setLocationGeometryAsPoint(final double xCoordinate, final double yCoordinate) {
        setLocationGeometryAsPoint(0, xCoordinate, yCoordinate);
    }
}
