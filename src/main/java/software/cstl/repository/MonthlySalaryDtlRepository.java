package software.cstl.repository;

import software.cstl.domain.MonthlySalaryDtl;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MonthlySalaryDtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlySalaryDtlRepository extends JpaRepository<MonthlySalaryDtl, Long>, JpaSpecificationExecutor<MonthlySalaryDtl> {
}
