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

import software.cstl.domain.FestivalAllowanceTimeLine;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.FestivalAllowanceTimeLineRepository;
import software.cstl.service.dto.FestivalAllowanceTimeLineCriteria;

/**
 * Service for executing complex queries for {@link FestivalAllowanceTimeLine} entities in the database.
 * The main input is a {@link FestivalAllowanceTimeLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FestivalAllowanceTimeLine} or a {@link Page} of {@link FestivalAllowanceTimeLine} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FestivalAllowanceTimeLineQueryService extends QueryService<FestivalAllowanceTimeLine> {

    private final Logger log = LoggerFactory.getLogger(FestivalAllowanceTimeLineQueryService.class);

    private final FestivalAllowanceTimeLineRepository festivalAllowanceTimeLineRepository;

    public FestivalAllowanceTimeLineQueryService(FestivalAllowanceTimeLineRepository festivalAllowanceTimeLineRepository) {
        this.festivalAllowanceTimeLineRepository = festivalAllowanceTimeLineRepository;
    }

    /**
     * Return a {@link List} of {@link FestivalAllowanceTimeLine} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FestivalAllowanceTimeLine> findByCriteria(FestivalAllowanceTimeLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FestivalAllowanceTimeLine> specification = createSpecification(criteria);
        return festivalAllowanceTimeLineRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FestivalAllowanceTimeLine} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FestivalAllowanceTimeLine> findByCriteria(FestivalAllowanceTimeLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FestivalAllowanceTimeLine> specification = createSpecification(criteria);
        return festivalAllowanceTimeLineRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FestivalAllowanceTimeLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FestivalAllowanceTimeLine> specification = createSpecification(criteria);
        return festivalAllowanceTimeLineRepository.count(specification);
    }

    /**
     * Function to convert {@link FestivalAllowanceTimeLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FestivalAllowanceTimeLine> createSpecification(FestivalAllowanceTimeLineCriteria criteria) {
        Specification<FestivalAllowanceTimeLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FestivalAllowanceTimeLine_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), FestivalAllowanceTimeLine_.year));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), FestivalAllowanceTimeLine_.month));
            }
        }
        return specification;
    }
}
