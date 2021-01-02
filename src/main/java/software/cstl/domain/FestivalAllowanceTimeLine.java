package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import software.cstl.domain.enumeration.MonthType;

/**
 * A FestivalAllowanceTimeLine.
 */
@Entity
@Table(name = "festival_allowance_time_line")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FestivalAllowanceTimeLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private MonthType month;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public FestivalAllowanceTimeLine year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MonthType getMonth() {
        return month;
    }

    public FestivalAllowanceTimeLine month(MonthType month) {
        this.month = month;
        return this;
    }

    public void setMonth(MonthType month) {
        this.month = month;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FestivalAllowanceTimeLine)) {
            return false;
        }
        return id != null && id.equals(((FestivalAllowanceTimeLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FestivalAllowanceTimeLine{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", month='" + getMonth() + "'" +
            "}";
    }
}
