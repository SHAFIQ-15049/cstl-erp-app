package software.cstl.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.EmployeeType;
import software.cstl.domain.enumeration.LeaveAppliedStatus;

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

    /**
     * Class for filtering LeaveAppliedStatus
     */
    public static class LeaveAppliedStatusFilter extends Filter<LeaveAppliedStatus> {

        public LeaveAppliedStatusFilter() {
        }

        public LeaveAppliedStatusFilter(LeaveAppliedStatusFilter filter) {
            super(filter);
        }

        @Override
        public LeaveAppliedStatusFilter copy() {
            return new LeaveAppliedStatusFilter(this);
        }

    }

    /**
     * Class for filtering EmployeeCategory
     */
    public static class EmployeeCategoryFilter extends Filter<EmployeeCategory> {

        public EmployeeCategoryFilter() {
        }

        public EmployeeCategoryFilter(EmployeeCategoryFilter filter) {
            super(filter);
        }

        @Override
        public EmployeeCategoryFilter copy() {
            return new EmployeeCategoryFilter(this);
        }

    }

    /**
     * Class for filtering EmployeeType
     */
    public static class EmployeeTypeFilter extends Filter<EmployeeType> {

        public EmployeeTypeFilter() {
        }

        public EmployeeTypeFilter(EmployeeTypeFilter filter) {
            super(filter);
        }

        @Override
        public EmployeeTypeFilter copy() {
            return new EmployeeTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter attendanceTime;

    private StringFilter machineNo;

    private AttendanceMarkedAsFilter markedAs;

    private LeaveAppliedStatusFilter leaveApplied;

    private StringFilter employeeMachineId;

    private EmployeeCategoryFilter employeeCategory;

    private EmployeeTypeFilter employeeType;

    private LongFilter employeeId;

    private LongFilter employeeSalaryId;

    private StringFilter empId;

    private LongFilter departmentId;

    private LongFilter designationId;

    private LongFilter lineId;

    private LongFilter gradeId;

    public AttendanceCriteria() {
    }

    public AttendanceCriteria(AttendanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.attendanceTime = other.attendanceTime == null ? null : other.attendanceTime.copy();
        this.machineNo = other.machineNo == null ? null : other.machineNo.copy();
        this.markedAs = other.markedAs == null ? null : other.markedAs.copy();
        this.leaveApplied = other.leaveApplied == null ? null : other.leaveApplied.copy();
        this.employeeMachineId = other.employeeMachineId == null ? null : other.employeeMachineId.copy();
        this.employeeCategory = other.employeeCategory == null ? null : other.employeeCategory.copy();
        this.employeeType = other.employeeType == null ? null : other.employeeType.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.empId = other.empId == null ? null : other.empId.copy();
        this.employeeSalaryId = other.employeeSalaryId == null ? null : other.employeeSalaryId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
        this.lineId = other.lineId == null ? null : other.lineId.copy();
        this.gradeId = other.gradeId == null ? null : other.gradeId.copy();
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

    public LeaveAppliedStatusFilter getLeaveApplied() {
        return leaveApplied;
    }

    public void setLeaveApplied(LeaveAppliedStatusFilter leaveApplied) {
        this.leaveApplied = leaveApplied;
    }

    public StringFilter getEmployeeMachineId() {
        return employeeMachineId;
    }

    public void setEmployeeMachineId(StringFilter employeeMachineId) {
        this.employeeMachineId = employeeMachineId;
    }

    public EmployeeCategoryFilter getEmployeeCategory() {
        return employeeCategory;
    }

    public void setEmployeeCategory(EmployeeCategoryFilter employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public EmployeeTypeFilter getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeTypeFilter employeeType) {
        this.employeeType = employeeType;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getEmployeeSalaryId() {
        return employeeSalaryId;
    }

    public void setEmployeeSalaryId(LongFilter employeeSalaryId) {
        this.employeeSalaryId = employeeSalaryId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getDesignationId() {
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
    }

    public LongFilter getLineId() {
        return lineId;
    }

    public void setLineId(LongFilter lineId) {
        this.lineId = lineId;
    }

    public LongFilter getGradeId() {
        return gradeId;
    }

    public void setGradeId(LongFilter gradeId) {
        this.gradeId = gradeId;
    }

    public StringFilter getEmpId() {
        return empId;
    }

    public void setEmpId(StringFilter empId) {
        this.empId = empId;
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
                Objects.equals(leaveApplied, that.leaveApplied) &&
                Objects.equals(employeeMachineId, that.employeeMachineId) &&
                Objects.equals(employeeCategory, that.employeeCategory) &&
                Objects.equals(employeeType, that.employeeType) &&
                Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(empId, that.empId) &&
                Objects.equals(employeeSalaryId, that.employeeSalaryId) &&
                Objects.equals(departmentId, that.departmentId) &&
                Objects.equals(designationId, that.designationId) &&
                Objects.equals(lineId, that.lineId) &&
                Objects.equals(gradeId, that.gradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            attendanceTime,
            machineNo,
            markedAs,
            leaveApplied,
            employeeMachineId,
            employeeCategory,
            employeeType,
            employeeId,
            empId,
            employeeSalaryId,
            departmentId,
            designationId,
            lineId,
            gradeId
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
            (leaveApplied != null ? "leaveApplied=" + leaveApplied + ", " : "") +
            (employeeMachineId != null ? "employeeMachineId=" + employeeMachineId + ", " : "") +
            (employeeCategory != null ? "employeeCategory=" + employeeCategory + ", " : "") +
            (employeeType != null ? "employeeType=" + employeeType + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (empId != null ? "empId=" + empId + ", " : "") +
            (employeeSalaryId != null ? "employeeSalaryId=" + employeeSalaryId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (designationId != null ? "designationId=" + designationId + ", " : "") +
            (lineId != null ? "lineId=" + lineId + ", " : "") +
            (gradeId != null ? "gradeId=" + gradeId + ", " : "") +
            "}";
    }

}
