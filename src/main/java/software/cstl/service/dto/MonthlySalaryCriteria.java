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
 * Criteria class for the {@link software.cstl.domain.MonthlySalary} entity. This class is used
 * in {@link software.cstl.web.rest.MonthlySalaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /monthly-salaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MonthlySalaryCriteria implements Serializable, Criteria {
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

    private InstantFilter fromDate;

    private InstantFilter toDate;

    private SalaryExecutionStatusFilter status;

    private InstantFilter executedOn;

    private StringFilter executedBy;

    private LongFilter monthlySalaryDtlId;

    private LongFilter departmentId;

    public MonthlySalaryCriteria() {
    }

    public MonthlySalaryCriteria(MonthlySalaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.month = other.month == null ? null : other.month.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.toDate = other.toDate == null ? null : other.toDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.executedOn = other.executedOn == null ? null : other.executedOn.copy();
        this.executedBy = other.executedBy == null ? null : other.executedBy.copy();
        this.monthlySalaryDtlId = other.monthlySalaryDtlId == null ? null : other.monthlySalaryDtlId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
    }

    @Override
    public MonthlySalaryCriteria copy() {
        return new MonthlySalaryCriteria(this);
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

    public InstantFilter getFromDate() {
        return fromDate;
    }

    public void setFromDate(InstantFilter fromDate) {
        this.fromDate = fromDate;
    }

    public InstantFilter getToDate() {
        return toDate;
    }

    public void setToDate(InstantFilter toDate) {
        this.toDate = toDate;
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

    public LongFilter getMonthlySalaryDtlId() {
        return monthlySalaryDtlId;
    }

    public void setMonthlySalaryDtlId(LongFilter monthlySalaryDtlId) {
        this.monthlySalaryDtlId = monthlySalaryDtlId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MonthlySalaryCriteria that = (MonthlySalaryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(year, that.year) &&
            Objects.equals(month, that.month) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(executedOn, that.executedOn) &&
            Objects.equals(executedBy, that.executedBy) &&
            Objects.equals(monthlySalaryDtlId, that.monthlySalaryDtlId) &&
            Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        year,
        month,
        fromDate,
        toDate,
        status,
        executedOn,
        executedBy,
        monthlySalaryDtlId,
        departmentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlySalaryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (toDate != null ? "toDate=" + toDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (executedOn != null ? "executedOn=" + executedOn + ", " : "") +
                (executedBy != null ? "executedBy=" + executedBy + ", " : "") +
                (monthlySalaryDtlId != null ? "monthlySalaryDtlId=" + monthlySalaryDtlId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            "}";
    }

}
