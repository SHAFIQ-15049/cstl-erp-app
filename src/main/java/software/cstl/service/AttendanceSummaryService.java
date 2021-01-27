package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Attendance;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.service.dto.AttendanceSummaryDTO;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
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

    public AttendanceSummaryService(AttendanceService attendanceService, EmployeeService employeeService) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    @Transactional
    public List<AttendanceSummaryDTO> update(List<AttendanceSummaryDTO> attendanceSummaryDTOs) {
        log.debug("Request to update Attendance Summaries : {}", attendanceSummaryDTOs);
        for(AttendanceSummaryDTO attendanceSummaryDTO: attendanceSummaryDTOs) {
            Optional<Employee> employee = employeeService.findOne(attendanceSummaryDTO.getEmployeeId());
            if(employee.isPresent()) {
                List<Attendance> attendances = attendanceService.findAll(employee.get(), attendanceSummaryDTO.getInTime(), attendanceSummaryDTO.getOutTime());
                for(Attendance attendance: attendances) {
                    attendance.setMarkedAs(attendanceSummaryDTO.getAttendanceMarkedAs());
                    attendanceService.save(attendance);
                }
            }
        }
        return attendanceSummaryDTOs;
    }

    /**
     * Get all the attendanceSummaries.
     *
     * @param employeeId the employee
     * @param fromDate   the fromDate
     * @param toDate     the toDate
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceSummaryDTO> findAll(Long employeeId, LocalDate fromDate, LocalDate toDate, AttendanceMarkedAs attendanceMarkedAs) {
        log.debug("Request to get all AttendanceSummaries {} {} {} {}", employeeId, fromDate, toDate, attendanceMarkedAs);
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = findAll(fromDate, toDate);
        List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificEmployee = new ArrayList<>();
        List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificEmployeeAndMarkedAs = new ArrayList<>();

        if(employeeId != -1) {
            for (AttendanceSummaryDTO attendanceSummaryDTO : attendanceSummaryDTOs) {
                if (attendanceSummaryDTO.getEmployeeId().equals(employeeId)) {
                    attendanceSummaryDTOsSpecificEmployee.add(attendanceSummaryDTO);
                }
            }
        }
        else {
            attendanceSummaryDTOsSpecificEmployee = attendanceSummaryDTOs;
        }

        if(attendanceMarkedAs != null) {
            for(AttendanceSummaryDTO attendanceSummaryDTO: attendanceSummaryDTOsSpecificEmployee) {
                if(attendanceSummaryDTO.getAttendanceMarkedAs().equals(attendanceMarkedAs)) {
                    attendanceSummaryDTOsSpecificEmployeeAndMarkedAs.add(attendanceSummaryDTO);
                }
            }
        }
        else {
            attendanceSummaryDTOsSpecificEmployeeAndMarkedAs = attendanceSummaryDTOsSpecificEmployee;
        }

        return attendanceSummaryDTOsSpecificEmployeeAndMarkedAs;
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
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = new ArrayList<>();

        while (from.isBefore(to)) {

            Instant start = from;
            Instant end = from.plusSeconds(86400);

            List<Attendance> attendancesByDateTime
                = getDayWiseAttendances(attendances, start, end);

            for (Attendance attendance : attendancesByDateTime) {
                List<Attendance> attendancesByEmployeeAndDateTime = getDayEmployeeWiseAttendances(attendancesByDateTime, attendance.getEmployee().getId());
                Instant inTime = getInTimeFromListOfDayEmployeeWiseAttendances(attendance, attendancesByEmployeeAndDateTime);
                Instant outTime = getOutTimeFromListOfDayEmployeeWiseAttendances(attendance, attendancesByEmployeeAndDateTime);

                AttendanceSummaryDTO attendanceSummaryDTO = getAttendanceSummaryDTO(attendance, inTime, outTime);
                attendanceSummaryDTOs.add(attendanceSummaryDTO);
            }

            from = from.plusSeconds(86400);
        }

        List<AttendanceSummaryDTO> distinctAttendances = attendanceSummaryDTOs.stream().distinct().collect(Collectors.toList());

        return addSerial(distinctAttendances);
    }

    private List<AttendanceSummaryDTO> addSerial(List<AttendanceSummaryDTO> removeDuplicates) {
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = new ArrayList<>();
        int serial = 0;
        for(AttendanceSummaryDTO attendanceSummaryDTO: removeDuplicates) {
            serial++;
            attendanceSummaryDTO.setSerialNo(Long.parseLong(serial + ""));
            attendanceSummaryDTOs.add(attendanceSummaryDTO);
        }

        return attendanceSummaryDTOs;
    }

    private AttendanceSummaryDTO getAttendanceSummaryDTO(Attendance attendance, Instant inTime, Instant outTime) {
        AttendanceSummaryDTO attendanceSummaryDTO = new AttendanceSummaryDTO();
        attendanceSummaryDTO.setEmployeeId(attendance.getEmployee().getId());
        attendanceSummaryDTO.setEmployeeName(attendance.getEmployee().getName());
        attendanceSummaryDTO.setEmployeeMachineId(attendance.getEmployee().getAttendanceMachineId());
        attendanceSummaryDTO.setEmployeeSalaryId(attendance.getEmployeeSalary() == null ? null :
            attendance.getEmployeeSalary().getId());
        attendanceSummaryDTO.setInTime(inTime);
        attendanceSummaryDTO.setOutTime(outTime);
        attendanceSummaryDTO.setDiff(Duration.between(inTime, outTime));
        attendanceSummaryDTO.setOverTime(Duration.between(inTime, outTime).toHours() > 8 ? Duration.between(inTime, outTime).minusHours(8) : Duration.ZERO);
        attendanceSummaryDTO.setAttendanceMarkedAs(attendance.getMarkedAs());
        return attendanceSummaryDTO;
    }

    private Instant getOutTimeFromListOfDayEmployeeWiseAttendances(Attendance attendance, List<Attendance> dayEmployeeWiseAttendances) {
        Instant outTime = attendance.getAttendanceTime();
        for(Attendance a: dayEmployeeWiseAttendances) {
            if(outTime.isBefore(a.getAttendanceTime())) {
                outTime = a.getAttendanceTime();
            }
        }
        return outTime;
    }

    private Instant getInTimeFromListOfDayEmployeeWiseAttendances(Attendance attendance, List<Attendance> dayEmployeeWiseAttendances) {
        Instant inTime = attendance.getAttendanceTime();
        for(Attendance a: dayEmployeeWiseAttendances) {
            if(inTime.isAfter(a.getAttendanceTime())) {
                inTime = a.getAttendanceTime();
            }
        }
        return inTime;
    }

    private List<Attendance> getDayEmployeeWiseAttendances(List<Attendance> dayWiseAttendances, Long employeeId) {
        List<Attendance> dayEmployeeWiseAttendances = new ArrayList<>();
        for(Attendance a: dayWiseAttendances) {
            if(a.getEmployee().getId().equals(employeeId)) {
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

}
