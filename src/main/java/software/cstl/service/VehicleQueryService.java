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

import software.cstl.domain.Vehicle;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.VehicleRepository;
import software.cstl.service.dto.VehicleCriteria;

/**
 * Service for executing complex queries for {@link Vehicle} entities in the database.
 * The main input is a {@link VehicleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Vehicle} or a {@link Page} of {@link Vehicle} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleQueryService extends QueryService<Vehicle> {

    private final Logger log = LoggerFactory.getLogger(VehicleQueryService.class);

    private final VehicleRepository vehicleRepository;

    public VehicleQueryService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Return a {@link List} of {@link Vehicle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Vehicle> findByCriteria(VehicleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vehicle> specification = createSpecification(criteria);
        return vehicleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Vehicle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Vehicle> findByCriteria(VehicleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vehicle> specification = createSpecification(criteria);
        return vehicleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vehicle> specification = createSpecification(criteria);
        return vehicleRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vehicle> createSpecification(VehicleCriteria criteria) {
        Specification<Vehicle> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Vehicle_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Vehicle_.name));
            }
            if (criteria.getVehicleId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVehicleId(), Vehicle_.vehicleId));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Vehicle_.type));
            }
            if (criteria.getClassOfVehicle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassOfVehicle(), Vehicle_.classOfVehicle));
            }
            if (criteria.getTypeOfBody() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeOfBody(), Vehicle_.typeOfBody));
            }
            if (criteria.getColour() != null) {
                specification = specification.and(buildSpecification(criteria.getColour(), Vehicle_.colour));
            }
            if (criteria.getNumberOfCylinders() != null) {
                specification = specification.and(buildSpecification(criteria.getNumberOfCylinders(), Vehicle_.numberOfCylinders));
            }
            if (criteria.getEngineNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEngineNumber(), Vehicle_.engineNumber));
            }
            if (criteria.getHorsePower() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHorsePower(), Vehicle_.horsePower));
            }
            if (criteria.getCubicCapacity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCubicCapacity(), Vehicle_.cubicCapacity));
            }
            if (criteria.getNoOfStandee() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoOfStandee(), Vehicle_.noOfStandee));
            }
            if (criteria.getUnladenWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnladenWeight(), Vehicle_.unladenWeight));
            }
            if (criteria.getPrevRegnNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrevRegnNo(), Vehicle_.prevRegnNo));
            }
            if (criteria.getMakersName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMakersName(), Vehicle_.makersName));
            }
            if (criteria.getMakersCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMakersCountry(), Vehicle_.makersCountry));
            }
            if (criteria.getYearsOfManufacture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearsOfManufacture(), Vehicle_.yearsOfManufacture));
            }
            if (criteria.getChassisNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChassisNumber(), Vehicle_.chassisNumber));
            }
            if (criteria.getFuelUsed() != null) {
                specification = specification.and(buildSpecification(criteria.getFuelUsed(), Vehicle_.fuelUsed));
            }
            if (criteria.getRpm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRpm(), Vehicle_.rpm));
            }
            if (criteria.getSeats() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeats(), Vehicle_.seats));
            }
            if (criteria.getWheelBase() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWheelBase(), Vehicle_.wheelBase));
            }
            if (criteria.getMaxLaden() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxLaden(), Vehicle_.maxLaden));
            }
        }
        return specification;
    }
}
