package software.cstl.repository;

import software.cstl.domain.PartialSalary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartialSalary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartialSalaryRepository extends JpaRepository<PartialSalary, Long>, JpaSpecificationExecutor<PartialSalary> {
}
