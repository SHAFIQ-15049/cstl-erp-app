package software.cstl.repository;

import software.cstl.domain.Holiday;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Spring Data  repository for the Holiday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {
    Boolean existsAllByFromLessThanEqualAndToGreaterThanEqual(LocalDate from, LocalDate to);
}
