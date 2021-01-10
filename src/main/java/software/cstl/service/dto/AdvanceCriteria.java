package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.PaymentStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link software.cstl.domain.Advance} entity. This class is used
 * in {@link software.cstl.web.rest.AdvanceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /advances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdvanceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PaymentStatus
     */
    public static class PaymentStatusFilter extends Filter<PaymentStatus> {

        public PaymentStatusFilter() {
        }

        public PaymentStatusFilter(PaymentStatusFilter filter) {
            super(filter);
        }

        @Override
        public PaymentStatusFilter copy() {
            return new PaymentStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter providedOn;

    private BigDecimalFilter amount;

    private BigDecimalFilter paymentPercentage;

    private BigDecimalFilter monthlyPaymentAmount;

    private PaymentStatusFilter paymentStatus;

    private BigDecimalFilter amountPaid;

    private BigDecimalFilter amountLeft;

    private LongFilter advancePaymentHistoryId;

    private LongFilter employeeId;

    public AdvanceCriteria() {
    }

    public AdvanceCriteria(AdvanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.providedOn = other.providedOn == null ? null : other.providedOn.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.paymentPercentage = other.paymentPercentage == null ? null : other.paymentPercentage.copy();
        this.monthlyPaymentAmount = other.monthlyPaymentAmount == null ? null : other.monthlyPaymentAmount.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.amountPaid = other.amountPaid == null ? null : other.amountPaid.copy();
        this.amountLeft = other.amountLeft == null ? null : other.amountLeft.copy();
        this.advancePaymentHistoryId = other.advancePaymentHistoryId == null ? null : other.advancePaymentHistoryId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public AdvanceCriteria copy() {
        return new AdvanceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getProvidedOn() {
        return providedOn;
    }

    public void setProvidedOn(LocalDateFilter providedOn) {
        this.providedOn = providedOn;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public BigDecimalFilter getPaymentPercentage() {
        return paymentPercentage;
    }

    public void setPaymentPercentage(BigDecimalFilter paymentPercentage) {
        this.paymentPercentage = paymentPercentage;
    }

    public BigDecimalFilter getMonthlyPaymentAmount() {
        return monthlyPaymentAmount;
    }

    public void setMonthlyPaymentAmount(BigDecimalFilter monthlyPaymentAmount) {
        this.monthlyPaymentAmount = monthlyPaymentAmount;
    }

    public PaymentStatusFilter getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimalFilter getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimalFilter amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimalFilter getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(BigDecimalFilter amountLeft) {
        this.amountLeft = amountLeft;
    }

    public LongFilter getAdvancePaymentHistoryId() {
        return advancePaymentHistoryId;
    }

    public void setAdvancePaymentHistoryId(LongFilter advancePaymentHistoryId) {
        this.advancePaymentHistoryId = advancePaymentHistoryId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdvanceCriteria that = (AdvanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(providedOn, that.providedOn) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(paymentPercentage, that.paymentPercentage) &&
            Objects.equals(monthlyPaymentAmount, that.monthlyPaymentAmount) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(amountPaid, that.amountPaid) &&
            Objects.equals(amountLeft, that.amountLeft) &&
            Objects.equals(advancePaymentHistoryId, that.advancePaymentHistoryId) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        providedOn,
        amount,
        paymentPercentage,
        monthlyPaymentAmount,
        paymentStatus,
        amountPaid,
        amountLeft,
        advancePaymentHistoryId,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (providedOn != null ? "providedOn=" + providedOn + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (paymentPercentage != null ? "paymentPercentage=" + paymentPercentage + ", " : "") +
                (monthlyPaymentAmount != null ? "monthlyPaymentAmount=" + monthlyPaymentAmount + ", " : "") +
                (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
                (amountPaid != null ? "amountPaid=" + amountPaid + ", " : "") +
                (amountLeft != null ? "amountLeft=" + amountLeft + ", " : "") +
                (advancePaymentHistoryId != null ? "advancePaymentHistoryId=" + advancePaymentHistoryId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
