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
import software.cstl.domain.AttendanceDataUpload;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.enumeration.ConsiderAsType;
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

    private static final ConsiderAsType DEFAULT_CONSIDER_AS = ConsiderAsType.REGULAR;
    private static final ConsiderAsType UPDATED_CONSIDER_AS = ConsiderAsType.WEEKENDWITHOVERTIME;

    private static final String DEFAULT_MACHINE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE_NO = "BBBBBBBBBB";

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
            .considerAs(DEFAULT_CONSIDER_AS)
            .machineNo(DEFAULT_MACHINE_NO);
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
            .considerAs(UPDATED_CONSIDER_AS)
            .machineNo(UPDATED_MACHINE_NO);
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
        assertThat(testAttendance.getConsiderAs()).isEqualTo(DEFAULT_CONSIDER_AS);
        assertThat(testAttendance.getMachineNo()).isEqualTo(DEFAULT_MACHINE_NO);
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
    public void checkConsiderAsIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceRepository.findAll().size();
        // set the field null
        attendance.setConsiderAs(null);

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
            .andExpect(jsonPath("$.[*].considerAs").value(hasItem(DEFAULT_CONSIDER_AS.toString())))
            .andExpect(jsonPath("$.[*].machineNo").value(hasItem(DEFAULT_MACHINE_NO)));
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
            .andExpect(jsonPath("$.considerAs").value(DEFAULT_CONSIDER_AS.toString()))
            .andExpect(jsonPath("$.machineNo").value(DEFAULT_MACHINE_NO));
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
    public void getAllAttendancesByConsiderAsIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where considerAs equals to DEFAULT_CONSIDER_AS
        defaultAttendanceShouldBeFound("considerAs.equals=" + DEFAULT_CONSIDER_AS);

        // Get all the attendanceList where considerAs equals to UPDATED_CONSIDER_AS
        defaultAttendanceShouldNotBeFound("considerAs.equals=" + UPDATED_CONSIDER_AS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByConsiderAsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where considerAs not equals to DEFAULT_CONSIDER_AS
        defaultAttendanceShouldNotBeFound("considerAs.notEquals=" + DEFAULT_CONSIDER_AS);

        // Get all the attendanceList where considerAs not equals to UPDATED_CONSIDER_AS
        defaultAttendanceShouldBeFound("considerAs.notEquals=" + UPDATED_CONSIDER_AS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByConsiderAsIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where considerAs in DEFAULT_CONSIDER_AS or UPDATED_CONSIDER_AS
        defaultAttendanceShouldBeFound("considerAs.in=" + DEFAULT_CONSIDER_AS + "," + UPDATED_CONSIDER_AS);

        // Get all the attendanceList where considerAs equals to UPDATED_CONSIDER_AS
        defaultAttendanceShouldNotBeFound("considerAs.in=" + UPDATED_CONSIDER_AS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByConsiderAsIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where considerAs is not null
        defaultAttendanceShouldBeFound("considerAs.specified=true");

        // Get all the attendanceList where considerAs is null
        defaultAttendanceShouldNotBeFound("considerAs.specified=false");
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
    public void getAllAttendancesByAttendanceDataUploadIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        AttendanceDataUpload attendanceDataUpload = AttendanceDataUploadResourceIT.createEntity(em);
        em.persist(attendanceDataUpload);
        em.flush();
        attendance.setAttendanceDataUpload(attendanceDataUpload);
        attendanceRepository.saveAndFlush(attendance);
        Long attendanceDataUploadId = attendanceDataUpload.getId();

        // Get all the attendanceList where attendanceDataUpload equals to attendanceDataUploadId
        defaultAttendanceShouldBeFound("attendanceDataUploadId.equals=" + attendanceDataUploadId);

        // Get all the attendanceList where attendanceDataUpload equals to attendanceDataUploadId + 1
        defaultAttendanceShouldNotBeFound("attendanceDataUploadId.equals=" + (attendanceDataUploadId + 1));
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
            .andExpect(jsonPath("$.[*].considerAs").value(hasItem(DEFAULT_CONSIDER_AS.toString())))
            .andExpect(jsonPath("$.[*].machineNo").value(hasItem(DEFAULT_MACHINE_NO)));

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
            .considerAs(UPDATED_CONSIDER_AS)
            .machineNo(UPDATED_MACHINE_NO);

        restAttendanceMockMvc.perform(put("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttendance)))
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getAttendanceTime()).isEqualTo(UPDATED_ATTENDANCE_TIME);
        assertThat(testAttendance.getConsiderAs()).isEqualTo(UPDATED_CONSIDER_AS);
        assertThat(testAttendance.getMachineNo()).isEqualTo(UPDATED_MACHINE_NO);
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
