package software.cstl.repository;

import software.cstl.domain.LeaveApplication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the LeaveApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long>, JpaSpecificationExecutor<LeaveApplication> {

    @Query("select leaveApplication from LeaveApplication leaveApplication where leaveApplication.appliedBy.login = ?#{principal.username}")
    List<LeaveApplication> findByAppliedByIsCurrentUser();

    @Query("select leaveApplication from LeaveApplication leaveApplication where leaveApplication.actionTakenBy.login = ?#{principal.username}")
    List<LeaveApplication> findByActionTakenByIsCurrentUser();
}
