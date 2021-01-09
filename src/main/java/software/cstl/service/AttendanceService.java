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

    public void bulkSave(AttendanceDataUpload attendanceDataUpload) {
        Employee candidate = null;
        EmployeeSalary candidateSalary = null;

        List<Employee> employees = employeeService.getAll();
        List<EmployeeSalary> employeeSalaries = employeeSalaryService.getAllByActiveStatus();

        String fileContents = commonService.getByteArrayToString(attendanceDataUpload.getFileUpload());
        String[] lines = commonService.getStringArrayBySeparatingStringContentUsingSeparator(fileContents, "\r\n");

        List<Attendance> attendances = new ArrayList<>();

        for (String line : lines) {
            candidate = null;
            candidateSalary = null;
            String[] data = commonService.getStringArrayBySeparatingStringContentUsingSeparator(line, ",");
            String machineCode = data[0];
            String employeeMachineId = data[1];
            String attendanceDate = data[2];
            String attendanceTime = data[3];
            for(Employee employee: employees) {
                if(employee.getAttendanceMachineId().equals(data[1])) {
                    candidate = employee;
                    break;
                }
            }
            if(candidate != null) {
                for (EmployeeSalary employeeSalary : employeeSalaries) {
                    if (employeeSalary.getEmployee().equals(candidate)) {
                        candidateSalary = employeeSalary;
                        break;
                    }
                }
            }

            if(candidate != null) {
                Attendance attendance = new Attendance();
                String year = attendanceDate.substring(0, 4);
                String month = attendanceDate.substring(4, 6);
                String day = attendanceDate.substring(6, 8);

                String hour = attendanceTime.substring(0, 2);
                String minute = attendanceTime.substring(2, 4);
                String second = attendanceTime.substring(4, 6);
                // 2007-12-03T10:15:30.00Z
                String charText = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second + ".00Z";

                Instant instant = Instant.parse(charText);
                attendance.setMachineNo(machineCode);
                attendance.setAttendanceTime(instant);
                attendance.setAttendanceDate(instant.atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1));
                attendance.setAttendanceDataUpload(attendanceDataUpload);
                attendance.setEmployee(candidate);
                attendance.setEmployeeSalary(candidateSalary);
                attendance.setConsiderAs(ConsiderAsType.REGULAR);

                attendances.add(attendance);
            }
            else {
                log.debug("ERROR! Missing employee or employee salary information {} ", line);
            }
        }
        saveAll(attendances);
    }
}
