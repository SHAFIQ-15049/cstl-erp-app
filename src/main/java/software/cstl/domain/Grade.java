package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

import software.cstl.domain.enumeration.EmployeeCategory;

/**
 * A Grade.
 */
@Entity
@Table(name = "mst_grade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Grade extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private EmployeeCategory category;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "initial_salary", precision = 21, scale = 2)
    private BigDecimal initialSalary;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeCategory getCategory() {
        return category;
    }

    public Grade category(EmployeeCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(EmployeeCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public Grade name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Grade description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getInitialSalary() {
        return initialSalary;
    }

    public Grade initialSalary(BigDecimal initialSalary) {
        this.initialSalary = initialSalary;
        return this;
    }

    public void setInitialSalary(BigDecimal initialSalary) {
        this.initialSalary = initialSalary;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grade)) {
            return false;
        }
        return id != null && id.equals(((Grade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Grade{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", initialSalary=" + getInitialSalary() +
            "}";
    }
}
