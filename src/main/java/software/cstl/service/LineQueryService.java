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

import software.cstl.domain.Line;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.LineRepository;
import software.cstl.service.dto.LineCriteria;

/**
 * Service for executing complex queries for {@link Line} entities in the database.
 * The main input is a {@link LineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Line} or a {@link Page} of {@link Line} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LineQueryService extends QueryService<Line> {

    private final Logger log = LoggerFactory.getLogger(LineQueryService.class);

    private final LineRepository lineRepository;

    public LineQueryService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    /**
     * Return a {@link List} of {@link Line} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Line> findByCriteria(LineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Line> specification = createSpecification(criteria);
        return lineRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Line} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Line> findByCriteria(LineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Line> specification = createSpecification(criteria);
        return lineRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Line> specification = createSpecification(criteria);
        return lineRepository.count(specification);
    }

    /**
     * Function to convert {@link LineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Line> createSpecification(LineCriteria criteria) {
        Specification<Line> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Line_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Line_.name));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(Line_.department, JoinType.LEFT).get(Department_.id)));
            }
        }
        return specification;
    }
}
