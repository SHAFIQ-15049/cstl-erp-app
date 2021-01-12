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

import software.cstl.domain.PartialSalary;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.PartialSalaryRepository;
import software.cstl.service.dto.PartialSalaryCriteria;

/**
 * Service for executing complex queries for {@link PartialSalary} entities in the database.
 * The main input is a {@link PartialSalaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PartialSalary} or a {@link Page} of {@link PartialSalary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartialSalaryQueryService extends QueryService<PartialSalary> {

    private final Logger log = LoggerFactory.getLogger(PartialSalaryQueryService.class);

    private final PartialSalaryRepository partialSalaryRepository;

    public PartialSalaryQueryService(PartialSalaryRepository partialSalaryRepository) {
        this.partialSalaryRepository = partialSalaryRepository;
    }

    /**
     * Return a {@link List} of {@link PartialSalary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PartialSalary> findByCriteria(PartialSalaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PartialSalary> specification = createSpecification(criteria);
        return partialSalaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PartialSalary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartialSalary> findByCriteria(PartialSalaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartialSalary> specification = createSpecification(criteria);
        return partialSalaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartialSalaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PartialSalary> specification = createSpecification(criteria);
        return partialSalaryRepository.count(specification);
    }

    /**
     * Function to convert {@link PartialSalaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PartialSalary> createSpecification(PartialSalaryCriteria criteria) {
        Specification<PartialSalary> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PartialSalary_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), PartialSalary_.year));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), PartialSalary_.month));
            }
            if (criteria.getTotalMonthDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMonthDays(), PartialSalary_.totalMonthDays));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), PartialSalary_.fromDate));
            }
            if (criteria.getToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToDate(), PartialSalary_.toDate));
            }
            if (criteria.getGross() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGross(), PartialSalary_.gross));
            }
            if (criteria.getBasic() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasic(), PartialSalary_.basic));
            }
            if (criteria.getBasicPercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasicPercent(), PartialSalary_.basicPercent));
            }
            if (criteria.getHouseRent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHouseRent(), PartialSalary_.houseRent));
            }
            if (criteria.getHouseRentPercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHouseRentPercent(), PartialSalary_.houseRentPercent));
            }
            if (criteria.getMedicalAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicalAllowance(), PartialSalary_.medicalAllowance));
            }
            if (criteria.getMedicalAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicalAllowancePercent(), PartialSalary_.medicalAllowancePercent));
            }
            if (criteria.getConvinceAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConvinceAllowance(), PartialSalary_.convinceAllowance));
            }
            if (criteria.getConvinceAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConvinceAllowancePercent(), PartialSalary_.convinceAllowancePercent));
            }
            if (criteria.getFoodAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFoodAllowance(), PartialSalary_.foodAllowance));
            }
            if (criteria.getFoodAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFoodAllowancePercent(), PartialSalary_.foodAllowancePercent));
            }
            if (criteria.getFine() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFine(), PartialSalary_.fine));
            }
            if (criteria.getAdvance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdvance(), PartialSalary_.advance));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), PartialSalary_.status));
            }
            if (criteria.getExecutedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutedOn(), PartialSalary_.executedOn));
            }
            if (criteria.getExecutedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExecutedBy(), PartialSalary_.executedBy));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(PartialSalary_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
