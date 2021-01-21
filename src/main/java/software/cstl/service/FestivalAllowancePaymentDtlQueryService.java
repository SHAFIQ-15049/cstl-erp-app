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

import software.cstl.domain.FestivalAllowancePaymentDtl;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.FestivalAllowancePaymentDtlRepository;
import software.cstl.service.dto.FestivalAllowancePaymentDtlCriteria;

/**
 * Service for executing complex queries for {@link FestivalAllowancePaymentDtl} entities in the database.
 * The main input is a {@link FestivalAllowancePaymentDtlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FestivalAllowancePaymentDtl} or a {@link Page} of {@link FestivalAllowancePaymentDtl} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FestivalAllowancePaymentDtlQueryService extends QueryService<FestivalAllowancePaymentDtl> {

    private final Logger log = LoggerFactory.getLogger(FestivalAllowancePaymentDtlQueryService.class);

    private final FestivalAllowancePaymentDtlRepository festivalAllowancePaymentDtlRepository;

    public FestivalAllowancePaymentDtlQueryService(FestivalAllowancePaymentDtlRepository festivalAllowancePaymentDtlRepository) {
        this.festivalAllowancePaymentDtlRepository = festivalAllowancePaymentDtlRepository;
    }

    /**
     * Return a {@link List} of {@link FestivalAllowancePaymentDtl} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FestivalAllowancePaymentDtl> findByCriteria(FestivalAllowancePaymentDtlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FestivalAllowancePaymentDtl> specification = createSpecification(criteria);
        return festivalAllowancePaymentDtlRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FestivalAllowancePaymentDtl} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FestivalAllowancePaymentDtl> findByCriteria(FestivalAllowancePaymentDtlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FestivalAllowancePaymentDtl> specification = createSpecification(criteria);
        return festivalAllowancePaymentDtlRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FestivalAllowancePaymentDtlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FestivalAllowancePaymentDtl> specification = createSpecification(criteria);
        return festivalAllowancePaymentDtlRepository.count(specification);
    }

    /**
     * Function to convert {@link FestivalAllowancePaymentDtlCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FestivalAllowancePaymentDtl> createSpecification(FestivalAllowancePaymentDtlCriteria criteria) {
        Specification<FestivalAllowancePaymentDtl> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FestivalAllowancePaymentDtl_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), FestivalAllowancePaymentDtl_.amount));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), FestivalAllowancePaymentDtl_.status));
            }
            if (criteria.getExecutedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutedOn(), FestivalAllowancePaymentDtl_.executedOn));
            }
            if (criteria.getExecutedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExecutedBy(), FestivalAllowancePaymentDtl_.executedBy));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(FestivalAllowancePaymentDtl_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getFestivalAllowancePaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getFestivalAllowancePaymentId(),
                    root -> root.join(FestivalAllowancePaymentDtl_.festivalAllowancePayment, JoinType.LEFT).get(FestivalAllowancePayment_.id)));
            }
        }
        return specification;
    }
}
