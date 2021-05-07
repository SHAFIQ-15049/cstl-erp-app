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
import software.cstl.service.dto.HolidayDateMapDTO;
import software.cstl.service.dto.LeaveApplicationDetailDateMapDTO;
import software.cstl.service.dto.LeaveBalanceDTO;
import software.cstl.service.dto.WeekendDateMapDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
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

    private final WeekendDateMapService weekendDateMapService;

    private final HolidayService holidayService;

    public LeaveBalanceService(EmployeeService employeeService, LeaveApplicationService leaveApplicationService, LeaveTypeService leaveTypeService, WeekendDateMapService weekendDateMapService, HolidayService holidayService) {
        this.employeeService = employeeService;
        this.leaveApplicationService = leaveApplicationService;
        this.leaveTypeService = leaveTypeService;
        this.holidayService = holidayService;
        this.weekendDateMapService = weekendDateMapService;
    }

    public List<LeaveBalanceDTO> getLeaveBalances(Long employeeId, int year) {

        List<LeaveBalanceDTO> leaveBalanceDTOs = new ArrayList<>();
        Optional<Employee> employee = employeeService.findOne(employeeId);

        if (employee.isPresent() && employee.get().getStatus().equals(EmployeeStatus.ACTIVE)) {
            List<LeaveType> leaveTypes = leaveTypeService.findAll();
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

        LocalDate joiningDate = employee.getJoiningDate();
        LocalDate firstIntervalDate = joiningDate.plusYears(1).minusDays(1);
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = year == currentDate.getYear() ? currentDate : LocalDate.of(year, joiningDate.getMonthValue(), joiningDate.getDayOfMonth());

        if (selectedDate.equals(firstIntervalDate) || selectedDate.isBefore(firstIntervalDate)) {
            return new LeaveBalanceDTO(leaveType.getId(), BigDecimal.ZERO,
                BigDecimal.ZERO, employee.getId(), employee.getName(),
                employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name());
        }

        LocalDate nextInterval = firstIntervalDate.plusDays(1);
        BigDecimal totalEarnedLeave = BigDecimal.ZERO;

        while (nextInterval.equals(selectedDate) || nextInterval.isBefore(selectedDate)) {
            LocalDate lastYearStartDate = nextInterval.minusYears(1);
            LocalDate lastYearEndDate = nextInterval.minusDays(1);
            List<LeaveApplication> leaveApplications = leaveApplicationService.getLeaveApplications(employee, leaveType,
                lastYearStartDate, lastYearEndDate, LeaveApplicationStatus.ACCEPTED);
            List<LeaveApplicationDetailDateMapDTO> acceptedLeaveApplications = leaveApplicationService.getLeaveApplicationDetailDateMapDto(leaveApplications);
            List<HolidayDateMapDTO> holidayDateMapDTOs = holidayService.findAllHolidayDateMapDTOs(lastYearStartDate, lastYearEndDate);
            List<WeekendDateMapDTO> weekendDateMapDTOs = weekendDateMapService.findAllWeekendDateMapDTOs(lastYearStartDate, lastYearEndDate);
            BigDecimal totalDays = BigDecimal.valueOf(DAYS.between(lastYearStartDate, lastYearEndDate)).add(BigDecimal.ONE);

            BigDecimal result = totalEarnedLeave.add(totalDays
                .subtract(BigDecimal.valueOf(holidayDateMapDTOs.size()))
                .subtract(BigDecimal.valueOf(weekendDateMapDTOs.size()))
                .subtract(BigDecimal.valueOf(acceptedLeaveApplications.size()))).divide(BigDecimal.valueOf(18), 3, RoundingMode.HALF_UP);

            totalEarnedLeave = totalEarnedLeave.add(result);
            nextInterval = nextInterval.plusYears(1);
        }
        totalEarnedLeave = totalEarnedLeave.compareTo(BigDecimal.valueOf(40)) > 0 ? BigDecimal.valueOf(40) : totalEarnedLeave;
        List<LeaveApplication> leaveApplicationList = leaveApplicationService.getLeaveApplications(employee, leaveType,
            joiningDate.minusYears(1), joiningDate, LeaveApplicationStatus.ACCEPTED);
        List<LeaveApplicationDetailDateMapDTO> acceptedLeaveApplicationList = leaveApplicationService.getLeaveApplicationDetailDateMapDto(leaveApplicationList);

        return new LeaveBalanceDTO(leaveType.getId(), totalEarnedLeave,
            totalEarnedLeave.subtract(BigDecimal.valueOf(acceptedLeaveApplicationList.size())), employee.getId(), employee.getName(),
            employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name());
    }

    public LeaveBalanceDTO getMaternityLeaveBalance(Employee employee, LeaveType leaveType, int year) {
        LocalDate joiningDate = employee.getJoiningDate();
        LocalDate firstIntervalDate = joiningDate.plusMonths(6).minusDays(1);
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = year == currentDate.getYear() ? currentDate : LocalDate.of(year, Month.DECEMBER, 31);

        if (employee.getPersonalInfo().getGender() == null || employee.getPersonalInfo().getGender().equals(GenderType.MALE)) {
            return new LeaveBalanceDTO(leaveType.getId(), BigDecimal.ZERO,
                BigDecimal.ZERO, employee.getId(), employee.getName(),
                employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name());
        }

        if (selectedDate.equals(firstIntervalDate) || selectedDate.isBefore(firstIntervalDate)) {
            return new LeaveBalanceDTO(leaveType.getId(), BigDecimal.ZERO,
                BigDecimal.ZERO, employee.getId(), employee.getName(),
                employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name());
        }

        LocalDate startDate = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, 31);
        List<LeaveApplication> acceptedLeaveApplications = leaveApplicationService.getLeaveApplications(employee, leaveType, startDate, endDate, LeaveApplicationStatus.ACCEPTED);
        BigDecimal numberOfTotalAcceptedLeave = BigDecimal.valueOf(leaveApplicationService.getLeaveApplicationDetailDateMapDto(acceptedLeaveApplications).size());
        return new LeaveBalanceDTO(leaveType.getId(), leaveType.getTotalDays(),
            leaveType.getTotalDays().subtract(numberOfTotalAcceptedLeave), employee.getId(), employee.getName(),
            employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name());
    }

    public LeaveBalanceDTO getDefaultLeaveBalance(Employee employee, LeaveType leaveType, int year) {
        LocalDate joiningDate = employee.getJoiningDate();
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = year == currentDate.getYear() ? currentDate : LocalDate.of(year, Month.DECEMBER, 31);

        if (selectedDate.equals(joiningDate) || selectedDate.isBefore(joiningDate)) {
            return new LeaveBalanceDTO(leaveType.getId(), BigDecimal.ZERO,
                BigDecimal.ZERO, employee.getId(), employee.getName(),
                employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name());
        }

        LocalDate startDate = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, 31);
        List<LeaveApplication> acceptedLeaveApplications = leaveApplicationService.getLeaveApplications(employee, leaveType, startDate, endDate, LeaveApplicationStatus.ACCEPTED);
        BigDecimal numberOfTotalAcceptedLeave = BigDecimal.valueOf(leaveApplicationService.getLeaveApplicationDetailDateMapDto(acceptedLeaveApplications).size());
        return new LeaveBalanceDTO(leaveType.getId(), leaveType.getTotalDays(),
            leaveType.getTotalDays().subtract(numberOfTotalAcceptedLeave), employee.getId(), employee.getName(),
            employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name());
    }
}
