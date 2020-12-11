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

import software.cstl.domain.Address;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.AddressRepository;
import software.cstl.service.dto.AddressCriteria;

/**
 * Service for executing complex queries for {@link Address} entities in the database.
 * The main input is a {@link AddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Address} or a {@link Page} of {@link Address} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressQueryService extends QueryService<Address> {

    private final Logger log = LoggerFactory.getLogger(AddressQueryService.class);

    private final AddressRepository addressRepository;

    public AddressQueryService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Return a {@link List} of {@link Address} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Address> findByCriteria(AddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Address> specification = createSpecification(criteria);
        return addressRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Address} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Address> findByCriteria(AddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Address> specification = createSpecification(criteria);
        return addressRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AddressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Address> specification = createSpecification(criteria);
        return addressRepository.count(specification);
    }

    /**
     * Function to convert {@link AddressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Address> createSpecification(AddressCriteria criteria) {
        Specification<Address> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Address_.id));
            }
            if (criteria.getPresentThanaTxt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPresentThanaTxt(), Address_.presentThanaTxt));
            }
            if (criteria.getPresentStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPresentStreet(), Address_.presentStreet));
            }
            if (criteria.getPresentStreetBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPresentStreetBangla(), Address_.presentStreetBangla));
            }
            if (criteria.getPresentArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPresentArea(), Address_.presentArea));
            }
            if (criteria.getPresentAreaBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPresentAreaBangla(), Address_.presentAreaBangla));
            }
            if (criteria.getPresentPostCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPresentPostCode(), Address_.presentPostCode));
            }
            if (criteria.getPresentPostCodeBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPresentPostCodeBangla(), Address_.presentPostCodeBangla));
            }
            if (criteria.getPermanentThanaTxt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermanentThanaTxt(), Address_.permanentThanaTxt));
            }
            if (criteria.getPermanentStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermanentStreet(), Address_.permanentStreet));
            }
            if (criteria.getPermanentStreetBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermanentStreetBangla(), Address_.permanentStreetBangla));
            }
            if (criteria.getPermanentArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermanentArea(), Address_.permanentArea));
            }
            if (criteria.getPermanentAreaBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermanentAreaBangla(), Address_.permanentAreaBangla));
            }
            if (criteria.getPermanentPostCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPermanentPostCode(), Address_.permanentPostCode));
            }
            if (criteria.getPermenentPostCodeBangla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermenentPostCodeBangla(), Address_.permenentPostCodeBangla));
            }
            if (criteria.getIsSame() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSame(), Address_.isSame));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Address_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getPresentDivisionId() != null) {
                specification = specification.and(buildSpecification(criteria.getPresentDivisionId(),
                    root -> root.join(Address_.presentDivision, JoinType.LEFT).get(Division_.id)));
            }
            if (criteria.getPresentDistrictId() != null) {
                specification = specification.and(buildSpecification(criteria.getPresentDistrictId(),
                    root -> root.join(Address_.presentDistrict, JoinType.LEFT).get(District_.id)));
            }
            if (criteria.getPresentThanaId() != null) {
                specification = specification.and(buildSpecification(criteria.getPresentThanaId(),
                    root -> root.join(Address_.presentThana, JoinType.LEFT).get(Thana_.id)));
            }
            if (criteria.getPermanentDivisionId() != null) {
                specification = specification.and(buildSpecification(criteria.getPermanentDivisionId(),
                    root -> root.join(Address_.permanentDivision, JoinType.LEFT).get(Division_.id)));
            }
            if (criteria.getPermanentDistrictId() != null) {
                specification = specification.and(buildSpecification(criteria.getPermanentDistrictId(),
                    root -> root.join(Address_.permanentDistrict, JoinType.LEFT).get(District_.id)));
            }
            if (criteria.getPermanentThanaId() != null) {
                specification = specification.and(buildSpecification(criteria.getPermanentThanaId(),
                    root -> root.join(Address_.permanentThana, JoinType.LEFT).get(Thana_.id)));
            }
        }
        return specification;
    }
}
