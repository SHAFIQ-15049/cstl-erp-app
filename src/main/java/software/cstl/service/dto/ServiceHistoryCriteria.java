package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.EmployeeType;
import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.ServiceStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link software.cstl.domain.ServiceHistory} entity. This class is used
 * in {@link software.cstl.web.rest.ServiceHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceHistoryCriteria implements Serializable, Criteria {
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
     * Class for filtering ServiceStatus
     */
    public static class ServiceStatusFilter extends Filter<ServiceStatus> {

        public ServiceStatusFilter() {
        }

        public ServiceStatusFilter(ServiceStatusFilter filter) {
            super(filter);
        }

        @Override
        public ServiceStatusFilter copy() {
            return new ServiceStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private EmployeeTypeFilter employeeType;

    private EmployeeCategoryFilter category;

    private LocalDateFilter from;

    private LocalDateFilter to;

    private ServiceStatusFilter status;

    private LongFilter departmentId;

    private LongFilter designationId;

    private LongFilter gradeId;

    private LongFilter employeeId;

    public ServiceHistoryCriteria() {
    }

    public ServiceHistoryCriteria(ServiceHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.employeeType = other.employeeType == null ? null : other.employeeType.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.from = other.from == null ? null : other.from.copy();
        this.to = other.to == null ? null : other.to.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
        this.gradeId = other.gradeId == null ? null : other.gradeId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public ServiceHistoryCriteria copy() {
        return new ServiceHistoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public EmployeeTypeFilter getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeTypeFilter employeeType) {
        this.employeeType = employeeType;
    }

    public EmployeeCategoryFilter getCategory() {
        return category;
    }

    public void setCategory(EmployeeCategoryFilter category) {
        this.category = category;
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

    public ServiceStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ServiceStatusFilter status) {
        this.status = status;
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

    public LongFilter getGradeId() {
        return gradeId;
    }

    public void setGradeId(LongFilter gradeId) {
        this.gradeId = gradeId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceHistoryCriteria that = (ServiceHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(employeeType, that.employeeType) &&
            Objects.equals(category, that.category) &&
            Objects.equals(from, that.from) &&
            Objects.equals(to, that.to) &&
            Objects.equals(status, that.status) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(designationId, that.designationId) &&
            Objects.equals(gradeId, that.gradeId) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        employeeType,
        category,
        from,
        to,
        status,
        departmentId,
        designationId,
        gradeId,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (employeeType != null ? "employeeType=" + employeeType + ", " : "") +
                (category != null ? "category=" + category + ", " : "") +
                (from != null ? "from=" + from + ", " : "") +
                (to != null ? "to=" + to + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (designationId != null ? "designationId=" + designationId + ", " : "") +
                (gradeId != null ? "gradeId=" + gradeId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
