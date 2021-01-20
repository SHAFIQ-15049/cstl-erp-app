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
 * A FestivalAllowancePayment.
 */
@Entity
@Table(
    name = "festival_allowance_payment",
    uniqueConstraints = @UniqueConstraint(columnNames = {"year","month","status"})
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FestivalAllowancePayment extends AbstractAuditingEntity implements Serializable {

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
    private String executedBy;

    @OneToMany(mappedBy = "festivalAllowancePayment", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<FestivalAllowancePaymentDtl> festivalAllowancePaymentDtls = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "festivalAllowancePayments", allowSetters = true)
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

    public FestivalAllowancePayment year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MonthType getMonth() {
        return month;
    }

    public FestivalAllowancePayment month(MonthType month) {
        this.month = month;
        return this;
    }

    public void setMonth(MonthType month) {
        this.month = month;
    }

    public SalaryExecutionStatus getStatus() {
        return status;
    }

    public FestivalAllowancePayment status(SalaryExecutionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SalaryExecutionStatus status) {
        this.status = status;
    }

    public Instant getExecutedOn() {
        return executedOn;
    }

    public FestivalAllowancePayment executedOn(Instant executedOn) {
        this.executedOn = executedOn;
        return this;
    }

    public void setExecutedOn(Instant executedOn) {
        this.executedOn = executedOn;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public FestivalAllowancePayment executedBy(String executedBy) {
        this.executedBy = executedBy;
        return this;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public Set<FestivalAllowancePaymentDtl> getFestivalAllowancePaymentDtls() {
        return festivalAllowancePaymentDtls;
    }

    public FestivalAllowancePayment festivalAllowancePaymentDtls(Set<FestivalAllowancePaymentDtl> festivalAllowancePaymentDtls) {
        this.festivalAllowancePaymentDtls = festivalAllowancePaymentDtls;
        return this;
    }

    public FestivalAllowancePayment addFestivalAllowancePaymentDtl(FestivalAllowancePaymentDtl festivalAllowancePaymentDtl) {
        this.festivalAllowancePaymentDtls.add(festivalAllowancePaymentDtl);
        festivalAllowancePaymentDtl.setFestivalAllowancePayment(this);
        return this;
    }

    public FestivalAllowancePayment removeFestivalAllowancePaymentDtl(FestivalAllowancePaymentDtl festivalAllowancePaymentDtl) {
        this.festivalAllowancePaymentDtls.remove(festivalAllowancePaymentDtl);
        festivalAllowancePaymentDtl.setFestivalAllowancePayment(null);
        return this;
    }

    public void setFestivalAllowancePaymentDtls(Set<FestivalAllowancePaymentDtl> festivalAllowancePaymentDtls) {
        this.festivalAllowancePaymentDtls = festivalAllowancePaymentDtls;
    }

    public Designation getDesignation() {
        return designation;
    }

    public FestivalAllowancePayment designation(Designation designation) {
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
        if (!(o instanceof FestivalAllowancePayment)) {
            return false;
        }
        return id != null && id.equals(((FestivalAllowancePayment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FestivalAllowancePayment{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", month='" + getMonth() + "'" +
            ", status='" + getStatus() + "'" +
            ", executedOn='" + getExecutedOn() + "'" +
            ", executedBy='" + getExecutedBy() + "'" +
            "}";
    }
}
