package software.cstl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import software.cstl.domain.Attendance;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.ConsiderAsType;

import java.lang.annotation.Native;
import java.time.LocalDate;
import java.util.List;
import java.time.Instant;


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

    int countAttendancesByEmployeeAndAndConsiderAsAndAttendanceTimeBetween(Employee employee, ConsiderAsType considerAsType, Instant fromDate, Instant toDate);

    List<Attendance> findAllByEmployeeAndConsiderAsAndAttendanceTimeBetween(Employee employee, ConsiderAsType considerAsType, Instant from, Instant to);

    List<Attendance> getAllByAttendanceTimeIsGreaterThanEqualAndAttendanceTimeIsLessThanEqual(Instant from, Instant to);

    List<Attendance> getALlByEmployeeEqualsAndAttendanceTimeIsGreaterThanEqualAndAttendanceTimeIsLessThanEqual(Employee employee, Instant from, Instant to);
}
