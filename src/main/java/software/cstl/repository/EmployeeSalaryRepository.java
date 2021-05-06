package software.cstl.repository;

import software.cstl.domain.Designation;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.EmployeeStatus;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the EmployeeSalary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Long>, JpaSpecificationExecutor<EmployeeSalary> {
    List<EmployeeSalary> findAllByEmployee_DesignationAndEmployee_Status(Designation designation, EmployeeStatus status);

    EmployeeSalary findByEmployeeAndStatus(Employee employee, ActiveStatus status);

    List<EmployeeSalary> findAllByStatusAndSalaryStartDateIsBeforeAndSalaryEndDateIsAfter(ActiveStatus status, Instant  startDate, Instant endDate);

    List<EmployeeSalary> findAllByStatusAndSalaryEndDateIsBefore(ActiveStatus status, Instant dateTime);

    EmployeeSalary findBySalaryStartDateIsLessThanEqualAndSalaryEndDateGreaterThanEqual(Instant startDate, Instant endDate);

    EmployeeSalary findByEmployee_IdAndSalaryStartDateIsLessThanEqualAndSalaryEndDateGreaterThanEqual(Long employeeId, Instant startDate, Instant endDate);


}
