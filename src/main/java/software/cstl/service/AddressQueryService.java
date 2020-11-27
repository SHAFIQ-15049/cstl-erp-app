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
            if (criteria.getStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreet(), Address_.street));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArea(), Address_.area));
            }
            if (criteria.getPostCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostCode(), Address_.postCode));
            }
            if (criteria.getAddressType() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressType(), Address_.addressType));
            }
            if (criteria.getDivisionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDivisionId(),
                    root -> root.join(Address_.division, JoinType.LEFT).get(Division_.id)));
            }
            if (criteria.getDistrictId() != null) {
                specification = specification.and(buildSpecification(criteria.getDistrictId(),
                    root -> root.join(Address_.district, JoinType.LEFT).get(District_.id)));
            }
            if (criteria.getThanaId() != null) {
                specification = specification.and(buildSpecification(criteria.getThanaId(),
                    root -> root.join(Address_.thana, JoinType.LEFT).get(Thana_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Address_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
