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

import software.cstl.domain.ServiceHistory;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.ServiceHistoryRepository;
import software.cstl.service.dto.ServiceHistoryCriteria;

/**
 * Service for executing complex queries for {@link ServiceHistory} entities in the database.
 * The main input is a {@link ServiceHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceHistory} or a {@link Page} of {@link ServiceHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceHistoryQueryService extends QueryService<ServiceHistory> {

    private final Logger log = LoggerFactory.getLogger(ServiceHistoryQueryService.class);

    private final ServiceHistoryRepository serviceHistoryRepository;

    public ServiceHistoryQueryService(ServiceHistoryRepository serviceHistoryRepository) {
        this.serviceHistoryRepository = serviceHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link ServiceHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceHistory> findByCriteria(ServiceHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceHistory> specification = createSpecification(criteria);
        return serviceHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ServiceHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceHistory> findByCriteria(ServiceHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceHistory> specification = createSpecification(criteria);
        return serviceHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceHistory> specification = createSpecification(criteria);
        return serviceHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceHistory> createSpecification(ServiceHistoryCriteria criteria) {
        Specification<ServiceHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServiceHistory_.id));
            }
            if (criteria.getEmployeeType() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeType(), ServiceHistory_.employeeType));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getCategory(), ServiceHistory_.category));
            }
            if (criteria.getFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFrom(), ServiceHistory_.from));
            }
            if (criteria.getTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTo(), ServiceHistory_.to));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ServiceHistory_.status));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(ServiceHistory_.department, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getDesignationId() != null) {
                specification = specification.and(buildSpecification(criteria.getDesignationId(),
                    root -> root.join(ServiceHistory_.designation, JoinType.LEFT).get(Designation_.id)));
            }
            if (criteria.getGradeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGradeId(),
                    root -> root.join(ServiceHistory_.grade, JoinType.LEFT).get(Grade_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(ServiceHistory_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
