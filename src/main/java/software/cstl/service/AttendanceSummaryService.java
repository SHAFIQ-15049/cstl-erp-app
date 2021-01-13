package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Attendance;
import software.cstl.service.dto.AttendanceSummaryDTO;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
     * @param fromDate the fromDate
     * @param toDate the toDate
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceSummaryDTO> findAll(Long employeeId, LocalDate fromDate, LocalDate toDate) {
        log.debug("Request to get all AttendanceSummaries {} {} {}", employeeId, fromDate, toDate);
        return null;
    }


    /**
     * Get all the attendanceSummaries.
     *
     * @param fromDate the fromDate
     * @param toDate the toDate
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceSummaryDTO> findAll(LocalDate fromDate, LocalDate toDate) {
        log.debug("Request to get all AttendanceSummaries {} {}", fromDate, toDate);
        List<Attendance> attendances = attendanceService.findAll(fromDate, toDate);
        List<AttendanceSummaryDTO> attendanceSummaryDTOs = new ArrayList<>();

        LocalDate startDate = fromDate;
        LocalDate endDate = toDate.plusDays(1);

        int counter = 0;

        while(startDate.isBefore(endDate)) {
            for (Attendance attendance : attendances) {
                if(startDate.equals(attendance.getAttendanceDate())) {
                    boolean flag = false;
                    for(AttendanceSummaryDTO attendanceSummaryDTO: attendanceSummaryDTOs) {
                        /*if(attendanceSummaryDTO.getEmployeeId().equals(attendance.getEmployee().getId()) && attendanceSummaryDTO.getAttendanceDate().equals(startDate)) {
                            if(attendanceSummaryDTO.getInTime().isAfter(attendance.getAttendanceTime())) {
                                attendanceSummaryDTO.setInTime(attendance.getAttendanceTime());
                            }
                            if(attendanceSummaryDTO.getInTime().isBefore(attendance.getAttendanceTime())) {
                                attendanceSummaryDTO.setOutTime(attendance.getAttendanceTime());
                            }
                            attendanceSummaryDTO.setDiff(Duration.between(attendance.getAttendanceTime(), attendance.getAttendanceTime()).toString());
                            attendanceSummaryDTO.setOverTime(Duration.between(attendance.getAttendanceTime(), attendance.getAttendanceTime()).toHours() > 8 ? Duration.between(attendance.getAttendanceTime(), attendance.getAttendanceTime()).minusHours(8).toString() : "N/A");
                            flag = true;
                            break;
                        }*/
                    }
                    if(!flag) {
                        counter++;
                        AttendanceSummaryDTO attendanceSummaryDTO = new AttendanceSummaryDTO();
                        attendanceSummaryDTO.setId((long) counter);
                        attendanceSummaryDTO.setEmployeeId(attendance.getEmployee().getId());
                        attendanceSummaryDTO.setEmployeeName(attendance.getEmployee().getName());
                        attendanceSummaryDTO.setEmployeeMachineId(attendance.getEmployee().getAttendanceMachineId());
                        attendanceSummaryDTO.setEmployeeSalaryId(attendance.getEmployeeSalary() == null ? null :
                            attendance.getEmployeeSalary().getId());
                        attendanceSummaryDTO.setAttendanceDate(attendance.getAttendanceDate());
                        //attendanceSummaryDTO.setInTime(attendance.getAttendanceTime());
                        attendanceSummaryDTO.setOutTime(null);
                        attendanceSummaryDTO.setDiff(Duration.between(attendance.getAttendanceTime(), attendance.getAttendanceTime()).toString());
                        attendanceSummaryDTO.setDiff(Duration.between(attendance.getAttendanceTime(), attendance.getAttendanceTime()).toString());
                        attendanceSummaryDTO.setOverTime(Duration.between(attendance.getAttendanceTime(), attendance.getAttendanceTime()).toHours() > 8 ? Duration.between(attendance.getAttendanceTime(), attendance.getAttendanceTime()).minusHours(8).toString() : "N/A");

                        attendanceSummaryDTOs.add(attendanceSummaryDTO);
                    }
                }
            }
            startDate = startDate.plusDays(1);
        }
        return attendanceSummaryDTOs;
    }

}
