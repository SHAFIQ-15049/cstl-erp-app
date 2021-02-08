package software.cstl.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class HolidayDateMapDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate holidayDate;

    private Long holidayId;

    public HolidayDateMapDTO() {
    }

    public HolidayDateMapDTO(Long id, @NotNull LocalDate holidayDate, Long holidayId) {
        this.id = id;
        this.holidayDate = holidayDate;
        this.holidayId = holidayId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    public Long getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(Long holidayId) {
        this.holidayId = holidayId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HolidayDateMapDTO)) {
            return false;
        }

        return id != null && id.equals(((HolidayDateMapDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WeekendDateMapDTO{" +
            "id=" + getId() +
            ", holidayDate='" + getHolidayDate() + "'" +
            ", holidayId=" + getHolidayId() +
            "}";
    }
}
