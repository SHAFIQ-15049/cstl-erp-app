package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.LeaveApplication;
import software.cstl.domain.LeaveType;
import software.cstl.domain.enumeration.LeaveApplicationStatus;
import software.cstl.repository.LeaveApplicationRepository;
import software.cstl.repository.LeaveTypeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link LeaveApplication}.
 */
@Service
@Transactional
public class LeaveApplicationService {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationService.class);

    private final LeaveApplicationRepository leaveApplicationRepository;

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveApplicationService(LeaveApplicationRepository leaveApplicationRepository, LeaveTypeRepository leaveTypeRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leaveTypeRepository = leaveTypeRepository;
    }

    /**
     * Save a leaveApplication.
     *
     * @param leaveApplication the entity to save.
     * @return the persisted entity.
     */
    public LeaveApplication save(LeaveApplication leaveApplication) {
        log.debug("Request to save LeaveApplication : {}", leaveApplication);
        return leaveApplicationRepository.save(leaveApplication);
    }

    /**
     * Get all the leaveApplications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveApplication> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveApplications");
        return leaveApplicationRepository.findAll(pageable);
    }


    /**
     * Get one leaveApplication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveApplication> findOne(Long id) {
        log.debug("Request to get LeaveApplication : {}", id);
        return leaveApplicationRepository.findById(id);
    }

    /**
     * Delete the leaveApplication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveApplication : {}", id);
        leaveApplicationRepository.deleteById(id);
    }

    public List<LeaveApplication> getLeaveApplications(Employee employee, LeaveType leaveType, LocalDate fromDate, LocalDate toDate, LeaveApplicationStatus leaveApplicationStatus) {
        return leaveApplicationRepository.findByApplicantEqualsAndLeaveTypeEqualsAndFromIsGreaterThanEqualAndToLessThanEqualAndStatus(employee, leaveType, fromDate, toDate, leaveApplicationStatus);
    }

    public List<LeaveApplication> getLeaveApplications(Employee employee, LocalDate fromDate, LocalDate toDate, LeaveApplicationStatus leaveApplicationStatus) {
        return leaveApplicationRepository.findByApplicantEqualsAndFromIsGreaterThanEqualAndToLessThanEqualAndStatus(employee, fromDate, toDate, leaveApplicationStatus);
    }

    public List<LeaveApplication> getLeaveApplications(Employee employee, LocalDate fromDate, LocalDate toDate) {
        return leaveApplicationRepository.findByApplicantEqualsAndFromIsGreaterThanEqualAndToLessThanEqual(employee, fromDate, toDate);
    }

    public boolean isValid(LeaveApplication leaveApplication) {
        LeaveType leaveType = leaveTypeRepository.getOne(leaveApplication.getLeaveType().getId());
        List<LeaveApplication> leaveApplications = leaveApplicationRepository.findByAppliedByIsCurrentUser();
        int totalDays = 0;
        for (LeaveApplication application : leaveApplications) {
            totalDays += application.getTotalDays();
        }
        totalDays = totalDays + leaveApplication.getTotalDays();
        return totalDays <= leaveType.getTotalDays();
    }
}
