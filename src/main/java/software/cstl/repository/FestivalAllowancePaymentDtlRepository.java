package software.cstl.repository;

import software.cstl.domain.FestivalAllowancePaymentDtl;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FestivalAllowancePaymentDtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FestivalAllowancePaymentDtlRepository extends JpaRepository<FestivalAllowancePaymentDtl, Long>, JpaSpecificationExecutor<FestivalAllowancePaymentDtl> {
}
