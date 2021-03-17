package software.cstl.service;

import net.sf.cglib.core.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.LeaveApplication;
import software.cstl.domain.LeaveType;
import software.cstl.domain.Weekend;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.GenderType;
import software.cstl.domain.enumeration.LeaveApplicationStatus;
import software.cstl.service.dto.HolidayDateMapDTO;
import software.cstl.service.dto.LeaveApplicationDetailDateMapDTO;
import software.cstl.service.dto.LeaveBalanceDTO;
import software.cstl.service.dto.WeekendDateMapDTO;
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

        LocalDate startDate = employee.getJoiningDate();
        LocalDate endDate = startDate.plusYears(1);
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = year == currentDate.getYear() ? currentDate : LocalDate.of(year, startDate.getMonthValue(), startDate.getDayOfMonth());

        if ((startDate.equals(selectedDate) || endDate.minusDays(1).equals(selectedDate))
            || ((startDate.isBefore(selectedDate) && endDate.minusDays(1).isAfter(selectedDate)))
        || startDate.isAfter(selectedDate)) {
            return new LeaveBalanceDTO(leaveType.getId(), BigDecimal.ZERO,
                BigDecimal.ZERO, employee.getId(), employee.getName(),
                employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name(), new ArrayList<>());
        } else {
            startDate = endDate;
            List<LeaveApplicationDetailDateMapDTO> acceptedLeaveApplications = new ArrayList<>();
            List<HolidayDateMapDTO> holidayDateMapDTOs = new ArrayList<>();
            List<WeekendDateMapDTO> weekendDateMapDTOs = new ArrayList<>();
            BigDecimal totalEarnedLeave = BigDecimal.ZERO;
            BigDecimal remainingEarnedLeave = BigDecimal.ZERO;

            while (startDate.equals(selectedDate) || startDate.isBefore(selectedDate)) {
                LocalDate lastYearStartDate = startDate.minusYears(1);
                LocalDate lastYearEndDate = startDate.minusDays(1);
                List<LeaveApplication> leaveApplications = new ArrayList<>();
                leaveApplications = leaveApplicationService.getLeaveApplications(employee, leaveType,
                    lastYearStartDate, lastYearEndDate, LeaveApplicationStatus.ACCEPTED);
                acceptedLeaveApplications = leaveApplicationService.getLeaveApplicationDetailDateMapDto(leaveApplications);
                holidayDateMapDTOs = holidayService.getHolidayDateMapDTOs(lastYearStartDate, lastYearEndDate);
                weekendDateMapDTOs = weekendDateMapService.getWeekendDateMapDTOs(lastYearStartDate, lastYearEndDate);
                BigDecimal totalDays = BigDecimal.valueOf(DAYS.between(lastYearStartDate, lastYearEndDate.plusDays(1)));

                BigDecimal result = totalEarnedLeave.add(totalDays
                    .subtract(BigDecimal.valueOf(holidayDateMapDTOs.size()))
                    .subtract(BigDecimal.valueOf(weekendDateMapDTOs.size()))
                    .subtract(BigDecimal.valueOf(acceptedLeaveApplications.size()))).divide(BigDecimal.valueOf(18), 3, RoundingMode.HALF_UP);

                totalEarnedLeave = totalEarnedLeave.add(result);
                startDate = startDate.plusYears(1);
            }
            totalEarnedLeave = totalEarnedLeave.compareTo(BigDecimal.valueOf(40)) > 0 ? BigDecimal.valueOf(40) : totalEarnedLeave;
            List<LeaveApplication> leaveApplicationList = leaveApplicationService.getLeaveApplications(employee, leaveType,
                startDate.minusYears(1), startDate.minusDays(1), LeaveApplicationStatus.ACCEPTED);
            List<LeaveApplicationDetailDateMapDTO> acceptedLeaveApplicationList = leaveApplicationService.getLeaveApplicationDetailDateMapDto(leaveApplicationList);

            leaveBalanceDTO = new LeaveBalanceDTO(leaveType.getId(), totalEarnedLeave,
                totalEarnedLeave.subtract(BigDecimal.valueOf(acceptedLeaveApplicationList.size())), employee.getId(), employee.getName(),
                employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name(), leaveApplicationList);
            return leaveBalanceDTO;
        }
    }

    public LeaveBalanceDTO getMaternityLeaveBalance(Employee employee, LeaveType leaveType, int year) {
        LeaveBalanceDTO leaveBalanceDTO = new LeaveBalanceDTO();
        LocalDate startDate = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, 31);

        if (employee.getPersonalInfo().getGender() == null || employee.getPersonalInfo().getGender().equals(GenderType.MALE)) {
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
        leaveBalanceDTO = new LeaveBalanceDTO(leaveType.getId(), leaveType.getTotalDays(),
            leaveType.getTotalDays().subtract(numberOfTotalAcceptedLeave), employee.getId(), employee.getName(),
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
        return new LeaveBalanceDTO(leaveType.getId(), leaveType.getTotalDays(),
            leaveType.getTotalDays().subtract(numberOfTotalAcceptedLeave), employee.getId(), employee.getName(),
            employee.getJoiningDate(), leaveType.getId(), leaveType.getName().name(), acceptedLeaveApplications);
    }

    private int getNumberOfYearsPassedFromJoiningDate(Employee employee, int year) {
        LocalDate startDate = employee.getJoiningDate();
        LocalDate endDate = LocalDate.of(year, startDate.getMonth(), startDate.getDayOfMonth());
        return Period.between(startDate, endDate).getYears();
    }
}
