package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Advance;
import software.cstl.domain.AdvancePaymentHistory;
import software.cstl.domain.Employee;
import software.cstl.repository.AdvanceRepository;
import software.cstl.service.AdvanceService;
import software.cstl.service.dto.AdvanceCriteria;
import software.cstl.service.AdvanceQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.PaymentStatus;
/**
 * Integration tests for the {@link AdvanceResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdvanceResourceIT {

    private static final LocalDate DEFAULT_PROVIDED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROVIDED_ON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PROVIDED_ON = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PAYMENT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PAYMENT_PERCENTAGE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MONTHLY_PAYMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTHLY_PAYMENT_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_MONTHLY_PAYMENT_AMOUNT = new BigDecimal(1 - 1);

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.NOT_PAID;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.IN_PROGRESS;

    private static final BigDecimal DEFAULT_AMOUNT_PAID = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_PAID = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_PAID = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AMOUNT_LEFT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_LEFT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_LEFT = new BigDecimal(1 - 1);

    @Autowired
    private AdvanceRepository advanceRepository;

    @Autowired
    private AdvanceService advanceService;

    @Autowired
    private AdvanceQueryService advanceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdvanceMockMvc;

    private Advance advance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Advance createEntity(EntityManager em) {
        Advance advance = new Advance()
            .providedOn(DEFAULT_PROVIDED_ON)
            .reason(DEFAULT_REASON)
            .amount(DEFAULT_AMOUNT)
            .paymentPercentage(DEFAULT_PAYMENT_PERCENTAGE)
            .monthlyPaymentAmount(DEFAULT_MONTHLY_PAYMENT_AMOUNT)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .amountPaid(DEFAULT_AMOUNT_PAID)
            .amountLeft(DEFAULT_AMOUNT_LEFT);
        return advance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Advance createUpdatedEntity(EntityManager em) {
        Advance advance = new Advance()
            .providedOn(UPDATED_PROVIDED_ON)
            .reason(UPDATED_REASON)
            .amount(UPDATED_AMOUNT)
            .paymentPercentage(UPDATED_PAYMENT_PERCENTAGE)
            .monthlyPaymentAmount(UPDATED_MONTHLY_PAYMENT_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .amountPaid(UPDATED_AMOUNT_PAID)
            .amountLeft(UPDATED_AMOUNT_LEFT);
        return advance;
    }

    @BeforeEach
    public void initTest() {
        advance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdvance() throws Exception {
        int databaseSizeBeforeCreate = advanceRepository.findAll().size();
        // Create the Advance
        restAdvanceMockMvc.perform(post("/api/advances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advance)))
            .andExpect(status().isCreated());

        // Validate the Advance in the database
        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeCreate + 1);
        Advance testAdvance = advanceList.get(advanceList.size() - 1);
        assertThat(testAdvance.getProvidedOn()).isEqualTo(DEFAULT_PROVIDED_ON);
        assertThat(testAdvance.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testAdvance.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAdvance.getPaymentPercentage()).isEqualTo(DEFAULT_PAYMENT_PERCENTAGE);
        assertThat(testAdvance.getMonthlyPaymentAmount()).isEqualTo(DEFAULT_MONTHLY_PAYMENT_AMOUNT);
        assertThat(testAdvance.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testAdvance.getAmountPaid()).isEqualTo(DEFAULT_AMOUNT_PAID);
        assertThat(testAdvance.getAmountLeft()).isEqualTo(DEFAULT_AMOUNT_LEFT);
    }

    @Test
    @Transactional
    public void createAdvanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = advanceRepository.findAll().size();

        // Create the Advance with an existing ID
        advance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdvanceMockMvc.perform(post("/api/advances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advance)))
            .andExpect(status().isBadRequest());

        // Validate the Advance in the database
        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProvidedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = advanceRepository.findAll().size();
        // set the field null
        advance.setProvidedOn(null);

        // Create the Advance, which fails.


        restAdvanceMockMvc.perform(post("/api/advances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advance)))
            .andExpect(status().isBadRequest());

        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = advanceRepository.findAll().size();
        // set the field null
        advance.setAmount(null);

        // Create the Advance, which fails.


        restAdvanceMockMvc.perform(post("/api/advances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advance)))
            .andExpect(status().isBadRequest());

        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = advanceRepository.findAll().size();
        // set the field null
        advance.setPaymentPercentage(null);

        // Create the Advance, which fails.


        restAdvanceMockMvc.perform(post("/api/advances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advance)))
            .andExpect(status().isBadRequest());

        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthlyPaymentAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = advanceRepository.findAll().size();
        // set the field null
        advance.setMonthlyPaymentAmount(null);

        // Create the Advance, which fails.


        restAdvanceMockMvc.perform(post("/api/advances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advance)))
            .andExpect(status().isBadRequest());

        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdvances() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList
        restAdvanceMockMvc.perform(get("/api/advances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advance.getId().intValue())))
            .andExpect(jsonPath("$.[*].providedOn").value(hasItem(DEFAULT_PROVIDED_ON.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentPercentage").value(hasItem(DEFAULT_PAYMENT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].monthlyPaymentAmount").value(hasItem(DEFAULT_MONTHLY_PAYMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].amountPaid").value(hasItem(DEFAULT_AMOUNT_PAID.intValue())))
            .andExpect(jsonPath("$.[*].amountLeft").value(hasItem(DEFAULT_AMOUNT_LEFT.intValue())));
    }
    
    @Test
    @Transactional
    public void getAdvance() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get the advance
        restAdvanceMockMvc.perform(get("/api/advances/{id}", advance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(advance.getId().intValue()))
            .andExpect(jsonPath("$.providedOn").value(DEFAULT_PROVIDED_ON.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentPercentage").value(DEFAULT_PAYMENT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.monthlyPaymentAmount").value(DEFAULT_MONTHLY_PAYMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.amountPaid").value(DEFAULT_AMOUNT_PAID.intValue()))
            .andExpect(jsonPath("$.amountLeft").value(DEFAULT_AMOUNT_LEFT.intValue()));
    }


    @Test
    @Transactional
    public void getAdvancesByIdFiltering() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        Long id = advance.getId();

        defaultAdvanceShouldBeFound("id.equals=" + id);
        defaultAdvanceShouldNotBeFound("id.notEquals=" + id);

        defaultAdvanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdvanceShouldNotBeFound("id.greaterThan=" + id);

        defaultAdvanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdvanceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn equals to DEFAULT_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.equals=" + DEFAULT_PROVIDED_ON);

        // Get all the advanceList where providedOn equals to UPDATED_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.equals=" + UPDATED_PROVIDED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn not equals to DEFAULT_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.notEquals=" + DEFAULT_PROVIDED_ON);

        // Get all the advanceList where providedOn not equals to UPDATED_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.notEquals=" + UPDATED_PROVIDED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn in DEFAULT_PROVIDED_ON or UPDATED_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.in=" + DEFAULT_PROVIDED_ON + "," + UPDATED_PROVIDED_ON);

        // Get all the advanceList where providedOn equals to UPDATED_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.in=" + UPDATED_PROVIDED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn is not null
        defaultAdvanceShouldBeFound("providedOn.specified=true");

        // Get all the advanceList where providedOn is null
        defaultAdvanceShouldNotBeFound("providedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn is greater than or equal to DEFAULT_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.greaterThanOrEqual=" + DEFAULT_PROVIDED_ON);

        // Get all the advanceList where providedOn is greater than or equal to UPDATED_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.greaterThanOrEqual=" + UPDATED_PROVIDED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn is less than or equal to DEFAULT_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.lessThanOrEqual=" + DEFAULT_PROVIDED_ON);

        // Get all the advanceList where providedOn is less than or equal to SMALLER_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.lessThanOrEqual=" + SMALLER_PROVIDED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn is less than DEFAULT_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.lessThan=" + DEFAULT_PROVIDED_ON);

        // Get all the advanceList where providedOn is less than UPDATED_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.lessThan=" + UPDATED_PROVIDED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn is greater than DEFAULT_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.greaterThan=" + DEFAULT_PROVIDED_ON);

        // Get all the advanceList where providedOn is greater than SMALLER_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.greaterThan=" + SMALLER_PROVIDED_ON);
    }


    @Test
    @Transactional
    public void getAllAdvancesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount equals to DEFAULT_AMOUNT
        defaultAdvanceShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the advanceList where amount equals to UPDATED_AMOUNT
        defaultAdvanceShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount not equals to DEFAULT_AMOUNT
        defaultAdvanceShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the advanceList where amount not equals to UPDATED_AMOUNT
        defaultAdvanceShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultAdvanceShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the advanceList where amount equals to UPDATED_AMOUNT
        defaultAdvanceShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount is not null
        defaultAdvanceShouldBeFound("amount.specified=true");

        // Get all the advanceList where amount is null
        defaultAdvanceShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultAdvanceShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the advanceList where amount is greater than or equal to UPDATED_AMOUNT
        defaultAdvanceShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount is less than or equal to DEFAULT_AMOUNT
        defaultAdvanceShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the advanceList where amount is less than or equal to SMALLER_AMOUNT
        defaultAdvanceShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount is less than DEFAULT_AMOUNT
        defaultAdvanceShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the advanceList where amount is less than UPDATED_AMOUNT
        defaultAdvanceShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount is greater than DEFAULT_AMOUNT
        defaultAdvanceShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the advanceList where amount is greater than SMALLER_AMOUNT
        defaultAdvanceShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllAdvancesByPaymentPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentPercentage equals to DEFAULT_PAYMENT_PERCENTAGE
        defaultAdvanceShouldBeFound("paymentPercentage.equals=" + DEFAULT_PAYMENT_PERCENTAGE);

        // Get all the advanceList where paymentPercentage equals to UPDATED_PAYMENT_PERCENTAGE
        defaultAdvanceShouldNotBeFound("paymentPercentage.equals=" + UPDATED_PAYMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentPercentage not equals to DEFAULT_PAYMENT_PERCENTAGE
        defaultAdvanceShouldNotBeFound("paymentPercentage.notEquals=" + DEFAULT_PAYMENT_PERCENTAGE);

        // Get all the advanceList where paymentPercentage not equals to UPDATED_PAYMENT_PERCENTAGE
        defaultAdvanceShouldBeFound("paymentPercentage.notEquals=" + UPDATED_PAYMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentPercentage in DEFAULT_PAYMENT_PERCENTAGE or UPDATED_PAYMENT_PERCENTAGE
        defaultAdvanceShouldBeFound("paymentPercentage.in=" + DEFAULT_PAYMENT_PERCENTAGE + "," + UPDATED_PAYMENT_PERCENTAGE);

        // Get all the advanceList where paymentPercentage equals to UPDATED_PAYMENT_PERCENTAGE
        defaultAdvanceShouldNotBeFound("paymentPercentage.in=" + UPDATED_PAYMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentPercentage is not null
        defaultAdvanceShouldBeFound("paymentPercentage.specified=true");

        // Get all the advanceList where paymentPercentage is null
        defaultAdvanceShouldNotBeFound("paymentPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentPercentage is greater than or equal to DEFAULT_PAYMENT_PERCENTAGE
        defaultAdvanceShouldBeFound("paymentPercentage.greaterThanOrEqual=" + DEFAULT_PAYMENT_PERCENTAGE);

        // Get all the advanceList where paymentPercentage is greater than or equal to UPDATED_PAYMENT_PERCENTAGE
        defaultAdvanceShouldNotBeFound("paymentPercentage.greaterThanOrEqual=" + UPDATED_PAYMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentPercentage is less than or equal to DEFAULT_PAYMENT_PERCENTAGE
        defaultAdvanceShouldBeFound("paymentPercentage.lessThanOrEqual=" + DEFAULT_PAYMENT_PERCENTAGE);

        // Get all the advanceList where paymentPercentage is less than or equal to SMALLER_PAYMENT_PERCENTAGE
        defaultAdvanceShouldNotBeFound("paymentPercentage.lessThanOrEqual=" + SMALLER_PAYMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentPercentage is less than DEFAULT_PAYMENT_PERCENTAGE
        defaultAdvanceShouldNotBeFound("paymentPercentage.lessThan=" + DEFAULT_PAYMENT_PERCENTAGE);

        // Get all the advanceList where paymentPercentage is less than UPDATED_PAYMENT_PERCENTAGE
        defaultAdvanceShouldBeFound("paymentPercentage.lessThan=" + UPDATED_PAYMENT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentPercentage is greater than DEFAULT_PAYMENT_PERCENTAGE
        defaultAdvanceShouldNotBeFound("paymentPercentage.greaterThan=" + DEFAULT_PAYMENT_PERCENTAGE);

        // Get all the advanceList where paymentPercentage is greater than SMALLER_PAYMENT_PERCENTAGE
        defaultAdvanceShouldBeFound("paymentPercentage.greaterThan=" + SMALLER_PAYMENT_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPaymentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPaymentAmount equals to DEFAULT_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldBeFound("monthlyPaymentAmount.equals=" + DEFAULT_MONTHLY_PAYMENT_AMOUNT);

        // Get all the advanceList where monthlyPaymentAmount equals to UPDATED_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldNotBeFound("monthlyPaymentAmount.equals=" + UPDATED_MONTHLY_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPaymentAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPaymentAmount not equals to DEFAULT_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldNotBeFound("monthlyPaymentAmount.notEquals=" + DEFAULT_MONTHLY_PAYMENT_AMOUNT);

        // Get all the advanceList where monthlyPaymentAmount not equals to UPDATED_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldBeFound("monthlyPaymentAmount.notEquals=" + UPDATED_MONTHLY_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPaymentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPaymentAmount in DEFAULT_MONTHLY_PAYMENT_AMOUNT or UPDATED_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldBeFound("monthlyPaymentAmount.in=" + DEFAULT_MONTHLY_PAYMENT_AMOUNT + "," + UPDATED_MONTHLY_PAYMENT_AMOUNT);

        // Get all the advanceList where monthlyPaymentAmount equals to UPDATED_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldNotBeFound("monthlyPaymentAmount.in=" + UPDATED_MONTHLY_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPaymentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPaymentAmount is not null
        defaultAdvanceShouldBeFound("monthlyPaymentAmount.specified=true");

        // Get all the advanceList where monthlyPaymentAmount is null
        defaultAdvanceShouldNotBeFound("monthlyPaymentAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPaymentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPaymentAmount is greater than or equal to DEFAULT_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldBeFound("monthlyPaymentAmount.greaterThanOrEqual=" + DEFAULT_MONTHLY_PAYMENT_AMOUNT);

        // Get all the advanceList where monthlyPaymentAmount is greater than or equal to UPDATED_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldNotBeFound("monthlyPaymentAmount.greaterThanOrEqual=" + UPDATED_MONTHLY_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPaymentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPaymentAmount is less than or equal to DEFAULT_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldBeFound("monthlyPaymentAmount.lessThanOrEqual=" + DEFAULT_MONTHLY_PAYMENT_AMOUNT);

        // Get all the advanceList where monthlyPaymentAmount is less than or equal to SMALLER_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldNotBeFound("monthlyPaymentAmount.lessThanOrEqual=" + SMALLER_MONTHLY_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPaymentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPaymentAmount is less than DEFAULT_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldNotBeFound("monthlyPaymentAmount.lessThan=" + DEFAULT_MONTHLY_PAYMENT_AMOUNT);

        // Get all the advanceList where monthlyPaymentAmount is less than UPDATED_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldBeFound("monthlyPaymentAmount.lessThan=" + UPDATED_MONTHLY_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPaymentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPaymentAmount is greater than DEFAULT_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldNotBeFound("monthlyPaymentAmount.greaterThan=" + DEFAULT_MONTHLY_PAYMENT_AMOUNT);

        // Get all the advanceList where monthlyPaymentAmount is greater than SMALLER_MONTHLY_PAYMENT_AMOUNT
        defaultAdvanceShouldBeFound("monthlyPaymentAmount.greaterThan=" + SMALLER_MONTHLY_PAYMENT_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllAdvancesByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultAdvanceShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the advanceList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultAdvanceShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentStatus not equals to DEFAULT_PAYMENT_STATUS
        defaultAdvanceShouldNotBeFound("paymentStatus.notEquals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the advanceList where paymentStatus not equals to UPDATED_PAYMENT_STATUS
        defaultAdvanceShouldBeFound("paymentStatus.notEquals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultAdvanceShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the advanceList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultAdvanceShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentStatus is not null
        defaultAdvanceShouldBeFound("paymentStatus.specified=true");

        // Get all the advanceList where paymentStatus is null
        defaultAdvanceShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountPaidIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountPaid equals to DEFAULT_AMOUNT_PAID
        defaultAdvanceShouldBeFound("amountPaid.equals=" + DEFAULT_AMOUNT_PAID);

        // Get all the advanceList where amountPaid equals to UPDATED_AMOUNT_PAID
        defaultAdvanceShouldNotBeFound("amountPaid.equals=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountPaidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountPaid not equals to DEFAULT_AMOUNT_PAID
        defaultAdvanceShouldNotBeFound("amountPaid.notEquals=" + DEFAULT_AMOUNT_PAID);

        // Get all the advanceList where amountPaid not equals to UPDATED_AMOUNT_PAID
        defaultAdvanceShouldBeFound("amountPaid.notEquals=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountPaidIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountPaid in DEFAULT_AMOUNT_PAID or UPDATED_AMOUNT_PAID
        defaultAdvanceShouldBeFound("amountPaid.in=" + DEFAULT_AMOUNT_PAID + "," + UPDATED_AMOUNT_PAID);

        // Get all the advanceList where amountPaid equals to UPDATED_AMOUNT_PAID
        defaultAdvanceShouldNotBeFound("amountPaid.in=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountPaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountPaid is not null
        defaultAdvanceShouldBeFound("amountPaid.specified=true");

        // Get all the advanceList where amountPaid is null
        defaultAdvanceShouldNotBeFound("amountPaid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountPaidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountPaid is greater than or equal to DEFAULT_AMOUNT_PAID
        defaultAdvanceShouldBeFound("amountPaid.greaterThanOrEqual=" + DEFAULT_AMOUNT_PAID);

        // Get all the advanceList where amountPaid is greater than or equal to UPDATED_AMOUNT_PAID
        defaultAdvanceShouldNotBeFound("amountPaid.greaterThanOrEqual=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountPaidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountPaid is less than or equal to DEFAULT_AMOUNT_PAID
        defaultAdvanceShouldBeFound("amountPaid.lessThanOrEqual=" + DEFAULT_AMOUNT_PAID);

        // Get all the advanceList where amountPaid is less than or equal to SMALLER_AMOUNT_PAID
        defaultAdvanceShouldNotBeFound("amountPaid.lessThanOrEqual=" + SMALLER_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountPaidIsLessThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountPaid is less than DEFAULT_AMOUNT_PAID
        defaultAdvanceShouldNotBeFound("amountPaid.lessThan=" + DEFAULT_AMOUNT_PAID);

        // Get all the advanceList where amountPaid is less than UPDATED_AMOUNT_PAID
        defaultAdvanceShouldBeFound("amountPaid.lessThan=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountPaidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountPaid is greater than DEFAULT_AMOUNT_PAID
        defaultAdvanceShouldNotBeFound("amountPaid.greaterThan=" + DEFAULT_AMOUNT_PAID);

        // Get all the advanceList where amountPaid is greater than SMALLER_AMOUNT_PAID
        defaultAdvanceShouldBeFound("amountPaid.greaterThan=" + SMALLER_AMOUNT_PAID);
    }


    @Test
    @Transactional
    public void getAllAdvancesByAmountLeftIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountLeft equals to DEFAULT_AMOUNT_LEFT
        defaultAdvanceShouldBeFound("amountLeft.equals=" + DEFAULT_AMOUNT_LEFT);

        // Get all the advanceList where amountLeft equals to UPDATED_AMOUNT_LEFT
        defaultAdvanceShouldNotBeFound("amountLeft.equals=" + UPDATED_AMOUNT_LEFT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountLeftIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountLeft not equals to DEFAULT_AMOUNT_LEFT
        defaultAdvanceShouldNotBeFound("amountLeft.notEquals=" + DEFAULT_AMOUNT_LEFT);

        // Get all the advanceList where amountLeft not equals to UPDATED_AMOUNT_LEFT
        defaultAdvanceShouldBeFound("amountLeft.notEquals=" + UPDATED_AMOUNT_LEFT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountLeftIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountLeft in DEFAULT_AMOUNT_LEFT or UPDATED_AMOUNT_LEFT
        defaultAdvanceShouldBeFound("amountLeft.in=" + DEFAULT_AMOUNT_LEFT + "," + UPDATED_AMOUNT_LEFT);

        // Get all the advanceList where amountLeft equals to UPDATED_AMOUNT_LEFT
        defaultAdvanceShouldNotBeFound("amountLeft.in=" + UPDATED_AMOUNT_LEFT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountLeftIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountLeft is not null
        defaultAdvanceShouldBeFound("amountLeft.specified=true");

        // Get all the advanceList where amountLeft is null
        defaultAdvanceShouldNotBeFound("amountLeft.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountLeftIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountLeft is greater than or equal to DEFAULT_AMOUNT_LEFT
        defaultAdvanceShouldBeFound("amountLeft.greaterThanOrEqual=" + DEFAULT_AMOUNT_LEFT);

        // Get all the advanceList where amountLeft is greater than or equal to UPDATED_AMOUNT_LEFT
        defaultAdvanceShouldNotBeFound("amountLeft.greaterThanOrEqual=" + UPDATED_AMOUNT_LEFT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountLeftIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountLeft is less than or equal to DEFAULT_AMOUNT_LEFT
        defaultAdvanceShouldBeFound("amountLeft.lessThanOrEqual=" + DEFAULT_AMOUNT_LEFT);

        // Get all the advanceList where amountLeft is less than or equal to SMALLER_AMOUNT_LEFT
        defaultAdvanceShouldNotBeFound("amountLeft.lessThanOrEqual=" + SMALLER_AMOUNT_LEFT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountLeftIsLessThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountLeft is less than DEFAULT_AMOUNT_LEFT
        defaultAdvanceShouldNotBeFound("amountLeft.lessThan=" + DEFAULT_AMOUNT_LEFT);

        // Get all the advanceList where amountLeft is less than UPDATED_AMOUNT_LEFT
        defaultAdvanceShouldBeFound("amountLeft.lessThan=" + UPDATED_AMOUNT_LEFT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountLeftIsGreaterThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amountLeft is greater than DEFAULT_AMOUNT_LEFT
        defaultAdvanceShouldNotBeFound("amountLeft.greaterThan=" + DEFAULT_AMOUNT_LEFT);

        // Get all the advanceList where amountLeft is greater than SMALLER_AMOUNT_LEFT
        defaultAdvanceShouldBeFound("amountLeft.greaterThan=" + SMALLER_AMOUNT_LEFT);
    }


    @Test
    @Transactional
    public void getAllAdvancesByAdvancePaymentHistoryIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);
        AdvancePaymentHistory advancePaymentHistory = AdvancePaymentHistoryResourceIT.createEntity(em);
        em.persist(advancePaymentHistory);
        em.flush();
        advance.addAdvancePaymentHistory(advancePaymentHistory);
        advanceRepository.saveAndFlush(advance);
        Long advancePaymentHistoryId = advancePaymentHistory.getId();

        // Get all the advanceList where advancePaymentHistory equals to advancePaymentHistoryId
        defaultAdvanceShouldBeFound("advancePaymentHistoryId.equals=" + advancePaymentHistoryId);

        // Get all the advanceList where advancePaymentHistory equals to advancePaymentHistoryId + 1
        defaultAdvanceShouldNotBeFound("advancePaymentHistoryId.equals=" + (advancePaymentHistoryId + 1));
    }


    @Test
    @Transactional
    public void getAllAdvancesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        advance.setEmployee(employee);
        advanceRepository.saveAndFlush(advance);
        Long employeeId = employee.getId();

        // Get all the advanceList where employee equals to employeeId
        defaultAdvanceShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the advanceList where employee equals to employeeId + 1
        defaultAdvanceShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdvanceShouldBeFound(String filter) throws Exception {
        restAdvanceMockMvc.perform(get("/api/advances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advance.getId().intValue())))
            .andExpect(jsonPath("$.[*].providedOn").value(hasItem(DEFAULT_PROVIDED_ON.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentPercentage").value(hasItem(DEFAULT_PAYMENT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].monthlyPaymentAmount").value(hasItem(DEFAULT_MONTHLY_PAYMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].amountPaid").value(hasItem(DEFAULT_AMOUNT_PAID.intValue())))
            .andExpect(jsonPath("$.[*].amountLeft").value(hasItem(DEFAULT_AMOUNT_LEFT.intValue())));

        // Check, that the count call also returns 1
        restAdvanceMockMvc.perform(get("/api/advances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdvanceShouldNotBeFound(String filter) throws Exception {
        restAdvanceMockMvc.perform(get("/api/advances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdvanceMockMvc.perform(get("/api/advances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAdvance() throws Exception {
        // Get the advance
        restAdvanceMockMvc.perform(get("/api/advances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdvance() throws Exception {
        // Initialize the database
        advanceService.save(advance);

        int databaseSizeBeforeUpdate = advanceRepository.findAll().size();

        // Update the advance
        Advance updatedAdvance = advanceRepository.findById(advance.getId()).get();
        // Disconnect from session so that the updates on updatedAdvance are not directly saved in db
        em.detach(updatedAdvance);
        updatedAdvance
            .providedOn(UPDATED_PROVIDED_ON)
            .reason(UPDATED_REASON)
            .amount(UPDATED_AMOUNT)
            .paymentPercentage(UPDATED_PAYMENT_PERCENTAGE)
            .monthlyPaymentAmount(UPDATED_MONTHLY_PAYMENT_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .amountPaid(UPDATED_AMOUNT_PAID)
            .amountLeft(UPDATED_AMOUNT_LEFT);

        restAdvanceMockMvc.perform(put("/api/advances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdvance)))
            .andExpect(status().isOk());

        // Validate the Advance in the database
        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeUpdate);
        Advance testAdvance = advanceList.get(advanceList.size() - 1);
        assertThat(testAdvance.getProvidedOn()).isEqualTo(UPDATED_PROVIDED_ON);
        assertThat(testAdvance.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testAdvance.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAdvance.getPaymentPercentage()).isEqualTo(UPDATED_PAYMENT_PERCENTAGE);
        assertThat(testAdvance.getMonthlyPaymentAmount()).isEqualTo(UPDATED_MONTHLY_PAYMENT_AMOUNT);
        assertThat(testAdvance.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testAdvance.getAmountPaid()).isEqualTo(UPDATED_AMOUNT_PAID);
        assertThat(testAdvance.getAmountLeft()).isEqualTo(UPDATED_AMOUNT_LEFT);
    }

    @Test
    @Transactional
    public void updateNonExistingAdvance() throws Exception {
        int databaseSizeBeforeUpdate = advanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdvanceMockMvc.perform(put("/api/advances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advance)))
            .andExpect(status().isBadRequest());

        // Validate the Advance in the database
        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdvance() throws Exception {
        // Initialize the database
        advanceService.save(advance);

        int databaseSizeBeforeDelete = advanceRepository.findAll().size();

        // Delete the advance
        restAdvanceMockMvc.perform(delete("/api/advances/{id}", advance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
