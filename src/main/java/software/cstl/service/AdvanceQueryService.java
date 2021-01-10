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

import software.cstl.domain.Advance;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.AdvanceRepository;
import software.cstl.service.dto.AdvanceCriteria;

/**
 * Service for executing complex queries for {@link Advance} entities in the database.
 * The main input is a {@link AdvanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Advance} or a {@link Page} of {@link Advance} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdvanceQueryService extends QueryService<Advance> {

    private final Logger log = LoggerFactory.getLogger(AdvanceQueryService.class);

    private final AdvanceRepository advanceRepository;

    public AdvanceQueryService(AdvanceRepository advanceRepository) {
        this.advanceRepository = advanceRepository;
    }

    /**
     * Return a {@link List} of {@link Advance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Advance> findByCriteria(AdvanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Advance> specification = createSpecification(criteria);
        return advanceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Advance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Advance> findByCriteria(AdvanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Advance> specification = createSpecification(criteria);
        return advanceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdvanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Advance> specification = createSpecification(criteria);
        return advanceRepository.count(specification);
    }

    /**
     * Function to convert {@link AdvanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Advance> createSpecification(AdvanceCriteria criteria) {
        Specification<Advance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Advance_.id));
            }
            if (criteria.getProvidedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProvidedOn(), Advance_.providedOn));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Advance_.amount));
            }
            if (criteria.getPaymentPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentPercentage(), Advance_.paymentPercentage));
            }
            if (criteria.getMonthlyPaymentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthlyPaymentAmount(), Advance_.monthlyPaymentAmount));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentStatus(), Advance_.paymentStatus));
            }
            if (criteria.getAmountPaid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountPaid(), Advance_.amountPaid));
            }
            if (criteria.getAmountLeft() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountLeft(), Advance_.amountLeft));
            }
            if (criteria.getAdvancePaymentHistoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdvancePaymentHistoryId(),
                    root -> root.join(Advance_.advancePaymentHistories, JoinType.LEFT).get(AdvancePaymentHistory_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Advance_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
