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
 * Criteria class for the {@link software.cstl.domain.Fine} entity. This class is used
 * in {@link software.cstl.web.rest.FineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FineCriteria implements Serializable, Criteria {
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

    private LocalDateFilter finedOn;

    private BigDecimalFilter amount;

    private BigDecimalFilter finePercentage;

    private BigDecimalFilter monthlyFineAmount;

    private PaymentStatusFilter paymentStatus;

    private BigDecimalFilter amountPaid;

    private BigDecimalFilter amountLeft;

    private LongFilter finePaymentHistoryId;

    private LongFilter employeeId;

    public FineCriteria() {
    }

    public FineCriteria(FineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.finedOn = other.finedOn == null ? null : other.finedOn.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.finePercentage = other.finePercentage == null ? null : other.finePercentage.copy();
        this.monthlyFineAmount = other.monthlyFineAmount == null ? null : other.monthlyFineAmount.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.amountPaid = other.amountPaid == null ? null : other.amountPaid.copy();
        this.amountLeft = other.amountLeft == null ? null : other.amountLeft.copy();
        this.finePaymentHistoryId = other.finePaymentHistoryId == null ? null : other.finePaymentHistoryId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public FineCriteria copy() {
        return new FineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getFinedOn() {
        return finedOn;
    }

    public void setFinedOn(LocalDateFilter finedOn) {
        this.finedOn = finedOn;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public BigDecimalFilter getFinePercentage() {
        return finePercentage;
    }

    public void setFinePercentage(BigDecimalFilter finePercentage) {
        this.finePercentage = finePercentage;
    }

    public BigDecimalFilter getMonthlyFineAmount() {
        return monthlyFineAmount;
    }

    public void setMonthlyFineAmount(BigDecimalFilter monthlyFineAmount) {
        this.monthlyFineAmount = monthlyFineAmount;
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

    public LongFilter getFinePaymentHistoryId() {
        return finePaymentHistoryId;
    }

    public void setFinePaymentHistoryId(LongFilter finePaymentHistoryId) {
        this.finePaymentHistoryId = finePaymentHistoryId;
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
        final FineCriteria that = (FineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(finedOn, that.finedOn) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(finePercentage, that.finePercentage) &&
            Objects.equals(monthlyFineAmount, that.monthlyFineAmount) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(amountPaid, that.amountPaid) &&
            Objects.equals(amountLeft, that.amountLeft) &&
            Objects.equals(finePaymentHistoryId, that.finePaymentHistoryId) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        finedOn,
        amount,
        finePercentage,
        monthlyFineAmount,
        paymentStatus,
        amountPaid,
        amountLeft,
        finePaymentHistoryId,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (finedOn != null ? "finedOn=" + finedOn + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (finePercentage != null ? "finePercentage=" + finePercentage + ", " : "") +
                (monthlyFineAmount != null ? "monthlyFineAmount=" + monthlyFineAmount + ", " : "") +
                (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
                (amountPaid != null ? "amountPaid=" + amountPaid + ", " : "") +
                (amountLeft != null ? "amountLeft=" + amountLeft + ", " : "") +
                (finePaymentHistoryId != null ? "finePaymentHistoryId=" + finePaymentHistoryId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
