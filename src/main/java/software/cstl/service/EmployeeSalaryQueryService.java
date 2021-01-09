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

import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.EmployeeSalaryRepository;
import software.cstl.service.dto.EmployeeSalaryCriteria;

/**
 * Service for executing complex queries for {@link EmployeeSalary} entities in the database.
 * The main input is a {@link EmployeeSalaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeSalary} or a {@link Page} of {@link EmployeeSalary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeSalaryQueryService extends QueryService<EmployeeSalary> {

    private final Logger log = LoggerFactory.getLogger(EmployeeSalaryQueryService.class);

    private final EmployeeSalaryRepository employeeSalaryRepository;

    public EmployeeSalaryQueryService(EmployeeSalaryRepository employeeSalaryRepository) {
        this.employeeSalaryRepository = employeeSalaryRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeSalary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeSalary> findByCriteria(EmployeeSalaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeSalary> specification = createSpecification(criteria);
        return employeeSalaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeSalary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSalary> findByCriteria(EmployeeSalaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeSalary> specification = createSpecification(criteria);
        return employeeSalaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeSalaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeSalary> specification = createSpecification(criteria);
        return employeeSalaryRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeSalaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeSalary> createSpecification(EmployeeSalaryCriteria criteria) {
        Specification<EmployeeSalary> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeSalary_.id));
            }
            if (criteria.getGross() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGross(), EmployeeSalary_.gross));
            }
            if (criteria.getIncrementAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIncrementAmount(), EmployeeSalary_.incrementAmount));
            }
            if (criteria.getIncrementPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIncrementPercentage(), EmployeeSalary_.incrementPercentage));
            }
            if (criteria.getSalaryStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalaryStartDate(), EmployeeSalary_.salaryStartDate));
            }
            if (criteria.getSalaryEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalaryEndDate(), EmployeeSalary_.salaryEndDate));
            }
            if (criteria.getNextIncrementDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextIncrementDate(), EmployeeSalary_.nextIncrementDate));
            }
            if (criteria.getBasic() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasic(), EmployeeSalary_.basic));
            }
            if (criteria.getBasicPercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasicPercent(), EmployeeSalary_.basicPercent));
            }
            if (criteria.getHouseRent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHouseRent(), EmployeeSalary_.houseRent));
            }
            if (criteria.getHouseRentPercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHouseRentPercent(), EmployeeSalary_.houseRentPercent));
            }
            if (criteria.getTotalAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAllowance(), EmployeeSalary_.totalAllowance));
            }
            if (criteria.getMedicalAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicalAllowance(), EmployeeSalary_.medicalAllowance));
            }
            if (criteria.getMedicalAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicalAllowancePercent(), EmployeeSalary_.medicalAllowancePercent));
            }
            if (criteria.getConvinceAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConvinceAllowance(), EmployeeSalary_.convinceAllowance));
            }
            if (criteria.getConvinceAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConvinceAllowancePercent(), EmployeeSalary_.convinceAllowancePercent));
            }
            if (criteria.getFoodAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFoodAllowance(), EmployeeSalary_.foodAllowance));
            }
            if (criteria.getFoodAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFoodAllowancePercent(), EmployeeSalary_.foodAllowancePercent));
            }
            if (criteria.getSpecialAllowanceActiveStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecialAllowanceActiveStatus(), EmployeeSalary_.specialAllowanceActiveStatus));
            }
            if (criteria.getSpecialAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSpecialAllowance(), EmployeeSalary_.specialAllowance));
            }
            if (criteria.getSpecialAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSpecialAllowancePercent(), EmployeeSalary_.specialAllowancePercent));
            }
            if (criteria.getInsuranceActiveStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getInsuranceActiveStatus(), EmployeeSalary_.insuranceActiveStatus));
            }
            if (criteria.getInsuranceAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInsuranceAmount(), EmployeeSalary_.insuranceAmount));
            }
            if (criteria.getInsurancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInsurancePercent(), EmployeeSalary_.insurancePercent));
            }
            if (criteria.getInsuranceProcessType() != null) {
                specification = specification.and(buildSpecification(criteria.getInsuranceProcessType(), EmployeeSalary_.insuranceProcessType));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), EmployeeSalary_.status));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(EmployeeSalary_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
