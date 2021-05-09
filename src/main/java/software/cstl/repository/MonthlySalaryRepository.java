package software.cstl.repository;

import software.cstl.domain.Department;
import software.cstl.domain.Designation;
import software.cstl.domain.MonthlySalary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.domain.enumeration.SalaryExecutionStatus;

import java.util.List;

/**
 * Spring Data  repository for the MonthlySalary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlySalaryRepository extends JpaRepository<MonthlySalary, Long>, JpaSpecificationExecutor<MonthlySalary> {
    MonthlySalary findMonthlySalaryByYearAndMonthAndDepartment(Integer year, MonthType month, Department department);

    Boolean existsByYearAndMonthAndDepartmentAndStatus(Integer year, MonthType monthType, Department department, SalaryExecutionStatus status);

    List<MonthlySalary> findAllByYearAndMonth(Integer year, MonthType month);

}
