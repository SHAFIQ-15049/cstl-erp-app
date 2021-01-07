package software.cstl.repository;

import software.cstl.domain.MonthlySalary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MonthlySalary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlySalaryRepository extends JpaRepository<MonthlySalary, Long>, JpaSpecificationExecutor<MonthlySalary> {

}
