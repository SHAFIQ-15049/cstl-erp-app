package software.cstl.repository.extended;

import software.cstl.domain.Designation;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.repository.EmployeeRepository;

import java.util.List;

public interface EmployeeExtRepository extends EmployeeRepository {
    List<Employee> findAllByDesignationAndStatus(Designation designation, EmployeeStatus status);
}
