package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Holiday.
 */
@Entity
@Table(name = "holiday")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_from", nullable = false)
    private LocalDate from;

    @NotNull
    @Column(name = "jhi_to", nullable = false)
    private LocalDate to;

    @NotNull
    @Column(name = "total_days", nullable = false)
    private Integer totalDays;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "holidays", allowSetters = true)
    private HolidayType holidayType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFrom() {
        return from;
    }

    public Holiday from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public Holiday to(LocalDate to) {
        this.to = to;
        return this;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public Holiday totalDays(Integer totalDays) {
        this.totalDays = totalDays;
        return this;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public HolidayType getHolidayType() {
        return holidayType;
    }

    public Holiday holidayType(HolidayType holidayType) {
        this.holidayType = holidayType;
        return this;
    }

    public void setHolidayType(HolidayType holidayType) {
        this.holidayType = holidayType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Holiday)) {
            return false;
        }
        return id != null && id.equals(((Holiday) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Holiday{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", totalDays=" + getTotalDays() +
            "}";
    }
}
