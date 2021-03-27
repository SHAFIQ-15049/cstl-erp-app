package software.cstl.repository;

import software.cstl.domain.IdCardManagement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IdCardManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdCardManagementRepository extends JpaRepository<IdCardManagement, Long>, JpaSpecificationExecutor<IdCardManagement> {
}
