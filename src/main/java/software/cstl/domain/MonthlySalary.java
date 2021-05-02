package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import software.cstl.domain.enumeration.MonthType;

import software.cstl.domain.enumeration.SalaryExecutionStatus;

/**
 * A MonthlySalary.
 */
@Entity
@Table(
    name = "monthly_salary",
    uniqueConstraints = @UniqueConstraint(columnNames = {"year","month","status","department_id"})
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MonthlySalary extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private MonthType month;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "to_date")
    private Instant toDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SalaryExecutionStatus status;

    @Column(name = "executed_on")
    private Instant executedOn;

    @Column(name = "executed_by")
    private String executedBy;

    @OneToMany(mappedBy = "monthlySalary", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MonthlySalaryDtl> monthlySalaryDtls = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "monthlySalaries", allowSetters = true)
    private Department department;

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

    public Instant getFromDate() {
        return fromDate;
    }

    public MonthlySalary fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public MonthlySalary toDate(Instant toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
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

    public String getExecutedBy() {
        return executedBy;
    }

    public MonthlySalary executedBy(String executedBy) {
        this.executedBy = executedBy;
        return this;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public Set<MonthlySalaryDtl> getMonthlySalaryDtls() {
        return monthlySalaryDtls;
    }

    public MonthlySalary monthlySalaryDtls(Set<MonthlySalaryDtl> monthlySalaryDtls) {
        this.monthlySalaryDtls = monthlySalaryDtls;
        return this;
    }

    public MonthlySalary addMonthlySalaryDtl(MonthlySalaryDtl monthlySalaryDtl) {
        this.monthlySalaryDtls.add(monthlySalaryDtl);
        monthlySalaryDtl.setMonthlySalary(this);
        return this;
    }

    public MonthlySalary removeMonthlySalaryDtl(MonthlySalaryDtl monthlySalaryDtl) {
        this.monthlySalaryDtls.remove(monthlySalaryDtl);
        monthlySalaryDtl.setMonthlySalary(null);
        return this;
    }

    public void setMonthlySalaryDtls(Set<MonthlySalaryDtl> monthlySalaryDtls) {
        this.monthlySalaryDtls = monthlySalaryDtls;
    }

    public Department getDepartment() {
        return department;
    }

    public MonthlySalary department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", executedOn='" + getExecutedOn() + "'" +
            ", executedBy='" + getExecutedBy() + "'" +
            "}";
    }
}
