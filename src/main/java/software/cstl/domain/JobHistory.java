package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A JobHistory.
 */
@Entity
@Table(name = "job_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobHistory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "serial", nullable = false)
    private Integer serial;

    @NotNull
    @Column(name = "organization", nullable = false)
    private String organization;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @Column(name = "jhi_from")
    private LocalDate from;

    @Column(name = "jhi_to")
    private LocalDate to;

    @Column(name = "pay_scale", precision = 21, scale = 2)
    private BigDecimal payScale;

    @Column(name = "total_experience")
    private Double totalExperience;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobHistories", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSerial() {
        return serial;
    }

    public JobHistory serial(Integer serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getOrganization() {
        return organization;
    }

    public JobHistory organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDesignation() {
        return designation;
    }

    public JobHistory designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getFrom() {
        return from;
    }

    public JobHistory from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public JobHistory to(LocalDate to) {
        this.to = to;
        return this;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public BigDecimal getPayScale() {
        return payScale;
    }

    public JobHistory payScale(BigDecimal payScale) {
        this.payScale = payScale;
        return this;
    }

    public void setPayScale(BigDecimal payScale) {
        this.payScale = payScale;
    }

    public Double getTotalExperience() {
        return totalExperience;
    }

    public JobHistory totalExperience(Double totalExperience) {
        this.totalExperience = totalExperience;
        return this;
    }

    public void setTotalExperience(Double totalExperience) {
        this.totalExperience = totalExperience;
    }

    public Employee getEmployee() {
        return employee;
    }

    public JobHistory employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobHistory)) {
            return false;
        }
        return id != null && id.equals(((JobHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobHistory{" +
            "id=" + getId() +
            ", serial=" + getSerial() +
            ", organization='" + getOrganization() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", payScale=" + getPayScale() +
            ", totalExperience=" + getTotalExperience() +
            "}";
    }
}
