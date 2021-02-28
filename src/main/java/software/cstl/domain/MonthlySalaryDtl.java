package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import software.cstl.domain.enumeration.SalaryExecutionStatus;

import software.cstl.domain.enumeration.PayrollGenerationType;

/**
 * A MonthlySalaryDtl.
 */
@Entity
@Table(
    name = "monthly_salary_dtl",
    uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id","monthly_salary_id","status"})
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MonthlySalaryDtl extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gross", precision = 21, scale = 2)
    private BigDecimal gross;

    @Column(name = "basic", precision = 21, scale = 2)
    private BigDecimal basic;

    @Column(name = "basic_percent", precision = 21, scale = 2)
    private BigDecimal basicPercent;

    @Column(name = "house_rent", precision = 21, scale = 2)
    private BigDecimal houseRent;

    @Column(name = "house_rent_percent", precision = 21, scale = 2)
    private BigDecimal houseRentPercent;

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

    @Column(name = "fine", precision = 21, scale = 2)
    private BigDecimal fine;

    @Column(name = "advance", precision = 21, scale = 2)
    private BigDecimal advance;

    @Column(name = "total_working_days")
    private Integer totalWorkingDays;

    @Column(name = "regular_leave")
    private Integer regularLeave;

    @Column(name = "sick_leave")
    private Integer sickLeave;

    @Column(name = "compensation_leave")
    private Integer compensationLeave;

    @Column(name = "festival_leave")
    private Integer festivalLeave;

    @Column(name = "weekly_leave")
    private Integer weeklyLeave;

    @Column(name = "present")
    private Integer present;

    @Column(name = "absent")
    private Integer absent;

    @Column(name = "total_month_days")
    private Integer totalMonthDays;

    @Column(name = "over_time_hour")
    private Double overTimeHour;

    @Column(name = "over_time_salary_hourly", precision = 21, scale = 2)
    private BigDecimal overTimeSalaryHourly;

    @Column(name = "over_time_salary", precision = 21, scale = 2)
    private BigDecimal overTimeSalary;

    @Column(name = "present_bonus", precision = 21, scale = 2)
    private BigDecimal presentBonus;

    @Column(name = "absent_fine", precision = 21, scale = 2)
    private BigDecimal absentFine;

    @Column(name = "stamp_price", precision = 21, scale = 2)
    private BigDecimal stampPrice;

    @Column(name = "tax", precision = 21, scale = 2)
    private BigDecimal tax;

    @Column(name = "others", precision = 21, scale = 2)
    private BigDecimal others;

    @Column(name = "total_payable", precision = 21, scale = 2)
    private BigDecimal totalPayable;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SalaryExecutionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PayrollGenerationType type;

    @Column(name = "executed_on")
    private Instant executedOn;

    @Column(name = "executed_by")
    private String executedBy;

    @Lob
    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties(value = "monthlySalaryDtls", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "monthlySalaryDtls", allowSetters = true)
    private MonthlySalary monthlySalary;

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

    public MonthlySalaryDtl gross(BigDecimal gross) {
        this.gross = gross;
        return this;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getBasic() {
        return basic;
    }

    public MonthlySalaryDtl basic(BigDecimal basic) {
        this.basic = basic;
        return this;
    }

    public void setBasic(BigDecimal basic) {
        this.basic = basic;
    }

    public BigDecimal getBasicPercent() {
        return basicPercent;
    }

    public MonthlySalaryDtl basicPercent(BigDecimal basicPercent) {
        this.basicPercent = basicPercent;
        return this;
    }

    public void setBasicPercent(BigDecimal basicPercent) {
        this.basicPercent = basicPercent;
    }

    public BigDecimal getHouseRent() {
        return houseRent;
    }

    public MonthlySalaryDtl houseRent(BigDecimal houseRent) {
        this.houseRent = houseRent;
        return this;
    }

    public void setHouseRent(BigDecimal houseRent) {
        this.houseRent = houseRent;
    }

    public BigDecimal getHouseRentPercent() {
        return houseRentPercent;
    }

    public MonthlySalaryDtl houseRentPercent(BigDecimal houseRentPercent) {
        this.houseRentPercent = houseRentPercent;
        return this;
    }

    public void setHouseRentPercent(BigDecimal houseRentPercent) {
        this.houseRentPercent = houseRentPercent;
    }

    public BigDecimal getMedicalAllowance() {
        return medicalAllowance;
    }

    public MonthlySalaryDtl medicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
        return this;
    }

    public void setMedicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public BigDecimal getMedicalAllowancePercent() {
        return medicalAllowancePercent;
    }

    public MonthlySalaryDtl medicalAllowancePercent(BigDecimal medicalAllowancePercent) {
        this.medicalAllowancePercent = medicalAllowancePercent;
        return this;
    }

    public void setMedicalAllowancePercent(BigDecimal medicalAllowancePercent) {
        this.medicalAllowancePercent = medicalAllowancePercent;
    }

    public BigDecimal getConvinceAllowance() {
        return convinceAllowance;
    }

    public MonthlySalaryDtl convinceAllowance(BigDecimal convinceAllowance) {
        this.convinceAllowance = convinceAllowance;
        return this;
    }

    public void setConvinceAllowance(BigDecimal convinceAllowance) {
        this.convinceAllowance = convinceAllowance;
    }

    public BigDecimal getConvinceAllowancePercent() {
        return convinceAllowancePercent;
    }

    public MonthlySalaryDtl convinceAllowancePercent(BigDecimal convinceAllowancePercent) {
        this.convinceAllowancePercent = convinceAllowancePercent;
        return this;
    }

    public void setConvinceAllowancePercent(BigDecimal convinceAllowancePercent) {
        this.convinceAllowancePercent = convinceAllowancePercent;
    }

    public BigDecimal getFoodAllowance() {
        return foodAllowance;
    }

    public MonthlySalaryDtl foodAllowance(BigDecimal foodAllowance) {
        this.foodAllowance = foodAllowance;
        return this;
    }

    public void setFoodAllowance(BigDecimal foodAllowance) {
        this.foodAllowance = foodAllowance;
    }

    public BigDecimal getFoodAllowancePercent() {
        return foodAllowancePercent;
    }

    public MonthlySalaryDtl foodAllowancePercent(BigDecimal foodAllowancePercent) {
        this.foodAllowancePercent = foodAllowancePercent;
        return this;
    }

    public void setFoodAllowancePercent(BigDecimal foodAllowancePercent) {
        this.foodAllowancePercent = foodAllowancePercent;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public MonthlySalaryDtl fine(BigDecimal fine) {
        this.fine = fine;
        return this;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public BigDecimal getAdvance() {
        return advance;
    }

    public MonthlySalaryDtl advance(BigDecimal advance) {
        this.advance = advance;
        return this;
    }

    public void setAdvance(BigDecimal advance) {
        this.advance = advance;
    }

    public Integer getTotalWorkingDays() {
        return totalWorkingDays;
    }

    public MonthlySalaryDtl totalWorkingDays(Integer totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
        return this;
    }

    public void setTotalWorkingDays(Integer totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
    }

    public Integer getRegularLeave() {
        return regularLeave;
    }

    public MonthlySalaryDtl regularLeave(Integer regularLeave) {
        this.regularLeave = regularLeave;
        return this;
    }

    public void setRegularLeave(Integer regularLeave) {
        this.regularLeave = regularLeave;
    }

    public Integer getSickLeave() {
        return sickLeave;
    }

    public MonthlySalaryDtl sickLeave(Integer sickLeave) {
        this.sickLeave = sickLeave;
        return this;
    }

    public void setSickLeave(Integer sickLeave) {
        this.sickLeave = sickLeave;
    }

    public Integer getCompensationLeave() {
        return compensationLeave;
    }

    public MonthlySalaryDtl compensationLeave(Integer compensationLeave) {
        this.compensationLeave = compensationLeave;
        return this;
    }

    public void setCompensationLeave(Integer compensationLeave) {
        this.compensationLeave = compensationLeave;
    }

    public Integer getFestivalLeave() {
        return festivalLeave;
    }

    public MonthlySalaryDtl festivalLeave(Integer festivalLeave) {
        this.festivalLeave = festivalLeave;
        return this;
    }

    public void setFestivalLeave(Integer festivalLeave) {
        this.festivalLeave = festivalLeave;
    }

    public Integer getWeeklyLeave() {
        return weeklyLeave;
    }

    public MonthlySalaryDtl weeklyLeave(Integer weeklyLeave) {
        this.weeklyLeave = weeklyLeave;
        return this;
    }

    public void setWeeklyLeave(Integer weeklyLeave) {
        this.weeklyLeave = weeklyLeave;
    }

    public Integer getPresent() {
        return present;
    }

    public MonthlySalaryDtl present(Integer present) {
        this.present = present;
        return this;
    }

    public void setPresent(Integer present) {
        this.present = present;
    }

    public Integer getAbsent() {
        return absent;
    }

    public MonthlySalaryDtl absent(Integer absent) {
        this.absent = absent;
        return this;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public Integer getTotalMonthDays() {
        return totalMonthDays;
    }

    public MonthlySalaryDtl totalMonthDays(Integer totalMonthDays) {
        this.totalMonthDays = totalMonthDays;
        return this;
    }

    public void setTotalMonthDays(Integer totalMonthDays) {
        this.totalMonthDays = totalMonthDays;
    }

    public Double getOverTimeHour() {
        return overTimeHour;
    }

    public MonthlySalaryDtl overTimeHour(Double overTimeHour) {
        this.overTimeHour = overTimeHour;
        return this;
    }

    public void setOverTimeHour(Double overTimeHour) {
        this.overTimeHour = overTimeHour;
    }

    public BigDecimal getOverTimeSalaryHourly() {
        return overTimeSalaryHourly;
    }

    public MonthlySalaryDtl overTimeSalaryHourly(BigDecimal overTimeSalaryHourly) {
        this.overTimeSalaryHourly = overTimeSalaryHourly;
        return this;
    }

    public void setOverTimeSalaryHourly(BigDecimal overTimeSalaryHourly) {
        this.overTimeSalaryHourly = overTimeSalaryHourly;
    }

    public BigDecimal getOverTimeSalary() {
        return overTimeSalary;
    }

    public MonthlySalaryDtl overTimeSalary(BigDecimal overTimeSalary) {
        this.overTimeSalary = overTimeSalary;
        return this;
    }

    public void setOverTimeSalary(BigDecimal overTimeSalary) {
        this.overTimeSalary = overTimeSalary;
    }

    public BigDecimal getPresentBonus() {
        return presentBonus;
    }

    public MonthlySalaryDtl presentBonus(BigDecimal presentBonus) {
        this.presentBonus = presentBonus;
        return this;
    }

    public void setPresentBonus(BigDecimal presentBonus) {
        this.presentBonus = presentBonus;
    }

    public BigDecimal getAbsentFine() {
        return absentFine;
    }

    public MonthlySalaryDtl absentFine(BigDecimal absentFine) {
        this.absentFine = absentFine;
        return this;
    }

    public void setAbsentFine(BigDecimal absentFine) {
        this.absentFine = absentFine;
    }

    public BigDecimal getStampPrice() {
        return stampPrice;
    }

    public MonthlySalaryDtl stampPrice(BigDecimal stampPrice) {
        this.stampPrice = stampPrice;
        return this;
    }

    public void setStampPrice(BigDecimal stampPrice) {
        this.stampPrice = stampPrice;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public MonthlySalaryDtl tax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getOthers() {
        return others;
    }

    public MonthlySalaryDtl others(BigDecimal others) {
        this.others = others;
        return this;
    }

    public void setOthers(BigDecimal others) {
        this.others = others;
    }

    public BigDecimal getTotalPayable() {
        return totalPayable;
    }

    public MonthlySalaryDtl totalPayable(BigDecimal totalPayable) {
        this.totalPayable = totalPayable;
        return this;
    }

    public void setTotalPayable(BigDecimal totalPayable) {
        this.totalPayable = totalPayable;
    }

    public SalaryExecutionStatus getStatus() {
        return status;
    }

    public MonthlySalaryDtl status(SalaryExecutionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SalaryExecutionStatus status) {
        this.status = status;
    }

    public PayrollGenerationType getType() {
        return type;
    }

    public MonthlySalaryDtl type(PayrollGenerationType type) {
        this.type = type;
        return this;
    }

    public void setType(PayrollGenerationType type) {
        this.type = type;
    }

    public Instant getExecutedOn() {
        return executedOn;
    }

    public MonthlySalaryDtl executedOn(Instant executedOn) {
        this.executedOn = executedOn;
        return this;
    }

    public void setExecutedOn(Instant executedOn) {
        this.executedOn = executedOn;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public MonthlySalaryDtl executedBy(String executedBy) {
        this.executedBy = executedBy;
        return this;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public String getNote() {
        return note;
    }

    public MonthlySalaryDtl note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Employee getEmployee() {
        return employee;
    }

    public MonthlySalaryDtl employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public MonthlySalary getMonthlySalary() {
        return monthlySalary;
    }

    public MonthlySalaryDtl monthlySalary(MonthlySalary monthlySalary) {
        this.monthlySalary = monthlySalary;
        return this;
    }

    public void setMonthlySalary(MonthlySalary monthlySalary) {
        this.monthlySalary = monthlySalary;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthlySalaryDtl)) {
            return false;
        }
        return id != null && id.equals(((MonthlySalaryDtl) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlySalaryDtl{" +
            "id=" + getId() +
            ", gross=" + getGross() +
            ", basic=" + getBasic() +
            ", basicPercent=" + getBasicPercent() +
            ", houseRent=" + getHouseRent() +
            ", houseRentPercent=" + getHouseRentPercent() +
            ", medicalAllowance=" + getMedicalAllowance() +
            ", medicalAllowancePercent=" + getMedicalAllowancePercent() +
            ", convinceAllowance=" + getConvinceAllowance() +
            ", convinceAllowancePercent=" + getConvinceAllowancePercent() +
            ", foodAllowance=" + getFoodAllowance() +
            ", foodAllowancePercent=" + getFoodAllowancePercent() +
            ", fine=" + getFine() +
            ", advance=" + getAdvance() +
            ", totalWorkingDays=" + getTotalWorkingDays() +
            ", regularLeave=" + getRegularLeave() +
            ", sickLeave=" + getSickLeave() +
            ", compensationLeave=" + getCompensationLeave() +
            ", festivalLeave=" + getFestivalLeave() +
            ", weeklyLeave=" + getWeeklyLeave() +
            ", present=" + getPresent() +
            ", absent=" + getAbsent() +
            ", totalMonthDays=" + getTotalMonthDays() +
            ", overTimeHour=" + getOverTimeHour() +
            ", overTimeSalaryHourly=" + getOverTimeSalaryHourly() +
            ", overTimeSalary=" + getOverTimeSalary() +
            ", presentBonus=" + getPresentBonus() +
            ", absentFine=" + getAbsentFine() +
            ", stampPrice=" + getStampPrice() +
            ", tax=" + getTax() +
            ", others=" + getOthers() +
            ", totalPayable=" + getTotalPayable() +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            ", executedOn='" + getExecutedOn() + "'" +
            ", executedBy='" + getExecutedBy() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
