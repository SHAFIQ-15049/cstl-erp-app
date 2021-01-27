package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import software.cstl.domain.enumeration.AttendanceMarkedAs;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marked_as", nullable = false)
    private AttendanceMarkedAs markedAs;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private AttendanceDataUpload attendanceDataUpload;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private EmployeeSalary employeeSalary;

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

    public AttendanceDataUpload getAttendanceDataUpload() {
        return attendanceDataUpload;
    }

    public Attendance attendanceDataUpload(AttendanceDataUpload attendanceDataUpload) {
        this.attendanceDataUpload = attendanceDataUpload;
        return this;
    }

    public void setAttendanceDataUpload(AttendanceDataUpload attendanceDataUpload) {
        this.attendanceDataUpload = attendanceDataUpload;
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
            "}";
    }
}
