package software.cstl.repository;

import software.cstl.domain.Employee;
import software.cstl.domain.Fine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.PaymentStatus;

import java.util.List;

/**
 * Spring Data  repository for the Fine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FineRepository extends JpaRepository<Fine, Long>, JpaSpecificationExecutor<Fine> {
    Fine findFineByEmployeeAndPaymentStatusIn(Employee employee, List<PaymentStatus> paymentStatusList);

    Boolean existsByEmployeeAndPaymentStatusIn(Employee employee, List<PaymentStatus> paymentStatusList);
}
