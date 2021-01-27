package software.cstl.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import software.cstl.domain.enumeration.AttendanceMarkedAs;

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
     * Class for filtering AttendanceMarkedAs
     */
    public static class AttendanceMarkedAsFilter extends Filter<AttendanceMarkedAs> {

        public AttendanceMarkedAsFilter() {
        }

        public AttendanceMarkedAsFilter(AttendanceMarkedAsFilter filter) {
            super(filter);
        }

        @Override
        public AttendanceMarkedAsFilter copy() {
            return new AttendanceMarkedAsFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter attendanceTime;

    private StringFilter machineNo;

    private AttendanceMarkedAsFilter markedAs;

    private LongFilter employeeId;

    private LongFilter attendanceDataUploadId;

    private LongFilter employeeSalaryId;

    public AttendanceCriteria() {
    }

    public AttendanceCriteria(AttendanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.attendanceTime = other.attendanceTime == null ? null : other.attendanceTime.copy();
        this.machineNo = other.machineNo == null ? null : other.machineNo.copy();
        this.markedAs = other.markedAs == null ? null : other.markedAs.copy();
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

    public InstantFilter getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(InstantFilter attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public StringFilter getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(StringFilter machineNo) {
        this.machineNo = machineNo;
    }

    public AttendanceMarkedAsFilter getMarkedAs() {
        return markedAs;
    }

    public void setMarkedAs(AttendanceMarkedAsFilter markedAs) {
        this.markedAs = markedAs;
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
            Objects.equals(attendanceTime, that.attendanceTime) &&
            Objects.equals(machineNo, that.machineNo) &&
            Objects.equals(markedAs, that.markedAs) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(attendanceDataUploadId, that.attendanceDataUploadId) &&
            Objects.equals(employeeSalaryId, that.employeeSalaryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        attendanceTime,
        machineNo,
        markedAs,
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
                (attendanceTime != null ? "attendanceTime=" + attendanceTime + ", " : "") +
                (machineNo != null ? "machineNo=" + machineNo + ", " : "") +
                (markedAs != null ? "markedAs=" + markedAs + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (attendanceDataUploadId != null ? "attendanceDataUploadId=" + attendanceDataUploadId + ", " : "") +
                (employeeSalaryId != null ? "employeeSalaryId=" + employeeSalaryId + ", " : "") +
            "}";
    }

}
