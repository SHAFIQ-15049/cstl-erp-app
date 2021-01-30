package software.cstl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import software.cstl.domain.Attendance;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.AttendanceMarkedAs;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the Attendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {
    @Query(value = "select count(*) from (" +
        "select distinct(attendance_date) from (" +
        "select char(attendance_date,'dd-mm-yyyy') attendance_date from attendance where  (attendance_date>=?1 and attendance_date<=?2)))", nativeQuery = true)
    int totalAttendanceDays(LocalDate from, LocalDate to);

    int countAttendancesByEmployeeAndAttendanceTimeBetween(Employee employee, Instant fromDate, Instant toDate);

    List<Attendance> findAllByEmployeeAndAttendanceTimeBetween(Employee employee, Instant from, Instant to);

    List<Attendance> getAllByAttendanceTimeIsGreaterThanEqualAndAttendanceTimeIsLessThanEqual(Instant from, Instant to);

    List<Attendance> getAllByAttendanceTimeIsGreaterThanEqualAndAttendanceTimeIsLessThanEqualAndMarkedAsEquals(Instant from, Instant to, AttendanceMarkedAs attendanceMarkedAs);

    List<Attendance> getALlByEmployeeEqualsAndAttendanceTimeIsGreaterThanEqualAndAttendanceTimeIsLessThanEqual(Employee employee, Instant from, Instant to);
}
