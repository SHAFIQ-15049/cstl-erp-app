package software.cstl.repository;

import software.cstl.domain.Employee;
import software.cstl.domain.PartialSalary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.MonthType;

import java.util.Optional;

/**
 * Spring Data  repository for the PartialSalary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartialSalaryRepository extends JpaRepository<PartialSalary, Long>, JpaSpecificationExecutor<PartialSalary> {
    Optional<PartialSalary> findByEmployeeAndYearAndMonth(Employee employee, Integer year, MonthType month);
}
