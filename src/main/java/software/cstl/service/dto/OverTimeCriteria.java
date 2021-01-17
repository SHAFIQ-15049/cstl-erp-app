package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.MonthType;
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
 * Criteria class for the {@link software.cstl.domain.OverTime} entity. This class is used
 * in {@link software.cstl.web.rest.OverTimeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /over-times?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OverTimeCriteria implements Serializable, Criteria {
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter year;

    private MonthTypeFilter month;

    private InstantFilter fromDate;

    private InstantFilter toDate;

    private DoubleFilter totalOverTime;

    private DoubleFilter officialOverTime;

    private DoubleFilter extraOverTime;

    private BigDecimalFilter totalAmount;

    private BigDecimalFilter officialAmount;

    private BigDecimalFilter extraAmount;

    private InstantFilter executedOn;

    private StringFilter executedBy;

    private LongFilter designationId;

    private LongFilter employeeId;

    public OverTimeCriteria() {
    }

    public OverTimeCriteria(OverTimeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.month = other.month == null ? null : other.month.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.toDate = other.toDate == null ? null : other.toDate.copy();
        this.totalOverTime = other.totalOverTime == null ? null : other.totalOverTime.copy();
        this.officialOverTime = other.officialOverTime == null ? null : other.officialOverTime.copy();
        this.extraOverTime = other.extraOverTime == null ? null : other.extraOverTime.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.officialAmount = other.officialAmount == null ? null : other.officialAmount.copy();
        this.extraAmount = other.extraAmount == null ? null : other.extraAmount.copy();
        this.executedOn = other.executedOn == null ? null : other.executedOn.copy();
        this.executedBy = other.executedBy == null ? null : other.executedBy.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public OverTimeCriteria copy() {
        return new OverTimeCriteria(this);
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

    public DoubleFilter getTotalOverTime() {
        return totalOverTime;
    }

    public void setTotalOverTime(DoubleFilter totalOverTime) {
        this.totalOverTime = totalOverTime;
    }

    public DoubleFilter getOfficialOverTime() {
        return officialOverTime;
    }

    public void setOfficialOverTime(DoubleFilter officialOverTime) {
        this.officialOverTime = officialOverTime;
    }

    public DoubleFilter getExtraOverTime() {
        return extraOverTime;
    }

    public void setExtraOverTime(DoubleFilter extraOverTime) {
        this.extraOverTime = extraOverTime;
    }

    public BigDecimalFilter getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimalFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimalFilter getOfficialAmount() {
        return officialAmount;
    }

    public void setOfficialAmount(BigDecimalFilter officialAmount) {
        this.officialAmount = officialAmount;
    }

    public BigDecimalFilter getExtraAmount() {
        return extraAmount;
    }

    public void setExtraAmount(BigDecimalFilter extraAmount) {
        this.extraAmount = extraAmount;
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

    public LongFilter getDesignationId() {
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
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
        final OverTimeCriteria that = (OverTimeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(year, that.year) &&
            Objects.equals(month, that.month) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(totalOverTime, that.totalOverTime) &&
            Objects.equals(officialOverTime, that.officialOverTime) &&
            Objects.equals(extraOverTime, that.extraOverTime) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(officialAmount, that.officialAmount) &&
            Objects.equals(extraAmount, that.extraAmount) &&
            Objects.equals(executedOn, that.executedOn) &&
            Objects.equals(executedBy, that.executedBy) &&
            Objects.equals(designationId, that.designationId) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        year,
        month,
        fromDate,
        toDate,
        totalOverTime,
        officialOverTime,
        extraOverTime,
        totalAmount,
        officialAmount,
        extraAmount,
        executedOn,
        executedBy,
        designationId,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OverTimeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (toDate != null ? "toDate=" + toDate + ", " : "") +
                (totalOverTime != null ? "totalOverTime=" + totalOverTime + ", " : "") +
                (officialOverTime != null ? "officialOverTime=" + officialOverTime + ", " : "") +
                (extraOverTime != null ? "extraOverTime=" + extraOverTime + ", " : "") +
                (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
                (officialAmount != null ? "officialAmount=" + officialAmount + ", " : "") +
                (extraAmount != null ? "extraAmount=" + extraAmount + ", " : "") +
                (executedOn != null ? "executedOn=" + executedOn + ", " : "") +
                (executedBy != null ? "executedBy=" + executedBy + ", " : "") +
                (designationId != null ? "designationId=" + designationId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
