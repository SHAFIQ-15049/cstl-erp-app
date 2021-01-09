package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

import software.cstl.domain.enumeration.MonthType;

/**
 * A FinePaymentHistory.
 */
@Entity
@Table(name = "fine_payment_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FinePaymentHistory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "month_type")
    private MonthType monthType;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "before_fine", precision = 21, scale = 2)
    private BigDecimal beforeFine;

    @Column(name = "after_fine", precision = 21, scale = 2)
    private BigDecimal afterFine;

    @ManyToOne
    @JsonIgnoreProperties(value = "finePaymentHistories", allowSetters = true)
    private Fine fine;

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

    public FinePaymentHistory year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MonthType getMonthType() {
        return monthType;
    }

    public FinePaymentHistory monthType(MonthType monthType) {
        this.monthType = monthType;
        return this;
    }

    public void setMonthType(MonthType monthType) {
        this.monthType = monthType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public FinePaymentHistory amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBeforeFine() {
        return beforeFine;
    }

    public FinePaymentHistory beforeFine(BigDecimal beforeFine) {
        this.beforeFine = beforeFine;
        return this;
    }

    public void setBeforeFine(BigDecimal beforeFine) {
        this.beforeFine = beforeFine;
    }

    public BigDecimal getAfterFine() {
        return afterFine;
    }

    public FinePaymentHistory afterFine(BigDecimal afterFine) {
        this.afterFine = afterFine;
        return this;
    }

    public void setAfterFine(BigDecimal afterFine) {
        this.afterFine = afterFine;
    }

    public Fine getFine() {
        return fine;
    }

    public FinePaymentHistory fine(Fine fine) {
        this.fine = fine;
        return this;
    }

    public void setFine(Fine fine) {
        this.fine = fine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FinePaymentHistory)) {
            return false;
        }
        return id != null && id.equals(((FinePaymentHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FinePaymentHistory{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", monthType='" + getMonthType() + "'" +
            ", amount=" + getAmount() +
            ", beforeFine=" + getBeforeFine() +
            ", afterFine=" + getAfterFine() +
            "}";
    }
}
