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

import software.cstl.domain.Customer;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.CustomerRepository;
import software.cstl.service.dto.CustomerCriteria;

/**
 * Service for executing complex queries for {@link Customer} entities in the database.
 * The main input is a {@link CustomerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Customer} or a {@link Page} of {@link Customer} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerQueryService extends QueryService<Customer> {

    private final Logger log = LoggerFactory.getLogger(CustomerQueryService.class);

    private final CustomerRepository customerRepository;

    public CustomerQueryService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Return a {@link List} of {@link Customer} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Customer> findByCriteria(CustomerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Customer} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Customer> findByCriteria(CustomerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Customer> createSpecification(CustomerCriteria criteria) {
        Specification<Customer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Customer_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Customer_.name));
            }
            if (criteria.getFatherOrHusband() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherOrHusband(), Customer_.fatherOrHusband));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Customer_.address));
            }
            if (criteria.getSex() != null) {
                specification = specification.and(buildSpecification(criteria.getSex(), Customer_.sex));
            }
            if (criteria.getPhoneNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNo(), Customer_.phoneNo));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), Customer_.nationality));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), Customer_.dateOfBirth));
            }
            if (criteria.getGuardiansName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuardiansName(), Customer_.guardiansName));
            }
            if (criteria.getChassisNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChassisNo(), Customer_.chassisNo));
            }
            if (criteria.getEngineNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEngineNo(), Customer_.engineNo));
            }
            if (criteria.getYearsOfMfg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearsOfMfg(), Customer_.yearsOfMfg));
            }
            if (criteria.getPreRegnNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreRegnNo(), Customer_.preRegnNo));
            }
            if (criteria.getPoOrBank() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPoOrBank(), Customer_.poOrBank));
            }
            if (criteria.getVoterIdNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoterIdNo(), Customer_.voterIdNo));
            }
            if (criteria.getVehicleId() != null) {
                specification = specification.and(buildSpecification(criteria.getVehicleId(),
                    root -> root.join(Customer_.vehicle, JoinType.LEFT).get(Vehicle_.id)));
            }
        }
        return specification;
    }
}
