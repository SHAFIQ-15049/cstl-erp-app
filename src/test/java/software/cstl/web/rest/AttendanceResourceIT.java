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
import software.cstl.domain.*;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.EmployeeType;
import software.cstl.domain.enumeration.LeaveAppliedStatus;
import software.cstl.repository.AttendanceRepository;
import software.cstl.service.AttendanceQueryService;
import software.cstl.service.AttendanceService;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private static final String DEFAULT_EMPLOYEE_MACHINE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_MACHINE_ID = "BBBBBBBBBB";

    private static final EmployeeCategory DEFAULT_EMPLOYEE_CATEGORY = EmployeeCategory.TOP_LEVEL;
    private static final EmployeeCategory UPDATED_EMPLOYEE_CATEGORY = EmployeeCategory.MID_LEVEL;

    private static final EmployeeType DEFAULT_EMPLOYEE_TYPE = EmployeeType.PERMANENT;
    private static final EmployeeType UPDATED_EMPLOYEE_TYPE = EmployeeType.TEMPORARY;

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
            .leaveApplied(DEFAULT_LEAVE_APPLIED)
            .employeeMachineId(DEFAULT_EMPLOYEE_MACHINE_ID)
            .employeeCategory(DEFAULT_EMPLOYEE_CATEGORY)
            .employeeType(DEFAULT_EMPLOYEE_TYPE);
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
            .leaveApplied(UPDATED_LEAVE_APPLIED)
            .employeeMachineId(UPDATED_EMPLOYEE_MACHINE_ID)
            .employeeCategory(UPDATED_EMPLOYEE_CATEGORY)
            .employeeType(UPDATED_EMPLOYEE_TYPE);
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
    public void createAttendance() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();
        // Create the Attendance
        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isCreated());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate + 1);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getAttendanceTime()).isEqualTo(DEFAULT_ATTENDANCE_TIME);
        assertThat(testAttendance.getMachineNo()).isEqualTo(DEFAULT_MACHINE_NO);
        assertThat(testAttendance.getMarkedAs()).isEqualTo(DEFAULT_MARKED_AS);
        assertThat(testAttendance.getLeaveApplied()).isEqualTo(DEFAULT_LEAVE_APPLIED);
        assertThat(testAttendance.getEmployeeMachineId()).isEqualTo(DEFAULT_EMPLOYEE_MACHINE_ID);
        assertThat(testAttendance.getEmployeeCategory()).isEqualTo(DEFAULT_EMPLOYEE_CATEGORY);
        assertThat(testAttendance.getEmployeeType()).isEqualTo(DEFAULT_EMPLOYEE_TYPE);
    }

    @Test
    @Transactional
    public void createAttendanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();

        // Create the Attendance with an existing ID
        attendance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAttendanceTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceRepository.findAll().size();
        // set the field null
        attendance.setAttendanceTime(null);

        // Create the Attendance, which fails.


        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isBadRequest());

        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMachineNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceRepository.findAll().size();
        // set the field null
        attendance.setMachineNo(null);

        // Create the Attendance, which fails.


        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isBadRequest());

        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].leaveApplied").value(hasItem(DEFAULT_LEAVE_APPLIED.toString())))
            .andExpect(jsonPath("$.[*].employeeMachineId").value(hasItem(DEFAULT_EMPLOYEE_MACHINE_ID)))
            .andExpect(jsonPath("$.[*].employeeCategory").value(hasItem(DEFAULT_EMPLOYEE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].employeeType").value(hasItem(DEFAULT_EMPLOYEE_TYPE.toString())));
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
            .andExpect(jsonPath("$.leaveApplied").value(DEFAULT_LEAVE_APPLIED.toString()))
            .andExpect(jsonPath("$.employeeMachineId").value(DEFAULT_EMPLOYEE_MACHINE_ID))
            .andExpect(jsonPath("$.employeeCategory").value(DEFAULT_EMPLOYEE_CATEGORY.toString()))
            .andExpect(jsonPath("$.employeeType").value(DEFAULT_EMPLOYEE_TYPE.toString()));
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
    public void getAllAttendancesByEmployeeMachineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId equals to DEFAULT_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldBeFound("employeeMachineId.equals=" + DEFAULT_EMPLOYEE_MACHINE_ID);

        // Get all the attendanceList where employeeMachineId equals to UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldNotBeFound("employeeMachineId.equals=" + UPDATED_EMPLOYEE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId not equals to DEFAULT_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldNotBeFound("employeeMachineId.notEquals=" + DEFAULT_EMPLOYEE_MACHINE_ID);

        // Get all the attendanceList where employeeMachineId not equals to UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldBeFound("employeeMachineId.notEquals=" + UPDATED_EMPLOYEE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId in DEFAULT_EMPLOYEE_MACHINE_ID or UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldBeFound("employeeMachineId.in=" + DEFAULT_EMPLOYEE_MACHINE_ID + "," + UPDATED_EMPLOYEE_MACHINE_ID);

        // Get all the attendanceList where employeeMachineId equals to UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldNotBeFound("employeeMachineId.in=" + UPDATED_EMPLOYEE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId is not null
        defaultAttendanceShouldBeFound("employeeMachineId.specified=true");

        // Get all the attendanceList where employeeMachineId is null
        defaultAttendanceShouldNotBeFound("employeeMachineId.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId contains DEFAULT_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldBeFound("employeeMachineId.contains=" + DEFAULT_EMPLOYEE_MACHINE_ID);

        // Get all the attendanceList where employeeMachineId contains UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldNotBeFound("employeeMachineId.contains=" + UPDATED_EMPLOYEE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId does not contain DEFAULT_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldNotBeFound("employeeMachineId.doesNotContain=" + DEFAULT_EMPLOYEE_MACHINE_ID);

        // Get all the attendanceList where employeeMachineId does not contain UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldBeFound("employeeMachineId.doesNotContain=" + UPDATED_EMPLOYEE_MACHINE_ID);
    }


    @Test
    @Transactional
    public void getAllAttendancesByEmployeeCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeCategory equals to DEFAULT_EMPLOYEE_CATEGORY
        defaultAttendanceShouldBeFound("employeeCategory.equals=" + DEFAULT_EMPLOYEE_CATEGORY);

        // Get all the attendanceList where employeeCategory equals to UPDATED_EMPLOYEE_CATEGORY
        defaultAttendanceShouldNotBeFound("employeeCategory.equals=" + UPDATED_EMPLOYEE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeCategory not equals to DEFAULT_EMPLOYEE_CATEGORY
        defaultAttendanceShouldNotBeFound("employeeCategory.notEquals=" + DEFAULT_EMPLOYEE_CATEGORY);

        // Get all the attendanceList where employeeCategory not equals to UPDATED_EMPLOYEE_CATEGORY
        defaultAttendanceShouldBeFound("employeeCategory.notEquals=" + UPDATED_EMPLOYEE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeCategory in DEFAULT_EMPLOYEE_CATEGORY or UPDATED_EMPLOYEE_CATEGORY
        defaultAttendanceShouldBeFound("employeeCategory.in=" + DEFAULT_EMPLOYEE_CATEGORY + "," + UPDATED_EMPLOYEE_CATEGORY);

        // Get all the attendanceList where employeeCategory equals to UPDATED_EMPLOYEE_CATEGORY
        defaultAttendanceShouldNotBeFound("employeeCategory.in=" + UPDATED_EMPLOYEE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeCategory is not null
        defaultAttendanceShouldBeFound("employeeCategory.specified=true");

        // Get all the attendanceList where employeeCategory is null
        defaultAttendanceShouldNotBeFound("employeeCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeType equals to DEFAULT_EMPLOYEE_TYPE
        defaultAttendanceShouldBeFound("employeeType.equals=" + DEFAULT_EMPLOYEE_TYPE);

        // Get all the attendanceList where employeeType equals to UPDATED_EMPLOYEE_TYPE
        defaultAttendanceShouldNotBeFound("employeeType.equals=" + UPDATED_EMPLOYEE_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeType not equals to DEFAULT_EMPLOYEE_TYPE
        defaultAttendanceShouldNotBeFound("employeeType.notEquals=" + DEFAULT_EMPLOYEE_TYPE);

        // Get all the attendanceList where employeeType not equals to UPDATED_EMPLOYEE_TYPE
        defaultAttendanceShouldBeFound("employeeType.notEquals=" + UPDATED_EMPLOYEE_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeType in DEFAULT_EMPLOYEE_TYPE or UPDATED_EMPLOYEE_TYPE
        defaultAttendanceShouldBeFound("employeeType.in=" + DEFAULT_EMPLOYEE_TYPE + "," + UPDATED_EMPLOYEE_TYPE);

        // Get all the attendanceList where employeeType equals to UPDATED_EMPLOYEE_TYPE
        defaultAttendanceShouldNotBeFound("employeeType.in=" + UPDATED_EMPLOYEE_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeType is not null
        defaultAttendanceShouldBeFound("employeeType.specified=true");

        // Get all the attendanceList where employeeType is null
        defaultAttendanceShouldNotBeFound("employeeType.specified=false");
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


    @Test
    @Transactional
    public void getAllAttendancesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        attendance.setDepartment(department);
        attendanceRepository.saveAndFlush(attendance);
        Long departmentId = department.getId();

        // Get all the attendanceList where department equals to departmentId
        defaultAttendanceShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the attendanceList where department equals to departmentId + 1
        defaultAttendanceShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllAttendancesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        Designation designation = DesignationResourceIT.createEntity(em);
        em.persist(designation);
        em.flush();
        attendance.setDesignation(designation);
        attendanceRepository.saveAndFlush(attendance);
        Long designationId = designation.getId();

        // Get all the attendanceList where designation equals to designationId
        defaultAttendanceShouldBeFound("designationId.equals=" + designationId);

        // Get all the attendanceList where designation equals to designationId + 1
        defaultAttendanceShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }


    @Test
    @Transactional
    public void getAllAttendancesByLineIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        Line line = LineResourceIT.createEntity(em);
        em.persist(line);
        em.flush();
        attendance.setLine(line);
        attendanceRepository.saveAndFlush(attendance);
        Long lineId = line.getId();

        // Get all the attendanceList where line equals to lineId
        defaultAttendanceShouldBeFound("lineId.equals=" + lineId);

        // Get all the attendanceList where line equals to lineId + 1
        defaultAttendanceShouldNotBeFound("lineId.equals=" + (lineId + 1));
    }


    @Test
    @Transactional
    public void getAllAttendancesByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        Grade grade = GradeResourceIT.createEntity(em);
        em.persist(grade);
        em.flush();
        attendance.setGrade(grade);
        attendanceRepository.saveAndFlush(attendance);
        Long gradeId = grade.getId();

        // Get all the attendanceList where grade equals to gradeId
        defaultAttendanceShouldBeFound("gradeId.equals=" + gradeId);

        // Get all the attendanceList where grade equals to gradeId + 1
        defaultAttendanceShouldNotBeFound("gradeId.equals=" + (gradeId + 1));
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
            .andExpect(jsonPath("$.[*].leaveApplied").value(hasItem(DEFAULT_LEAVE_APPLIED.toString())))
            .andExpect(jsonPath("$.[*].employeeMachineId").value(hasItem(DEFAULT_EMPLOYEE_MACHINE_ID)))
            .andExpect(jsonPath("$.[*].employeeCategory").value(hasItem(DEFAULT_EMPLOYEE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].employeeType").value(hasItem(DEFAULT_EMPLOYEE_TYPE.toString())));

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

    @Test
    @Transactional
    public void updateAttendance() throws Exception {
        // Initialize the database
        attendanceService.save(attendance);

        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance
        Attendance updatedAttendance = attendanceRepository.findById(attendance.getId()).get();
        // Disconnect from session so that the updates on updatedAttendance are not directly saved in db
        em.detach(updatedAttendance);
        updatedAttendance
            .attendanceTime(UPDATED_ATTENDANCE_TIME)
            .machineNo(UPDATED_MACHINE_NO)
            .markedAs(UPDATED_MARKED_AS)
            .leaveApplied(UPDATED_LEAVE_APPLIED)
            .employeeMachineId(UPDATED_EMPLOYEE_MACHINE_ID)
            .employeeCategory(UPDATED_EMPLOYEE_CATEGORY)
            .employeeType(UPDATED_EMPLOYEE_TYPE);

        restAttendanceMockMvc.perform(put("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttendance)))
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getAttendanceTime()).isEqualTo(UPDATED_ATTENDANCE_TIME);
        assertThat(testAttendance.getMachineNo()).isEqualTo(UPDATED_MACHINE_NO);
        assertThat(testAttendance.getMarkedAs()).isEqualTo(UPDATED_MARKED_AS);
        assertThat(testAttendance.getLeaveApplied()).isEqualTo(UPDATED_LEAVE_APPLIED);
        assertThat(testAttendance.getEmployeeMachineId()).isEqualTo(UPDATED_EMPLOYEE_MACHINE_ID);
        assertThat(testAttendance.getEmployeeCategory()).isEqualTo(UPDATED_EMPLOYEE_CATEGORY);
        assertThat(testAttendance.getEmployeeType()).isEqualTo(UPDATED_EMPLOYEE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceMockMvc.perform(put("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendance() throws Exception {
        // Initialize the database
        attendanceService.save(attendance);

        int databaseSizeBeforeDelete = attendanceRepository.findAll().size();

        // Delete the attendance
        restAttendanceMockMvc.perform(delete("/api/attendances/{id}", attendance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
