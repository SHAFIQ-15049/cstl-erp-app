package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.MonthlySalary;
import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.domain.Department;
import software.cstl.repository.MonthlySalaryRepository;
import software.cstl.service.MonthlySalaryService;
import software.cstl.service.dto.MonthlySalaryCriteria;
import software.cstl.service.MonthlySalaryQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
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
 * Integration tests for the {@link MonthlySalaryResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MonthlySalaryResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final MonthType DEFAULT_MONTH = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH = MonthType.FEBRUARY;

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SalaryExecutionStatus DEFAULT_STATUS = SalaryExecutionStatus.DONE;
    private static final SalaryExecutionStatus UPDATED_STATUS = SalaryExecutionStatus.NOT_DONE;

    private static final Instant DEFAULT_EXECUTED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXECUTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_EXECUTED_BY = "BBBBBBBBBB";

    @Autowired
    private MonthlySalaryRepository monthlySalaryRepository;

    @Autowired
    private MonthlySalaryService monthlySalaryService;

    @Autowired
    private MonthlySalaryQueryService monthlySalaryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMonthlySalaryMockMvc;

    private MonthlySalary monthlySalary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlySalary createEntity(EntityManager em) {
        MonthlySalary monthlySalary = new MonthlySalary()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .status(DEFAULT_STATUS)
            .executedOn(DEFAULT_EXECUTED_ON)
            .executedBy(DEFAULT_EXECUTED_BY);
        return monthlySalary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlySalary createUpdatedEntity(EntityManager em) {
        MonthlySalary monthlySalary = new MonthlySalary()
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .status(UPDATED_STATUS)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY);
        return monthlySalary;
    }

    @BeforeEach
    public void initTest() {
        monthlySalary = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonthlySalary() throws Exception {
        int databaseSizeBeforeCreate = monthlySalaryRepository.findAll().size();
        // Create the MonthlySalary
        restMonthlySalaryMockMvc.perform(post("/api/monthly-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalary)))
            .andExpect(status().isCreated());

        // Validate the MonthlySalary in the database
        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeCreate + 1);
        MonthlySalary testMonthlySalary = monthlySalaryList.get(monthlySalaryList.size() - 1);
        assertThat(testMonthlySalary.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testMonthlySalary.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testMonthlySalary.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testMonthlySalary.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testMonthlySalary.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMonthlySalary.getExecutedOn()).isEqualTo(DEFAULT_EXECUTED_ON);
        assertThat(testMonthlySalary.getExecutedBy()).isEqualTo(DEFAULT_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void createMonthlySalaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monthlySalaryRepository.findAll().size();

        // Create the MonthlySalary with an existing ID
        monthlySalary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlySalaryMockMvc.perform(post("/api/monthly-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalary)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlySalary in the database
        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaries() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlySalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)));
    }
    
    @Test
    @Transactional
    public void getMonthlySalary() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get the monthlySalary
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries/{id}", monthlySalary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monthlySalary.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.executedOn").value(DEFAULT_EXECUTED_ON.toString()))
            .andExpect(jsonPath("$.executedBy").value(DEFAULT_EXECUTED_BY));
    }


    @Test
    @Transactional
    public void getMonthlySalariesByIdFiltering() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        Long id = monthlySalary.getId();

        defaultMonthlySalaryShouldBeFound("id.equals=" + id);
        defaultMonthlySalaryShouldNotBeFound("id.notEquals=" + id);

        defaultMonthlySalaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMonthlySalaryShouldNotBeFound("id.greaterThan=" + id);

        defaultMonthlySalaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMonthlySalaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year equals to DEFAULT_YEAR
        defaultMonthlySalaryShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the monthlySalaryList where year equals to UPDATED_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year not equals to DEFAULT_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the monthlySalaryList where year not equals to UPDATED_YEAR
        defaultMonthlySalaryShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultMonthlySalaryShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the monthlySalaryList where year equals to UPDATED_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year is not null
        defaultMonthlySalaryShouldBeFound("year.specified=true");

        // Get all the monthlySalaryList where year is null
        defaultMonthlySalaryShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year is greater than or equal to DEFAULT_YEAR
        defaultMonthlySalaryShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the monthlySalaryList where year is greater than or equal to UPDATED_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year is less than or equal to DEFAULT_YEAR
        defaultMonthlySalaryShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the monthlySalaryList where year is less than or equal to SMALLER_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year is less than DEFAULT_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the monthlySalaryList where year is less than UPDATED_YEAR
        defaultMonthlySalaryShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year is greater than DEFAULT_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the monthlySalaryList where year is greater than SMALLER_YEAR
        defaultMonthlySalaryShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }


    @Test
    @Transactional
    public void getAllMonthlySalariesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where month equals to DEFAULT_MONTH
        defaultMonthlySalaryShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the monthlySalaryList where month equals to UPDATED_MONTH
        defaultMonthlySalaryShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where month not equals to DEFAULT_MONTH
        defaultMonthlySalaryShouldNotBeFound("month.notEquals=" + DEFAULT_MONTH);

        // Get all the monthlySalaryList where month not equals to UPDATED_MONTH
        defaultMonthlySalaryShouldBeFound("month.notEquals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultMonthlySalaryShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the monthlySalaryList where month equals to UPDATED_MONTH
        defaultMonthlySalaryShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where month is not null
        defaultMonthlySalaryShouldBeFound("month.specified=true");

        // Get all the monthlySalaryList where month is null
        defaultMonthlySalaryShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where fromDate equals to DEFAULT_FROM_DATE
        defaultMonthlySalaryShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the monthlySalaryList where fromDate equals to UPDATED_FROM_DATE
        defaultMonthlySalaryShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where fromDate not equals to DEFAULT_FROM_DATE
        defaultMonthlySalaryShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the monthlySalaryList where fromDate not equals to UPDATED_FROM_DATE
        defaultMonthlySalaryShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultMonthlySalaryShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the monthlySalaryList where fromDate equals to UPDATED_FROM_DATE
        defaultMonthlySalaryShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where fromDate is not null
        defaultMonthlySalaryShouldBeFound("fromDate.specified=true");

        // Get all the monthlySalaryList where fromDate is null
        defaultMonthlySalaryShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where toDate equals to DEFAULT_TO_DATE
        defaultMonthlySalaryShouldBeFound("toDate.equals=" + DEFAULT_TO_DATE);

        // Get all the monthlySalaryList where toDate equals to UPDATED_TO_DATE
        defaultMonthlySalaryShouldNotBeFound("toDate.equals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByToDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where toDate not equals to DEFAULT_TO_DATE
        defaultMonthlySalaryShouldNotBeFound("toDate.notEquals=" + DEFAULT_TO_DATE);

        // Get all the monthlySalaryList where toDate not equals to UPDATED_TO_DATE
        defaultMonthlySalaryShouldBeFound("toDate.notEquals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByToDateIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where toDate in DEFAULT_TO_DATE or UPDATED_TO_DATE
        defaultMonthlySalaryShouldBeFound("toDate.in=" + DEFAULT_TO_DATE + "," + UPDATED_TO_DATE);

        // Get all the monthlySalaryList where toDate equals to UPDATED_TO_DATE
        defaultMonthlySalaryShouldNotBeFound("toDate.in=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where toDate is not null
        defaultMonthlySalaryShouldBeFound("toDate.specified=true");

        // Get all the monthlySalaryList where toDate is null
        defaultMonthlySalaryShouldNotBeFound("toDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where status equals to DEFAULT_STATUS
        defaultMonthlySalaryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the monthlySalaryList where status equals to UPDATED_STATUS
        defaultMonthlySalaryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where status not equals to DEFAULT_STATUS
        defaultMonthlySalaryShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the monthlySalaryList where status not equals to UPDATED_STATUS
        defaultMonthlySalaryShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMonthlySalaryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the monthlySalaryList where status equals to UPDATED_STATUS
        defaultMonthlySalaryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where status is not null
        defaultMonthlySalaryShouldBeFound("status.specified=true");

        // Get all the monthlySalaryList where status is null
        defaultMonthlySalaryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByExecutedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where executedOn equals to DEFAULT_EXECUTED_ON
        defaultMonthlySalaryShouldBeFound("executedOn.equals=" + DEFAULT_EXECUTED_ON);

        // Get all the monthlySalaryList where executedOn equals to UPDATED_EXECUTED_ON
        defaultMonthlySalaryShouldNotBeFound("executedOn.equals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByExecutedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where executedOn not equals to DEFAULT_EXECUTED_ON
        defaultMonthlySalaryShouldNotBeFound("executedOn.notEquals=" + DEFAULT_EXECUTED_ON);

        // Get all the monthlySalaryList where executedOn not equals to UPDATED_EXECUTED_ON
        defaultMonthlySalaryShouldBeFound("executedOn.notEquals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByExecutedOnIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where executedOn in DEFAULT_EXECUTED_ON or UPDATED_EXECUTED_ON
        defaultMonthlySalaryShouldBeFound("executedOn.in=" + DEFAULT_EXECUTED_ON + "," + UPDATED_EXECUTED_ON);

        // Get all the monthlySalaryList where executedOn equals to UPDATED_EXECUTED_ON
        defaultMonthlySalaryShouldNotBeFound("executedOn.in=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByExecutedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where executedOn is not null
        defaultMonthlySalaryShouldBeFound("executedOn.specified=true");

        // Get all the monthlySalaryList where executedOn is null
        defaultMonthlySalaryShouldNotBeFound("executedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByExecutedByIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where executedBy equals to DEFAULT_EXECUTED_BY
        defaultMonthlySalaryShouldBeFound("executedBy.equals=" + DEFAULT_EXECUTED_BY);

        // Get all the monthlySalaryList where executedBy equals to UPDATED_EXECUTED_BY
        defaultMonthlySalaryShouldNotBeFound("executedBy.equals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByExecutedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where executedBy not equals to DEFAULT_EXECUTED_BY
        defaultMonthlySalaryShouldNotBeFound("executedBy.notEquals=" + DEFAULT_EXECUTED_BY);

        // Get all the monthlySalaryList where executedBy not equals to UPDATED_EXECUTED_BY
        defaultMonthlySalaryShouldBeFound("executedBy.notEquals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByExecutedByIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where executedBy in DEFAULT_EXECUTED_BY or UPDATED_EXECUTED_BY
        defaultMonthlySalaryShouldBeFound("executedBy.in=" + DEFAULT_EXECUTED_BY + "," + UPDATED_EXECUTED_BY);

        // Get all the monthlySalaryList where executedBy equals to UPDATED_EXECUTED_BY
        defaultMonthlySalaryShouldNotBeFound("executedBy.in=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByExecutedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where executedBy is not null
        defaultMonthlySalaryShouldBeFound("executedBy.specified=true");

        // Get all the monthlySalaryList where executedBy is null
        defaultMonthlySalaryShouldNotBeFound("executedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllMonthlySalariesByExecutedByContainsSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where executedBy contains DEFAULT_EXECUTED_BY
        defaultMonthlySalaryShouldBeFound("executedBy.contains=" + DEFAULT_EXECUTED_BY);

        // Get all the monthlySalaryList where executedBy contains UPDATED_EXECUTED_BY
        defaultMonthlySalaryShouldNotBeFound("executedBy.contains=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByExecutedByNotContainsSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where executedBy does not contain DEFAULT_EXECUTED_BY
        defaultMonthlySalaryShouldNotBeFound("executedBy.doesNotContain=" + DEFAULT_EXECUTED_BY);

        // Get all the monthlySalaryList where executedBy does not contain UPDATED_EXECUTED_BY
        defaultMonthlySalaryShouldBeFound("executedBy.doesNotContain=" + UPDATED_EXECUTED_BY);
    }


    @Test
    @Transactional
    public void getAllMonthlySalariesByMonthlySalaryDtlIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);
        MonthlySalaryDtl monthlySalaryDtl = MonthlySalaryDtlResourceIT.createEntity(em);
        em.persist(monthlySalaryDtl);
        em.flush();
        monthlySalary.addMonthlySalaryDtl(monthlySalaryDtl);
        monthlySalaryRepository.saveAndFlush(monthlySalary);
        Long monthlySalaryDtlId = monthlySalaryDtl.getId();

        // Get all the monthlySalaryList where monthlySalaryDtl equals to monthlySalaryDtlId
        defaultMonthlySalaryShouldBeFound("monthlySalaryDtlId.equals=" + monthlySalaryDtlId);

        // Get all the monthlySalaryList where monthlySalaryDtl equals to monthlySalaryDtlId + 1
        defaultMonthlySalaryShouldNotBeFound("monthlySalaryDtlId.equals=" + (monthlySalaryDtlId + 1));
    }


    @Test
    @Transactional
    public void getAllMonthlySalariesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        monthlySalary.setDepartment(department);
        monthlySalaryRepository.saveAndFlush(monthlySalary);
        Long departmentId = department.getId();

        // Get all the monthlySalaryList where department equals to departmentId
        defaultMonthlySalaryShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the monthlySalaryList where department equals to departmentId + 1
        defaultMonthlySalaryShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMonthlySalaryShouldBeFound(String filter) throws Exception {
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlySalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)));

        // Check, that the count call also returns 1
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMonthlySalaryShouldNotBeFound(String filter) throws Exception {
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMonthlySalary() throws Exception {
        // Get the monthlySalary
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonthlySalary() throws Exception {
        // Initialize the database
        monthlySalaryService.save(monthlySalary);

        int databaseSizeBeforeUpdate = monthlySalaryRepository.findAll().size();

        // Update the monthlySalary
        MonthlySalary updatedMonthlySalary = monthlySalaryRepository.findById(monthlySalary.getId()).get();
        // Disconnect from session so that the updates on updatedMonthlySalary are not directly saved in db
        em.detach(updatedMonthlySalary);
        updatedMonthlySalary
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .status(UPDATED_STATUS)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY);

        restMonthlySalaryMockMvc.perform(put("/api/monthly-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonthlySalary)))
            .andExpect(status().isOk());

        // Validate the MonthlySalary in the database
        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeUpdate);
        MonthlySalary testMonthlySalary = monthlySalaryList.get(monthlySalaryList.size() - 1);
        assertThat(testMonthlySalary.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMonthlySalary.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testMonthlySalary.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testMonthlySalary.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testMonthlySalary.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMonthlySalary.getExecutedOn()).isEqualTo(UPDATED_EXECUTED_ON);
        assertThat(testMonthlySalary.getExecutedBy()).isEqualTo(UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingMonthlySalary() throws Exception {
        int databaseSizeBeforeUpdate = monthlySalaryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlySalaryMockMvc.perform(put("/api/monthly-salaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalary)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlySalary in the database
        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonthlySalary() throws Exception {
        // Initialize the database
        monthlySalaryService.save(monthlySalary);

        int databaseSizeBeforeDelete = monthlySalaryRepository.findAll().size();

        // Delete the monthlySalary
        restMonthlySalaryMockMvc.perform(delete("/api/monthly-salaries/{id}", monthlySalary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
