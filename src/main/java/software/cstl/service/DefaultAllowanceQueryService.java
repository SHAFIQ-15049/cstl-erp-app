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

import software.cstl.domain.DefaultAllowance;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.DefaultAllowanceRepository;
import software.cstl.service.dto.DefaultAllowanceCriteria;

/**
 * Service for executing complex queries for {@link DefaultAllowance} entities in the database.
 * The main input is a {@link DefaultAllowanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DefaultAllowance} or a {@link Page} of {@link DefaultAllowance} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DefaultAllowanceQueryService extends QueryService<DefaultAllowance> {

    private final Logger log = LoggerFactory.getLogger(DefaultAllowanceQueryService.class);

    private final DefaultAllowanceRepository defaultAllowanceRepository;

    public DefaultAllowanceQueryService(DefaultAllowanceRepository defaultAllowanceRepository) {
        this.defaultAllowanceRepository = defaultAllowanceRepository;
    }

    /**
     * Return a {@link List} of {@link DefaultAllowance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DefaultAllowance> findByCriteria(DefaultAllowanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DefaultAllowance> specification = createSpecification(criteria);
        return defaultAllowanceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DefaultAllowance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DefaultAllowance> findByCriteria(DefaultAllowanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DefaultAllowance> specification = createSpecification(criteria);
        return defaultAllowanceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DefaultAllowanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DefaultAllowance> specification = createSpecification(criteria);
        return defaultAllowanceRepository.count(specification);
    }

    /**
     * Function to convert {@link DefaultAllowanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DefaultAllowance> createSpecification(DefaultAllowanceCriteria criteria) {
        Specification<DefaultAllowance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DefaultAllowance_.id));
            }
            if (criteria.getBasic() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasic(), DefaultAllowance_.basic));
            }
            if (criteria.getBasicPercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasicPercent(), DefaultAllowance_.basicPercent));
            }
            if (criteria.getTotalAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAllowance(), DefaultAllowance_.totalAllowance));
            }
            if (criteria.getMedicalAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicalAllowance(), DefaultAllowance_.medicalAllowance));
            }
            if (criteria.getMedicalAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicalAllowancePercent(), DefaultAllowance_.medicalAllowancePercent));
            }
            if (criteria.getConvinceAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConvinceAllowance(), DefaultAllowance_.convinceAllowance));
            }
            if (criteria.getConvinceAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConvinceAllowancePercent(), DefaultAllowance_.convinceAllowancePercent));
            }
            if (criteria.getFoodAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFoodAllowance(), DefaultAllowance_.foodAllowance));
            }
            if (criteria.getFoodAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFoodAllowancePercent(), DefaultAllowance_.foodAllowancePercent));
            }
            if (criteria.getFestivalAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFestivalAllowance(), DefaultAllowance_.festivalAllowance));
            }
            if (criteria.getFestivalAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFestivalAllowancePercent(), DefaultAllowance_.festivalAllowancePercent));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), DefaultAllowance_.status));
            }
        }
        return specification;
    }
}
