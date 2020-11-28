package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.EmployeeType;
import software.cstl.domain.enumeration.EmployeeStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link software.cstl.domain.Employee} entity. This class is used
 * in {@link software.cstl.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable, Criteria {
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
    /**
     * Class for filtering EmployeeStatus
     */
    public static class EmployeeStatusFilter extends Filter<EmployeeStatus> {

        public EmployeeStatusFilter() {
        }

        public EmployeeStatusFilter(EmployeeStatusFilter filter) {
            super(filter);
        }

        @Override
        public EmployeeStatusFilter copy() {
            return new EmployeeStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter empId;

    private StringFilter globalId;

    private StringFilter localId;

    private EmployeeCategoryFilter category;

    private EmployeeTypeFilter type;

    private LocalDateFilter joiningDate;

    private EmployeeStatusFilter status;

    private LocalDateFilter terminationDate;

    private LongFilter personalInfoId;

    private LongFilter addressId;

    private LongFilter educationalInfoId;

    private LongFilter trainingId;

    private LongFilter employeeAccountId;

    private LongFilter jobHistoryId;

    private LongFilter serviceHistoryId;

    private LongFilter companyId;

    private LongFilter departmentId;

    private LongFilter gradeId;

    private LongFilter designationId;

    public EmployeeCriteria() {
    }

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.empId = other.empId == null ? null : other.empId.copy();
        this.globalId = other.globalId == null ? null : other.globalId.copy();
        this.localId = other.localId == null ? null : other.localId.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.joiningDate = other.joiningDate == null ? null : other.joiningDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.terminationDate = other.terminationDate == null ? null : other.terminationDate.copy();
        this.personalInfoId = other.personalInfoId == null ? null : other.personalInfoId.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.educationalInfoId = other.educationalInfoId == null ? null : other.educationalInfoId.copy();
        this.trainingId = other.trainingId == null ? null : other.trainingId.copy();
        this.employeeAccountId = other.employeeAccountId == null ? null : other.employeeAccountId.copy();
        this.jobHistoryId = other.jobHistoryId == null ? null : other.jobHistoryId.copy();
        this.serviceHistoryId = other.serviceHistoryId == null ? null : other.serviceHistoryId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.gradeId = other.gradeId == null ? null : other.gradeId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmpId() {
        return empId;
    }

    public void setEmpId(StringFilter empId) {
        this.empId = empId;
    }

    public StringFilter getGlobalId() {
        return globalId;
    }

    public void setGlobalId(StringFilter globalId) {
        this.globalId = globalId;
    }

    public StringFilter getLocalId() {
        return localId;
    }

    public void setLocalId(StringFilter localId) {
        this.localId = localId;
    }

    public EmployeeCategoryFilter getCategory() {
        return category;
    }

    public void setCategory(EmployeeCategoryFilter category) {
        this.category = category;
    }

    public EmployeeTypeFilter getType() {
        return type;
    }

    public void setType(EmployeeTypeFilter type) {
        this.type = type;
    }

    public LocalDateFilter getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDateFilter joiningDate) {
        this.joiningDate = joiningDate;
    }

    public EmployeeStatusFilter getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatusFilter status) {
        this.status = status;
    }

    public LocalDateFilter getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDateFilter terminationDate) {
        this.terminationDate = terminationDate;
    }

    public LongFilter getPersonalInfoId() {
        return personalInfoId;
    }

    public void setPersonalInfoId(LongFilter personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
    }

    public LongFilter getEducationalInfoId() {
        return educationalInfoId;
    }

    public void setEducationalInfoId(LongFilter educationalInfoId) {
        this.educationalInfoId = educationalInfoId;
    }

    public LongFilter getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(LongFilter trainingId) {
        this.trainingId = trainingId;
    }

    public LongFilter getEmployeeAccountId() {
        return employeeAccountId;
    }

    public void setEmployeeAccountId(LongFilter employeeAccountId) {
        this.employeeAccountId = employeeAccountId;
    }

    public LongFilter getJobHistoryId() {
        return jobHistoryId;
    }

    public void setJobHistoryId(LongFilter jobHistoryId) {
        this.jobHistoryId = jobHistoryId;
    }

    public LongFilter getServiceHistoryId() {
        return serviceHistoryId;
    }

    public void setServiceHistoryId(LongFilter serviceHistoryId) {
        this.serviceHistoryId = serviceHistoryId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getGradeId() {
        return gradeId;
    }

    public void setGradeId(LongFilter gradeId) {
        this.gradeId = gradeId;
    }

    public LongFilter getDesignationId() {
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(empId, that.empId) &&
            Objects.equals(globalId, that.globalId) &&
            Objects.equals(localId, that.localId) &&
            Objects.equals(category, that.category) &&
            Objects.equals(type, that.type) &&
            Objects.equals(joiningDate, that.joiningDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(terminationDate, that.terminationDate) &&
            Objects.equals(personalInfoId, that.personalInfoId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(educationalInfoId, that.educationalInfoId) &&
            Objects.equals(trainingId, that.trainingId) &&
            Objects.equals(employeeAccountId, that.employeeAccountId) &&
            Objects.equals(jobHistoryId, that.jobHistoryId) &&
            Objects.equals(serviceHistoryId, that.serviceHistoryId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(gradeId, that.gradeId) &&
            Objects.equals(designationId, that.designationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        empId,
        globalId,
        localId,
        category,
        type,
        joiningDate,
        status,
        terminationDate,
        personalInfoId,
        addressId,
        educationalInfoId,
        trainingId,
        employeeAccountId,
        jobHistoryId,
        serviceHistoryId,
        companyId,
        departmentId,
        gradeId,
        designationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (empId != null ? "empId=" + empId + ", " : "") +
                (globalId != null ? "globalId=" + globalId + ", " : "") +
                (localId != null ? "localId=" + localId + ", " : "") +
                (category != null ? "category=" + category + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (joiningDate != null ? "joiningDate=" + joiningDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (terminationDate != null ? "terminationDate=" + terminationDate + ", " : "") +
                (personalInfoId != null ? "personalInfoId=" + personalInfoId + ", " : "") +
                (addressId != null ? "addressId=" + addressId + ", " : "") +
                (educationalInfoId != null ? "educationalInfoId=" + educationalInfoId + ", " : "") +
                (trainingId != null ? "trainingId=" + trainingId + ", " : "") +
                (employeeAccountId != null ? "employeeAccountId=" + employeeAccountId + ", " : "") +
                (jobHistoryId != null ? "jobHistoryId=" + jobHistoryId + ", " : "") +
                (serviceHistoryId != null ? "serviceHistoryId=" + serviceHistoryId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (gradeId != null ? "gradeId=" + gradeId + ", " : "") +
                (designationId != null ? "designationId=" + designationId + ", " : "") +
            "}";
    }

}
