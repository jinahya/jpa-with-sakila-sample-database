package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * An entity class for mapping {@value #TABLE_NAME} table.
 * <p>
 * <blockquote>
 * The {@value TABLE_NAME} table lists all staff members, including information for email address, login information,
 * and picture.<br/>The {@value #TABLE_NAME} table refers to the {@value Store#TABLE_NAME} and
 * {@value Address#TABLE_NAME} tables using foreign keys, and is referred to by the {@value MappedRental#TABLE_NAME},
 * {@value MappedPayment#TABLE_NAME}, and {@value Store#TABLE_NAME} tables.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-staff.html">5.1.15 The staff Table</a>
 */
@Entity
@Table(name = Staff.TABLE_NAME)
@Slf4j
public class Staff
        extends _BaseEntity<Integer> {

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
     * Creates a new instance with specified value of {@link Staff_#staffId staffId} attribute.
     *
     * @param staffId the value of {@link Staff_#staffId staffId} attribute.
     * @return a new instance with {@code staffId}.
     */
    public static Staff of(final int staffId) {
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
    protected Integer identifier() {
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

    public void setAddressId(final Integer addressId) {
        this.addressId = addressId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(final byte[] picture) {
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

    private void setStoreId(final Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(final Integer active) {
        this.active = active;
    }

    public Boolean getActiveAsBoolean() {
        // TODO: Implement
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void setActiveAsBoolean(final Boolean activeAsBoolean) {
        // TODO: Implement!
        throw new UnsupportedOperationException("not implemented yet");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    private String getPassword() {
        return password;
    }

    private void setPassword(final String password) {
        this.password = password;
    }

    /**
     * A surrogate primary key that uniquely identifies the staff member.
     */
    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_STAFF_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer staffId;

    /**
     * The first name of the staff member.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    /**
     * The last name of the staff member.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    /**
     * A foreign key to the staff member {@value Address#TABLE_NAME} in the address table.
     */
    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ADDRESS_ID, nullable = false)
    private Integer addressId;

    /**
     * A {@code BLOB} containing a photograph of the employee.
     */
    @Basic(optional = true)
    @Column(name = "picture", nullable = true)
    private byte[] picture;

    /**
     * The staff member email address.
     */
    @Email
    @Basic(optional = true)
    @Column(name = "email", nullable = true, length = 50)
    private String email;

    /**
     * The staff member “home store.” The employee can work at other stores but is generally assigned to the store
     * listed.
     */
    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_STORE_ID, nullable = false)
    private Integer storeId;

    /**
     * Whether this is an active employee. If employees leave, their rows are not deleted from this table; instead, this
     * column is set to {@code FALSE}.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    private Integer active;

    /**
     * The user name used by the staff member to access the rental system.
     */
    @NotNull
    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 16, updatable = false)
    private String username;

    /**
     * The password used by the staff member to access the rental system. The password should be stored as a hash using
     * the {@code SHA2()} function.
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
    @OneToOne(optional = false, fetch = FetchType.LAZY,
              cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = COLUMN_NAME_ADDRESS_ID, nullable = false, insertable = false, updatable = false)
    private Address address;

    /**
     * Returns current value of {@link Store_#store store} attribute.
     *
     * @return current value of the {@link Store_#store store} attribute.
     */
    public Store getStore() {
        return store;
    }

    /**
     * Replaces current value of {@link Store_#store store} attribute with specified value.
     *
     * @param store new value for the {@link Store_#store store} attribute.
     * @apiNote This method also updates current value of {@link Store#storeId storeId} with {@code store?.storeId}.
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
    @JoinColumn(name = COLUMN_NAME_STORE_ID, nullable = false, insertable = false, updatable = false)
    private Store store;

    /**
     * Signs with specified password.
     *
     * @param clientPassword the password to verity with.
     */
    public void signIn(final byte[] clientPassword) {
        if (clientPassword == null) {
            if (this.password != null) {
                throw new IllegalStateException("unable to sign in without password");
            }
            log.warn("signing in without password; username: " + username);
            return;
        }
        final byte[] copy = Arrays.copyOf(clientPassword, clientPassword.length);
        if (Objects.equals(_PersistenceUtils.sha1(copy), password)) {
            log.info("signed in with sha1. updating password with sha2...");
            setPassword(_PersistenceUtils.sha2(copy));
            return;
        }
        if (Objects.equals(_PersistenceUtils.sha2(copy), password)) {
            return;
        }
        throw new IllegalStateException("unable to sign in");
    }

    /**
     * Updates the password of this staff with specified value.
     *
     * @param oldClientPassword an old password to verify.
     * @param newClientPassword a new password to update.
     */
    public void changePassword(final byte[] oldClientPassword, final byte[] newClientPassword) {
        if (Objects.requireNonNull(newClientPassword, "newClientPassword is null").length == 0) {
            throw new IllegalArgumentException("empty new client password");
        }
        signIn(oldClientPassword);
        setPassword(_PersistenceUtils.sha2(newClientPassword));
    }
}
