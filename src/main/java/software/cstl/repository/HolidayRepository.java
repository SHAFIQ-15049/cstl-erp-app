package software.cstl.repository;

import software.cstl.domain.Holiday;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data  repository for the Holiday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {
    Boolean existsAllByFromLessThanEqualAndToGreaterThanEqual(LocalDate from, LocalDate to);

    @Query("select h from Holiday h where ?1<= h.to and ?2>=h.from")
    List<Holiday> getOverLappingHolidays(LocalDate from, LocalDate to);
}
