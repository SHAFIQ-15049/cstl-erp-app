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

import software.cstl.domain.FestivalAllowancePayment;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.FestivalAllowancePaymentRepository;
import software.cstl.service.dto.FestivalAllowancePaymentCriteria;

/**
 * Service for executing complex queries for {@link FestivalAllowancePayment} entities in the database.
 * The main input is a {@link FestivalAllowancePaymentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FestivalAllowancePayment} or a {@link Page} of {@link FestivalAllowancePayment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FestivalAllowancePaymentQueryService extends QueryService<FestivalAllowancePayment> {

    private final Logger log = LoggerFactory.getLogger(FestivalAllowancePaymentQueryService.class);

    private final FestivalAllowancePaymentRepository festivalAllowancePaymentRepository;

    public FestivalAllowancePaymentQueryService(FestivalAllowancePaymentRepository festivalAllowancePaymentRepository) {
        this.festivalAllowancePaymentRepository = festivalAllowancePaymentRepository;
    }

    /**
     * Return a {@link List} of {@link FestivalAllowancePayment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FestivalAllowancePayment> findByCriteria(FestivalAllowancePaymentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FestivalAllowancePayment> specification = createSpecification(criteria);
        return festivalAllowancePaymentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FestivalAllowancePayment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FestivalAllowancePayment> findByCriteria(FestivalAllowancePaymentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FestivalAllowancePayment> specification = createSpecification(criteria);
        return festivalAllowancePaymentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FestivalAllowancePaymentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FestivalAllowancePayment> specification = createSpecification(criteria);
        return festivalAllowancePaymentRepository.count(specification);
    }

    /**
     * Function to convert {@link FestivalAllowancePaymentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FestivalAllowancePayment> createSpecification(FestivalAllowancePaymentCriteria criteria) {
        Specification<FestivalAllowancePayment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FestivalAllowancePayment_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), FestivalAllowancePayment_.year));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), FestivalAllowancePayment_.month));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), FestivalAllowancePayment_.status));
            }
            if (criteria.getExecutedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutedOn(), FestivalAllowancePayment_.executedOn));
            }
            if (criteria.getExecutedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExecutedBy(), FestivalAllowancePayment_.executedBy));
            }
            if (criteria.getFestivalAllowancePaymentDtlId() != null) {
                specification = specification.and(buildSpecification(criteria.getFestivalAllowancePaymentDtlId(),
                    root -> root.join(FestivalAllowancePayment_.festivalAllowancePaymentDtls, JoinType.LEFT).get(FestivalAllowancePaymentDtl_.id)));
            }
            if (criteria.getDesignationId() != null) {
                specification = specification.and(buildSpecification(criteria.getDesignationId(),
                    root -> root.join(FestivalAllowancePayment_.designation, JoinType.LEFT).get(Designation_.id)));
            }
        }
        return specification;
    }
}
