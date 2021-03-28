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

import software.cstl.domain.IdCardManagement;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.IdCardManagementRepository;
import software.cstl.service.dto.IdCardManagementCriteria;

/**
 * Service for executing complex queries for {@link IdCardManagement} entities in the database.
 * The main input is a {@link IdCardManagementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IdCardManagement} or a {@link Page} of {@link IdCardManagement} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IdCardManagementQueryService extends QueryService<IdCardManagement> {

    private final Logger log = LoggerFactory.getLogger(IdCardManagementQueryService.class);

    private final IdCardManagementRepository idCardManagementRepository;

    public IdCardManagementQueryService(IdCardManagementRepository idCardManagementRepository) {
        this.idCardManagementRepository = idCardManagementRepository;
    }

    /**
     * Return a {@link List} of {@link IdCardManagement} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IdCardManagement> findByCriteria(IdCardManagementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IdCardManagement> specification = createSpecification(criteria);
        return idCardManagementRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link IdCardManagement} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IdCardManagement> findByCriteria(IdCardManagementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IdCardManagement> specification = createSpecification(criteria);
        return idCardManagementRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IdCardManagementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IdCardManagement> specification = createSpecification(criteria);
        return idCardManagementRepository.count(specification);
    }

    /**
     * Function to convert {@link IdCardManagementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IdCardManagement> createSpecification(IdCardManagementCriteria criteria) {
        Specification<IdCardManagement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IdCardManagement_.id));
            }
            if (criteria.getCardNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCardNo(), IdCardManagement_.cardNo));
            }
            if (criteria.getIssueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssueDate(), IdCardManagement_.issueDate));
            }
            if (criteria.getTicketNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTicketNo(), IdCardManagement_.ticketNo));
            }
            if (criteria.getValidTill() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTill(), IdCardManagement_.validTill));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(IdCardManagement_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
