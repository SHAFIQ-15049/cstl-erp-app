package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Attendance;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.LeaveAppliedStatus;
import software.cstl.service.dto.AttendanceSummaryDTO;

import java.io.ByteArrayInputStream;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing {@link AttendanceSummaryDTO}.
 */
@Service
@Transactional
public class AttendanceSummaryService {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryService.class);

    private final AttendanceService attendanceService;

    private final EmployeeService employeeService;

    private final EmployeeSalaryService employeeSalaryService;

    public AttendanceSummaryService(AttendanceService attendanceService, EmployeeService employeeService, EmployeeSalaryService employeeSalaryService) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
        this.employeeSalaryService = employeeSalaryService;
    }

    @Transactional
    public List<AttendanceSummaryDTO> update(List<AttendanceSummaryDTO> attendanceSummaryDTOs) {
        log.debug("Request to update Attendance Summaries : {}", attendanceSummaryDTOs);
        for (AttendanceSummaryDTO attendanceSummaryDTO : attendanceSummaryDTOs) {
            Optional<Employee> employee = employeeService.findOne(attendanceSummaryDTO.getEmployeeId());
            if (employee.isPresent() && employee.get().getStatus().equals(EmployeeStatus.ACTIVE) &&
                attendanceSummaryDTO.getInTime() != null && attendanceSummaryDTO.getOutTime() != null) {
                List<Attendance> attendances = attendanceService.findAll(employee.get(), attendanceSummaryDTO.getInTime(), attendanceSummaryDTO.getOutTime());
                for (Attendance attendance : attendances) {
                    attendance.setMarkedAs(attendanceSummaryDTO.getAttendanceMarkedAs());
                    attendanceService.save(attendance);
                }
            }
        }
        return attendanceSummaryDTOs;
    }

    @Transactional(readOnly = true)
    public List<AttendanceSummaryDTO> findAllWhoWillGetDutyLeave() {
        log.debug("Request to get all AttendanceSummaries");
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = findAll(LocalDate.of(1995, Month.JANUARY, 1), LocalDate.now());
        List<AttendanceSummaryDTO> attendanceSummaryDTOsWhoWillGetDutyLeave = new ArrayList<>();
        for (AttendanceSummaryDTO attendanceSummaryDTO : attendanceSummaryDTOs) {
            if ((attendanceSummaryDTO.getAttendanceMarkedAs().equals(AttendanceMarkedAs.WR) || attendanceSummaryDTO.getAttendanceMarkedAs().equals(AttendanceMarkedAs.HR)) && (attendanceSummaryDTO.getLeaveAppliedStatus() == null || attendanceSummaryDTO.getLeaveAppliedStatus().equals(LeaveAppliedStatus.NO))) {
                attendanceSummaryDTOsWhoWillGetDutyLeave.add(attendanceSummaryDTO);
            }
        }
        return attendanceSummaryDTOsWhoWillGetDutyLeave;
    }

    @Transactional(readOnly = true)
    public List<AttendanceSummaryDTO> findAll(Long departmentId, String empId, Long employeeId, LocalDate fromDate, LocalDate toDate, AttendanceMarkedAs attendanceMarkedAs) {
        log.debug("Request to get all AttendanceSummaries {} {} {} {}", employeeId, fromDate, toDate, attendanceMarkedAs);
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = findAll(fromDate, toDate);
        List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificEmpId = filterByEmpId(empId, attendanceSummaryDTOs);
        List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificDepartment = filterByDepartment(departmentId, attendanceSummaryDTOsSpecificEmpId);
        List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificEmployee = filterByEmployee(employeeId, attendanceSummaryDTOsSpecificDepartment);
        return filterByEmployeeAndMarkedAs(attendanceMarkedAs, attendanceSummaryDTOsSpecificEmployee);
    }

    @Transactional(readOnly = true)
    public List<AttendanceSummaryDTO> findAll(Long employeeId, LocalDate fromDate, LocalDate toDate) {
        log.debug("Request to get all AttendanceSummaries {} {} {}", employeeId, fromDate, toDate);
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = findAll(fromDate, toDate);
        return filterByEmployee(employeeId, attendanceSummaryDTOs);
    }

    private List<AttendanceSummaryDTO> filterByEmployeeAndMarkedAs(AttendanceMarkedAs attendanceMarkedAs, List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificEmployee) {
        List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificEmployeeAndMarkedAs = new ArrayList<>();
        if (attendanceMarkedAs != null) {
            for (AttendanceSummaryDTO attendanceSummaryDTO : attendanceSummaryDTOsSpecificEmployee) {
                if (attendanceSummaryDTO.getAttendanceMarkedAs().equals(attendanceMarkedAs)) {
                    attendanceSummaryDTOsSpecificEmployeeAndMarkedAs.add(attendanceSummaryDTO);
                }
            }
        } else {
            attendanceSummaryDTOsSpecificEmployeeAndMarkedAs = attendanceSummaryDTOsSpecificEmployee;
        }
        return attendanceSummaryDTOsSpecificEmployeeAndMarkedAs;
    }

    private List<AttendanceSummaryDTO> filterByEmpId(String empId, List<AttendanceSummaryDTO> attendanceSummaryDTOs) {
        List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificEmpId = new ArrayList<>();
        if (!empId.equals("-1")) {
            for (AttendanceSummaryDTO attendanceSummaryDTO : attendanceSummaryDTOs) {
                if (attendanceSummaryDTO.getEmpId().equals(empId)) {
                    attendanceSummaryDTOsSpecificEmpId.add(attendanceSummaryDTO);
                }
            }
        } else {
            attendanceSummaryDTOsSpecificEmpId = attendanceSummaryDTOs;
        }
        return attendanceSummaryDTOsSpecificEmpId;
    }

    private List<AttendanceSummaryDTO> filterByDepartment(Long departmentId, List<AttendanceSummaryDTO> attendanceSummaryDTOs) {
        List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificDepartment = new ArrayList<>();
        if (departmentId != -1) {
            for (AttendanceSummaryDTO attendanceSummaryDTO : attendanceSummaryDTOs) {
                if (attendanceSummaryDTO.getDepartmentId().equals(departmentId)) {
                    attendanceSummaryDTOsSpecificDepartment.add(attendanceSummaryDTO);
                }
            }
        } else {
            attendanceSummaryDTOsSpecificDepartment = attendanceSummaryDTOs;
        }
        return attendanceSummaryDTOsSpecificDepartment;
    }

    private List<AttendanceSummaryDTO> filterByEmployee(Long employeeId, List<AttendanceSummaryDTO> attendanceSummaryDTOs) {
        List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificEmployee = new ArrayList<>();
        if (employeeId != -1) {
            for (AttendanceSummaryDTO attendanceSummaryDTO : attendanceSummaryDTOs) {
                if (attendanceSummaryDTO.getEmployeeId().equals(employeeId)) {
                    attendanceSummaryDTOsSpecificEmployee.add(attendanceSummaryDTO);
                }
            }
        } else {
            attendanceSummaryDTOsSpecificEmployee = attendanceSummaryDTOs;
        }
        return attendanceSummaryDTOsSpecificEmployee;
    }

    /**
     * Get all the attendanceSummaries.
     *
     * @param fromDate the fromDate
     * @param toDate   the toDate
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceSummaryDTO> findAll(LocalDate fromDate, LocalDate toDate) {
        log.debug("Request to get all AttendanceSummaries {} {}", fromDate, toDate);

        LocalDate startDate = fromDate;
        LocalDate endDate = toDate.plusDays(1);

        int fromYear = startDate.getYear();
        int fromMonth = startDate.getMonthValue();
        int fromDay = startDate.getDayOfMonth();

        int toYear = endDate.getYear();
        int toMonth = endDate.getMonthValue();
        int toDay = endDate.getDayOfMonth();

        String fromDayString = fromDay < 10 ? "0" + fromDay : "" + fromDay;
        String toDayString = toDay < 10 ? "0" + toDay : "" + toDay;

        String fromMonthString = fromMonth < 10 ? "0" + fromMonth : "" + fromMonth;
        String toMonthString = toMonth < 10 ? "0" + toMonth : "" + toMonth;

        String fromInstantText = fromYear + "-" + fromMonthString + "-" + fromDayString + "T06:00:00.00Z";
        String toInstantText = toYear + "-" + toMonthString + "-" + toDayString + "T06:00:00.00Z";

        Instant from = Instant.parse(fromInstantText);
        Instant to = Instant.parse(toInstantText);

        List<Attendance> attendances = attendanceService.findAll(from, to);
        List<Employee> employees = employeeService.getAll();
        List<EmployeeSalary> employeeSalaries = employeeSalaryService.getAll();
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = new ArrayList<>();
        List<AttendanceSummaryDTO> distinctAttendances = new ArrayList<>();
        List<AttendanceSummaryDTO> totalAttendanceSummary = new ArrayList<>();

        while (from.isBefore(to)) {

            Instant start = from;
            Instant end = from.plusSeconds(86400);

            List<Attendance> attendancesByDateTime
                = getDayWiseAttendances(attendances, start, end);

            for (Attendance attendance : attendancesByDateTime) {
                List<Attendance> attendancesByEmployeeAndDateTime = getDayEmployeeWiseAttendances(attendancesByDateTime, attendance.getEmployee().getId());
                Instant inTime = getInTimeFromListOfDayEmployeeWiseAttendances(attendance, attendancesByEmployeeAndDateTime);
                Instant outTime = getOutTimeFromListOfDayEmployeeWiseAttendances(attendance, attendancesByEmployeeAndDateTime);

                AttendanceSummaryDTO attendanceSummaryDTO = getAttendanceSummaryDTO(attendance, inTime, outTime, from.atZone(ZoneId.systemDefault()).toLocalDate());
                attendanceSummaryDTOs.add(attendanceSummaryDTO);
            }

            from = from.plusSeconds(86400);

            distinctAttendances = attendanceSummaryDTOs.stream().distinct().collect(Collectors.toList());
            totalAttendanceSummary.addAll(getTotalAttendanceSummary(employees, employeeSalaries, distinctAttendances, from.atZone(ZoneId.systemDefault()).toLocalDate()));
        }
        return addSerial(totalAttendanceSummary);
    }

    private List<AttendanceSummaryDTO> getTotalAttendanceSummary(List<Employee> employees, List<EmployeeSalary> employeeSalaries, List<AttendanceSummaryDTO> distinctAttendances, LocalDate searchingDate) {
        List<AttendanceSummaryDTO> totalAttendanceSummary = new ArrayList<>();
        for (Employee employee : employees) {
            boolean found = false;
            AttendanceSummaryDTO summaryDTO = null;
            for (AttendanceSummaryDTO attendanceSummaryDTO : distinctAttendances) {
                if (employee.getAttendanceMachineId().equals(attendanceSummaryDTO.getEmployeeMachineId()) && attendanceSummaryDTO.getAttendanceDate().equals(searchingDate)) {
                    found = true;
                    summaryDTO = attendanceSummaryDTO;
                    break;
                }
            }
            if(found) {
                totalAttendanceSummary.add(summaryDTO);
            }
            else {
                AttendanceSummaryDTO attendanceSummaryDTO = new AttendanceSummaryDTO();
                attendanceSummaryDTO.setDepartmentId(employee.getDepartment().getId());
                attendanceSummaryDTO.setDepartmentName(employee.getDepartment().getName());
                attendanceSummaryDTO.setEmployeeId(employee.getId());
                attendanceSummaryDTO.setEmpId(employee.getEmpId());
                attendanceSummaryDTO.setEmployeeName(employee.getName());
                attendanceSummaryDTO.setEmployeeMachineId(employee.getAttendanceMachineId());
                attendanceSummaryDTO.setAttendanceDate(searchingDate);
                EmployeeSalary employeeSalary = getEmployeeSalary(employeeSalaries, employee);
                attendanceSummaryDTO.setEmployeeSalaryId(employeeSalary == null ? null : employeeSalary.getId());
                attendanceSummaryDTO.setInTime(null);
                attendanceSummaryDTO.setOutTime(null);
                attendanceSummaryDTO.setDiff(Duration.ZERO);
                attendanceSummaryDTO.setOverTime(Duration.ZERO);
                attendanceSummaryDTO.setAttendanceMarkedAs(null);
                attendanceSummaryDTO.setLeaveAppliedStatus(LeaveAppliedStatus.NO);
                attendanceSummaryDTO.setAttendanceStatus("Absent");
                totalAttendanceSummary.add(attendanceSummaryDTO);
            }
        }
        return totalAttendanceSummary;
    }

    private List<AttendanceSummaryDTO> addSerial(List<AttendanceSummaryDTO> removeDuplicates) {
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = new ArrayList<>();
        int serial = 0;
        for (AttendanceSummaryDTO attendanceSummaryDTO : removeDuplicates) {
            serial++;
            attendanceSummaryDTO.setSerialNo(Long.parseLong(serial + ""));
            attendanceSummaryDTOs.add(attendanceSummaryDTO);
        }

        return attendanceSummaryDTOs;
    }

    private AttendanceSummaryDTO getAttendanceSummaryDTO(Attendance attendance, Instant inTime, Instant outTime, LocalDate searchingDate) {
        AttendanceSummaryDTO attendanceSummaryDTO = new AttendanceSummaryDTO();
        attendanceSummaryDTO.setDepartmentId(attendance.getDepartment().getId());
        attendanceSummaryDTO.setDepartmentName(attendance.getDepartment().getName());
        attendanceSummaryDTO.setEmployeeId(attendance.getEmployee().getId());
        attendanceSummaryDTO.setEmployeeName(attendance.getEmployee().getName());
        attendanceSummaryDTO.setEmpId(attendance.getEmployee().getEmpId());
        attendanceSummaryDTO.setEmployeeMachineId(attendance.getEmployee().getAttendanceMachineId());
        attendanceSummaryDTO.setEmployeeSalaryId(attendance.getEmployeeSalary() == null ? null :
            attendance.getEmployeeSalary().getId());
        attendanceSummaryDTO.setAttendanceDate(searchingDate);
        attendanceSummaryDTO.setInTime(inTime);
        attendanceSummaryDTO.setOutTime(inTime.equals(outTime) ? null : outTime);
        if (attendance.getMarkedAs().equals(AttendanceMarkedAs.WO) || attendance.getMarkedAs().equals(AttendanceMarkedAs.HO)) {
            attendanceSummaryDTO.setDiff(Duration.ZERO);
            attendanceSummaryDTO.setOverTime(Duration.between(inTime, outTime));
        } else {
            attendanceSummaryDTO.setDiff(Duration.between(inTime, outTime));
            attendanceSummaryDTO.setOverTime(Duration.between(inTime, outTime).toHours() > 8 ? Duration.between(inTime, outTime).minusHours(8) : Duration.ZERO);
        }
        attendanceSummaryDTO.setAttendanceMarkedAs(attendance.getMarkedAs());
        attendanceSummaryDTO.setLeaveAppliedStatus(attendance.getLeaveApplied());
        if (attendanceSummaryDTO.getInTime() != null || attendanceSummaryDTO.getOutTime() != null) {
            attendanceSummaryDTO.setAttendanceStatus("Present");
        } else {
            attendanceSummaryDTO.setAttendanceStatus("Absent");
        }
        return attendanceSummaryDTO;
    }

    private Instant getOutTimeFromListOfDayEmployeeWiseAttendances(Attendance attendance, List<Attendance> dayEmployeeWiseAttendances) {
        Instant outTime = attendance.getAttendanceTime();
        for (Attendance a : dayEmployeeWiseAttendances) {
            if (outTime.isBefore(a.getAttendanceTime())) {
                outTime = a.getAttendanceTime();
            }
        }
        return outTime;
    }

    private Instant getInTimeFromListOfDayEmployeeWiseAttendances(Attendance attendance, List<Attendance> dayEmployeeWiseAttendances) {
        Instant inTime = attendance.getAttendanceTime();
        for (Attendance a : dayEmployeeWiseAttendances) {
            if (inTime.isAfter(a.getAttendanceTime())) {
                inTime = a.getAttendanceTime();
            }
        }
        return inTime;
    }

    private List<Attendance> getDayEmployeeWiseAttendances(List<Attendance> dayWiseAttendances, Long employeeId) {
        List<Attendance> dayEmployeeWiseAttendances = new ArrayList<>();
        for (Attendance a : dayWiseAttendances) {
            if (a.getEmployee().getId().equals(employeeId)) {
                dayEmployeeWiseAttendances.add(a);
            }
        }
        return dayEmployeeWiseAttendances;
    }

    private List<Attendance> getDayWiseAttendances(List<Attendance> attendances, Instant dayWiseFrom, Instant dayWiseTo) {
        List<Attendance> dayWiseAttendances = new ArrayList<>();
        for (Attendance attendance : attendances) {
            if (attendance.getAttendanceTime().isAfter(dayWiseFrom) && attendance.getAttendanceTime().isBefore(dayWiseTo)) {
                dayWiseAttendances.add(attendance);
            }
        }
        return dayWiseAttendances;
    }

    private EmployeeSalary getEmployeeSalary(List<EmployeeSalary> employeeSalaries, Employee employee) {
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            if (employeeSalary.getEmployee().equals(employee) && employeeSalary.getStatus().equals(ActiveStatus.ACTIVE)) {
                return employeeSalary;
            }
        }
        return null;
    }
}
