package software.cstl.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Attendance;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.domain.enumeration.LeaveAppliedStatus;
import software.cstl.repository.AttendanceRepository;
import software.cstl.service.AttendanceQueryService;
import software.cstl.service.AttendanceService;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link AttendanceResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttendanceResourceIT {

    private static final Instant DEFAULT_ATTENDANCE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATTENDANCE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MACHINE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE_NO = "BBBBBBBBBB";

    private static final AttendanceMarkedAs DEFAULT_MARKED_AS = AttendanceMarkedAs.R;
    private static final AttendanceMarkedAs UPDATED_MARKED_AS = AttendanceMarkedAs.WR;

    private static final LeaveAppliedStatus DEFAULT_LEAVE_APPLIED = LeaveAppliedStatus.YES;
    private static final LeaveAppliedStatus UPDATED_LEAVE_APPLIED = LeaveAppliedStatus.NO;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceQueryService attendanceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendanceMockMvc;

    private Attendance attendance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendance createEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .attendanceTime(DEFAULT_ATTENDANCE_TIME)
            .machineNo(DEFAULT_MACHINE_NO)
            .markedAs(DEFAULT_MARKED_AS)
            .leaveApplied(DEFAULT_LEAVE_APPLIED);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        attendance.setEmployee(employee);
        return attendance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendance createUpdatedEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .attendanceTime(UPDATED_ATTENDANCE_TIME)
            .machineNo(UPDATED_MACHINE_NO)
            .markedAs(UPDATED_MARKED_AS)
            .leaveApplied(UPDATED_LEAVE_APPLIED);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        attendance.setEmployee(employee);
        return attendance;
    }

    @BeforeEach
    public void initTest() {
        attendance = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllAttendances() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].attendanceTime").value(hasItem(DEFAULT_ATTENDANCE_TIME.toString())))
            .andExpect(jsonPath("$.[*].machineNo").value(hasItem(DEFAULT_MACHINE_NO)))
            .andExpect(jsonPath("$.[*].markedAs").value(hasItem(DEFAULT_MARKED_AS.toString())))
            .andExpect(jsonPath("$.[*].leaveApplied").value(hasItem(DEFAULT_LEAVE_APPLIED.toString())));
    }

    @Test
    @Transactional
    public void getAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get the attendance
        restAttendanceMockMvc.perform(get("/api/attendances/{id}", attendance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendance.getId().intValue()))
            .andExpect(jsonPath("$.attendanceTime").value(DEFAULT_ATTENDANCE_TIME.toString()))
            .andExpect(jsonPath("$.machineNo").value(DEFAULT_MACHINE_NO))
            .andExpect(jsonPath("$.markedAs").value(DEFAULT_MARKED_AS.toString()))
            .andExpect(jsonPath("$.leaveApplied").value(DEFAULT_LEAVE_APPLIED.toString()));
    }


    @Test
    @Transactional
    public void getAttendancesByIdFiltering() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        Long id = attendance.getId();

        defaultAttendanceShouldBeFound("id.equals=" + id);
        defaultAttendanceShouldNotBeFound("id.notEquals=" + id);

        defaultAttendanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttendanceShouldNotBeFound("id.greaterThan=" + id);

        defaultAttendanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttendanceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAttendancesByAttendanceTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceTime equals to DEFAULT_ATTENDANCE_TIME
        defaultAttendanceShouldBeFound("attendanceTime.equals=" + DEFAULT_ATTENDANCE_TIME);

        // Get all the attendanceList where attendanceTime equals to UPDATED_ATTENDANCE_TIME
        defaultAttendanceShouldNotBeFound("attendanceTime.equals=" + UPDATED_ATTENDANCE_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceTime not equals to DEFAULT_ATTENDANCE_TIME
        defaultAttendanceShouldNotBeFound("attendanceTime.notEquals=" + DEFAULT_ATTENDANCE_TIME);

        // Get all the attendanceList where attendanceTime not equals to UPDATED_ATTENDANCE_TIME
        defaultAttendanceShouldBeFound("attendanceTime.notEquals=" + UPDATED_ATTENDANCE_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceTimeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceTime in DEFAULT_ATTENDANCE_TIME or UPDATED_ATTENDANCE_TIME
        defaultAttendanceShouldBeFound("attendanceTime.in=" + DEFAULT_ATTENDANCE_TIME + "," + UPDATED_ATTENDANCE_TIME);

        // Get all the attendanceList where attendanceTime equals to UPDATED_ATTENDANCE_TIME
        defaultAttendanceShouldNotBeFound("attendanceTime.in=" + UPDATED_ATTENDANCE_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceTime is not null
        defaultAttendanceShouldBeFound("attendanceTime.specified=true");

        // Get all the attendanceList where attendanceTime is null
        defaultAttendanceShouldNotBeFound("attendanceTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByMachineNoIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo equals to DEFAULT_MACHINE_NO
        defaultAttendanceShouldBeFound("machineNo.equals=" + DEFAULT_MACHINE_NO);

        // Get all the attendanceList where machineNo equals to UPDATED_MACHINE_NO
        defaultAttendanceShouldNotBeFound("machineNo.equals=" + UPDATED_MACHINE_NO);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMachineNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo not equals to DEFAULT_MACHINE_NO
        defaultAttendanceShouldNotBeFound("machineNo.notEquals=" + DEFAULT_MACHINE_NO);

        // Get all the attendanceList where machineNo not equals to UPDATED_MACHINE_NO
        defaultAttendanceShouldBeFound("machineNo.notEquals=" + UPDATED_MACHINE_NO);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMachineNoIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo in DEFAULT_MACHINE_NO or UPDATED_MACHINE_NO
        defaultAttendanceShouldBeFound("machineNo.in=" + DEFAULT_MACHINE_NO + "," + UPDATED_MACHINE_NO);

        // Get all the attendanceList where machineNo equals to UPDATED_MACHINE_NO
        defaultAttendanceShouldNotBeFound("machineNo.in=" + UPDATED_MACHINE_NO);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMachineNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo is not null
        defaultAttendanceShouldBeFound("machineNo.specified=true");

        // Get all the attendanceList where machineNo is null
        defaultAttendanceShouldNotBeFound("machineNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttendancesByMachineNoContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo contains DEFAULT_MACHINE_NO
        defaultAttendanceShouldBeFound("machineNo.contains=" + DEFAULT_MACHINE_NO);

        // Get all the attendanceList where machineNo contains UPDATED_MACHINE_NO
        defaultAttendanceShouldNotBeFound("machineNo.contains=" + UPDATED_MACHINE_NO);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMachineNoNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo does not contain DEFAULT_MACHINE_NO
        defaultAttendanceShouldNotBeFound("machineNo.doesNotContain=" + DEFAULT_MACHINE_NO);

        // Get all the attendanceList where machineNo does not contain UPDATED_MACHINE_NO
        defaultAttendanceShouldBeFound("machineNo.doesNotContain=" + UPDATED_MACHINE_NO);
    }


    @Test
    @Transactional
    public void getAllAttendancesByMarkedAsIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where markedAs equals to DEFAULT_MARKED_AS
        defaultAttendanceShouldBeFound("markedAs.equals=" + DEFAULT_MARKED_AS);

        // Get all the attendanceList where markedAs equals to UPDATED_MARKED_AS
        defaultAttendanceShouldNotBeFound("markedAs.equals=" + UPDATED_MARKED_AS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMarkedAsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where markedAs not equals to DEFAULT_MARKED_AS
        defaultAttendanceShouldNotBeFound("markedAs.notEquals=" + DEFAULT_MARKED_AS);

        // Get all the attendanceList where markedAs not equals to UPDATED_MARKED_AS
        defaultAttendanceShouldBeFound("markedAs.notEquals=" + UPDATED_MARKED_AS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMarkedAsIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where markedAs in DEFAULT_MARKED_AS or UPDATED_MARKED_AS
        defaultAttendanceShouldBeFound("markedAs.in=" + DEFAULT_MARKED_AS + "," + UPDATED_MARKED_AS);

        // Get all the attendanceList where markedAs equals to UPDATED_MARKED_AS
        defaultAttendanceShouldNotBeFound("markedAs.in=" + UPDATED_MARKED_AS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMarkedAsIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where markedAs is not null
        defaultAttendanceShouldBeFound("markedAs.specified=true");

        // Get all the attendanceList where markedAs is null
        defaultAttendanceShouldNotBeFound("markedAs.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByLeaveAppliedIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where leaveApplied equals to DEFAULT_LEAVE_APPLIED
        defaultAttendanceShouldBeFound("leaveApplied.equals=" + DEFAULT_LEAVE_APPLIED);

        // Get all the attendanceList where leaveApplied equals to UPDATED_LEAVE_APPLIED
        defaultAttendanceShouldNotBeFound("leaveApplied.equals=" + UPDATED_LEAVE_APPLIED);
    }

    @Test
    @Transactional
    public void getAllAttendancesByLeaveAppliedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where leaveApplied not equals to DEFAULT_LEAVE_APPLIED
        defaultAttendanceShouldNotBeFound("leaveApplied.notEquals=" + DEFAULT_LEAVE_APPLIED);

        // Get all the attendanceList where leaveApplied not equals to UPDATED_LEAVE_APPLIED
        defaultAttendanceShouldBeFound("leaveApplied.notEquals=" + UPDATED_LEAVE_APPLIED);
    }

    @Test
    @Transactional
    public void getAllAttendancesByLeaveAppliedIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where leaveApplied in DEFAULT_LEAVE_APPLIED or UPDATED_LEAVE_APPLIED
        defaultAttendanceShouldBeFound("leaveApplied.in=" + DEFAULT_LEAVE_APPLIED + "," + UPDATED_LEAVE_APPLIED);

        // Get all the attendanceList where leaveApplied equals to UPDATED_LEAVE_APPLIED
        defaultAttendanceShouldNotBeFound("leaveApplied.in=" + UPDATED_LEAVE_APPLIED);
    }

    @Test
    @Transactional
    public void getAllAttendancesByLeaveAppliedIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where leaveApplied is not null
        defaultAttendanceShouldBeFound("leaveApplied.specified=true");

        // Get all the attendanceList where leaveApplied is null
        defaultAttendanceShouldNotBeFound("leaveApplied.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Employee employee = attendance.getEmployee();
        attendanceRepository.saveAndFlush(attendance);
        Long employeeId = employee.getId();

        // Get all the attendanceList where employee equals to employeeId
        defaultAttendanceShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the attendanceList where employee equals to employeeId + 1
        defaultAttendanceShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        EmployeeSalary employeeSalary = EmployeeSalaryResourceIT.createEntity(em);
        em.persist(employeeSalary);
        em.flush();
        attendance.setEmployeeSalary(employeeSalary);
        attendanceRepository.saveAndFlush(attendance);
        Long employeeSalaryId = employeeSalary.getId();

        // Get all the attendanceList where employeeSalary equals to employeeSalaryId
        defaultAttendanceShouldBeFound("employeeSalaryId.equals=" + employeeSalaryId);

        // Get all the attendanceList where employeeSalary equals to employeeSalaryId + 1
        defaultAttendanceShouldNotBeFound("employeeSalaryId.equals=" + (employeeSalaryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttendanceShouldBeFound(String filter) throws Exception {
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].attendanceTime").value(hasItem(DEFAULT_ATTENDANCE_TIME.toString())))
            .andExpect(jsonPath("$.[*].machineNo").value(hasItem(DEFAULT_MACHINE_NO)))
            .andExpect(jsonPath("$.[*].markedAs").value(hasItem(DEFAULT_MARKED_AS.toString())))
            .andExpect(jsonPath("$.[*].leaveApplied").value(hasItem(DEFAULT_LEAVE_APPLIED.toString())));

        // Check, that the count call also returns 1
        restAttendanceMockMvc.perform(get("/api/attendances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttendanceShouldNotBeFound(String filter) throws Exception {
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttendanceMockMvc.perform(get("/api/attendances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAttendance() throws Exception {
        // Get the attendance
        restAttendanceMockMvc.perform(get("/api/attendances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
