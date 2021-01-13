package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Attendance;
import software.cstl.domain.AttendanceDataUpload;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.enumeration.ConsiderAsType;
import software.cstl.repository.AttendanceRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Attendance}.
 */
@Service
@Transactional
public class AttendanceService {

    private final Logger log = LoggerFactory.getLogger(AttendanceService.class);

    private final AttendanceRepository attendanceRepository;

    private final CommonService commonService;

    private final EmployeeService employeeService;

    private final EmployeeSalaryService employeeSalaryService;

    public AttendanceService(AttendanceRepository attendanceRepository, CommonService commonService,
                             EmployeeService employeeService, EmployeeSalaryService employeeSalaryService) {
        this.attendanceRepository = attendanceRepository;
        this.commonService = commonService;
        this.employeeService = employeeService;
        this.employeeSalaryService = employeeSalaryService;
    }

    /**
     * Save a attendance.
     *
     * @param attendance the entity to save.
     * @return the persisted entity.
     */
    public Attendance save(Attendance attendance) {
        log.debug("Request to save Attendance : {}", attendance);
        return attendanceRepository.save(attendance);
    }

    /**
     * Save a bulk attendance.
     *
     * @param attendances the entity to save.
     * @return the persisted entities.
     */
    public List<Attendance> saveAll(List<Attendance> attendances) {
        log.debug("Request to save Attendances : {}", attendances);
        return attendanceRepository.saveAll(attendances);
    }

    /**
     * Get all the attendances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Attendance> findAll(Pageable pageable) {
        log.debug("Request to get all Attendances");
        return attendanceRepository.findAll(pageable);
    }


    /**
     * Get one attendance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Attendance> findOne(Long id) {
        log.debug("Request to get Attendance : {}", id);
        return attendanceRepository.findById(id);
    }

    /**
     * Delete the attendance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Attendance : {}", id);
        attendanceRepository.deleteById(id);
    }

    /**
     * Get all the attendances.
     *
     * @param employee the employee.
     * @param fromDate the fromDate.
     * @param toDate the toDate.
     * @return the list of entities.
     */
    public List<Attendance> findAll(Employee employee, LocalDate fromDate, LocalDate toDate) {
        return attendanceRepository.getALlByEmployeeEqualsAndAttendanceDateIsGreaterThanEqualAndAttendanceDateIsLessThanEqual(employee, fromDate, toDate);
    }

    /**
     * Get all the attendances.
     *
     * @param fromDate the fromDate.
     * @param toDate the toDate.
     * @return the list of entities.
     */
    public List<Attendance> findAll(LocalDate fromDate, LocalDate toDate) {
        return attendanceRepository.getAllByAttendanceDateIsGreaterThanEqualAndAttendanceDateIsLessThanEqual(fromDate, toDate);
    }

    /**
     * Save bulk attendance from TXT file.
     *
     * @param attendanceDataUpload the entity where attendance data needs to process and then save.
     */
    public void bulkSave(AttendanceDataUpload attendanceDataUpload) {
        List<Employee> employees = employeeService.getAll();
        List<EmployeeSalary> employeeSalaries = employeeSalaryService.getAllByActiveStatus();

        String fileContents = commonService.getByteArrayToString(attendanceDataUpload.getFileUpload());
        String[] lines = commonService.getStringArrayBySeparatingStringContentUsingSeparator(fileContents, "\r\n");

        List<Attendance> attendances = new ArrayList<>();

        for (String line : lines) {
            Attendance attendance = splitAndValidateContentAndPrepareAttendanceData(attendanceDataUpload, employees, employeeSalaries, line);
            if(attendance != null) {
                attendances.add(attendance);
            }
        }
        saveAll(attendances);
    }

    private Attendance splitAndValidateContentAndPrepareAttendanceData(AttendanceDataUpload attendanceDataUpload, List<Employee> employees, List<EmployeeSalary> employeeSalaries, String line) {
        EmployeeSalary candidateSalary = null;
        Employee candidate = null;
        String[] data = commonService.getStringArrayBySeparatingStringContentUsingSeparator(line, ",");
        String machineCode = data[0];
        String employeeMachineId = data[1];
        String attendanceDate = data[2];
        String attendanceTime = data[3];
        candidate = validateAttendanceMachineIdWithEmployeeRecord(employees, employeeMachineId);
        if(candidate != null) {
            candidateSalary = getEmployeeSalary(employeeSalaries, candidate);

            String year = attendanceDate.substring(0, 4);
            String month = attendanceDate.substring(4, 6);
            String day = attendanceDate.substring(6, 8);

            String hour = attendanceTime.substring(0, 2);
            String minute = attendanceTime.substring(2, 4);
            String second = attendanceTime.substring(4, 6);
            String instantText = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second + ".00Z";

            LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            Instant instant = Instant.parse(instantText);
            return getAttendance(attendanceDataUpload, candidateSalary, candidate, machineCode, localDate, instant);
        }
        else {
            log.debug("ERROR! Missing employee or employee salary information {} ", line);
            return null;
        }
    }

    private Attendance getAttendance(AttendanceDataUpload attendanceDataUpload, EmployeeSalary candidateSalary, Employee candidate, String machineCode, LocalDate localDate, Instant instant) {
        Attendance attendance = new Attendance();
        attendance.setMachineNo(machineCode);
        attendance.setAttendanceDate(localDate.plusDays(1));
        attendance.setAttendanceTime(instant);
        attendance.setEmployee(candidate);
        attendance.setEmployeeSalary(candidateSalary);
        attendance.setConsiderAs(ConsiderAsType.REGULAR);
        attendance.setAttendanceDataUpload(attendanceDataUpload);
        attendance.setInOrOutTime(instant.atZone(ZoneId.of("Asia/Dhaka")));
        return attendance;
    }

    private EmployeeSalary getEmployeeSalary(List<EmployeeSalary> employeeSalaries, Employee candidate) {
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            if (employeeSalary.getEmployee().equals(candidate)) {
                return employeeSalary;
            }
        }
        return null;
    }

    private Employee validateAttendanceMachineIdWithEmployeeRecord(List<Employee> employees, String employeeMachineId) {
        for(Employee employee: employees) {
            if(employee.getAttendanceMachineId().equals(employeeMachineId)) {
                return employee;
            }
        }
        return null;
    }
}
