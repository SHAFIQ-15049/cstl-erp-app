package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.FestivalAllowancePayment;
import software.cstl.domain.FestivalAllowancePaymentDtl;
import software.cstl.domain.Designation;
import software.cstl.repository.FestivalAllowancePaymentRepository;
import software.cstl.service.FestivalAllowancePaymentService;
import software.cstl.service.dto.FestivalAllowancePaymentCriteria;
import software.cstl.service.FestivalAllowancePaymentQueryService;

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
 * Integration tests for the {@link FestivalAllowancePaymentResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FestivalAllowancePaymentResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final MonthType DEFAULT_MONTH = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH = MonthType.FEBRUARY;

    private static final SalaryExecutionStatus DEFAULT_STATUS = SalaryExecutionStatus.DONE;
    private static final SalaryExecutionStatus UPDATED_STATUS = SalaryExecutionStatus.NOT_DONE;

    private static final Instant DEFAULT_EXECUTED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXECUTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_EXECUTED_BY = "BBBBBBBBBB";

    @Autowired
    private FestivalAllowancePaymentRepository festivalAllowancePaymentRepository;

    @Autowired
    private FestivalAllowancePaymentService festivalAllowancePaymentService;

    @Autowired
    private FestivalAllowancePaymentQueryService festivalAllowancePaymentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFestivalAllowancePaymentMockMvc;

    private FestivalAllowancePayment festivalAllowancePayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FestivalAllowancePayment createEntity(EntityManager em) {
        FestivalAllowancePayment festivalAllowancePayment = new FestivalAllowancePayment()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .status(DEFAULT_STATUS)
            .executedOn(DEFAULT_EXECUTED_ON)
            .executedBy(DEFAULT_EXECUTED_BY);
        return festivalAllowancePayment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FestivalAllowancePayment createUpdatedEntity(EntityManager em) {
        FestivalAllowancePayment festivalAllowancePayment = new FestivalAllowancePayment()
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .status(UPDATED_STATUS)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY);
        return festivalAllowancePayment;
    }

    @BeforeEach
    public void initTest() {
        festivalAllowancePayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createFestivalAllowancePayment() throws Exception {
        int databaseSizeBeforeCreate = festivalAllowancePaymentRepository.findAll().size();
        // Create the FestivalAllowancePayment
        restFestivalAllowancePaymentMockMvc.perform(post("/api/festival-allowance-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(festivalAllowancePayment)))
            .andExpect(status().isCreated());

        // Validate the FestivalAllowancePayment in the database
        List<FestivalAllowancePayment> festivalAllowancePaymentList = festivalAllowancePaymentRepository.findAll();
        assertThat(festivalAllowancePaymentList).hasSize(databaseSizeBeforeCreate + 1);
        FestivalAllowancePayment testFestivalAllowancePayment = festivalAllowancePaymentList.get(festivalAllowancePaymentList.size() - 1);
        assertThat(testFestivalAllowancePayment.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testFestivalAllowancePayment.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testFestivalAllowancePayment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFestivalAllowancePayment.getExecutedOn()).isEqualTo(DEFAULT_EXECUTED_ON);
        assertThat(testFestivalAllowancePayment.getExecutedBy()).isEqualTo(DEFAULT_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void createFestivalAllowancePaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = festivalAllowancePaymentRepository.findAll().size();

        // Create the FestivalAllowancePayment with an existing ID
        festivalAllowancePayment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFestivalAllowancePaymentMockMvc.perform(post("/api/festival-allowance-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(festivalAllowancePayment)))
            .andExpect(status().isBadRequest());

        // Validate the FestivalAllowancePayment in the database
        List<FestivalAllowancePayment> festivalAllowancePaymentList = festivalAllowancePaymentRepository.findAll();
        assertThat(festivalAllowancePaymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowancePayments() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList
        restFestivalAllowancePaymentMockMvc.perform(get("/api/festival-allowance-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(festivalAllowancePayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)));
    }
    
    @Test
    @Transactional
    public void getFestivalAllowancePayment() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get the festivalAllowancePayment
        restFestivalAllowancePaymentMockMvc.perform(get("/api/festival-allowance-payments/{id}", festivalAllowancePayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(festivalAllowancePayment.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.executedOn").value(DEFAULT_EXECUTED_ON.toString()))
            .andExpect(jsonPath("$.executedBy").value(DEFAULT_EXECUTED_BY));
    }


    @Test
    @Transactional
    public void getFestivalAllowancePaymentsByIdFiltering() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        Long id = festivalAllowancePayment.getId();

        defaultFestivalAllowancePaymentShouldBeFound("id.equals=" + id);
        defaultFestivalAllowancePaymentShouldNotBeFound("id.notEquals=" + id);

        defaultFestivalAllowancePaymentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFestivalAllowancePaymentShouldNotBeFound("id.greaterThan=" + id);

        defaultFestivalAllowancePaymentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFestivalAllowancePaymentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where year equals to DEFAULT_YEAR
        defaultFestivalAllowancePaymentShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the festivalAllowancePaymentList where year equals to UPDATED_YEAR
        defaultFestivalAllowancePaymentShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where year not equals to DEFAULT_YEAR
        defaultFestivalAllowancePaymentShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the festivalAllowancePaymentList where year not equals to UPDATED_YEAR
        defaultFestivalAllowancePaymentShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultFestivalAllowancePaymentShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the festivalAllowancePaymentList where year equals to UPDATED_YEAR
        defaultFestivalAllowancePaymentShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where year is not null
        defaultFestivalAllowancePaymentShouldBeFound("year.specified=true");

        // Get all the festivalAllowancePaymentList where year is null
        defaultFestivalAllowancePaymentShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where year is greater than or equal to DEFAULT_YEAR
        defaultFestivalAllowancePaymentShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the festivalAllowancePaymentList where year is greater than or equal to UPDATED_YEAR
        defaultFestivalAllowancePaymentShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where year is less than or equal to DEFAULT_YEAR
        defaultFestivalAllowancePaymentShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the festivalAllowancePaymentList where year is less than or equal to SMALLER_YEAR
        defaultFestivalAllowancePaymentShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where year is less than DEFAULT_YEAR
        defaultFestivalAllowancePaymentShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the festivalAllowancePaymentList where year is less than UPDATED_YEAR
        defaultFestivalAllowancePaymentShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where year is greater than DEFAULT_YEAR
        defaultFestivalAllowancePaymentShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the festivalAllowancePaymentList where year is greater than SMALLER_YEAR
        defaultFestivalAllowancePaymentShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where month equals to DEFAULT_MONTH
        defaultFestivalAllowancePaymentShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the festivalAllowancePaymentList where month equals to UPDATED_MONTH
        defaultFestivalAllowancePaymentShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where month not equals to DEFAULT_MONTH
        defaultFestivalAllowancePaymentShouldNotBeFound("month.notEquals=" + DEFAULT_MONTH);

        // Get all the festivalAllowancePaymentList where month not equals to UPDATED_MONTH
        defaultFestivalAllowancePaymentShouldBeFound("month.notEquals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultFestivalAllowancePaymentShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the festivalAllowancePaymentList where month equals to UPDATED_MONTH
        defaultFestivalAllowancePaymentShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where month is not null
        defaultFestivalAllowancePaymentShouldBeFound("month.specified=true");

        // Get all the festivalAllowancePaymentList where month is null
        defaultFestivalAllowancePaymentShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where status equals to DEFAULT_STATUS
        defaultFestivalAllowancePaymentShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the festivalAllowancePaymentList where status equals to UPDATED_STATUS
        defaultFestivalAllowancePaymentShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where status not equals to DEFAULT_STATUS
        defaultFestivalAllowancePaymentShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the festivalAllowancePaymentList where status not equals to UPDATED_STATUS
        defaultFestivalAllowancePaymentShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultFestivalAllowancePaymentShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the festivalAllowancePaymentList where status equals to UPDATED_STATUS
        defaultFestivalAllowancePaymentShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where status is not null
        defaultFestivalAllowancePaymentShouldBeFound("status.specified=true");

        // Get all the festivalAllowancePaymentList where status is null
        defaultFestivalAllowancePaymentShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByExecutedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where executedOn equals to DEFAULT_EXECUTED_ON
        defaultFestivalAllowancePaymentShouldBeFound("executedOn.equals=" + DEFAULT_EXECUTED_ON);

        // Get all the festivalAllowancePaymentList where executedOn equals to UPDATED_EXECUTED_ON
        defaultFestivalAllowancePaymentShouldNotBeFound("executedOn.equals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByExecutedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where executedOn not equals to DEFAULT_EXECUTED_ON
        defaultFestivalAllowancePaymentShouldNotBeFound("executedOn.notEquals=" + DEFAULT_EXECUTED_ON);

        // Get all the festivalAllowancePaymentList where executedOn not equals to UPDATED_EXECUTED_ON
        defaultFestivalAllowancePaymentShouldBeFound("executedOn.notEquals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByExecutedOnIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where executedOn in DEFAULT_EXECUTED_ON or UPDATED_EXECUTED_ON
        defaultFestivalAllowancePaymentShouldBeFound("executedOn.in=" + DEFAULT_EXECUTED_ON + "," + UPDATED_EXECUTED_ON);

        // Get all the festivalAllowancePaymentList where executedOn equals to UPDATED_EXECUTED_ON
        defaultFestivalAllowancePaymentShouldNotBeFound("executedOn.in=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByExecutedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where executedOn is not null
        defaultFestivalAllowancePaymentShouldBeFound("executedOn.specified=true");

        // Get all the festivalAllowancePaymentList where executedOn is null
        defaultFestivalAllowancePaymentShouldNotBeFound("executedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByExecutedByIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where executedBy equals to DEFAULT_EXECUTED_BY
        defaultFestivalAllowancePaymentShouldBeFound("executedBy.equals=" + DEFAULT_EXECUTED_BY);

        // Get all the festivalAllowancePaymentList where executedBy equals to UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentShouldNotBeFound("executedBy.equals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByExecutedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where executedBy not equals to DEFAULT_EXECUTED_BY
        defaultFestivalAllowancePaymentShouldNotBeFound("executedBy.notEquals=" + DEFAULT_EXECUTED_BY);

        // Get all the festivalAllowancePaymentList where executedBy not equals to UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentShouldBeFound("executedBy.notEquals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByExecutedByIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where executedBy in DEFAULT_EXECUTED_BY or UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentShouldBeFound("executedBy.in=" + DEFAULT_EXECUTED_BY + "," + UPDATED_EXECUTED_BY);

        // Get all the festivalAllowancePaymentList where executedBy equals to UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentShouldNotBeFound("executedBy.in=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByExecutedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where executedBy is not null
        defaultFestivalAllowancePaymentShouldBeFound("executedBy.specified=true");

        // Get all the festivalAllowancePaymentList where executedBy is null
        defaultFestivalAllowancePaymentShouldNotBeFound("executedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByExecutedByContainsSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where executedBy contains DEFAULT_EXECUTED_BY
        defaultFestivalAllowancePaymentShouldBeFound("executedBy.contains=" + DEFAULT_EXECUTED_BY);

        // Get all the festivalAllowancePaymentList where executedBy contains UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentShouldNotBeFound("executedBy.contains=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByExecutedByNotContainsSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);

        // Get all the festivalAllowancePaymentList where executedBy does not contain DEFAULT_EXECUTED_BY
        defaultFestivalAllowancePaymentShouldNotBeFound("executedBy.doesNotContain=" + DEFAULT_EXECUTED_BY);

        // Get all the festivalAllowancePaymentList where executedBy does not contain UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentShouldBeFound("executedBy.doesNotContain=" + UPDATED_EXECUTED_BY);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByFestivalAllowancePaymentDtlIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);
        FestivalAllowancePaymentDtl festivalAllowancePaymentDtl = FestivalAllowancePaymentDtlResourceIT.createEntity(em);
        em.persist(festivalAllowancePaymentDtl);
        em.flush();
        festivalAllowancePayment.addFestivalAllowancePaymentDtl(festivalAllowancePaymentDtl);
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);
        Long festivalAllowancePaymentDtlId = festivalAllowancePaymentDtl.getId();

        // Get all the festivalAllowancePaymentList where festivalAllowancePaymentDtl equals to festivalAllowancePaymentDtlId
        defaultFestivalAllowancePaymentShouldBeFound("festivalAllowancePaymentDtlId.equals=" + festivalAllowancePaymentDtlId);

        // Get all the festivalAllowancePaymentList where festivalAllowancePaymentDtl equals to festivalAllowancePaymentDtlId + 1
        defaultFestivalAllowancePaymentShouldNotBeFound("festivalAllowancePaymentDtlId.equals=" + (festivalAllowancePaymentDtlId + 1));
    }


    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);
        Designation designation = DesignationResourceIT.createEntity(em);
        em.persist(designation);
        em.flush();
        festivalAllowancePayment.setDesignation(designation);
        festivalAllowancePaymentRepository.saveAndFlush(festivalAllowancePayment);
        Long designationId = designation.getId();

        // Get all the festivalAllowancePaymentList where designation equals to designationId
        defaultFestivalAllowancePaymentShouldBeFound("designationId.equals=" + designationId);

        // Get all the festivalAllowancePaymentList where designation equals to designationId + 1
        defaultFestivalAllowancePaymentShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFestivalAllowancePaymentShouldBeFound(String filter) throws Exception {
        restFestivalAllowancePaymentMockMvc.perform(get("/api/festival-allowance-payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(festivalAllowancePayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)));

        // Check, that the count call also returns 1
        restFestivalAllowancePaymentMockMvc.perform(get("/api/festival-allowance-payments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFestivalAllowancePaymentShouldNotBeFound(String filter) throws Exception {
        restFestivalAllowancePaymentMockMvc.perform(get("/api/festival-allowance-payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFestivalAllowancePaymentMockMvc.perform(get("/api/festival-allowance-payments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFestivalAllowancePayment() throws Exception {
        // Get the festivalAllowancePayment
        restFestivalAllowancePaymentMockMvc.perform(get("/api/festival-allowance-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFestivalAllowancePayment() throws Exception {
        // Initialize the database
        festivalAllowancePaymentService.save(festivalAllowancePayment);

        int databaseSizeBeforeUpdate = festivalAllowancePaymentRepository.findAll().size();

        // Update the festivalAllowancePayment
        FestivalAllowancePayment updatedFestivalAllowancePayment = festivalAllowancePaymentRepository.findById(festivalAllowancePayment.getId()).get();
        // Disconnect from session so that the updates on updatedFestivalAllowancePayment are not directly saved in db
        em.detach(updatedFestivalAllowancePayment);
        updatedFestivalAllowancePayment
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .status(UPDATED_STATUS)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY);

        restFestivalAllowancePaymentMockMvc.perform(put("/api/festival-allowance-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFestivalAllowancePayment)))
            .andExpect(status().isOk());

        // Validate the FestivalAllowancePayment in the database
        List<FestivalAllowancePayment> festivalAllowancePaymentList = festivalAllowancePaymentRepository.findAll();
        assertThat(festivalAllowancePaymentList).hasSize(databaseSizeBeforeUpdate);
        FestivalAllowancePayment testFestivalAllowancePayment = festivalAllowancePaymentList.get(festivalAllowancePaymentList.size() - 1);
        assertThat(testFestivalAllowancePayment.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testFestivalAllowancePayment.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testFestivalAllowancePayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFestivalAllowancePayment.getExecutedOn()).isEqualTo(UPDATED_EXECUTED_ON);
        assertThat(testFestivalAllowancePayment.getExecutedBy()).isEqualTo(UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingFestivalAllowancePayment() throws Exception {
        int databaseSizeBeforeUpdate = festivalAllowancePaymentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFestivalAllowancePaymentMockMvc.perform(put("/api/festival-allowance-payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(festivalAllowancePayment)))
            .andExpect(status().isBadRequest());

        // Validate the FestivalAllowancePayment in the database
        List<FestivalAllowancePayment> festivalAllowancePaymentList = festivalAllowancePaymentRepository.findAll();
        assertThat(festivalAllowancePaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFestivalAllowancePayment() throws Exception {
        // Initialize the database
        festivalAllowancePaymentService.save(festivalAllowancePayment);

        int databaseSizeBeforeDelete = festivalAllowancePaymentRepository.findAll().size();

        // Delete the festivalAllowancePayment
        restFestivalAllowancePaymentMockMvc.perform(delete("/api/festival-allowance-payments/{id}", festivalAllowancePayment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FestivalAllowancePayment> festivalAllowancePaymentList = festivalAllowancePaymentRepository.findAll();
        assertThat(festivalAllowancePaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
