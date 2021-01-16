package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Employee;
import software.cstl.domain.PartialSalary;
import software.cstl.domain.OverTime;
import software.cstl.domain.Fine;
import software.cstl.domain.Advance;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.EducationalInfo;
import software.cstl.domain.Training;
import software.cstl.domain.EmployeeAccount;
import software.cstl.domain.JobHistory;
import software.cstl.domain.ServiceHistory;
import software.cstl.domain.Company;
import software.cstl.domain.Department;
import software.cstl.domain.Grade;
import software.cstl.domain.Designation;
import software.cstl.domain.Line;
import software.cstl.domain.Address;
import software.cstl.domain.PersonalInfo;
import software.cstl.repository.EmployeeRepository;
import software.cstl.service.EmployeeService;
import software.cstl.service.dto.EmployeeCriteria;
import software.cstl.service.EmployeeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.EmployeeType;
import software.cstl.domain.enumeration.EmployeeStatus;
/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMP_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GLOBAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_GLOBAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ATTENDANCE_MACHINE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ATTENDANCE_MACHINE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_ID = "BBBBBBBBBB";

    private static final EmployeeCategory DEFAULT_CATEGORY = EmployeeCategory.TOP_LEVEL;
    private static final EmployeeCategory UPDATED_CATEGORY = EmployeeCategory.MID_LEVEL;

    private static final EmployeeType DEFAULT_TYPE = EmployeeType.PERMANENT;
    private static final EmployeeType UPDATED_TYPE = EmployeeType.TEMPORARY;

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_JOINING_DATE = LocalDate.ofEpochDay(-1L);

    private static final EmployeeStatus DEFAULT_STATUS = EmployeeStatus.ACTIVE;
    private static final EmployeeStatus UPDATED_STATUS = EmployeeStatus.TERMINATED;

    private static final LocalDate DEFAULT_TERMINATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TERMINATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TERMINATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TERMINATION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_TERMINATION_REASON = "BBBBBBBBBB";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeQueryService employeeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .name(DEFAULT_NAME)
            .empId(DEFAULT_EMP_ID)
            .globalId(DEFAULT_GLOBAL_ID)
            .attendanceMachineId(DEFAULT_ATTENDANCE_MACHINE_ID)
            .localId(DEFAULT_LOCAL_ID)
            .category(DEFAULT_CATEGORY)
            .type(DEFAULT_TYPE)
            .joiningDate(DEFAULT_JOINING_DATE)
            .status(DEFAULT_STATUS)
            .terminationDate(DEFAULT_TERMINATION_DATE)
            .terminationReason(DEFAULT_TERMINATION_REASON);
        return employee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .name(UPDATED_NAME)
            .empId(UPDATED_EMP_ID)
            .globalId(UPDATED_GLOBAL_ID)
            .attendanceMachineId(UPDATED_ATTENDANCE_MACHINE_ID)
            .localId(UPDATED_LOCAL_ID)
            .category(UPDATED_CATEGORY)
            .type(UPDATED_TYPE)
            .joiningDate(UPDATED_JOINING_DATE)
            .status(UPDATED_STATUS)
            .terminationDate(UPDATED_TERMINATION_DATE)
            .terminationReason(UPDATED_TERMINATION_REASON);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployee.getEmpId()).isEqualTo(DEFAULT_EMP_ID);
        assertThat(testEmployee.getGlobalId()).isEqualTo(DEFAULT_GLOBAL_ID);
        assertThat(testEmployee.getAttendanceMachineId()).isEqualTo(DEFAULT_ATTENDANCE_MACHINE_ID);
        assertThat(testEmployee.getLocalId()).isEqualTo(DEFAULT_LOCAL_ID);
        assertThat(testEmployee.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testEmployee.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmployee.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testEmployee.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployee.getTerminationDate()).isEqualTo(DEFAULT_TERMINATION_DATE);
        assertThat(testEmployee.getTerminationReason()).isEqualTo(DEFAULT_TERMINATION_REASON);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setName(null);

        // Create the Employee, which fails.


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmpId(null);

        // Create the Employee, which fails.


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGlobalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setGlobalId(null);

        // Create the Employee, which fails.


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setLocalId(null);

        // Create the Employee, which fails.


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].empId").value(hasItem(DEFAULT_EMP_ID)))
            .andExpect(jsonPath("$.[*].globalId").value(hasItem(DEFAULT_GLOBAL_ID)))
            .andExpect(jsonPath("$.[*].attendanceMachineId").value(hasItem(DEFAULT_ATTENDANCE_MACHINE_ID)))
            .andExpect(jsonPath("$.[*].localId").value(hasItem(DEFAULT_LOCAL_ID)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].terminationReason").value(hasItem(DEFAULT_TERMINATION_REASON.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.empId").value(DEFAULT_EMP_ID))
            .andExpect(jsonPath("$.globalId").value(DEFAULT_GLOBAL_ID))
            .andExpect(jsonPath("$.attendanceMachineId").value(DEFAULT_ATTENDANCE_MACHINE_ID))
            .andExpect(jsonPath("$.localId").value(DEFAULT_LOCAL_ID))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.terminationDate").value(DEFAULT_TERMINATION_DATE.toString()))
            .andExpect(jsonPath("$.terminationReason").value(DEFAULT_TERMINATION_REASON.toString()));
    }


    @Test
    @Transactional
    public void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name equals to DEFAULT_NAME
        defaultEmployeeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the employeeList where name equals to UPDATED_NAME
        defaultEmployeeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name not equals to DEFAULT_NAME
        defaultEmployeeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the employeeList where name not equals to UPDATED_NAME
        defaultEmployeeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEmployeeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the employeeList where name equals to UPDATED_NAME
        defaultEmployeeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name is not null
        defaultEmployeeShouldBeFound("name.specified=true");

        // Get all the employeeList where name is null
        defaultEmployeeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name contains DEFAULT_NAME
        defaultEmployeeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the employeeList where name contains UPDATED_NAME
        defaultEmployeeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where name does not contain DEFAULT_NAME
        defaultEmployeeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the employeeList where name does not contain UPDATED_NAME
        defaultEmployeeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllEmployeesByEmpIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empId equals to DEFAULT_EMP_ID
        defaultEmployeeShouldBeFound("empId.equals=" + DEFAULT_EMP_ID);

        // Get all the employeeList where empId equals to UPDATED_EMP_ID
        defaultEmployeeShouldNotBeFound("empId.equals=" + UPDATED_EMP_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmpIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empId not equals to DEFAULT_EMP_ID
        defaultEmployeeShouldNotBeFound("empId.notEquals=" + DEFAULT_EMP_ID);

        // Get all the employeeList where empId not equals to UPDATED_EMP_ID
        defaultEmployeeShouldBeFound("empId.notEquals=" + UPDATED_EMP_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmpIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empId in DEFAULT_EMP_ID or UPDATED_EMP_ID
        defaultEmployeeShouldBeFound("empId.in=" + DEFAULT_EMP_ID + "," + UPDATED_EMP_ID);

        // Get all the employeeList where empId equals to UPDATED_EMP_ID
        defaultEmployeeShouldNotBeFound("empId.in=" + UPDATED_EMP_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmpIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empId is not null
        defaultEmployeeShouldBeFound("empId.specified=true");

        // Get all the employeeList where empId is null
        defaultEmployeeShouldNotBeFound("empId.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByEmpIdContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empId contains DEFAULT_EMP_ID
        defaultEmployeeShouldBeFound("empId.contains=" + DEFAULT_EMP_ID);

        // Get all the employeeList where empId contains UPDATED_EMP_ID
        defaultEmployeeShouldNotBeFound("empId.contains=" + UPDATED_EMP_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmpIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empId does not contain DEFAULT_EMP_ID
        defaultEmployeeShouldNotBeFound("empId.doesNotContain=" + DEFAULT_EMP_ID);

        // Get all the employeeList where empId does not contain UPDATED_EMP_ID
        defaultEmployeeShouldBeFound("empId.doesNotContain=" + UPDATED_EMP_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByGlobalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where globalId equals to DEFAULT_GLOBAL_ID
        defaultEmployeeShouldBeFound("globalId.equals=" + DEFAULT_GLOBAL_ID);

        // Get all the employeeList where globalId equals to UPDATED_GLOBAL_ID
        defaultEmployeeShouldNotBeFound("globalId.equals=" + UPDATED_GLOBAL_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGlobalIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where globalId not equals to DEFAULT_GLOBAL_ID
        defaultEmployeeShouldNotBeFound("globalId.notEquals=" + DEFAULT_GLOBAL_ID);

        // Get all the employeeList where globalId not equals to UPDATED_GLOBAL_ID
        defaultEmployeeShouldBeFound("globalId.notEquals=" + UPDATED_GLOBAL_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGlobalIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where globalId in DEFAULT_GLOBAL_ID or UPDATED_GLOBAL_ID
        defaultEmployeeShouldBeFound("globalId.in=" + DEFAULT_GLOBAL_ID + "," + UPDATED_GLOBAL_ID);

        // Get all the employeeList where globalId equals to UPDATED_GLOBAL_ID
        defaultEmployeeShouldNotBeFound("globalId.in=" + UPDATED_GLOBAL_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGlobalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where globalId is not null
        defaultEmployeeShouldBeFound("globalId.specified=true");

        // Get all the employeeList where globalId is null
        defaultEmployeeShouldNotBeFound("globalId.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByGlobalIdContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where globalId contains DEFAULT_GLOBAL_ID
        defaultEmployeeShouldBeFound("globalId.contains=" + DEFAULT_GLOBAL_ID);

        // Get all the employeeList where globalId contains UPDATED_GLOBAL_ID
        defaultEmployeeShouldNotBeFound("globalId.contains=" + UPDATED_GLOBAL_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGlobalIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where globalId does not contain DEFAULT_GLOBAL_ID
        defaultEmployeeShouldNotBeFound("globalId.doesNotContain=" + DEFAULT_GLOBAL_ID);

        // Get all the employeeList where globalId does not contain UPDATED_GLOBAL_ID
        defaultEmployeeShouldBeFound("globalId.doesNotContain=" + UPDATED_GLOBAL_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByAttendanceMachineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceMachineId equals to DEFAULT_ATTENDANCE_MACHINE_ID
        defaultEmployeeShouldBeFound("attendanceMachineId.equals=" + DEFAULT_ATTENDANCE_MACHINE_ID);

        // Get all the employeeList where attendanceMachineId equals to UPDATED_ATTENDANCE_MACHINE_ID
        defaultEmployeeShouldNotBeFound("attendanceMachineId.equals=" + UPDATED_ATTENDANCE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttendanceMachineIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceMachineId not equals to DEFAULT_ATTENDANCE_MACHINE_ID
        defaultEmployeeShouldNotBeFound("attendanceMachineId.notEquals=" + DEFAULT_ATTENDANCE_MACHINE_ID);

        // Get all the employeeList where attendanceMachineId not equals to UPDATED_ATTENDANCE_MACHINE_ID
        defaultEmployeeShouldBeFound("attendanceMachineId.notEquals=" + UPDATED_ATTENDANCE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttendanceMachineIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceMachineId in DEFAULT_ATTENDANCE_MACHINE_ID or UPDATED_ATTENDANCE_MACHINE_ID
        defaultEmployeeShouldBeFound("attendanceMachineId.in=" + DEFAULT_ATTENDANCE_MACHINE_ID + "," + UPDATED_ATTENDANCE_MACHINE_ID);

        // Get all the employeeList where attendanceMachineId equals to UPDATED_ATTENDANCE_MACHINE_ID
        defaultEmployeeShouldNotBeFound("attendanceMachineId.in=" + UPDATED_ATTENDANCE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttendanceMachineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceMachineId is not null
        defaultEmployeeShouldBeFound("attendanceMachineId.specified=true");

        // Get all the employeeList where attendanceMachineId is null
        defaultEmployeeShouldNotBeFound("attendanceMachineId.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByAttendanceMachineIdContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceMachineId contains DEFAULT_ATTENDANCE_MACHINE_ID
        defaultEmployeeShouldBeFound("attendanceMachineId.contains=" + DEFAULT_ATTENDANCE_MACHINE_ID);

        // Get all the employeeList where attendanceMachineId contains UPDATED_ATTENDANCE_MACHINE_ID
        defaultEmployeeShouldNotBeFound("attendanceMachineId.contains=" + UPDATED_ATTENDANCE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByAttendanceMachineIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceMachineId does not contain DEFAULT_ATTENDANCE_MACHINE_ID
        defaultEmployeeShouldNotBeFound("attendanceMachineId.doesNotContain=" + DEFAULT_ATTENDANCE_MACHINE_ID);

        // Get all the employeeList where attendanceMachineId does not contain UPDATED_ATTENDANCE_MACHINE_ID
        defaultEmployeeShouldBeFound("attendanceMachineId.doesNotContain=" + UPDATED_ATTENDANCE_MACHINE_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByLocalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localId equals to DEFAULT_LOCAL_ID
        defaultEmployeeShouldBeFound("localId.equals=" + DEFAULT_LOCAL_ID);

        // Get all the employeeList where localId equals to UPDATED_LOCAL_ID
        defaultEmployeeShouldNotBeFound("localId.equals=" + UPDATED_LOCAL_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLocalIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localId not equals to DEFAULT_LOCAL_ID
        defaultEmployeeShouldNotBeFound("localId.notEquals=" + DEFAULT_LOCAL_ID);

        // Get all the employeeList where localId not equals to UPDATED_LOCAL_ID
        defaultEmployeeShouldBeFound("localId.notEquals=" + UPDATED_LOCAL_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLocalIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localId in DEFAULT_LOCAL_ID or UPDATED_LOCAL_ID
        defaultEmployeeShouldBeFound("localId.in=" + DEFAULT_LOCAL_ID + "," + UPDATED_LOCAL_ID);

        // Get all the employeeList where localId equals to UPDATED_LOCAL_ID
        defaultEmployeeShouldNotBeFound("localId.in=" + UPDATED_LOCAL_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLocalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localId is not null
        defaultEmployeeShouldBeFound("localId.specified=true");

        // Get all the employeeList where localId is null
        defaultEmployeeShouldNotBeFound("localId.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByLocalIdContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localId contains DEFAULT_LOCAL_ID
        defaultEmployeeShouldBeFound("localId.contains=" + DEFAULT_LOCAL_ID);

        // Get all the employeeList where localId contains UPDATED_LOCAL_ID
        defaultEmployeeShouldNotBeFound("localId.contains=" + UPDATED_LOCAL_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLocalIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localId does not contain DEFAULT_LOCAL_ID
        defaultEmployeeShouldNotBeFound("localId.doesNotContain=" + DEFAULT_LOCAL_ID);

        // Get all the employeeList where localId does not contain UPDATED_LOCAL_ID
        defaultEmployeeShouldBeFound("localId.doesNotContain=" + UPDATED_LOCAL_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where category equals to DEFAULT_CATEGORY
        defaultEmployeeShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the employeeList where category equals to UPDATED_CATEGORY
        defaultEmployeeShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where category not equals to DEFAULT_CATEGORY
        defaultEmployeeShouldNotBeFound("category.notEquals=" + DEFAULT_CATEGORY);

        // Get all the employeeList where category not equals to UPDATED_CATEGORY
        defaultEmployeeShouldBeFound("category.notEquals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultEmployeeShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the employeeList where category equals to UPDATED_CATEGORY
        defaultEmployeeShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where category is not null
        defaultEmployeeShouldBeFound("category.specified=true");

        // Get all the employeeList where category is null
        defaultEmployeeShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where type equals to DEFAULT_TYPE
        defaultEmployeeShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the employeeList where type equals to UPDATED_TYPE
        defaultEmployeeShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where type not equals to DEFAULT_TYPE
        defaultEmployeeShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the employeeList where type not equals to UPDATED_TYPE
        defaultEmployeeShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEmployeeShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the employeeList where type equals to UPDATED_TYPE
        defaultEmployeeShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where type is not null
        defaultEmployeeShouldBeFound("type.specified=true");

        // Get all the employeeList where type is null
        defaultEmployeeShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate equals to DEFAULT_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.equals=" + DEFAULT_JOINING_DATE);

        // Get all the employeeList where joiningDate equals to UPDATED_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.equals=" + UPDATED_JOINING_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate not equals to DEFAULT_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.notEquals=" + DEFAULT_JOINING_DATE);

        // Get all the employeeList where joiningDate not equals to UPDATED_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.notEquals=" + UPDATED_JOINING_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate in DEFAULT_JOINING_DATE or UPDATED_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.in=" + DEFAULT_JOINING_DATE + "," + UPDATED_JOINING_DATE);

        // Get all the employeeList where joiningDate equals to UPDATED_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.in=" + UPDATED_JOINING_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate is not null
        defaultEmployeeShouldBeFound("joiningDate.specified=true");

        // Get all the employeeList where joiningDate is null
        defaultEmployeeShouldNotBeFound("joiningDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate is greater than or equal to DEFAULT_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.greaterThanOrEqual=" + DEFAULT_JOINING_DATE);

        // Get all the employeeList where joiningDate is greater than or equal to UPDATED_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.greaterThanOrEqual=" + UPDATED_JOINING_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate is less than or equal to DEFAULT_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.lessThanOrEqual=" + DEFAULT_JOINING_DATE);

        // Get all the employeeList where joiningDate is less than or equal to SMALLER_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.lessThanOrEqual=" + SMALLER_JOINING_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate is less than DEFAULT_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.lessThan=" + DEFAULT_JOINING_DATE);

        // Get all the employeeList where joiningDate is less than UPDATED_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.lessThan=" + UPDATED_JOINING_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate is greater than DEFAULT_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.greaterThan=" + DEFAULT_JOINING_DATE);

        // Get all the employeeList where joiningDate is greater than SMALLER_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.greaterThan=" + SMALLER_JOINING_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status equals to DEFAULT_STATUS
        defaultEmployeeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the employeeList where status equals to UPDATED_STATUS
        defaultEmployeeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status not equals to DEFAULT_STATUS
        defaultEmployeeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the employeeList where status not equals to UPDATED_STATUS
        defaultEmployeeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmployeeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the employeeList where status equals to UPDATED_STATUS
        defaultEmployeeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status is not null
        defaultEmployeeShouldBeFound("status.specified=true");

        // Get all the employeeList where status is null
        defaultEmployeeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate equals to DEFAULT_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.equals=" + DEFAULT_TERMINATION_DATE);

        // Get all the employeeList where terminationDate equals to UPDATED_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.equals=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate not equals to DEFAULT_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.notEquals=" + DEFAULT_TERMINATION_DATE);

        // Get all the employeeList where terminationDate not equals to UPDATED_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.notEquals=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate in DEFAULT_TERMINATION_DATE or UPDATED_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.in=" + DEFAULT_TERMINATION_DATE + "," + UPDATED_TERMINATION_DATE);

        // Get all the employeeList where terminationDate equals to UPDATED_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.in=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate is not null
        defaultEmployeeShouldBeFound("terminationDate.specified=true");

        // Get all the employeeList where terminationDate is null
        defaultEmployeeShouldNotBeFound("terminationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate is greater than or equal to DEFAULT_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.greaterThanOrEqual=" + DEFAULT_TERMINATION_DATE);

        // Get all the employeeList where terminationDate is greater than or equal to UPDATED_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.greaterThanOrEqual=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate is less than or equal to DEFAULT_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.lessThanOrEqual=" + DEFAULT_TERMINATION_DATE);

        // Get all the employeeList where terminationDate is less than or equal to SMALLER_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.lessThanOrEqual=" + SMALLER_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate is less than DEFAULT_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.lessThan=" + DEFAULT_TERMINATION_DATE);

        // Get all the employeeList where terminationDate is less than UPDATED_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.lessThan=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate is greater than DEFAULT_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.greaterThan=" + DEFAULT_TERMINATION_DATE);

        // Get all the employeeList where terminationDate is greater than SMALLER_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.greaterThan=" + SMALLER_TERMINATION_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByPartialSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        PartialSalary partialSalary = PartialSalaryResourceIT.createEntity(em);
        em.persist(partialSalary);
        em.flush();
        employee.addPartialSalary(partialSalary);
        employeeRepository.saveAndFlush(employee);
        Long partialSalaryId = partialSalary.getId();

        // Get all the employeeList where partialSalary equals to partialSalaryId
        defaultEmployeeShouldBeFound("partialSalaryId.equals=" + partialSalaryId);

        // Get all the employeeList where partialSalary equals to partialSalaryId + 1
        defaultEmployeeShouldNotBeFound("partialSalaryId.equals=" + (partialSalaryId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByOverTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        OverTime overTime = OverTimeResourceIT.createEntity(em);
        em.persist(overTime);
        em.flush();
        employee.addOverTime(overTime);
        employeeRepository.saveAndFlush(employee);
        Long overTimeId = overTime.getId();

        // Get all the employeeList where overTime equals to overTimeId
        defaultEmployeeShouldBeFound("overTimeId.equals=" + overTimeId);

        // Get all the employeeList where overTime equals to overTimeId + 1
        defaultEmployeeShouldNotBeFound("overTimeId.equals=" + (overTimeId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByFineIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Fine fine = FineResourceIT.createEntity(em);
        em.persist(fine);
        em.flush();
        employee.addFine(fine);
        employeeRepository.saveAndFlush(employee);
        Long fineId = fine.getId();

        // Get all the employeeList where fine equals to fineId
        defaultEmployeeShouldBeFound("fineId.equals=" + fineId);

        // Get all the employeeList where fine equals to fineId + 1
        defaultEmployeeShouldNotBeFound("fineId.equals=" + (fineId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByAdvanceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Advance advance = AdvanceResourceIT.createEntity(em);
        em.persist(advance);
        em.flush();
        employee.addAdvance(advance);
        employeeRepository.saveAndFlush(employee);
        Long advanceId = advance.getId();

        // Get all the employeeList where advance equals to advanceId
        defaultEmployeeShouldBeFound("advanceId.equals=" + advanceId);

        // Get all the employeeList where advance equals to advanceId + 1
        defaultEmployeeShouldNotBeFound("advanceId.equals=" + (advanceId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByEmployeeSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        EmployeeSalary employeeSalary = EmployeeSalaryResourceIT.createEntity(em);
        em.persist(employeeSalary);
        em.flush();
        employee.addEmployeeSalary(employeeSalary);
        employeeRepository.saveAndFlush(employee);
        Long employeeSalaryId = employeeSalary.getId();

        // Get all the employeeList where employeeSalary equals to employeeSalaryId
        defaultEmployeeShouldBeFound("employeeSalaryId.equals=" + employeeSalaryId);

        // Get all the employeeList where employeeSalary equals to employeeSalaryId + 1
        defaultEmployeeShouldNotBeFound("employeeSalaryId.equals=" + (employeeSalaryId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByEducationalInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        EducationalInfo educationalInfo = EducationalInfoResourceIT.createEntity(em);
        em.persist(educationalInfo);
        em.flush();
        employee.addEducationalInfo(educationalInfo);
        employeeRepository.saveAndFlush(employee);
        Long educationalInfoId = educationalInfo.getId();

        // Get all the employeeList where educationalInfo equals to educationalInfoId
        defaultEmployeeShouldBeFound("educationalInfoId.equals=" + educationalInfoId);

        // Get all the employeeList where educationalInfo equals to educationalInfoId + 1
        defaultEmployeeShouldNotBeFound("educationalInfoId.equals=" + (educationalInfoId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByTrainingIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Training training = TrainingResourceIT.createEntity(em);
        em.persist(training);
        em.flush();
        employee.addTraining(training);
        employeeRepository.saveAndFlush(employee);
        Long trainingId = training.getId();

        // Get all the employeeList where training equals to trainingId
        defaultEmployeeShouldBeFound("trainingId.equals=" + trainingId);

        // Get all the employeeList where training equals to trainingId + 1
        defaultEmployeeShouldNotBeFound("trainingId.equals=" + (trainingId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByEmployeeAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        EmployeeAccount employeeAccount = EmployeeAccountResourceIT.createEntity(em);
        em.persist(employeeAccount);
        em.flush();
        employee.addEmployeeAccount(employeeAccount);
        employeeRepository.saveAndFlush(employee);
        Long employeeAccountId = employeeAccount.getId();

        // Get all the employeeList where employeeAccount equals to employeeAccountId
        defaultEmployeeShouldBeFound("employeeAccountId.equals=" + employeeAccountId);

        // Get all the employeeList where employeeAccount equals to employeeAccountId + 1
        defaultEmployeeShouldNotBeFound("employeeAccountId.equals=" + (employeeAccountId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByJobHistoryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        JobHistory jobHistory = JobHistoryResourceIT.createEntity(em);
        em.persist(jobHistory);
        em.flush();
        employee.addJobHistory(jobHistory);
        employeeRepository.saveAndFlush(employee);
        Long jobHistoryId = jobHistory.getId();

        // Get all the employeeList where jobHistory equals to jobHistoryId
        defaultEmployeeShouldBeFound("jobHistoryId.equals=" + jobHistoryId);

        // Get all the employeeList where jobHistory equals to jobHistoryId + 1
        defaultEmployeeShouldNotBeFound("jobHistoryId.equals=" + (jobHistoryId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByServiceHistoryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        ServiceHistory serviceHistory = ServiceHistoryResourceIT.createEntity(em);
        em.persist(serviceHistory);
        em.flush();
        employee.addServiceHistory(serviceHistory);
        employeeRepository.saveAndFlush(employee);
        Long serviceHistoryId = serviceHistory.getId();

        // Get all the employeeList where serviceHistory equals to serviceHistoryId
        defaultEmployeeShouldBeFound("serviceHistoryId.equals=" + serviceHistoryId);

        // Get all the employeeList where serviceHistory equals to serviceHistoryId + 1
        defaultEmployeeShouldNotBeFound("serviceHistoryId.equals=" + (serviceHistoryId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        employee.setCompany(company);
        employeeRepository.saveAndFlush(employee);
        Long companyId = company.getId();

        // Get all the employeeList where company equals to companyId
        defaultEmployeeShouldBeFound("companyId.equals=" + companyId);

        // Get all the employeeList where company equals to companyId + 1
        defaultEmployeeShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        employee.setDepartment(department);
        employeeRepository.saveAndFlush(employee);
        Long departmentId = department.getId();

        // Get all the employeeList where department equals to departmentId
        defaultEmployeeShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the employeeList where department equals to departmentId + 1
        defaultEmployeeShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Grade grade = GradeResourceIT.createEntity(em);
        em.persist(grade);
        em.flush();
        employee.setGrade(grade);
        employeeRepository.saveAndFlush(employee);
        Long gradeId = grade.getId();

        // Get all the employeeList where grade equals to gradeId
        defaultEmployeeShouldBeFound("gradeId.equals=" + gradeId);

        // Get all the employeeList where grade equals to gradeId + 1
        defaultEmployeeShouldNotBeFound("gradeId.equals=" + (gradeId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Designation designation = DesignationResourceIT.createEntity(em);
        em.persist(designation);
        em.flush();
        employee.setDesignation(designation);
        employeeRepository.saveAndFlush(employee);
        Long designationId = designation.getId();

        // Get all the employeeList where designation equals to designationId
        defaultEmployeeShouldBeFound("designationId.equals=" + designationId);

        // Get all the employeeList where designation equals to designationId + 1
        defaultEmployeeShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByLineIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Line line = LineResourceIT.createEntity(em);
        em.persist(line);
        em.flush();
        employee.setLine(line);
        employeeRepository.saveAndFlush(employee);
        Long lineId = line.getId();

        // Get all the employeeList where line equals to lineId
        defaultEmployeeShouldBeFound("lineId.equals=" + lineId);

        // Get all the employeeList where line equals to lineId + 1
        defaultEmployeeShouldNotBeFound("lineId.equals=" + (lineId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Address address = AddressResourceIT.createEntity(em);
        em.persist(address);
        em.flush();
        employee.setAddress(address);
        address.setEmployee(employee);
        employeeRepository.saveAndFlush(employee);
        Long addressId = address.getId();

        // Get all the employeeList where address equals to addressId
        defaultEmployeeShouldBeFound("addressId.equals=" + addressId);

        // Get all the employeeList where address equals to addressId + 1
        defaultEmployeeShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByPersonalInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        PersonalInfo personalInfo = PersonalInfoResourceIT.createEntity(em);
        em.persist(personalInfo);
        em.flush();
        employee.setPersonalInfo(personalInfo);
        personalInfo.setEmployee(employee);
        employeeRepository.saveAndFlush(employee);
        Long personalInfoId = personalInfo.getId();

        // Get all the employeeList where personalInfo equals to personalInfoId
        defaultEmployeeShouldBeFound("personalInfoId.equals=" + personalInfoId);

        // Get all the employeeList where personalInfo equals to personalInfoId + 1
        defaultEmployeeShouldNotBeFound("personalInfoId.equals=" + (personalInfoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].empId").value(hasItem(DEFAULT_EMP_ID)))
            .andExpect(jsonPath("$.[*].globalId").value(hasItem(DEFAULT_GLOBAL_ID)))
            .andExpect(jsonPath("$.[*].attendanceMachineId").value(hasItem(DEFAULT_ATTENDANCE_MACHINE_ID)))
            .andExpect(jsonPath("$.[*].localId").value(hasItem(DEFAULT_LOCAL_ID)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].terminationReason").value(hasItem(DEFAULT_TERMINATION_REASON.toString())));

        // Check, that the count call also returns 1
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeService.save(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .name(UPDATED_NAME)
            .empId(UPDATED_EMP_ID)
            .globalId(UPDATED_GLOBAL_ID)
            .attendanceMachineId(UPDATED_ATTENDANCE_MACHINE_ID)
            .localId(UPDATED_LOCAL_ID)
            .category(UPDATED_CATEGORY)
            .type(UPDATED_TYPE)
            .joiningDate(UPDATED_JOINING_DATE)
            .status(UPDATED_STATUS)
            .terminationDate(UPDATED_TERMINATION_DATE)
            .terminationReason(UPDATED_TERMINATION_REASON);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployee)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployee.getEmpId()).isEqualTo(UPDATED_EMP_ID);
        assertThat(testEmployee.getGlobalId()).isEqualTo(UPDATED_GLOBAL_ID);
        assertThat(testEmployee.getAttendanceMachineId()).isEqualTo(UPDATED_ATTENDANCE_MACHINE_ID);
        assertThat(testEmployee.getLocalId()).isEqualTo(UPDATED_LOCAL_ID);
        assertThat(testEmployee.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testEmployee.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployee.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testEmployee.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployee.getTerminationDate()).isEqualTo(UPDATED_TERMINATION_DATE);
        assertThat(testEmployee.getTerminationReason()).isEqualTo(UPDATED_TERMINATION_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeService.save(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
