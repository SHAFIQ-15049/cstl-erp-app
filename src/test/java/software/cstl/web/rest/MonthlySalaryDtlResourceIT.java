package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.domain.MonthlySalary;
import software.cstl.domain.Employee;
import software.cstl.repository.MonthlySalaryDtlRepository;
import software.cstl.service.MonthlySalaryDtlService;
import software.cstl.service.dto.MonthlySalaryDtlCriteria;
import software.cstl.service.MonthlySalaryDtlQueryService;

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
 * Integration tests for the {@link MonthlySalaryDtlResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MonthlySalaryDtlResourceIT {

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

    private static final Instant DEFAULT_EXECUTED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private MonthlySalaryDtlRepository monthlySalaryDtlRepository;

    @Autowired
    private MonthlySalaryDtlService monthlySalaryDtlService;

    @Autowired
    private MonthlySalaryDtlQueryService monthlySalaryDtlQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMonthlySalaryDtlMockMvc;

    private MonthlySalaryDtl monthlySalaryDtl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlySalaryDtl createEntity(EntityManager em) {
        MonthlySalaryDtl monthlySalaryDtl = new MonthlySalaryDtl()
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
        return monthlySalaryDtl;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlySalaryDtl createUpdatedEntity(EntityManager em) {
        MonthlySalaryDtl monthlySalaryDtl = new MonthlySalaryDtl()
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
        return monthlySalaryDtl;
    }

    @BeforeEach
    public void initTest() {
        monthlySalaryDtl = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonthlySalaryDtl() throws Exception {
        int databaseSizeBeforeCreate = monthlySalaryDtlRepository.findAll().size();
        // Create the MonthlySalaryDtl
        restMonthlySalaryDtlMockMvc.perform(post("/api/monthly-salary-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDtl)))
            .andExpect(status().isCreated());

        // Validate the MonthlySalaryDtl in the database
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAll();
        assertThat(monthlySalaryDtlList).hasSize(databaseSizeBeforeCreate + 1);
        MonthlySalaryDtl testMonthlySalaryDtl = monthlySalaryDtlList.get(monthlySalaryDtlList.size() - 1);
        assertThat(testMonthlySalaryDtl.getGross()).isEqualTo(DEFAULT_GROSS);
        assertThat(testMonthlySalaryDtl.getBasic()).isEqualTo(DEFAULT_BASIC);
        assertThat(testMonthlySalaryDtl.getBasicPercent()).isEqualTo(DEFAULT_BASIC_PERCENT);
        assertThat(testMonthlySalaryDtl.getHouseRent()).isEqualTo(DEFAULT_HOUSE_RENT);
        assertThat(testMonthlySalaryDtl.getHouseRentPercent()).isEqualTo(DEFAULT_HOUSE_RENT_PERCENT);
        assertThat(testMonthlySalaryDtl.getMedicalAllowance()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getMedicalAllowancePercent()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getConvinceAllowance()).isEqualTo(DEFAULT_CONVINCE_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getConvinceAllowancePercent()).isEqualTo(DEFAULT_CONVINCE_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getFoodAllowance()).isEqualTo(DEFAULT_FOOD_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getFoodAllowancePercent()).isEqualTo(DEFAULT_FOOD_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getFine()).isEqualTo(DEFAULT_FINE);
        assertThat(testMonthlySalaryDtl.getAdvance()).isEqualTo(DEFAULT_ADVANCE);
        assertThat(testMonthlySalaryDtl.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMonthlySalaryDtl.getExecutedOn()).isEqualTo(DEFAULT_EXECUTED_ON);
        assertThat(testMonthlySalaryDtl.getExecutedBy()).isEqualTo(DEFAULT_EXECUTED_BY);
        assertThat(testMonthlySalaryDtl.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createMonthlySalaryDtlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monthlySalaryDtlRepository.findAll().size();

        // Create the MonthlySalaryDtl with an existing ID
        monthlySalaryDtl.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlySalaryDtlMockMvc.perform(post("/api/monthly-salary-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDtl)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlySalaryDtl in the database
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAll();
        assertThat(monthlySalaryDtlList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtls() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlySalaryDtl.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getMonthlySalaryDtl() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get the monthlySalaryDtl
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls/{id}", monthlySalaryDtl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monthlySalaryDtl.getId().intValue()))
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
            .andExpect(jsonPath("$.executedBy").value(DEFAULT_EXECUTED_BY.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }


    @Test
    @Transactional
    public void getMonthlySalaryDtlsByIdFiltering() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        Long id = monthlySalaryDtl.getId();

        defaultMonthlySalaryDtlShouldBeFound("id.equals=" + id);
        defaultMonthlySalaryDtlShouldNotBeFound("id.notEquals=" + id);

        defaultMonthlySalaryDtlShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMonthlySalaryDtlShouldNotBeFound("id.greaterThan=" + id);

        defaultMonthlySalaryDtlShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMonthlySalaryDtlShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross equals to DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.equals=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross equals to UPDATED_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.equals=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross not equals to DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.notEquals=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross not equals to UPDATED_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.notEquals=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross in DEFAULT_GROSS or UPDATED_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.in=" + DEFAULT_GROSS + "," + UPDATED_GROSS);

        // Get all the monthlySalaryDtlList where gross equals to UPDATED_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.in=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross is not null
        defaultMonthlySalaryDtlShouldBeFound("gross.specified=true");

        // Get all the monthlySalaryDtlList where gross is null
        defaultMonthlySalaryDtlShouldNotBeFound("gross.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross is greater than or equal to DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.greaterThanOrEqual=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross is greater than or equal to UPDATED_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.greaterThanOrEqual=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross is less than or equal to DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.lessThanOrEqual=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross is less than or equal to SMALLER_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.lessThanOrEqual=" + SMALLER_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross is less than DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.lessThan=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross is less than UPDATED_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.lessThan=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross is greater than DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.greaterThan=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross is greater than SMALLER_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.greaterThan=" + SMALLER_GROSS);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic equals to DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.equals=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic equals to UPDATED_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.equals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic not equals to DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.notEquals=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic not equals to UPDATED_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.notEquals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic in DEFAULT_BASIC or UPDATED_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.in=" + DEFAULT_BASIC + "," + UPDATED_BASIC);

        // Get all the monthlySalaryDtlList where basic equals to UPDATED_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.in=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic is not null
        defaultMonthlySalaryDtlShouldBeFound("basic.specified=true");

        // Get all the monthlySalaryDtlList where basic is null
        defaultMonthlySalaryDtlShouldNotBeFound("basic.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic is greater than or equal to DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.greaterThanOrEqual=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic is greater than or equal to UPDATED_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.greaterThanOrEqual=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic is less than or equal to DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.lessThanOrEqual=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic is less than or equal to SMALLER_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.lessThanOrEqual=" + SMALLER_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic is less than DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.lessThan=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic is less than UPDATED_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.lessThan=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic is greater than DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.greaterThan=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic is greater than SMALLER_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.greaterThan=" + SMALLER_BASIC);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent equals to DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.equals=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent equals to UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.equals=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent not equals to DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.notEquals=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent not equals to UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.notEquals=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent in DEFAULT_BASIC_PERCENT or UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.in=" + DEFAULT_BASIC_PERCENT + "," + UPDATED_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent equals to UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.in=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent is not null
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.specified=true");

        // Get all the monthlySalaryDtlList where basicPercent is null
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent is greater than or equal to DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.greaterThanOrEqual=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent is greater than or equal to UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.greaterThanOrEqual=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent is less than or equal to DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.lessThanOrEqual=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent is less than or equal to SMALLER_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.lessThanOrEqual=" + SMALLER_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent is less than DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.lessThan=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent is less than UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.lessThan=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent is greater than DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.greaterThan=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent is greater than SMALLER_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.greaterThan=" + SMALLER_BASIC_PERCENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent equals to DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.equals=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent equals to UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.equals=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent not equals to DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.notEquals=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent not equals to UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.notEquals=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent in DEFAULT_HOUSE_RENT or UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.in=" + DEFAULT_HOUSE_RENT + "," + UPDATED_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent equals to UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.in=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent is not null
        defaultMonthlySalaryDtlShouldBeFound("houseRent.specified=true");

        // Get all the monthlySalaryDtlList where houseRent is null
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent is greater than or equal to DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.greaterThanOrEqual=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent is greater than or equal to UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.greaterThanOrEqual=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent is less than or equal to DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.lessThanOrEqual=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent is less than or equal to SMALLER_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.lessThanOrEqual=" + SMALLER_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent is less than DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.lessThan=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent is less than UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.lessThan=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent is greater than DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.greaterThan=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent is greater than SMALLER_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.greaterThan=" + SMALLER_HOUSE_RENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent equals to DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.equals=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent equals to UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.equals=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent not equals to DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.notEquals=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent not equals to UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.notEquals=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent in DEFAULT_HOUSE_RENT_PERCENT or UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.in=" + DEFAULT_HOUSE_RENT_PERCENT + "," + UPDATED_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent equals to UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.in=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent is not null
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.specified=true");

        // Get all the monthlySalaryDtlList where houseRentPercent is null
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent is greater than or equal to DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.greaterThanOrEqual=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent is greater than or equal to UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.greaterThanOrEqual=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent is less than or equal to DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.lessThanOrEqual=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent is less than or equal to SMALLER_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.lessThanOrEqual=" + SMALLER_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent is less than DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.lessThan=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent is less than UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.lessThan=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent is greater than DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.greaterThan=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent is greater than SMALLER_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.greaterThan=" + SMALLER_HOUSE_RENT_PERCENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.equals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.equals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance not equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.notEquals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance not equals to UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.notEquals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance in DEFAULT_MEDICAL_ALLOWANCE or UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.in=" + DEFAULT_MEDICAL_ALLOWANCE + "," + UPDATED_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.in=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance is not null
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.specified=true");

        // Get all the monthlySalaryDtlList where medicalAllowance is null
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance is greater than or equal to DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.greaterThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance is greater than or equal to UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.greaterThanOrEqual=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance is less than or equal to DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.lessThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance is less than or equal to SMALLER_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.lessThanOrEqual=" + SMALLER_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance is less than DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.lessThan=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance is less than UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.lessThan=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance is greater than DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.greaterThan=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance is greater than SMALLER_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.greaterThan=" + SMALLER_MEDICAL_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent equals to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.equals=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.equals=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent not equals to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.notEquals=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent not equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.notEquals=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent in DEFAULT_MEDICAL_ALLOWANCE_PERCENT or UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.in=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT + "," + UPDATED_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.in=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is not null
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.specified=true");

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is null
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is greater than or equal to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.greaterThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is greater than or equal to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.greaterThanOrEqual=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is less than or equal to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.lessThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is less than or equal to SMALLER_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.lessThanOrEqual=" + SMALLER_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is less than DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.lessThan=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is less than UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.lessThan=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is greater than DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.greaterThan=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is greater than SMALLER_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.greaterThan=" + SMALLER_MEDICAL_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance equals to DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.equals=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance equals to UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.equals=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance not equals to DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.notEquals=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance not equals to UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.notEquals=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance in DEFAULT_CONVINCE_ALLOWANCE or UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.in=" + DEFAULT_CONVINCE_ALLOWANCE + "," + UPDATED_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance equals to UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.in=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance is not null
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.specified=true");

        // Get all the monthlySalaryDtlList where convinceAllowance is null
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance is greater than or equal to DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.greaterThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance is greater than or equal to UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.greaterThanOrEqual=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance is less than or equal to DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.lessThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance is less than or equal to SMALLER_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.lessThanOrEqual=" + SMALLER_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance is less than DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.lessThan=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance is less than UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.lessThan=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance is greater than DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.greaterThan=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance is greater than SMALLER_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.greaterThan=" + SMALLER_CONVINCE_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent equals to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.equals=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.equals=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent not equals to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.notEquals=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent not equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.notEquals=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent in DEFAULT_CONVINCE_ALLOWANCE_PERCENT or UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.in=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT + "," + UPDATED_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.in=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is not null
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.specified=true");

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is null
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is greater than or equal to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.greaterThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is greater than or equal to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.greaterThanOrEqual=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is less than or equal to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.lessThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is less than or equal to SMALLER_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.lessThanOrEqual=" + SMALLER_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is less than DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.lessThan=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is less than UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.lessThan=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is greater than DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.greaterThan=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is greater than SMALLER_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.greaterThan=" + SMALLER_CONVINCE_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance equals to DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.equals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.equals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance not equals to DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.notEquals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance not equals to UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.notEquals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance in DEFAULT_FOOD_ALLOWANCE or UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.in=" + DEFAULT_FOOD_ALLOWANCE + "," + UPDATED_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.in=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance is not null
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.specified=true");

        // Get all the monthlySalaryDtlList where foodAllowance is null
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance is greater than or equal to DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.greaterThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance is greater than or equal to UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.greaterThanOrEqual=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance is less than or equal to DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.lessThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance is less than or equal to SMALLER_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.lessThanOrEqual=" + SMALLER_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance is less than DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.lessThan=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance is less than UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.lessThan=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance is greater than DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.greaterThan=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance is greater than SMALLER_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.greaterThan=" + SMALLER_FOOD_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent equals to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.equals=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.equals=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent not equals to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.notEquals=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent not equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.notEquals=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent in DEFAULT_FOOD_ALLOWANCE_PERCENT or UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.in=" + DEFAULT_FOOD_ALLOWANCE_PERCENT + "," + UPDATED_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.in=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is not null
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.specified=true");

        // Get all the monthlySalaryDtlList where foodAllowancePercent is null
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is greater than or equal to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.greaterThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is greater than or equal to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.greaterThanOrEqual=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is less than or equal to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.lessThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is less than or equal to SMALLER_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.lessThanOrEqual=" + SMALLER_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is less than DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.lessThan=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is less than UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.lessThan=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is greater than DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.greaterThan=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is greater than SMALLER_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.greaterThan=" + SMALLER_FOOD_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine equals to DEFAULT_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.equals=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine equals to UPDATED_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.equals=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine not equals to DEFAULT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.notEquals=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine not equals to UPDATED_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.notEquals=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine in DEFAULT_FINE or UPDATED_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.in=" + DEFAULT_FINE + "," + UPDATED_FINE);

        // Get all the monthlySalaryDtlList where fine equals to UPDATED_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.in=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine is not null
        defaultMonthlySalaryDtlShouldBeFound("fine.specified=true");

        // Get all the monthlySalaryDtlList where fine is null
        defaultMonthlySalaryDtlShouldNotBeFound("fine.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine is greater than or equal to DEFAULT_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.greaterThanOrEqual=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine is greater than or equal to UPDATED_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.greaterThanOrEqual=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine is less than or equal to DEFAULT_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.lessThanOrEqual=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine is less than or equal to SMALLER_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.lessThanOrEqual=" + SMALLER_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine is less than DEFAULT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.lessThan=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine is less than UPDATED_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.lessThan=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine is greater than DEFAULT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.greaterThan=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine is greater than SMALLER_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.greaterThan=" + SMALLER_FINE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance equals to DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.equals=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance equals to UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.equals=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance not equals to DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.notEquals=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance not equals to UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.notEquals=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance in DEFAULT_ADVANCE or UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.in=" + DEFAULT_ADVANCE + "," + UPDATED_ADVANCE);

        // Get all the monthlySalaryDtlList where advance equals to UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.in=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance is not null
        defaultMonthlySalaryDtlShouldBeFound("advance.specified=true");

        // Get all the monthlySalaryDtlList where advance is null
        defaultMonthlySalaryDtlShouldNotBeFound("advance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance is greater than or equal to DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.greaterThanOrEqual=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance is greater than or equal to UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.greaterThanOrEqual=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance is less than or equal to DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.lessThanOrEqual=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance is less than or equal to SMALLER_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.lessThanOrEqual=" + SMALLER_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance is less than DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.lessThan=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance is less than UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.lessThan=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance is greater than DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.greaterThan=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance is greater than SMALLER_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.greaterThan=" + SMALLER_ADVANCE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where status equals to DEFAULT_STATUS
        defaultMonthlySalaryDtlShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the monthlySalaryDtlList where status equals to UPDATED_STATUS
        defaultMonthlySalaryDtlShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where status not equals to DEFAULT_STATUS
        defaultMonthlySalaryDtlShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the monthlySalaryDtlList where status not equals to UPDATED_STATUS
        defaultMonthlySalaryDtlShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMonthlySalaryDtlShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the monthlySalaryDtlList where status equals to UPDATED_STATUS
        defaultMonthlySalaryDtlShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where status is not null
        defaultMonthlySalaryDtlShouldBeFound("status.specified=true");

        // Get all the monthlySalaryDtlList where status is null
        defaultMonthlySalaryDtlShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedOn equals to DEFAULT_EXECUTED_ON
        defaultMonthlySalaryDtlShouldBeFound("executedOn.equals=" + DEFAULT_EXECUTED_ON);

        // Get all the monthlySalaryDtlList where executedOn equals to UPDATED_EXECUTED_ON
        defaultMonthlySalaryDtlShouldNotBeFound("executedOn.equals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedOn not equals to DEFAULT_EXECUTED_ON
        defaultMonthlySalaryDtlShouldNotBeFound("executedOn.notEquals=" + DEFAULT_EXECUTED_ON);

        // Get all the monthlySalaryDtlList where executedOn not equals to UPDATED_EXECUTED_ON
        defaultMonthlySalaryDtlShouldBeFound("executedOn.notEquals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedOnIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedOn in DEFAULT_EXECUTED_ON or UPDATED_EXECUTED_ON
        defaultMonthlySalaryDtlShouldBeFound("executedOn.in=" + DEFAULT_EXECUTED_ON + "," + UPDATED_EXECUTED_ON);

        // Get all the monthlySalaryDtlList where executedOn equals to UPDATED_EXECUTED_ON
        defaultMonthlySalaryDtlShouldNotBeFound("executedOn.in=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedOn is not null
        defaultMonthlySalaryDtlShouldBeFound("executedOn.specified=true");

        // Get all the monthlySalaryDtlList where executedOn is null
        defaultMonthlySalaryDtlShouldNotBeFound("executedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedByIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedBy equals to DEFAULT_EXECUTED_BY
        defaultMonthlySalaryDtlShouldBeFound("executedBy.equals=" + DEFAULT_EXECUTED_BY);

        // Get all the monthlySalaryDtlList where executedBy equals to UPDATED_EXECUTED_BY
        defaultMonthlySalaryDtlShouldNotBeFound("executedBy.equals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedBy not equals to DEFAULT_EXECUTED_BY
        defaultMonthlySalaryDtlShouldNotBeFound("executedBy.notEquals=" + DEFAULT_EXECUTED_BY);

        // Get all the monthlySalaryDtlList where executedBy not equals to UPDATED_EXECUTED_BY
        defaultMonthlySalaryDtlShouldBeFound("executedBy.notEquals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedByIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedBy in DEFAULT_EXECUTED_BY or UPDATED_EXECUTED_BY
        defaultMonthlySalaryDtlShouldBeFound("executedBy.in=" + DEFAULT_EXECUTED_BY + "," + UPDATED_EXECUTED_BY);

        // Get all the monthlySalaryDtlList where executedBy equals to UPDATED_EXECUTED_BY
        defaultMonthlySalaryDtlShouldNotBeFound("executedBy.in=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedBy is not null
        defaultMonthlySalaryDtlShouldBeFound("executedBy.specified=true");

        // Get all the monthlySalaryDtlList where executedBy is null
        defaultMonthlySalaryDtlShouldNotBeFound("executedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMonthlySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);
        MonthlySalary monthlySalary = MonthlySalaryResourceIT.createEntity(em);
        em.persist(monthlySalary);
        em.flush();
        monthlySalaryDtl.setMonthlySalary(monthlySalary);
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);
        Long monthlySalaryId = monthlySalary.getId();

        // Get all the monthlySalaryDtlList where monthlySalary equals to monthlySalaryId
        defaultMonthlySalaryDtlShouldBeFound("monthlySalaryId.equals=" + monthlySalaryId);

        // Get all the monthlySalaryDtlList where monthlySalary equals to monthlySalaryId + 1
        defaultMonthlySalaryDtlShouldNotBeFound("monthlySalaryId.equals=" + (monthlySalaryId + 1));
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        monthlySalaryDtl.setEmployee(employee);
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);
        Long employeeId = employee.getId();

        // Get all the monthlySalaryDtlList where employee equals to employeeId
        defaultMonthlySalaryDtlShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the monthlySalaryDtlList where employee equals to employeeId + 1
        defaultMonthlySalaryDtlShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMonthlySalaryDtlShouldBeFound(String filter) throws Exception {
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlySalaryDtl.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));

        // Check, that the count call also returns 1
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMonthlySalaryDtlShouldNotBeFound(String filter) throws Exception {
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMonthlySalaryDtl() throws Exception {
        // Get the monthlySalaryDtl
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonthlySalaryDtl() throws Exception {
        // Initialize the database
        monthlySalaryDtlService.save(monthlySalaryDtl);

        int databaseSizeBeforeUpdate = monthlySalaryDtlRepository.findAll().size();

        // Update the monthlySalaryDtl
        MonthlySalaryDtl updatedMonthlySalaryDtl = monthlySalaryDtlRepository.findById(monthlySalaryDtl.getId()).get();
        // Disconnect from session so that the updates on updatedMonthlySalaryDtl are not directly saved in db
        em.detach(updatedMonthlySalaryDtl);
        updatedMonthlySalaryDtl
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

        restMonthlySalaryDtlMockMvc.perform(put("/api/monthly-salary-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonthlySalaryDtl)))
            .andExpect(status().isOk());

        // Validate the MonthlySalaryDtl in the database
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAll();
        assertThat(monthlySalaryDtlList).hasSize(databaseSizeBeforeUpdate);
        MonthlySalaryDtl testMonthlySalaryDtl = monthlySalaryDtlList.get(monthlySalaryDtlList.size() - 1);
        assertThat(testMonthlySalaryDtl.getGross()).isEqualTo(UPDATED_GROSS);
        assertThat(testMonthlySalaryDtl.getBasic()).isEqualTo(UPDATED_BASIC);
        assertThat(testMonthlySalaryDtl.getBasicPercent()).isEqualTo(UPDATED_BASIC_PERCENT);
        assertThat(testMonthlySalaryDtl.getHouseRent()).isEqualTo(UPDATED_HOUSE_RENT);
        assertThat(testMonthlySalaryDtl.getHouseRentPercent()).isEqualTo(UPDATED_HOUSE_RENT_PERCENT);
        assertThat(testMonthlySalaryDtl.getMedicalAllowance()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getMedicalAllowancePercent()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getConvinceAllowance()).isEqualTo(UPDATED_CONVINCE_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getConvinceAllowancePercent()).isEqualTo(UPDATED_CONVINCE_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getFoodAllowance()).isEqualTo(UPDATED_FOOD_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getFoodAllowancePercent()).isEqualTo(UPDATED_FOOD_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getFine()).isEqualTo(UPDATED_FINE);
        assertThat(testMonthlySalaryDtl.getAdvance()).isEqualTo(UPDATED_ADVANCE);
        assertThat(testMonthlySalaryDtl.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMonthlySalaryDtl.getExecutedOn()).isEqualTo(UPDATED_EXECUTED_ON);
        assertThat(testMonthlySalaryDtl.getExecutedBy()).isEqualTo(UPDATED_EXECUTED_BY);
        assertThat(testMonthlySalaryDtl.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingMonthlySalaryDtl() throws Exception {
        int databaseSizeBeforeUpdate = monthlySalaryDtlRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlySalaryDtlMockMvc.perform(put("/api/monthly-salary-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDtl)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlySalaryDtl in the database
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAll();
        assertThat(monthlySalaryDtlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonthlySalaryDtl() throws Exception {
        // Initialize the database
        monthlySalaryDtlService.save(monthlySalaryDtl);

        int databaseSizeBeforeDelete = monthlySalaryDtlRepository.findAll().size();

        // Delete the monthlySalaryDtl
        restMonthlySalaryDtlMockMvc.perform(delete("/api/monthly-salary-dtls/{id}", monthlySalaryDtl.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAll();
        assertThat(monthlySalaryDtlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
