package software.cstl.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import software.cstl.domain.enumeration.ConsiderAsType;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link software.cstl.domain.Attendance} entity. This class is used
 * in {@link software.cstl.web.rest.AttendanceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attendances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AttendanceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ConsiderAsType
     */
    public static class ConsiderAsTypeFilter extends Filter<ConsiderAsType> {

        public ConsiderAsTypeFilter() {
        }

        public ConsiderAsTypeFilter(ConsiderAsTypeFilter filter) {
            super(filter);
        }

        @Override
        public ConsiderAsTypeFilter copy() {
            return new ConsiderAsTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter attendanceDate;

    private InstantFilter attendanceTime;

    private ConsiderAsTypeFilter considerAs;

    private LongFilter employeeId;

    private LongFilter attendanceDataUploadId;

    private LongFilter employeeSalaryId;

    public AttendanceCriteria() {
    }

    public AttendanceCriteria(AttendanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.attendanceDate = other.attendanceDate == null ? null : other.attendanceDate.copy();
        this.attendanceTime = other.attendanceTime == null ? null : other.attendanceTime.copy();
        this.considerAs = other.considerAs == null ? null : other.considerAs.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.attendanceDataUploadId = other.attendanceDataUploadId == null ? null : other.attendanceDataUploadId.copy();
        this.employeeSalaryId = other.employeeSalaryId == null ? null : other.employeeSalaryId.copy();
    }

    @Override
    public AttendanceCriteria copy() {
        return new AttendanceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDateFilter attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public InstantFilter getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(InstantFilter attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public ConsiderAsTypeFilter getConsiderAs() {
        return considerAs;
    }

    public void setConsiderAs(ConsiderAsTypeFilter considerAs) {
        this.considerAs = considerAs;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getAttendanceDataUploadId() {
        return attendanceDataUploadId;
    }

    public void setAttendanceDataUploadId(LongFilter attendanceDataUploadId) {
        this.attendanceDataUploadId = attendanceDataUploadId;
    }

    public LongFilter getEmployeeSalaryId() {
        return employeeSalaryId;
    }

    public void setEmployeeSalaryId(LongFilter employeeSalaryId) {
        this.employeeSalaryId = employeeSalaryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceCriteria that = (AttendanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(attendanceDate, that.attendanceDate) &&
            Objects.equals(attendanceTime, that.attendanceTime) &&
            Objects.equals(considerAs, that.considerAs) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(attendanceDataUploadId, that.attendanceDataUploadId) &&
            Objects.equals(employeeSalaryId, that.employeeSalaryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        attendanceDate,
        attendanceTime,
        considerAs,
        employeeId,
        attendanceDataUploadId,
        employeeSalaryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (attendanceDate != null ? "attendanceDate=" + attendanceDate + ", " : "") +
                (attendanceTime != null ? "attendanceTime=" + attendanceTime + ", " : "") +
                (considerAs != null ? "considerAs=" + considerAs + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (attendanceDataUploadId != null ? "attendanceDataUploadId=" + attendanceDataUploadId + ", " : "") +
                (employeeSalaryId != null ? "employeeSalaryId=" + employeeSalaryId + ", " : "") +
            "}";
    }

}
