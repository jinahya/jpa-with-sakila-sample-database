package com.github.jinahya.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "language", schema = "sakila", catalog = "")
public class MappedLanguage {

    public static final String TABLE_NAME = "language";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "language_id", nullable = false)
    private Object languageId;

    @Basic
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Basic
    @Column(name = "last_update", nullable = false)
    private Timestamp lastUpdate;

    public Object getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Object languageId) {
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        MappedLanguage that = (MappedLanguage) obj;

        if (languageId != null ? !languageId.equals(that.languageId) : that.languageId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = languageId != null ? languageId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }
}
