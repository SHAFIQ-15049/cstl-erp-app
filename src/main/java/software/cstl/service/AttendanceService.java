package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Attendance;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.domain.enumeration.LeaveAppliedStatus;
import software.cstl.repository.AttendanceRepository;
import software.cstl.service.dto.AttendanceDataUploadDTO;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    public List<Attendance> save(List<Attendance> attendances) {
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
     * @param from     the fromDateTime.
     * @param to       the toDateTime.
     * @return the list of entities.
     */
    public List<Attendance> findAll(Employee employee, Instant from, Instant to) {
        return attendanceRepository.findAllByEmployeeAndAttendanceTimeBetween(employee, from, to);
    }

    /**
     * Get all the attendances.
     *
     * @param from the fromDateTime.
     * @param to   the toDateTime.
     * @return the list of entities.
     */
    public List<Attendance> findAll(Instant from, Instant to) {
        return attendanceRepository.findAttendancesByAttendanceTimeGreaterThanEqualAndAttendanceTimeLessThanEqual(from, to);
    }

    /**
     * Get all the attendances.
     *
     * @param from               the fromDateTime.
     * @param to                 the toDateTime.
     * @param attendanceMarkedAs the markedAs
     * @return the list of entities.
     */
    public List<Attendance> findAll(Instant from, Instant to, AttendanceMarkedAs attendanceMarkedAs) {
        return attendanceRepository.findAllByMarkedAsAndAttendanceTimeBetween(attendanceMarkedAs, from, to);
    }

    /**
     * Save bulk attendance from TXT file.
     *
     * @param attendance the attendance data.
     */
    public Attendance manualSave(Attendance attendance) {
        log.debug("Request to save Attendance : {}", attendance);
        List<EmployeeSalary> employeeSalaries = employeeSalaryService.getAllByActiveStatus();
        Attendance result = build(employeeSalaries, attendance);
        return save(result);
    }

    private Attendance build(List<EmployeeSalary> employeeSalaries, Attendance attendance) {
        EmployeeSalary employeeSalary = findActiveEmployeeSalary(employeeSalaries, attendance.getEmployee());
        return build(employeeSalary, attendance.getEmployee().getAttendanceMachineId(),
            attendance.getEmployee(), attendance.getMachineNo(), attendance.getAttendanceTime());
    }

    /**
     * Save bulk attendance from TXT file.
     *
     * @param attendanceDataUploadDTO the entity where attendance data needs to process and then save.
     */
    public List<Attendance> bulkSave(AttendanceDataUploadDTO attendanceDataUploadDTO) {
        List<Employee> employees = employeeService.findAll();
        List<EmployeeSalary> employeeSalaries = employeeSalaryService.getAllByActiveStatus();

        String fileContents = commonService.getByteArrayToString(attendanceDataUploadDTO.getFileUpload());
        String[] lines = commonService.getStringArrayBySeparatingStringContentUsingSeparator(fileContents, "\r\n");

        List<Attendance> attendances = new ArrayList<>();

        for (String line : lines) {
            Attendance attendance = splitAndValidateContentAndBuildAttendanceData(employees, employeeSalaries, line);
            if (attendance != null) {
                attendances.add(attendance);
            }
        }
        return save(attendances);
    }

    private Attendance splitAndValidateContentAndBuildAttendanceData(List<Employee> employees, List<EmployeeSalary> employeeSalaries, String line) {
        EmployeeSalary candidateSalary;
        Employee employee;

        String[] data = commonService.getStringArrayBySeparatingStringContentUsingSeparator(line, ",");
        String machineCode = data[0];
        String employeeMachineId = data[1];
        String attendanceDate = data[2];
        String attendanceTime = data[3];

        employee = validateEmployeeAttendanceMachineIdWithEmployeeRecord(employees, employeeMachineId);

        if (employee != null) {
            candidateSalary = findActiveEmployeeSalary(employeeSalaries, employee);

            String year = attendanceDate.substring(0, 4);
            String month = attendanceDate.substring(4, 6);
            String day = attendanceDate.substring(6, 8);

            String hour = attendanceTime.substring(0, 2);
            String minute = attendanceTime.substring(2, 4);
            String second = attendanceTime.substring(4, 6);
            String instantText = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second + ".00Z";

            Instant instant = Instant.parse(instantText);
            ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(instant);
            instant = instant.minusSeconds(zoneOffset.getTotalSeconds());

            return build(candidateSalary, employeeMachineId, employee, machineCode, instant);

        } else {
            log.debug("ERROR! Employee or employee salary information is missing {} ", line);
            return null;
        }
    }

    private Attendance build(EmployeeSalary employeeSalary, String employeeMachineId, Employee employee, String machineCode, Instant instant) {
        Attendance attendance = new Attendance();
        attendance.setEmployeeMachineId(employeeMachineId);
        attendance.setMachineNo(machineCode);
        attendance.setAttendanceTime(instant);
        attendance.setEmployee(employee);
        attendance.setDepartment(employee.getDepartment());
        attendance.setDesignation(employee.getDesignation());
        attendance.setEmployeeCategory(employee.getCategory());
        attendance.setEmployeeType(employee.getType());
        attendance.setGrade(employee.getGrade());
        attendance.setLine(employee.getLine());
        attendance.setEmployeeSalary(employeeSalary);
        attendance.setMarkedAs(AttendanceMarkedAs.R);
        attendance.setLeaveApplied(LeaveAppliedStatus.NO);
        return attendance;
    }

    private EmployeeSalary findActiveEmployeeSalary(List<EmployeeSalary> employeeSalaries, Employee employee) {
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            if (employeeSalary.getEmployee().equals(employee) && employeeSalary.getStatus().equals(ActiveStatus.ACTIVE)) {
                return employeeSalary;
            }
        }
        return null;
    }

    private Employee validateEmployeeAttendanceMachineIdWithEmployeeRecord(List<Employee> employees, String employeeMachineId) {
        for (Employee employee : employees) {
            if(employee.getAttendanceMachineId() != null) {
                if (employee.getAttendanceMachineId().equals(employeeMachineId)) {
                    return employee;
                }
            }
        }
        return null;
    }
}
