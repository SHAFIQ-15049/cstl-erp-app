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

import software.cstl.domain.MonthlySalary;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.MonthlySalaryRepository;
import software.cstl.service.dto.MonthlySalaryCriteria;

/**
 * Service for executing complex queries for {@link MonthlySalary} entities in the database.
 * The main input is a {@link MonthlySalaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MonthlySalary} or a {@link Page} of {@link MonthlySalary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MonthlySalaryQueryService extends QueryService<MonthlySalary> {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryQueryService.class);

    private final MonthlySalaryRepository monthlySalaryRepository;

    public MonthlySalaryQueryService(MonthlySalaryRepository monthlySalaryRepository) {
        this.monthlySalaryRepository = monthlySalaryRepository;
    }

    /**
     * Return a {@link List} of {@link MonthlySalary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MonthlySalary> findByCriteria(MonthlySalaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MonthlySalary> specification = createSpecification(criteria);
        return monthlySalaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MonthlySalary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MonthlySalary> findByCriteria(MonthlySalaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MonthlySalary> specification = createSpecification(criteria);
        return monthlySalaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MonthlySalaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MonthlySalary> specification = createSpecification(criteria);
        return monthlySalaryRepository.count(specification);
    }

    /**
     * Function to convert {@link MonthlySalaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MonthlySalary> createSpecification(MonthlySalaryCriteria criteria) {
        Specification<MonthlySalary> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MonthlySalary_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), MonthlySalary_.year));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), MonthlySalary_.month));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), MonthlySalary_.fromDate));
            }
            if (criteria.getToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToDate(), MonthlySalary_.toDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), MonthlySalary_.status));
            }
            if (criteria.getExecutedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutedOn(), MonthlySalary_.executedOn));
            }
            if (criteria.getExecutedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExecutedBy(), MonthlySalary_.executedBy));
            }
            if (criteria.getMonthlySalaryDtlId() != null) {
                specification = specification.and(buildSpecification(criteria.getMonthlySalaryDtlId(),
                    root -> root.join(MonthlySalary_.monthlySalaryDtls, JoinType.LEFT).get(MonthlySalaryDtl_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(MonthlySalary_.department, JoinType.LEFT).get(Department_.id)));
            }
        }
        return specification;
    }
}
