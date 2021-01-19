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

import software.cstl.domain.OverTime;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.OverTimeRepository;
import software.cstl.service.dto.OverTimeCriteria;

/**
 * Service for executing complex queries for {@link OverTime} entities in the database.
 * The main input is a {@link OverTimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OverTime} or a {@link Page} of {@link OverTime} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OverTimeQueryService extends QueryService<OverTime> {

    private final Logger log = LoggerFactory.getLogger(OverTimeQueryService.class);

    private final OverTimeRepository overTimeRepository;

    public OverTimeQueryService(OverTimeRepository overTimeRepository) {
        this.overTimeRepository = overTimeRepository;
    }

    /**
     * Return a {@link List} of {@link OverTime} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OverTime> findByCriteria(OverTimeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OverTime> specification = createSpecification(criteria);
        return overTimeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OverTime} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OverTime> findByCriteria(OverTimeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OverTime> specification = createSpecification(criteria);
        return overTimeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OverTimeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OverTime> specification = createSpecification(criteria);
        return overTimeRepository.count(specification);
    }

    /**
     * Function to convert {@link OverTimeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OverTime> createSpecification(OverTimeCriteria criteria) {
        Specification<OverTime> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OverTime_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), OverTime_.year));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), OverTime_.month));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), OverTime_.fromDate));
            }
            if (criteria.getToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToDate(), OverTime_.toDate));
            }
            if (criteria.getTotalOverTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalOverTime(), OverTime_.totalOverTime));
            }
            if (criteria.getOfficialOverTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOfficialOverTime(), OverTime_.officialOverTime));
            }
            if (criteria.getExtraOverTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExtraOverTime(), OverTime_.extraOverTime));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), OverTime_.totalAmount));
            }
            if (criteria.getOfficialAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOfficialAmount(), OverTime_.officialAmount));
            }
            if (criteria.getExtraAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExtraAmount(), OverTime_.extraAmount));
            }
            if (criteria.getExecutedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutedOn(), OverTime_.executedOn));
            }
            if (criteria.getExecutedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExecutedBy(), OverTime_.executedBy));
            }
            if (criteria.getDesignationId() != null) {
                specification = specification.and(buildSpecification(criteria.getDesignationId(),
                    root -> root.join(OverTime_.designation, JoinType.LEFT).get(Designation_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(OverTime_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
