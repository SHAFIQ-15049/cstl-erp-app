package software.cstl.repository;

import software.cstl.domain.Employee;
import software.cstl.domain.Fine;
import software.cstl.domain.FinePaymentHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.MonthType;

/**
 * Spring Data  repository for the FinePaymentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinePaymentHistoryRepository extends JpaRepository<FinePaymentHistory, Long>, JpaSpecificationExecutor<FinePaymentHistory> {
    FinePaymentHistory findByFineAndYearAndMonthType(Fine fine, Integer year, MonthType monthType);

    FinePaymentHistory findByFine_EmployeeAndYearAndMonthType(Employee employee, Integer year, MonthType monthType);

    Boolean existsByFineAndYearAndMonthType(Fine fine, Integer year, MonthType monthType);
}
