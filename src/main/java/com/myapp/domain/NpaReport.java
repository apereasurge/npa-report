package com.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NpaReport.
 */
@Entity
@Table(name = "npa_report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NpaReport implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "npa_id")
    private String npaId;

    @Column(name = "location")
    private String location;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNpaId() {
        return npaId;
    }

    public NpaReport npaId(String npaId) {
        this.npaId = npaId;
        return this;
    }

    public void setNpaId(String npaId) {
        this.npaId = npaId;
    }

    public String getLocation() {
        return location;
    }

    public NpaReport location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NpaReport)) {
            return false;
        }
        return id != null && id.equals(((NpaReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NpaReport{" +
            "id=" + getId() +
            ", npaId='" + getNpaId() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
