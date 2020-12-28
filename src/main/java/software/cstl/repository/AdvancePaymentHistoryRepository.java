package software.cstl.repository;

import software.cstl.domain.AdvancePaymentHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdvancePaymentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvancePaymentHistoryRepository extends JpaRepository<AdvancePaymentHistory, Long>, JpaSpecificationExecutor<AdvancePaymentHistory> {
}
