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

import software.cstl.domain.Designation;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.DesignationRepository;
import software.cstl.service.dto.DesignationCriteria;

/**
 * Service for executing complex queries for {@link Designation} entities in the database.
 * The main input is a {@link DesignationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Designation} or a {@link Page} of {@link Designation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DesignationQueryService extends QueryService<Designation> {

    private final Logger log = LoggerFactory.getLogger(DesignationQueryService.class);

    private final DesignationRepository designationRepository;

    public DesignationQueryService(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    /**
     * Return a {@link List} of {@link Designation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Designation> findByCriteria(DesignationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Designation> specification = createSpecification(criteria);
        return designationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Designation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Designation> findByCriteria(DesignationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Designation> specification = createSpecification(criteria);
        return designationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DesignationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Designation> specification = createSpecification(criteria);
        return designationRepository.count(specification);
    }

    /**
     * Function to convert {@link DesignationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Designation> createSpecification(DesignationCriteria criteria) {
        Specification<Designation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Designation_.id));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getCategory(), Designation_.category));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Designation_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), Designation_.shortName));
            }
            if (criteria.getNameInBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameInBangla(), Designation_.nameInBangla));
            }
        }
        return specification;
    }
}
