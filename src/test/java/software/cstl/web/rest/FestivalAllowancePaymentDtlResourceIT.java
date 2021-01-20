package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.FestivalAllowancePaymentDtl;
import software.cstl.domain.Employee;
import software.cstl.domain.FestivalAllowancePayment;
import software.cstl.repository.FestivalAllowancePaymentDtlRepository;
import software.cstl.service.FestivalAllowancePaymentDtlService;
import software.cstl.service.dto.FestivalAllowancePaymentDtlCriteria;
import software.cstl.service.FestivalAllowancePaymentDtlQueryService;

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

import software.cstl.domain.enumeration.SalaryExecutionStatus;
/**
 * Integration tests for the {@link FestivalAllowancePaymentDtlResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FestivalAllowancePaymentDtlResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final SalaryExecutionStatus DEFAULT_STATUS = SalaryExecutionStatus.DONE;
    private static final SalaryExecutionStatus UPDATED_STATUS = SalaryExecutionStatus.NOT_DONE;

    private static final Instant DEFAULT_EXECUTED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXECUTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_EXECUTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private FestivalAllowancePaymentDtlRepository festivalAllowancePaymentDtlRepository;

    @Autowired
    private FestivalAllowancePaymentDtlService festivalAllowancePaymentDtlService;

    @Autowired
    private FestivalAllowancePaymentDtlQueryService festivalAllowancePaymentDtlQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFestivalAllowancePaymentDtlMockMvc;

    private FestivalAllowancePaymentDtl festivalAllowancePaymentDtl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FestivalAllowancePaymentDtl createEntity(EntityManager em) {
        FestivalAllowancePaymentDtl festivalAllowancePaymentDtl = new FestivalAllowancePaymentDtl()
            .amount(DEFAULT_AMOUNT)
            .status(DEFAULT_STATUS)
            .executedOn(DEFAULT_EXECUTED_ON)
            .executedBy(DEFAULT_EXECUTED_BY)
            .note(DEFAULT_NOTE);
        return festivalAllowancePaymentDtl;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FestivalAllowancePaymentDtl createUpdatedEntity(EntityManager em) {
        FestivalAllowancePaymentDtl festivalAllowancePaymentDtl = new FestivalAllowancePaymentDtl()
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY)
            .note(UPDATED_NOTE);
        return festivalAllowancePaymentDtl;
    }

    @BeforeEach
    public void initTest() {
        festivalAllowancePaymentDtl = createEntity(em);
    }

    @Test
    @Transactional
    public void createFestivalAllowancePaymentDtl() throws Exception {
        int databaseSizeBeforeCreate = festivalAllowancePaymentDtlRepository.findAll().size();
        // Create the FestivalAllowancePaymentDtl
        restFestivalAllowancePaymentDtlMockMvc.perform(post("/api/festival-allowance-payment-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(festivalAllowancePaymentDtl)))
            .andExpect(status().isCreated());

        // Validate the FestivalAllowancePaymentDtl in the database
        List<FestivalAllowancePaymentDtl> festivalAllowancePaymentDtlList = festivalAllowancePaymentDtlRepository.findAll();
        assertThat(festivalAllowancePaymentDtlList).hasSize(databaseSizeBeforeCreate + 1);
        FestivalAllowancePaymentDtl testFestivalAllowancePaymentDtl = festivalAllowancePaymentDtlList.get(festivalAllowancePaymentDtlList.size() - 1);
        assertThat(testFestivalAllowancePaymentDtl.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testFestivalAllowancePaymentDtl.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFestivalAllowancePaymentDtl.getExecutedOn()).isEqualTo(DEFAULT_EXECUTED_ON);
        assertThat(testFestivalAllowancePaymentDtl.getExecutedBy()).isEqualTo(DEFAULT_EXECUTED_BY);
        assertThat(testFestivalAllowancePaymentDtl.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createFestivalAllowancePaymentDtlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = festivalAllowancePaymentDtlRepository.findAll().size();

        // Create the FestivalAllowancePaymentDtl with an existing ID
        festivalAllowancePaymentDtl.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFestivalAllowancePaymentDtlMockMvc.perform(post("/api/festival-allowance-payment-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(festivalAllowancePaymentDtl)))
            .andExpect(status().isBadRequest());

        // Validate the FestivalAllowancePaymentDtl in the database
        List<FestivalAllowancePaymentDtl> festivalAllowancePaymentDtlList = festivalAllowancePaymentDtlRepository.findAll();
        assertThat(festivalAllowancePaymentDtlList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtls() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList
        restFestivalAllowancePaymentDtlMockMvc.perform(get("/api/festival-allowance-payment-dtls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(festivalAllowancePaymentDtl.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getFestivalAllowancePaymentDtl() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get the festivalAllowancePaymentDtl
        restFestivalAllowancePaymentDtlMockMvc.perform(get("/api/festival-allowance-payment-dtls/{id}", festivalAllowancePaymentDtl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(festivalAllowancePaymentDtl.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.executedOn").value(DEFAULT_EXECUTED_ON.toString()))
            .andExpect(jsonPath("$.executedBy").value(DEFAULT_EXECUTED_BY))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }


    @Test
    @Transactional
    public void getFestivalAllowancePaymentDtlsByIdFiltering() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        Long id = festivalAllowancePaymentDtl.getId();

        defaultFestivalAllowancePaymentDtlShouldBeFound("id.equals=" + id);
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("id.notEquals=" + id);

        defaultFestivalAllowancePaymentDtlShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("id.greaterThan=" + id);

        defaultFestivalAllowancePaymentDtlShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where amount equals to DEFAULT_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the festivalAllowancePaymentDtlList where amount equals to UPDATED_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where amount not equals to DEFAULT_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the festivalAllowancePaymentDtlList where amount not equals to UPDATED_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the festivalAllowancePaymentDtlList where amount equals to UPDATED_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where amount is not null
        defaultFestivalAllowancePaymentDtlShouldBeFound("amount.specified=true");

        // Get all the festivalAllowancePaymentDtlList where amount is null
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the festivalAllowancePaymentDtlList where amount is greater than or equal to UPDATED_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where amount is less than or equal to DEFAULT_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the festivalAllowancePaymentDtlList where amount is less than or equal to SMALLER_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where amount is less than DEFAULT_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the festivalAllowancePaymentDtlList where amount is less than UPDATED_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where amount is greater than DEFAULT_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the festivalAllowancePaymentDtlList where amount is greater than SMALLER_AMOUNT
        defaultFestivalAllowancePaymentDtlShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where status equals to DEFAULT_STATUS
        defaultFestivalAllowancePaymentDtlShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the festivalAllowancePaymentDtlList where status equals to UPDATED_STATUS
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where status not equals to DEFAULT_STATUS
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the festivalAllowancePaymentDtlList where status not equals to UPDATED_STATUS
        defaultFestivalAllowancePaymentDtlShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultFestivalAllowancePaymentDtlShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the festivalAllowancePaymentDtlList where status equals to UPDATED_STATUS
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where status is not null
        defaultFestivalAllowancePaymentDtlShouldBeFound("status.specified=true");

        // Get all the festivalAllowancePaymentDtlList where status is null
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByExecutedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where executedOn equals to DEFAULT_EXECUTED_ON
        defaultFestivalAllowancePaymentDtlShouldBeFound("executedOn.equals=" + DEFAULT_EXECUTED_ON);

        // Get all the festivalAllowancePaymentDtlList where executedOn equals to UPDATED_EXECUTED_ON
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("executedOn.equals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByExecutedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where executedOn not equals to DEFAULT_EXECUTED_ON
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("executedOn.notEquals=" + DEFAULT_EXECUTED_ON);

        // Get all the festivalAllowancePaymentDtlList where executedOn not equals to UPDATED_EXECUTED_ON
        defaultFestivalAllowancePaymentDtlShouldBeFound("executedOn.notEquals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByExecutedOnIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where executedOn in DEFAULT_EXECUTED_ON or UPDATED_EXECUTED_ON
        defaultFestivalAllowancePaymentDtlShouldBeFound("executedOn.in=" + DEFAULT_EXECUTED_ON + "," + UPDATED_EXECUTED_ON);

        // Get all the festivalAllowancePaymentDtlList where executedOn equals to UPDATED_EXECUTED_ON
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("executedOn.in=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByExecutedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where executedOn is not null
        defaultFestivalAllowancePaymentDtlShouldBeFound("executedOn.specified=true");

        // Get all the festivalAllowancePaymentDtlList where executedOn is null
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("executedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByExecutedByIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where executedBy equals to DEFAULT_EXECUTED_BY
        defaultFestivalAllowancePaymentDtlShouldBeFound("executedBy.equals=" + DEFAULT_EXECUTED_BY);

        // Get all the festivalAllowancePaymentDtlList where executedBy equals to UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("executedBy.equals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByExecutedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where executedBy not equals to DEFAULT_EXECUTED_BY
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("executedBy.notEquals=" + DEFAULT_EXECUTED_BY);

        // Get all the festivalAllowancePaymentDtlList where executedBy not equals to UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentDtlShouldBeFound("executedBy.notEquals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByExecutedByIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where executedBy in DEFAULT_EXECUTED_BY or UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentDtlShouldBeFound("executedBy.in=" + DEFAULT_EXECUTED_BY + "," + UPDATED_EXECUTED_BY);

        // Get all the festivalAllowancePaymentDtlList where executedBy equals to UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("executedBy.in=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByExecutedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where executedBy is not null
        defaultFestivalAllowancePaymentDtlShouldBeFound("executedBy.specified=true");

        // Get all the festivalAllowancePaymentDtlList where executedBy is null
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("executedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByExecutedByContainsSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where executedBy contains DEFAULT_EXECUTED_BY
        defaultFestivalAllowancePaymentDtlShouldBeFound("executedBy.contains=" + DEFAULT_EXECUTED_BY);

        // Get all the festivalAllowancePaymentDtlList where executedBy contains UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("executedBy.contains=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByExecutedByNotContainsSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);

        // Get all the festivalAllowancePaymentDtlList where executedBy does not contain DEFAULT_EXECUTED_BY
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("executedBy.doesNotContain=" + DEFAULT_EXECUTED_BY);

        // Get all the festivalAllowancePaymentDtlList where executedBy does not contain UPDATED_EXECUTED_BY
        defaultFestivalAllowancePaymentDtlShouldBeFound("executedBy.doesNotContain=" + UPDATED_EXECUTED_BY);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        festivalAllowancePaymentDtl.setEmployee(employee);
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);
        Long employeeId = employee.getId();

        // Get all the festivalAllowancePaymentDtlList where employee equals to employeeId
        defaultFestivalAllowancePaymentDtlShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the festivalAllowancePaymentDtlList where employee equals to employeeId + 1
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllFestivalAllowancePaymentDtlsByFestivalAllowancePaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);
        FestivalAllowancePayment festivalAllowancePayment = FestivalAllowancePaymentResourceIT.createEntity(em);
        em.persist(festivalAllowancePayment);
        em.flush();
        festivalAllowancePaymentDtl.setFestivalAllowancePayment(festivalAllowancePayment);
        festivalAllowancePaymentDtlRepository.saveAndFlush(festivalAllowancePaymentDtl);
        Long festivalAllowancePaymentId = festivalAllowancePayment.getId();

        // Get all the festivalAllowancePaymentDtlList where festivalAllowancePayment equals to festivalAllowancePaymentId
        defaultFestivalAllowancePaymentDtlShouldBeFound("festivalAllowancePaymentId.equals=" + festivalAllowancePaymentId);

        // Get all the festivalAllowancePaymentDtlList where festivalAllowancePayment equals to festivalAllowancePaymentId + 1
        defaultFestivalAllowancePaymentDtlShouldNotBeFound("festivalAllowancePaymentId.equals=" + (festivalAllowancePaymentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFestivalAllowancePaymentDtlShouldBeFound(String filter) throws Exception {
        restFestivalAllowancePaymentDtlMockMvc.perform(get("/api/festival-allowance-payment-dtls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(festivalAllowancePaymentDtl.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));

        // Check, that the count call also returns 1
        restFestivalAllowancePaymentDtlMockMvc.perform(get("/api/festival-allowance-payment-dtls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFestivalAllowancePaymentDtlShouldNotBeFound(String filter) throws Exception {
        restFestivalAllowancePaymentDtlMockMvc.perform(get("/api/festival-allowance-payment-dtls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFestivalAllowancePaymentDtlMockMvc.perform(get("/api/festival-allowance-payment-dtls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFestivalAllowancePaymentDtl() throws Exception {
        // Get the festivalAllowancePaymentDtl
        restFestivalAllowancePaymentDtlMockMvc.perform(get("/api/festival-allowance-payment-dtls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFestivalAllowancePaymentDtl() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlService.save(festivalAllowancePaymentDtl);

        int databaseSizeBeforeUpdate = festivalAllowancePaymentDtlRepository.findAll().size();

        // Update the festivalAllowancePaymentDtl
        FestivalAllowancePaymentDtl updatedFestivalAllowancePaymentDtl = festivalAllowancePaymentDtlRepository.findById(festivalAllowancePaymentDtl.getId()).get();
        // Disconnect from session so that the updates on updatedFestivalAllowancePaymentDtl are not directly saved in db
        em.detach(updatedFestivalAllowancePaymentDtl);
        updatedFestivalAllowancePaymentDtl
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY)
            .note(UPDATED_NOTE);

        restFestivalAllowancePaymentDtlMockMvc.perform(put("/api/festival-allowance-payment-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFestivalAllowancePaymentDtl)))
            .andExpect(status().isOk());

        // Validate the FestivalAllowancePaymentDtl in the database
        List<FestivalAllowancePaymentDtl> festivalAllowancePaymentDtlList = festivalAllowancePaymentDtlRepository.findAll();
        assertThat(festivalAllowancePaymentDtlList).hasSize(databaseSizeBeforeUpdate);
        FestivalAllowancePaymentDtl testFestivalAllowancePaymentDtl = festivalAllowancePaymentDtlList.get(festivalAllowancePaymentDtlList.size() - 1);
        assertThat(testFestivalAllowancePaymentDtl.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFestivalAllowancePaymentDtl.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFestivalAllowancePaymentDtl.getExecutedOn()).isEqualTo(UPDATED_EXECUTED_ON);
        assertThat(testFestivalAllowancePaymentDtl.getExecutedBy()).isEqualTo(UPDATED_EXECUTED_BY);
        assertThat(testFestivalAllowancePaymentDtl.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingFestivalAllowancePaymentDtl() throws Exception {
        int databaseSizeBeforeUpdate = festivalAllowancePaymentDtlRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFestivalAllowancePaymentDtlMockMvc.perform(put("/api/festival-allowance-payment-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(festivalAllowancePaymentDtl)))
            .andExpect(status().isBadRequest());

        // Validate the FestivalAllowancePaymentDtl in the database
        List<FestivalAllowancePaymentDtl> festivalAllowancePaymentDtlList = festivalAllowancePaymentDtlRepository.findAll();
        assertThat(festivalAllowancePaymentDtlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFestivalAllowancePaymentDtl() throws Exception {
        // Initialize the database
        festivalAllowancePaymentDtlService.save(festivalAllowancePaymentDtl);

        int databaseSizeBeforeDelete = festivalAllowancePaymentDtlRepository.findAll().size();

        // Delete the festivalAllowancePaymentDtl
        restFestivalAllowancePaymentDtlMockMvc.perform(delete("/api/festival-allowance-payment-dtls/{id}", festivalAllowancePaymentDtl.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FestivalAllowancePaymentDtl> festivalAllowancePaymentDtlList = festivalAllowancePaymentDtlRepository.findAll();
        assertThat(festivalAllowancePaymentDtlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
