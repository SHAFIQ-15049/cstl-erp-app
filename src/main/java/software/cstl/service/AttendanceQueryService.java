package software.cstl.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.*;
import software.cstl.repository.AttendanceRepository;
import software.cstl.service.dto.AttendanceCriteria;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link Attendance} entities in the database.
 * The main input is a {@link AttendanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Attendance} or a {@link Page} of {@link Attendance} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttendanceQueryService extends QueryService<Attendance> {

    private final Logger log = LoggerFactory.getLogger(AttendanceQueryService.class);

    private final AttendanceRepository attendanceRepository;

    public AttendanceQueryService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    /**
     * Return a {@link List} of {@link Attendance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Attendance> findByCriteria(AttendanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Attendance> specification = createSpecification(criteria);
        return attendanceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Attendance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Attendance> findByCriteria(AttendanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Attendance> specification = createSpecification(criteria);
        return attendanceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttendanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Attendance> specification = createSpecification(criteria);
        return attendanceRepository.count(specification);
    }

    /**
     * Function to convert {@link AttendanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Attendance> createSpecification(AttendanceCriteria criteria) {
        Specification<Attendance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Attendance_.id));
            }
            if (criteria.getAttendanceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttendanceDate(), Attendance_.attendanceDate));
            }
            if (criteria.getAttendanceTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttendanceTime(), Attendance_.attendanceTime));
            }
            if (criteria.getConsiderAs() != null) {
                specification = specification.and(buildSpecification(criteria.getConsiderAs(), Attendance_.considerAs));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Attendance_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getAttendanceDataUploadId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttendanceDataUploadId(),
                    root -> root.join(Attendance_.attendanceDataUpload, JoinType.LEFT).get(AttendanceDataUpload_.id)));
            }
            if (criteria.getEmployeeSalaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeSalaryId(),
                    root -> root.join(Attendance_.employeeSalary, JoinType.LEFT).get(EmployeeSalary_.id)));
            }
        }
        return specification;
    }
}
