package software.cstl.repository;

import software.cstl.domain.FestivalAllowancePayment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FestivalAllowancePayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FestivalAllowancePaymentRepository extends JpaRepository<FestivalAllowancePayment, Long>, JpaSpecificationExecutor<FestivalAllowancePayment> {
}
