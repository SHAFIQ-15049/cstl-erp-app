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

import software.cstl.domain.District;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.DistrictRepository;
import software.cstl.service.dto.DistrictCriteria;

/**
 * Service for executing complex queries for {@link District} entities in the database.
 * The main input is a {@link DistrictCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link District} or a {@link Page} of {@link District} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DistrictQueryService extends QueryService<District> {

    private final Logger log = LoggerFactory.getLogger(DistrictQueryService.class);

    private final DistrictRepository districtRepository;

    public DistrictQueryService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    /**
     * Return a {@link List} of {@link District} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<District> findByCriteria(DistrictCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<District> specification = createSpecification(criteria);
        return districtRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link District} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<District> findByCriteria(DistrictCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<District> specification = createSpecification(criteria);
        return districtRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DistrictCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<District> specification = createSpecification(criteria);
        return districtRepository.count(specification);
    }

    /**
     * Function to convert {@link DistrictCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<District> createSpecification(DistrictCriteria criteria) {
        Specification<District> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), District_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), District_.name));
            }
            if (criteria.getBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBangla(), District_.bangla));
            }
            if (criteria.getWeb() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWeb(), District_.web));
            }
            if (criteria.getDivisionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDivisionId(),
                    root -> root.join(District_.division, JoinType.LEFT).get(Division_.id)));
            }
        }
        return specification;
    }
}
