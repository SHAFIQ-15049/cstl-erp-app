package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.PartialSalary;
import software.cstl.domain.Employee;
import software.cstl.repository.PartialSalaryRepository;
import software.cstl.service.PartialSalaryService;
import software.cstl.service.dto.PartialSalaryCriteria;
import software.cstl.service.PartialSalaryQueryService;

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

import software.cstl.domain.enumeration.MonthType;
import software.cstl.domain.enumeration.SalaryExecutionStatus;
/**
 * Integration tests for the {@link PartialSalaryResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartialSalaryResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final MonthType DEFAULT_MONTH = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH = MonthType.FEBRUARY;

    private static final Integer DEFAULT_TOTAL_MONTH_DAYS = 1;
    private static final Integer UPDATED_TOTAL_MONTH_DAYS = 2;
    private static final Integer SMALLER_TOTAL_MONTH_DAYS = 1 - 1;

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_GROSS = new BigDecimal(2);
    private static final BigDecimal SMALLER_GROSS = new BigDecimal(1 - 1);

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

    private static final BigDecimal DEFAULT_FINE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FINE = new BigDecimal(2);
    private static final BigDecimal SMALLER_FINE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ADVANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADVANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_ADVANCE = new BigDecimal(1 - 1);

    private static final SalaryExecutionStatus DEFAULT_STATUS = SalaryExecutionStatus.DONE;
    private static final SalaryExecutionStatus UPDATED_STATUS = SalaryExecutionStatus.NOT_DONE;

    private static final Instant DEFAULT_EXECUTED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXECUTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_EXECUTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private PartialSalaryRepository partialSalaryRepository;

    @Autowired
    private PartialSalaryService partialSalaryService;

    @Autowired
    private PartialSalaryQueryService partialSalaryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartialSalaryMockMvc;

    private PartialSalary partialSalary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartialSalary createEntity(EntityManager em) {
        PartialSalary partialSalary = new PartialSalary()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .totalMonthDays(DEFAULT_TOTAL_MONTH_DAYS)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .gross(DEFAULT_GROSS)
            .basic(DEFAULT_BASIC)
            .basicPercent(DEFAULT_BASIC_PERCENT)
            .houseRent(DEFAULT_HOUSE_RENT)
            .houseRentPercent(DEFAULT_HOUSE_RENT_PERCENT)
            .medicalAllowance(DEFAULT_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(DEFAULT_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(DEFAULT_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(DEFAULT_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(DEFAULT_FOOD_ALLOWANCE)
            .foodAllowancePercent(DEFAULT_FOOD_ALLOWANCE_PERCENT)
            .fine(DEFAULT_FINE)
            .advance(DEFAULT_ADVANCE)
            .status(DEFAULT_STATUS)
            .executedOn(DEFAULT_EXECUTED_ON)
            .executedBy(DEFAULT_EXECUTED_BY)
            .note(DEFAULT_NOTE);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        partialSalary.setEmployee(employee);
        return partialSalary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartialSalary createUpdatedEntity(EntityManager em) {
        PartialSalary partialSalary = new PartialSalary()
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .totalMonthDays(UPDATED_TOTAL_MONTH_DAYS)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .gross(UPDATED_GROSS)
            .basic(UPDATED_BASIC)
            .basicPercent(UPDATED_BASIC_PERCENT)
            .houseRent(UPDATED_HOUSE_RENT)
            .houseRentPercent(UPDATED_HOUSE_RENT_PERCENT)
            .medicalAllowance(UPDATED_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(UPDATED_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(UPDATED_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(UPDATED_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(UPDATED_FOOD_ALLOWANCE)
            .foodAllowancePercent(UPDATED_FOOD_ALLOWANCE_PERCENT)
            .fine(UPDATED_FINE)
            .advance(UPDATED_ADVANCE)
            .status(UPDATED_STATUS)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY)
            .note(UPDATED_NOTE);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        partialSalary.setEmployee(employee);
        return partialSalary;
    }

    @BeforeEach
    public void initTest() {
        partialSalary = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartialSalary() throws Exception {
        int databaseSizeBeforeCreate = partialSalaryRepository.findAll().size();
        // Create the PartialSalary
        restPartialSalaryMockMvc.perform(post("/api/partial-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partialSalary)))
            .andExpect(status().isCreated());

        // Validate the PartialSalary in the database
        List<PartialSalary> partialSalaryList = partialSalaryRepository.findAll();
        assertThat(partialSalaryList).hasSize(databaseSizeBeforeCreate + 1);
        PartialSalary testPartialSalary = partialSalaryList.get(partialSalaryList.size() - 1);
        assertThat(testPartialSalary.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testPartialSalary.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testPartialSalary.getTotalMonthDays()).isEqualTo(DEFAULT_TOTAL_MONTH_DAYS);
        assertThat(testPartialSalary.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartialSalary.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testPartialSalary.getGross()).isEqualTo(DEFAULT_GROSS);
        assertThat(testPartialSalary.getBasic()).isEqualTo(DEFAULT_BASIC);
        assertThat(testPartialSalary.getBasicPercent()).isEqualTo(DEFAULT_BASIC_PERCENT);
        assertThat(testPartialSalary.getHouseRent()).isEqualTo(DEFAULT_HOUSE_RENT);
        assertThat(testPartialSalary.getHouseRentPercent()).isEqualTo(DEFAULT_HOUSE_RENT_PERCENT);
        assertThat(testPartialSalary.getMedicalAllowance()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE);
        assertThat(testPartialSalary.getMedicalAllowancePercent()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE_PERCENT);
        assertThat(testPartialSalary.getConvinceAllowance()).isEqualTo(DEFAULT_CONVINCE_ALLOWANCE);
        assertThat(testPartialSalary.getConvinceAllowancePercent()).isEqualTo(DEFAULT_CONVINCE_ALLOWANCE_PERCENT);
        assertThat(testPartialSalary.getFoodAllowance()).isEqualTo(DEFAULT_FOOD_ALLOWANCE);
        assertThat(testPartialSalary.getFoodAllowancePercent()).isEqualTo(DEFAULT_FOOD_ALLOWANCE_PERCENT);
        assertThat(testPartialSalary.getFine()).isEqualTo(DEFAULT_FINE);
        assertThat(testPartialSalary.getAdvance()).isEqualTo(DEFAULT_ADVANCE);
        assertThat(testPartialSalary.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPartialSalary.getExecutedOn()).isEqualTo(DEFAULT_EXECUTED_ON);
        assertThat(testPartialSalary.getExecutedBy()).isEqualTo(DEFAULT_EXECUTED_BY);
        assertThat(testPartialSalary.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createPartialSalaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partialSalaryRepository.findAll().size();

        // Create the PartialSalary with an existing ID
        partialSalary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartialSalaryMockMvc.perform(post("/api/partial-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partialSalary)))
            .andExpect(status().isBadRequest());

        // Validate the PartialSalary in the database
        List<PartialSalary> partialSalaryList = partialSalaryRepository.findAll();
        assertThat(partialSalaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = partialSalaryRepository.findAll().size();
        // set the field null
        partialSalary.setYear(null);

        // Create the PartialSalary, which fails.


        restPartialSalaryMockMvc.perform(post("/api/partial-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partialSalary)))
            .andExpect(status().isBadRequest());

        List<PartialSalary> partialSalaryList = partialSalaryRepository.findAll();
        assertThat(partialSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = partialSalaryRepository.findAll().size();
        // set the field null
        partialSalary.setMonth(null);

        // Create the PartialSalary, which fails.


        restPartialSalaryMockMvc.perform(post("/api/partial-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partialSalary)))
            .andExpect(status().isBadRequest());

        List<PartialSalary> partialSalaryList = partialSalaryRepository.findAll();
        assertThat(partialSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalMonthDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = partialSalaryRepository.findAll().size();
        // set the field null
        partialSalary.setTotalMonthDays(null);

        // Create the PartialSalary, which fails.


        restPartialSalaryMockMvc.perform(post("/api/partial-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partialSalary)))
            .andExpect(status().isBadRequest());

        List<PartialSalary> partialSalaryList = partialSalaryRepository.findAll();
        assertThat(partialSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = partialSalaryRepository.findAll().size();
        // set the field null
        partialSalary.setFromDate(null);

        // Create the PartialSalary, which fails.


        restPartialSalaryMockMvc.perform(post("/api/partial-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partialSalary)))
            .andExpect(status().isBadRequest());

        List<PartialSalary> partialSalaryList = partialSalaryRepository.findAll();
        assertThat(partialSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = partialSalaryRepository.findAll().size();
        // set the field null
        partialSalary.setToDate(null);

        // Create the PartialSalary, which fails.


        restPartialSalaryMockMvc.perform(post("/api/partial-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partialSalary)))
            .andExpect(status().isBadRequest());

        List<PartialSalary> partialSalaryList = partialSalaryRepository.findAll();
        assertThat(partialSalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartialSalaries() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList
        restPartialSalaryMockMvc.perform(get("/api/partial-salaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partialSalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].totalMonthDays").value(hasItem(DEFAULT_TOTAL_MONTH_DAYS)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].gross").value(hasItem(DEFAULT_GROSS.intValue())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].basicPercent").value(hasItem(DEFAULT_BASIC_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRentPercent").value(hasItem(DEFAULT_HOUSE_RENT_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowancePercent").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowance").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowancePercent").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowancePercent").value(hasItem(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE.intValue())))
            .andExpect(jsonPath("$.[*].advance").value(hasItem(DEFAULT_ADVANCE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getPartialSalary() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get the partialSalary
        restPartialSalaryMockMvc.perform(get("/api/partial-salaries/{id}", partialSalary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partialSalary.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.totalMonthDays").value(DEFAULT_TOTAL_MONTH_DAYS))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.gross").value(DEFAULT_GROSS.intValue()))
            .andExpect(jsonPath("$.basic").value(DEFAULT_BASIC.intValue()))
            .andExpect(jsonPath("$.basicPercent").value(DEFAULT_BASIC_PERCENT.intValue()))
            .andExpect(jsonPath("$.houseRent").value(DEFAULT_HOUSE_RENT.intValue()))
            .andExpect(jsonPath("$.houseRentPercent").value(DEFAULT_HOUSE_RENT_PERCENT.intValue()))
            .andExpect(jsonPath("$.medicalAllowance").value(DEFAULT_MEDICAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.medicalAllowancePercent").value(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.convinceAllowance").value(DEFAULT_CONVINCE_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.convinceAllowancePercent").value(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.foodAllowance").value(DEFAULT_FOOD_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.foodAllowancePercent").value(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.fine").value(DEFAULT_FINE.intValue()))
            .andExpect(jsonPath("$.advance").value(DEFAULT_ADVANCE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.executedOn").value(DEFAULT_EXECUTED_ON.toString()))
            .andExpect(jsonPath("$.executedBy").value(DEFAULT_EXECUTED_BY))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }


    @Test
    @Transactional
    public void getPartialSalariesByIdFiltering() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        Long id = partialSalary.getId();

        defaultPartialSalaryShouldBeFound("id.equals=" + id);
        defaultPartialSalaryShouldNotBeFound("id.notEquals=" + id);

        defaultPartialSalaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPartialSalaryShouldNotBeFound("id.greaterThan=" + id);

        defaultPartialSalaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPartialSalaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where year equals to DEFAULT_YEAR
        defaultPartialSalaryShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the partialSalaryList where year equals to UPDATED_YEAR
        defaultPartialSalaryShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where year not equals to DEFAULT_YEAR
        defaultPartialSalaryShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the partialSalaryList where year not equals to UPDATED_YEAR
        defaultPartialSalaryShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultPartialSalaryShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the partialSalaryList where year equals to UPDATED_YEAR
        defaultPartialSalaryShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where year is not null
        defaultPartialSalaryShouldBeFound("year.specified=true");

        // Get all the partialSalaryList where year is null
        defaultPartialSalaryShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where year is greater than or equal to DEFAULT_YEAR
        defaultPartialSalaryShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the partialSalaryList where year is greater than or equal to UPDATED_YEAR
        defaultPartialSalaryShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where year is less than or equal to DEFAULT_YEAR
        defaultPartialSalaryShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the partialSalaryList where year is less than or equal to SMALLER_YEAR
        defaultPartialSalaryShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where year is less than DEFAULT_YEAR
        defaultPartialSalaryShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the partialSalaryList where year is less than UPDATED_YEAR
        defaultPartialSalaryShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where year is greater than DEFAULT_YEAR
        defaultPartialSalaryShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the partialSalaryList where year is greater than SMALLER_YEAR
        defaultPartialSalaryShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where month equals to DEFAULT_MONTH
        defaultPartialSalaryShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the partialSalaryList where month equals to UPDATED_MONTH
        defaultPartialSalaryShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where month not equals to DEFAULT_MONTH
        defaultPartialSalaryShouldNotBeFound("month.notEquals=" + DEFAULT_MONTH);

        // Get all the partialSalaryList where month not equals to UPDATED_MONTH
        defaultPartialSalaryShouldBeFound("month.notEquals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultPartialSalaryShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the partialSalaryList where month equals to UPDATED_MONTH
        defaultPartialSalaryShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where month is not null
        defaultPartialSalaryShouldBeFound("month.specified=true");

        // Get all the partialSalaryList where month is null
        defaultPartialSalaryShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByTotalMonthDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where totalMonthDays equals to DEFAULT_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldBeFound("totalMonthDays.equals=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the partialSalaryList where totalMonthDays equals to UPDATED_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldNotBeFound("totalMonthDays.equals=" + UPDATED_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByTotalMonthDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where totalMonthDays not equals to DEFAULT_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldNotBeFound("totalMonthDays.notEquals=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the partialSalaryList where totalMonthDays not equals to UPDATED_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldBeFound("totalMonthDays.notEquals=" + UPDATED_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByTotalMonthDaysIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where totalMonthDays in DEFAULT_TOTAL_MONTH_DAYS or UPDATED_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldBeFound("totalMonthDays.in=" + DEFAULT_TOTAL_MONTH_DAYS + "," + UPDATED_TOTAL_MONTH_DAYS);

        // Get all the partialSalaryList where totalMonthDays equals to UPDATED_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldNotBeFound("totalMonthDays.in=" + UPDATED_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByTotalMonthDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where totalMonthDays is not null
        defaultPartialSalaryShouldBeFound("totalMonthDays.specified=true");

        // Get all the partialSalaryList where totalMonthDays is null
        defaultPartialSalaryShouldNotBeFound("totalMonthDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByTotalMonthDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where totalMonthDays is greater than or equal to DEFAULT_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldBeFound("totalMonthDays.greaterThanOrEqual=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the partialSalaryList where totalMonthDays is greater than or equal to UPDATED_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldNotBeFound("totalMonthDays.greaterThanOrEqual=" + UPDATED_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByTotalMonthDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where totalMonthDays is less than or equal to DEFAULT_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldBeFound("totalMonthDays.lessThanOrEqual=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the partialSalaryList where totalMonthDays is less than or equal to SMALLER_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldNotBeFound("totalMonthDays.lessThanOrEqual=" + SMALLER_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByTotalMonthDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where totalMonthDays is less than DEFAULT_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldNotBeFound("totalMonthDays.lessThan=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the partialSalaryList where totalMonthDays is less than UPDATED_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldBeFound("totalMonthDays.lessThan=" + UPDATED_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByTotalMonthDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where totalMonthDays is greater than DEFAULT_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldNotBeFound("totalMonthDays.greaterThan=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the partialSalaryList where totalMonthDays is greater than SMALLER_TOTAL_MONTH_DAYS
        defaultPartialSalaryShouldBeFound("totalMonthDays.greaterThan=" + SMALLER_TOTAL_MONTH_DAYS);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fromDate equals to DEFAULT_FROM_DATE
        defaultPartialSalaryShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the partialSalaryList where fromDate equals to UPDATED_FROM_DATE
        defaultPartialSalaryShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fromDate not equals to DEFAULT_FROM_DATE
        defaultPartialSalaryShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the partialSalaryList where fromDate not equals to UPDATED_FROM_DATE
        defaultPartialSalaryShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultPartialSalaryShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the partialSalaryList where fromDate equals to UPDATED_FROM_DATE
        defaultPartialSalaryShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fromDate is not null
        defaultPartialSalaryShouldBeFound("fromDate.specified=true");

        // Get all the partialSalaryList where fromDate is null
        defaultPartialSalaryShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where toDate equals to DEFAULT_TO_DATE
        defaultPartialSalaryShouldBeFound("toDate.equals=" + DEFAULT_TO_DATE);

        // Get all the partialSalaryList where toDate equals to UPDATED_TO_DATE
        defaultPartialSalaryShouldNotBeFound("toDate.equals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByToDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where toDate not equals to DEFAULT_TO_DATE
        defaultPartialSalaryShouldNotBeFound("toDate.notEquals=" + DEFAULT_TO_DATE);

        // Get all the partialSalaryList where toDate not equals to UPDATED_TO_DATE
        defaultPartialSalaryShouldBeFound("toDate.notEquals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByToDateIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where toDate in DEFAULT_TO_DATE or UPDATED_TO_DATE
        defaultPartialSalaryShouldBeFound("toDate.in=" + DEFAULT_TO_DATE + "," + UPDATED_TO_DATE);

        // Get all the partialSalaryList where toDate equals to UPDATED_TO_DATE
        defaultPartialSalaryShouldNotBeFound("toDate.in=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where toDate is not null
        defaultPartialSalaryShouldBeFound("toDate.specified=true");

        // Get all the partialSalaryList where toDate is null
        defaultPartialSalaryShouldNotBeFound("toDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByGrossIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where gross equals to DEFAULT_GROSS
        defaultPartialSalaryShouldBeFound("gross.equals=" + DEFAULT_GROSS);

        // Get all the partialSalaryList where gross equals to UPDATED_GROSS
        defaultPartialSalaryShouldNotBeFound("gross.equals=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByGrossIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where gross not equals to DEFAULT_GROSS
        defaultPartialSalaryShouldNotBeFound("gross.notEquals=" + DEFAULT_GROSS);

        // Get all the partialSalaryList where gross not equals to UPDATED_GROSS
        defaultPartialSalaryShouldBeFound("gross.notEquals=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByGrossIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where gross in DEFAULT_GROSS or UPDATED_GROSS
        defaultPartialSalaryShouldBeFound("gross.in=" + DEFAULT_GROSS + "," + UPDATED_GROSS);

        // Get all the partialSalaryList where gross equals to UPDATED_GROSS
        defaultPartialSalaryShouldNotBeFound("gross.in=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByGrossIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where gross is not null
        defaultPartialSalaryShouldBeFound("gross.specified=true");

        // Get all the partialSalaryList where gross is null
        defaultPartialSalaryShouldNotBeFound("gross.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByGrossIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where gross is greater than or equal to DEFAULT_GROSS
        defaultPartialSalaryShouldBeFound("gross.greaterThanOrEqual=" + DEFAULT_GROSS);

        // Get all the partialSalaryList where gross is greater than or equal to UPDATED_GROSS
        defaultPartialSalaryShouldNotBeFound("gross.greaterThanOrEqual=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByGrossIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where gross is less than or equal to DEFAULT_GROSS
        defaultPartialSalaryShouldBeFound("gross.lessThanOrEqual=" + DEFAULT_GROSS);

        // Get all the partialSalaryList where gross is less than or equal to SMALLER_GROSS
        defaultPartialSalaryShouldNotBeFound("gross.lessThanOrEqual=" + SMALLER_GROSS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByGrossIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where gross is less than DEFAULT_GROSS
        defaultPartialSalaryShouldNotBeFound("gross.lessThan=" + DEFAULT_GROSS);

        // Get all the partialSalaryList where gross is less than UPDATED_GROSS
        defaultPartialSalaryShouldBeFound("gross.lessThan=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByGrossIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where gross is greater than DEFAULT_GROSS
        defaultPartialSalaryShouldNotBeFound("gross.greaterThan=" + DEFAULT_GROSS);

        // Get all the partialSalaryList where gross is greater than SMALLER_GROSS
        defaultPartialSalaryShouldBeFound("gross.greaterThan=" + SMALLER_GROSS);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByBasicIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basic equals to DEFAULT_BASIC
        defaultPartialSalaryShouldBeFound("basic.equals=" + DEFAULT_BASIC);

        // Get all the partialSalaryList where basic equals to UPDATED_BASIC
        defaultPartialSalaryShouldNotBeFound("basic.equals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basic not equals to DEFAULT_BASIC
        defaultPartialSalaryShouldNotBeFound("basic.notEquals=" + DEFAULT_BASIC);

        // Get all the partialSalaryList where basic not equals to UPDATED_BASIC
        defaultPartialSalaryShouldBeFound("basic.notEquals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basic in DEFAULT_BASIC or UPDATED_BASIC
        defaultPartialSalaryShouldBeFound("basic.in=" + DEFAULT_BASIC + "," + UPDATED_BASIC);

        // Get all the partialSalaryList where basic equals to UPDATED_BASIC
        defaultPartialSalaryShouldNotBeFound("basic.in=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basic is not null
        defaultPartialSalaryShouldBeFound("basic.specified=true");

        // Get all the partialSalaryList where basic is null
        defaultPartialSalaryShouldNotBeFound("basic.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basic is greater than or equal to DEFAULT_BASIC
        defaultPartialSalaryShouldBeFound("basic.greaterThanOrEqual=" + DEFAULT_BASIC);

        // Get all the partialSalaryList where basic is greater than or equal to UPDATED_BASIC
        defaultPartialSalaryShouldNotBeFound("basic.greaterThanOrEqual=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basic is less than or equal to DEFAULT_BASIC
        defaultPartialSalaryShouldBeFound("basic.lessThanOrEqual=" + DEFAULT_BASIC);

        // Get all the partialSalaryList where basic is less than or equal to SMALLER_BASIC
        defaultPartialSalaryShouldNotBeFound("basic.lessThanOrEqual=" + SMALLER_BASIC);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basic is less than DEFAULT_BASIC
        defaultPartialSalaryShouldNotBeFound("basic.lessThan=" + DEFAULT_BASIC);

        // Get all the partialSalaryList where basic is less than UPDATED_BASIC
        defaultPartialSalaryShouldBeFound("basic.lessThan=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basic is greater than DEFAULT_BASIC
        defaultPartialSalaryShouldNotBeFound("basic.greaterThan=" + DEFAULT_BASIC);

        // Get all the partialSalaryList where basic is greater than SMALLER_BASIC
        defaultPartialSalaryShouldBeFound("basic.greaterThan=" + SMALLER_BASIC);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByBasicPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basicPercent equals to DEFAULT_BASIC_PERCENT
        defaultPartialSalaryShouldBeFound("basicPercent.equals=" + DEFAULT_BASIC_PERCENT);

        // Get all the partialSalaryList where basicPercent equals to UPDATED_BASIC_PERCENT
        defaultPartialSalaryShouldNotBeFound("basicPercent.equals=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basicPercent not equals to DEFAULT_BASIC_PERCENT
        defaultPartialSalaryShouldNotBeFound("basicPercent.notEquals=" + DEFAULT_BASIC_PERCENT);

        // Get all the partialSalaryList where basicPercent not equals to UPDATED_BASIC_PERCENT
        defaultPartialSalaryShouldBeFound("basicPercent.notEquals=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicPercentIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basicPercent in DEFAULT_BASIC_PERCENT or UPDATED_BASIC_PERCENT
        defaultPartialSalaryShouldBeFound("basicPercent.in=" + DEFAULT_BASIC_PERCENT + "," + UPDATED_BASIC_PERCENT);

        // Get all the partialSalaryList where basicPercent equals to UPDATED_BASIC_PERCENT
        defaultPartialSalaryShouldNotBeFound("basicPercent.in=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basicPercent is not null
        defaultPartialSalaryShouldBeFound("basicPercent.specified=true");

        // Get all the partialSalaryList where basicPercent is null
        defaultPartialSalaryShouldNotBeFound("basicPercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basicPercent is greater than or equal to DEFAULT_BASIC_PERCENT
        defaultPartialSalaryShouldBeFound("basicPercent.greaterThanOrEqual=" + DEFAULT_BASIC_PERCENT);

        // Get all the partialSalaryList where basicPercent is greater than or equal to UPDATED_BASIC_PERCENT
        defaultPartialSalaryShouldNotBeFound("basicPercent.greaterThanOrEqual=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basicPercent is less than or equal to DEFAULT_BASIC_PERCENT
        defaultPartialSalaryShouldBeFound("basicPercent.lessThanOrEqual=" + DEFAULT_BASIC_PERCENT);

        // Get all the partialSalaryList where basicPercent is less than or equal to SMALLER_BASIC_PERCENT
        defaultPartialSalaryShouldNotBeFound("basicPercent.lessThanOrEqual=" + SMALLER_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basicPercent is less than DEFAULT_BASIC_PERCENT
        defaultPartialSalaryShouldNotBeFound("basicPercent.lessThan=" + DEFAULT_BASIC_PERCENT);

        // Get all the partialSalaryList where basicPercent is less than UPDATED_BASIC_PERCENT
        defaultPartialSalaryShouldBeFound("basicPercent.lessThan=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByBasicPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where basicPercent is greater than DEFAULT_BASIC_PERCENT
        defaultPartialSalaryShouldNotBeFound("basicPercent.greaterThan=" + DEFAULT_BASIC_PERCENT);

        // Get all the partialSalaryList where basicPercent is greater than SMALLER_BASIC_PERCENT
        defaultPartialSalaryShouldBeFound("basicPercent.greaterThan=" + SMALLER_BASIC_PERCENT);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRent equals to DEFAULT_HOUSE_RENT
        defaultPartialSalaryShouldBeFound("houseRent.equals=" + DEFAULT_HOUSE_RENT);

        // Get all the partialSalaryList where houseRent equals to UPDATED_HOUSE_RENT
        defaultPartialSalaryShouldNotBeFound("houseRent.equals=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRent not equals to DEFAULT_HOUSE_RENT
        defaultPartialSalaryShouldNotBeFound("houseRent.notEquals=" + DEFAULT_HOUSE_RENT);

        // Get all the partialSalaryList where houseRent not equals to UPDATED_HOUSE_RENT
        defaultPartialSalaryShouldBeFound("houseRent.notEquals=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRent in DEFAULT_HOUSE_RENT or UPDATED_HOUSE_RENT
        defaultPartialSalaryShouldBeFound("houseRent.in=" + DEFAULT_HOUSE_RENT + "," + UPDATED_HOUSE_RENT);

        // Get all the partialSalaryList where houseRent equals to UPDATED_HOUSE_RENT
        defaultPartialSalaryShouldNotBeFound("houseRent.in=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRent is not null
        defaultPartialSalaryShouldBeFound("houseRent.specified=true");

        // Get all the partialSalaryList where houseRent is null
        defaultPartialSalaryShouldNotBeFound("houseRent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRent is greater than or equal to DEFAULT_HOUSE_RENT
        defaultPartialSalaryShouldBeFound("houseRent.greaterThanOrEqual=" + DEFAULT_HOUSE_RENT);

        // Get all the partialSalaryList where houseRent is greater than or equal to UPDATED_HOUSE_RENT
        defaultPartialSalaryShouldNotBeFound("houseRent.greaterThanOrEqual=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRent is less than or equal to DEFAULT_HOUSE_RENT
        defaultPartialSalaryShouldBeFound("houseRent.lessThanOrEqual=" + DEFAULT_HOUSE_RENT);

        // Get all the partialSalaryList where houseRent is less than or equal to SMALLER_HOUSE_RENT
        defaultPartialSalaryShouldNotBeFound("houseRent.lessThanOrEqual=" + SMALLER_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRent is less than DEFAULT_HOUSE_RENT
        defaultPartialSalaryShouldNotBeFound("houseRent.lessThan=" + DEFAULT_HOUSE_RENT);

        // Get all the partialSalaryList where houseRent is less than UPDATED_HOUSE_RENT
        defaultPartialSalaryShouldBeFound("houseRent.lessThan=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRent is greater than DEFAULT_HOUSE_RENT
        defaultPartialSalaryShouldNotBeFound("houseRent.greaterThan=" + DEFAULT_HOUSE_RENT);

        // Get all the partialSalaryList where houseRent is greater than SMALLER_HOUSE_RENT
        defaultPartialSalaryShouldBeFound("houseRent.greaterThan=" + SMALLER_HOUSE_RENT);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRentPercent equals to DEFAULT_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldBeFound("houseRentPercent.equals=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the partialSalaryList where houseRentPercent equals to UPDATED_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldNotBeFound("houseRentPercent.equals=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRentPercent not equals to DEFAULT_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldNotBeFound("houseRentPercent.notEquals=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the partialSalaryList where houseRentPercent not equals to UPDATED_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldBeFound("houseRentPercent.notEquals=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentPercentIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRentPercent in DEFAULT_HOUSE_RENT_PERCENT or UPDATED_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldBeFound("houseRentPercent.in=" + DEFAULT_HOUSE_RENT_PERCENT + "," + UPDATED_HOUSE_RENT_PERCENT);

        // Get all the partialSalaryList where houseRentPercent equals to UPDATED_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldNotBeFound("houseRentPercent.in=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRentPercent is not null
        defaultPartialSalaryShouldBeFound("houseRentPercent.specified=true");

        // Get all the partialSalaryList where houseRentPercent is null
        defaultPartialSalaryShouldNotBeFound("houseRentPercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRentPercent is greater than or equal to DEFAULT_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldBeFound("houseRentPercent.greaterThanOrEqual=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the partialSalaryList where houseRentPercent is greater than or equal to UPDATED_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldNotBeFound("houseRentPercent.greaterThanOrEqual=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRentPercent is less than or equal to DEFAULT_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldBeFound("houseRentPercent.lessThanOrEqual=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the partialSalaryList where houseRentPercent is less than or equal to SMALLER_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldNotBeFound("houseRentPercent.lessThanOrEqual=" + SMALLER_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRentPercent is less than DEFAULT_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldNotBeFound("houseRentPercent.lessThan=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the partialSalaryList where houseRentPercent is less than UPDATED_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldBeFound("houseRentPercent.lessThan=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByHouseRentPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where houseRentPercent is greater than DEFAULT_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldNotBeFound("houseRentPercent.greaterThan=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the partialSalaryList where houseRentPercent is greater than SMALLER_HOUSE_RENT_PERCENT
        defaultPartialSalaryShouldBeFound("houseRentPercent.greaterThan=" + SMALLER_HOUSE_RENT_PERCENT);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowance equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldBeFound("medicalAllowance.equals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the partialSalaryList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("medicalAllowance.equals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowance not equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("medicalAllowance.notEquals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the partialSalaryList where medicalAllowance not equals to UPDATED_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldBeFound("medicalAllowance.notEquals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowance in DEFAULT_MEDICAL_ALLOWANCE or UPDATED_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldBeFound("medicalAllowance.in=" + DEFAULT_MEDICAL_ALLOWANCE + "," + UPDATED_MEDICAL_ALLOWANCE);

        // Get all the partialSalaryList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("medicalAllowance.in=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowance is not null
        defaultPartialSalaryShouldBeFound("medicalAllowance.specified=true");

        // Get all the partialSalaryList where medicalAllowance is null
        defaultPartialSalaryShouldNotBeFound("medicalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowance is greater than or equal to DEFAULT_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldBeFound("medicalAllowance.greaterThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the partialSalaryList where medicalAllowance is greater than or equal to UPDATED_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("medicalAllowance.greaterThanOrEqual=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowance is less than or equal to DEFAULT_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldBeFound("medicalAllowance.lessThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the partialSalaryList where medicalAllowance is less than or equal to SMALLER_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("medicalAllowance.lessThanOrEqual=" + SMALLER_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowance is less than DEFAULT_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("medicalAllowance.lessThan=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the partialSalaryList where medicalAllowance is less than UPDATED_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldBeFound("medicalAllowance.lessThan=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowance is greater than DEFAULT_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("medicalAllowance.greaterThan=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the partialSalaryList where medicalAllowance is greater than SMALLER_MEDICAL_ALLOWANCE
        defaultPartialSalaryShouldBeFound("medicalAllowance.greaterThan=" + SMALLER_MEDICAL_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowancePercent equals to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("medicalAllowancePercent.equals=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where medicalAllowancePercent equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("medicalAllowancePercent.equals=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowancePercent not equals to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("medicalAllowancePercent.notEquals=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where medicalAllowancePercent not equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("medicalAllowancePercent.notEquals=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowancePercent in DEFAULT_MEDICAL_ALLOWANCE_PERCENT or UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("medicalAllowancePercent.in=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT + "," + UPDATED_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where medicalAllowancePercent equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("medicalAllowancePercent.in=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowancePercent is not null
        defaultPartialSalaryShouldBeFound("medicalAllowancePercent.specified=true");

        // Get all the partialSalaryList where medicalAllowancePercent is null
        defaultPartialSalaryShouldNotBeFound("medicalAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowancePercent is greater than or equal to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("medicalAllowancePercent.greaterThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where medicalAllowancePercent is greater than or equal to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("medicalAllowancePercent.greaterThanOrEqual=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowancePercent is less than or equal to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("medicalAllowancePercent.lessThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where medicalAllowancePercent is less than or equal to SMALLER_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("medicalAllowancePercent.lessThanOrEqual=" + SMALLER_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowancePercent is less than DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("medicalAllowancePercent.lessThan=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where medicalAllowancePercent is less than UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("medicalAllowancePercent.lessThan=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByMedicalAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where medicalAllowancePercent is greater than DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("medicalAllowancePercent.greaterThan=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where medicalAllowancePercent is greater than SMALLER_MEDICAL_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("medicalAllowancePercent.greaterThan=" + SMALLER_MEDICAL_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowance equals to DEFAULT_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldBeFound("convinceAllowance.equals=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the partialSalaryList where convinceAllowance equals to UPDATED_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("convinceAllowance.equals=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowance not equals to DEFAULT_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("convinceAllowance.notEquals=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the partialSalaryList where convinceAllowance not equals to UPDATED_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldBeFound("convinceAllowance.notEquals=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowance in DEFAULT_CONVINCE_ALLOWANCE or UPDATED_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldBeFound("convinceAllowance.in=" + DEFAULT_CONVINCE_ALLOWANCE + "," + UPDATED_CONVINCE_ALLOWANCE);

        // Get all the partialSalaryList where convinceAllowance equals to UPDATED_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("convinceAllowance.in=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowance is not null
        defaultPartialSalaryShouldBeFound("convinceAllowance.specified=true");

        // Get all the partialSalaryList where convinceAllowance is null
        defaultPartialSalaryShouldNotBeFound("convinceAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowance is greater than or equal to DEFAULT_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldBeFound("convinceAllowance.greaterThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the partialSalaryList where convinceAllowance is greater than or equal to UPDATED_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("convinceAllowance.greaterThanOrEqual=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowance is less than or equal to DEFAULT_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldBeFound("convinceAllowance.lessThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the partialSalaryList where convinceAllowance is less than or equal to SMALLER_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("convinceAllowance.lessThanOrEqual=" + SMALLER_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowance is less than DEFAULT_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("convinceAllowance.lessThan=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the partialSalaryList where convinceAllowance is less than UPDATED_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldBeFound("convinceAllowance.lessThan=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowance is greater than DEFAULT_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("convinceAllowance.greaterThan=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the partialSalaryList where convinceAllowance is greater than SMALLER_CONVINCE_ALLOWANCE
        defaultPartialSalaryShouldBeFound("convinceAllowance.greaterThan=" + SMALLER_CONVINCE_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowancePercent equals to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("convinceAllowancePercent.equals=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where convinceAllowancePercent equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("convinceAllowancePercent.equals=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowancePercent not equals to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("convinceAllowancePercent.notEquals=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where convinceAllowancePercent not equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("convinceAllowancePercent.notEquals=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowancePercent in DEFAULT_CONVINCE_ALLOWANCE_PERCENT or UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("convinceAllowancePercent.in=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT + "," + UPDATED_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where convinceAllowancePercent equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("convinceAllowancePercent.in=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowancePercent is not null
        defaultPartialSalaryShouldBeFound("convinceAllowancePercent.specified=true");

        // Get all the partialSalaryList where convinceAllowancePercent is null
        defaultPartialSalaryShouldNotBeFound("convinceAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowancePercent is greater than or equal to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("convinceAllowancePercent.greaterThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where convinceAllowancePercent is greater than or equal to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("convinceAllowancePercent.greaterThanOrEqual=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowancePercent is less than or equal to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("convinceAllowancePercent.lessThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where convinceAllowancePercent is less than or equal to SMALLER_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("convinceAllowancePercent.lessThanOrEqual=" + SMALLER_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowancePercent is less than DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("convinceAllowancePercent.lessThan=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where convinceAllowancePercent is less than UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("convinceAllowancePercent.lessThan=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByConvinceAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where convinceAllowancePercent is greater than DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("convinceAllowancePercent.greaterThan=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where convinceAllowancePercent is greater than SMALLER_CONVINCE_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("convinceAllowancePercent.greaterThan=" + SMALLER_CONVINCE_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowance equals to DEFAULT_FOOD_ALLOWANCE
        defaultPartialSalaryShouldBeFound("foodAllowance.equals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the partialSalaryList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("foodAllowance.equals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowance not equals to DEFAULT_FOOD_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("foodAllowance.notEquals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the partialSalaryList where foodAllowance not equals to UPDATED_FOOD_ALLOWANCE
        defaultPartialSalaryShouldBeFound("foodAllowance.notEquals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowance in DEFAULT_FOOD_ALLOWANCE or UPDATED_FOOD_ALLOWANCE
        defaultPartialSalaryShouldBeFound("foodAllowance.in=" + DEFAULT_FOOD_ALLOWANCE + "," + UPDATED_FOOD_ALLOWANCE);

        // Get all the partialSalaryList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("foodAllowance.in=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowance is not null
        defaultPartialSalaryShouldBeFound("foodAllowance.specified=true");

        // Get all the partialSalaryList where foodAllowance is null
        defaultPartialSalaryShouldNotBeFound("foodAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowance is greater than or equal to DEFAULT_FOOD_ALLOWANCE
        defaultPartialSalaryShouldBeFound("foodAllowance.greaterThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the partialSalaryList where foodAllowance is greater than or equal to UPDATED_FOOD_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("foodAllowance.greaterThanOrEqual=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowance is less than or equal to DEFAULT_FOOD_ALLOWANCE
        defaultPartialSalaryShouldBeFound("foodAllowance.lessThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the partialSalaryList where foodAllowance is less than or equal to SMALLER_FOOD_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("foodAllowance.lessThanOrEqual=" + SMALLER_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowance is less than DEFAULT_FOOD_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("foodAllowance.lessThan=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the partialSalaryList where foodAllowance is less than UPDATED_FOOD_ALLOWANCE
        defaultPartialSalaryShouldBeFound("foodAllowance.lessThan=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowance is greater than DEFAULT_FOOD_ALLOWANCE
        defaultPartialSalaryShouldNotBeFound("foodAllowance.greaterThan=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the partialSalaryList where foodAllowance is greater than SMALLER_FOOD_ALLOWANCE
        defaultPartialSalaryShouldBeFound("foodAllowance.greaterThan=" + SMALLER_FOOD_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowancePercent equals to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("foodAllowancePercent.equals=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where foodAllowancePercent equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("foodAllowancePercent.equals=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowancePercent not equals to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("foodAllowancePercent.notEquals=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where foodAllowancePercent not equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("foodAllowancePercent.notEquals=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowancePercent in DEFAULT_FOOD_ALLOWANCE_PERCENT or UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("foodAllowancePercent.in=" + DEFAULT_FOOD_ALLOWANCE_PERCENT + "," + UPDATED_FOOD_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where foodAllowancePercent equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("foodAllowancePercent.in=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowancePercent is not null
        defaultPartialSalaryShouldBeFound("foodAllowancePercent.specified=true");

        // Get all the partialSalaryList where foodAllowancePercent is null
        defaultPartialSalaryShouldNotBeFound("foodAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowancePercent is greater than or equal to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("foodAllowancePercent.greaterThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where foodAllowancePercent is greater than or equal to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("foodAllowancePercent.greaterThanOrEqual=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowancePercent is less than or equal to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("foodAllowancePercent.lessThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where foodAllowancePercent is less than or equal to SMALLER_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("foodAllowancePercent.lessThanOrEqual=" + SMALLER_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowancePercent is less than DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("foodAllowancePercent.lessThan=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where foodAllowancePercent is less than UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("foodAllowancePercent.lessThan=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFoodAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where foodAllowancePercent is greater than DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldNotBeFound("foodAllowancePercent.greaterThan=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the partialSalaryList where foodAllowancePercent is greater than SMALLER_FOOD_ALLOWANCE_PERCENT
        defaultPartialSalaryShouldBeFound("foodAllowancePercent.greaterThan=" + SMALLER_FOOD_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByFineIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fine equals to DEFAULT_FINE
        defaultPartialSalaryShouldBeFound("fine.equals=" + DEFAULT_FINE);

        // Get all the partialSalaryList where fine equals to UPDATED_FINE
        defaultPartialSalaryShouldNotBeFound("fine.equals=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fine not equals to DEFAULT_FINE
        defaultPartialSalaryShouldNotBeFound("fine.notEquals=" + DEFAULT_FINE);

        // Get all the partialSalaryList where fine not equals to UPDATED_FINE
        defaultPartialSalaryShouldBeFound("fine.notEquals=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFineIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fine in DEFAULT_FINE or UPDATED_FINE
        defaultPartialSalaryShouldBeFound("fine.in=" + DEFAULT_FINE + "," + UPDATED_FINE);

        // Get all the partialSalaryList where fine equals to UPDATED_FINE
        defaultPartialSalaryShouldNotBeFound("fine.in=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFineIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fine is not null
        defaultPartialSalaryShouldBeFound("fine.specified=true");

        // Get all the partialSalaryList where fine is null
        defaultPartialSalaryShouldNotBeFound("fine.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fine is greater than or equal to DEFAULT_FINE
        defaultPartialSalaryShouldBeFound("fine.greaterThanOrEqual=" + DEFAULT_FINE);

        // Get all the partialSalaryList where fine is greater than or equal to UPDATED_FINE
        defaultPartialSalaryShouldNotBeFound("fine.greaterThanOrEqual=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fine is less than or equal to DEFAULT_FINE
        defaultPartialSalaryShouldBeFound("fine.lessThanOrEqual=" + DEFAULT_FINE);

        // Get all the partialSalaryList where fine is less than or equal to SMALLER_FINE
        defaultPartialSalaryShouldNotBeFound("fine.lessThanOrEqual=" + SMALLER_FINE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFineIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fine is less than DEFAULT_FINE
        defaultPartialSalaryShouldNotBeFound("fine.lessThan=" + DEFAULT_FINE);

        // Get all the partialSalaryList where fine is less than UPDATED_FINE
        defaultPartialSalaryShouldBeFound("fine.lessThan=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByFineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where fine is greater than DEFAULT_FINE
        defaultPartialSalaryShouldNotBeFound("fine.greaterThan=" + DEFAULT_FINE);

        // Get all the partialSalaryList where fine is greater than SMALLER_FINE
        defaultPartialSalaryShouldBeFound("fine.greaterThan=" + SMALLER_FINE);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByAdvanceIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where advance equals to DEFAULT_ADVANCE
        defaultPartialSalaryShouldBeFound("advance.equals=" + DEFAULT_ADVANCE);

        // Get all the partialSalaryList where advance equals to UPDATED_ADVANCE
        defaultPartialSalaryShouldNotBeFound("advance.equals=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByAdvanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where advance not equals to DEFAULT_ADVANCE
        defaultPartialSalaryShouldNotBeFound("advance.notEquals=" + DEFAULT_ADVANCE);

        // Get all the partialSalaryList where advance not equals to UPDATED_ADVANCE
        defaultPartialSalaryShouldBeFound("advance.notEquals=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByAdvanceIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where advance in DEFAULT_ADVANCE or UPDATED_ADVANCE
        defaultPartialSalaryShouldBeFound("advance.in=" + DEFAULT_ADVANCE + "," + UPDATED_ADVANCE);

        // Get all the partialSalaryList where advance equals to UPDATED_ADVANCE
        defaultPartialSalaryShouldNotBeFound("advance.in=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByAdvanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where advance is not null
        defaultPartialSalaryShouldBeFound("advance.specified=true");

        // Get all the partialSalaryList where advance is null
        defaultPartialSalaryShouldNotBeFound("advance.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByAdvanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where advance is greater than or equal to DEFAULT_ADVANCE
        defaultPartialSalaryShouldBeFound("advance.greaterThanOrEqual=" + DEFAULT_ADVANCE);

        // Get all the partialSalaryList where advance is greater than or equal to UPDATED_ADVANCE
        defaultPartialSalaryShouldNotBeFound("advance.greaterThanOrEqual=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByAdvanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where advance is less than or equal to DEFAULT_ADVANCE
        defaultPartialSalaryShouldBeFound("advance.lessThanOrEqual=" + DEFAULT_ADVANCE);

        // Get all the partialSalaryList where advance is less than or equal to SMALLER_ADVANCE
        defaultPartialSalaryShouldNotBeFound("advance.lessThanOrEqual=" + SMALLER_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByAdvanceIsLessThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where advance is less than DEFAULT_ADVANCE
        defaultPartialSalaryShouldNotBeFound("advance.lessThan=" + DEFAULT_ADVANCE);

        // Get all the partialSalaryList where advance is less than UPDATED_ADVANCE
        defaultPartialSalaryShouldBeFound("advance.lessThan=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByAdvanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where advance is greater than DEFAULT_ADVANCE
        defaultPartialSalaryShouldNotBeFound("advance.greaterThan=" + DEFAULT_ADVANCE);

        // Get all the partialSalaryList where advance is greater than SMALLER_ADVANCE
        defaultPartialSalaryShouldBeFound("advance.greaterThan=" + SMALLER_ADVANCE);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where status equals to DEFAULT_STATUS
        defaultPartialSalaryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the partialSalaryList where status equals to UPDATED_STATUS
        defaultPartialSalaryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where status not equals to DEFAULT_STATUS
        defaultPartialSalaryShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the partialSalaryList where status not equals to UPDATED_STATUS
        defaultPartialSalaryShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPartialSalaryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the partialSalaryList where status equals to UPDATED_STATUS
        defaultPartialSalaryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where status is not null
        defaultPartialSalaryShouldBeFound("status.specified=true");

        // Get all the partialSalaryList where status is null
        defaultPartialSalaryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByExecutedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where executedOn equals to DEFAULT_EXECUTED_ON
        defaultPartialSalaryShouldBeFound("executedOn.equals=" + DEFAULT_EXECUTED_ON);

        // Get all the partialSalaryList where executedOn equals to UPDATED_EXECUTED_ON
        defaultPartialSalaryShouldNotBeFound("executedOn.equals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByExecutedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where executedOn not equals to DEFAULT_EXECUTED_ON
        defaultPartialSalaryShouldNotBeFound("executedOn.notEquals=" + DEFAULT_EXECUTED_ON);

        // Get all the partialSalaryList where executedOn not equals to UPDATED_EXECUTED_ON
        defaultPartialSalaryShouldBeFound("executedOn.notEquals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByExecutedOnIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where executedOn in DEFAULT_EXECUTED_ON or UPDATED_EXECUTED_ON
        defaultPartialSalaryShouldBeFound("executedOn.in=" + DEFAULT_EXECUTED_ON + "," + UPDATED_EXECUTED_ON);

        // Get all the partialSalaryList where executedOn equals to UPDATED_EXECUTED_ON
        defaultPartialSalaryShouldNotBeFound("executedOn.in=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByExecutedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where executedOn is not null
        defaultPartialSalaryShouldBeFound("executedOn.specified=true");

        // Get all the partialSalaryList where executedOn is null
        defaultPartialSalaryShouldNotBeFound("executedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByExecutedByIsEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where executedBy equals to DEFAULT_EXECUTED_BY
        defaultPartialSalaryShouldBeFound("executedBy.equals=" + DEFAULT_EXECUTED_BY);

        // Get all the partialSalaryList where executedBy equals to UPDATED_EXECUTED_BY
        defaultPartialSalaryShouldNotBeFound("executedBy.equals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByExecutedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where executedBy not equals to DEFAULT_EXECUTED_BY
        defaultPartialSalaryShouldNotBeFound("executedBy.notEquals=" + DEFAULT_EXECUTED_BY);

        // Get all the partialSalaryList where executedBy not equals to UPDATED_EXECUTED_BY
        defaultPartialSalaryShouldBeFound("executedBy.notEquals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByExecutedByIsInShouldWork() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where executedBy in DEFAULT_EXECUTED_BY or UPDATED_EXECUTED_BY
        defaultPartialSalaryShouldBeFound("executedBy.in=" + DEFAULT_EXECUTED_BY + "," + UPDATED_EXECUTED_BY);

        // Get all the partialSalaryList where executedBy equals to UPDATED_EXECUTED_BY
        defaultPartialSalaryShouldNotBeFound("executedBy.in=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByExecutedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where executedBy is not null
        defaultPartialSalaryShouldBeFound("executedBy.specified=true");

        // Get all the partialSalaryList where executedBy is null
        defaultPartialSalaryShouldNotBeFound("executedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartialSalariesByExecutedByContainsSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where executedBy contains DEFAULT_EXECUTED_BY
        defaultPartialSalaryShouldBeFound("executedBy.contains=" + DEFAULT_EXECUTED_BY);

        // Get all the partialSalaryList where executedBy contains UPDATED_EXECUTED_BY
        defaultPartialSalaryShouldNotBeFound("executedBy.contains=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllPartialSalariesByExecutedByNotContainsSomething() throws Exception {
        // Initialize the database
        partialSalaryRepository.saveAndFlush(partialSalary);

        // Get all the partialSalaryList where executedBy does not contain DEFAULT_EXECUTED_BY
        defaultPartialSalaryShouldNotBeFound("executedBy.doesNotContain=" + DEFAULT_EXECUTED_BY);

        // Get all the partialSalaryList where executedBy does not contain UPDATED_EXECUTED_BY
        defaultPartialSalaryShouldBeFound("executedBy.doesNotContain=" + UPDATED_EXECUTED_BY);
    }


    @Test
    @Transactional
    public void getAllPartialSalariesByEmployeeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Employee employee = partialSalary.getEmployee();
        partialSalaryRepository.saveAndFlush(partialSalary);
        Long employeeId = employee.getId();

        // Get all the partialSalaryList where employee equals to employeeId
        defaultPartialSalaryShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the partialSalaryList where employee equals to employeeId + 1
        defaultPartialSalaryShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartialSalaryShouldBeFound(String filter) throws Exception {
        restPartialSalaryMockMvc.perform(get("/api/partial-salaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partialSalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].totalMonthDays").value(hasItem(DEFAULT_TOTAL_MONTH_DAYS)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].gross").value(hasItem(DEFAULT_GROSS.intValue())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].basicPercent").value(hasItem(DEFAULT_BASIC_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRentPercent").value(hasItem(DEFAULT_HOUSE_RENT_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowancePercent").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowance").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowancePercent").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowancePercent").value(hasItem(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE.intValue())))
            .andExpect(jsonPath("$.[*].advance").value(hasItem(DEFAULT_ADVANCE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));

        // Check, that the count call also returns 1
        restPartialSalaryMockMvc.perform(get("/api/partial-salaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartialSalaryShouldNotBeFound(String filter) throws Exception {
        restPartialSalaryMockMvc.perform(get("/api/partial-salaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartialSalaryMockMvc.perform(get("/api/partial-salaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPartialSalary() throws Exception {
        // Get the partialSalary
        restPartialSalaryMockMvc.perform(get("/api/partial-salaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartialSalary() throws Exception {
        // Initialize the database
        partialSalaryService.save(partialSalary);

        int databaseSizeBeforeUpdate = partialSalaryRepository.findAll().size();

        // Update the partialSalary
        PartialSalary updatedPartialSalary = partialSalaryRepository.findById(partialSalary.getId()).get();
        // Disconnect from session so that the updates on updatedPartialSalary are not directly saved in db
        em.detach(updatedPartialSalary);
        updatedPartialSalary
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .totalMonthDays(UPDATED_TOTAL_MONTH_DAYS)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .gross(UPDATED_GROSS)
            .basic(UPDATED_BASIC)
            .basicPercent(UPDATED_BASIC_PERCENT)
            .houseRent(UPDATED_HOUSE_RENT)
            .houseRentPercent(UPDATED_HOUSE_RENT_PERCENT)
            .medicalAllowance(UPDATED_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(UPDATED_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(UPDATED_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(UPDATED_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(UPDATED_FOOD_ALLOWANCE)
            .foodAllowancePercent(UPDATED_FOOD_ALLOWANCE_PERCENT)
            .fine(UPDATED_FINE)
            .advance(UPDATED_ADVANCE)
            .status(UPDATED_STATUS)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY)
            .note(UPDATED_NOTE);

        restPartialSalaryMockMvc.perform(put("/api/partial-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartialSalary)))
            .andExpect(status().isOk());

        // Validate the PartialSalary in the database
        List<PartialSalary> partialSalaryList = partialSalaryRepository.findAll();
        assertThat(partialSalaryList).hasSize(databaseSizeBeforeUpdate);
        PartialSalary testPartialSalary = partialSalaryList.get(partialSalaryList.size() - 1);
        assertThat(testPartialSalary.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPartialSalary.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testPartialSalary.getTotalMonthDays()).isEqualTo(UPDATED_TOTAL_MONTH_DAYS);
        assertThat(testPartialSalary.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartialSalary.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testPartialSalary.getGross()).isEqualTo(UPDATED_GROSS);
        assertThat(testPartialSalary.getBasic()).isEqualTo(UPDATED_BASIC);
        assertThat(testPartialSalary.getBasicPercent()).isEqualTo(UPDATED_BASIC_PERCENT);
        assertThat(testPartialSalary.getHouseRent()).isEqualTo(UPDATED_HOUSE_RENT);
        assertThat(testPartialSalary.getHouseRentPercent()).isEqualTo(UPDATED_HOUSE_RENT_PERCENT);
        assertThat(testPartialSalary.getMedicalAllowance()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE);
        assertThat(testPartialSalary.getMedicalAllowancePercent()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE_PERCENT);
        assertThat(testPartialSalary.getConvinceAllowance()).isEqualTo(UPDATED_CONVINCE_ALLOWANCE);
        assertThat(testPartialSalary.getConvinceAllowancePercent()).isEqualTo(UPDATED_CONVINCE_ALLOWANCE_PERCENT);
        assertThat(testPartialSalary.getFoodAllowance()).isEqualTo(UPDATED_FOOD_ALLOWANCE);
        assertThat(testPartialSalary.getFoodAllowancePercent()).isEqualTo(UPDATED_FOOD_ALLOWANCE_PERCENT);
        assertThat(testPartialSalary.getFine()).isEqualTo(UPDATED_FINE);
        assertThat(testPartialSalary.getAdvance()).isEqualTo(UPDATED_ADVANCE);
        assertThat(testPartialSalary.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPartialSalary.getExecutedOn()).isEqualTo(UPDATED_EXECUTED_ON);
        assertThat(testPartialSalary.getExecutedBy()).isEqualTo(UPDATED_EXECUTED_BY);
        assertThat(testPartialSalary.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartialSalary() throws Exception {
        int databaseSizeBeforeUpdate = partialSalaryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartialSalaryMockMvc.perform(put("/api/partial-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partialSalary)))
            .andExpect(status().isBadRequest());

        // Validate the PartialSalary in the database
        List<PartialSalary> partialSalaryList = partialSalaryRepository.findAll();
        assertThat(partialSalaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartialSalary() throws Exception {
        // Initialize the database
        partialSalaryService.save(partialSalary);

        int databaseSizeBeforeDelete = partialSalaryRepository.findAll().size();

        // Delete the partialSalary
        restPartialSalaryMockMvc.perform(delete("/api/partial-salaries/{id}", partialSalary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartialSalary> partialSalaryList = partialSalaryRepository.findAll();
        assertThat(partialSalaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
