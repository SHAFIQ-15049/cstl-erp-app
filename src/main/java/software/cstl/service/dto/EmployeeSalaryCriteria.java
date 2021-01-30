package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.InsuranceProcessType;
import software.cstl.domain.enumeration.ActiveStatus;
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
 * Criteria class for the {@link software.cstl.domain.EmployeeSalary} entity. This class is used
 * in {@link software.cstl.web.rest.EmployeeSalaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-salaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeSalaryCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ActiveStatus
     */
    public static class ActiveStatusFilter extends Filter<ActiveStatus> {

        public ActiveStatusFilter() {
        }

        public ActiveStatusFilter(ActiveStatusFilter filter) {
            super(filter);
        }

        @Override
        public ActiveStatusFilter copy() {
            return new ActiveStatusFilter(this);
        }

    }
    /**
     * Class for filtering InsuranceProcessType
     */
    public static class InsuranceProcessTypeFilter extends Filter<InsuranceProcessType> {

        public InsuranceProcessTypeFilter() {
        }

        public InsuranceProcessTypeFilter(InsuranceProcessTypeFilter filter) {
            super(filter);
        }

        @Override
        public InsuranceProcessTypeFilter copy() {
            return new InsuranceProcessTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter gross;

    private BigDecimalFilter incrementAmount;

    private BigDecimalFilter incrementPercentage;

    private InstantFilter salaryStartDate;

    private InstantFilter salaryEndDate;

    private InstantFilter nextIncrementDate;

    private BigDecimalFilter basic;

    private BigDecimalFilter basicPercent;

    private BigDecimalFilter houseRent;

    private BigDecimalFilter houseRentPercent;

    private BigDecimalFilter totalAllowance;

    private BigDecimalFilter medicalAllowance;

    private BigDecimalFilter medicalAllowancePercent;

    private BigDecimalFilter convinceAllowance;

    private BigDecimalFilter convinceAllowancePercent;

    private BigDecimalFilter foodAllowance;

    private BigDecimalFilter foodAllowancePercent;

    private ActiveStatusFilter specialAllowanceActiveStatus;

    private BigDecimalFilter specialAllowance;

    private BigDecimalFilter specialAllowancePercent;

    private ActiveStatusFilter insuranceActiveStatus;

    private BigDecimalFilter insuranceAmount;

    private BigDecimalFilter insurancePercent;

    private InsuranceProcessTypeFilter insuranceProcessType;

    private ActiveStatusFilter status;

    private LongFilter employeeId;

    public EmployeeSalaryCriteria() {
    }

    public EmployeeSalaryCriteria(EmployeeSalaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.gross = other.gross == null ? null : other.gross.copy();
        this.incrementAmount = other.incrementAmount == null ? null : other.incrementAmount.copy();
        this.incrementPercentage = other.incrementPercentage == null ? null : other.incrementPercentage.copy();
        this.salaryStartDate = other.salaryStartDate == null ? null : other.salaryStartDate.copy();
        this.salaryEndDate = other.salaryEndDate == null ? null : other.salaryEndDate.copy();
        this.nextIncrementDate = other.nextIncrementDate == null ? null : other.nextIncrementDate.copy();
        this.basic = other.basic == null ? null : other.basic.copy();
        this.basicPercent = other.basicPercent == null ? null : other.basicPercent.copy();
        this.houseRent = other.houseRent == null ? null : other.houseRent.copy();
        this.houseRentPercent = other.houseRentPercent == null ? null : other.houseRentPercent.copy();
        this.totalAllowance = other.totalAllowance == null ? null : other.totalAllowance.copy();
        this.medicalAllowance = other.medicalAllowance == null ? null : other.medicalAllowance.copy();
        this.medicalAllowancePercent = other.medicalAllowancePercent == null ? null : other.medicalAllowancePercent.copy();
        this.convinceAllowance = other.convinceAllowance == null ? null : other.convinceAllowance.copy();
        this.convinceAllowancePercent = other.convinceAllowancePercent == null ? null : other.convinceAllowancePercent.copy();
        this.foodAllowance = other.foodAllowance == null ? null : other.foodAllowance.copy();
        this.foodAllowancePercent = other.foodAllowancePercent == null ? null : other.foodAllowancePercent.copy();
        this.specialAllowanceActiveStatus = other.specialAllowanceActiveStatus == null ? null : other.specialAllowanceActiveStatus.copy();
        this.specialAllowance = other.specialAllowance == null ? null : other.specialAllowance.copy();
        this.specialAllowancePercent = other.specialAllowancePercent == null ? null : other.specialAllowancePercent.copy();
        this.insuranceActiveStatus = other.insuranceActiveStatus == null ? null : other.insuranceActiveStatus.copy();
        this.insuranceAmount = other.insuranceAmount == null ? null : other.insuranceAmount.copy();
        this.insurancePercent = other.insurancePercent == null ? null : other.insurancePercent.copy();
        this.insuranceProcessType = other.insuranceProcessType == null ? null : other.insuranceProcessType.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public EmployeeSalaryCriteria copy() {
        return new EmployeeSalaryCriteria(this);
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

    public BigDecimalFilter getIncrementAmount() {
        return incrementAmount;
    }

    public void setIncrementAmount(BigDecimalFilter incrementAmount) {
        this.incrementAmount = incrementAmount;
    }

    public BigDecimalFilter getIncrementPercentage() {
        return incrementPercentage;
    }

    public void setIncrementPercentage(BigDecimalFilter incrementPercentage) {
        this.incrementPercentage = incrementPercentage;
    }

    public InstantFilter getSalaryStartDate() {
        return salaryStartDate;
    }

    public void setSalaryStartDate(InstantFilter salaryStartDate) {
        this.salaryStartDate = salaryStartDate;
    }

    public InstantFilter getSalaryEndDate() {
        return salaryEndDate;
    }

    public void setSalaryEndDate(InstantFilter salaryEndDate) {
        this.salaryEndDate = salaryEndDate;
    }

    public InstantFilter getNextIncrementDate() {
        return nextIncrementDate;
    }

    public void setNextIncrementDate(InstantFilter nextIncrementDate) {
        this.nextIncrementDate = nextIncrementDate;
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

    public BigDecimalFilter getTotalAllowance() {
        return totalAllowance;
    }

    public void setTotalAllowance(BigDecimalFilter totalAllowance) {
        this.totalAllowance = totalAllowance;
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

    public ActiveStatusFilter getSpecialAllowanceActiveStatus() {
        return specialAllowanceActiveStatus;
    }

    public void setSpecialAllowanceActiveStatus(ActiveStatusFilter specialAllowanceActiveStatus) {
        this.specialAllowanceActiveStatus = specialAllowanceActiveStatus;
    }

    public BigDecimalFilter getSpecialAllowance() {
        return specialAllowance;
    }

    public void setSpecialAllowance(BigDecimalFilter specialAllowance) {
        this.specialAllowance = specialAllowance;
    }

    public BigDecimalFilter getSpecialAllowancePercent() {
        return specialAllowancePercent;
    }

    public void setSpecialAllowancePercent(BigDecimalFilter specialAllowancePercent) {
        this.specialAllowancePercent = specialAllowancePercent;
    }

    public ActiveStatusFilter getInsuranceActiveStatus() {
        return insuranceActiveStatus;
    }

    public void setInsuranceActiveStatus(ActiveStatusFilter insuranceActiveStatus) {
        this.insuranceActiveStatus = insuranceActiveStatus;
    }

    public BigDecimalFilter getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimalFilter insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public BigDecimalFilter getInsurancePercent() {
        return insurancePercent;
    }

    public void setInsurancePercent(BigDecimalFilter insurancePercent) {
        this.insurancePercent = insurancePercent;
    }

    public InsuranceProcessTypeFilter getInsuranceProcessType() {
        return insuranceProcessType;
    }

    public void setInsuranceProcessType(InsuranceProcessTypeFilter insuranceProcessType) {
        this.insuranceProcessType = insuranceProcessType;
    }

    public ActiveStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ActiveStatusFilter status) {
        this.status = status;
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
        final EmployeeSalaryCriteria that = (EmployeeSalaryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(gross, that.gross) &&
            Objects.equals(incrementAmount, that.incrementAmount) &&
            Objects.equals(incrementPercentage, that.incrementPercentage) &&
            Objects.equals(salaryStartDate, that.salaryStartDate) &&
            Objects.equals(salaryEndDate, that.salaryEndDate) &&
            Objects.equals(nextIncrementDate, that.nextIncrementDate) &&
            Objects.equals(basic, that.basic) &&
            Objects.equals(basicPercent, that.basicPercent) &&
            Objects.equals(houseRent, that.houseRent) &&
            Objects.equals(houseRentPercent, that.houseRentPercent) &&
            Objects.equals(totalAllowance, that.totalAllowance) &&
            Objects.equals(medicalAllowance, that.medicalAllowance) &&
            Objects.equals(medicalAllowancePercent, that.medicalAllowancePercent) &&
            Objects.equals(convinceAllowance, that.convinceAllowance) &&
            Objects.equals(convinceAllowancePercent, that.convinceAllowancePercent) &&
            Objects.equals(foodAllowance, that.foodAllowance) &&
            Objects.equals(foodAllowancePercent, that.foodAllowancePercent) &&
            Objects.equals(specialAllowanceActiveStatus, that.specialAllowanceActiveStatus) &&
            Objects.equals(specialAllowance, that.specialAllowance) &&
            Objects.equals(specialAllowancePercent, that.specialAllowancePercent) &&
            Objects.equals(insuranceActiveStatus, that.insuranceActiveStatus) &&
            Objects.equals(insuranceAmount, that.insuranceAmount) &&
            Objects.equals(insurancePercent, that.insurancePercent) &&
            Objects.equals(insuranceProcessType, that.insuranceProcessType) &&
            Objects.equals(status, that.status) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        gross,
        incrementAmount,
        incrementPercentage,
        salaryStartDate,
        salaryEndDate,
        nextIncrementDate,
        basic,
        basicPercent,
        houseRent,
        houseRentPercent,
        totalAllowance,
        medicalAllowance,
        medicalAllowancePercent,
        convinceAllowance,
        convinceAllowancePercent,
        foodAllowance,
        foodAllowancePercent,
        specialAllowanceActiveStatus,
        specialAllowance,
        specialAllowancePercent,
        insuranceActiveStatus,
        insuranceAmount,
        insurancePercent,
        insuranceProcessType,
        status,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSalaryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (gross != null ? "gross=" + gross + ", " : "") +
                (incrementAmount != null ? "incrementAmount=" + incrementAmount + ", " : "") +
                (incrementPercentage != null ? "incrementPercentage=" + incrementPercentage + ", " : "") +
                (salaryStartDate != null ? "salaryStartDate=" + salaryStartDate + ", " : "") +
                (salaryEndDate != null ? "salaryEndDate=" + salaryEndDate + ", " : "") +
                (nextIncrementDate != null ? "nextIncrementDate=" + nextIncrementDate + ", " : "") +
                (basic != null ? "basic=" + basic + ", " : "") +
                (basicPercent != null ? "basicPercent=" + basicPercent + ", " : "") +
                (houseRent != null ? "houseRent=" + houseRent + ", " : "") +
                (houseRentPercent != null ? "houseRentPercent=" + houseRentPercent + ", " : "") +
                (totalAllowance != null ? "totalAllowance=" + totalAllowance + ", " : "") +
                (medicalAllowance != null ? "medicalAllowance=" + medicalAllowance + ", " : "") +
                (medicalAllowancePercent != null ? "medicalAllowancePercent=" + medicalAllowancePercent + ", " : "") +
                (convinceAllowance != null ? "convinceAllowance=" + convinceAllowance + ", " : "") +
                (convinceAllowancePercent != null ? "convinceAllowancePercent=" + convinceAllowancePercent + ", " : "") +
                (foodAllowance != null ? "foodAllowance=" + foodAllowance + ", " : "") +
                (foodAllowancePercent != null ? "foodAllowancePercent=" + foodAllowancePercent + ", " : "") +
                (specialAllowanceActiveStatus != null ? "specialAllowanceActiveStatus=" + specialAllowanceActiveStatus + ", " : "") +
                (specialAllowance != null ? "specialAllowance=" + specialAllowance + ", " : "") +
                (specialAllowancePercent != null ? "specialAllowancePercent=" + specialAllowancePercent + ", " : "") +
                (insuranceActiveStatus != null ? "insuranceActiveStatus=" + insuranceActiveStatus + ", " : "") +
                (insuranceAmount != null ? "insuranceAmount=" + insuranceAmount + ", " : "") +
                (insurancePercent != null ? "insurancePercent=" + insurancePercent + ", " : "") +
                (insuranceProcessType != null ? "insuranceProcessType=" + insuranceProcessType + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
