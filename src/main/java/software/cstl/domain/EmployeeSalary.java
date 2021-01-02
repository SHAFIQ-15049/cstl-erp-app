package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import software.cstl.domain.enumeration.ActiveStatus;

/**
 * A EmployeeSalary.
 */
@Entity
@Table(name = "employee_allowance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeSalary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gross", precision = 21, scale = 2)
    private BigDecimal gross;

    @Column(name = "increment_amount", precision = 21, scale = 2)
    private BigDecimal incrementAmount;

    @Column(name = "increment_percentage", precision = 21, scale = 2)
    private BigDecimal incrementPercentage;

    @Column(name = "salary_start_date")
    private LocalDate salaryStartDate;

    @Column(name = "salary_end_date")
    private LocalDate salaryEndDate;

    @Column(name = "next_increment_date")
    private LocalDate nextIncrementDate;

    @Column(name = "basic", precision = 21, scale = 2)
    private BigDecimal basic;

    @Column(name = "basic_percent", precision = 21, scale = 2)
    private BigDecimal basicPercent;

    @Column(name = "house_rent", precision = 21, scale = 2)
    private BigDecimal houseRent;

    @Column(name = "house_rent_percent", precision = 21, scale = 2)
    private BigDecimal houseRentPercent;

    @NotNull
    @Column(name = "total_allowance", precision = 21, scale = 2, nullable = false)
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

    public LocalDate getSalaryStartDate() {
        return salaryStartDate;
    }

    public EmployeeSalary salaryStartDate(LocalDate salaryStartDate) {
        this.salaryStartDate = salaryStartDate;
        return this;
    }

    public void setSalaryStartDate(LocalDate salaryStartDate) {
        this.salaryStartDate = salaryStartDate;
    }

    public LocalDate getSalaryEndDate() {
        return salaryEndDate;
    }

    public EmployeeSalary salaryEndDate(LocalDate salaryEndDate) {
        this.salaryEndDate = salaryEndDate;
        return this;
    }

    public void setSalaryEndDate(LocalDate salaryEndDate) {
        this.salaryEndDate = salaryEndDate;
    }

    public LocalDate getNextIncrementDate() {
        return nextIncrementDate;
    }

    public EmployeeSalary nextIncrementDate(LocalDate nextIncrementDate) {
        this.nextIncrementDate = nextIncrementDate;
        return this;
    }

    public void setNextIncrementDate(LocalDate nextIncrementDate) {
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
            ", status='" + getStatus() + "'" +
            "}";
    }
}
