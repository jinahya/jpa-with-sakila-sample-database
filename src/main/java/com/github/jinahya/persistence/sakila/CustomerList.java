package com.github.jinahya.persistence.sakila;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

/**
 * An entity class for mapping {@value #VIEW_NAME} view.
 * <p>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-views-customer_list.html">
 * The {@code customer_list} view provides a list of customers, with first name and last name concatenated together and
 * address information combined into a single view.<br/>The {@code customer_list} view incorporates data from the
 * {@value Customer#TABLE_NAME}, {@value Address#TABLE_NAME}, {@value City#TABLE_NAME}, and {@value Country#TABLE_NAME}
 * tables.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-views-customer_list.html">5.2.2 The customer_list
 * View</a>
 */
@Entity
@Table(name = CustomerList.VIEW_NAME)
public class CustomerList
        extends __BaseEntity<Integer> {

    /**
     * The name of the database view to which this class maps. The value is {@value}.
     */
    public static final String VIEW_NAME = "customer_list";

    public static final String COLUMN_NAME_ID = "ID";

    public static final String COLUMN_NAME_SID = "SID";

    /**
     * Creates a new instance.
     */
    protected CustomerList() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
               "id=" + id +
               ",name=" + name +
               ",address=" + address +
               ",zipCode=" + zipCode +
               ",phone=" + phone +
               ",city=" + city +
               ",country=" + country +
               ",notes=" + notes +
               ",sid=" + sid +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CustomerList)) return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    Integer identifier() {
        return getId();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getNotes() {
        return notes;
    }

    public Integer getSid() {
        return sid;
    }

    @Max(_PersistenceConstants.MAX_SMALLINT_UNSIGNED)
    @PositiveOrZero
    @Id
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ID, nullable = false,
            insertable = /*false*/true, // EclipseLink
            updatable = false)
    private Integer id;

    @Basic(optional = true)
    @Column(name = "name", nullable = true, length = 91, insertable = false, updatable = false)
    private String name;

    @NotNull
    @Basic(optional = false)
    @Column(name = "address", nullable = false, length = 50, insertable = false, updatable = false)
    private String address;

    @Basic(optional = true)
//    @Column(name = "`zip code`", nullable = true, length = 10, insertable = false, updatable = false)
    @Column(name = "\"zip code\"", nullable = true, length = 10, insertable = false, updatable = false)
    private String zipCode;

    @NotNull
    @Basic(optional = false)
    @Column(name = "phone", nullable = false, length = 20, insertable = false, updatable = false)
    private String phone;

    @NotNull
    @Basic(optional = false)
    @Column(name = "city", nullable = false, length = 50, insertable = false, updatable = false)
    private String city;

    @NotNull
    @Basic(optional = false)
    @Column(name = "country", nullable = false, length = 50, insertable = false, updatable = false)
    private String country;

    @NotNull
    @Basic(optional = false)
    @Column(name = "notes", nullable = false, length = 6, insertable = false, updatable = false)
    private String notes;

    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_SID, nullable = false, insertable = false, updatable = false)
    private Integer sid;
}
