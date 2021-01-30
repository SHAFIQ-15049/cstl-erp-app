package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.EmployeeType;
import software.cstl.domain.enumeration.LeaveAppliedStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Attendance.
 */
@Entity
@Table(name = "attendance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "attendance_time", nullable = false)
    private Instant attendanceTime;

    @NotNull
    @Column(name = "machine_no", nullable = false)
    private String machineNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "marked_as")
    private AttendanceMarkedAs markedAs;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_applied")
    private LeaveAppliedStatus leaveApplied;

    @Column(name = "employee_machine_id")
    private String employeeMachineId;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_category")
    private EmployeeCategory employeeCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type")
    private EmployeeType employeeType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private EmployeeSalary employeeSalary;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private Designation designation;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private Line line;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private Grade grade;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getAttendanceTime() {
        return attendanceTime;
    }

    public Attendance attendanceTime(Instant attendanceTime) {
        this.attendanceTime = attendanceTime;
        return this;
    }

    public void setAttendanceTime(Instant attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public Attendance machineNo(String machineNo) {
        this.machineNo = machineNo;
        return this;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public AttendanceMarkedAs getMarkedAs() {
        return markedAs;
    }

    public Attendance markedAs(AttendanceMarkedAs markedAs) {
        this.markedAs = markedAs;
        return this;
    }

    public void setMarkedAs(AttendanceMarkedAs markedAs) {
        this.markedAs = markedAs;
    }

    public LeaveAppliedStatus getLeaveApplied() {
        return leaveApplied;
    }

    public Attendance leaveApplied(LeaveAppliedStatus leaveApplied) {
        this.leaveApplied = leaveApplied;
        return this;
    }

    public void setLeaveApplied(LeaveAppliedStatus leaveApplied) {
        this.leaveApplied = leaveApplied;
    }

    public String getEmployeeMachineId() {
        return employeeMachineId;
    }

    public Attendance employeeMachineId(String employeeMachineId) {
        this.employeeMachineId = employeeMachineId;
        return this;
    }

    public void setEmployeeMachineId(String employeeMachineId) {
        this.employeeMachineId = employeeMachineId;
    }

    public EmployeeCategory getEmployeeCategory() {
        return employeeCategory;
    }

    public Attendance employeeCategory(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
        return this;
    }

    public void setEmployeeCategory(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public Attendance employeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
        return this;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Attendance employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeSalary getEmployeeSalary() {
        return employeeSalary;
    }

    public Attendance employeeSalary(EmployeeSalary employeeSalary) {
        this.employeeSalary = employeeSalary;
        return this;
    }

    public void setEmployeeSalary(EmployeeSalary employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public Department getDepartment() {
        return department;
    }

    public Attendance department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public Attendance designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Line getLine() {
        return line;
    }

    public Attendance line(Line line) {
        this.line = line;
        return this;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Grade getGrade() {
        return grade;
    }

    public Attendance grade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendance)) {
            return false;
        }
        return id != null && id.equals(((Attendance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attendance{" +
            "id=" + getId() +
            ", attendanceTime='" + getAttendanceTime() + "'" +
            ", machineNo='" + getMachineNo() + "'" +
            ", markedAs='" + getMarkedAs() + "'" +
            ", leaveApplied='" + getLeaveApplied() + "'" +
            ", employeeMachineId='" + getEmployeeMachineId() + "'" +
            ", employeeCategory='" + getEmployeeCategory() + "'" +
            ", employeeType='" + getEmployeeType() + "'" +
            "}";
    }
}
