package software.cstl.repository.extended;

import software.cstl.domain.Designation;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.EmployeeType;
import software.cstl.repository.EmployeeRepository;

import java.util.List;

public interface EmployeeExtRepository extends EmployeeRepository {
    List<Employee> findAllByDesignationAndStatus(Designation designation, EmployeeStatus status);

    List<Employee> findAllByDesignation_IdAndStatus(Long designationId, EmployeeStatus status);

    List<Employee> findAllByDesignationAndStatusAndType(Designation designation, EmployeeStatus status, EmployeeType type);
}
