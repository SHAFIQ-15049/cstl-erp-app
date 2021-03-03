package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import software.cstl.domain.enumeration.ActiveStatus;

import software.cstl.domain.enumeration.InsuranceProcessType;

/**
 * A EmployeeSalary.
 */
@Entity
@Table(name = "employee_salary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeSalary extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "gross", precision = 21, scale = 2, nullable = false)
    private BigDecimal gross;

    @NotNull
    @Column(name = "increment_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal incrementAmount;

    @Column(name = "increment_percentage", precision = 21, scale = 2)
    private BigDecimal incrementPercentage;

    @NotNull
    @Column(name = "salary_start_date", nullable = false)
    private Instant salaryStartDate;

    @NotNull
    @Column(name = "salary_end_date", nullable = false)
    private Instant salaryEndDate;

    @Column(name = "next_increment_date")
    private Instant nextIncrementDate;

    @NotNull
    @Column(name = "basic", precision = 21, scale = 2, nullable = false)
    private BigDecimal basic;

    @Column(name = "basic_percent", precision = 21, scale = 2)
    private BigDecimal basicPercent;

    @NotNull
    @Column(name = "house_rent", precision = 21, scale = 2, nullable = false)
    private BigDecimal houseRent;

    @Column(name = "house_rent_percent", precision = 21, scale = 2)
    private BigDecimal houseRentPercent;

    @Column(name = "total_allowance", precision = 21, scale = 2)
    private BigDecimal totalAllowance;

    @Column(name = "medical_allowance", precision = 21, scale = 2)
    private BigDecimal medicalAllowance;

    @Column(name = "medical_allowance_percent", precision = 21, scale = 2)
    private BigDecimal medicalAllowancePercent;

    @Column(name = "convince_allowance", precision = 21, scale = 2)
    private BigDecimal convinceAllowance;

    @Column(name = "convince_allowance_percent", precision = 21, scale = 2)
    private BigDecimal convinceAllowancePercent;

    @Column(name = "food_allowance", precision = 21, scale = 2)
    private BigDecimal foodAllowance;

    @Column(name = "food_allowance_percent", precision = 21, scale = 2)
    private BigDecimal foodAllowancePercent;

    @Enumerated(EnumType.STRING)
    @Column(name = "special_allowance_active_status")
    private ActiveStatus specialAllowanceActiveStatus;

    @Column(name = "special_allowance", precision = 21, scale = 2)
    private BigDecimal specialAllowance;

    @Column(name = "special_allowance_percent", precision = 21, scale = 2)
    private BigDecimal specialAllowancePercent;

    @Lob
    @Column(name = "special_allowance_description")
    private String specialAllowanceDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "insurance_active_status")
    private ActiveStatus insuranceActiveStatus;

    @Column(name = "insurance_amount", precision = 21, scale = 2)
    private BigDecimal insuranceAmount;

    @Column(name = "insurance_percent", precision = 21, scale = 2)
    private BigDecimal insurancePercent;

    @Lob
    @Column(name = "insurance_description")
    private String insuranceDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "insurance_process_type")
    private InsuranceProcessType insuranceProcessType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ActiveStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = "employeeSalaries", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public EmployeeSalary gross(BigDecimal gross) {
        this.gross = gross;
        return this;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getIncrementAmount() {
        return incrementAmount;
    }

    public EmployeeSalary incrementAmount(BigDecimal incrementAmount) {
        this.incrementAmount = incrementAmount;
        return this;
    }

    public void setIncrementAmount(BigDecimal incrementAmount) {
        this.incrementAmount = incrementAmount;
    }

    public BigDecimal getIncrementPercentage() {
        return incrementPercentage;
    }

    public EmployeeSalary incrementPercentage(BigDecimal incrementPercentage) {
        this.incrementPercentage = incrementPercentage;
        return this;
    }

    public void setIncrementPercentage(BigDecimal incrementPercentage) {
        this.incrementPercentage = incrementPercentage;
    }

    public Instant getSalaryStartDate() {
        return salaryStartDate;
    }

    public EmployeeSalary salaryStartDate(Instant salaryStartDate) {
        this.salaryStartDate = salaryStartDate;
        return this;
    }

    public void setSalaryStartDate(Instant salaryStartDate) {
        this.salaryStartDate = salaryStartDate;
    }

    public Instant getSalaryEndDate() {
        return salaryEndDate;
    }

    public EmployeeSalary salaryEndDate(Instant salaryEndDate) {
        this.salaryEndDate = salaryEndDate;
        return this;
    }

    public void setSalaryEndDate(Instant salaryEndDate) {
        this.salaryEndDate = salaryEndDate;
    }

    public Instant getNextIncrementDate() {
        return nextIncrementDate;
    }

    public EmployeeSalary nextIncrementDate(Instant nextIncrementDate) {
        this.nextIncrementDate = nextIncrementDate;
        return this;
    }

    public void setNextIncrementDate(Instant nextIncrementDate) {
        this.nextIncrementDate = nextIncrementDate;
    }

    public BigDecimal getBasic() {
        return basic;
    }

    public EmployeeSalary basic(BigDecimal basic) {
        this.basic = basic;
        return this;
    }

    public void setBasic(BigDecimal basic) {
        this.basic = basic;
    }

    public BigDecimal getBasicPercent() {
        return basicPercent;
    }

    public EmployeeSalary basicPercent(BigDecimal basicPercent) {
        this.basicPercent = basicPercent;
        return this;
    }

    public void setBasicPercent(BigDecimal basicPercent) {
        this.basicPercent = basicPercent;
    }

    public BigDecimal getHouseRent() {
        return houseRent;
    }

    public EmployeeSalary houseRent(BigDecimal houseRent) {
        this.houseRent = houseRent;
        return this;
    }

    public void setHouseRent(BigDecimal houseRent) {
        this.houseRent = houseRent;
    }

    public BigDecimal getHouseRentPercent() {
        return houseRentPercent;
    }

    public EmployeeSalary houseRentPercent(BigDecimal houseRentPercent) {
        this.houseRentPercent = houseRentPercent;
        return this;
    }

    public void setHouseRentPercent(BigDecimal houseRentPercent) {
        this.houseRentPercent = houseRentPercent;
    }

    public BigDecimal getTotalAllowance() {
        return totalAllowance;
    }

    public EmployeeSalary totalAllowance(BigDecimal totalAllowance) {
        this.totalAllowance = totalAllowance;
        return this;
    }

    public void setTotalAllowance(BigDecimal totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    public BigDecimal getMedicalAllowance() {
        return medicalAllowance;
    }

    public EmployeeSalary medicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
        return this;
    }

    public void setMedicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public BigDecimal getMedicalAllowancePercent() {
        return medicalAllowancePercent;
    }

    public EmployeeSalary medicalAllowancePercent(BigDecimal medicalAllowancePercent) {
        this.medicalAllowancePercent = medicalAllowancePercent;
        return this;
    }

    public void setMedicalAllowancePercent(BigDecimal medicalAllowancePercent) {
        this.medicalAllowancePercent = medicalAllowancePercent;
    }

    public BigDecimal getConvinceAllowance() {
        return convinceAllowance;
    }

    public EmployeeSalary convinceAllowance(BigDecimal convinceAllowance) {
        this.convinceAllowance = convinceAllowance;
        return this;
    }

    public void setConvinceAllowance(BigDecimal convinceAllowance) {
        this.convinceAllowance = convinceAllowance;
    }

    public BigDecimal getConvinceAllowancePercent() {
        return convinceAllowancePercent;
    }

    public EmployeeSalary convinceAllowancePercent(BigDecimal convinceAllowancePercent) {
        this.convinceAllowancePercent = convinceAllowancePercent;
        return this;
    }

    public void setConvinceAllowancePercent(BigDecimal convinceAllowancePercent) {
        this.convinceAllowancePercent = convinceAllowancePercent;
    }

    public BigDecimal getFoodAllowance() {
        return foodAllowance;
    }

    public EmployeeSalary foodAllowance(BigDecimal foodAllowance) {
        this.foodAllowance = foodAllowance;
        return this;
    }

    public void setFoodAllowance(BigDecimal foodAllowance) {
        this.foodAllowance = foodAllowance;
    }

    public BigDecimal getFoodAllowancePercent() {
        return foodAllowancePercent;
    }

    public EmployeeSalary foodAllowancePercent(BigDecimal foodAllowancePercent) {
        this.foodAllowancePercent = foodAllowancePercent;
        return this;
    }

    public void setFoodAllowancePercent(BigDecimal foodAllowancePercent) {
        this.foodAllowancePercent = foodAllowancePercent;
    }

    public ActiveStatus getSpecialAllowanceActiveStatus() {
        return specialAllowanceActiveStatus;
    }

    public EmployeeSalary specialAllowanceActiveStatus(ActiveStatus specialAllowanceActiveStatus) {
        this.specialAllowanceActiveStatus = specialAllowanceActiveStatus;
        return this;
    }

    public void setSpecialAllowanceActiveStatus(ActiveStatus specialAllowanceActiveStatus) {
        this.specialAllowanceActiveStatus = specialAllowanceActiveStatus;
    }

    public BigDecimal getSpecialAllowance() {
        return specialAllowance;
    }

    public EmployeeSalary specialAllowance(BigDecimal specialAllowance) {
        this.specialAllowance = specialAllowance;
        return this;
    }

    public void setSpecialAllowance(BigDecimal specialAllowance) {
        this.specialAllowance = specialAllowance;
    }

    public BigDecimal getSpecialAllowancePercent() {
        return specialAllowancePercent;
    }

    public EmployeeSalary specialAllowancePercent(BigDecimal specialAllowancePercent) {
        this.specialAllowancePercent = specialAllowancePercent;
        return this;
    }

    public void setSpecialAllowancePercent(BigDecimal specialAllowancePercent) {
        this.specialAllowancePercent = specialAllowancePercent;
    }

    public String getSpecialAllowanceDescription() {
        return specialAllowanceDescription;
    }

    public EmployeeSalary specialAllowanceDescription(String specialAllowanceDescription) {
        this.specialAllowanceDescription = specialAllowanceDescription;
        return this;
    }

    public void setSpecialAllowanceDescription(String specialAllowanceDescription) {
        this.specialAllowanceDescription = specialAllowanceDescription;
    }

    public ActiveStatus getInsuranceActiveStatus() {
        return insuranceActiveStatus;
    }

    public EmployeeSalary insuranceActiveStatus(ActiveStatus insuranceActiveStatus) {
        this.insuranceActiveStatus = insuranceActiveStatus;
        return this;
    }

    public void setInsuranceActiveStatus(ActiveStatus insuranceActiveStatus) {
        this.insuranceActiveStatus = insuranceActiveStatus;
    }

    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public EmployeeSalary insuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
        return this;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public BigDecimal getInsurancePercent() {
        return insurancePercent;
    }

    public EmployeeSalary insurancePercent(BigDecimal insurancePercent) {
        this.insurancePercent = insurancePercent;
        return this;
    }

    public void setInsurancePercent(BigDecimal insurancePercent) {
        this.insurancePercent = insurancePercent;
    }

    public String getInsuranceDescription() {
        return insuranceDescription;
    }

    public EmployeeSalary insuranceDescription(String insuranceDescription) {
        this.insuranceDescription = insuranceDescription;
        return this;
    }

    public void setInsuranceDescription(String insuranceDescription) {
        this.insuranceDescription = insuranceDescription;
    }

    public InsuranceProcessType getInsuranceProcessType() {
        return insuranceProcessType;
    }

    public EmployeeSalary insuranceProcessType(InsuranceProcessType insuranceProcessType) {
        this.insuranceProcessType = insuranceProcessType;
        return this;
    }

    public void setInsuranceProcessType(InsuranceProcessType insuranceProcessType) {
        this.insuranceProcessType = insuranceProcessType;
    }

    public ActiveStatus getStatus() {
        return status;
    }

    public EmployeeSalary status(ActiveStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ActiveStatus status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeSalary employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeSalary)) {
            return false;
        }
        return id != null && id.equals(((EmployeeSalary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSalary{" +
            "id=" + getId() +
            ", gross=" + getGross() +
            ", incrementAmount=" + getIncrementAmount() +
            ", incrementPercentage=" + getIncrementPercentage() +
            ", salaryStartDate='" + getSalaryStartDate() + "'" +
            ", salaryEndDate='" + getSalaryEndDate() + "'" +
            ", nextIncrementDate='" + getNextIncrementDate() + "'" +
            ", basic=" + getBasic() +
            ", basicPercent=" + getBasicPercent() +
            ", houseRent=" + getHouseRent() +
            ", houseRentPercent=" + getHouseRentPercent() +
            ", totalAllowance=" + getTotalAllowance() +
            ", medicalAllowance=" + getMedicalAllowance() +
            ", medicalAllowancePercent=" + getMedicalAllowancePercent() +
            ", convinceAllowance=" + getConvinceAllowance() +
            ", convinceAllowancePercent=" + getConvinceAllowancePercent() +
            ", foodAllowance=" + getFoodAllowance() +
            ", foodAllowancePercent=" + getFoodAllowancePercent() +
            ", specialAllowanceActiveStatus='" + getSpecialAllowanceActiveStatus() + "'" +
            ", specialAllowance=" + getSpecialAllowance() +
            ", specialAllowancePercent=" + getSpecialAllowancePercent() +
            ", specialAllowanceDescription='" + getSpecialAllowanceDescription() + "'" +
            ", insuranceActiveStatus='" + getInsuranceActiveStatus() + "'" +
            ", insuranceAmount=" + getInsuranceAmount() +
            ", insurancePercent=" + getInsurancePercent() +
            ", insuranceDescription='" + getInsuranceDescription() + "'" +
            ", insuranceProcessType='" + getInsuranceProcessType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
