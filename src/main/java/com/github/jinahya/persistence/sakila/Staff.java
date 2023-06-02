package com.github.jinahya.persistence.sakila;

import com.github.jinahya.persistence.sakila.util.SecurityUtils;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
 * The {@value TABLE_NAME} table lists all staff members, including information for email address, login information,
 * and picture.<br/>The {@value #TABLE_NAME} table refers to the {@value Store#TABLE_NAME} and
 * {@value Address#TABLE_NAME} tables using foreign keys, and is referred to by the {@value Rental#TABLE_NAME},
 * {@value Payment#TABLE_NAME}, and {@value Store#TABLE_NAME} tables.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">5.1.15 The staff Table</a>
 */
@Entity
@Table(name = Staff.TABLE_NAME)
public class Staff
        extends _BaseEntity<Integer> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "staff";

    /**
     * The name of the table column to which the {@link Staff_#staffId staffId} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_STAFF_ID = "staff_id";

    /**
     * The name of the table column to which the {@link Staff_#addressId addressId} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_ADDRESS_ID = "address_id";

    /**
     * The name of the table column to which the {@link Staff_#storeId storeId} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_STORE_ID = Store.COLUMN_NAME_STORE_ID;

    /**
     * The name of the table column to which the {@link Staff_#active active} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_ACTIVE = "active";

    /**
     * The default value of the {@link #COLUMN_NAME_ACTIVE} column. The value is {@value}.
     */
    public static final int COLUMN_VALUE_ACTIVE_1 = 1;

    /**
     * Creates a new instance with specified value of {@link Staff_#staffId staffId} attribute.
     *
     * @param staffId the value of {@link Staff_#staffId staffId} attribute.
     * @return a new instance with {@code staffId}.
     */
    public static Staff ofStaffId(final int staffId) {
        final var instance = new Staff();
        instance.staffId = staffId;
        return instance;
    }

    /**
     * Creates a new instance.
     */
    public Staff() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "staffId=" + staffId +
               ",firstName='" + firstName +
               ",lastName='" + lastName +
               ",addressId=" + addressId +
//               ",picture=" + Arrays.toString(picture) +
               ",email='" + email +
               ",storeId=" + storeId +
               ",active=" + active +
               ",username='" + username +
//               ",password='" + password +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Staff)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    Integer identifier() {
        return getStaffId();
    }

    /**
     * Returns current value of {@link Staff_#staffId staffId} attribute.
     *
     * @return current value of the {@link Staff_#staffId staffId} attribute.
     */
    public Integer getStaffId() {
        return staffId;
    }

    /**
     * Replaces current value of {@link Staff_#staffId staffId} attribute with specified value.
     *
     * @param staffId new value for the {@link Staff_#staffId staffId} attribute.
     * @deprecated for removal.
     */
    @Deprecated(forRemoval = true)
    private void setStaffId(final Integer staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Integer getAddressId() {
        return addressId;
    }

    void setAddressId(final Integer addressId) {
        this.addressId = addressId;
    }

    byte[] getPicture() {
        return picture;
    }

    void setPicture(final byte[] picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getStoreId() {
        return storeId;
    }

    void setStoreId(final Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(final Integer active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    String getPassword() {
        return password;
    }

    private void setPassword(final String password) {
        this.password = password;
    }

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
     * A surrogate primary key that uniquely identifies the staff member.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_STAFF_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer staffId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
     * The first name of the staff member.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
     * The last name of the staff member.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
     * A foreign key to the staff member {@value Address#TABLE_NAME} in the address table.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ADDRESS_ID, nullable = false)
    private Integer addressId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
     * A {@code BLOB} containing a photograph of the employee.
     * </blockquote>
     */
    @Basic(optional = true)
    @Column(name = "picture", nullable = true)
    private byte[] picture;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
     * The staff member email address.
     * </blockquote>
     */
    @Email
    @Basic(optional = true)
    @Column(name = "email", nullable = true, length = 50)
    private String email;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
     * The staff member “home store.” The employee can work at other stores but is generally assigned to the store
     * listed.
     * </blockquote>
     */
    @Max(_DomainConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_STORE_ID, nullable = false)
    private Integer storeId;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
     * Whether this is an active employee. If employees leave, their rows are not deleted from this table; instead, this
     * column is set to {@code FALSE}.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ACTIVE, nullable = false)
    private Integer active = COLUMN_VALUE_ACTIVE_1;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
     * The user name used by the staff member to access the rental system.
     * </blockquote>
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 16, updatable = false)
    private String username;

    /**
     * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">
     * The password used by the staff member to access the rental system. The password should be stored as a hash using
     * the {@code SHA2()} function.
     * </blockquote>
     */
    @Basic(optional = true)
    @Column(name = "password", nullable = true, length = 40)
    private String password;

    /**
     * Returns current value of {@link Staff_#address address} attribute.
     *
     * @return current value of the {@link Staff_#address address} attribute.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Replaces current value of {@link Staff_#address address} attribute with specified value.
     *
     * @param address new value for the {@link Staff_#address address} attribute.
     * @apiNote This method also updates {@link Staff_#addressId addressId} attribute with {@code address?.addressId}.
     */
    public void setAddress(final Address address) {
        this.address = address;
        setAddressId(
                Optional.ofNullable(this.address)
                        .map(Address::getAddressId)
                        .orElse(null)
        );
    }

    @Valid
    @NotNull
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_ADDRESS_ID, nullable = false,
                insertable = false, // !!!
                updatable = false   // !!!
                )
    private Address address;

    @Transient
    public void getPictureWritingTo(final OutputStream stream) throws IOException {
        Objects.requireNonNull(stream, "stream is null");
        final var data = getPicture();
        if (data == null) {
            throw new IllegalStateException("data is currently null");
        }
        stream.write(data);
    }

    @Transient
    public void getPictureWritingTo(final java.io.File file) throws IOException {
        Objects.requireNonNull(file, "file is null");
        try (final var stream = new FileOutputStream(file)) {
            getPictureWritingTo(stream);
            stream.flush();
        }
    }

    @Transient
    public void getPictureWritingTo(final WritableByteChannel channel) throws IOException {
        Objects.requireNonNull(channel, "channel is null");
        final var data = getPicture();
        if (data == null) {
            throw new IllegalStateException("data is currently null");
        }
        for (final var buffer = ByteBuffer.wrap(data);
             buffer.hasRemaining();
             channel.write(buffer)) {
            // empty
        }
    }

    @Transient
    public void getPictureWritingTo(final java.nio.file.Path path) throws IOException {
        Objects.requireNonNull(path, "path is null");
        try (final var channel = FileChannel.open(
                path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.DSYNC)) {
            getPictureWritingTo(channel);
            channel.force(false);
        }
    }

    @Transient
    public void setPictureReadingFrom(final InputStream stream) throws IOException {
        Objects.requireNonNull(stream, "stream is null");
        final var image = ImageIO.read(stream);
        if (image == null) {
            throw new IllegalArgumentException("no registered ImageReader claims to be able to read");
        }
        final var raster = image.getRaster();
        final var buffer = raster.getDataBuffer();
        if (buffer.getDataType() != DataBuffer.TYPE_BYTE) {
            throw new IllegalArgumentException(
                    "image.raster.dataBuffer.type(" + buffer.getDataType() +
                    ") != TYPE_BYTE(" + DataBuffer.TYPE_BYTE + ")");
        }
        final byte[] data = ((DataBufferByte) buffer).getData();
        setPicture(data);
    }

    @Transient
    public void setPictureReadingFrom(final java.io.File file) throws IOException {
        Objects.requireNonNull(file, "file is null");
        try (final var stream = new FileInputStream(file)) {
            setPictureReadingFrom(stream);
        }
    }

    @Transient
    public void setPictureReadingFrom(final java.nio.file.Path path) throws IOException {
        Objects.requireNonNull(path, "path is null");
        try (final var channel = FileChannel.open(path, StandardOpenOption.READ)) {
            setPictureReadingFrom(Channels.newInputStream(channel));
        }
    }

    /**
     * Returns current value of {@link Staff_#store store} attribute.
     *
     * @return current value of the {@link Staff_#store store} attribute.
     */
    public Store getStore() {
        return store;
    }

    /**
     * Replaces current value of {@link Staff_#store store} attribute with specified value.
     *
     * @param store new value for the {@link Staff_#store store} attribute.
     * @apiNote This method also updates current value of {@link Staff#storeId storeId} with {@code store?.storeId}.
     */
    public void setStore(final Store store) {
        this.store = store;
        setStoreId(
                Optional.ofNullable(this.store)
                        .map(Store::getStoreId)
                        .orElse(null)
        );
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_STORE_ID, nullable = false,
                insertable = false, // !!!
                updatable = false   // !!!
                )
    private Store store;

    /**
     * Returns current value of {@link Staff_#active active} attribute as a {@link Boolean} value.
     *
     * @return {@link Boolean#FALSE} if current value of {@link Staff_#active active} attribute is {@code 0};
     * {@link Boolean#FALSE} otherwise excluding {@code null}; {@code null} if current value of the
     * {@link Staff_#active active} attribute is {@code null}.
     */
    public Boolean getActiveAsBoolean() {
        return Optional.ofNullable(getActive())
                .map(_DomainConverters.BooleanConverter::intToBoolean)
                .orElse(null);
    }

    /**
     * Replaces current value of {@link Staff_#active active} attribute with specified {@code boolean} value.
     *
     * @param activeAsBoolean the {@code boolean} value for the {@link Staff_#active active} attribute;
     *                        {@link Boolean#FALSE} for {@code 0};  {@link Boolean#TRUE} for {@code 1}; {@code null} for
     *                        {@code null}.
     */
    public void setActiveAsBoolean(final Boolean activeAsBoolean) {
        setActive(
                Optional.ofNullable(activeAsBoolean)
                        .map(_DomainConverters.BooleanConverter::booleanToInt)
                        .orElse(null)
        );
    }

    public Staff activate() {
        setActiveAsBoolean(Boolean.TRUE);
        return this;
    }

    public Staff deactivate() {
        setActiveAsBoolean(Boolean.FALSE);
        return this;
    }

    /**
     * Signs with specified password.
     *
     * @param clientPassword the password to verity with.
     * @throws IllegalArgumentException if failed to sign in with {@code clientPassword}.
     */
    public void signIn(byte[] clientPassword) {
        if (clientPassword != null && clientPassword.length == 0) {
            throw new IllegalArgumentException("empty password");
        }
        final var password_ = getPassword();
        if (clientPassword == null) {
            if (password_ != null) {
                throw new IllegalStateException("unable to sign in without password");
            }
            log.warn("signed in without password; username: " + username);
            return;
        }
        final byte[] clientPassword_ = Arrays.copyOf(clientPassword, clientPassword.length);
        clientPassword = null;
        if (Objects.equals(SecurityUtils.sha1(clientPassword_), password_)) {
            log.info("signed in with sha1");
            if (false) {
                log.info("updating password with sha2...");
                setPassword(SecurityUtils.sha2(clientPassword_));
            }
            return;
        }
        if (Objects.equals(SecurityUtils.sha2(clientPassword_), password_)) {
            return;
        }
        throw new IllegalArgumentException("unable to sign in");
    }

    /**
     * Updates the password of this staff using specified values.
     *
     * @param oldClientPassword an old password to verify.
     * @param newClientPassword a new password to set.
     * @throws IllegalArgumentException if failed to sign in with {@code oldClientPassword}.
     */
    public void changePassword(final byte[] oldClientPassword, byte[] newClientPassword) {
        if (newClientPassword != null && newClientPassword.length == 0) {
            throw new IllegalArgumentException("empty new client password");
        }
        final byte[] newClientPassword_ = Optional.ofNullable(newClientPassword).map(v -> Arrays.copyOf(v, v.length)).orElse(null);
        newClientPassword = null;
        signIn(oldClientPassword);
        if (newClientPassword_ == null) {
            setPassword(null);
            return;
        }
        if (false) {
            setPassword(SecurityUtils.sha2(newClientPassword_));
        }
        setPassword(SecurityUtils.sha1(newClientPassword_));
    }
}
