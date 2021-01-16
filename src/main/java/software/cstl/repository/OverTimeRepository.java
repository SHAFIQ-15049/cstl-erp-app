package software.cstl.repository;

import software.cstl.domain.OverTime;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OverTime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverTimeRepository extends JpaRepository<OverTime, Long> {
}
