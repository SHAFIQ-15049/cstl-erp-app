package software.cstl.repository;

import software.cstl.domain.EducationalInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EducationalInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationalInfoRepository extends JpaRepository<EducationalInfo, Long>, JpaSpecificationExecutor<EducationalInfo> {
}
