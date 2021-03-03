package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.Employee;
import software.cstl.repository.EmployeeSalaryRepository;
import software.cstl.service.EmployeeSalaryService;
import software.cstl.service.dto.EmployeeSalaryCriteria;
import software.cstl.service.EmployeeSalaryQueryService;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.InsuranceProcessType;
import software.cstl.domain.enumeration.ActiveStatus;
/**
 * Integration tests for the {@link EmployeeSalaryResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeSalaryResourceIT {

    private static final BigDecimal DEFAULT_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_GROSS = new BigDecimal(2);
    private static final BigDecimal SMALLER_GROSS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_INCREMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INCREMENT_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_INCREMENT_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_INCREMENT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_INCREMENT_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_INCREMENT_PERCENTAGE = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_SALARY_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SALARY_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SALARY_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SALARY_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NEXT_INCREMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NEXT_INCREMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_BASIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC = new BigDecimal(2);
    private static final BigDecimal SMALLER_BASIC = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_BASIC_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_BASIC_PERCENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HOUSE_RENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOUSE_RENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_HOUSE_RENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HOUSE_RENT_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOUSE_RENT_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_HOUSE_RENT_PERCENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_ALLOWANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_ALLOWANCE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MEDICAL_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MEDICAL_ALLOWANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_MEDICAL_ALLOWANCE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MEDICAL_ALLOWANCE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MEDICAL_ALLOWANCE_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_MEDICAL_ALLOWANCE_PERCENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CONVINCE_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONVINCE_ALLOWANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CONVINCE_ALLOWANCE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CONVINCE_ALLOWANCE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONVINCE_ALLOWANCE_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_CONVINCE_ALLOWANCE_PERCENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOOD_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOOD_ALLOWANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOOD_ALLOWANCE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOOD_ALLOWANCE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOOD_ALLOWANCE_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOOD_ALLOWANCE_PERCENT = new BigDecimal(1 - 1);

    private static final ActiveStatus DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS = ActiveStatus.ACTIVE;
    private static final ActiveStatus UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS = ActiveStatus.NOT_ACTIVE;

    private static final BigDecimal DEFAULT_SPECIAL_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SPECIAL_ALLOWANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SPECIAL_ALLOWANCE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SPECIAL_ALLOWANCE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_SPECIAL_ALLOWANCE_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_SPECIAL_ALLOWANCE_PERCENT = new BigDecimal(1 - 1);

    private static final String DEFAULT_SPECIAL_ALLOWANCE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_ALLOWANCE_DESCRIPTION = "BBBBBBBBBB";

    private static final ActiveStatus DEFAULT_INSURANCE_ACTIVE_STATUS = ActiveStatus.ACTIVE;
    private static final ActiveStatus UPDATED_INSURANCE_ACTIVE_STATUS = ActiveStatus.NOT_ACTIVE;

    private static final BigDecimal DEFAULT_INSURANCE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INSURANCE_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_INSURANCE_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_INSURANCE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INSURANCE_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_INSURANCE_PERCENT = new BigDecimal(1 - 1);

    private static final String DEFAULT_INSURANCE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_INSURANCE_DESCRIPTION = "BBBBBBBBBB";

    private static final InsuranceProcessType DEFAULT_INSURANCE_PROCESS_TYPE = InsuranceProcessType.PROCESS_WITH_SALARY;
    private static final InsuranceProcessType UPDATED_INSURANCE_PROCESS_TYPE = InsuranceProcessType.PROCESS_SEPARATELY;

    private static final ActiveStatus DEFAULT_STATUS = ActiveStatus.ACTIVE;
    private static final ActiveStatus UPDATED_STATUS = ActiveStatus.NOT_ACTIVE;

    @Autowired
    private EmployeeSalaryRepository employeeSalaryRepository;

    @Autowired
    private EmployeeSalaryService employeeSalaryService;

    @Autowired
    private EmployeeSalaryQueryService employeeSalaryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeSalaryMockMvc;

    private EmployeeSalary employeeSalary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSalary createEntity(EntityManager em) {
        EmployeeSalary employeeSalary = new EmployeeSalary()
            .gross(DEFAULT_GROSS)
            .incrementAmount(DEFAULT_INCREMENT_AMOUNT)
            .incrementPercentage(DEFAULT_INCREMENT_PERCENTAGE)
            .salaryStartDate(DEFAULT_SALARY_START_DATE)
            .salaryEndDate(DEFAULT_SALARY_END_DATE)
            .nextIncrementDate(DEFAULT_NEXT_INCREMENT_DATE)
            .basic(DEFAULT_BASIC)
            .basicPercent(DEFAULT_BASIC_PERCENT)
            .houseRent(DEFAULT_HOUSE_RENT)
            .houseRentPercent(DEFAULT_HOUSE_RENT_PERCENT)
            .totalAllowance(DEFAULT_TOTAL_ALLOWANCE)
            .medicalAllowance(DEFAULT_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(DEFAULT_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(DEFAULT_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(DEFAULT_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(DEFAULT_FOOD_ALLOWANCE)
            .foodAllowancePercent(DEFAULT_FOOD_ALLOWANCE_PERCENT)
            .specialAllowanceActiveStatus(DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS)
            .specialAllowance(DEFAULT_SPECIAL_ALLOWANCE)
            .specialAllowancePercent(DEFAULT_SPECIAL_ALLOWANCE_PERCENT)
            .specialAllowanceDescription(DEFAULT_SPECIAL_ALLOWANCE_DESCRIPTION)
            .insuranceActiveStatus(DEFAULT_INSURANCE_ACTIVE_STATUS)
            .insuranceAmount(DEFAULT_INSURANCE_AMOUNT)
            .insurancePercent(DEFAULT_INSURANCE_PERCENT)
            .insuranceDescription(DEFAULT_INSURANCE_DESCRIPTION)
            .insuranceProcessType(DEFAULT_INSURANCE_PROCESS_TYPE)
            .status(DEFAULT_STATUS);
        return employeeSalary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSalary createUpdatedEntity(EntityManager em) {
        EmployeeSalary employeeSalary = new EmployeeSalary()
            .gross(UPDATED_GROSS)
            .incrementAmount(UPDATED_INCREMENT_AMOUNT)
            .incrementPercentage(UPDATED_INCREMENT_PERCENTAGE)
            .salaryStartDate(UPDATED_SALARY_START_DATE)
            .salaryEndDate(UPDATED_SALARY_END_DATE)
            .nextIncrementDate(UPDATED_NEXT_INCREMENT_DATE)
            .basic(UPDATED_BASIC)
            .basicPercent(UPDATED_BASIC_PERCENT)
            .houseRent(UPDATED_HOUSE_RENT)
            .houseRentPercent(UPDATED_HOUSE_RENT_PERCENT)
            .totalAllowance(UPDATED_TOTAL_ALLOWANCE)
            .medicalAllowance(UPDATED_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(UPDATED_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(UPDATED_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(UPDATED_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(UPDATED_FOOD_ALLOWANCE)
            .foodAllowancePercent(UPDATED_FOOD_ALLOWANCE_PERCENT)
            .specialAllowanceActiveStatus(UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS)
            .specialAllowance(UPDATED_SPECIAL_ALLOWANCE)
            .specialAllowancePercent(UPDATED_SPECIAL_ALLOWANCE_PERCENT)
            .specialAllowanceDescription(UPDATED_SPECIAL_ALLOWANCE_DESCRIPTION)
            .insuranceActiveStatus(UPDATED_INSURANCE_ACTIVE_STATUS)
            .insuranceAmount(UPDATED_INSURANCE_AMOUNT)
            .insurancePercent(UPDATED_INSURANCE_PERCENT)
            .insuranceDescription(UPDATED_INSURANCE_DESCRIPTION)
            .insuranceProcessType(UPDATED_INSURANCE_PROCESS_TYPE)
            .status(UPDATED_STATUS);
        return employeeSalary;
    }

    @BeforeEach
    public void initTest() {
        employeeSalary = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeSalary() throws Exception {
        int databaseSizeBeforeCreate = employeeSalaryRepository.findAll().size();
        // Create the EmployeeSalary
        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isCreated());

        // Validate the EmployeeSalary in the database
        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeSalary testEmployeeSalary = employeeSalaryList.get(employeeSalaryList.size() - 1);
        assertThat(testEmployeeSalary.getGross()).isEqualTo(DEFAULT_GROSS);
        assertThat(testEmployeeSalary.getIncrementAmount()).isEqualTo(DEFAULT_INCREMENT_AMOUNT);
        assertThat(testEmployeeSalary.getIncrementPercentage()).isEqualTo(DEFAULT_INCREMENT_PERCENTAGE);
        assertThat(testEmployeeSalary.getSalaryStartDate()).isEqualTo(DEFAULT_SALARY_START_DATE);
        assertThat(testEmployeeSalary.getSalaryEndDate()).isEqualTo(DEFAULT_SALARY_END_DATE);
        assertThat(testEmployeeSalary.getNextIncrementDate()).isEqualTo(DEFAULT_NEXT_INCREMENT_DATE);
        assertThat(testEmployeeSalary.getBasic()).isEqualTo(DEFAULT_BASIC);
        assertThat(testEmployeeSalary.getBasicPercent()).isEqualTo(DEFAULT_BASIC_PERCENT);
        assertThat(testEmployeeSalary.getHouseRent()).isEqualTo(DEFAULT_HOUSE_RENT);
        assertThat(testEmployeeSalary.getHouseRentPercent()).isEqualTo(DEFAULT_HOUSE_RENT_PERCENT);
        assertThat(testEmployeeSalary.getTotalAllowance()).isEqualTo(DEFAULT_TOTAL_ALLOWANCE);
        assertThat(testEmployeeSalary.getMedicalAllowance()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE);
        assertThat(testEmployeeSalary.getMedicalAllowancePercent()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE_PERCENT);
        assertThat(testEmployeeSalary.getConvinceAllowance()).isEqualTo(DEFAULT_CONVINCE_ALLOWANCE);
        assertThat(testEmployeeSalary.getConvinceAllowancePercent()).isEqualTo(DEFAULT_CONVINCE_ALLOWANCE_PERCENT);
        assertThat(testEmployeeSalary.getFoodAllowance()).isEqualTo(DEFAULT_FOOD_ALLOWANCE);
        assertThat(testEmployeeSalary.getFoodAllowancePercent()).isEqualTo(DEFAULT_FOOD_ALLOWANCE_PERCENT);
        assertThat(testEmployeeSalary.getSpecialAllowanceActiveStatus()).isEqualTo(DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS);
        assertThat(testEmployeeSalary.getSpecialAllowance()).isEqualTo(DEFAULT_SPECIAL_ALLOWANCE);
        assertThat(testEmployeeSalary.getSpecialAllowancePercent()).isEqualTo(DEFAULT_SPECIAL_ALLOWANCE_PERCENT);
        assertThat(testEmployeeSalary.getSpecialAllowanceDescription()).isEqualTo(DEFAULT_SPECIAL_ALLOWANCE_DESCRIPTION);
        assertThat(testEmployeeSalary.getInsuranceActiveStatus()).isEqualTo(DEFAULT_INSURANCE_ACTIVE_STATUS);
        assertThat(testEmployeeSalary.getInsuranceAmount()).isEqualTo(DEFAULT_INSURANCE_AMOUNT);
        assertThat(testEmployeeSalary.getInsurancePercent()).isEqualTo(DEFAULT_INSURANCE_PERCENT);
        assertThat(testEmployeeSalary.getInsuranceDescription()).isEqualTo(DEFAULT_INSURANCE_DESCRIPTION);
        assertThat(testEmployeeSalary.getInsuranceProcessType()).isEqualTo(DEFAULT_INSURANCE_PROCESS_TYPE);
        assertThat(testEmployeeSalary.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEmployeeSalaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeSalaryRepository.findAll().size();

        // Create the EmployeeSalary with an existing ID
        employeeSalary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSalary in the database
        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGrossIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSalaryRepository.findAll().size();
        // set the field null
        employeeSalary.setGross(null);

        // Create the EmployeeSalary, which fails.


        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncrementAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSalaryRepository.findAll().size();
        // set the field null
        employeeSalary.setIncrementAmount(null);

        // Create the EmployeeSalary, which fails.


        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaryStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSalaryRepository.findAll().size();
        // set the field null
        employeeSalary.setSalaryStartDate(null);

        // Create the EmployeeSalary, which fails.


        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaryEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSalaryRepository.findAll().size();
        // set the field null
        employeeSalary.setSalaryEndDate(null);

        // Create the EmployeeSalary, which fails.


        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBasicIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSalaryRepository.findAll().size();
        // set the field null
        employeeSalary.setBasic(null);

        // Create the EmployeeSalary, which fails.


        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHouseRentIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSalaryRepository.findAll().size();
        // set the field null
        employeeSalary.setHouseRent(null);

        // Create the EmployeeSalary, which fails.


        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSalaryRepository.findAll().size();
        // set the field null
        employeeSalary.setStatus(null);

        // Create the EmployeeSalary, which fails.


        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalaries() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList
        restEmployeeSalaryMockMvc.perform(get("/api/employee-salaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].gross").value(hasItem(DEFAULT_GROSS.intValue())))
            .andExpect(jsonPath("$.[*].incrementAmount").value(hasItem(DEFAULT_INCREMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].incrementPercentage").value(hasItem(DEFAULT_INCREMENT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].salaryStartDate").value(hasItem(DEFAULT_SALARY_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].salaryEndDate").value(hasItem(DEFAULT_SALARY_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].nextIncrementDate").value(hasItem(DEFAULT_NEXT_INCREMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].basicPercent").value(hasItem(DEFAULT_BASIC_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRentPercent").value(hasItem(DEFAULT_HOUSE_RENT_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].totalAllowance").value(hasItem(DEFAULT_TOTAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowancePercent").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowance").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowancePercent").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowancePercent").value(hasItem(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].specialAllowanceActiveStatus").value(hasItem(DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].specialAllowance").value(hasItem(DEFAULT_SPECIAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].specialAllowancePercent").value(hasItem(DEFAULT_SPECIAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].specialAllowanceDescription").value(hasItem(DEFAULT_SPECIAL_ALLOWANCE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].insuranceActiveStatus").value(hasItem(DEFAULT_INSURANCE_ACTIVE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].insuranceAmount").value(hasItem(DEFAULT_INSURANCE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].insurancePercent").value(hasItem(DEFAULT_INSURANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].insuranceDescription").value(hasItem(DEFAULT_INSURANCE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].insuranceProcessType").value(hasItem(DEFAULT_INSURANCE_PROCESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployeeSalary() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get the employeeSalary
        restEmployeeSalaryMockMvc.perform(get("/api/employee-salaries/{id}", employeeSalary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeSalary.getId().intValue()))
            .andExpect(jsonPath("$.gross").value(DEFAULT_GROSS.intValue()))
            .andExpect(jsonPath("$.incrementAmount").value(DEFAULT_INCREMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.incrementPercentage").value(DEFAULT_INCREMENT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.salaryStartDate").value(DEFAULT_SALARY_START_DATE.toString()))
            .andExpect(jsonPath("$.salaryEndDate").value(DEFAULT_SALARY_END_DATE.toString()))
            .andExpect(jsonPath("$.nextIncrementDate").value(DEFAULT_NEXT_INCREMENT_DATE.toString()))
            .andExpect(jsonPath("$.basic").value(DEFAULT_BASIC.intValue()))
            .andExpect(jsonPath("$.basicPercent").value(DEFAULT_BASIC_PERCENT.intValue()))
            .andExpect(jsonPath("$.houseRent").value(DEFAULT_HOUSE_RENT.intValue()))
            .andExpect(jsonPath("$.houseRentPercent").value(DEFAULT_HOUSE_RENT_PERCENT.intValue()))
            .andExpect(jsonPath("$.totalAllowance").value(DEFAULT_TOTAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.medicalAllowance").value(DEFAULT_MEDICAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.medicalAllowancePercent").value(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.convinceAllowance").value(DEFAULT_CONVINCE_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.convinceAllowancePercent").value(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.foodAllowance").value(DEFAULT_FOOD_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.foodAllowancePercent").value(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.specialAllowanceActiveStatus").value(DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS.toString()))
            .andExpect(jsonPath("$.specialAllowance").value(DEFAULT_SPECIAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.specialAllowancePercent").value(DEFAULT_SPECIAL_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.specialAllowanceDescription").value(DEFAULT_SPECIAL_ALLOWANCE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.insuranceActiveStatus").value(DEFAULT_INSURANCE_ACTIVE_STATUS.toString()))
            .andExpect(jsonPath("$.insuranceAmount").value(DEFAULT_INSURANCE_AMOUNT.intValue()))
            .andExpect(jsonPath("$.insurancePercent").value(DEFAULT_INSURANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.insuranceDescription").value(DEFAULT_INSURANCE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.insuranceProcessType").value(DEFAULT_INSURANCE_PROCESS_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getEmployeeSalariesByIdFiltering() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        Long id = employeeSalary.getId();

        defaultEmployeeSalaryShouldBeFound("id.equals=" + id);
        defaultEmployeeSalaryShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeSalaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeSalaryShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeSalaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeSalaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByGrossIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where gross equals to DEFAULT_GROSS
        defaultEmployeeSalaryShouldBeFound("gross.equals=" + DEFAULT_GROSS);

        // Get all the employeeSalaryList where gross equals to UPDATED_GROSS
        defaultEmployeeSalaryShouldNotBeFound("gross.equals=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByGrossIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where gross not equals to DEFAULT_GROSS
        defaultEmployeeSalaryShouldNotBeFound("gross.notEquals=" + DEFAULT_GROSS);

        // Get all the employeeSalaryList where gross not equals to UPDATED_GROSS
        defaultEmployeeSalaryShouldBeFound("gross.notEquals=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByGrossIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where gross in DEFAULT_GROSS or UPDATED_GROSS
        defaultEmployeeSalaryShouldBeFound("gross.in=" + DEFAULT_GROSS + "," + UPDATED_GROSS);

        // Get all the employeeSalaryList where gross equals to UPDATED_GROSS
        defaultEmployeeSalaryShouldNotBeFound("gross.in=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByGrossIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where gross is not null
        defaultEmployeeSalaryShouldBeFound("gross.specified=true");

        // Get all the employeeSalaryList where gross is null
        defaultEmployeeSalaryShouldNotBeFound("gross.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByGrossIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where gross is greater than or equal to DEFAULT_GROSS
        defaultEmployeeSalaryShouldBeFound("gross.greaterThanOrEqual=" + DEFAULT_GROSS);

        // Get all the employeeSalaryList where gross is greater than or equal to UPDATED_GROSS
        defaultEmployeeSalaryShouldNotBeFound("gross.greaterThanOrEqual=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByGrossIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where gross is less than or equal to DEFAULT_GROSS
        defaultEmployeeSalaryShouldBeFound("gross.lessThanOrEqual=" + DEFAULT_GROSS);

        // Get all the employeeSalaryList where gross is less than or equal to SMALLER_GROSS
        defaultEmployeeSalaryShouldNotBeFound("gross.lessThanOrEqual=" + SMALLER_GROSS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByGrossIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where gross is less than DEFAULT_GROSS
        defaultEmployeeSalaryShouldNotBeFound("gross.lessThan=" + DEFAULT_GROSS);

        // Get all the employeeSalaryList where gross is less than UPDATED_GROSS
        defaultEmployeeSalaryShouldBeFound("gross.lessThan=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByGrossIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where gross is greater than DEFAULT_GROSS
        defaultEmployeeSalaryShouldNotBeFound("gross.greaterThan=" + DEFAULT_GROSS);

        // Get all the employeeSalaryList where gross is greater than SMALLER_GROSS
        defaultEmployeeSalaryShouldBeFound("gross.greaterThan=" + SMALLER_GROSS);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementAmount equals to DEFAULT_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldBeFound("incrementAmount.equals=" + DEFAULT_INCREMENT_AMOUNT);

        // Get all the employeeSalaryList where incrementAmount equals to UPDATED_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("incrementAmount.equals=" + UPDATED_INCREMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementAmount not equals to DEFAULT_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("incrementAmount.notEquals=" + DEFAULT_INCREMENT_AMOUNT);

        // Get all the employeeSalaryList where incrementAmount not equals to UPDATED_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldBeFound("incrementAmount.notEquals=" + UPDATED_INCREMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementAmountIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementAmount in DEFAULT_INCREMENT_AMOUNT or UPDATED_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldBeFound("incrementAmount.in=" + DEFAULT_INCREMENT_AMOUNT + "," + UPDATED_INCREMENT_AMOUNT);

        // Get all the employeeSalaryList where incrementAmount equals to UPDATED_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("incrementAmount.in=" + UPDATED_INCREMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementAmount is not null
        defaultEmployeeSalaryShouldBeFound("incrementAmount.specified=true");

        // Get all the employeeSalaryList where incrementAmount is null
        defaultEmployeeSalaryShouldNotBeFound("incrementAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementAmount is greater than or equal to DEFAULT_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldBeFound("incrementAmount.greaterThanOrEqual=" + DEFAULT_INCREMENT_AMOUNT);

        // Get all the employeeSalaryList where incrementAmount is greater than or equal to UPDATED_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("incrementAmount.greaterThanOrEqual=" + UPDATED_INCREMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementAmount is less than or equal to DEFAULT_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldBeFound("incrementAmount.lessThanOrEqual=" + DEFAULT_INCREMENT_AMOUNT);

        // Get all the employeeSalaryList where incrementAmount is less than or equal to SMALLER_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("incrementAmount.lessThanOrEqual=" + SMALLER_INCREMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementAmount is less than DEFAULT_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("incrementAmount.lessThan=" + DEFAULT_INCREMENT_AMOUNT);

        // Get all the employeeSalaryList where incrementAmount is less than UPDATED_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldBeFound("incrementAmount.lessThan=" + UPDATED_INCREMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementAmount is greater than DEFAULT_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("incrementAmount.greaterThan=" + DEFAULT_INCREMENT_AMOUNT);

        // Get all the employeeSalaryList where incrementAmount is greater than SMALLER_INCREMENT_AMOUNT
        defaultEmployeeSalaryShouldBeFound("incrementAmount.greaterThan=" + SMALLER_INCREMENT_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementPercentage equals to DEFAULT_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldBeFound("incrementPercentage.equals=" + DEFAULT_INCREMENT_PERCENTAGE);

        // Get all the employeeSalaryList where incrementPercentage equals to UPDATED_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldNotBeFound("incrementPercentage.equals=" + UPDATED_INCREMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementPercentage not equals to DEFAULT_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldNotBeFound("incrementPercentage.notEquals=" + DEFAULT_INCREMENT_PERCENTAGE);

        // Get all the employeeSalaryList where incrementPercentage not equals to UPDATED_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldBeFound("incrementPercentage.notEquals=" + UPDATED_INCREMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementPercentage in DEFAULT_INCREMENT_PERCENTAGE or UPDATED_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldBeFound("incrementPercentage.in=" + DEFAULT_INCREMENT_PERCENTAGE + "," + UPDATED_INCREMENT_PERCENTAGE);

        // Get all the employeeSalaryList where incrementPercentage equals to UPDATED_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldNotBeFound("incrementPercentage.in=" + UPDATED_INCREMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementPercentage is not null
        defaultEmployeeSalaryShouldBeFound("incrementPercentage.specified=true");

        // Get all the employeeSalaryList where incrementPercentage is null
        defaultEmployeeSalaryShouldNotBeFound("incrementPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementPercentage is greater than or equal to DEFAULT_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldBeFound("incrementPercentage.greaterThanOrEqual=" + DEFAULT_INCREMENT_PERCENTAGE);

        // Get all the employeeSalaryList where incrementPercentage is greater than or equal to UPDATED_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldNotBeFound("incrementPercentage.greaterThanOrEqual=" + UPDATED_INCREMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementPercentage is less than or equal to DEFAULT_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldBeFound("incrementPercentage.lessThanOrEqual=" + DEFAULT_INCREMENT_PERCENTAGE);

        // Get all the employeeSalaryList where incrementPercentage is less than or equal to SMALLER_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldNotBeFound("incrementPercentage.lessThanOrEqual=" + SMALLER_INCREMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementPercentage is less than DEFAULT_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldNotBeFound("incrementPercentage.lessThan=" + DEFAULT_INCREMENT_PERCENTAGE);

        // Get all the employeeSalaryList where incrementPercentage is less than UPDATED_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldBeFound("incrementPercentage.lessThan=" + UPDATED_INCREMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByIncrementPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where incrementPercentage is greater than DEFAULT_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldNotBeFound("incrementPercentage.greaterThan=" + DEFAULT_INCREMENT_PERCENTAGE);

        // Get all the employeeSalaryList where incrementPercentage is greater than SMALLER_INCREMENT_PERCENTAGE
        defaultEmployeeSalaryShouldBeFound("incrementPercentage.greaterThan=" + SMALLER_INCREMENT_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesBySalaryStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where salaryStartDate equals to DEFAULT_SALARY_START_DATE
        defaultEmployeeSalaryShouldBeFound("salaryStartDate.equals=" + DEFAULT_SALARY_START_DATE);

        // Get all the employeeSalaryList where salaryStartDate equals to UPDATED_SALARY_START_DATE
        defaultEmployeeSalaryShouldNotBeFound("salaryStartDate.equals=" + UPDATED_SALARY_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySalaryStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where salaryStartDate not equals to DEFAULT_SALARY_START_DATE
        defaultEmployeeSalaryShouldNotBeFound("salaryStartDate.notEquals=" + DEFAULT_SALARY_START_DATE);

        // Get all the employeeSalaryList where salaryStartDate not equals to UPDATED_SALARY_START_DATE
        defaultEmployeeSalaryShouldBeFound("salaryStartDate.notEquals=" + UPDATED_SALARY_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySalaryStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where salaryStartDate in DEFAULT_SALARY_START_DATE or UPDATED_SALARY_START_DATE
        defaultEmployeeSalaryShouldBeFound("salaryStartDate.in=" + DEFAULT_SALARY_START_DATE + "," + UPDATED_SALARY_START_DATE);

        // Get all the employeeSalaryList where salaryStartDate equals to UPDATED_SALARY_START_DATE
        defaultEmployeeSalaryShouldNotBeFound("salaryStartDate.in=" + UPDATED_SALARY_START_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySalaryStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where salaryStartDate is not null
        defaultEmployeeSalaryShouldBeFound("salaryStartDate.specified=true");

        // Get all the employeeSalaryList where salaryStartDate is null
        defaultEmployeeSalaryShouldNotBeFound("salaryStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySalaryEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where salaryEndDate equals to DEFAULT_SALARY_END_DATE
        defaultEmployeeSalaryShouldBeFound("salaryEndDate.equals=" + DEFAULT_SALARY_END_DATE);

        // Get all the employeeSalaryList where salaryEndDate equals to UPDATED_SALARY_END_DATE
        defaultEmployeeSalaryShouldNotBeFound("salaryEndDate.equals=" + UPDATED_SALARY_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySalaryEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where salaryEndDate not equals to DEFAULT_SALARY_END_DATE
        defaultEmployeeSalaryShouldNotBeFound("salaryEndDate.notEquals=" + DEFAULT_SALARY_END_DATE);

        // Get all the employeeSalaryList where salaryEndDate not equals to UPDATED_SALARY_END_DATE
        defaultEmployeeSalaryShouldBeFound("salaryEndDate.notEquals=" + UPDATED_SALARY_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySalaryEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where salaryEndDate in DEFAULT_SALARY_END_DATE or UPDATED_SALARY_END_DATE
        defaultEmployeeSalaryShouldBeFound("salaryEndDate.in=" + DEFAULT_SALARY_END_DATE + "," + UPDATED_SALARY_END_DATE);

        // Get all the employeeSalaryList where salaryEndDate equals to UPDATED_SALARY_END_DATE
        defaultEmployeeSalaryShouldNotBeFound("salaryEndDate.in=" + UPDATED_SALARY_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySalaryEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where salaryEndDate is not null
        defaultEmployeeSalaryShouldBeFound("salaryEndDate.specified=true");

        // Get all the employeeSalaryList where salaryEndDate is null
        defaultEmployeeSalaryShouldNotBeFound("salaryEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByNextIncrementDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where nextIncrementDate equals to DEFAULT_NEXT_INCREMENT_DATE
        defaultEmployeeSalaryShouldBeFound("nextIncrementDate.equals=" + DEFAULT_NEXT_INCREMENT_DATE);

        // Get all the employeeSalaryList where nextIncrementDate equals to UPDATED_NEXT_INCREMENT_DATE
        defaultEmployeeSalaryShouldNotBeFound("nextIncrementDate.equals=" + UPDATED_NEXT_INCREMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByNextIncrementDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where nextIncrementDate not equals to DEFAULT_NEXT_INCREMENT_DATE
        defaultEmployeeSalaryShouldNotBeFound("nextIncrementDate.notEquals=" + DEFAULT_NEXT_INCREMENT_DATE);

        // Get all the employeeSalaryList where nextIncrementDate not equals to UPDATED_NEXT_INCREMENT_DATE
        defaultEmployeeSalaryShouldBeFound("nextIncrementDate.notEquals=" + UPDATED_NEXT_INCREMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByNextIncrementDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where nextIncrementDate in DEFAULT_NEXT_INCREMENT_DATE or UPDATED_NEXT_INCREMENT_DATE
        defaultEmployeeSalaryShouldBeFound("nextIncrementDate.in=" + DEFAULT_NEXT_INCREMENT_DATE + "," + UPDATED_NEXT_INCREMENT_DATE);

        // Get all the employeeSalaryList where nextIncrementDate equals to UPDATED_NEXT_INCREMENT_DATE
        defaultEmployeeSalaryShouldNotBeFound("nextIncrementDate.in=" + UPDATED_NEXT_INCREMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByNextIncrementDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where nextIncrementDate is not null
        defaultEmployeeSalaryShouldBeFound("nextIncrementDate.specified=true");

        // Get all the employeeSalaryList where nextIncrementDate is null
        defaultEmployeeSalaryShouldNotBeFound("nextIncrementDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basic equals to DEFAULT_BASIC
        defaultEmployeeSalaryShouldBeFound("basic.equals=" + DEFAULT_BASIC);

        // Get all the employeeSalaryList where basic equals to UPDATED_BASIC
        defaultEmployeeSalaryShouldNotBeFound("basic.equals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basic not equals to DEFAULT_BASIC
        defaultEmployeeSalaryShouldNotBeFound("basic.notEquals=" + DEFAULT_BASIC);

        // Get all the employeeSalaryList where basic not equals to UPDATED_BASIC
        defaultEmployeeSalaryShouldBeFound("basic.notEquals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basic in DEFAULT_BASIC or UPDATED_BASIC
        defaultEmployeeSalaryShouldBeFound("basic.in=" + DEFAULT_BASIC + "," + UPDATED_BASIC);

        // Get all the employeeSalaryList where basic equals to UPDATED_BASIC
        defaultEmployeeSalaryShouldNotBeFound("basic.in=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basic is not null
        defaultEmployeeSalaryShouldBeFound("basic.specified=true");

        // Get all the employeeSalaryList where basic is null
        defaultEmployeeSalaryShouldNotBeFound("basic.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basic is greater than or equal to DEFAULT_BASIC
        defaultEmployeeSalaryShouldBeFound("basic.greaterThanOrEqual=" + DEFAULT_BASIC);

        // Get all the employeeSalaryList where basic is greater than or equal to UPDATED_BASIC
        defaultEmployeeSalaryShouldNotBeFound("basic.greaterThanOrEqual=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basic is less than or equal to DEFAULT_BASIC
        defaultEmployeeSalaryShouldBeFound("basic.lessThanOrEqual=" + DEFAULT_BASIC);

        // Get all the employeeSalaryList where basic is less than or equal to SMALLER_BASIC
        defaultEmployeeSalaryShouldNotBeFound("basic.lessThanOrEqual=" + SMALLER_BASIC);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basic is less than DEFAULT_BASIC
        defaultEmployeeSalaryShouldNotBeFound("basic.lessThan=" + DEFAULT_BASIC);

        // Get all the employeeSalaryList where basic is less than UPDATED_BASIC
        defaultEmployeeSalaryShouldBeFound("basic.lessThan=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basic is greater than DEFAULT_BASIC
        defaultEmployeeSalaryShouldNotBeFound("basic.greaterThan=" + DEFAULT_BASIC);

        // Get all the employeeSalaryList where basic is greater than SMALLER_BASIC
        defaultEmployeeSalaryShouldBeFound("basic.greaterThan=" + SMALLER_BASIC);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basicPercent equals to DEFAULT_BASIC_PERCENT
        defaultEmployeeSalaryShouldBeFound("basicPercent.equals=" + DEFAULT_BASIC_PERCENT);

        // Get all the employeeSalaryList where basicPercent equals to UPDATED_BASIC_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("basicPercent.equals=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basicPercent not equals to DEFAULT_BASIC_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("basicPercent.notEquals=" + DEFAULT_BASIC_PERCENT);

        // Get all the employeeSalaryList where basicPercent not equals to UPDATED_BASIC_PERCENT
        defaultEmployeeSalaryShouldBeFound("basicPercent.notEquals=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicPercentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basicPercent in DEFAULT_BASIC_PERCENT or UPDATED_BASIC_PERCENT
        defaultEmployeeSalaryShouldBeFound("basicPercent.in=" + DEFAULT_BASIC_PERCENT + "," + UPDATED_BASIC_PERCENT);

        // Get all the employeeSalaryList where basicPercent equals to UPDATED_BASIC_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("basicPercent.in=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basicPercent is not null
        defaultEmployeeSalaryShouldBeFound("basicPercent.specified=true");

        // Get all the employeeSalaryList where basicPercent is null
        defaultEmployeeSalaryShouldNotBeFound("basicPercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basicPercent is greater than or equal to DEFAULT_BASIC_PERCENT
        defaultEmployeeSalaryShouldBeFound("basicPercent.greaterThanOrEqual=" + DEFAULT_BASIC_PERCENT);

        // Get all the employeeSalaryList where basicPercent is greater than or equal to UPDATED_BASIC_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("basicPercent.greaterThanOrEqual=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basicPercent is less than or equal to DEFAULT_BASIC_PERCENT
        defaultEmployeeSalaryShouldBeFound("basicPercent.lessThanOrEqual=" + DEFAULT_BASIC_PERCENT);

        // Get all the employeeSalaryList where basicPercent is less than or equal to SMALLER_BASIC_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("basicPercent.lessThanOrEqual=" + SMALLER_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basicPercent is less than DEFAULT_BASIC_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("basicPercent.lessThan=" + DEFAULT_BASIC_PERCENT);

        // Get all the employeeSalaryList where basicPercent is less than UPDATED_BASIC_PERCENT
        defaultEmployeeSalaryShouldBeFound("basicPercent.lessThan=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByBasicPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where basicPercent is greater than DEFAULT_BASIC_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("basicPercent.greaterThan=" + DEFAULT_BASIC_PERCENT);

        // Get all the employeeSalaryList where basicPercent is greater than SMALLER_BASIC_PERCENT
        defaultEmployeeSalaryShouldBeFound("basicPercent.greaterThan=" + SMALLER_BASIC_PERCENT);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRent equals to DEFAULT_HOUSE_RENT
        defaultEmployeeSalaryShouldBeFound("houseRent.equals=" + DEFAULT_HOUSE_RENT);

        // Get all the employeeSalaryList where houseRent equals to UPDATED_HOUSE_RENT
        defaultEmployeeSalaryShouldNotBeFound("houseRent.equals=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRent not equals to DEFAULT_HOUSE_RENT
        defaultEmployeeSalaryShouldNotBeFound("houseRent.notEquals=" + DEFAULT_HOUSE_RENT);

        // Get all the employeeSalaryList where houseRent not equals to UPDATED_HOUSE_RENT
        defaultEmployeeSalaryShouldBeFound("houseRent.notEquals=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRent in DEFAULT_HOUSE_RENT or UPDATED_HOUSE_RENT
        defaultEmployeeSalaryShouldBeFound("houseRent.in=" + DEFAULT_HOUSE_RENT + "," + UPDATED_HOUSE_RENT);

        // Get all the employeeSalaryList where houseRent equals to UPDATED_HOUSE_RENT
        defaultEmployeeSalaryShouldNotBeFound("houseRent.in=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRent is not null
        defaultEmployeeSalaryShouldBeFound("houseRent.specified=true");

        // Get all the employeeSalaryList where houseRent is null
        defaultEmployeeSalaryShouldNotBeFound("houseRent.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRent is greater than or equal to DEFAULT_HOUSE_RENT
        defaultEmployeeSalaryShouldBeFound("houseRent.greaterThanOrEqual=" + DEFAULT_HOUSE_RENT);

        // Get all the employeeSalaryList where houseRent is greater than or equal to UPDATED_HOUSE_RENT
        defaultEmployeeSalaryShouldNotBeFound("houseRent.greaterThanOrEqual=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRent is less than or equal to DEFAULT_HOUSE_RENT
        defaultEmployeeSalaryShouldBeFound("houseRent.lessThanOrEqual=" + DEFAULT_HOUSE_RENT);

        // Get all the employeeSalaryList where houseRent is less than or equal to SMALLER_HOUSE_RENT
        defaultEmployeeSalaryShouldNotBeFound("houseRent.lessThanOrEqual=" + SMALLER_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRent is less than DEFAULT_HOUSE_RENT
        defaultEmployeeSalaryShouldNotBeFound("houseRent.lessThan=" + DEFAULT_HOUSE_RENT);

        // Get all the employeeSalaryList where houseRent is less than UPDATED_HOUSE_RENT
        defaultEmployeeSalaryShouldBeFound("houseRent.lessThan=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRent is greater than DEFAULT_HOUSE_RENT
        defaultEmployeeSalaryShouldNotBeFound("houseRent.greaterThan=" + DEFAULT_HOUSE_RENT);

        // Get all the employeeSalaryList where houseRent is greater than SMALLER_HOUSE_RENT
        defaultEmployeeSalaryShouldBeFound("houseRent.greaterThan=" + SMALLER_HOUSE_RENT);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRentPercent equals to DEFAULT_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldBeFound("houseRentPercent.equals=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the employeeSalaryList where houseRentPercent equals to UPDATED_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("houseRentPercent.equals=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRentPercent not equals to DEFAULT_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("houseRentPercent.notEquals=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the employeeSalaryList where houseRentPercent not equals to UPDATED_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldBeFound("houseRentPercent.notEquals=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentPercentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRentPercent in DEFAULT_HOUSE_RENT_PERCENT or UPDATED_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldBeFound("houseRentPercent.in=" + DEFAULT_HOUSE_RENT_PERCENT + "," + UPDATED_HOUSE_RENT_PERCENT);

        // Get all the employeeSalaryList where houseRentPercent equals to UPDATED_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("houseRentPercent.in=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRentPercent is not null
        defaultEmployeeSalaryShouldBeFound("houseRentPercent.specified=true");

        // Get all the employeeSalaryList where houseRentPercent is null
        defaultEmployeeSalaryShouldNotBeFound("houseRentPercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRentPercent is greater than or equal to DEFAULT_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldBeFound("houseRentPercent.greaterThanOrEqual=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the employeeSalaryList where houseRentPercent is greater than or equal to UPDATED_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("houseRentPercent.greaterThanOrEqual=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRentPercent is less than or equal to DEFAULT_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldBeFound("houseRentPercent.lessThanOrEqual=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the employeeSalaryList where houseRentPercent is less than or equal to SMALLER_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("houseRentPercent.lessThanOrEqual=" + SMALLER_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRentPercent is less than DEFAULT_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("houseRentPercent.lessThan=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the employeeSalaryList where houseRentPercent is less than UPDATED_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldBeFound("houseRentPercent.lessThan=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByHouseRentPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where houseRentPercent is greater than DEFAULT_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("houseRentPercent.greaterThan=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the employeeSalaryList where houseRentPercent is greater than SMALLER_HOUSE_RENT_PERCENT
        defaultEmployeeSalaryShouldBeFound("houseRentPercent.greaterThan=" + SMALLER_HOUSE_RENT_PERCENT);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByTotalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where totalAllowance equals to DEFAULT_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("totalAllowance.equals=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the employeeSalaryList where totalAllowance equals to UPDATED_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("totalAllowance.equals=" + UPDATED_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByTotalAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where totalAllowance not equals to DEFAULT_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("totalAllowance.notEquals=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the employeeSalaryList where totalAllowance not equals to UPDATED_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("totalAllowance.notEquals=" + UPDATED_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByTotalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where totalAllowance in DEFAULT_TOTAL_ALLOWANCE or UPDATED_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("totalAllowance.in=" + DEFAULT_TOTAL_ALLOWANCE + "," + UPDATED_TOTAL_ALLOWANCE);

        // Get all the employeeSalaryList where totalAllowance equals to UPDATED_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("totalAllowance.in=" + UPDATED_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByTotalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where totalAllowance is not null
        defaultEmployeeSalaryShouldBeFound("totalAllowance.specified=true");

        // Get all the employeeSalaryList where totalAllowance is null
        defaultEmployeeSalaryShouldNotBeFound("totalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByTotalAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where totalAllowance is greater than or equal to DEFAULT_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("totalAllowance.greaterThanOrEqual=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the employeeSalaryList where totalAllowance is greater than or equal to UPDATED_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("totalAllowance.greaterThanOrEqual=" + UPDATED_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByTotalAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where totalAllowance is less than or equal to DEFAULT_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("totalAllowance.lessThanOrEqual=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the employeeSalaryList where totalAllowance is less than or equal to SMALLER_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("totalAllowance.lessThanOrEqual=" + SMALLER_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByTotalAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where totalAllowance is less than DEFAULT_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("totalAllowance.lessThan=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the employeeSalaryList where totalAllowance is less than UPDATED_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("totalAllowance.lessThan=" + UPDATED_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByTotalAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where totalAllowance is greater than DEFAULT_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("totalAllowance.greaterThan=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the employeeSalaryList where totalAllowance is greater than SMALLER_TOTAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("totalAllowance.greaterThan=" + SMALLER_TOTAL_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowance equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("medicalAllowance.equals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the employeeSalaryList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowance.equals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowance not equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowance.notEquals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the employeeSalaryList where medicalAllowance not equals to UPDATED_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("medicalAllowance.notEquals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowance in DEFAULT_MEDICAL_ALLOWANCE or UPDATED_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("medicalAllowance.in=" + DEFAULT_MEDICAL_ALLOWANCE + "," + UPDATED_MEDICAL_ALLOWANCE);

        // Get all the employeeSalaryList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowance.in=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowance is not null
        defaultEmployeeSalaryShouldBeFound("medicalAllowance.specified=true");

        // Get all the employeeSalaryList where medicalAllowance is null
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowance is greater than or equal to DEFAULT_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("medicalAllowance.greaterThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the employeeSalaryList where medicalAllowance is greater than or equal to UPDATED_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowance.greaterThanOrEqual=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowance is less than or equal to DEFAULT_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("medicalAllowance.lessThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the employeeSalaryList where medicalAllowance is less than or equal to SMALLER_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowance.lessThanOrEqual=" + SMALLER_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowance is less than DEFAULT_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowance.lessThan=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the employeeSalaryList where medicalAllowance is less than UPDATED_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("medicalAllowance.lessThan=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowance is greater than DEFAULT_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowance.greaterThan=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the employeeSalaryList where medicalAllowance is greater than SMALLER_MEDICAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("medicalAllowance.greaterThan=" + SMALLER_MEDICAL_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowancePercent equals to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("medicalAllowancePercent.equals=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where medicalAllowancePercent equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowancePercent.equals=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowancePercent not equals to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowancePercent.notEquals=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where medicalAllowancePercent not equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("medicalAllowancePercent.notEquals=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowancePercent in DEFAULT_MEDICAL_ALLOWANCE_PERCENT or UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("medicalAllowancePercent.in=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT + "," + UPDATED_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where medicalAllowancePercent equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowancePercent.in=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowancePercent is not null
        defaultEmployeeSalaryShouldBeFound("medicalAllowancePercent.specified=true");

        // Get all the employeeSalaryList where medicalAllowancePercent is null
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowancePercent is greater than or equal to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("medicalAllowancePercent.greaterThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where medicalAllowancePercent is greater than or equal to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowancePercent.greaterThanOrEqual=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowancePercent is less than or equal to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("medicalAllowancePercent.lessThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where medicalAllowancePercent is less than or equal to SMALLER_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowancePercent.lessThanOrEqual=" + SMALLER_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowancePercent is less than DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowancePercent.lessThan=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where medicalAllowancePercent is less than UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("medicalAllowancePercent.lessThan=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByMedicalAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where medicalAllowancePercent is greater than DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("medicalAllowancePercent.greaterThan=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where medicalAllowancePercent is greater than SMALLER_MEDICAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("medicalAllowancePercent.greaterThan=" + SMALLER_MEDICAL_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowance equals to DEFAULT_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("convinceAllowance.equals=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the employeeSalaryList where convinceAllowance equals to UPDATED_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowance.equals=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowance not equals to DEFAULT_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowance.notEquals=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the employeeSalaryList where convinceAllowance not equals to UPDATED_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("convinceAllowance.notEquals=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowance in DEFAULT_CONVINCE_ALLOWANCE or UPDATED_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("convinceAllowance.in=" + DEFAULT_CONVINCE_ALLOWANCE + "," + UPDATED_CONVINCE_ALLOWANCE);

        // Get all the employeeSalaryList where convinceAllowance equals to UPDATED_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowance.in=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowance is not null
        defaultEmployeeSalaryShouldBeFound("convinceAllowance.specified=true");

        // Get all the employeeSalaryList where convinceAllowance is null
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowance is greater than or equal to DEFAULT_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("convinceAllowance.greaterThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the employeeSalaryList where convinceAllowance is greater than or equal to UPDATED_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowance.greaterThanOrEqual=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowance is less than or equal to DEFAULT_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("convinceAllowance.lessThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the employeeSalaryList where convinceAllowance is less than or equal to SMALLER_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowance.lessThanOrEqual=" + SMALLER_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowance is less than DEFAULT_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowance.lessThan=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the employeeSalaryList where convinceAllowance is less than UPDATED_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("convinceAllowance.lessThan=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowance is greater than DEFAULT_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowance.greaterThan=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the employeeSalaryList where convinceAllowance is greater than SMALLER_CONVINCE_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("convinceAllowance.greaterThan=" + SMALLER_CONVINCE_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowancePercent equals to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("convinceAllowancePercent.equals=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where convinceAllowancePercent equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowancePercent.equals=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowancePercent not equals to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowancePercent.notEquals=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where convinceAllowancePercent not equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("convinceAllowancePercent.notEquals=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowancePercent in DEFAULT_CONVINCE_ALLOWANCE_PERCENT or UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("convinceAllowancePercent.in=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT + "," + UPDATED_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where convinceAllowancePercent equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowancePercent.in=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowancePercent is not null
        defaultEmployeeSalaryShouldBeFound("convinceAllowancePercent.specified=true");

        // Get all the employeeSalaryList where convinceAllowancePercent is null
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowancePercent is greater than or equal to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("convinceAllowancePercent.greaterThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where convinceAllowancePercent is greater than or equal to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowancePercent.greaterThanOrEqual=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowancePercent is less than or equal to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("convinceAllowancePercent.lessThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where convinceAllowancePercent is less than or equal to SMALLER_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowancePercent.lessThanOrEqual=" + SMALLER_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowancePercent is less than DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowancePercent.lessThan=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where convinceAllowancePercent is less than UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("convinceAllowancePercent.lessThan=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByConvinceAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where convinceAllowancePercent is greater than DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("convinceAllowancePercent.greaterThan=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where convinceAllowancePercent is greater than SMALLER_CONVINCE_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("convinceAllowancePercent.greaterThan=" + SMALLER_CONVINCE_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowance equals to DEFAULT_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("foodAllowance.equals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the employeeSalaryList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("foodAllowance.equals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowance not equals to DEFAULT_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("foodAllowance.notEquals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the employeeSalaryList where foodAllowance not equals to UPDATED_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("foodAllowance.notEquals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowance in DEFAULT_FOOD_ALLOWANCE or UPDATED_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("foodAllowance.in=" + DEFAULT_FOOD_ALLOWANCE + "," + UPDATED_FOOD_ALLOWANCE);

        // Get all the employeeSalaryList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("foodAllowance.in=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowance is not null
        defaultEmployeeSalaryShouldBeFound("foodAllowance.specified=true");

        // Get all the employeeSalaryList where foodAllowance is null
        defaultEmployeeSalaryShouldNotBeFound("foodAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowance is greater than or equal to DEFAULT_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("foodAllowance.greaterThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the employeeSalaryList where foodAllowance is greater than or equal to UPDATED_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("foodAllowance.greaterThanOrEqual=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowance is less than or equal to DEFAULT_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("foodAllowance.lessThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the employeeSalaryList where foodAllowance is less than or equal to SMALLER_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("foodAllowance.lessThanOrEqual=" + SMALLER_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowance is less than DEFAULT_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("foodAllowance.lessThan=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the employeeSalaryList where foodAllowance is less than UPDATED_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("foodAllowance.lessThan=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowance is greater than DEFAULT_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("foodAllowance.greaterThan=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the employeeSalaryList where foodAllowance is greater than SMALLER_FOOD_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("foodAllowance.greaterThan=" + SMALLER_FOOD_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowancePercent equals to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("foodAllowancePercent.equals=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where foodAllowancePercent equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("foodAllowancePercent.equals=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowancePercent not equals to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("foodAllowancePercent.notEquals=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where foodAllowancePercent not equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("foodAllowancePercent.notEquals=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowancePercent in DEFAULT_FOOD_ALLOWANCE_PERCENT or UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("foodAllowancePercent.in=" + DEFAULT_FOOD_ALLOWANCE_PERCENT + "," + UPDATED_FOOD_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where foodAllowancePercent equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("foodAllowancePercent.in=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowancePercent is not null
        defaultEmployeeSalaryShouldBeFound("foodAllowancePercent.specified=true");

        // Get all the employeeSalaryList where foodAllowancePercent is null
        defaultEmployeeSalaryShouldNotBeFound("foodAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowancePercent is greater than or equal to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("foodAllowancePercent.greaterThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where foodAllowancePercent is greater than or equal to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("foodAllowancePercent.greaterThanOrEqual=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowancePercent is less than or equal to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("foodAllowancePercent.lessThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where foodAllowancePercent is less than or equal to SMALLER_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("foodAllowancePercent.lessThanOrEqual=" + SMALLER_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowancePercent is less than DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("foodAllowancePercent.lessThan=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where foodAllowancePercent is less than UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("foodAllowancePercent.lessThan=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByFoodAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where foodAllowancePercent is greater than DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("foodAllowancePercent.greaterThan=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where foodAllowancePercent is greater than SMALLER_FOOD_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("foodAllowancePercent.greaterThan=" + SMALLER_FOOD_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceActiveStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowanceActiveStatus equals to DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldBeFound("specialAllowanceActiveStatus.equals=" + DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS);

        // Get all the employeeSalaryList where specialAllowanceActiveStatus equals to UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldNotBeFound("specialAllowanceActiveStatus.equals=" + UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceActiveStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowanceActiveStatus not equals to DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldNotBeFound("specialAllowanceActiveStatus.notEquals=" + DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS);

        // Get all the employeeSalaryList where specialAllowanceActiveStatus not equals to UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldBeFound("specialAllowanceActiveStatus.notEquals=" + UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceActiveStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowanceActiveStatus in DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS or UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldBeFound("specialAllowanceActiveStatus.in=" + DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS + "," + UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS);

        // Get all the employeeSalaryList where specialAllowanceActiveStatus equals to UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldNotBeFound("specialAllowanceActiveStatus.in=" + UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceActiveStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowanceActiveStatus is not null
        defaultEmployeeSalaryShouldBeFound("specialAllowanceActiveStatus.specified=true");

        // Get all the employeeSalaryList where specialAllowanceActiveStatus is null
        defaultEmployeeSalaryShouldNotBeFound("specialAllowanceActiveStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowance equals to DEFAULT_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("specialAllowance.equals=" + DEFAULT_SPECIAL_ALLOWANCE);

        // Get all the employeeSalaryList where specialAllowance equals to UPDATED_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("specialAllowance.equals=" + UPDATED_SPECIAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowance not equals to DEFAULT_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("specialAllowance.notEquals=" + DEFAULT_SPECIAL_ALLOWANCE);

        // Get all the employeeSalaryList where specialAllowance not equals to UPDATED_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("specialAllowance.notEquals=" + UPDATED_SPECIAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowance in DEFAULT_SPECIAL_ALLOWANCE or UPDATED_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("specialAllowance.in=" + DEFAULT_SPECIAL_ALLOWANCE + "," + UPDATED_SPECIAL_ALLOWANCE);

        // Get all the employeeSalaryList where specialAllowance equals to UPDATED_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("specialAllowance.in=" + UPDATED_SPECIAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowance is not null
        defaultEmployeeSalaryShouldBeFound("specialAllowance.specified=true");

        // Get all the employeeSalaryList where specialAllowance is null
        defaultEmployeeSalaryShouldNotBeFound("specialAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowance is greater than or equal to DEFAULT_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("specialAllowance.greaterThanOrEqual=" + DEFAULT_SPECIAL_ALLOWANCE);

        // Get all the employeeSalaryList where specialAllowance is greater than or equal to UPDATED_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("specialAllowance.greaterThanOrEqual=" + UPDATED_SPECIAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowance is less than or equal to DEFAULT_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("specialAllowance.lessThanOrEqual=" + DEFAULT_SPECIAL_ALLOWANCE);

        // Get all the employeeSalaryList where specialAllowance is less than or equal to SMALLER_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("specialAllowance.lessThanOrEqual=" + SMALLER_SPECIAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowance is less than DEFAULT_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("specialAllowance.lessThan=" + DEFAULT_SPECIAL_ALLOWANCE);

        // Get all the employeeSalaryList where specialAllowance is less than UPDATED_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("specialAllowance.lessThan=" + UPDATED_SPECIAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowance is greater than DEFAULT_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldNotBeFound("specialAllowance.greaterThan=" + DEFAULT_SPECIAL_ALLOWANCE);

        // Get all the employeeSalaryList where specialAllowance is greater than SMALLER_SPECIAL_ALLOWANCE
        defaultEmployeeSalaryShouldBeFound("specialAllowance.greaterThan=" + SMALLER_SPECIAL_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowancePercent equals to DEFAULT_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("specialAllowancePercent.equals=" + DEFAULT_SPECIAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where specialAllowancePercent equals to UPDATED_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("specialAllowancePercent.equals=" + UPDATED_SPECIAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowancePercent not equals to DEFAULT_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("specialAllowancePercent.notEquals=" + DEFAULT_SPECIAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where specialAllowancePercent not equals to UPDATED_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("specialAllowancePercent.notEquals=" + UPDATED_SPECIAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowancePercent in DEFAULT_SPECIAL_ALLOWANCE_PERCENT or UPDATED_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("specialAllowancePercent.in=" + DEFAULT_SPECIAL_ALLOWANCE_PERCENT + "," + UPDATED_SPECIAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where specialAllowancePercent equals to UPDATED_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("specialAllowancePercent.in=" + UPDATED_SPECIAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowancePercent is not null
        defaultEmployeeSalaryShouldBeFound("specialAllowancePercent.specified=true");

        // Get all the employeeSalaryList where specialAllowancePercent is null
        defaultEmployeeSalaryShouldNotBeFound("specialAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowancePercent is greater than or equal to DEFAULT_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("specialAllowancePercent.greaterThanOrEqual=" + DEFAULT_SPECIAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where specialAllowancePercent is greater than or equal to UPDATED_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("specialAllowancePercent.greaterThanOrEqual=" + UPDATED_SPECIAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowancePercent is less than or equal to DEFAULT_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("specialAllowancePercent.lessThanOrEqual=" + DEFAULT_SPECIAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where specialAllowancePercent is less than or equal to SMALLER_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("specialAllowancePercent.lessThanOrEqual=" + SMALLER_SPECIAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowancePercent is less than DEFAULT_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("specialAllowancePercent.lessThan=" + DEFAULT_SPECIAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where specialAllowancePercent is less than UPDATED_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("specialAllowancePercent.lessThan=" + UPDATED_SPECIAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesBySpecialAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where specialAllowancePercent is greater than DEFAULT_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("specialAllowancePercent.greaterThan=" + DEFAULT_SPECIAL_ALLOWANCE_PERCENT);

        // Get all the employeeSalaryList where specialAllowancePercent is greater than SMALLER_SPECIAL_ALLOWANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("specialAllowancePercent.greaterThan=" + SMALLER_SPECIAL_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceActiveStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceActiveStatus equals to DEFAULT_INSURANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldBeFound("insuranceActiveStatus.equals=" + DEFAULT_INSURANCE_ACTIVE_STATUS);

        // Get all the employeeSalaryList where insuranceActiveStatus equals to UPDATED_INSURANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldNotBeFound("insuranceActiveStatus.equals=" + UPDATED_INSURANCE_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceActiveStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceActiveStatus not equals to DEFAULT_INSURANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldNotBeFound("insuranceActiveStatus.notEquals=" + DEFAULT_INSURANCE_ACTIVE_STATUS);

        // Get all the employeeSalaryList where insuranceActiveStatus not equals to UPDATED_INSURANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldBeFound("insuranceActiveStatus.notEquals=" + UPDATED_INSURANCE_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceActiveStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceActiveStatus in DEFAULT_INSURANCE_ACTIVE_STATUS or UPDATED_INSURANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldBeFound("insuranceActiveStatus.in=" + DEFAULT_INSURANCE_ACTIVE_STATUS + "," + UPDATED_INSURANCE_ACTIVE_STATUS);

        // Get all the employeeSalaryList where insuranceActiveStatus equals to UPDATED_INSURANCE_ACTIVE_STATUS
        defaultEmployeeSalaryShouldNotBeFound("insuranceActiveStatus.in=" + UPDATED_INSURANCE_ACTIVE_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceActiveStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceActiveStatus is not null
        defaultEmployeeSalaryShouldBeFound("insuranceActiveStatus.specified=true");

        // Get all the employeeSalaryList where insuranceActiveStatus is null
        defaultEmployeeSalaryShouldNotBeFound("insuranceActiveStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceAmount equals to DEFAULT_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldBeFound("insuranceAmount.equals=" + DEFAULT_INSURANCE_AMOUNT);

        // Get all the employeeSalaryList where insuranceAmount equals to UPDATED_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("insuranceAmount.equals=" + UPDATED_INSURANCE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceAmount not equals to DEFAULT_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("insuranceAmount.notEquals=" + DEFAULT_INSURANCE_AMOUNT);

        // Get all the employeeSalaryList where insuranceAmount not equals to UPDATED_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldBeFound("insuranceAmount.notEquals=" + UPDATED_INSURANCE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceAmountIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceAmount in DEFAULT_INSURANCE_AMOUNT or UPDATED_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldBeFound("insuranceAmount.in=" + DEFAULT_INSURANCE_AMOUNT + "," + UPDATED_INSURANCE_AMOUNT);

        // Get all the employeeSalaryList where insuranceAmount equals to UPDATED_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("insuranceAmount.in=" + UPDATED_INSURANCE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceAmount is not null
        defaultEmployeeSalaryShouldBeFound("insuranceAmount.specified=true");

        // Get all the employeeSalaryList where insuranceAmount is null
        defaultEmployeeSalaryShouldNotBeFound("insuranceAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceAmount is greater than or equal to DEFAULT_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldBeFound("insuranceAmount.greaterThanOrEqual=" + DEFAULT_INSURANCE_AMOUNT);

        // Get all the employeeSalaryList where insuranceAmount is greater than or equal to UPDATED_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("insuranceAmount.greaterThanOrEqual=" + UPDATED_INSURANCE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceAmount is less than or equal to DEFAULT_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldBeFound("insuranceAmount.lessThanOrEqual=" + DEFAULT_INSURANCE_AMOUNT);

        // Get all the employeeSalaryList where insuranceAmount is less than or equal to SMALLER_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("insuranceAmount.lessThanOrEqual=" + SMALLER_INSURANCE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceAmount is less than DEFAULT_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("insuranceAmount.lessThan=" + DEFAULT_INSURANCE_AMOUNT);

        // Get all the employeeSalaryList where insuranceAmount is less than UPDATED_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldBeFound("insuranceAmount.lessThan=" + UPDATED_INSURANCE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceAmount is greater than DEFAULT_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldNotBeFound("insuranceAmount.greaterThan=" + DEFAULT_INSURANCE_AMOUNT);

        // Get all the employeeSalaryList where insuranceAmount is greater than SMALLER_INSURANCE_AMOUNT
        defaultEmployeeSalaryShouldBeFound("insuranceAmount.greaterThan=" + SMALLER_INSURANCE_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsurancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insurancePercent equals to DEFAULT_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("insurancePercent.equals=" + DEFAULT_INSURANCE_PERCENT);

        // Get all the employeeSalaryList where insurancePercent equals to UPDATED_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("insurancePercent.equals=" + UPDATED_INSURANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsurancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insurancePercent not equals to DEFAULT_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("insurancePercent.notEquals=" + DEFAULT_INSURANCE_PERCENT);

        // Get all the employeeSalaryList where insurancePercent not equals to UPDATED_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("insurancePercent.notEquals=" + UPDATED_INSURANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsurancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insurancePercent in DEFAULT_INSURANCE_PERCENT or UPDATED_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("insurancePercent.in=" + DEFAULT_INSURANCE_PERCENT + "," + UPDATED_INSURANCE_PERCENT);

        // Get all the employeeSalaryList where insurancePercent equals to UPDATED_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("insurancePercent.in=" + UPDATED_INSURANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsurancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insurancePercent is not null
        defaultEmployeeSalaryShouldBeFound("insurancePercent.specified=true");

        // Get all the employeeSalaryList where insurancePercent is null
        defaultEmployeeSalaryShouldNotBeFound("insurancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsurancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insurancePercent is greater than or equal to DEFAULT_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("insurancePercent.greaterThanOrEqual=" + DEFAULT_INSURANCE_PERCENT);

        // Get all the employeeSalaryList where insurancePercent is greater than or equal to UPDATED_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("insurancePercent.greaterThanOrEqual=" + UPDATED_INSURANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsurancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insurancePercent is less than or equal to DEFAULT_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("insurancePercent.lessThanOrEqual=" + DEFAULT_INSURANCE_PERCENT);

        // Get all the employeeSalaryList where insurancePercent is less than or equal to SMALLER_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("insurancePercent.lessThanOrEqual=" + SMALLER_INSURANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsurancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insurancePercent is less than DEFAULT_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("insurancePercent.lessThan=" + DEFAULT_INSURANCE_PERCENT);

        // Get all the employeeSalaryList where insurancePercent is less than UPDATED_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("insurancePercent.lessThan=" + UPDATED_INSURANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsurancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insurancePercent is greater than DEFAULT_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldNotBeFound("insurancePercent.greaterThan=" + DEFAULT_INSURANCE_PERCENT);

        // Get all the employeeSalaryList where insurancePercent is greater than SMALLER_INSURANCE_PERCENT
        defaultEmployeeSalaryShouldBeFound("insurancePercent.greaterThan=" + SMALLER_INSURANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceProcessTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceProcessType equals to DEFAULT_INSURANCE_PROCESS_TYPE
        defaultEmployeeSalaryShouldBeFound("insuranceProcessType.equals=" + DEFAULT_INSURANCE_PROCESS_TYPE);

        // Get all the employeeSalaryList where insuranceProcessType equals to UPDATED_INSURANCE_PROCESS_TYPE
        defaultEmployeeSalaryShouldNotBeFound("insuranceProcessType.equals=" + UPDATED_INSURANCE_PROCESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceProcessTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceProcessType not equals to DEFAULT_INSURANCE_PROCESS_TYPE
        defaultEmployeeSalaryShouldNotBeFound("insuranceProcessType.notEquals=" + DEFAULT_INSURANCE_PROCESS_TYPE);

        // Get all the employeeSalaryList where insuranceProcessType not equals to UPDATED_INSURANCE_PROCESS_TYPE
        defaultEmployeeSalaryShouldBeFound("insuranceProcessType.notEquals=" + UPDATED_INSURANCE_PROCESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceProcessTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceProcessType in DEFAULT_INSURANCE_PROCESS_TYPE or UPDATED_INSURANCE_PROCESS_TYPE
        defaultEmployeeSalaryShouldBeFound("insuranceProcessType.in=" + DEFAULT_INSURANCE_PROCESS_TYPE + "," + UPDATED_INSURANCE_PROCESS_TYPE);

        // Get all the employeeSalaryList where insuranceProcessType equals to UPDATED_INSURANCE_PROCESS_TYPE
        defaultEmployeeSalaryShouldNotBeFound("insuranceProcessType.in=" + UPDATED_INSURANCE_PROCESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByInsuranceProcessTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where insuranceProcessType is not null
        defaultEmployeeSalaryShouldBeFound("insuranceProcessType.specified=true");

        // Get all the employeeSalaryList where insuranceProcessType is null
        defaultEmployeeSalaryShouldNotBeFound("insuranceProcessType.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where status equals to DEFAULT_STATUS
        defaultEmployeeSalaryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the employeeSalaryList where status equals to UPDATED_STATUS
        defaultEmployeeSalaryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where status not equals to DEFAULT_STATUS
        defaultEmployeeSalaryShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the employeeSalaryList where status not equals to UPDATED_STATUS
        defaultEmployeeSalaryShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmployeeSalaryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the employeeSalaryList where status equals to UPDATED_STATUS
        defaultEmployeeSalaryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList where status is not null
        defaultEmployeeSalaryShouldBeFound("status.specified=true");

        // Get all the employeeSalaryList where status is null
        defaultEmployeeSalaryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSalariesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeSalary.setEmployee(employee);
        employeeSalaryRepository.saveAndFlush(employeeSalary);
        Long employeeId = employee.getId();

        // Get all the employeeSalaryList where employee equals to employeeId
        defaultEmployeeSalaryShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeSalaryList where employee equals to employeeId + 1
        defaultEmployeeSalaryShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeSalaryShouldBeFound(String filter) throws Exception {
        restEmployeeSalaryMockMvc.perform(get("/api/employee-salaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].gross").value(hasItem(DEFAULT_GROSS.intValue())))
            .andExpect(jsonPath("$.[*].incrementAmount").value(hasItem(DEFAULT_INCREMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].incrementPercentage").value(hasItem(DEFAULT_INCREMENT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].salaryStartDate").value(hasItem(DEFAULT_SALARY_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].salaryEndDate").value(hasItem(DEFAULT_SALARY_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].nextIncrementDate").value(hasItem(DEFAULT_NEXT_INCREMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].basicPercent").value(hasItem(DEFAULT_BASIC_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRentPercent").value(hasItem(DEFAULT_HOUSE_RENT_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].totalAllowance").value(hasItem(DEFAULT_TOTAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowancePercent").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowance").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowancePercent").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowancePercent").value(hasItem(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].specialAllowanceActiveStatus").value(hasItem(DEFAULT_SPECIAL_ALLOWANCE_ACTIVE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].specialAllowance").value(hasItem(DEFAULT_SPECIAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].specialAllowancePercent").value(hasItem(DEFAULT_SPECIAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].specialAllowanceDescription").value(hasItem(DEFAULT_SPECIAL_ALLOWANCE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].insuranceActiveStatus").value(hasItem(DEFAULT_INSURANCE_ACTIVE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].insuranceAmount").value(hasItem(DEFAULT_INSURANCE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].insurancePercent").value(hasItem(DEFAULT_INSURANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].insuranceDescription").value(hasItem(DEFAULT_INSURANCE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].insuranceProcessType").value(hasItem(DEFAULT_INSURANCE_PROCESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restEmployeeSalaryMockMvc.perform(get("/api/employee-salaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeSalaryShouldNotBeFound(String filter) throws Exception {
        restEmployeeSalaryMockMvc.perform(get("/api/employee-salaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeSalaryMockMvc.perform(get("/api/employee-salaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeSalary() throws Exception {
        // Get the employeeSalary
        restEmployeeSalaryMockMvc.perform(get("/api/employee-salaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeSalary() throws Exception {
        // Initialize the database
        employeeSalaryService.save(employeeSalary);

        int databaseSizeBeforeUpdate = employeeSalaryRepository.findAll().size();

        // Update the employeeSalary
        EmployeeSalary updatedEmployeeSalary = employeeSalaryRepository.findById(employeeSalary.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeSalary are not directly saved in db
        em.detach(updatedEmployeeSalary);
        updatedEmployeeSalary
            .gross(UPDATED_GROSS)
            .incrementAmount(UPDATED_INCREMENT_AMOUNT)
            .incrementPercentage(UPDATED_INCREMENT_PERCENTAGE)
            .salaryStartDate(UPDATED_SALARY_START_DATE)
            .salaryEndDate(UPDATED_SALARY_END_DATE)
            .nextIncrementDate(UPDATED_NEXT_INCREMENT_DATE)
            .basic(UPDATED_BASIC)
            .basicPercent(UPDATED_BASIC_PERCENT)
            .houseRent(UPDATED_HOUSE_RENT)
            .houseRentPercent(UPDATED_HOUSE_RENT_PERCENT)
            .totalAllowance(UPDATED_TOTAL_ALLOWANCE)
            .medicalAllowance(UPDATED_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(UPDATED_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(UPDATED_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(UPDATED_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(UPDATED_FOOD_ALLOWANCE)
            .foodAllowancePercent(UPDATED_FOOD_ALLOWANCE_PERCENT)
            .specialAllowanceActiveStatus(UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS)
            .specialAllowance(UPDATED_SPECIAL_ALLOWANCE)
            .specialAllowancePercent(UPDATED_SPECIAL_ALLOWANCE_PERCENT)
            .specialAllowanceDescription(UPDATED_SPECIAL_ALLOWANCE_DESCRIPTION)
            .insuranceActiveStatus(UPDATED_INSURANCE_ACTIVE_STATUS)
            .insuranceAmount(UPDATED_INSURANCE_AMOUNT)
            .insurancePercent(UPDATED_INSURANCE_PERCENT)
            .insuranceDescription(UPDATED_INSURANCE_DESCRIPTION)
            .insuranceProcessType(UPDATED_INSURANCE_PROCESS_TYPE)
            .status(UPDATED_STATUS);

        restEmployeeSalaryMockMvc.perform(put("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeSalary)))
            .andExpect(status().isOk());

        // Validate the EmployeeSalary in the database
        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSalary testEmployeeSalary = employeeSalaryList.get(employeeSalaryList.size() - 1);
        assertThat(testEmployeeSalary.getGross()).isEqualTo(UPDATED_GROSS);
        assertThat(testEmployeeSalary.getIncrementAmount()).isEqualTo(UPDATED_INCREMENT_AMOUNT);
        assertThat(testEmployeeSalary.getIncrementPercentage()).isEqualTo(UPDATED_INCREMENT_PERCENTAGE);
        assertThat(testEmployeeSalary.getSalaryStartDate()).isEqualTo(UPDATED_SALARY_START_DATE);
        assertThat(testEmployeeSalary.getSalaryEndDate()).isEqualTo(UPDATED_SALARY_END_DATE);
        assertThat(testEmployeeSalary.getNextIncrementDate()).isEqualTo(UPDATED_NEXT_INCREMENT_DATE);
        assertThat(testEmployeeSalary.getBasic()).isEqualTo(UPDATED_BASIC);
        assertThat(testEmployeeSalary.getBasicPercent()).isEqualTo(UPDATED_BASIC_PERCENT);
        assertThat(testEmployeeSalary.getHouseRent()).isEqualTo(UPDATED_HOUSE_RENT);
        assertThat(testEmployeeSalary.getHouseRentPercent()).isEqualTo(UPDATED_HOUSE_RENT_PERCENT);
        assertThat(testEmployeeSalary.getTotalAllowance()).isEqualTo(UPDATED_TOTAL_ALLOWANCE);
        assertThat(testEmployeeSalary.getMedicalAllowance()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE);
        assertThat(testEmployeeSalary.getMedicalAllowancePercent()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE_PERCENT);
        assertThat(testEmployeeSalary.getConvinceAllowance()).isEqualTo(UPDATED_CONVINCE_ALLOWANCE);
        assertThat(testEmployeeSalary.getConvinceAllowancePercent()).isEqualTo(UPDATED_CONVINCE_ALLOWANCE_PERCENT);
        assertThat(testEmployeeSalary.getFoodAllowance()).isEqualTo(UPDATED_FOOD_ALLOWANCE);
        assertThat(testEmployeeSalary.getFoodAllowancePercent()).isEqualTo(UPDATED_FOOD_ALLOWANCE_PERCENT);
        assertThat(testEmployeeSalary.getSpecialAllowanceActiveStatus()).isEqualTo(UPDATED_SPECIAL_ALLOWANCE_ACTIVE_STATUS);
        assertThat(testEmployeeSalary.getSpecialAllowance()).isEqualTo(UPDATED_SPECIAL_ALLOWANCE);
        assertThat(testEmployeeSalary.getSpecialAllowancePercent()).isEqualTo(UPDATED_SPECIAL_ALLOWANCE_PERCENT);
        assertThat(testEmployeeSalary.getSpecialAllowanceDescription()).isEqualTo(UPDATED_SPECIAL_ALLOWANCE_DESCRIPTION);
        assertThat(testEmployeeSalary.getInsuranceActiveStatus()).isEqualTo(UPDATED_INSURANCE_ACTIVE_STATUS);
        assertThat(testEmployeeSalary.getInsuranceAmount()).isEqualTo(UPDATED_INSURANCE_AMOUNT);
        assertThat(testEmployeeSalary.getInsurancePercent()).isEqualTo(UPDATED_INSURANCE_PERCENT);
        assertThat(testEmployeeSalary.getInsuranceDescription()).isEqualTo(UPDATED_INSURANCE_DESCRIPTION);
        assertThat(testEmployeeSalary.getInsuranceProcessType()).isEqualTo(UPDATED_INSURANCE_PROCESS_TYPE);
        assertThat(testEmployeeSalary.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeSalary() throws Exception {
        int databaseSizeBeforeUpdate = employeeSalaryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeSalaryMockMvc.perform(put("/api/employee-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSalary in the database
        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeSalary() throws Exception {
        // Initialize the database
        employeeSalaryService.save(employeeSalary);

        int databaseSizeBeforeDelete = employeeSalaryRepository.findAll().size();

        // Delete the employeeSalary
        restEmployeeSalaryMockMvc.perform(delete("/api/employee-salaries/{id}", employeeSalary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
