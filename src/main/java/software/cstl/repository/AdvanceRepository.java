package software.cstl.repository;

import software.cstl.domain.Advance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.PaymentStatus;

import java.util.List;

/**
 * Spring Data  repository for the Advance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvanceRepository extends JpaRepository<Advance, Long>, JpaSpecificationExecutor<Advance> {
    Boolean existsByEmployeeAndPaymentStatusIn(Employee employee, List<PaymentStatus> paymentStatusList);

    Advance findByEmployeeAndPaymentStatusIn(Employee employee, List<PaymentStatus> paymentStatusList);
}
