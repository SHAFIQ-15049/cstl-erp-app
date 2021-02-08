package software.cstl.service.dto;

import software.cstl.domain.enumeration.WeekDay;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class WeekendDateMapDTO implements Serializable {

    private Long serialNo;

    @NotNull
    private LocalDate weekendDate;


    private Long weekendId;

    private WeekDay weekendDay;

    public WeekendDateMapDTO() {
    }

    public WeekendDateMapDTO(Long serialNo, @NotNull LocalDate weekendDate, Long weekendId, WeekDay weekendDay) {
        this.serialNo = serialNo;
        this.weekendDate = weekendDate;
        this.weekendId = weekendId;
        this.weekendDay = weekendDay;
    }

    public Long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Long id) {
        this.serialNo = id;
    }

    public LocalDate getWeekendDate() {
        return weekendDate;
    }

    public void setWeekendDate(LocalDate weekendDate) {
        this.weekendDate = weekendDate;
    }

    public Long getWeekendId() {
        return weekendId;
    }

    public void setWeekendId(Long weekendId) {
        this.weekendId = weekendId;
    }

    public WeekDay getWeekendDay() {
        return weekendDay;
    }

    public void setWeekendDay(WeekDay weekendDay) {
        this.weekendDay = weekendDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeekendDateMapDTO)) {
            return false;
        }

        return serialNo != null && serialNo.equals(((WeekendDateMapDTO) o).serialNo);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WeekendDateMapDTO{" +
            "id=" + getSerialNo() +
            ", weekendDate='" + getWeekendDate() + "'" +
            ", weekendId=" + getWeekendId() +
            ", weekendDay='" + getWeekendDay() + "'" +
            "}";
    }
}
