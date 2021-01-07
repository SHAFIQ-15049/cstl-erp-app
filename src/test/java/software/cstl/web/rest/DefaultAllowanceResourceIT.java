package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.DefaultAllowance;
import software.cstl.repository.DefaultAllowanceRepository;
import software.cstl.service.DefaultAllowanceService;
import software.cstl.service.dto.DefaultAllowanceCriteria;
import software.cstl.service.DefaultAllowanceQueryService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.ActiveStatus;
/**
 * Integration tests for the {@link DefaultAllowanceResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DefaultAllowanceResourceIT {

    private static final BigDecimal DEFAULT_BASIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC = new BigDecimal(2);
    private static final BigDecimal SMALLER_BASIC = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_BASIC_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_BASIC_PERCENT = new BigDecimal(1 - 1);

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

    private static final BigDecimal DEFAULT_FESTIVAL_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FESTIVAL_ALLOWANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_FESTIVAL_ALLOWANCE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FESTIVAL_ALLOWANCE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FESTIVAL_ALLOWANCE_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_FESTIVAL_ALLOWANCE_PERCENT = new BigDecimal(1 - 1);

    private static final ActiveStatus DEFAULT_STATUS = ActiveStatus.ACTIVE;
    private static final ActiveStatus UPDATED_STATUS = ActiveStatus.NOT_ACTIVE;

    @Autowired
    private DefaultAllowanceRepository defaultAllowanceRepository;

    @Autowired
    private DefaultAllowanceService defaultAllowanceService;

    @Autowired
    private DefaultAllowanceQueryService defaultAllowanceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDefaultAllowanceMockMvc;

    private DefaultAllowance defaultAllowance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DefaultAllowance createEntity(EntityManager em) {
        DefaultAllowance defaultAllowance = new DefaultAllowance()
            .basic(DEFAULT_BASIC)
            .basicPercent(DEFAULT_BASIC_PERCENT)
            .totalAllowance(DEFAULT_TOTAL_ALLOWANCE)
            .medicalAllowance(DEFAULT_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(DEFAULT_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(DEFAULT_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(DEFAULT_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(DEFAULT_FOOD_ALLOWANCE)
            .foodAllowancePercent(DEFAULT_FOOD_ALLOWANCE_PERCENT)
            .festivalAllowance(DEFAULT_FESTIVAL_ALLOWANCE)
            .festivalAllowancePercent(DEFAULT_FESTIVAL_ALLOWANCE_PERCENT)
            .status(DEFAULT_STATUS);
        return defaultAllowance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DefaultAllowance createUpdatedEntity(EntityManager em) {
        DefaultAllowance defaultAllowance = new DefaultAllowance()
            .basic(UPDATED_BASIC)
            .basicPercent(UPDATED_BASIC_PERCENT)
            .totalAllowance(UPDATED_TOTAL_ALLOWANCE)
            .medicalAllowance(UPDATED_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(UPDATED_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(UPDATED_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(UPDATED_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(UPDATED_FOOD_ALLOWANCE)
            .foodAllowancePercent(UPDATED_FOOD_ALLOWANCE_PERCENT)
            .festivalAllowance(UPDATED_FESTIVAL_ALLOWANCE)
            .festivalAllowancePercent(UPDATED_FESTIVAL_ALLOWANCE_PERCENT)
            .status(UPDATED_STATUS);
        return defaultAllowance;
    }

    @BeforeEach
    public void initTest() {
        defaultAllowance = createEntity(em);
    }

    @Test
    @Transactional
    public void createDefaultAllowance() throws Exception {
        int databaseSizeBeforeCreate = defaultAllowanceRepository.findAll().size();
        // Create the DefaultAllowance
        restDefaultAllowanceMockMvc.perform(post("/api/default-allowances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(defaultAllowance)))
            .andExpect(status().isCreated());

        // Validate the DefaultAllowance in the database
        List<DefaultAllowance> defaultAllowanceList = defaultAllowanceRepository.findAll();
        assertThat(defaultAllowanceList).hasSize(databaseSizeBeforeCreate + 1);
        DefaultAllowance testDefaultAllowance = defaultAllowanceList.get(defaultAllowanceList.size() - 1);
        assertThat(testDefaultAllowance.getBasic()).isEqualTo(DEFAULT_BASIC);
        assertThat(testDefaultAllowance.getBasicPercent()).isEqualTo(DEFAULT_BASIC_PERCENT);
        assertThat(testDefaultAllowance.getTotalAllowance()).isEqualTo(DEFAULT_TOTAL_ALLOWANCE);
        assertThat(testDefaultAllowance.getMedicalAllowance()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE);
        assertThat(testDefaultAllowance.getMedicalAllowancePercent()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE_PERCENT);
        assertThat(testDefaultAllowance.getConvinceAllowance()).isEqualTo(DEFAULT_CONVINCE_ALLOWANCE);
        assertThat(testDefaultAllowance.getConvinceAllowancePercent()).isEqualTo(DEFAULT_CONVINCE_ALLOWANCE_PERCENT);
        assertThat(testDefaultAllowance.getFoodAllowance()).isEqualTo(DEFAULT_FOOD_ALLOWANCE);
        assertThat(testDefaultAllowance.getFoodAllowancePercent()).isEqualTo(DEFAULT_FOOD_ALLOWANCE_PERCENT);
        assertThat(testDefaultAllowance.getFestivalAllowance()).isEqualTo(DEFAULT_FESTIVAL_ALLOWANCE);
        assertThat(testDefaultAllowance.getFestivalAllowancePercent()).isEqualTo(DEFAULT_FESTIVAL_ALLOWANCE_PERCENT);
        assertThat(testDefaultAllowance.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDefaultAllowanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = defaultAllowanceRepository.findAll().size();

        // Create the DefaultAllowance with an existing ID
        defaultAllowance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDefaultAllowanceMockMvc.perform(post("/api/default-allowances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(defaultAllowance)))
            .andExpect(status().isBadRequest());

        // Validate the DefaultAllowance in the database
        List<DefaultAllowance> defaultAllowanceList = defaultAllowanceRepository.findAll();
        assertThat(defaultAllowanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalAllowanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = defaultAllowanceRepository.findAll().size();
        // set the field null
        defaultAllowance.setTotalAllowance(null);

        // Create the DefaultAllowance, which fails.


        restDefaultAllowanceMockMvc.perform(post("/api/default-allowances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(defaultAllowance)))
            .andExpect(status().isBadRequest());

        List<DefaultAllowance> defaultAllowanceList = defaultAllowanceRepository.findAll();
        assertThat(defaultAllowanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = defaultAllowanceRepository.findAll().size();
        // set the field null
        defaultAllowance.setStatus(null);

        // Create the DefaultAllowance, which fails.


        restDefaultAllowanceMockMvc.perform(post("/api/default-allowances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(defaultAllowance)))
            .andExpect(status().isBadRequest());

        List<DefaultAllowance> defaultAllowanceList = defaultAllowanceRepository.findAll();
        assertThat(defaultAllowanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowances() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList
        restDefaultAllowanceMockMvc.perform(get("/api/default-allowances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defaultAllowance.getId().intValue())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].basicPercent").value(hasItem(DEFAULT_BASIC_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].totalAllowance").value(hasItem(DEFAULT_TOTAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowancePercent").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowance").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowancePercent").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowancePercent").value(hasItem(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].festivalAllowance").value(hasItem(DEFAULT_FESTIVAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].festivalAllowancePercent").value(hasItem(DEFAULT_FESTIVAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getDefaultAllowance() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get the defaultAllowance
        restDefaultAllowanceMockMvc.perform(get("/api/default-allowances/{id}", defaultAllowance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(defaultAllowance.getId().intValue()))
            .andExpect(jsonPath("$.basic").value(DEFAULT_BASIC.intValue()))
            .andExpect(jsonPath("$.basicPercent").value(DEFAULT_BASIC_PERCENT.intValue()))
            .andExpect(jsonPath("$.totalAllowance").value(DEFAULT_TOTAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.medicalAllowance").value(DEFAULT_MEDICAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.medicalAllowancePercent").value(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.convinceAllowance").value(DEFAULT_CONVINCE_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.convinceAllowancePercent").value(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.foodAllowance").value(DEFAULT_FOOD_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.foodAllowancePercent").value(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.festivalAllowance").value(DEFAULT_FESTIVAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.festivalAllowancePercent").value(DEFAULT_FESTIVAL_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getDefaultAllowancesByIdFiltering() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        Long id = defaultAllowance.getId();

        defaultDefaultAllowanceShouldBeFound("id.equals=" + id);
        defaultDefaultAllowanceShouldNotBeFound("id.notEquals=" + id);

        defaultDefaultAllowanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDefaultAllowanceShouldNotBeFound("id.greaterThan=" + id);

        defaultDefaultAllowanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDefaultAllowanceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basic equals to DEFAULT_BASIC
        defaultDefaultAllowanceShouldBeFound("basic.equals=" + DEFAULT_BASIC);

        // Get all the defaultAllowanceList where basic equals to UPDATED_BASIC
        defaultDefaultAllowanceShouldNotBeFound("basic.equals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basic not equals to DEFAULT_BASIC
        defaultDefaultAllowanceShouldNotBeFound("basic.notEquals=" + DEFAULT_BASIC);

        // Get all the defaultAllowanceList where basic not equals to UPDATED_BASIC
        defaultDefaultAllowanceShouldBeFound("basic.notEquals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basic in DEFAULT_BASIC or UPDATED_BASIC
        defaultDefaultAllowanceShouldBeFound("basic.in=" + DEFAULT_BASIC + "," + UPDATED_BASIC);

        // Get all the defaultAllowanceList where basic equals to UPDATED_BASIC
        defaultDefaultAllowanceShouldNotBeFound("basic.in=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basic is not null
        defaultDefaultAllowanceShouldBeFound("basic.specified=true");

        // Get all the defaultAllowanceList where basic is null
        defaultDefaultAllowanceShouldNotBeFound("basic.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basic is greater than or equal to DEFAULT_BASIC
        defaultDefaultAllowanceShouldBeFound("basic.greaterThanOrEqual=" + DEFAULT_BASIC);

        // Get all the defaultAllowanceList where basic is greater than or equal to UPDATED_BASIC
        defaultDefaultAllowanceShouldNotBeFound("basic.greaterThanOrEqual=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basic is less than or equal to DEFAULT_BASIC
        defaultDefaultAllowanceShouldBeFound("basic.lessThanOrEqual=" + DEFAULT_BASIC);

        // Get all the defaultAllowanceList where basic is less than or equal to SMALLER_BASIC
        defaultDefaultAllowanceShouldNotBeFound("basic.lessThanOrEqual=" + SMALLER_BASIC);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basic is less than DEFAULT_BASIC
        defaultDefaultAllowanceShouldNotBeFound("basic.lessThan=" + DEFAULT_BASIC);

        // Get all the defaultAllowanceList where basic is less than UPDATED_BASIC
        defaultDefaultAllowanceShouldBeFound("basic.lessThan=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basic is greater than DEFAULT_BASIC
        defaultDefaultAllowanceShouldNotBeFound("basic.greaterThan=" + DEFAULT_BASIC);

        // Get all the defaultAllowanceList where basic is greater than SMALLER_BASIC
        defaultDefaultAllowanceShouldBeFound("basic.greaterThan=" + SMALLER_BASIC);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basicPercent equals to DEFAULT_BASIC_PERCENT
        defaultDefaultAllowanceShouldBeFound("basicPercent.equals=" + DEFAULT_BASIC_PERCENT);

        // Get all the defaultAllowanceList where basicPercent equals to UPDATED_BASIC_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("basicPercent.equals=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basicPercent not equals to DEFAULT_BASIC_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("basicPercent.notEquals=" + DEFAULT_BASIC_PERCENT);

        // Get all the defaultAllowanceList where basicPercent not equals to UPDATED_BASIC_PERCENT
        defaultDefaultAllowanceShouldBeFound("basicPercent.notEquals=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicPercentIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basicPercent in DEFAULT_BASIC_PERCENT or UPDATED_BASIC_PERCENT
        defaultDefaultAllowanceShouldBeFound("basicPercent.in=" + DEFAULT_BASIC_PERCENT + "," + UPDATED_BASIC_PERCENT);

        // Get all the defaultAllowanceList where basicPercent equals to UPDATED_BASIC_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("basicPercent.in=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basicPercent is not null
        defaultDefaultAllowanceShouldBeFound("basicPercent.specified=true");

        // Get all the defaultAllowanceList where basicPercent is null
        defaultDefaultAllowanceShouldNotBeFound("basicPercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basicPercent is greater than or equal to DEFAULT_BASIC_PERCENT
        defaultDefaultAllowanceShouldBeFound("basicPercent.greaterThanOrEqual=" + DEFAULT_BASIC_PERCENT);

        // Get all the defaultAllowanceList where basicPercent is greater than or equal to UPDATED_BASIC_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("basicPercent.greaterThanOrEqual=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basicPercent is less than or equal to DEFAULT_BASIC_PERCENT
        defaultDefaultAllowanceShouldBeFound("basicPercent.lessThanOrEqual=" + DEFAULT_BASIC_PERCENT);

        // Get all the defaultAllowanceList where basicPercent is less than or equal to SMALLER_BASIC_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("basicPercent.lessThanOrEqual=" + SMALLER_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basicPercent is less than DEFAULT_BASIC_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("basicPercent.lessThan=" + DEFAULT_BASIC_PERCENT);

        // Get all the defaultAllowanceList where basicPercent is less than UPDATED_BASIC_PERCENT
        defaultDefaultAllowanceShouldBeFound("basicPercent.lessThan=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByBasicPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where basicPercent is greater than DEFAULT_BASIC_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("basicPercent.greaterThan=" + DEFAULT_BASIC_PERCENT);

        // Get all the defaultAllowanceList where basicPercent is greater than SMALLER_BASIC_PERCENT
        defaultDefaultAllowanceShouldBeFound("basicPercent.greaterThan=" + SMALLER_BASIC_PERCENT);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByTotalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where totalAllowance equals to DEFAULT_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("totalAllowance.equals=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the defaultAllowanceList where totalAllowance equals to UPDATED_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("totalAllowance.equals=" + UPDATED_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByTotalAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where totalAllowance not equals to DEFAULT_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("totalAllowance.notEquals=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the defaultAllowanceList where totalAllowance not equals to UPDATED_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("totalAllowance.notEquals=" + UPDATED_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByTotalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where totalAllowance in DEFAULT_TOTAL_ALLOWANCE or UPDATED_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("totalAllowance.in=" + DEFAULT_TOTAL_ALLOWANCE + "," + UPDATED_TOTAL_ALLOWANCE);

        // Get all the defaultAllowanceList where totalAllowance equals to UPDATED_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("totalAllowance.in=" + UPDATED_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByTotalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where totalAllowance is not null
        defaultDefaultAllowanceShouldBeFound("totalAllowance.specified=true");

        // Get all the defaultAllowanceList where totalAllowance is null
        defaultDefaultAllowanceShouldNotBeFound("totalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByTotalAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where totalAllowance is greater than or equal to DEFAULT_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("totalAllowance.greaterThanOrEqual=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the defaultAllowanceList where totalAllowance is greater than or equal to UPDATED_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("totalAllowance.greaterThanOrEqual=" + UPDATED_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByTotalAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where totalAllowance is less than or equal to DEFAULT_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("totalAllowance.lessThanOrEqual=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the defaultAllowanceList where totalAllowance is less than or equal to SMALLER_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("totalAllowance.lessThanOrEqual=" + SMALLER_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByTotalAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where totalAllowance is less than DEFAULT_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("totalAllowance.lessThan=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the defaultAllowanceList where totalAllowance is less than UPDATED_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("totalAllowance.lessThan=" + UPDATED_TOTAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByTotalAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where totalAllowance is greater than DEFAULT_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("totalAllowance.greaterThan=" + DEFAULT_TOTAL_ALLOWANCE);

        // Get all the defaultAllowanceList where totalAllowance is greater than SMALLER_TOTAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("totalAllowance.greaterThan=" + SMALLER_TOTAL_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowance equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("medicalAllowance.equals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the defaultAllowanceList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowance.equals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowance not equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowance.notEquals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the defaultAllowanceList where medicalAllowance not equals to UPDATED_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("medicalAllowance.notEquals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowance in DEFAULT_MEDICAL_ALLOWANCE or UPDATED_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("medicalAllowance.in=" + DEFAULT_MEDICAL_ALLOWANCE + "," + UPDATED_MEDICAL_ALLOWANCE);

        // Get all the defaultAllowanceList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowance.in=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowance is not null
        defaultDefaultAllowanceShouldBeFound("medicalAllowance.specified=true");

        // Get all the defaultAllowanceList where medicalAllowance is null
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowance is greater than or equal to DEFAULT_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("medicalAllowance.greaterThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the defaultAllowanceList where medicalAllowance is greater than or equal to UPDATED_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowance.greaterThanOrEqual=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowance is less than or equal to DEFAULT_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("medicalAllowance.lessThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the defaultAllowanceList where medicalAllowance is less than or equal to SMALLER_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowance.lessThanOrEqual=" + SMALLER_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowance is less than DEFAULT_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowance.lessThan=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the defaultAllowanceList where medicalAllowance is less than UPDATED_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("medicalAllowance.lessThan=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowance is greater than DEFAULT_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowance.greaterThan=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the defaultAllowanceList where medicalAllowance is greater than SMALLER_MEDICAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("medicalAllowance.greaterThan=" + SMALLER_MEDICAL_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowancePercent equals to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("medicalAllowancePercent.equals=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where medicalAllowancePercent equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowancePercent.equals=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowancePercent not equals to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowancePercent.notEquals=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where medicalAllowancePercent not equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("medicalAllowancePercent.notEquals=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowancePercent in DEFAULT_MEDICAL_ALLOWANCE_PERCENT or UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("medicalAllowancePercent.in=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT + "," + UPDATED_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where medicalAllowancePercent equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowancePercent.in=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowancePercent is not null
        defaultDefaultAllowanceShouldBeFound("medicalAllowancePercent.specified=true");

        // Get all the defaultAllowanceList where medicalAllowancePercent is null
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowancePercent is greater than or equal to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("medicalAllowancePercent.greaterThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where medicalAllowancePercent is greater than or equal to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowancePercent.greaterThanOrEqual=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowancePercent is less than or equal to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("medicalAllowancePercent.lessThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where medicalAllowancePercent is less than or equal to SMALLER_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowancePercent.lessThanOrEqual=" + SMALLER_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowancePercent is less than DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowancePercent.lessThan=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where medicalAllowancePercent is less than UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("medicalAllowancePercent.lessThan=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByMedicalAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where medicalAllowancePercent is greater than DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("medicalAllowancePercent.greaterThan=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where medicalAllowancePercent is greater than SMALLER_MEDICAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("medicalAllowancePercent.greaterThan=" + SMALLER_MEDICAL_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowance equals to DEFAULT_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("convinceAllowance.equals=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the defaultAllowanceList where convinceAllowance equals to UPDATED_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowance.equals=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowance not equals to DEFAULT_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowance.notEquals=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the defaultAllowanceList where convinceAllowance not equals to UPDATED_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("convinceAllowance.notEquals=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowance in DEFAULT_CONVINCE_ALLOWANCE or UPDATED_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("convinceAllowance.in=" + DEFAULT_CONVINCE_ALLOWANCE + "," + UPDATED_CONVINCE_ALLOWANCE);

        // Get all the defaultAllowanceList where convinceAllowance equals to UPDATED_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowance.in=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowance is not null
        defaultDefaultAllowanceShouldBeFound("convinceAllowance.specified=true");

        // Get all the defaultAllowanceList where convinceAllowance is null
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowance is greater than or equal to DEFAULT_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("convinceAllowance.greaterThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the defaultAllowanceList where convinceAllowance is greater than or equal to UPDATED_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowance.greaterThanOrEqual=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowance is less than or equal to DEFAULT_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("convinceAllowance.lessThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the defaultAllowanceList where convinceAllowance is less than or equal to SMALLER_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowance.lessThanOrEqual=" + SMALLER_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowance is less than DEFAULT_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowance.lessThan=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the defaultAllowanceList where convinceAllowance is less than UPDATED_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("convinceAllowance.lessThan=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowance is greater than DEFAULT_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowance.greaterThan=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the defaultAllowanceList where convinceAllowance is greater than SMALLER_CONVINCE_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("convinceAllowance.greaterThan=" + SMALLER_CONVINCE_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowancePercent equals to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("convinceAllowancePercent.equals=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where convinceAllowancePercent equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowancePercent.equals=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowancePercent not equals to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowancePercent.notEquals=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where convinceAllowancePercent not equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("convinceAllowancePercent.notEquals=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowancePercent in DEFAULT_CONVINCE_ALLOWANCE_PERCENT or UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("convinceAllowancePercent.in=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT + "," + UPDATED_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where convinceAllowancePercent equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowancePercent.in=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowancePercent is not null
        defaultDefaultAllowanceShouldBeFound("convinceAllowancePercent.specified=true");

        // Get all the defaultAllowanceList where convinceAllowancePercent is null
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowancePercent is greater than or equal to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("convinceAllowancePercent.greaterThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where convinceAllowancePercent is greater than or equal to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowancePercent.greaterThanOrEqual=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowancePercent is less than or equal to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("convinceAllowancePercent.lessThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where convinceAllowancePercent is less than or equal to SMALLER_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowancePercent.lessThanOrEqual=" + SMALLER_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowancePercent is less than DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowancePercent.lessThan=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where convinceAllowancePercent is less than UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("convinceAllowancePercent.lessThan=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByConvinceAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where convinceAllowancePercent is greater than DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("convinceAllowancePercent.greaterThan=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where convinceAllowancePercent is greater than SMALLER_CONVINCE_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("convinceAllowancePercent.greaterThan=" + SMALLER_CONVINCE_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowance equals to DEFAULT_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("foodAllowance.equals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the defaultAllowanceList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("foodAllowance.equals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowance not equals to DEFAULT_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("foodAllowance.notEquals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the defaultAllowanceList where foodAllowance not equals to UPDATED_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("foodAllowance.notEquals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowance in DEFAULT_FOOD_ALLOWANCE or UPDATED_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("foodAllowance.in=" + DEFAULT_FOOD_ALLOWANCE + "," + UPDATED_FOOD_ALLOWANCE);

        // Get all the defaultAllowanceList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("foodAllowance.in=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowance is not null
        defaultDefaultAllowanceShouldBeFound("foodAllowance.specified=true");

        // Get all the defaultAllowanceList where foodAllowance is null
        defaultDefaultAllowanceShouldNotBeFound("foodAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowance is greater than or equal to DEFAULT_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("foodAllowance.greaterThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the defaultAllowanceList where foodAllowance is greater than or equal to UPDATED_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("foodAllowance.greaterThanOrEqual=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowance is less than or equal to DEFAULT_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("foodAllowance.lessThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the defaultAllowanceList where foodAllowance is less than or equal to SMALLER_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("foodAllowance.lessThanOrEqual=" + SMALLER_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowance is less than DEFAULT_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("foodAllowance.lessThan=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the defaultAllowanceList where foodAllowance is less than UPDATED_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("foodAllowance.lessThan=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowance is greater than DEFAULT_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("foodAllowance.greaterThan=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the defaultAllowanceList where foodAllowance is greater than SMALLER_FOOD_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("foodAllowance.greaterThan=" + SMALLER_FOOD_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowancePercent equals to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("foodAllowancePercent.equals=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where foodAllowancePercent equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("foodAllowancePercent.equals=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowancePercent not equals to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("foodAllowancePercent.notEquals=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where foodAllowancePercent not equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("foodAllowancePercent.notEquals=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowancePercent in DEFAULT_FOOD_ALLOWANCE_PERCENT or UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("foodAllowancePercent.in=" + DEFAULT_FOOD_ALLOWANCE_PERCENT + "," + UPDATED_FOOD_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where foodAllowancePercent equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("foodAllowancePercent.in=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowancePercent is not null
        defaultDefaultAllowanceShouldBeFound("foodAllowancePercent.specified=true");

        // Get all the defaultAllowanceList where foodAllowancePercent is null
        defaultDefaultAllowanceShouldNotBeFound("foodAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowancePercent is greater than or equal to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("foodAllowancePercent.greaterThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where foodAllowancePercent is greater than or equal to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("foodAllowancePercent.greaterThanOrEqual=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowancePercent is less than or equal to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("foodAllowancePercent.lessThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where foodAllowancePercent is less than or equal to SMALLER_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("foodAllowancePercent.lessThanOrEqual=" + SMALLER_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowancePercent is less than DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("foodAllowancePercent.lessThan=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where foodAllowancePercent is less than UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("foodAllowancePercent.lessThan=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFoodAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where foodAllowancePercent is greater than DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("foodAllowancePercent.greaterThan=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where foodAllowancePercent is greater than SMALLER_FOOD_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("foodAllowancePercent.greaterThan=" + SMALLER_FOOD_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowance equals to DEFAULT_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("festivalAllowance.equals=" + DEFAULT_FESTIVAL_ALLOWANCE);

        // Get all the defaultAllowanceList where festivalAllowance equals to UPDATED_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowance.equals=" + UPDATED_FESTIVAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowance not equals to DEFAULT_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowance.notEquals=" + DEFAULT_FESTIVAL_ALLOWANCE);

        // Get all the defaultAllowanceList where festivalAllowance not equals to UPDATED_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("festivalAllowance.notEquals=" + UPDATED_FESTIVAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowance in DEFAULT_FESTIVAL_ALLOWANCE or UPDATED_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("festivalAllowance.in=" + DEFAULT_FESTIVAL_ALLOWANCE + "," + UPDATED_FESTIVAL_ALLOWANCE);

        // Get all the defaultAllowanceList where festivalAllowance equals to UPDATED_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowance.in=" + UPDATED_FESTIVAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowance is not null
        defaultDefaultAllowanceShouldBeFound("festivalAllowance.specified=true");

        // Get all the defaultAllowanceList where festivalAllowance is null
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowance is greater than or equal to DEFAULT_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("festivalAllowance.greaterThanOrEqual=" + DEFAULT_FESTIVAL_ALLOWANCE);

        // Get all the defaultAllowanceList where festivalAllowance is greater than or equal to UPDATED_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowance.greaterThanOrEqual=" + UPDATED_FESTIVAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowance is less than or equal to DEFAULT_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("festivalAllowance.lessThanOrEqual=" + DEFAULT_FESTIVAL_ALLOWANCE);

        // Get all the defaultAllowanceList where festivalAllowance is less than or equal to SMALLER_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowance.lessThanOrEqual=" + SMALLER_FESTIVAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowance is less than DEFAULT_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowance.lessThan=" + DEFAULT_FESTIVAL_ALLOWANCE);

        // Get all the defaultAllowanceList where festivalAllowance is less than UPDATED_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("festivalAllowance.lessThan=" + UPDATED_FESTIVAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowance is greater than DEFAULT_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowance.greaterThan=" + DEFAULT_FESTIVAL_ALLOWANCE);

        // Get all the defaultAllowanceList where festivalAllowance is greater than SMALLER_FESTIVAL_ALLOWANCE
        defaultDefaultAllowanceShouldBeFound("festivalAllowance.greaterThan=" + SMALLER_FESTIVAL_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowancePercent equals to DEFAULT_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("festivalAllowancePercent.equals=" + DEFAULT_FESTIVAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where festivalAllowancePercent equals to UPDATED_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowancePercent.equals=" + UPDATED_FESTIVAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowancePercent not equals to DEFAULT_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowancePercent.notEquals=" + DEFAULT_FESTIVAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where festivalAllowancePercent not equals to UPDATED_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("festivalAllowancePercent.notEquals=" + UPDATED_FESTIVAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowancePercent in DEFAULT_FESTIVAL_ALLOWANCE_PERCENT or UPDATED_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("festivalAllowancePercent.in=" + DEFAULT_FESTIVAL_ALLOWANCE_PERCENT + "," + UPDATED_FESTIVAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where festivalAllowancePercent equals to UPDATED_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowancePercent.in=" + UPDATED_FESTIVAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowancePercent is not null
        defaultDefaultAllowanceShouldBeFound("festivalAllowancePercent.specified=true");

        // Get all the defaultAllowanceList where festivalAllowancePercent is null
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowancePercent is greater than or equal to DEFAULT_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("festivalAllowancePercent.greaterThanOrEqual=" + DEFAULT_FESTIVAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where festivalAllowancePercent is greater than or equal to UPDATED_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowancePercent.greaterThanOrEqual=" + UPDATED_FESTIVAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowancePercent is less than or equal to DEFAULT_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("festivalAllowancePercent.lessThanOrEqual=" + DEFAULT_FESTIVAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where festivalAllowancePercent is less than or equal to SMALLER_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowancePercent.lessThanOrEqual=" + SMALLER_FESTIVAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowancePercent is less than DEFAULT_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowancePercent.lessThan=" + DEFAULT_FESTIVAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where festivalAllowancePercent is less than UPDATED_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("festivalAllowancePercent.lessThan=" + UPDATED_FESTIVAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByFestivalAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where festivalAllowancePercent is greater than DEFAULT_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldNotBeFound("festivalAllowancePercent.greaterThan=" + DEFAULT_FESTIVAL_ALLOWANCE_PERCENT);

        // Get all the defaultAllowanceList where festivalAllowancePercent is greater than SMALLER_FESTIVAL_ALLOWANCE_PERCENT
        defaultDefaultAllowanceShouldBeFound("festivalAllowancePercent.greaterThan=" + SMALLER_FESTIVAL_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllDefaultAllowancesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where status equals to DEFAULT_STATUS
        defaultDefaultAllowanceShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the defaultAllowanceList where status equals to UPDATED_STATUS
        defaultDefaultAllowanceShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where status not equals to DEFAULT_STATUS
        defaultDefaultAllowanceShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the defaultAllowanceList where status not equals to UPDATED_STATUS
        defaultDefaultAllowanceShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDefaultAllowanceShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the defaultAllowanceList where status equals to UPDATED_STATUS
        defaultDefaultAllowanceShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDefaultAllowancesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        defaultAllowanceRepository.saveAndFlush(defaultAllowance);

        // Get all the defaultAllowanceList where status is not null
        defaultDefaultAllowanceShouldBeFound("status.specified=true");

        // Get all the defaultAllowanceList where status is null
        defaultDefaultAllowanceShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDefaultAllowanceShouldBeFound(String filter) throws Exception {
        restDefaultAllowanceMockMvc.perform(get("/api/default-allowances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defaultAllowance.getId().intValue())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].basicPercent").value(hasItem(DEFAULT_BASIC_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].totalAllowance").value(hasItem(DEFAULT_TOTAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowancePercent").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowance").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowancePercent").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowancePercent").value(hasItem(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].festivalAllowance").value(hasItem(DEFAULT_FESTIVAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].festivalAllowancePercent").value(hasItem(DEFAULT_FESTIVAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restDefaultAllowanceMockMvc.perform(get("/api/default-allowances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDefaultAllowanceShouldNotBeFound(String filter) throws Exception {
        restDefaultAllowanceMockMvc.perform(get("/api/default-allowances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDefaultAllowanceMockMvc.perform(get("/api/default-allowances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDefaultAllowance() throws Exception {
        // Get the defaultAllowance
        restDefaultAllowanceMockMvc.perform(get("/api/default-allowances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDefaultAllowance() throws Exception {
        // Initialize the database
        defaultAllowanceService.save(defaultAllowance);

        int databaseSizeBeforeUpdate = defaultAllowanceRepository.findAll().size();

        // Update the defaultAllowance
        DefaultAllowance updatedDefaultAllowance = defaultAllowanceRepository.findById(defaultAllowance.getId()).get();
        // Disconnect from session so that the updates on updatedDefaultAllowance are not directly saved in db
        em.detach(updatedDefaultAllowance);
        updatedDefaultAllowance
            .basic(UPDATED_BASIC)
            .basicPercent(UPDATED_BASIC_PERCENT)
            .totalAllowance(UPDATED_TOTAL_ALLOWANCE)
            .medicalAllowance(UPDATED_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(UPDATED_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(UPDATED_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(UPDATED_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(UPDATED_FOOD_ALLOWANCE)
            .foodAllowancePercent(UPDATED_FOOD_ALLOWANCE_PERCENT)
            .festivalAllowance(UPDATED_FESTIVAL_ALLOWANCE)
            .festivalAllowancePercent(UPDATED_FESTIVAL_ALLOWANCE_PERCENT)
            .status(UPDATED_STATUS);

        restDefaultAllowanceMockMvc.perform(put("/api/default-allowances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDefaultAllowance)))
            .andExpect(status().isOk());

        // Validate the DefaultAllowance in the database
        List<DefaultAllowance> defaultAllowanceList = defaultAllowanceRepository.findAll();
        assertThat(defaultAllowanceList).hasSize(databaseSizeBeforeUpdate);
        DefaultAllowance testDefaultAllowance = defaultAllowanceList.get(defaultAllowanceList.size() - 1);
        assertThat(testDefaultAllowance.getBasic()).isEqualTo(UPDATED_BASIC);
        assertThat(testDefaultAllowance.getBasicPercent()).isEqualTo(UPDATED_BASIC_PERCENT);
        assertThat(testDefaultAllowance.getTotalAllowance()).isEqualTo(UPDATED_TOTAL_ALLOWANCE);
        assertThat(testDefaultAllowance.getMedicalAllowance()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE);
        assertThat(testDefaultAllowance.getMedicalAllowancePercent()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE_PERCENT);
        assertThat(testDefaultAllowance.getConvinceAllowance()).isEqualTo(UPDATED_CONVINCE_ALLOWANCE);
        assertThat(testDefaultAllowance.getConvinceAllowancePercent()).isEqualTo(UPDATED_CONVINCE_ALLOWANCE_PERCENT);
        assertThat(testDefaultAllowance.getFoodAllowance()).isEqualTo(UPDATED_FOOD_ALLOWANCE);
        assertThat(testDefaultAllowance.getFoodAllowancePercent()).isEqualTo(UPDATED_FOOD_ALLOWANCE_PERCENT);
        assertThat(testDefaultAllowance.getFestivalAllowance()).isEqualTo(UPDATED_FESTIVAL_ALLOWANCE);
        assertThat(testDefaultAllowance.getFestivalAllowancePercent()).isEqualTo(UPDATED_FESTIVAL_ALLOWANCE_PERCENT);
        assertThat(testDefaultAllowance.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDefaultAllowance() throws Exception {
        int databaseSizeBeforeUpdate = defaultAllowanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDefaultAllowanceMockMvc.perform(put("/api/default-allowances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(defaultAllowance)))
            .andExpect(status().isBadRequest());

        // Validate the DefaultAllowance in the database
        List<DefaultAllowance> defaultAllowanceList = defaultAllowanceRepository.findAll();
        assertThat(defaultAllowanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDefaultAllowance() throws Exception {
        // Initialize the database
        defaultAllowanceService.save(defaultAllowance);

        int databaseSizeBeforeDelete = defaultAllowanceRepository.findAll().size();

        // Delete the defaultAllowance
        restDefaultAllowanceMockMvc.perform(delete("/api/default-allowances/{id}", defaultAllowance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DefaultAllowance> defaultAllowanceList = defaultAllowanceRepository.findAll();
        assertThat(defaultAllowanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
