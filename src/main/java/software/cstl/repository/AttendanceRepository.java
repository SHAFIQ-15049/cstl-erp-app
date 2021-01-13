package software.cstl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import software.cstl.domain.Attendance;
import software.cstl.domain.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data  repository for the Attendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

    List<Attendance> getAllByAttendanceDateIsGreaterThanEqualAndAttendanceDateIsLessThanEqual(LocalDate fromDate, LocalDate toDate);

    List<Attendance> getALlByEmployeeEqualsAndAttendanceDateIsGreaterThanEqualAndAttendanceDateIsLessThanEqual(Employee employee, LocalDate fromDate, LocalDate toDate);
}
