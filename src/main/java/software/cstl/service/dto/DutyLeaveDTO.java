package software.cstl.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class DutyLeaveDTO implements Serializable {

    private Long id;

    private LocalDate fromDate;

    private LocalDate toDate;


    private Long employeeId;

    private String employeeName;

    private AttendanceSummaryDTO attendanceSummaryDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
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

    public AttendanceSummaryDTO getAttendanceSummaryDTO() {
        return attendanceSummaryDTO;
    }

    public void setAttendanceSummaryDTO(AttendanceSummaryDTO attendanceSummaryDTO) {
        this.attendanceSummaryDTO = attendanceSummaryDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DutyLeaveDTO)) {
            return false;
        }

        return id != null && id.equals(((DutyLeaveDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DutyLeaveDTO{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeName='" + getEmployeeName() + "'" +
            "}";
    }
}
