package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.LeaveApplication;
import software.cstl.domain.LeaveType;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.GenderType;
import software.cstl.domain.enumeration.LeaveApplicationStatus;
import software.cstl.service.dto.LeaveBalanceDTO;
import software.cstl.web.rest.errors.BadRequestAlertException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
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

    private final WeekendDateMapService weekendDateMapService;

    private final HolidayService holidayService;

    public LeaveBalanceService(EmployeeService employeeService, LeaveApplicationService leaveApplicationService, LeaveTypeService leaveTypeService, WeekendService weekendService, WeekendDateMapService weekendDateMapService, HolidayService holidayService) {
        this.employeeService = employeeService;
        this.leaveApplicationService = leaveApplicationService;
        this.leaveTypeService = leaveTypeService;
        this.weekendService = weekendService;
        this.holidayService = holidayService;
        this.weekendDateMapService = weekendDateMapService;
    }

    public List<LeaveBalanceDTO> getLeaveBalances(Long employeeId, int year) {

        List<LeaveBalanceDTO> leaveBalanceDTOs = new ArrayList<>();
        Optional<Employee> employee = employeeService.findOne(employeeId);

        if (employee.isPresent() && employee.get().getStatus().equals(EmployeeStatus.ACTIVE)) {
            List<LeaveType> leaveTypes = leaveTypeService.getAll();
            for (LeaveType leaveType : leaveTypes) {

                LeaveBalanceDTO leaveBalanceDTO = new LeaveBalanceDTO();
                switch (leaveType.getName()) {
                    case EARNED_LEAVE:
                        leaveBalanceDTO = getEarnedLeaveBalance(employee.get(), leaveType, year);
                        break;
                    case MATERNITY_LEAVE:
                        leaveBalanceDTO = getMaternityLeaveBalance(employee.get(), leaveType, year);
                        break;
                    default:
                        leaveBalanceDTO = getDefaultLeaveBalance(employee.get(), leaveType, year);
                }
                leaveBalanceDTOs.add(leaveBalanceDTO);
            }
        }
        return leaveBalanceDTOs;
    }

    public LeaveBalanceDTO getEarnedLeaveBalance(Employee employee, LeaveType leaveType, int year) {
        LeaveBalanceDTO leaveBalanceDTO = new LeaveBalanceDTO();

        BigDecimal remaining = BigDecimal.ZERO;
        List<LeaveApplication> acceptedLeaveApplications = new ArrayList<>();
        int numberOfYearPassedAfterJoining = getNumberOfYearsPassedFromJoiningDate(employee, year);

        for (int i = 0; i < numberOfYearPassedAfterJoining; i++) {
            LocalDate startDate = employee.getJoiningDate().plusYears(i);
            LocalDate endDate = startDate.plusYears(1).minusDays(1);
            BigDecimal totalDays = BigDecimal.valueOf(DAYS.between(startDate, endDate));
            BigDecimal numberOfWeekends = BigDecimal.valueOf(weekendDateMapService.getWeekendDateMapDTOs(startDate, endDate).size());
            BigDecimal numberOfHolidays = BigDecimal.valueOf(holidayService.getHolidayDateMapDTOs(startDate, endDate).size());
            acceptedLeaveApplications = leaveApplicationService.getLeaveApplications(employee, leaveType, startDate, endDate, LeaveApplicationStatus.ACCEPTED);
            BigDecimal numberOfAcceptedLeave = BigDecimal.valueOf(acceptedLeaveApplications.size());
            remaining = remaining.add((totalDays.subtract(numberOfWeekends).subtract(numberOfHolidays).subtract(numberOfAcceptedLeave)).divide(BigDecimal.valueOf(18), 3, RoundingMode.CEILING));
        }
        BigDecimal numberOfTotalAcceptedLeave = BigDecimal.valueOf(leaveApplicationService.getLeaveApplicationDetailDateMapDto(acceptedLeaveApplications).size());
        remaining = remaining.compareTo(BigDecimal.valueOf(40)) > 0 ? BigDecimal.valueOf(40) : remaining;
        leaveBalanceDTO = new LeaveBalanceDTO(leaveType.getId(), remaining,
            remaining.subtract(numberOfTotalAcceptedLeave), employee.getId(), employee.getName(),
            employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name(), acceptedLeaveApplications);

        return leaveBalanceDTO;
    }

    public LeaveBalanceDTO getMaternityLeaveBalance(Employee employee, LeaveType leaveType, int year) {
        LeaveBalanceDTO leaveBalanceDTO = new LeaveBalanceDTO();
        LocalDate startDate = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, 31);

        if(employee.getPersonalInfo().getGender() == null || employee.getPersonalInfo().getGender().equals(GenderType.MALE)) {
            return new LeaveBalanceDTO(leaveType.getId(), BigDecimal.ZERO,
                BigDecimal.ZERO, employee.getId(), employee.getName(),
                employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name(), new ArrayList<>());
        }

        if (startDate.isBefore(employee.getJoiningDate()) && !(employee.getJoiningDate().getYear() == startDate.getYear())) {
            return new LeaveBalanceDTO(leaveType.getId(), BigDecimal.ZERO,
                BigDecimal.ZERO, employee.getId(), employee.getName(),
                employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name(), new ArrayList<>());
        }

        if (!LocalDate.now().isAfter(employee.getJoiningDate().plusMonths(6))) {
            return new LeaveBalanceDTO(leaveType.getId(), BigDecimal.ZERO,
                BigDecimal.ZERO, employee.getId(), employee.getName(),
                employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name(), new ArrayList<>());
        }

        List<LeaveApplication> acceptedLeaveApplications = leaveApplicationService.getLeaveApplications(employee, leaveType, startDate, endDate, LeaveApplicationStatus.ACCEPTED);
        BigDecimal numberOfTotalAcceptedLeave = BigDecimal.valueOf(leaveApplicationService.getLeaveApplicationDetailDateMapDto(acceptedLeaveApplications).size());
        leaveBalanceDTO = new LeaveBalanceDTO(leaveType.getId(), BigDecimal.valueOf(leaveType.getTotalDays()),
            BigDecimal.valueOf(leaveType.getTotalDays()).subtract(numberOfTotalAcceptedLeave), employee.getId(), employee.getName(),
            employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name(), acceptedLeaveApplications);

        return leaveBalanceDTO;
    }

    public LeaveBalanceDTO getDefaultLeaveBalance(Employee employee, LeaveType leaveType, int year) {
        LocalDate startDate = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, 31);
        if (startDate.isBefore(employee.getJoiningDate()) && !(employee.getJoiningDate().getYear() == startDate.getYear())) {
            return new LeaveBalanceDTO(leaveType.getId(), BigDecimal.ZERO,
                BigDecimal.ZERO, employee.getId(), employee.getName(),
                employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name(), new ArrayList<>());
        }

        List<LeaveApplication> acceptedLeaveApplications = leaveApplicationService.getLeaveApplications(employee, leaveType, startDate, endDate, LeaveApplicationStatus.ACCEPTED);
        BigDecimal numberOfTotalAcceptedLeave = BigDecimal.valueOf(leaveApplicationService.getLeaveApplicationDetailDateMapDto(acceptedLeaveApplications).size());
        return new LeaveBalanceDTO(leaveType.getId(), BigDecimal.valueOf(leaveType.getTotalDays()),
            BigDecimal.valueOf(leaveType.getTotalDays()).subtract(numberOfTotalAcceptedLeave), employee.getId(), employee.getName(),
            employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name(), acceptedLeaveApplications);
    }

    private int getNumberOfYearsPassedFromJoiningDate(Employee employee, int year) {
        LocalDate startDate = employee.getJoiningDate();
        LocalDate endDate = LocalDate.of(year, startDate.getMonth(), startDate.getDayOfMonth());
        return Period.between(startDate, endDate).getYears();
    }
}
