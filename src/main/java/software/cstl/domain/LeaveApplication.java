package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import software.cstl.domain.enumeration.LeaveApplicationStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A LeaveApplication.
 */
@Entity
@Table(name = "leave_application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LeaveApplication implements Serializable {

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LeaveApplicationStatus status;

    @NotNull
    @Column(name = "reason", nullable = false)
    private String reason;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "leaveApplications", allowSetters = true)
    private User appliedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "leaveApplications", allowSetters = true)
    private User actionTakenBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "leaveApplications", allowSetters = true)
    private LeaveType leaveType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "leaveApplications", allowSetters = true)
    private Employee applicant;

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

    public LeaveApplication from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public LeaveApplication to(LocalDate to) {
        this.to = to;
        return this;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public LeaveApplication totalDays(Integer totalDays) {
        this.totalDays = totalDays;
        return this;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public LeaveApplicationStatus getStatus() {
        return status;
    }

    public LeaveApplication status(LeaveApplicationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(LeaveApplicationStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public LeaveApplication reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getAppliedBy() {
        return appliedBy;
    }

    public LeaveApplication appliedBy(User user) {
        this.appliedBy = user;
        return this;
    }

    public void setAppliedBy(User user) {
        this.appliedBy = user;
    }

    public User getActionTakenBy() {
        return actionTakenBy;
    }

    public LeaveApplication actionTakenBy(User user) {
        this.actionTakenBy = user;
        return this;
    }

    public void setActionTakenBy(User user) {
        this.actionTakenBy = user;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public LeaveApplication leaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
        return this;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Employee getApplicant() {
        return applicant;
    }

    public LeaveApplication applicant(Employee employee) {
        this.applicant = employee;
        return this;
    }

    public void setApplicant(Employee employee) {
        this.applicant = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveApplication)) {
            return false;
        }
        return id != null && id.equals(((LeaveApplication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplication{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", totalDays=" + getTotalDays() +
            ", status='" + getStatus() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
