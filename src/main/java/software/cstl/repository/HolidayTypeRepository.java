package software.cstl.repository;

import software.cstl.domain.HolidayType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HolidayType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolidayTypeRepository extends JpaRepository<HolidayType, Long>, JpaSpecificationExecutor<HolidayType> {
}
