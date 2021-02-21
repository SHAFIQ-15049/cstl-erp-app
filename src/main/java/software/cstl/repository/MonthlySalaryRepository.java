package software.cstl.repository;

import software.cstl.domain.Designation;
import software.cstl.domain.MonthlySalary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.MonthType;

import java.util.List;

/**
 * Spring Data  repository for the MonthlySalary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlySalaryRepository extends JpaRepository<MonthlySalary, Long>, JpaSpecificationExecutor<MonthlySalary> {
    MonthlySalary findMonthlySalaryByYearAndMonthAndDesignation(Integer year, MonthType month, Designation designation);

    List<MonthlySalary> findAllByYearAndMonth(Integer year, MonthType month);

}
