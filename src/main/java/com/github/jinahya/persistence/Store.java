package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = Store.TABLE_NAME)
public class Store
        extends _BaseEntity<Integer> {

    /**
     * The name of the database table to which this class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "store";

    public static final String COLUMN_NAME_STORE_ID = "store_id";

    public static final String COLUMN_NAME_MANAGE_STAFF_ID = "manager_staff_id";

    public static final String COLUMN_NAME_MANAGE_ADDRESS_ID = Address.COLUMN_NAME_ADDRESS_ID;

    @Override
    protected Integer identifier() {
        return getStoreId();
    }

    public Integer getStoreId() {
        return storeId;
    }

    @Deprecated
    private void setStoreId(final Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getManagerStaffId() {
        return managerStaffId;
    }

    public void setManagerStaffId(final Integer managerStaffId) {
        this.managerStaffId = managerStaffId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(final Integer addressId) {
        this.addressId = addressId;
    }

    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_STORE_ID, nullable = false,
            insertable = true, // EclipseLink
            updatable = false)
    private Integer storeId;

    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_MANAGE_STAFF_ID, nullable = false)
    private Integer managerStaffId;

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_MANAGE_ADDRESS_ID, nullable = false)
    private Integer addressId;

    // TODO: Map for a Staff!

    // TODO: Map for a Address!
}
