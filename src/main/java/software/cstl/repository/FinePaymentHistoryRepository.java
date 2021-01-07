package software.cstl.repository;

import software.cstl.domain.FinePaymentHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FinePaymentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinePaymentHistoryRepository extends JpaRepository<FinePaymentHistory, Long>, JpaSpecificationExecutor<FinePaymentHistory> {
}
