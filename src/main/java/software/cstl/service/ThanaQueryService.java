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

import software.cstl.domain.Thana;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.ThanaRepository;
import software.cstl.service.dto.ThanaCriteria;

/**
 * Service for executing complex queries for {@link Thana} entities in the database.
 * The main input is a {@link ThanaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Thana} or a {@link Page} of {@link Thana} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ThanaQueryService extends QueryService<Thana> {

    private final Logger log = LoggerFactory.getLogger(ThanaQueryService.class);

    private final ThanaRepository thanaRepository;

    public ThanaQueryService(ThanaRepository thanaRepository) {
        this.thanaRepository = thanaRepository;
    }

    /**
     * Return a {@link List} of {@link Thana} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Thana> findByCriteria(ThanaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Thana> specification = createSpecification(criteria);
        return thanaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Thana} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Thana> findByCriteria(ThanaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Thana> specification = createSpecification(criteria);
        return thanaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ThanaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Thana> specification = createSpecification(criteria);
        return thanaRepository.count(specification);
    }

    /**
     * Function to convert {@link ThanaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Thana> createSpecification(ThanaCriteria criteria) {
        Specification<Thana> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Thana_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Thana_.name));
            }
            if (criteria.getBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBangla(), Thana_.bangla));
            }
            if (criteria.getWeb() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWeb(), Thana_.web));
            }
            if (criteria.getDistrictId() != null) {
                specification = specification.and(buildSpecification(criteria.getDistrictId(),
                    root -> root.join(Thana_.district, JoinType.LEFT).get(District_.id)));
            }
        }
        return specification;
    }
}
