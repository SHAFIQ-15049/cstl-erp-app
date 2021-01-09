package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import software.cstl.domain.enumeration.WeekDay;

import software.cstl.domain.enumeration.WeekendStatus;

/**
 * A Weekend.
 */
@Entity
@Table(name = "weekend")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Weekend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private WeekDay day;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WeekendStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeekDay getDay() {
        return day;
    }

    public Weekend day(WeekDay day) {
        this.day = day;
        return this;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public WeekendStatus getStatus() {
        return status;
    }

    public Weekend status(WeekendStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(WeekendStatus status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Weekend)) {
            return false;
        }
        return id != null && id.equals(((Weekend) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Weekend{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
