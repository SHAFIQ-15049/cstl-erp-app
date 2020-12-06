package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Division.
 */
@Entity
@Table(name = "mst_division")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Division extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "bangla", nullable = false)
    private String bangla;

    @Column(name = "web")
    private String web;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Division name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBangla() {
        return bangla;
    }

    public Division bangla(String bangla) {
        this.bangla = bangla;
        return this;
    }

    public void setBangla(String bangla) {
        this.bangla = bangla;
    }

    public String getWeb() {
        return web;
    }

    public Division web(String web) {
        this.web = web;
        return this;
    }

    public void setWeb(String web) {
        this.web = web;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Division)) {
            return false;
        }
        return id != null && id.equals(((Division) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Division{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bangla='" + getBangla() + "'" +
            ", web='" + getWeb() + "'" +
            "}";
    }
}
