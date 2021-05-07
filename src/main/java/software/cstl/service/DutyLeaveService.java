package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.*;
import software.cstl.domain.enumeration.LeaveApplicationStatus;
import software.cstl.domain.enumeration.LeaveAppliedStatus;
import software.cstl.service.dto.DutyLeaveDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class DutyLeaveService {

    private final Logger log = LoggerFactory.getLogger(DutyLeaveService.class);

    private final LeaveApplicationService leaveApplicationService;

    private final EmployeeService employeeService;

    private final UserService userService;

    private final LeaveTypeService leaveTypeService;

    private final AttendanceService attendanceService;

    public DutyLeaveService(LeaveApplicationService leaveApplicationService, EmployeeService employeeService, UserService userService, LeaveTypeService leaveTypeService, AttendanceService attendanceService) {
        this.leaveApplicationService = leaveApplicationService;
        this.employeeService = employeeService;
        this.userService = userService;
        this.leaveTypeService = leaveTypeService;
        this.attendanceService = attendanceService;
    }

    /**
     * Save a dutyLeave.
     *
     * @param dutyLeaveDTO the entity to save.
     * @return the persisted entity.
     */
    public DutyLeaveDTO save(DutyLeaveDTO dutyLeaveDTO) {
        log.debug("Request to save DutyLeave : {}", dutyLeaveDTO);
        return null;
    }

    @Transactional
    public List<DutyLeaveDTO> save(List<DutyLeaveDTO> dutyLeaveDTOs) {
        log.debug("Request to save DutyLeave : {}", dutyLeaveDTOs);

        List<LeaveType> leaveTypes = leaveTypeService.findAll();
        LeaveType leaveType = new LeaveType();

        for(LeaveType leaveType1: leaveTypes) {
            if(leaveType1.getName().name().contains("Duty")) {
                leaveType = leaveType1;
            }
        }
        Optional<User> user = userService.getUserWithAuthorities();

        if(user.isPresent()) {
            for (DutyLeaveDTO dutyLeaveDTO : dutyLeaveDTOs) {
                Optional<Employee> employee = employeeService.findOne(dutyLeaveDTO.getEmployeeId());

                if (employee.isPresent()) {
                    LeaveApplication leaveApplication = new LeaveApplication();

                    leaveApplication.applicant(employee.get());
                    leaveApplication.from(dutyLeaveDTO.getFromDate());
                    leaveApplication.to(dutyLeaveDTO.getToDate());
                    leaveApplication.totalDays(BigDecimal.ONE);
                    leaveApplication.appliedBy(user.get());
                    leaveApplication.setReason("Duty Leave");
                    leaveApplication.setStatus(LeaveApplicationStatus.ACCEPTED_BY_FIRST_AUTHORITY);

                    leaveApplication.setLeaveType(leaveType);

                    leaveApplicationService.save(leaveApplication);
                }

                if(employee.isPresent() && dutyLeaveDTO.getAttendanceSummaryDTO().getInTime() != null && dutyLeaveDTO.getAttendanceSummaryDTO().getOutTime() != null) {
                    List<Attendance> attendances = attendanceService.findAll(employee.get(), dutyLeaveDTO.getAttendanceSummaryDTO().getInTime(), dutyLeaveDTO.getAttendanceSummaryDTO().getOutTime());
                    for(Attendance attendance: attendances) {
                        attendance.setLeaveApplied(LeaveAppliedStatus.YES);
                        attendanceService.save(attendance);
                    }
                }
            }
        }
        return dutyLeaveDTOs;
    }

    /**
     * Get all the dutyLeaves.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DutyLeaveDTO> findAll() {
        log.debug("Request to get all DutyLeaves");
        return null;
    }


    /**
     * Get one dutyLeave by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DutyLeaveDTO> findOne(Long id) {
        log.debug("Request to get DutyLeave : {}", id);
        return null;
    }
}
