package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.SalaryExecutionStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link software.cstl.domain.FestivalAllowancePaymentDtl} entity. This class is used
 * in {@link software.cstl.web.rest.FestivalAllowancePaymentDtlResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /festival-allowance-payment-dtls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FestivalAllowancePaymentDtlCriteria implements Serializable, Criteria {
    /**
     * Class for filtering SalaryExecutionStatus
     */
    public static class SalaryExecutionStatusFilter extends Filter<SalaryExecutionStatus> {

        public SalaryExecutionStatusFilter() {
        }

        public SalaryExecutionStatusFilter(SalaryExecutionStatusFilter filter) {
            super(filter);
        }

        @Override
        public SalaryExecutionStatusFilter copy() {
            return new SalaryExecutionStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter amount;

    private SalaryExecutionStatusFilter status;

    private InstantFilter executedOn;

    private StringFilter executedBy;

    private LongFilter employeeId;

    private LongFilter festivalAllowancePaymentId;

    public FestivalAllowancePaymentDtlCriteria() {
    }

    public FestivalAllowancePaymentDtlCriteria(FestivalAllowancePaymentDtlCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.executedOn = other.executedOn == null ? null : other.executedOn.copy();
        this.executedBy = other.executedBy == null ? null : other.executedBy.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.festivalAllowancePaymentId = other.festivalAllowancePaymentId == null ? null : other.festivalAllowancePaymentId.copy();
    }

    @Override
    public FestivalAllowancePaymentDtlCriteria copy() {
        return new FestivalAllowancePaymentDtlCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public SalaryExecutionStatusFilter getStatus() {
        return status;
    }

    public void setStatus(SalaryExecutionStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getExecutedOn() {
        return executedOn;
    }

    public void setExecutedOn(InstantFilter executedOn) {
        this.executedOn = executedOn;
    }

    public StringFilter getExecutedBy() {
        return executedBy;
    }

    public void setExecutedBy(StringFilter executedBy) {
        this.executedBy = executedBy;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getFestivalAllowancePaymentId() {
        return festivalAllowancePaymentId;
    }

    public void setFestivalAllowancePaymentId(LongFilter festivalAllowancePaymentId) {
        this.festivalAllowancePaymentId = festivalAllowancePaymentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FestivalAllowancePaymentDtlCriteria that = (FestivalAllowancePaymentDtlCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(status, that.status) &&
            Objects.equals(executedOn, that.executedOn) &&
            Objects.equals(executedBy, that.executedBy) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(festivalAllowancePaymentId, that.festivalAllowancePaymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amount,
        status,
        executedOn,
        executedBy,
        employeeId,
        festivalAllowancePaymentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FestivalAllowancePaymentDtlCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (executedOn != null ? "executedOn=" + executedOn + ", " : "") +
                (executedBy != null ? "executedBy=" + executedBy + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (festivalAllowancePaymentId != null ? "festivalAllowancePaymentId=" + festivalAllowancePaymentId + ", " : "") +
            "}";
    }

}
