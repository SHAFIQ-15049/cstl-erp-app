package software.cstl.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;
import software.cstl.domain.enumeration.LeaveApplicationStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link software.cstl.domain.LeaveApplication} entity. This class is used
 * in {@link software.cstl.web.rest.LeaveApplicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-applications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LeaveApplicationCriteria implements Serializable, Criteria {
    /**
     * Class for filtering LeaveApplicationStatus
     */
    public static class LeaveApplicationStatusFilter extends Filter<LeaveApplicationStatus> {

        public LeaveApplicationStatusFilter() {
        }

        public LeaveApplicationStatusFilter(LeaveApplicationStatusFilter filter) {
            super(filter);
        }

        @Override
        public LeaveApplicationStatusFilter copy() {
            return new LeaveApplicationStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter from;

    private LocalDateFilter to;

    private IntegerFilter totalDays;

    private LeaveApplicationStatusFilter status;

    private StringFilter reason;

    private LongFilter appliedById;

    private LongFilter actionTakenById;

    private LongFilter leaveTypeId;

    private LongFilter applicantId;

    public LeaveApplicationCriteria() {
    }

    public LeaveApplicationCriteria(LeaveApplicationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.from = other.from == null ? null : other.from.copy();
        this.to = other.to == null ? null : other.to.copy();
        this.totalDays = other.totalDays == null ? null : other.totalDays.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.appliedById = other.appliedById == null ? null : other.appliedById.copy();
        this.actionTakenById = other.actionTakenById == null ? null : other.actionTakenById.copy();
        this.leaveTypeId = other.leaveTypeId == null ? null : other.leaveTypeId.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
    }

    @Override
    public LeaveApplicationCriteria copy() {
        return new LeaveApplicationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getFrom() {
        return from;
    }

    public void setFrom(LocalDateFilter from) {
        this.from = from;
    }

    public LocalDateFilter getTo() {
        return to;
    }

    public void setTo(LocalDateFilter to) {
        this.to = to;
    }

    public IntegerFilter getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(IntegerFilter totalDays) {
        this.totalDays = totalDays;
    }

    public LeaveApplicationStatusFilter getStatus() {
        return status;
    }

    public void setStatus(LeaveApplicationStatusFilter status) {
        this.status = status;
    }

    public StringFilter getReason() {
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public LongFilter getAppliedById() {
        return appliedById;
    }

    public void setAppliedById(LongFilter appliedById) {
        this.appliedById = appliedById;
    }

    public LongFilter getActionTakenById() {
        return actionTakenById;
    }

    public void setActionTakenById(LongFilter actionTakenById) {
        this.actionTakenById = actionTakenById;
    }

    public LongFilter getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(LongFilter leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public LongFilter getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(LongFilter applicantId) {
        this.applicantId = applicantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LeaveApplicationCriteria that = (LeaveApplicationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(from, that.from) &&
            Objects.equals(to, that.to) &&
            Objects.equals(totalDays, that.totalDays) &&
            Objects.equals(status, that.status) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(appliedById, that.appliedById) &&
            Objects.equals(actionTakenById, that.actionTakenById) &&
            Objects.equals(leaveTypeId, that.leaveTypeId) &&
            Objects.equals(applicantId, that.applicantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        from,
        to,
        totalDays,
        status,
        reason,
        appliedById,
        actionTakenById,
        leaveTypeId,
        applicantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplicationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (from != null ? "from=" + from + ", " : "") +
                (to != null ? "to=" + to + ", " : "") +
                (totalDays != null ? "totalDays=" + totalDays + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (reason != null ? "reason=" + reason + ", " : "") +
                (appliedById != null ? "appliedById=" + appliedById + ", " : "") +
                (actionTakenById != null ? "actionTakenById=" + actionTakenById + ", " : "") +
                (leaveTypeId != null ? "leaveTypeId=" + leaveTypeId + ", " : "") +
                (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
            "}";
    }

}
