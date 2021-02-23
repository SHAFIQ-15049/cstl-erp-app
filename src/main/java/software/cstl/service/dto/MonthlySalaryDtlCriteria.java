package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.SalaryExecutionStatus;
import software.cstl.domain.enumeration.PayrollGenerationType;
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
 * Criteria class for the {@link software.cstl.domain.MonthlySalaryDtl} entity. This class is used
 * in {@link software.cstl.web.rest.MonthlySalaryDtlResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /monthly-salary-dtls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MonthlySalaryDtlCriteria implements Serializable, Criteria {
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
    /**
     * Class for filtering PayrollGenerationType
     */
    public static class PayrollGenerationTypeFilter extends Filter<PayrollGenerationType> {

        public PayrollGenerationTypeFilter() {
        }

        public PayrollGenerationTypeFilter(PayrollGenerationTypeFilter filter) {
            super(filter);
        }

        @Override
        public PayrollGenerationTypeFilter copy() {
            return new PayrollGenerationTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

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

    private IntegerFilter totalWorkingDays;

    private IntegerFilter regularLeave;

    private IntegerFilter sickLeave;

    private IntegerFilter compensationLeave;

    private IntegerFilter festivalLeave;

    private IntegerFilter weeklyLeave;

    private IntegerFilter present;

    private IntegerFilter absent;

    private IntegerFilter totalMonthDays;

    private DoubleFilter overTimeHour;

    private BigDecimalFilter overTimeSalaryHourly;

    private BigDecimalFilter overTimeSalary;

    private BigDecimalFilter presentBonus;

    private BigDecimalFilter absentFine;

    private BigDecimalFilter stampPrice;

    private BigDecimalFilter tax;

    private BigDecimalFilter others;

    private BigDecimalFilter totalPayable;

    private SalaryExecutionStatusFilter status;

    private PayrollGenerationTypeFilter type;

    private InstantFilter executedOn;

    private StringFilter executedBy;

    private LongFilter employeeId;

    private LongFilter monthlySalaryId;

    public MonthlySalaryDtlCriteria() {
    }

    public MonthlySalaryDtlCriteria(MonthlySalaryDtlCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
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
        this.totalWorkingDays = other.totalWorkingDays == null ? null : other.totalWorkingDays.copy();
        this.regularLeave = other.regularLeave == null ? null : other.regularLeave.copy();
        this.sickLeave = other.sickLeave == null ? null : other.sickLeave.copy();
        this.compensationLeave = other.compensationLeave == null ? null : other.compensationLeave.copy();
        this.festivalLeave = other.festivalLeave == null ? null : other.festivalLeave.copy();
        this.weeklyLeave = other.weeklyLeave == null ? null : other.weeklyLeave.copy();
        this.present = other.present == null ? null : other.present.copy();
        this.absent = other.absent == null ? null : other.absent.copy();
        this.totalMonthDays = other.totalMonthDays == null ? null : other.totalMonthDays.copy();
        this.overTimeHour = other.overTimeHour == null ? null : other.overTimeHour.copy();
        this.overTimeSalaryHourly = other.overTimeSalaryHourly == null ? null : other.overTimeSalaryHourly.copy();
        this.overTimeSalary = other.overTimeSalary == null ? null : other.overTimeSalary.copy();
        this.presentBonus = other.presentBonus == null ? null : other.presentBonus.copy();
        this.absentFine = other.absentFine == null ? null : other.absentFine.copy();
        this.stampPrice = other.stampPrice == null ? null : other.stampPrice.copy();
        this.tax = other.tax == null ? null : other.tax.copy();
        this.others = other.others == null ? null : other.others.copy();
        this.totalPayable = other.totalPayable == null ? null : other.totalPayable.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.executedOn = other.executedOn == null ? null : other.executedOn.copy();
        this.executedBy = other.executedBy == null ? null : other.executedBy.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.monthlySalaryId = other.monthlySalaryId == null ? null : other.monthlySalaryId.copy();
    }

    @Override
    public MonthlySalaryDtlCriteria copy() {
        return new MonthlySalaryDtlCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public IntegerFilter getTotalWorkingDays() {
        return totalWorkingDays;
    }

    public void setTotalWorkingDays(IntegerFilter totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
    }

    public IntegerFilter getRegularLeave() {
        return regularLeave;
    }

    public void setRegularLeave(IntegerFilter regularLeave) {
        this.regularLeave = regularLeave;
    }

    public IntegerFilter getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(IntegerFilter sickLeave) {
        this.sickLeave = sickLeave;
    }

    public IntegerFilter getCompensationLeave() {
        return compensationLeave;
    }

    public void setCompensationLeave(IntegerFilter compensationLeave) {
        this.compensationLeave = compensationLeave;
    }

    public IntegerFilter getFestivalLeave() {
        return festivalLeave;
    }

    public void setFestivalLeave(IntegerFilter festivalLeave) {
        this.festivalLeave = festivalLeave;
    }

    public IntegerFilter getWeeklyLeave() {
        return weeklyLeave;
    }

    public void setWeeklyLeave(IntegerFilter weeklyLeave) {
        this.weeklyLeave = weeklyLeave;
    }

    public IntegerFilter getPresent() {
        return present;
    }

    public void setPresent(IntegerFilter present) {
        this.present = present;
    }

    public IntegerFilter getAbsent() {
        return absent;
    }

    public void setAbsent(IntegerFilter absent) {
        this.absent = absent;
    }

    public IntegerFilter getTotalMonthDays() {
        return totalMonthDays;
    }

    public void setTotalMonthDays(IntegerFilter totalMonthDays) {
        this.totalMonthDays = totalMonthDays;
    }

    public DoubleFilter getOverTimeHour() {
        return overTimeHour;
    }

    public void setOverTimeHour(DoubleFilter overTimeHour) {
        this.overTimeHour = overTimeHour;
    }

    public BigDecimalFilter getOverTimeSalaryHourly() {
        return overTimeSalaryHourly;
    }

    public void setOverTimeSalaryHourly(BigDecimalFilter overTimeSalaryHourly) {
        this.overTimeSalaryHourly = overTimeSalaryHourly;
    }

    public BigDecimalFilter getOverTimeSalary() {
        return overTimeSalary;
    }

    public void setOverTimeSalary(BigDecimalFilter overTimeSalary) {
        this.overTimeSalary = overTimeSalary;
    }

    public BigDecimalFilter getPresentBonus() {
        return presentBonus;
    }

    public void setPresentBonus(BigDecimalFilter presentBonus) {
        this.presentBonus = presentBonus;
    }

    public BigDecimalFilter getAbsentFine() {
        return absentFine;
    }

    public void setAbsentFine(BigDecimalFilter absentFine) {
        this.absentFine = absentFine;
    }

    public BigDecimalFilter getStampPrice() {
        return stampPrice;
    }

    public void setStampPrice(BigDecimalFilter stampPrice) {
        this.stampPrice = stampPrice;
    }

    public BigDecimalFilter getTax() {
        return tax;
    }

    public void setTax(BigDecimalFilter tax) {
        this.tax = tax;
    }

    public BigDecimalFilter getOthers() {
        return others;
    }

    public void setOthers(BigDecimalFilter others) {
        this.others = others;
    }

    public BigDecimalFilter getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(BigDecimalFilter totalPayable) {
        this.totalPayable = totalPayable;
    }

    public SalaryExecutionStatusFilter getStatus() {
        return status;
    }

    public void setStatus(SalaryExecutionStatusFilter status) {
        this.status = status;
    }

    public PayrollGenerationTypeFilter getType() {
        return type;
    }

    public void setType(PayrollGenerationTypeFilter type) {
        this.type = type;
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

    public LongFilter getMonthlySalaryId() {
        return monthlySalaryId;
    }

    public void setMonthlySalaryId(LongFilter monthlySalaryId) {
        this.monthlySalaryId = monthlySalaryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MonthlySalaryDtlCriteria that = (MonthlySalaryDtlCriteria) o;
        return
            Objects.equals(id, that.id) &&
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
            Objects.equals(totalWorkingDays, that.totalWorkingDays) &&
            Objects.equals(regularLeave, that.regularLeave) &&
            Objects.equals(sickLeave, that.sickLeave) &&
            Objects.equals(compensationLeave, that.compensationLeave) &&
            Objects.equals(festivalLeave, that.festivalLeave) &&
            Objects.equals(weeklyLeave, that.weeklyLeave) &&
            Objects.equals(present, that.present) &&
            Objects.equals(absent, that.absent) &&
            Objects.equals(totalMonthDays, that.totalMonthDays) &&
            Objects.equals(overTimeHour, that.overTimeHour) &&
            Objects.equals(overTimeSalaryHourly, that.overTimeSalaryHourly) &&
            Objects.equals(overTimeSalary, that.overTimeSalary) &&
            Objects.equals(presentBonus, that.presentBonus) &&
            Objects.equals(absentFine, that.absentFine) &&
            Objects.equals(stampPrice, that.stampPrice) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(others, that.others) &&
            Objects.equals(totalPayable, that.totalPayable) &&
            Objects.equals(status, that.status) &&
            Objects.equals(type, that.type) &&
            Objects.equals(executedOn, that.executedOn) &&
            Objects.equals(executedBy, that.executedBy) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(monthlySalaryId, that.monthlySalaryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
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
        totalWorkingDays,
        regularLeave,
        sickLeave,
        compensationLeave,
        festivalLeave,
        weeklyLeave,
        present,
        absent,
        totalMonthDays,
        overTimeHour,
        overTimeSalaryHourly,
        overTimeSalary,
        presentBonus,
        absentFine,
        stampPrice,
        tax,
        others,
        totalPayable,
        status,
        type,
        executedOn,
        executedBy,
        employeeId,
        monthlySalaryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlySalaryDtlCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
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
                (totalWorkingDays != null ? "totalWorkingDays=" + totalWorkingDays + ", " : "") +
                (regularLeave != null ? "regularLeave=" + regularLeave + ", " : "") +
                (sickLeave != null ? "sickLeave=" + sickLeave + ", " : "") +
                (compensationLeave != null ? "compensationLeave=" + compensationLeave + ", " : "") +
                (festivalLeave != null ? "festivalLeave=" + festivalLeave + ", " : "") +
                (weeklyLeave != null ? "weeklyLeave=" + weeklyLeave + ", " : "") +
                (present != null ? "present=" + present + ", " : "") +
                (absent != null ? "absent=" + absent + ", " : "") +
                (totalMonthDays != null ? "totalMonthDays=" + totalMonthDays + ", " : "") +
                (overTimeHour != null ? "overTimeHour=" + overTimeHour + ", " : "") +
                (overTimeSalaryHourly != null ? "overTimeSalaryHourly=" + overTimeSalaryHourly + ", " : "") +
                (overTimeSalary != null ? "overTimeSalary=" + overTimeSalary + ", " : "") +
                (presentBonus != null ? "presentBonus=" + presentBonus + ", " : "") +
                (absentFine != null ? "absentFine=" + absentFine + ", " : "") +
                (stampPrice != null ? "stampPrice=" + stampPrice + ", " : "") +
                (tax != null ? "tax=" + tax + ", " : "") +
                (others != null ? "others=" + others + ", " : "") +
                (totalPayable != null ? "totalPayable=" + totalPayable + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (executedOn != null ? "executedOn=" + executedOn + ", " : "") +
                (executedBy != null ? "executedBy=" + executedBy + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (monthlySalaryId != null ? "monthlySalaryId=" + monthlySalaryId + ", " : "") +
            "}";
    }

}
