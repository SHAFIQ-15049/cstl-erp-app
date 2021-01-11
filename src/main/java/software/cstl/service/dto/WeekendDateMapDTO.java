package software.cstl.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link software.cstl.domain.WeekendDateMap} entity.
 */
public class WeekendDateMapDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate weekendDate;

    private String remarks;


    private Long weekendId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getWeekendDate() {
        return weekendDate;
    }

    public void setWeekendDate(LocalDate weekendDate) {
        this.weekendDate = weekendDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getWeekendId() {
        return weekendId;
    }

    public void setWeekendId(Long weekendId) {
        this.weekendId = weekendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeekendDateMapDTO)) {
            return false;
        }

        return id != null && id.equals(((WeekendDateMapDTO) o).id);
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
            ", weekendDate='" + getWeekendDate() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", weekendId=" + getWeekendId() +
            "}";
    }
}
