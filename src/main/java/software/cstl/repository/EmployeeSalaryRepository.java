package software.cstl.repository;

import software.cstl.domain.EmployeeSalary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeSalary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Long>, JpaSpecificationExecutor<EmployeeSalary> {
}
