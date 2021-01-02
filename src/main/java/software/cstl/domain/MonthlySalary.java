package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import software.cstl.domain.enumeration.MonthType;

import software.cstl.domain.enumeration.SalaryExecutionStatus;

/**
 * A MonthlySalary.
 */
@Entity
@Table(name = "monthly_salary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MonthlySalary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private MonthType month;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SalaryExecutionStatus status;

    @Column(name = "executed_on")
    private Instant executedOn;

    @Column(name = "executed_by")
    private Instant executedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "monthlySalaries", allowSetters = true)
    private Designation designation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public MonthlySalary year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MonthType getMonth() {
        return month;
    }

    public MonthlySalary month(MonthType month) {
        this.month = month;
        return this;
    }

    public void setMonth(MonthType month) {
        this.month = month;
    }

    public SalaryExecutionStatus getStatus() {
        return status;
    }

    public MonthlySalary status(SalaryExecutionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SalaryExecutionStatus status) {
        this.status = status;
    }

    public Instant getExecutedOn() {
        return executedOn;
    }

    public MonthlySalary executedOn(Instant executedOn) {
        this.executedOn = executedOn;
        return this;
    }

    public void setExecutedOn(Instant executedOn) {
        this.executedOn = executedOn;
    }

    public Instant getExecutedBy() {
        return executedBy;
    }

    public MonthlySalary executedBy(Instant executedBy) {
        this.executedBy = executedBy;
        return this;
    }

    public void setExecutedBy(Instant executedBy) {
        this.executedBy = executedBy;
    }

    public Designation getDesignation() {
        return designation;
    }

    public MonthlySalary designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthlySalary)) {
            return false;
        }
        return id != null && id.equals(((MonthlySalary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlySalary{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", month='" + getMonth() + "'" +
            ", status='" + getStatus() + "'" +
            ", executedOn='" + getExecutedOn() + "'" +
            ", executedBy='" + getExecutedBy() + "'" +
            "}";
    }
}
