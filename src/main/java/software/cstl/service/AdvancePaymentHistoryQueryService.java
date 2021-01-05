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

import software.cstl.domain.AdvancePaymentHistory;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.AdvancePaymentHistoryRepository;
import software.cstl.service.dto.AdvancePaymentHistoryCriteria;

/**
 * Service for executing complex queries for {@link AdvancePaymentHistory} entities in the database.
 * The main input is a {@link AdvancePaymentHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdvancePaymentHistory} or a {@link Page} of {@link AdvancePaymentHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdvancePaymentHistoryQueryService extends QueryService<AdvancePaymentHistory> {

    private final Logger log = LoggerFactory.getLogger(AdvancePaymentHistoryQueryService.class);

    private final AdvancePaymentHistoryRepository advancePaymentHistoryRepository;

    public AdvancePaymentHistoryQueryService(AdvancePaymentHistoryRepository advancePaymentHistoryRepository) {
        this.advancePaymentHistoryRepository = advancePaymentHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link AdvancePaymentHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdvancePaymentHistory> findByCriteria(AdvancePaymentHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdvancePaymentHistory> specification = createSpecification(criteria);
        return advancePaymentHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AdvancePaymentHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdvancePaymentHistory> findByCriteria(AdvancePaymentHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdvancePaymentHistory> specification = createSpecification(criteria);
        return advancePaymentHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdvancePaymentHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdvancePaymentHistory> specification = createSpecification(criteria);
        return advancePaymentHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link AdvancePaymentHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdvancePaymentHistory> createSpecification(AdvancePaymentHistoryCriteria criteria) {
        Specification<AdvancePaymentHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdvancePaymentHistory_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), AdvancePaymentHistory_.year));
            }
            if (criteria.getMonthType() != null) {
                specification = specification.and(buildSpecification(criteria.getMonthType(), AdvancePaymentHistory_.monthType));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), AdvancePaymentHistory_.amount));
            }
            if (criteria.getBefore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBefore(), AdvancePaymentHistory_.before));
            }
            if (criteria.getAfter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAfter(), AdvancePaymentHistory_.after));
            }
            if (criteria.getAdvanceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdvanceId(),
                    root -> root.join(AdvancePaymentHistory_.advance, JoinType.LEFT).get(Advance_.id)));
            }
        }
        return specification;
    }
}
