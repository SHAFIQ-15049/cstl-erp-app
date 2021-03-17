package software.cstl.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class LeaveApplicationDetailDateMapDTO implements Serializable {

    private Long id;

    private LocalDate leaveAppliedDate;


    private Long leaveApplicationId;

    public LeaveApplicationDetailDateMapDTO() {
    }

    public LeaveApplicationDetailDateMapDTO(Long id, LocalDate leaveAppliedDate, Long leaveApplicationId) {
        this.id = id;
        this.leaveAppliedDate = leaveAppliedDate;
        this.leaveApplicationId = leaveApplicationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLeaveAppliedDate() {
        return leaveAppliedDate;
    }

    public void setLeaveAppliedDate(LocalDate leaveAppliedDate) {
        this.leaveAppliedDate = leaveAppliedDate;
    }

    public Long getLeaveApplicationId() {
        return leaveApplicationId;
    }

    public void setLeaveApplicationId(Long leaveApplicationId) {
        this.leaveApplicationId = leaveApplicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveApplicationDetailDateMapDTO)) {
            return false;
        }

        return id != null && id.equals(((LeaveApplicationDetailDateMapDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplicationDetailDateMapDTO{" +
            "id=" + getId() +
            ", leaveAppliedDate='" + getLeaveAppliedDate() + "'" +
            ", leaveApplicationId=" + getLeaveApplicationId() +
            "}";
    }
}
