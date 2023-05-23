package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

/**
 * An entity class for mapping {@value #VIEW_NAME} view.
 * <p>
 * <cite><a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-views-staff-list.html">5.2.7 The staff_list
 * View</a></cite>
 * <blockquote cite="https://dev.mysql.com/doc/sakila/en/sakila-structure-views-staff-list.html">
 * <p>The {@value #VIEW_NAME} view provides a list of all staff members, including address and store information.</p>
 * <p>The {@value #VIEW_NAME} view incorporates data from the {@value Staff#TABLE_NAME} and {@value Address#TABLE_NAME}
 * tables.</p>
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-views-staff-list.html">5.2.7 The staff_list
 * View</a>
 */
@NamedQuery(name = StaffListConstants.NAMED_QUERY_FIND_BY_ID,
            query = "SELECT e FROM StaffList AS e WHERE e.id = :id")
@NamedQuery(name = StaffListConstants.NAMED_QUERY_FIND_ALL,
            query = "SELECT e FROM StaffList AS e")
@Entity
@Table(name = StaffList.VIEW_NAME)
public class StaffList
        extends __BaseEntity<Integer> {

    /**
     * The name of the database view to which this class maps. The value is {@value}.
     */
    public static final String VIEW_NAME = "staff_list";

    /**
     * Creates a new instance.
     */
    protected StaffList() {
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
               ",sid=" + sid +
               '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StaffList that)) return false;
        return equals_(that);
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

    public Integer getSid() {
        return sid;
    }

    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @NotNull
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Basic(optional = true)
    @Column(name = "name", nullable = true, length = 91, insertable = false, updatable = false)
    private String name;

    @NotNull
    @Basic(optional = false)
    @Column(name = "address", nullable = false, length = 50, insertable = false, updatable = false)
    private String address;

    @Basic(optional = true)
    @Column(name = "zip code", nullable = true, length = 10, insertable = false, updatable = false)
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

    @Max(_PersistenceConstants.MAX_TINYINT_UNSIGNED)
    @PositiveOrZero
    @Basic(optional = false)
    @Column(name = "SID", nullable = false, insertable = false, updatable = false)
    private Integer sid;
}
