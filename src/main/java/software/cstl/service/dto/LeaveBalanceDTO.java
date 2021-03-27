package software.cstl.service.dto;

import software.cstl.domain.LeaveApplication;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class LeaveBalanceDTO implements Serializable {

    private Long id;

    private BigDecimal totalDays;

    private BigDecimal remainingDays;


    private Long employeeId;

    private String employeeName;

    private LocalDate employeeJoiningDate;

    private Long leaveTypeId;

    private String leaveTypeName;

    public LeaveBalanceDTO() {
    }

    public LeaveBalanceDTO(Long id, BigDecimal totalDays, BigDecimal remainingDays, Long employeeId, String employeeName, LocalDate employeeJoiningDate, Long leaveTypeId, String leaveTypeName) {
        this.id = id;
        this.totalDays = totalDays;
        this.remainingDays = remainingDays;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeJoiningDate = employeeJoiningDate;
        this.leaveTypeId = leaveTypeId;
        this.leaveTypeName = leaveTypeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(BigDecimal totalDays) {
        this.totalDays = totalDays;
    }

    public BigDecimal getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(BigDecimal remainingDays) {
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
