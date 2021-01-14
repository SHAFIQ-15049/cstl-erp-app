package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Attendance;
import software.cstl.service.dto.AttendanceSummaryDTO;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * Get all the attendanceSummaries.
     *
     * @param employeeId the employee
     * @param fromDate   the fromDate
     * @param toDate     the toDate
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceSummaryDTO> findAll(Long employeeId, LocalDate fromDate, LocalDate toDate) {
        log.debug("Request to get all AttendanceSummaries {} {} {}", employeeId, fromDate, toDate);
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = findAll(fromDate, toDate);
        List<AttendanceSummaryDTO> attendanceSummaryDTOsSpecificEmployee = new ArrayList<>();

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
        String toInstantText = toYear + "-" + toMonthString + "-" + toDayString + "T05:59:59.00Z";

        Instant from = Instant.parse(fromInstantText);
        Instant to = Instant.parse(toInstantText);

        List<Attendance> attendances = attendanceService.findAll(from, to);
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = new ArrayList<>();

        while (from.isBefore(to)) {

            Instant dayWiseFrom = from;
            Instant dayWiseTo = from.plusSeconds(86400);

            List<Attendance> dayWiseAttendances = new ArrayList<>();

            for (Attendance attendance : attendances) {
                if (attendance.getAttendanceTime().isAfter(dayWiseFrom) && attendance.getAttendanceTime().isBefore(dayWiseTo)) {
                    dayWiseAttendances.add(attendance);
                }
            }

            for (Attendance attendance : dayWiseAttendances) {
                AttendanceSummaryDTO attendanceSummaryDTO = new AttendanceSummaryDTO();
                attendanceSummaryDTO.setEmployeeId(attendance.getEmployee().getId());
                attendanceSummaryDTO.setEmployeeName(attendance.getEmployee().getName());
                attendanceSummaryDTO.setEmployeeMachineId(attendance.getEmployee().getAttendanceMachineId());
                attendanceSummaryDTO.setEmployeeSalaryId(attendance.getEmployeeSalary() == null ? null :
                    attendance.getEmployeeSalary().getId());

                List<Attendance> dayEmployeeWiseAttendances = new ArrayList<>();
                for(Attendance a: dayWiseAttendances) {
                    if(a.getEmployee().getId().equals(attendanceSummaryDTO.getEmployeeId())) {
                        dayEmployeeWiseAttendances.add(a);
                    }
                }

                Instant inTime = attendance.getAttendanceTime();
                for(Attendance a: dayEmployeeWiseAttendances) {
                    if(inTime.isAfter(a.getAttendanceTime())) {
                        inTime = a.getAttendanceTime();
                    }
                }

                Instant outTime = attendance.getAttendanceTime();
                for(Attendance a: dayEmployeeWiseAttendances) {
                    if(outTime.isBefore(a.getAttendanceTime())) {
                        outTime = a.getAttendanceTime();
                    }
                }

                attendanceSummaryDTO.setInTime(inTime);
                attendanceSummaryDTO.setOutTime(outTime);
                attendanceSummaryDTO.setDiff(Duration.between(inTime, outTime));
                attendanceSummaryDTO.setOverTime(Duration.between(inTime, outTime).toHours() > 8 ? Duration.between(inTime, outTime).minusHours(8) : Duration.ZERO);

                attendanceSummaryDTOs.add(attendanceSummaryDTO);
            }

            from = from.plusSeconds(86400);
        }

        List<AttendanceSummaryDTO> removeDuplicates = attendanceSummaryDTOs.stream().distinct().collect(Collectors.toList());

        int serial = 0;
        for(AttendanceSummaryDTO attendanceSummaryDTO: removeDuplicates) {
            serial++;
            attendanceSummaryDTO.setId(Long.parseLong(serial + ""));
        }

        return removeDuplicates;
    }

}
