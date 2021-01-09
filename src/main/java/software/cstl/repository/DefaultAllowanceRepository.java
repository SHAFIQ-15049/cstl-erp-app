package software.cstl.repository;

import software.cstl.domain.DefaultAllowance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.ActiveStatus;

/**
 * Spring Data  repository for the DefaultAllowance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefaultAllowanceRepository extends JpaRepository<DefaultAllowance, Long>, JpaSpecificationExecutor<DefaultAllowance> {
    DefaultAllowance findDefaultAllowanceByStatus(ActiveStatus status);
}
