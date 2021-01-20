package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.domain.enumeration.SalaryExecutionStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link software.cstl.domain.FestivalAllowancePayment} entity. This class is used
 * in {@link software.cstl.web.rest.FestivalAllowancePaymentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /festival-allowance-payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FestivalAllowancePaymentCriteria implements Serializable, Criteria {
    /**
     * Class for filtering MonthType
     */
    public static class MonthTypeFilter extends Filter<MonthType> {

        public MonthTypeFilter() {
        }

        public MonthTypeFilter(MonthTypeFilter filter) {
            super(filter);
        }

        @Override
        public MonthTypeFilter copy() {
            return new MonthTypeFilter(this);
        }

    }
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

    private IntegerFilter year;

    private MonthTypeFilter month;

    private SalaryExecutionStatusFilter status;

    private InstantFilter executedOn;

    private StringFilter executedBy;

    private LongFilter festivalAllowancePaymentDtlId;

    private LongFilter designationId;

    public FestivalAllowancePaymentCriteria() {
    }

    public FestivalAllowancePaymentCriteria(FestivalAllowancePaymentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.month = other.month == null ? null : other.month.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.executedOn = other.executedOn == null ? null : other.executedOn.copy();
        this.executedBy = other.executedBy == null ? null : other.executedBy.copy();
        this.festivalAllowancePaymentDtlId = other.festivalAllowancePaymentDtlId == null ? null : other.festivalAllowancePaymentDtlId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
    }

    @Override
    public FestivalAllowancePaymentCriteria copy() {
        return new FestivalAllowancePaymentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getYear() {
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
    }

    public MonthTypeFilter getMonth() {
        return month;
    }

    public void setMonth(MonthTypeFilter month) {
        this.month = month;
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

    public LongFilter getFestivalAllowancePaymentDtlId() {
        return festivalAllowancePaymentDtlId;
    }

    public void setFestivalAllowancePaymentDtlId(LongFilter festivalAllowancePaymentDtlId) {
        this.festivalAllowancePaymentDtlId = festivalAllowancePaymentDtlId;
    }

    public LongFilter getDesignationId() {
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FestivalAllowancePaymentCriteria that = (FestivalAllowancePaymentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(year, that.year) &&
            Objects.equals(month, that.month) &&
            Objects.equals(status, that.status) &&
            Objects.equals(executedOn, that.executedOn) &&
            Objects.equals(executedBy, that.executedBy) &&
            Objects.equals(festivalAllowancePaymentDtlId, that.festivalAllowancePaymentDtlId) &&
            Objects.equals(designationId, that.designationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        year,
        month,
        status,
        executedOn,
        executedBy,
        festivalAllowancePaymentDtlId,
        designationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FestivalAllowancePaymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (executedOn != null ? "executedOn=" + executedOn + ", " : "") +
                (executedBy != null ? "executedBy=" + executedBy + ", " : "") +
                (festivalAllowancePaymentDtlId != null ? "festivalAllowancePaymentDtlId=" + festivalAllowancePaymentDtlId + ", " : "") +
                (designationId != null ? "designationId=" + designationId + ", " : "") +
            "}";
    }

}
