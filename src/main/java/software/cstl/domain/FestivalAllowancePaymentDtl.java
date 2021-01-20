package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import software.cstl.domain.enumeration.SalaryExecutionStatus;

/**
 * A FestivalAllowancePaymentDtl.
 */
@Entity
@Table(name = "festival_allowance_payment_dtl")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FestivalAllowancePaymentDtl extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SalaryExecutionStatus status;

    @Column(name = "executed_on")
    private Instant executedOn;

    @Column(name = "executed_by")
    private String executedBy;

    @Lob
    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties(value = "festivalAllowancePaymentDtls", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "festivalAllowancePaymentDtls", allowSetters = true)
    private FestivalAllowancePayment festivalAllowancePayment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public FestivalAllowancePaymentDtl amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public SalaryExecutionStatus getStatus() {
        return status;
    }

    public FestivalAllowancePaymentDtl status(SalaryExecutionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SalaryExecutionStatus status) {
        this.status = status;
    }

    public Instant getExecutedOn() {
        return executedOn;
    }

    public FestivalAllowancePaymentDtl executedOn(Instant executedOn) {
        this.executedOn = executedOn;
        return this;
    }

    public void setExecutedOn(Instant executedOn) {
        this.executedOn = executedOn;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public FestivalAllowancePaymentDtl executedBy(String executedBy) {
        this.executedBy = executedBy;
        return this;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public String getNote() {
        return note;
    }

    public FestivalAllowancePaymentDtl note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Employee getEmployee() {
        return employee;
    }

    public FestivalAllowancePaymentDtl employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public FestivalAllowancePayment getFestivalAllowancePayment() {
        return festivalAllowancePayment;
    }

    public FestivalAllowancePaymentDtl festivalAllowancePayment(FestivalAllowancePayment festivalAllowancePayment) {
        this.festivalAllowancePayment = festivalAllowancePayment;
        return this;
    }

    public void setFestivalAllowancePayment(FestivalAllowancePayment festivalAllowancePayment) {
        this.festivalAllowancePayment = festivalAllowancePayment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FestivalAllowancePaymentDtl)) {
            return false;
        }
        return id != null && id.equals(((FestivalAllowancePaymentDtl) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FestivalAllowancePaymentDtl{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", executedOn='" + getExecutedOn() + "'" +
            ", executedBy='" + getExecutedBy() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
