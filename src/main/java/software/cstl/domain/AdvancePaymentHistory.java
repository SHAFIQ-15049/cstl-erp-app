package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

import software.cstl.domain.enumeration.MonthType;

/**
 * A AdvancePaymentHistory.
 */
@Entity
@Table(name = "advance_payment_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvancePaymentHistory extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "jhi_before", precision = 21, scale = 2)
    private BigDecimal before;

    @Column(name = "after", precision = 21, scale = 2)
    private BigDecimal after;

    @ManyToOne
    @JsonIgnoreProperties(value = "advancePaymentHistories", allowSetters = true)
    private Advance advance;

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

    public AdvancePaymentHistory year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MonthType getMonthType() {
        return monthType;
    }

    public AdvancePaymentHistory monthType(MonthType monthType) {
        this.monthType = monthType;
        return this;
    }

    public void setMonthType(MonthType monthType) {
        this.monthType = monthType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public AdvancePaymentHistory amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBefore() {
        return before;
    }

    public AdvancePaymentHistory before(BigDecimal before) {
        this.before = before;
        return this;
    }

    public void setBefore(BigDecimal before) {
        this.before = before;
    }

    public BigDecimal getAfter() {
        return after;
    }

    public AdvancePaymentHistory after(BigDecimal after) {
        this.after = after;
        return this;
    }

    public void setAfter(BigDecimal after) {
        this.after = after;
    }

    public Advance getAdvance() {
        return advance;
    }

    public AdvancePaymentHistory advance(Advance advance) {
        this.advance = advance;
        return this;
    }

    public void setAdvance(Advance advance) {
        this.advance = advance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvancePaymentHistory)) {
            return false;
        }
        return id != null && id.equals(((AdvancePaymentHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvancePaymentHistory{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", monthType='" + getMonthType() + "'" +
            ", amount=" + getAmount() +
            ", before=" + getBefore() +
            ", after=" + getAfter() +
            "}";
    }
}
