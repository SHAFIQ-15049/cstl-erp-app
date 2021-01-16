package software.cstl.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import software.cstl.domain.Employee;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.EmployeeRepository;
import software.cstl.service.dto.EmployeeCriteria;

/**
 * Service for executing complex queries for {@link Employee} entities in the database.
 * The main input is a {@link EmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Employee} or a {@link Page} of {@link Employee} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService extends QueryService<Employee> {

    private final Logger log = LoggerFactory.getLogger(EmployeeQueryService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeQueryService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Return a {@link List} of {@link Employee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Employee> findByCriteria(EmployeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Employee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Employee> findByCriteria(EmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employee> createSpecification(EmployeeCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Employee_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Employee_.name));
            }
            if (criteria.getEmpId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpId(), Employee_.empId));
            }
            if (criteria.getGlobalId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGlobalId(), Employee_.globalId));
            }
            if (criteria.getAttendanceMachineId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttendanceMachineId(), Employee_.attendanceMachineId));
            }
            if (criteria.getLocalId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalId(), Employee_.localId));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getCategory(), Employee_.category));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Employee_.type));
            }
            if (criteria.getJoiningDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJoiningDate(), Employee_.joiningDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Employee_.status));
            }
            if (criteria.getTerminationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTerminationDate(), Employee_.terminationDate));
            }
            if (criteria.getPartialSalaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartialSalaryId(),
                    root -> root.join(Employee_.partialSalaries, JoinType.LEFT).get(PartialSalary_.id)));
            }
            if (criteria.getOverTimeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOverTimeId(),
                    root -> root.join(Employee_.overTimes, JoinType.LEFT).get(OverTime_.id)));
            }
            if (criteria.getFineId() != null) {
                specification = specification.and(buildSpecification(criteria.getFineId(),
                    root -> root.join(Employee_.fines, JoinType.LEFT).get(Fine_.id)));
            }
            if (criteria.getAdvanceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdvanceId(),
                    root -> root.join(Employee_.advances, JoinType.LEFT).get(Advance_.id)));
            }
            if (criteria.getEmployeeSalaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeSalaryId(),
                    root -> root.join(Employee_.employeeSalaries, JoinType.LEFT).get(EmployeeSalary_.id)));
            }
            if (criteria.getEducationalInfoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEducationalInfoId(),
                    root -> root.join(Employee_.educationalInfos, JoinType.LEFT).get(EducationalInfo_.id)));
            }
            if (criteria.getTrainingId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrainingId(),
                    root -> root.join(Employee_.trainings, JoinType.LEFT).get(Training_.id)));
            }
            if (criteria.getEmployeeAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeAccountId(),
                    root -> root.join(Employee_.employeeAccounts, JoinType.LEFT).get(EmployeeAccount_.id)));
            }
            if (criteria.getJobHistoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobHistoryId(),
                    root -> root.join(Employee_.jobHistories, JoinType.LEFT).get(JobHistory_.id)));
            }
            if (criteria.getServiceHistoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceHistoryId(),
                    root -> root.join(Employee_.serviceHistories, JoinType.LEFT).get(ServiceHistory_.id)));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(Employee_.company, JoinType.LEFT).get(Company_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(Employee_.department, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getGradeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGradeId(),
                    root -> root.join(Employee_.grade, JoinType.LEFT).get(Grade_.id)));
            }
            if (criteria.getDesignationId() != null) {
                specification = specification.and(buildSpecification(criteria.getDesignationId(),
                    root -> root.join(Employee_.designation, JoinType.LEFT).get(Designation_.id)));
            }
            if (criteria.getLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getLineId(),
                    root -> root.join(Employee_.line, JoinType.LEFT).get(Line_.id)));
            }
            if (criteria.getAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressId(),
                    root -> root.join(Employee_.address, JoinType.LEFT).get(Address_.id)));
            }
            if (criteria.getPersonalInfoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonalInfoId(),
                    root -> root.join(Employee_.personalInfo, JoinType.LEFT).get(PersonalInfo_.id)));
            }
        }
        return specification;
    }
}
