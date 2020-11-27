package software.cstl.repository;

import software.cstl.domain.PersonalInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PersonalInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long>, JpaSpecificationExecutor<PersonalInfo> {
}
