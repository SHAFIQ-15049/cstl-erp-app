package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import software.cstl.domain.enumeration.PaymentStatus;

/**
 * A Advance.
 */
@Entity
@Table(name = "advance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Advance extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "provided_on", nullable = false)
    private LocalDate providedOn;

    @Lob
    @Column(name = "reason")
    private String reason;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "payment_percentage", precision = 21, scale = 2, nullable = false)
    private BigDecimal paymentPercentage;

    @NotNull
    @Column(name = "monthly_payment_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal monthlyPaymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "amount_paid", precision = 21, scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "amount_left", precision = 21, scale = 2)
    private BigDecimal amountLeft;

    @OneToMany(mappedBy = "advance")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AdvancePaymentHistory> advancePaymentHistories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "advances", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getProvidedOn() {
        return providedOn;
    }

    public Advance providedOn(LocalDate providedOn) {
        this.providedOn = providedOn;
        return this;
    }

    public void setProvidedOn(LocalDate providedOn) {
        this.providedOn = providedOn;
    }

    public String getReason() {
        return reason;
    }

    public Advance reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Advance amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaymentPercentage() {
        return paymentPercentage;
    }

    public Advance paymentPercentage(BigDecimal paymentPercentage) {
        this.paymentPercentage = paymentPercentage;
        return this;
    }

    public void setPaymentPercentage(BigDecimal paymentPercentage) {
        this.paymentPercentage = paymentPercentage;
    }

    public BigDecimal getMonthlyPaymentAmount() {
        return monthlyPaymentAmount;
    }

    public Advance monthlyPaymentAmount(BigDecimal monthlyPaymentAmount) {
        this.monthlyPaymentAmount = monthlyPaymentAmount;
        return this;
    }

    public void setMonthlyPaymentAmount(BigDecimal monthlyPaymentAmount) {
        this.monthlyPaymentAmount = monthlyPaymentAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Advance paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public Advance amountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getAmountLeft() {
        return amountLeft;
    }

    public Advance amountLeft(BigDecimal amountLeft) {
        this.amountLeft = amountLeft;
        return this;
    }

    public void setAmountLeft(BigDecimal amountLeft) {
        this.amountLeft = amountLeft;
    }

    public Set<AdvancePaymentHistory> getAdvancePaymentHistories() {
        return advancePaymentHistories;
    }

    public Advance advancePaymentHistories(Set<AdvancePaymentHistory> advancePaymentHistories) {
        this.advancePaymentHistories = advancePaymentHistories;
        return this;
    }

    public Advance addAdvancePaymentHistory(AdvancePaymentHistory advancePaymentHistory) {
        this.advancePaymentHistories.add(advancePaymentHistory);
        advancePaymentHistory.setAdvance(this);
        return this;
    }

    public Advance removeAdvancePaymentHistory(AdvancePaymentHistory advancePaymentHistory) {
        this.advancePaymentHistories.remove(advancePaymentHistory);
        advancePaymentHistory.setAdvance(null);
        return this;
    }

    public void setAdvancePaymentHistories(Set<AdvancePaymentHistory> advancePaymentHistories) {
        this.advancePaymentHistories = advancePaymentHistories;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Advance employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Advance)) {
            return false;
        }
        return id != null && id.equals(((Advance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Advance{" +
            "id=" + getId() +
            ", providedOn='" + getProvidedOn() + "'" +
            ", reason='" + getReason() + "'" +
            ", amount=" + getAmount() +
            ", paymentPercentage=" + getPaymentPercentage() +
            ", monthlyPaymentAmount=" + getMonthlyPaymentAmount() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", amountPaid=" + getAmountPaid() +
            ", amountLeft=" + getAmountLeft() +
            "}";
    }
}
