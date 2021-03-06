package software.cstl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import software.cstl.domain.Employee;
import software.cstl.domain.LeaveApplication;
import software.cstl.domain.LeaveType;
import software.cstl.domain.enumeration.LeaveApplicationStatus;

import java.time.LocalDate;
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

    List<LeaveApplication> findByApplicantEqualsAndLeaveTypeEqualsAndFromIsGreaterThanEqualAndToLessThanEqualAndStatus(Employee applicant, LeaveType leaveType, LocalDate from, LocalDate to, LeaveApplicationStatus leaveApplicationStatus);

    Boolean existsAllByStatusAndFromLessThanEqualAndToGreaterThanEqual(LeaveApplicationStatus status, LocalDate from, LocalDate to);

    @Query("select l from LeaveApplication  l where ?1<=l.to and ?2>=l.from ")
    List<LeaveApplication> findLeaveApplicationsWithinARange(LocalDate from, LocalDate to);
}
