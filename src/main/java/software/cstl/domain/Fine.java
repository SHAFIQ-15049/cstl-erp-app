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

import org.hibernate.annotations.ColumnDefault;
import software.cstl.domain.enumeration.PaymentStatus;

/**
 * A Fine.
 */
@Entity
@Table(name = "fine")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fine extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fined_on", nullable = false)
    private LocalDate finedOn;

    @Lob
    @Column(name = "reason")
    private String reason;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "fine_percentage", precision = 21, scale = 2, nullable = false)
    private BigDecimal finePercentage;

    @NotNull
    @Column(name = "monthly_fine_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal monthlyFineAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "amount_paid", precision = 21, scale = 2)
    @ColumnDefault("0")
    private BigDecimal amountPaid = BigDecimal.ZERO;

    @Column(name = "amount_left", precision = 21, scale = 2)
    @ColumnDefault("0")
    private BigDecimal amountLeft = BigDecimal.ZERO;

    @OneToMany(mappedBy = "fine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<FinePaymentHistory> finePaymentHistories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "fines", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFinedOn() {
        return finedOn;
    }

    public Fine finedOn(LocalDate finedOn) {
        this.finedOn = finedOn;
        return this;
    }

    public void setFinedOn(LocalDate finedOn) {
        this.finedOn = finedOn;
    }

    public String getReason() {
        return reason;
    }

    public Fine reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Fine amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFinePercentage() {
        return finePercentage;
    }

    public Fine finePercentage(BigDecimal finePercentage) {
        this.finePercentage = finePercentage;
        return this;
    }

    public void setFinePercentage(BigDecimal finePercentage) {
        this.finePercentage = finePercentage;
    }

    public BigDecimal getMonthlyFineAmount() {
        return monthlyFineAmount;
    }

    public Fine monthlyFineAmount(BigDecimal monthlyFineAmount) {
        this.monthlyFineAmount = monthlyFineAmount;
        return this;
    }

    public void setMonthlyFineAmount(BigDecimal monthlyFineAmount) {
        this.monthlyFineAmount = monthlyFineAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Fine paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public Fine amountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getAmountLeft() {
        return amountLeft;
    }

    public Fine amountLeft(BigDecimal amountLeft) {
        this.amountLeft = amountLeft;
        return this;
    }

    public void setAmountLeft(BigDecimal amountLeft) {
        this.amountLeft = amountLeft;
    }

    public Set<FinePaymentHistory> getFinePaymentHistories() {
        return finePaymentHistories;
    }

    public Fine finePaymentHistories(Set<FinePaymentHistory> finePaymentHistories) {
        this.finePaymentHistories = finePaymentHistories;
        return this;
    }

    public Fine addFinePaymentHistory(FinePaymentHistory finePaymentHistory) {
        this.finePaymentHistories.add(finePaymentHistory);
        finePaymentHistory.setFine(this);
        return this;
    }

    public Fine removeFinePaymentHistory(FinePaymentHistory finePaymentHistory) {
        this.finePaymentHistories.remove(finePaymentHistory);
        finePaymentHistory.setFine(null);
        return this;
    }

    public void setFinePaymentHistories(Set<FinePaymentHistory> finePaymentHistories) {
        this.finePaymentHistories = finePaymentHistories;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Fine employee(Employee employee) {
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
        if (!(o instanceof Fine)) {
            return false;
        }
        return id != null && id.equals(((Fine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fine{" +
            "id=" + getId() +
            ", finedOn='" + getFinedOn() + "'" +
            ", reason='" + getReason() + "'" +
            ", amount=" + getAmount() +
            ", finePercentage=" + getFinePercentage() +
            ", monthlyFineAmount=" + getMonthlyFineAmount() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", amountPaid=" + getAmountPaid() +
            ", amountLeft=" + getAmountLeft() +
            "}";
    }
}
