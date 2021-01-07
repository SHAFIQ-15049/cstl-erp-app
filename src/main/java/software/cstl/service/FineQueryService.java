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

import software.cstl.domain.Fine;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.FineRepository;
import software.cstl.service.dto.FineCriteria;

/**
 * Service for executing complex queries for {@link Fine} entities in the database.
 * The main input is a {@link FineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Fine} or a {@link Page} of {@link Fine} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FineQueryService extends QueryService<Fine> {

    private final Logger log = LoggerFactory.getLogger(FineQueryService.class);

    private final FineRepository fineRepository;

    public FineQueryService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    /**
     * Return a {@link List} of {@link Fine} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Fine> findByCriteria(FineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fine> specification = createSpecification(criteria);
        return fineRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Fine} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Fine> findByCriteria(FineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fine> specification = createSpecification(criteria);
        return fineRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fine> specification = createSpecification(criteria);
        return fineRepository.count(specification);
    }

    /**
     * Function to convert {@link FineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fine> createSpecification(FineCriteria criteria) {
        Specification<Fine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Fine_.id));
            }
            if (criteria.getFinedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinedOn(), Fine_.finedOn));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Fine_.amount));
            }
            if (criteria.getFinePercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinePercentage(), Fine_.finePercentage));
            }
            if (criteria.getMonthlyFineAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthlyFineAmount(), Fine_.monthlyFineAmount));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentStatus(), Fine_.paymentStatus));
            }
            if (criteria.getAmountPaid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountPaid(), Fine_.amountPaid));
            }
            if (criteria.getAmountLeft() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountLeft(), Fine_.amountLeft));
            }
            if (criteria.getFinePaymentHistoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getFinePaymentHistoryId(),
                    root -> root.join(Fine_.finePaymentHistories, JoinType.LEFT).get(FinePaymentHistory_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Fine_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
