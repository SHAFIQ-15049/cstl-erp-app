package software.cstl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import software.cstl.domain.Attendance;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.AttendanceMarkedAs;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data  repository for the Attendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

    List<Attendance> findAllByAttendanceTimeBetween(Instant from, Instant to);

    List<Attendance> findAllByEmployeeAndAttendanceTimeBetween(Employee employee, Instant from, Instant to);

    int countAttendancesByEmployeeAndAttendanceTimeBetween(Employee employee, Instant fromDate, Instant toDate);

    List<Attendance> findAllByMarkedAsAndAttendanceTimeBetween(AttendanceMarkedAs attendanceMarkedAs, Instant from, Instant to);
}
