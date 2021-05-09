package software.cstl.repository;

import software.cstl.domain.Employee;
import software.cstl.domain.OverTime;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.MonthType;

import java.util.Optional;

/**
 * Spring Data  repository for the OverTime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverTimeRepository extends JpaRepository<OverTime, Long>, JpaSpecificationExecutor<OverTime> {
    void deleteOverTimeByYearAndMonthAndDesignation_Id(Integer year, MonthType monthType, Long designationid);

    void deleteAllByYearAndMonth(Integer year, MonthType monthType);

    Optional<OverTime> findByYearAndMonthAndEmployee(Integer year, MonthType monthType, Employee employee);
}
