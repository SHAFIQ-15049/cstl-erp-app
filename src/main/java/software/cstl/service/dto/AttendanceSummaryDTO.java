package software.cstl.service.dto;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

public class AttendanceSummaryDTO implements Serializable {

    private Long serialNo;

    private Instant inTime;

    private Instant outTime;

    private Duration diff;

    private Duration overTime;


    private Long employeeId;

    private String employeeName;

    private String employeeMachineId;

    private Long employeeSalaryId;

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
