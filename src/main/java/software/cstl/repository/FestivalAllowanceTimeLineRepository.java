package software.cstl.repository;

import software.cstl.domain.FestivalAllowanceTimeLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FestivalAllowanceTimeLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FestivalAllowanceTimeLineRepository extends JpaRepository<FestivalAllowanceTimeLine, Long>, JpaSpecificationExecutor<FestivalAllowanceTimeLine> {
}
