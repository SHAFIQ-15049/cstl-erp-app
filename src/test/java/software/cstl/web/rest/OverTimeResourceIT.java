package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.OverTime;
import software.cstl.domain.Designation;
import software.cstl.domain.Employee;
import software.cstl.repository.OverTimeRepository;
import software.cstl.service.OverTimeService;
import software.cstl.service.dto.OverTimeCriteria;
import software.cstl.service.OverTimeQueryService;

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
/**
 * Integration tests for the {@link OverTimeResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OverTimeResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final MonthType DEFAULT_MONTH = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH = MonthType.FEBRUARY;

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TOTAL_OVER_TIME = 1D;
    private static final Double UPDATED_TOTAL_OVER_TIME = 2D;
    private static final Double SMALLER_TOTAL_OVER_TIME = 1D - 1D;

    private static final Double DEFAULT_OFFICIAL_OVER_TIME = 1D;
    private static final Double UPDATED_OFFICIAL_OVER_TIME = 2D;
    private static final Double SMALLER_OFFICIAL_OVER_TIME = 1D - 1D;

    private static final Double DEFAULT_EXTRA_OVER_TIME = 1D;
    private static final Double UPDATED_EXTRA_OVER_TIME = 2D;
    private static final Double SMALLER_EXTRA_OVER_TIME = 1D - 1D;

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OFFICIAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_OFFICIAL_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_OFFICIAL_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EXTRA_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTRA_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXTRA_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXECUTED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXECUTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_EXECUTED_BY = "BBBBBBBBBB";

    @Autowired
    private OverTimeRepository overTimeRepository;

    @Autowired
    private OverTimeService overTimeService;

    @Autowired
    private OverTimeQueryService overTimeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverTimeMockMvc;

    private OverTime overTime;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OverTime createEntity(EntityManager em) {
        OverTime overTime = new OverTime()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .totalOverTime(DEFAULT_TOTAL_OVER_TIME)
            .officialOverTime(DEFAULT_OFFICIAL_OVER_TIME)
            .extraOverTime(DEFAULT_EXTRA_OVER_TIME)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .officialAmount(DEFAULT_OFFICIAL_AMOUNT)
            .extraAmount(DEFAULT_EXTRA_AMOUNT)
            .note(DEFAULT_NOTE)
            .executedOn(DEFAULT_EXECUTED_ON)
            .executedBy(DEFAULT_EXECUTED_BY);
        return overTime;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OverTime createUpdatedEntity(EntityManager em) {
        OverTime overTime = new OverTime()
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .totalOverTime(UPDATED_TOTAL_OVER_TIME)
            .officialOverTime(UPDATED_OFFICIAL_OVER_TIME)
            .extraOverTime(UPDATED_EXTRA_OVER_TIME)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .officialAmount(UPDATED_OFFICIAL_AMOUNT)
            .extraAmount(UPDATED_EXTRA_AMOUNT)
            .note(UPDATED_NOTE)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY);
        return overTime;
    }

    @BeforeEach
    public void initTest() {
        overTime = createEntity(em);
    }

    @Test
    @Transactional
    public void createOverTime() throws Exception {
        int databaseSizeBeforeCreate = overTimeRepository.findAll().size();
        // Create the OverTime
        restOverTimeMockMvc.perform(post("/api/over-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(overTime)))
            .andExpect(status().isCreated());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeCreate + 1);
        OverTime testOverTime = overTimeList.get(overTimeList.size() - 1);
        assertThat(testOverTime.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testOverTime.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testOverTime.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testOverTime.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testOverTime.getTotalOverTime()).isEqualTo(DEFAULT_TOTAL_OVER_TIME);
        assertThat(testOverTime.getOfficialOverTime()).isEqualTo(DEFAULT_OFFICIAL_OVER_TIME);
        assertThat(testOverTime.getExtraOverTime()).isEqualTo(DEFAULT_EXTRA_OVER_TIME);
        assertThat(testOverTime.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testOverTime.getOfficialAmount()).isEqualTo(DEFAULT_OFFICIAL_AMOUNT);
        assertThat(testOverTime.getExtraAmount()).isEqualTo(DEFAULT_EXTRA_AMOUNT);
        assertThat(testOverTime.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testOverTime.getExecutedOn()).isEqualTo(DEFAULT_EXECUTED_ON);
        assertThat(testOverTime.getExecutedBy()).isEqualTo(DEFAULT_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void createOverTimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = overTimeRepository.findAll().size();

        // Create the OverTime with an existing ID
        overTime.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverTimeMockMvc.perform(post("/api/over-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(overTime)))
            .andExpect(status().isBadRequest());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOverTimes() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList
        restOverTimeMockMvc.perform(get("/api/over-times?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalOverTime").value(hasItem(DEFAULT_TOTAL_OVER_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].officialOverTime").value(hasItem(DEFAULT_OFFICIAL_OVER_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].extraOverTime").value(hasItem(DEFAULT_EXTRA_OVER_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].officialAmount").value(hasItem(DEFAULT_OFFICIAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].extraAmount").value(hasItem(DEFAULT_EXTRA_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)));
    }
    
    @Test
    @Transactional
    public void getOverTime() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get the overTime
        restOverTimeMockMvc.perform(get("/api/over-times/{id}", overTime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overTime.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.totalOverTime").value(DEFAULT_TOTAL_OVER_TIME.doubleValue()))
            .andExpect(jsonPath("$.officialOverTime").value(DEFAULT_OFFICIAL_OVER_TIME.doubleValue()))
            .andExpect(jsonPath("$.extraOverTime").value(DEFAULT_EXTRA_OVER_TIME.doubleValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.officialAmount").value(DEFAULT_OFFICIAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.extraAmount").value(DEFAULT_EXTRA_AMOUNT.intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.executedOn").value(DEFAULT_EXECUTED_ON.toString()))
            .andExpect(jsonPath("$.executedBy").value(DEFAULT_EXECUTED_BY));
    }


    @Test
    @Transactional
    public void getOverTimesByIdFiltering() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        Long id = overTime.getId();

        defaultOverTimeShouldBeFound("id.equals=" + id);
        defaultOverTimeShouldNotBeFound("id.notEquals=" + id);

        defaultOverTimeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOverTimeShouldNotBeFound("id.greaterThan=" + id);

        defaultOverTimeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOverTimeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOverTimesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where year equals to DEFAULT_YEAR
        defaultOverTimeShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the overTimeList where year equals to UPDATED_YEAR
        defaultOverTimeShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllOverTimesByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where year not equals to DEFAULT_YEAR
        defaultOverTimeShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the overTimeList where year not equals to UPDATED_YEAR
        defaultOverTimeShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllOverTimesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultOverTimeShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the overTimeList where year equals to UPDATED_YEAR
        defaultOverTimeShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllOverTimesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where year is not null
        defaultOverTimeShouldBeFound("year.specified=true");

        // Get all the overTimeList where year is null
        defaultOverTimeShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where year is greater than or equal to DEFAULT_YEAR
        defaultOverTimeShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the overTimeList where year is greater than or equal to UPDATED_YEAR
        defaultOverTimeShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllOverTimesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where year is less than or equal to DEFAULT_YEAR
        defaultOverTimeShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the overTimeList where year is less than or equal to SMALLER_YEAR
        defaultOverTimeShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllOverTimesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where year is less than DEFAULT_YEAR
        defaultOverTimeShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the overTimeList where year is less than UPDATED_YEAR
        defaultOverTimeShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllOverTimesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where year is greater than DEFAULT_YEAR
        defaultOverTimeShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the overTimeList where year is greater than SMALLER_YEAR
        defaultOverTimeShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }


    @Test
    @Transactional
    public void getAllOverTimesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where month equals to DEFAULT_MONTH
        defaultOverTimeShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the overTimeList where month equals to UPDATED_MONTH
        defaultOverTimeShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllOverTimesByMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where month not equals to DEFAULT_MONTH
        defaultOverTimeShouldNotBeFound("month.notEquals=" + DEFAULT_MONTH);

        // Get all the overTimeList where month not equals to UPDATED_MONTH
        defaultOverTimeShouldBeFound("month.notEquals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllOverTimesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultOverTimeShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the overTimeList where month equals to UPDATED_MONTH
        defaultOverTimeShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllOverTimesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where month is not null
        defaultOverTimeShouldBeFound("month.specified=true");

        // Get all the overTimeList where month is null
        defaultOverTimeShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where fromDate equals to DEFAULT_FROM_DATE
        defaultOverTimeShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the overTimeList where fromDate equals to UPDATED_FROM_DATE
        defaultOverTimeShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllOverTimesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where fromDate not equals to DEFAULT_FROM_DATE
        defaultOverTimeShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the overTimeList where fromDate not equals to UPDATED_FROM_DATE
        defaultOverTimeShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllOverTimesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultOverTimeShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the overTimeList where fromDate equals to UPDATED_FROM_DATE
        defaultOverTimeShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllOverTimesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where fromDate is not null
        defaultOverTimeShouldBeFound("fromDate.specified=true");

        // Get all the overTimeList where fromDate is null
        defaultOverTimeShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where toDate equals to DEFAULT_TO_DATE
        defaultOverTimeShouldBeFound("toDate.equals=" + DEFAULT_TO_DATE);

        // Get all the overTimeList where toDate equals to UPDATED_TO_DATE
        defaultOverTimeShouldNotBeFound("toDate.equals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllOverTimesByToDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where toDate not equals to DEFAULT_TO_DATE
        defaultOverTimeShouldNotBeFound("toDate.notEquals=" + DEFAULT_TO_DATE);

        // Get all the overTimeList where toDate not equals to UPDATED_TO_DATE
        defaultOverTimeShouldBeFound("toDate.notEquals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllOverTimesByToDateIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where toDate in DEFAULT_TO_DATE or UPDATED_TO_DATE
        defaultOverTimeShouldBeFound("toDate.in=" + DEFAULT_TO_DATE + "," + UPDATED_TO_DATE);

        // Get all the overTimeList where toDate equals to UPDATED_TO_DATE
        defaultOverTimeShouldNotBeFound("toDate.in=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllOverTimesByToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where toDate is not null
        defaultOverTimeShouldBeFound("toDate.specified=true");

        // Get all the overTimeList where toDate is null
        defaultOverTimeShouldNotBeFound("toDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalOverTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalOverTime equals to DEFAULT_TOTAL_OVER_TIME
        defaultOverTimeShouldBeFound("totalOverTime.equals=" + DEFAULT_TOTAL_OVER_TIME);

        // Get all the overTimeList where totalOverTime equals to UPDATED_TOTAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("totalOverTime.equals=" + UPDATED_TOTAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalOverTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalOverTime not equals to DEFAULT_TOTAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("totalOverTime.notEquals=" + DEFAULT_TOTAL_OVER_TIME);

        // Get all the overTimeList where totalOverTime not equals to UPDATED_TOTAL_OVER_TIME
        defaultOverTimeShouldBeFound("totalOverTime.notEquals=" + UPDATED_TOTAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalOverTimeIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalOverTime in DEFAULT_TOTAL_OVER_TIME or UPDATED_TOTAL_OVER_TIME
        defaultOverTimeShouldBeFound("totalOverTime.in=" + DEFAULT_TOTAL_OVER_TIME + "," + UPDATED_TOTAL_OVER_TIME);

        // Get all the overTimeList where totalOverTime equals to UPDATED_TOTAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("totalOverTime.in=" + UPDATED_TOTAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalOverTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalOverTime is not null
        defaultOverTimeShouldBeFound("totalOverTime.specified=true");

        // Get all the overTimeList where totalOverTime is null
        defaultOverTimeShouldNotBeFound("totalOverTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalOverTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalOverTime is greater than or equal to DEFAULT_TOTAL_OVER_TIME
        defaultOverTimeShouldBeFound("totalOverTime.greaterThanOrEqual=" + DEFAULT_TOTAL_OVER_TIME);

        // Get all the overTimeList where totalOverTime is greater than or equal to UPDATED_TOTAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("totalOverTime.greaterThanOrEqual=" + UPDATED_TOTAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalOverTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalOverTime is less than or equal to DEFAULT_TOTAL_OVER_TIME
        defaultOverTimeShouldBeFound("totalOverTime.lessThanOrEqual=" + DEFAULT_TOTAL_OVER_TIME);

        // Get all the overTimeList where totalOverTime is less than or equal to SMALLER_TOTAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("totalOverTime.lessThanOrEqual=" + SMALLER_TOTAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalOverTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalOverTime is less than DEFAULT_TOTAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("totalOverTime.lessThan=" + DEFAULT_TOTAL_OVER_TIME);

        // Get all the overTimeList where totalOverTime is less than UPDATED_TOTAL_OVER_TIME
        defaultOverTimeShouldBeFound("totalOverTime.lessThan=" + UPDATED_TOTAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalOverTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalOverTime is greater than DEFAULT_TOTAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("totalOverTime.greaterThan=" + DEFAULT_TOTAL_OVER_TIME);

        // Get all the overTimeList where totalOverTime is greater than SMALLER_TOTAL_OVER_TIME
        defaultOverTimeShouldBeFound("totalOverTime.greaterThan=" + SMALLER_TOTAL_OVER_TIME);
    }


    @Test
    @Transactional
    public void getAllOverTimesByOfficialOverTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialOverTime equals to DEFAULT_OFFICIAL_OVER_TIME
        defaultOverTimeShouldBeFound("officialOverTime.equals=" + DEFAULT_OFFICIAL_OVER_TIME);

        // Get all the overTimeList where officialOverTime equals to UPDATED_OFFICIAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("officialOverTime.equals=" + UPDATED_OFFICIAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialOverTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialOverTime not equals to DEFAULT_OFFICIAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("officialOverTime.notEquals=" + DEFAULT_OFFICIAL_OVER_TIME);

        // Get all the overTimeList where officialOverTime not equals to UPDATED_OFFICIAL_OVER_TIME
        defaultOverTimeShouldBeFound("officialOverTime.notEquals=" + UPDATED_OFFICIAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialOverTimeIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialOverTime in DEFAULT_OFFICIAL_OVER_TIME or UPDATED_OFFICIAL_OVER_TIME
        defaultOverTimeShouldBeFound("officialOverTime.in=" + DEFAULT_OFFICIAL_OVER_TIME + "," + UPDATED_OFFICIAL_OVER_TIME);

        // Get all the overTimeList where officialOverTime equals to UPDATED_OFFICIAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("officialOverTime.in=" + UPDATED_OFFICIAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialOverTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialOverTime is not null
        defaultOverTimeShouldBeFound("officialOverTime.specified=true");

        // Get all the overTimeList where officialOverTime is null
        defaultOverTimeShouldNotBeFound("officialOverTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialOverTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialOverTime is greater than or equal to DEFAULT_OFFICIAL_OVER_TIME
        defaultOverTimeShouldBeFound("officialOverTime.greaterThanOrEqual=" + DEFAULT_OFFICIAL_OVER_TIME);

        // Get all the overTimeList where officialOverTime is greater than or equal to UPDATED_OFFICIAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("officialOverTime.greaterThanOrEqual=" + UPDATED_OFFICIAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialOverTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialOverTime is less than or equal to DEFAULT_OFFICIAL_OVER_TIME
        defaultOverTimeShouldBeFound("officialOverTime.lessThanOrEqual=" + DEFAULT_OFFICIAL_OVER_TIME);

        // Get all the overTimeList where officialOverTime is less than or equal to SMALLER_OFFICIAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("officialOverTime.lessThanOrEqual=" + SMALLER_OFFICIAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialOverTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialOverTime is less than DEFAULT_OFFICIAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("officialOverTime.lessThan=" + DEFAULT_OFFICIAL_OVER_TIME);

        // Get all the overTimeList where officialOverTime is less than UPDATED_OFFICIAL_OVER_TIME
        defaultOverTimeShouldBeFound("officialOverTime.lessThan=" + UPDATED_OFFICIAL_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialOverTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialOverTime is greater than DEFAULT_OFFICIAL_OVER_TIME
        defaultOverTimeShouldNotBeFound("officialOverTime.greaterThan=" + DEFAULT_OFFICIAL_OVER_TIME);

        // Get all the overTimeList where officialOverTime is greater than SMALLER_OFFICIAL_OVER_TIME
        defaultOverTimeShouldBeFound("officialOverTime.greaterThan=" + SMALLER_OFFICIAL_OVER_TIME);
    }


    @Test
    @Transactional
    public void getAllOverTimesByExtraOverTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraOverTime equals to DEFAULT_EXTRA_OVER_TIME
        defaultOverTimeShouldBeFound("extraOverTime.equals=" + DEFAULT_EXTRA_OVER_TIME);

        // Get all the overTimeList where extraOverTime equals to UPDATED_EXTRA_OVER_TIME
        defaultOverTimeShouldNotBeFound("extraOverTime.equals=" + UPDATED_EXTRA_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraOverTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraOverTime not equals to DEFAULT_EXTRA_OVER_TIME
        defaultOverTimeShouldNotBeFound("extraOverTime.notEquals=" + DEFAULT_EXTRA_OVER_TIME);

        // Get all the overTimeList where extraOverTime not equals to UPDATED_EXTRA_OVER_TIME
        defaultOverTimeShouldBeFound("extraOverTime.notEquals=" + UPDATED_EXTRA_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraOverTimeIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraOverTime in DEFAULT_EXTRA_OVER_TIME or UPDATED_EXTRA_OVER_TIME
        defaultOverTimeShouldBeFound("extraOverTime.in=" + DEFAULT_EXTRA_OVER_TIME + "," + UPDATED_EXTRA_OVER_TIME);

        // Get all the overTimeList where extraOverTime equals to UPDATED_EXTRA_OVER_TIME
        defaultOverTimeShouldNotBeFound("extraOverTime.in=" + UPDATED_EXTRA_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraOverTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraOverTime is not null
        defaultOverTimeShouldBeFound("extraOverTime.specified=true");

        // Get all the overTimeList where extraOverTime is null
        defaultOverTimeShouldNotBeFound("extraOverTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraOverTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraOverTime is greater than or equal to DEFAULT_EXTRA_OVER_TIME
        defaultOverTimeShouldBeFound("extraOverTime.greaterThanOrEqual=" + DEFAULT_EXTRA_OVER_TIME);

        // Get all the overTimeList where extraOverTime is greater than or equal to UPDATED_EXTRA_OVER_TIME
        defaultOverTimeShouldNotBeFound("extraOverTime.greaterThanOrEqual=" + UPDATED_EXTRA_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraOverTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraOverTime is less than or equal to DEFAULT_EXTRA_OVER_TIME
        defaultOverTimeShouldBeFound("extraOverTime.lessThanOrEqual=" + DEFAULT_EXTRA_OVER_TIME);

        // Get all the overTimeList where extraOverTime is less than or equal to SMALLER_EXTRA_OVER_TIME
        defaultOverTimeShouldNotBeFound("extraOverTime.lessThanOrEqual=" + SMALLER_EXTRA_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraOverTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraOverTime is less than DEFAULT_EXTRA_OVER_TIME
        defaultOverTimeShouldNotBeFound("extraOverTime.lessThan=" + DEFAULT_EXTRA_OVER_TIME);

        // Get all the overTimeList where extraOverTime is less than UPDATED_EXTRA_OVER_TIME
        defaultOverTimeShouldBeFound("extraOverTime.lessThan=" + UPDATED_EXTRA_OVER_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraOverTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraOverTime is greater than DEFAULT_EXTRA_OVER_TIME
        defaultOverTimeShouldNotBeFound("extraOverTime.greaterThan=" + DEFAULT_EXTRA_OVER_TIME);

        // Get all the overTimeList where extraOverTime is greater than SMALLER_EXTRA_OVER_TIME
        defaultOverTimeShouldBeFound("extraOverTime.greaterThan=" + SMALLER_EXTRA_OVER_TIME);
    }


    @Test
    @Transactional
    public void getAllOverTimesByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultOverTimeShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the overTimeList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultOverTimeShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalAmount not equals to DEFAULT_TOTAL_AMOUNT
        defaultOverTimeShouldNotBeFound("totalAmount.notEquals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the overTimeList where totalAmount not equals to UPDATED_TOTAL_AMOUNT
        defaultOverTimeShouldBeFound("totalAmount.notEquals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultOverTimeShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the overTimeList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultOverTimeShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalAmount is not null
        defaultOverTimeShouldBeFound("totalAmount.specified=true");

        // Get all the overTimeList where totalAmount is null
        defaultOverTimeShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalAmount is greater than or equal to DEFAULT_TOTAL_AMOUNT
        defaultOverTimeShouldBeFound("totalAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the overTimeList where totalAmount is greater than or equal to UPDATED_TOTAL_AMOUNT
        defaultOverTimeShouldNotBeFound("totalAmount.greaterThanOrEqual=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalAmount is less than or equal to DEFAULT_TOTAL_AMOUNT
        defaultOverTimeShouldBeFound("totalAmount.lessThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the overTimeList where totalAmount is less than or equal to SMALLER_TOTAL_AMOUNT
        defaultOverTimeShouldNotBeFound("totalAmount.lessThanOrEqual=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalAmount is less than DEFAULT_TOTAL_AMOUNT
        defaultOverTimeShouldNotBeFound("totalAmount.lessThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the overTimeList where totalAmount is less than UPDATED_TOTAL_AMOUNT
        defaultOverTimeShouldBeFound("totalAmount.lessThan=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByTotalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where totalAmount is greater than DEFAULT_TOTAL_AMOUNT
        defaultOverTimeShouldNotBeFound("totalAmount.greaterThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the overTimeList where totalAmount is greater than SMALLER_TOTAL_AMOUNT
        defaultOverTimeShouldBeFound("totalAmount.greaterThan=" + SMALLER_TOTAL_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllOverTimesByOfficialAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialAmount equals to DEFAULT_OFFICIAL_AMOUNT
        defaultOverTimeShouldBeFound("officialAmount.equals=" + DEFAULT_OFFICIAL_AMOUNT);

        // Get all the overTimeList where officialAmount equals to UPDATED_OFFICIAL_AMOUNT
        defaultOverTimeShouldNotBeFound("officialAmount.equals=" + UPDATED_OFFICIAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialAmount not equals to DEFAULT_OFFICIAL_AMOUNT
        defaultOverTimeShouldNotBeFound("officialAmount.notEquals=" + DEFAULT_OFFICIAL_AMOUNT);

        // Get all the overTimeList where officialAmount not equals to UPDATED_OFFICIAL_AMOUNT
        defaultOverTimeShouldBeFound("officialAmount.notEquals=" + UPDATED_OFFICIAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialAmountIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialAmount in DEFAULT_OFFICIAL_AMOUNT or UPDATED_OFFICIAL_AMOUNT
        defaultOverTimeShouldBeFound("officialAmount.in=" + DEFAULT_OFFICIAL_AMOUNT + "," + UPDATED_OFFICIAL_AMOUNT);

        // Get all the overTimeList where officialAmount equals to UPDATED_OFFICIAL_AMOUNT
        defaultOverTimeShouldNotBeFound("officialAmount.in=" + UPDATED_OFFICIAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialAmount is not null
        defaultOverTimeShouldBeFound("officialAmount.specified=true");

        // Get all the overTimeList where officialAmount is null
        defaultOverTimeShouldNotBeFound("officialAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialAmount is greater than or equal to DEFAULT_OFFICIAL_AMOUNT
        defaultOverTimeShouldBeFound("officialAmount.greaterThanOrEqual=" + DEFAULT_OFFICIAL_AMOUNT);

        // Get all the overTimeList where officialAmount is greater than or equal to UPDATED_OFFICIAL_AMOUNT
        defaultOverTimeShouldNotBeFound("officialAmount.greaterThanOrEqual=" + UPDATED_OFFICIAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialAmount is less than or equal to DEFAULT_OFFICIAL_AMOUNT
        defaultOverTimeShouldBeFound("officialAmount.lessThanOrEqual=" + DEFAULT_OFFICIAL_AMOUNT);

        // Get all the overTimeList where officialAmount is less than or equal to SMALLER_OFFICIAL_AMOUNT
        defaultOverTimeShouldNotBeFound("officialAmount.lessThanOrEqual=" + SMALLER_OFFICIAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialAmount is less than DEFAULT_OFFICIAL_AMOUNT
        defaultOverTimeShouldNotBeFound("officialAmount.lessThan=" + DEFAULT_OFFICIAL_AMOUNT);

        // Get all the overTimeList where officialAmount is less than UPDATED_OFFICIAL_AMOUNT
        defaultOverTimeShouldBeFound("officialAmount.lessThan=" + UPDATED_OFFICIAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOfficialAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where officialAmount is greater than DEFAULT_OFFICIAL_AMOUNT
        defaultOverTimeShouldNotBeFound("officialAmount.greaterThan=" + DEFAULT_OFFICIAL_AMOUNT);

        // Get all the overTimeList where officialAmount is greater than SMALLER_OFFICIAL_AMOUNT
        defaultOverTimeShouldBeFound("officialAmount.greaterThan=" + SMALLER_OFFICIAL_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllOverTimesByExtraAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraAmount equals to DEFAULT_EXTRA_AMOUNT
        defaultOverTimeShouldBeFound("extraAmount.equals=" + DEFAULT_EXTRA_AMOUNT);

        // Get all the overTimeList where extraAmount equals to UPDATED_EXTRA_AMOUNT
        defaultOverTimeShouldNotBeFound("extraAmount.equals=" + UPDATED_EXTRA_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraAmount not equals to DEFAULT_EXTRA_AMOUNT
        defaultOverTimeShouldNotBeFound("extraAmount.notEquals=" + DEFAULT_EXTRA_AMOUNT);

        // Get all the overTimeList where extraAmount not equals to UPDATED_EXTRA_AMOUNT
        defaultOverTimeShouldBeFound("extraAmount.notEquals=" + UPDATED_EXTRA_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraAmountIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraAmount in DEFAULT_EXTRA_AMOUNT or UPDATED_EXTRA_AMOUNT
        defaultOverTimeShouldBeFound("extraAmount.in=" + DEFAULT_EXTRA_AMOUNT + "," + UPDATED_EXTRA_AMOUNT);

        // Get all the overTimeList where extraAmount equals to UPDATED_EXTRA_AMOUNT
        defaultOverTimeShouldNotBeFound("extraAmount.in=" + UPDATED_EXTRA_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraAmount is not null
        defaultOverTimeShouldBeFound("extraAmount.specified=true");

        // Get all the overTimeList where extraAmount is null
        defaultOverTimeShouldNotBeFound("extraAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraAmount is greater than or equal to DEFAULT_EXTRA_AMOUNT
        defaultOverTimeShouldBeFound("extraAmount.greaterThanOrEqual=" + DEFAULT_EXTRA_AMOUNT);

        // Get all the overTimeList where extraAmount is greater than or equal to UPDATED_EXTRA_AMOUNT
        defaultOverTimeShouldNotBeFound("extraAmount.greaterThanOrEqual=" + UPDATED_EXTRA_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraAmount is less than or equal to DEFAULT_EXTRA_AMOUNT
        defaultOverTimeShouldBeFound("extraAmount.lessThanOrEqual=" + DEFAULT_EXTRA_AMOUNT);

        // Get all the overTimeList where extraAmount is less than or equal to SMALLER_EXTRA_AMOUNT
        defaultOverTimeShouldNotBeFound("extraAmount.lessThanOrEqual=" + SMALLER_EXTRA_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraAmount is less than DEFAULT_EXTRA_AMOUNT
        defaultOverTimeShouldNotBeFound("extraAmount.lessThan=" + DEFAULT_EXTRA_AMOUNT);

        // Get all the overTimeList where extraAmount is less than UPDATED_EXTRA_AMOUNT
        defaultOverTimeShouldBeFound("extraAmount.lessThan=" + UPDATED_EXTRA_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExtraAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where extraAmount is greater than DEFAULT_EXTRA_AMOUNT
        defaultOverTimeShouldNotBeFound("extraAmount.greaterThan=" + DEFAULT_EXTRA_AMOUNT);

        // Get all the overTimeList where extraAmount is greater than SMALLER_EXTRA_AMOUNT
        defaultOverTimeShouldBeFound("extraAmount.greaterThan=" + SMALLER_EXTRA_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllOverTimesByExecutedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where executedOn equals to DEFAULT_EXECUTED_ON
        defaultOverTimeShouldBeFound("executedOn.equals=" + DEFAULT_EXECUTED_ON);

        // Get all the overTimeList where executedOn equals to UPDATED_EXECUTED_ON
        defaultOverTimeShouldNotBeFound("executedOn.equals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExecutedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where executedOn not equals to DEFAULT_EXECUTED_ON
        defaultOverTimeShouldNotBeFound("executedOn.notEquals=" + DEFAULT_EXECUTED_ON);

        // Get all the overTimeList where executedOn not equals to UPDATED_EXECUTED_ON
        defaultOverTimeShouldBeFound("executedOn.notEquals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExecutedOnIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where executedOn in DEFAULT_EXECUTED_ON or UPDATED_EXECUTED_ON
        defaultOverTimeShouldBeFound("executedOn.in=" + DEFAULT_EXECUTED_ON + "," + UPDATED_EXECUTED_ON);

        // Get all the overTimeList where executedOn equals to UPDATED_EXECUTED_ON
        defaultOverTimeShouldNotBeFound("executedOn.in=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExecutedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where executedOn is not null
        defaultOverTimeShouldBeFound("executedOn.specified=true");

        // Get all the overTimeList where executedOn is null
        defaultOverTimeShouldNotBeFound("executedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByExecutedByIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where executedBy equals to DEFAULT_EXECUTED_BY
        defaultOverTimeShouldBeFound("executedBy.equals=" + DEFAULT_EXECUTED_BY);

        // Get all the overTimeList where executedBy equals to UPDATED_EXECUTED_BY
        defaultOverTimeShouldNotBeFound("executedBy.equals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExecutedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where executedBy not equals to DEFAULT_EXECUTED_BY
        defaultOverTimeShouldNotBeFound("executedBy.notEquals=" + DEFAULT_EXECUTED_BY);

        // Get all the overTimeList where executedBy not equals to UPDATED_EXECUTED_BY
        defaultOverTimeShouldBeFound("executedBy.notEquals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExecutedByIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where executedBy in DEFAULT_EXECUTED_BY or UPDATED_EXECUTED_BY
        defaultOverTimeShouldBeFound("executedBy.in=" + DEFAULT_EXECUTED_BY + "," + UPDATED_EXECUTED_BY);

        // Get all the overTimeList where executedBy equals to UPDATED_EXECUTED_BY
        defaultOverTimeShouldNotBeFound("executedBy.in=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExecutedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where executedBy is not null
        defaultOverTimeShouldBeFound("executedBy.specified=true");

        // Get all the overTimeList where executedBy is null
        defaultOverTimeShouldNotBeFound("executedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllOverTimesByExecutedByContainsSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where executedBy contains DEFAULT_EXECUTED_BY
        defaultOverTimeShouldBeFound("executedBy.contains=" + DEFAULT_EXECUTED_BY);

        // Get all the overTimeList where executedBy contains UPDATED_EXECUTED_BY
        defaultOverTimeShouldNotBeFound("executedBy.contains=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllOverTimesByExecutedByNotContainsSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where executedBy does not contain DEFAULT_EXECUTED_BY
        defaultOverTimeShouldNotBeFound("executedBy.doesNotContain=" + DEFAULT_EXECUTED_BY);

        // Get all the overTimeList where executedBy does not contain UPDATED_EXECUTED_BY
        defaultOverTimeShouldBeFound("executedBy.doesNotContain=" + UPDATED_EXECUTED_BY);
    }


    @Test
    @Transactional
    public void getAllOverTimesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);
        Designation designation = DesignationResourceIT.createEntity(em);
        em.persist(designation);
        em.flush();
        overTime.setDesignation(designation);
        overTimeRepository.saveAndFlush(overTime);
        Long designationId = designation.getId();

        // Get all the overTimeList where designation equals to designationId
        defaultOverTimeShouldBeFound("designationId.equals=" + designationId);

        // Get all the overTimeList where designation equals to designationId + 1
        defaultOverTimeShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }


    @Test
    @Transactional
    public void getAllOverTimesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        overTime.setEmployee(employee);
        overTimeRepository.saveAndFlush(overTime);
        Long employeeId = employee.getId();

        // Get all the overTimeList where employee equals to employeeId
        defaultOverTimeShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the overTimeList where employee equals to employeeId + 1
        defaultOverTimeShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOverTimeShouldBeFound(String filter) throws Exception {
        restOverTimeMockMvc.perform(get("/api/over-times?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalOverTime").value(hasItem(DEFAULT_TOTAL_OVER_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].officialOverTime").value(hasItem(DEFAULT_OFFICIAL_OVER_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].extraOverTime").value(hasItem(DEFAULT_EXTRA_OVER_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].officialAmount").value(hasItem(DEFAULT_OFFICIAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].extraAmount").value(hasItem(DEFAULT_EXTRA_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)));

        // Check, that the count call also returns 1
        restOverTimeMockMvc.perform(get("/api/over-times/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOverTimeShouldNotBeFound(String filter) throws Exception {
        restOverTimeMockMvc.perform(get("/api/over-times?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOverTimeMockMvc.perform(get("/api/over-times/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOverTime() throws Exception {
        // Get the overTime
        restOverTimeMockMvc.perform(get("/api/over-times/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOverTime() throws Exception {
        // Initialize the database
        overTimeService.save(overTime);

        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();

        // Update the overTime
        OverTime updatedOverTime = overTimeRepository.findById(overTime.getId()).get();
        // Disconnect from session so that the updates on updatedOverTime are not directly saved in db
        em.detach(updatedOverTime);
        updatedOverTime
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .totalOverTime(UPDATED_TOTAL_OVER_TIME)
            .officialOverTime(UPDATED_OFFICIAL_OVER_TIME)
            .extraOverTime(UPDATED_EXTRA_OVER_TIME)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .officialAmount(UPDATED_OFFICIAL_AMOUNT)
            .extraAmount(UPDATED_EXTRA_AMOUNT)
            .note(UPDATED_NOTE)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY);

        restOverTimeMockMvc.perform(put("/api/over-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOverTime)))
            .andExpect(status().isOk());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
        OverTime testOverTime = overTimeList.get(overTimeList.size() - 1);
        assertThat(testOverTime.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testOverTime.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testOverTime.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testOverTime.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testOverTime.getTotalOverTime()).isEqualTo(UPDATED_TOTAL_OVER_TIME);
        assertThat(testOverTime.getOfficialOverTime()).isEqualTo(UPDATED_OFFICIAL_OVER_TIME);
        assertThat(testOverTime.getExtraOverTime()).isEqualTo(UPDATED_EXTRA_OVER_TIME);
        assertThat(testOverTime.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testOverTime.getOfficialAmount()).isEqualTo(UPDATED_OFFICIAL_AMOUNT);
        assertThat(testOverTime.getExtraAmount()).isEqualTo(UPDATED_EXTRA_AMOUNT);
        assertThat(testOverTime.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testOverTime.getExecutedOn()).isEqualTo(UPDATED_EXECUTED_ON);
        assertThat(testOverTime.getExecutedBy()).isEqualTo(UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingOverTime() throws Exception {
        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverTimeMockMvc.perform(put("/api/over-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(overTime)))
            .andExpect(status().isBadRequest());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOverTime() throws Exception {
        // Initialize the database
        overTimeService.save(overTime);

        int databaseSizeBeforeDelete = overTimeRepository.findAll().size();

        // Delete the overTime
        restOverTimeMockMvc.perform(delete("/api/over-times/{id}", overTime.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
