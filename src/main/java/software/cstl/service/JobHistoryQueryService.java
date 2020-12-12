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

import software.cstl.domain.JobHistory;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.JobHistoryRepository;
import software.cstl.service.dto.JobHistoryCriteria;

/**
 * Service for executing complex queries for {@link JobHistory} entities in the database.
 * The main input is a {@link JobHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobHistory} or a {@link Page} of {@link JobHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobHistoryQueryService extends QueryService<JobHistory> {

    private final Logger log = LoggerFactory.getLogger(JobHistoryQueryService.class);

    private final JobHistoryRepository jobHistoryRepository;

    public JobHistoryQueryService(JobHistoryRepository jobHistoryRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link JobHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobHistory> findByCriteria(JobHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobHistory> specification = createSpecification(criteria);
        return jobHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link JobHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobHistory> findByCriteria(JobHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JobHistory> specification = createSpecification(criteria);
        return jobHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JobHistory> specification = createSpecification(criteria);
        return jobHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link JobHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<JobHistory> createSpecification(JobHistoryCriteria criteria) {
        Specification<JobHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), JobHistory_.id));
            }
            if (criteria.getSerial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSerial(), JobHistory_.serial));
            }
            if (criteria.getOrganization() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganization(), JobHistory_.organization));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), JobHistory_.designation));
            }
            if (criteria.getFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFrom(), JobHistory_.from));
            }
            if (criteria.getTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTo(), JobHistory_.to));
            }
            if (criteria.getPayScale() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPayScale(), JobHistory_.payScale));
            }
            if (criteria.getTotalExperience() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalExperience(), JobHistory_.totalExperience));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(JobHistory_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
