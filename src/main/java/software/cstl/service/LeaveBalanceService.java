package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.LeaveApplication;
import software.cstl.domain.LeaveType;
import software.cstl.domain.enumeration.LeaveApplicationStatus;
import software.cstl.service.dto.LeaveBalanceDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class LeaveBalanceService {

    private final Logger log = LoggerFactory.getLogger(LeaveBalanceService.class);

    private final EmployeeService employeeService;

    private final LeaveApplicationService leaveApplicationService;

    private final LeaveTypeService leaveTypeService;

    private final WeekendService weekendService;

    private final HolidayService holidayService;

    public LeaveBalanceService(EmployeeService employeeService, LeaveApplicationService leaveApplicationService, LeaveTypeService leaveTypeService, WeekendService weekendService, HolidayService holidayService) {
        this.employeeService = employeeService;
        this.leaveApplicationService = leaveApplicationService;
        this.leaveTypeService = leaveTypeService;
        this.weekendService = weekendService;
        this.holidayService = holidayService;
    }

    public List<LeaveBalanceDTO> calculate(Long employeeId) {

        List<LeaveBalanceDTO> leaveBalanceDTOs = new ArrayList<>();
        Optional<Employee> employee = employeeService.findOne(employeeId);

        if (employee.isPresent()) {
            List<LeaveType> leaveTypes = leaveTypeService.getAll();

            for (LeaveType leaveType : leaveTypes) {
                LeaveBalanceDTO leaveBalanceDTO = calculate(employeeId, leaveType.getId());
                leaveBalanceDTOs.add(leaveBalanceDTO);
            }
        }

        return leaveBalanceDTOs;
    }

    public LeaveBalanceDTO calculate(Long employeeId, Long leaveTypeId) {

        Optional<Employee> employee = employeeService.findOne(employeeId);
        Optional<LeaveType> leaveType = leaveTypeService.findOne(leaveTypeId);

        LeaveBalanceDTO leaveBalanceDTO = new LeaveBalanceDTO();

        if (employee.isPresent() && leaveType.isPresent()) {
            int numberOfYearPassedAfterJoining = getNumberOfYearsPassedAfterJoining(employee.get().getJoiningDate());

            LocalDate startDate = employee.get().getJoiningDate().plusYears(numberOfYearPassedAfterJoining);
            LocalDate endDate = startDate.plusYears(1);

            List<LeaveApplication> acceptedLeaveApplications = leaveApplicationService.getLeaveApplications(employee.get(), leaveType.get(), startDate, endDate, LeaveApplicationStatus.ACCEPTED);

            leaveBalanceDTO = getLeaveBalanceDTO(employee.get(), leaveType.get(), acceptedLeaveApplications, numberOfYearPassedAfterJoining);
        }

        return leaveBalanceDTO;
    }

    private LeaveBalanceDTO getLeaveBalanceDTO(Employee employee, LeaveType leaveType, List<LeaveApplication> acceptedLeaveApplications, int numberOfYearPassedAfterJoining) {
        LeaveBalanceDTO leaveBalanceDTO = new LeaveBalanceDTO();
        leaveBalanceDTO.setId(leaveType.getId());
        leaveBalanceDTO.setEmployeeId(employee.getId());
        leaveBalanceDTO.setEmployeeName(employee.getName());
        leaveBalanceDTO.setEmployeeJoiningDate(employee.getJoiningDate());
        leaveBalanceDTO.setLeaveTypeId(leaveType.getId());
        leaveBalanceDTO.setLeaveTypeName(leaveType.getName());
        leaveBalanceDTO.setTotalDays(Double.parseDouble(leaveType.getTotalDays().toString()));
        if (leaveType.getMaxValidity() > 1) {
            if (numberOfYearPassedAfterJoining > 0) {

                double earnedLeaveAmount = 0;

                LocalDate startDate = employee.getJoiningDate();
                LocalDate endDate = LocalDate.now();
                int numberOfWeekends = weekendService.getWeekendDateMapDTOs(startDate, endDate).size();
                int numberOfHolidays = holidayService.getHolidayDateMapDTOs(startDate, endDate).size();
                List<LeaveApplication> takenLeaves = leaveApplicationService.getLeaveApplications(employee, leaveType, employee.getJoiningDate(), LocalDate.now(), LeaveApplicationStatus.ACCEPTED);
                long totalDays = DAYS.between(startDate, endDate);
                earnedLeaveAmount = (double) (totalDays - numberOfWeekends - numberOfHolidays - takenLeaves.size())/18;
                if(earnedLeaveAmount > 40) {
                    earnedLeaveAmount = 40;
                }
                leaveBalanceDTO.setRemainingDays(earnedLeaveAmount);
            } else {
                leaveBalanceDTO.setRemainingDays((double) 0);
            }
        } else {
            leaveBalanceDTO.setRemainingDays((double) leaveType.getTotalDays() - acceptedLeaveApplications.size());
        }
        leaveBalanceDTO.setAcceptedLeaveApplications(acceptedLeaveApplications);
        return leaveBalanceDTO;
    }

    private int getNumberOfYearsPassedAfterJoining(LocalDate employeeJoiningDate) {
        LocalDate today = LocalDate.now();

        LocalDate startDate = employeeJoiningDate;
        LocalDate endDate = startDate.plusYears(1);

        int totalYear = 0;

        while (!(startDate.isAfter(today) && endDate.isBefore(today))) {

            totalYear = totalYear + 1;
            startDate = startDate.plusYears(1);
        }

        return totalYear - 1;
    }
}
