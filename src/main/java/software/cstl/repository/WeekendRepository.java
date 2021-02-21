package software.cstl.repository;

import software.cstl.domain.Weekend;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.WeekendStatus;

import java.util.List;

/**
 * Spring Data  repository for the Weekend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeekendRepository extends JpaRepository<Weekend, Long>, JpaSpecificationExecutor<Weekend> {
    List<Weekend> findAllByStatus(WeekendStatus weekendStatus);
}
