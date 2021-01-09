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

import software.cstl.domain.PersonalInfo;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.PersonalInfoRepository;
import software.cstl.service.dto.PersonalInfoCriteria;

/**
 * Service for executing complex queries for {@link PersonalInfo} entities in the database.
 * The main input is a {@link PersonalInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonalInfo} or a {@link Page} of {@link PersonalInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonalInfoQueryService extends QueryService<PersonalInfo> {

    private final Logger log = LoggerFactory.getLogger(PersonalInfoQueryService.class);

    private final PersonalInfoRepository personalInfoRepository;

    public PersonalInfoQueryService(PersonalInfoRepository personalInfoRepository) {
        this.personalInfoRepository = personalInfoRepository;
    }

    /**
     * Return a {@link List} of {@link PersonalInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonalInfo> findByCriteria(PersonalInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonalInfo> specification = createSpecification(criteria);
        return personalInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PersonalInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonalInfo> findByCriteria(PersonalInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonalInfo> specification = createSpecification(criteria);
        return personalInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonalInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonalInfo> specification = createSpecification(criteria);
        return personalInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link PersonalInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PersonalInfo> createSpecification(PersonalInfoCriteria criteria) {
        Specification<PersonalInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PersonalInfo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PersonalInfo_.name));
            }
            if (criteria.getBanglaName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBanglaName(), PersonalInfo_.banglaName));
            }
            if (criteria.getPhotoId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhotoId(), PersonalInfo_.photoId));
            }
            if (criteria.getFatherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherName(), PersonalInfo_.fatherName));
            }
            if (criteria.getFatherNameBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherNameBangla(), PersonalInfo_.fatherNameBangla));
            }
            if (criteria.getMotherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotherName(), PersonalInfo_.motherName));
            }
            if (criteria.getMotherNameBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotherNameBangla(), PersonalInfo_.motherNameBangla));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getMaritalStatus(), PersonalInfo_.maritalStatus));
            }
            if (criteria.getSpouseName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpouseName(), PersonalInfo_.spouseName));
            }
            if (criteria.getSpouseNameBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpouseNameBangla(), PersonalInfo_.spouseNameBangla));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), PersonalInfo_.dateOfBirth));
            }
            if (criteria.getNationalId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationalId(), PersonalInfo_.nationalId));
            }
            if (criteria.getNationalIdAttachmentId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationalIdAttachmentId(), PersonalInfo_.nationalIdAttachmentId));
            }
            if (criteria.getBirthRegistration() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBirthRegistration(), PersonalInfo_.birthRegistration));
            }
            if (criteria.getBirthRegistrationAttachmentId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBirthRegistrationAttachmentId(), PersonalInfo_.birthRegistrationAttachmentId));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), PersonalInfo_.height));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), PersonalInfo_.gender));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildSpecification(criteria.getReligion(), PersonalInfo_.religion));
            }
            if (criteria.getBloodGroup() != null) {
                specification = specification.and(buildSpecification(criteria.getBloodGroup(), PersonalInfo_.bloodGroup));
            }
            if (criteria.getEmergencyContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmergencyContact(), PersonalInfo_.emergencyContact));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(PersonalInfo_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
