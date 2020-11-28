package software.cstl.repository;

import software.cstl.domain.ServiceHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long>, JpaSpecificationExecutor<ServiceHistory> {
}
