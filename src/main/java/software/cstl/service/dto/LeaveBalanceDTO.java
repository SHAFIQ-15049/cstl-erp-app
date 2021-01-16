package software.cstl.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link software.cstl.domain.LeaveBalance} entity.
 */
public class LeaveBalanceDTO implements Serializable {

    private Long id;

    private Double totalDays;

    private Double remainingDays;


    private Long employeeId;

    private String employeeName;

    private LocalDate employeeJoiningDate;

    private Long leaveTypeId;

    private String leaveTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Double totalDays) {
        this.totalDays = totalDays;
    }

    public Double getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(Double remainingDays) {
        this.remainingDays = remainingDays;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDate getEmployeeJoiningDate() {
        return employeeJoiningDate;
    }

    public void setEmployeeJoiningDate(LocalDate employeeJoiningDate) {
        this.employeeJoiningDate = employeeJoiningDate;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveBalanceDTO)) {
            return false;
        }

        return id != null && id.equals(((LeaveBalanceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveBalanceDTO{" +
            "id=" + getId() +
            ", totalDays=" + getTotalDays() +
            ", remainingDays=" + getRemainingDays() +
            ", employeeId=" + getEmployeeId() +
            ", employeeName=" + getEmployeeName() +
            ", employeeJoiningDate=" + getEmployeeJoiningDate() +
            ", leaveTypeName=" + getLeaveTypeName() +
            ", leaveTypeId=" + getLeaveTypeId() +
            "}";
    }
}
