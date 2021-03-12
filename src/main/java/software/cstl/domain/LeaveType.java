package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

import software.cstl.domain.enumeration.LeaveTypeName;

/**
 * A LeaveType.
 */
@Entity
@Table(name = "leave_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LeaveType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private LeaveTypeName name;

    @NotNull
    @Column(name = "total_days", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalDays;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeaveTypeName getName() {
        return name;
    }

    public LeaveType name(LeaveTypeName name) {
        this.name = name;
        return this;
    }

    public void setName(LeaveTypeName name) {
        this.name = name;
    }

    public BigDecimal getTotalDays() {
        return totalDays;
    }

    public LeaveType totalDays(BigDecimal totalDays) {
        this.totalDays = totalDays;
        return this;
    }

    public void setTotalDays(BigDecimal totalDays) {
        this.totalDays = totalDays;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveType)) {
            return false;
        }
        return id != null && id.equals(((LeaveType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", totalDays=" + getTotalDays() +
            "}";
    }
}
