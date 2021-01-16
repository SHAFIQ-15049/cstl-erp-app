package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.LeaveType;
import software.cstl.service.dto.LeaveBalanceDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link LeaveBalanceDTO}.
 */
@Service
@Transactional
public class LeaveBalanceService {

    private final Logger log = LoggerFactory.getLogger(LeaveBalanceService.class);

    private final EmployeeService employeeService;

    private final LeaveApplicationService leaveApplicationService;

    private final LeaveTypeService leaveTypeService;

    public LeaveBalanceService(EmployeeService employeeService, LeaveApplicationService leaveApplicationService, LeaveTypeService leaveTypeService) {
        this.employeeService = employeeService;
        this.leaveApplicationService = leaveApplicationService;
        this.leaveTypeService = leaveTypeService;
    }

    public List<LeaveBalanceDTO> calculate(Long employeeId) {

        Optional<Employee> employee = employeeService.findOne(employeeId);

        if (employee.isPresent()) {
            List<LeaveType> leaveTypes = leaveTypeService.getAll();

            for(LeaveType leaveType: leaveTypes) {

            }
        }

        return null;
    }

    public LeaveBalanceDTO calculate(Long employeeId, Long leaveTypeId) {

        Optional<Employee> employee = employeeService.findOne(employeeId);
        Optional<LeaveType> leaveType = leaveTypeService.findOne(leaveTypeId);

        LeaveBalanceDTO leaveBalanceDTO = new LeaveBalanceDTO();

        if(employee.isPresent() && leaveType.isPresent()) {
            int numberOfYearPassedAfterJoining = getNumberOfYearsPassedAfterJoining(employee.get().getJoiningDate());

            LocalDate startDate = employee.get().getJoiningDate().plusYears(numberOfYearPassedAfterJoining);
            LocalDate endDate = startDate.plusYears(1);
        }

        return null;
    }

    private int getNumberOfYearsPassedAfterJoining(LocalDate employeeJoiningDate) {
        LocalDate today = LocalDate.now();

        LocalDate startDate = employeeJoiningDate;
        LocalDate endDate = startDate.plusYears(1);

        int totalYear = 0;

        while(!(startDate.isBefore(today) && endDate.isAfter(today))) {

            totalYear = totalYear + 1;
            startDate = startDate.plusYears(1);
        }

        return totalYear - 1;
    }
}
