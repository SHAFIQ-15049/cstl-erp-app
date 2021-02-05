package software.cstl.service.dto;

import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.domain.enumeration.LeaveAppliedStatus;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

public class AttendanceSummaryDTO implements Serializable {

    private Long serialNo;

    private Instant inTime;

    private Instant outTime;

    private Duration diff;

    private Duration overTime;

    private AttendanceMarkedAs attendanceMarkedAs;

    private Long departmentId;

    private String departmentName;

    private Long employeeId;

    private String employeeName;

    private String employeeMachineId;

    private Long employeeSalaryId;

    private LeaveAppliedStatus leaveAppliedStatus;

    public Long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
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

    public Duration getDiff() {
        return diff;
    }

    public void setDiff(Duration diff) {
        this.diff = diff;
    }

    public Duration getOverTime() {
        return overTime;
    }

    public void setOverTime(Duration overTime) {
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

    public AttendanceMarkedAs getAttendanceMarkedAs() {
        return attendanceMarkedAs;
    }

    public void setAttendanceMarkedAs(AttendanceMarkedAs attendanceMarkedAs) {
        this.attendanceMarkedAs = attendanceMarkedAs;
    }

    public LeaveAppliedStatus getLeaveAppliedStatus() {
        return leaveAppliedStatus;
    }

    public void setLeaveAppliedStatus(LeaveAppliedStatus leaveAppliedStatus) {
        this.leaveAppliedStatus = leaveAppliedStatus;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceSummaryDTO)) {
            return false;
        }

        return employeeId != null && employeeId.equals(((AttendanceSummaryDTO) o).employeeId) &&
            inTime != null && inTime.equals(((AttendanceSummaryDTO) o).inTime) &&
            outTime != null && outTime.equals(((AttendanceSummaryDTO) o).outTime);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceSummaryDTO{" +
            "id=" + getSerialNo() +
            ", inTime='" + getInTime() + "'" +
            ", outTime='" + getOutTime() + "'" +
            ", diff='" + getDiff() + "'" +
            ", overTime='" + getOverTime() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeSalaryId=" + getEmployeeSalaryId() +
            "}";
    }
}
