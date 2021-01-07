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

import software.cstl.domain.FinePaymentHistory;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.FinePaymentHistoryRepository;
import software.cstl.service.dto.FinePaymentHistoryCriteria;

/**
 * Service for executing complex queries for {@link FinePaymentHistory} entities in the database.
 * The main input is a {@link FinePaymentHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FinePaymentHistory} or a {@link Page} of {@link FinePaymentHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FinePaymentHistoryQueryService extends QueryService<FinePaymentHistory> {

    private final Logger log = LoggerFactory.getLogger(FinePaymentHistoryQueryService.class);

    private final FinePaymentHistoryRepository finePaymentHistoryRepository;

    public FinePaymentHistoryQueryService(FinePaymentHistoryRepository finePaymentHistoryRepository) {
        this.finePaymentHistoryRepository = finePaymentHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link FinePaymentHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FinePaymentHistory> findByCriteria(FinePaymentHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FinePaymentHistory> specification = createSpecification(criteria);
        return finePaymentHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FinePaymentHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FinePaymentHistory> findByCriteria(FinePaymentHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FinePaymentHistory> specification = createSpecification(criteria);
        return finePaymentHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FinePaymentHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FinePaymentHistory> specification = createSpecification(criteria);
        return finePaymentHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link FinePaymentHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FinePaymentHistory> createSpecification(FinePaymentHistoryCriteria criteria) {
        Specification<FinePaymentHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FinePaymentHistory_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), FinePaymentHistory_.year));
            }
            if (criteria.getMonthType() != null) {
                specification = specification.and(buildSpecification(criteria.getMonthType(), FinePaymentHistory_.monthType));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), FinePaymentHistory_.amount));
            }
            if (criteria.getBeforeFine() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBeforeFine(), FinePaymentHistory_.beforeFine));
            }
            if (criteria.getAfterFine() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAfterFine(), FinePaymentHistory_.afterFine));
            }
            if (criteria.getFineId() != null) {
                specification = specification.and(buildSpecification(criteria.getFineId(),
                    root -> root.join(FinePaymentHistory_.fine, JoinType.LEFT).get(Fine_.id)));
            }
        }
        return specification;
    }
}
