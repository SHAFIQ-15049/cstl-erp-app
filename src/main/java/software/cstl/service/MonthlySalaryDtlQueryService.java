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

import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.MonthlySalaryDtlRepository;
import software.cstl.service.dto.MonthlySalaryDtlCriteria;

/**
 * Service for executing complex queries for {@link MonthlySalaryDtl} entities in the database.
 * The main input is a {@link MonthlySalaryDtlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MonthlySalaryDtl} or a {@link Page} of {@link MonthlySalaryDtl} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MonthlySalaryDtlQueryService extends QueryService<MonthlySalaryDtl> {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryDtlQueryService.class);

    private final MonthlySalaryDtlRepository monthlySalaryDtlRepository;

    public MonthlySalaryDtlQueryService(MonthlySalaryDtlRepository monthlySalaryDtlRepository) {
        this.monthlySalaryDtlRepository = monthlySalaryDtlRepository;
    }

    /**
     * Return a {@link List} of {@link MonthlySalaryDtl} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MonthlySalaryDtl> findByCriteria(MonthlySalaryDtlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MonthlySalaryDtl> specification = createSpecification(criteria);
        return monthlySalaryDtlRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MonthlySalaryDtl} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MonthlySalaryDtl> findByCriteria(MonthlySalaryDtlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MonthlySalaryDtl> specification = createSpecification(criteria);
        return monthlySalaryDtlRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MonthlySalaryDtlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MonthlySalaryDtl> specification = createSpecification(criteria);
        return monthlySalaryDtlRepository.count(specification);
    }

    /**
     * Function to convert {@link MonthlySalaryDtlCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MonthlySalaryDtl> createSpecification(MonthlySalaryDtlCriteria criteria) {
        Specification<MonthlySalaryDtl> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MonthlySalaryDtl_.id));
            }
            if (criteria.getGross() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGross(), MonthlySalaryDtl_.gross));
            }
            if (criteria.getBasic() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasic(), MonthlySalaryDtl_.basic));
            }
            if (criteria.getBasicPercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasicPercent(), MonthlySalaryDtl_.basicPercent));
            }
            if (criteria.getHouseRent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHouseRent(), MonthlySalaryDtl_.houseRent));
            }
            if (criteria.getHouseRentPercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHouseRentPercent(), MonthlySalaryDtl_.houseRentPercent));
            }
            if (criteria.getMedicalAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicalAllowance(), MonthlySalaryDtl_.medicalAllowance));
            }
            if (criteria.getMedicalAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicalAllowancePercent(), MonthlySalaryDtl_.medicalAllowancePercent));
            }
            if (criteria.getConvinceAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConvinceAllowance(), MonthlySalaryDtl_.convinceAllowance));
            }
            if (criteria.getConvinceAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConvinceAllowancePercent(), MonthlySalaryDtl_.convinceAllowancePercent));
            }
            if (criteria.getFoodAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFoodAllowance(), MonthlySalaryDtl_.foodAllowance));
            }
            if (criteria.getFoodAllowancePercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFoodAllowancePercent(), MonthlySalaryDtl_.foodAllowancePercent));
            }
            if (criteria.getFine() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFine(), MonthlySalaryDtl_.fine));
            }
            if (criteria.getAdvance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdvance(), MonthlySalaryDtl_.advance));
            }
            if (criteria.getTotalWorkingDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalWorkingDays(), MonthlySalaryDtl_.totalWorkingDays));
            }
            if (criteria.getRegularLeave() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegularLeave(), MonthlySalaryDtl_.regularLeave));
            }
            if (criteria.getSickLeave() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSickLeave(), MonthlySalaryDtl_.sickLeave));
            }
            if (criteria.getCompensationLeave() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompensationLeave(), MonthlySalaryDtl_.compensationLeave));
            }
            if (criteria.getFestivalLeave() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFestivalLeave(), MonthlySalaryDtl_.festivalLeave));
            }
            if (criteria.getWeeklyLeave() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeeklyLeave(), MonthlySalaryDtl_.weeklyLeave));
            }
            if (criteria.getPresent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPresent(), MonthlySalaryDtl_.present));
            }
            if (criteria.getAbsent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAbsent(), MonthlySalaryDtl_.absent));
            }
            if (criteria.getTotalMonthDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMonthDays(), MonthlySalaryDtl_.totalMonthDays));
            }
            if (criteria.getOverTimeHour() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOverTimeHour(), MonthlySalaryDtl_.overTimeHour));
            }
            if (criteria.getOverTimeSalaryHourly() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOverTimeSalaryHourly(), MonthlySalaryDtl_.overTimeSalaryHourly));
            }
            if (criteria.getOverTimeSalary() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOverTimeSalary(), MonthlySalaryDtl_.overTimeSalary));
            }
            if (criteria.getPresentBonus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPresentBonus(), MonthlySalaryDtl_.presentBonus));
            }
            if (criteria.getAbsentFine() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAbsentFine(), MonthlySalaryDtl_.absentFine));
            }
            if (criteria.getStampPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStampPrice(), MonthlySalaryDtl_.stampPrice));
            }
            if (criteria.getTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTax(), MonthlySalaryDtl_.tax));
            }
            if (criteria.getOthers() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOthers(), MonthlySalaryDtl_.others));
            }
            if (criteria.getTotalPayable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPayable(), MonthlySalaryDtl_.totalPayable));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), MonthlySalaryDtl_.status));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), MonthlySalaryDtl_.type));
            }
            if (criteria.getExecutedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecutedOn(), MonthlySalaryDtl_.executedOn));
            }
            if (criteria.getExecutedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExecutedBy(), MonthlySalaryDtl_.executedBy));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(MonthlySalaryDtl_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getMonthlySalaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getMonthlySalaryId(),
                    root -> root.join(MonthlySalaryDtl_.monthlySalary, JoinType.LEFT).get(MonthlySalary_.id)));
            }
        }
        return specification;
    }
}
