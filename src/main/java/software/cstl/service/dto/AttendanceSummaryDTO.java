package software.cstl.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

public class AttendanceSummaryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate attendanceDate;

    @NotNull
    private Instant inTime;

    private Instant outTime;

    @NotNull
    private String diff;

    @NotNull
    private String overTime;


    private Long employeeId;

    private String employeeName;

    private String employeeMachineId;

    private Long employeeSalaryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Instant getInTime() {
        return inTime;
    }

    public void setInTime(Instant inTime) {
        this.inTime = inTime;
    }

    public Instant getOutTime() {
        return outTime;
    }

    public void setOutTime(Instant outTime) {
        this.outTime = outTime;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeMachineId() {
        return employeeMachineId;
    }

    public void setEmployeeMachineId(String employeeMachineId) {
        this.employeeMachineId = employeeMachineId;
    }

    public Long getEmployeeSalaryId() {
        return employeeSalaryId;
    }

    public void setEmployeeSalaryId(Long employeeSalaryId) {
        this.employeeSalaryId = employeeSalaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceSummaryDTO)) {
            return false;
        }

        return id != null && id.equals(((AttendanceSummaryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceSummaryDTO{" +
            "id=" + getId() +
            ", attendanceDate='" + getAttendanceDate() + "'" +
            ", inTime='" + getInTime() + "'" +
            ", outTime='" + getOutTime() + "'" +
            ", diff='" + getDiff() + "'" +
            ", overTime='" + getOverTime() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeSalaryId=" + getEmployeeSalaryId() +
            "}";
    }
}
