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

import software.cstl.domain.Holiday;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.HolidayRepository;
import software.cstl.service.dto.HolidayCriteria;

/**
 * Service for executing complex queries for {@link Holiday} entities in the database.
 * The main input is a {@link HolidayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Holiday} or a {@link Page} of {@link Holiday} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HolidayQueryService extends QueryService<Holiday> {

    private final Logger log = LoggerFactory.getLogger(HolidayQueryService.class);

    private final HolidayRepository holidayRepository;

    public HolidayQueryService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    /**
     * Return a {@link List} of {@link Holiday} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Holiday> findByCriteria(HolidayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Holiday> specification = createSpecification(criteria);
        return holidayRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Holiday} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Holiday> findByCriteria(HolidayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Holiday> specification = createSpecification(criteria);
        return holidayRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HolidayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Holiday> specification = createSpecification(criteria);
        return holidayRepository.count(specification);
    }

    /**
     * Function to convert {@link HolidayCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Holiday> createSpecification(HolidayCriteria criteria) {
        Specification<Holiday> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Holiday_.id));
            }
            if (criteria.getFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFrom(), Holiday_.from));
            }
            if (criteria.getTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTo(), Holiday_.to));
            }
            if (criteria.getTotalDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalDays(), Holiday_.totalDays));
            }
            if (criteria.getHolidayTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getHolidayTypeId(),
                    root -> root.join(Holiday_.holidayType, JoinType.LEFT).get(HolidayType_.id)));
            }
        }
        return specification;
    }
}
