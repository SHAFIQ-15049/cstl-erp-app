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

import software.cstl.domain.EducationalInfo;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.EducationalInfoRepository;
import software.cstl.service.dto.EducationalInfoCriteria;

/**
 * Service for executing complex queries for {@link EducationalInfo} entities in the database.
 * The main input is a {@link EducationalInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EducationalInfo} or a {@link Page} of {@link EducationalInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EducationalInfoQueryService extends QueryService<EducationalInfo> {

    private final Logger log = LoggerFactory.getLogger(EducationalInfoQueryService.class);

    private final EducationalInfoRepository educationalInfoRepository;

    public EducationalInfoQueryService(EducationalInfoRepository educationalInfoRepository) {
        this.educationalInfoRepository = educationalInfoRepository;
    }

    /**
     * Return a {@link List} of {@link EducationalInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EducationalInfo> findByCriteria(EducationalInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EducationalInfo> specification = createSpecification(criteria);
        return educationalInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EducationalInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EducationalInfo> findByCriteria(EducationalInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EducationalInfo> specification = createSpecification(criteria);
        return educationalInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EducationalInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EducationalInfo> specification = createSpecification(criteria);
        return educationalInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link EducationalInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EducationalInfo> createSpecification(EducationalInfoCriteria criteria) {
        Specification<EducationalInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EducationalInfo_.id));
            }
            if (criteria.getSerial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSerial(), EducationalInfo_.serial));
            }
            if (criteria.getDegree() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDegree(), EducationalInfo_.degree));
            }
            if (criteria.getInstitution() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstitution(), EducationalInfo_.institution));
            }
            if (criteria.getPassingYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassingYear(), EducationalInfo_.passingYear));
            }
            if (criteria.getCourseDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCourseDuration(), EducationalInfo_.courseDuration));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(EducationalInfo_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
