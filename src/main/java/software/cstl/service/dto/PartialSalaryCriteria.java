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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link software.cstl.domain.PartialSalary} entity. This class is used
 * in {@link software.cstl.web.rest.PartialSalaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /partial-salaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PartialSalaryCriteria implements Serializable, Criteria {
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

    private IntegerFilter totalMonthDays;

    private InstantFilter fromDate;

    private InstantFilter toDate;

    private BigDecimalFilter gross;

    private BigDecimalFilter basic;

    private BigDecimalFilter basicPercent;

    private BigDecimalFilter houseRent;

    private BigDecimalFilter houseRentPercent;

    private BigDecimalFilter medicalAllowance;

    private BigDecimalFilter medicalAllowancePercent;

    private BigDecimalFilter convinceAllowance;

    private BigDecimalFilter convinceAllowancePercent;

    private BigDecimalFilter foodAllowance;

    private BigDecimalFilter foodAllowancePercent;

    private BigDecimalFilter fine;

    private BigDecimalFilter advance;

    private SalaryExecutionStatusFilter status;

    private InstantFilter executedOn;

    private StringFilter executedBy;

    private LongFilter employeeId;

    public PartialSalaryCriteria() {
    }

    public PartialSalaryCriteria(PartialSalaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.month = other.month == null ? null : other.month.copy();
        this.totalMonthDays = other.totalMonthDays == null ? null : other.totalMonthDays.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.toDate = other.toDate == null ? null : other.toDate.copy();
        this.gross = other.gross == null ? null : other.gross.copy();
        this.basic = other.basic == null ? null : other.basic.copy();
        this.basicPercent = other.basicPercent == null ? null : other.basicPercent.copy();
        this.houseRent = other.houseRent == null ? null : other.houseRent.copy();
        this.houseRentPercent = other.houseRentPercent == null ? null : other.houseRentPercent.copy();
        this.medicalAllowance = other.medicalAllowance == null ? null : other.medicalAllowance.copy();
        this.medicalAllowancePercent = other.medicalAllowancePercent == null ? null : other.medicalAllowancePercent.copy();
        this.convinceAllowance = other.convinceAllowance == null ? null : other.convinceAllowance.copy();
        this.convinceAllowancePercent = other.convinceAllowancePercent == null ? null : other.convinceAllowancePercent.copy();
        this.foodAllowance = other.foodAllowance == null ? null : other.foodAllowance.copy();
        this.foodAllowancePercent = other.foodAllowancePercent == null ? null : other.foodAllowancePercent.copy();
        this.fine = other.fine == null ? null : other.fine.copy();
        this.advance = other.advance == null ? null : other.advance.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.executedOn = other.executedOn == null ? null : other.executedOn.copy();
        this.executedBy = other.executedBy == null ? null : other.executedBy.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public PartialSalaryCriteria copy() {
        return new PartialSalaryCriteria(this);
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

    public IntegerFilter getTotalMonthDays() {
        return totalMonthDays;
    }

    public void setTotalMonthDays(IntegerFilter totalMonthDays) {
        this.totalMonthDays = totalMonthDays;
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

    public BigDecimalFilter getGross() {
        return gross;
    }

    public void setGross(BigDecimalFilter gross) {
        this.gross = gross;
    }

    public BigDecimalFilter getBasic() {
        return basic;
    }

    public void setBasic(BigDecimalFilter basic) {
        this.basic = basic;
    }

    public BigDecimalFilter getBasicPercent() {
        return basicPercent;
    }

    public void setBasicPercent(BigDecimalFilter basicPercent) {
        this.basicPercent = basicPercent;
    }

    public BigDecimalFilter getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(BigDecimalFilter houseRent) {
        this.houseRent = houseRent;
    }

    public BigDecimalFilter getHouseRentPercent() {
        return houseRentPercent;
    }

    public void setHouseRentPercent(BigDecimalFilter houseRentPercent) {
        this.houseRentPercent = houseRentPercent;
    }

    public BigDecimalFilter getMedicalAllowance() {
        return medicalAllowance;
    }

    public void setMedicalAllowance(BigDecimalFilter medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public BigDecimalFilter getMedicalAllowancePercent() {
        return medicalAllowancePercent;
    }

    public void setMedicalAllowancePercent(BigDecimalFilter medicalAllowancePercent) {
        this.medicalAllowancePercent = medicalAllowancePercent;
    }

    public BigDecimalFilter getConvinceAllowance() {
        return convinceAllowance;
    }

    public void setConvinceAllowance(BigDecimalFilter convinceAllowance) {
        this.convinceAllowance = convinceAllowance;
    }

    public BigDecimalFilter getConvinceAllowancePercent() {
        return convinceAllowancePercent;
    }

    public void setConvinceAllowancePercent(BigDecimalFilter convinceAllowancePercent) {
        this.convinceAllowancePercent = convinceAllowancePercent;
    }

    public BigDecimalFilter getFoodAllowance() {
        return foodAllowance;
    }

    public void setFoodAllowance(BigDecimalFilter foodAllowance) {
        this.foodAllowance = foodAllowance;
    }

    public BigDecimalFilter getFoodAllowancePercent() {
        return foodAllowancePercent;
    }

    public void setFoodAllowancePercent(BigDecimalFilter foodAllowancePercent) {
        this.foodAllowancePercent = foodAllowancePercent;
    }

    public BigDecimalFilter getFine() {
        return fine;
    }

    public void setFine(BigDecimalFilter fine) {
        this.fine = fine;
    }

    public BigDecimalFilter getAdvance() {
        return advance;
    }

    public void setAdvance(BigDecimalFilter advance) {
        this.advance = advance;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartialSalaryCriteria that = (PartialSalaryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(year, that.year) &&
            Objects.equals(month, that.month) &&
            Objects.equals(totalMonthDays, that.totalMonthDays) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(gross, that.gross) &&
            Objects.equals(basic, that.basic) &&
            Objects.equals(basicPercent, that.basicPercent) &&
            Objects.equals(houseRent, that.houseRent) &&
            Objects.equals(houseRentPercent, that.houseRentPercent) &&
            Objects.equals(medicalAllowance, that.medicalAllowance) &&
            Objects.equals(medicalAllowancePercent, that.medicalAllowancePercent) &&
            Objects.equals(convinceAllowance, that.convinceAllowance) &&
            Objects.equals(convinceAllowancePercent, that.convinceAllowancePercent) &&
            Objects.equals(foodAllowance, that.foodAllowance) &&
            Objects.equals(foodAllowancePercent, that.foodAllowancePercent) &&
            Objects.equals(fine, that.fine) &&
            Objects.equals(advance, that.advance) &&
            Objects.equals(status, that.status) &&
            Objects.equals(executedOn, that.executedOn) &&
            Objects.equals(executedBy, that.executedBy) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        year,
        month,
        totalMonthDays,
        fromDate,
        toDate,
        gross,
        basic,
        basicPercent,
        houseRent,
        houseRentPercent,
        medicalAllowance,
        medicalAllowancePercent,
        convinceAllowance,
        convinceAllowancePercent,
        foodAllowance,
        foodAllowancePercent,
        fine,
        advance,
        status,
        executedOn,
        executedBy,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartialSalaryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (totalMonthDays != null ? "totalMonthDays=" + totalMonthDays + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (toDate != null ? "toDate=" + toDate + ", " : "") +
                (gross != null ? "gross=" + gross + ", " : "") +
                (basic != null ? "basic=" + basic + ", " : "") +
                (basicPercent != null ? "basicPercent=" + basicPercent + ", " : "") +
                (houseRent != null ? "houseRent=" + houseRent + ", " : "") +
                (houseRentPercent != null ? "houseRentPercent=" + houseRentPercent + ", " : "") +
                (medicalAllowance != null ? "medicalAllowance=" + medicalAllowance + ", " : "") +
                (medicalAllowancePercent != null ? "medicalAllowancePercent=" + medicalAllowancePercent + ", " : "") +
                (convinceAllowance != null ? "convinceAllowance=" + convinceAllowance + ", " : "") +
                (convinceAllowancePercent != null ? "convinceAllowancePercent=" + convinceAllowancePercent + ", " : "") +
                (foodAllowance != null ? "foodAllowance=" + foodAllowance + ", " : "") +
                (foodAllowancePercent != null ? "foodAllowancePercent=" + foodAllowancePercent + ", " : "") +
                (fine != null ? "fine=" + fine + ", " : "") +
                (advance != null ? "advance=" + advance + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (executedOn != null ? "executedOn=" + executedOn + ", " : "") +
                (executedBy != null ? "executedBy=" + executedBy + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
