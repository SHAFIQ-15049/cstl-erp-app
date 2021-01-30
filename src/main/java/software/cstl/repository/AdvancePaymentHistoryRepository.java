package software.cstl.repository;

import software.cstl.domain.Advance;
import software.cstl.domain.AdvancePaymentHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.MonthType;

import java.time.Year;

/**
 * Spring Data  repository for the AdvancePaymentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvancePaymentHistoryRepository extends JpaRepository<AdvancePaymentHistory, Long>, JpaSpecificationExecutor<AdvancePaymentHistory> {
    Boolean existsByAdvanceAndYearAndMonthType(Advance advance, Integer year, MonthType monthType);

    AdvancePaymentHistory findByAdvanceAndYearAndMonthType(Advance advance, Integer year, MonthType monthType);

    AdvancePaymentHistory findByAdvance_EmployeeAndYearAndMonthType(Employee employee, Integer year, MonthType monthType);
}
