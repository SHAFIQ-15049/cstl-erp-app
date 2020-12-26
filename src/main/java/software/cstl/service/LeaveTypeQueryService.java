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

import software.cstl.domain.LeaveType;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.LeaveTypeRepository;
import software.cstl.service.dto.LeaveTypeCriteria;

/**
 * Service for executing complex queries for {@link LeaveType} entities in the database.
 * The main input is a {@link LeaveTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveType} or a {@link Page} of {@link LeaveType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveTypeQueryService extends QueryService<LeaveType> {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeQueryService.class);

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeQueryService(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveType> findByCriteria(LeaveTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveType> specification = createSpecification(criteria);
        return leaveTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveType> findByCriteria(LeaveTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveType> specification = createSpecification(criteria);
        return leaveTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveType> specification = createSpecification(criteria);
        return leaveTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveType> createSpecification(LeaveTypeCriteria criteria) {
        Specification<LeaveType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LeaveType_.name));
            }
            if (criteria.getTotalDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalDays(), LeaveType_.totalDays));
            }
        }
        return specification;
    }
}
