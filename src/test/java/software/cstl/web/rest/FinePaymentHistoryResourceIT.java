package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.FinePaymentHistory;
import software.cstl.domain.Fine;
import software.cstl.repository.FinePaymentHistoryRepository;
import software.cstl.service.FinePaymentHistoryService;
import software.cstl.service.dto.FinePaymentHistoryCriteria;
import software.cstl.service.FinePaymentHistoryQueryService;

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

import software.cstl.domain.enumeration.MonthType;
/**
 * Integration tests for the {@link FinePaymentHistoryResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FinePaymentHistoryResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final MonthType DEFAULT_MONTH_TYPE = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH_TYPE = MonthType.FEBRUARY;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_BEFORE_FINE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEFORE_FINE = new BigDecimal(2);
    private static final BigDecimal SMALLER_BEFORE_FINE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AFTER_FINE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AFTER_FINE = new BigDecimal(2);
    private static final BigDecimal SMALLER_AFTER_FINE = new BigDecimal(1 - 1);

    @Autowired
    private FinePaymentHistoryRepository finePaymentHistoryRepository;

    @Autowired
    private FinePaymentHistoryService finePaymentHistoryService;

    @Autowired
    private FinePaymentHistoryQueryService finePaymentHistoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFinePaymentHistoryMockMvc;

    private FinePaymentHistory finePaymentHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinePaymentHistory createEntity(EntityManager em) {
        FinePaymentHistory finePaymentHistory = new FinePaymentHistory()
            .year(DEFAULT_YEAR)
            .monthType(DEFAULT_MONTH_TYPE)
            .amount(DEFAULT_AMOUNT)
            .beforeFine(DEFAULT_BEFORE_FINE)
            .afterFine(DEFAULT_AFTER_FINE);
        return finePaymentHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinePaymentHistory createUpdatedEntity(EntityManager em) {
        FinePaymentHistory finePaymentHistory = new FinePaymentHistory()
            .year(UPDATED_YEAR)
            .monthType(UPDATED_MONTH_TYPE)
            .amount(UPDATED_AMOUNT)
            .beforeFine(UPDATED_BEFORE_FINE)
            .afterFine(UPDATED_AFTER_FINE);
        return finePaymentHistory;
    }

    @BeforeEach
    public void initTest() {
        finePaymentHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinePaymentHistory() throws Exception {
        int databaseSizeBeforeCreate = finePaymentHistoryRepository.findAll().size();
        // Create the FinePaymentHistory
        restFinePaymentHistoryMockMvc.perform(post("/api/fine-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finePaymentHistory)))
            .andExpect(status().isCreated());

        // Validate the FinePaymentHistory in the database
        List<FinePaymentHistory> finePaymentHistoryList = finePaymentHistoryRepository.findAll();
        assertThat(finePaymentHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        FinePaymentHistory testFinePaymentHistory = finePaymentHistoryList.get(finePaymentHistoryList.size() - 1);
        assertThat(testFinePaymentHistory.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testFinePaymentHistory.getMonthType()).isEqualTo(DEFAULT_MONTH_TYPE);
        assertThat(testFinePaymentHistory.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testFinePaymentHistory.getBeforeFine()).isEqualTo(DEFAULT_BEFORE_FINE);
        assertThat(testFinePaymentHistory.getAfterFine()).isEqualTo(DEFAULT_AFTER_FINE);
    }

    @Test
    @Transactional
    public void createFinePaymentHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = finePaymentHistoryRepository.findAll().size();

        // Create the FinePaymentHistory with an existing ID
        finePaymentHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinePaymentHistoryMockMvc.perform(post("/api/fine-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finePaymentHistory)))
            .andExpect(status().isBadRequest());

        // Validate the FinePaymentHistory in the database
        List<FinePaymentHistory> finePaymentHistoryList = finePaymentHistoryRepository.findAll();
        assertThat(finePaymentHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFinePaymentHistories() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList
        restFinePaymentHistoryMockMvc.perform(get("/api/fine-payment-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finePaymentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].beforeFine").value(hasItem(DEFAULT_BEFORE_FINE.intValue())))
            .andExpect(jsonPath("$.[*].afterFine").value(hasItem(DEFAULT_AFTER_FINE.intValue())));
    }
    
    @Test
    @Transactional
    public void getFinePaymentHistory() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get the finePaymentHistory
        restFinePaymentHistoryMockMvc.perform(get("/api/fine-payment-histories/{id}", finePaymentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(finePaymentHistory.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.monthType").value(DEFAULT_MONTH_TYPE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.beforeFine").value(DEFAULT_BEFORE_FINE.intValue()))
            .andExpect(jsonPath("$.afterFine").value(DEFAULT_AFTER_FINE.intValue()));
    }


    @Test
    @Transactional
    public void getFinePaymentHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        Long id = finePaymentHistory.getId();

        defaultFinePaymentHistoryShouldBeFound("id.equals=" + id);
        defaultFinePaymentHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultFinePaymentHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFinePaymentHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultFinePaymentHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFinePaymentHistoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where year equals to DEFAULT_YEAR
        defaultFinePaymentHistoryShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the finePaymentHistoryList where year equals to UPDATED_YEAR
        defaultFinePaymentHistoryShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where year not equals to DEFAULT_YEAR
        defaultFinePaymentHistoryShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the finePaymentHistoryList where year not equals to UPDATED_YEAR
        defaultFinePaymentHistoryShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultFinePaymentHistoryShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the finePaymentHistoryList where year equals to UPDATED_YEAR
        defaultFinePaymentHistoryShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where year is not null
        defaultFinePaymentHistoryShouldBeFound("year.specified=true");

        // Get all the finePaymentHistoryList where year is null
        defaultFinePaymentHistoryShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where year is greater than or equal to DEFAULT_YEAR
        defaultFinePaymentHistoryShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the finePaymentHistoryList where year is greater than or equal to UPDATED_YEAR
        defaultFinePaymentHistoryShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where year is less than or equal to DEFAULT_YEAR
        defaultFinePaymentHistoryShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the finePaymentHistoryList where year is less than or equal to SMALLER_YEAR
        defaultFinePaymentHistoryShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where year is less than DEFAULT_YEAR
        defaultFinePaymentHistoryShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the finePaymentHistoryList where year is less than UPDATED_YEAR
        defaultFinePaymentHistoryShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where year is greater than DEFAULT_YEAR
        defaultFinePaymentHistoryShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the finePaymentHistoryList where year is greater than SMALLER_YEAR
        defaultFinePaymentHistoryShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }


    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByMonthTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where monthType equals to DEFAULT_MONTH_TYPE
        defaultFinePaymentHistoryShouldBeFound("monthType.equals=" + DEFAULT_MONTH_TYPE);

        // Get all the finePaymentHistoryList where monthType equals to UPDATED_MONTH_TYPE
        defaultFinePaymentHistoryShouldNotBeFound("monthType.equals=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByMonthTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where monthType not equals to DEFAULT_MONTH_TYPE
        defaultFinePaymentHistoryShouldNotBeFound("monthType.notEquals=" + DEFAULT_MONTH_TYPE);

        // Get all the finePaymentHistoryList where monthType not equals to UPDATED_MONTH_TYPE
        defaultFinePaymentHistoryShouldBeFound("monthType.notEquals=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByMonthTypeIsInShouldWork() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where monthType in DEFAULT_MONTH_TYPE or UPDATED_MONTH_TYPE
        defaultFinePaymentHistoryShouldBeFound("monthType.in=" + DEFAULT_MONTH_TYPE + "," + UPDATED_MONTH_TYPE);

        // Get all the finePaymentHistoryList where monthType equals to UPDATED_MONTH_TYPE
        defaultFinePaymentHistoryShouldNotBeFound("monthType.in=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByMonthTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where monthType is not null
        defaultFinePaymentHistoryShouldBeFound("monthType.specified=true");

        // Get all the finePaymentHistoryList where monthType is null
        defaultFinePaymentHistoryShouldNotBeFound("monthType.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where amount equals to DEFAULT_AMOUNT
        defaultFinePaymentHistoryShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the finePaymentHistoryList where amount equals to UPDATED_AMOUNT
        defaultFinePaymentHistoryShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where amount not equals to DEFAULT_AMOUNT
        defaultFinePaymentHistoryShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the finePaymentHistoryList where amount not equals to UPDATED_AMOUNT
        defaultFinePaymentHistoryShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultFinePaymentHistoryShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the finePaymentHistoryList where amount equals to UPDATED_AMOUNT
        defaultFinePaymentHistoryShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where amount is not null
        defaultFinePaymentHistoryShouldBeFound("amount.specified=true");

        // Get all the finePaymentHistoryList where amount is null
        defaultFinePaymentHistoryShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultFinePaymentHistoryShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the finePaymentHistoryList where amount is greater than or equal to UPDATED_AMOUNT
        defaultFinePaymentHistoryShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where amount is less than or equal to DEFAULT_AMOUNT
        defaultFinePaymentHistoryShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the finePaymentHistoryList where amount is less than or equal to SMALLER_AMOUNT
        defaultFinePaymentHistoryShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where amount is less than DEFAULT_AMOUNT
        defaultFinePaymentHistoryShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the finePaymentHistoryList where amount is less than UPDATED_AMOUNT
        defaultFinePaymentHistoryShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where amount is greater than DEFAULT_AMOUNT
        defaultFinePaymentHistoryShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the finePaymentHistoryList where amount is greater than SMALLER_AMOUNT
        defaultFinePaymentHistoryShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByBeforeFineIsEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where beforeFine equals to DEFAULT_BEFORE_FINE
        defaultFinePaymentHistoryShouldBeFound("beforeFine.equals=" + DEFAULT_BEFORE_FINE);

        // Get all the finePaymentHistoryList where beforeFine equals to UPDATED_BEFORE_FINE
        defaultFinePaymentHistoryShouldNotBeFound("beforeFine.equals=" + UPDATED_BEFORE_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByBeforeFineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where beforeFine not equals to DEFAULT_BEFORE_FINE
        defaultFinePaymentHistoryShouldNotBeFound("beforeFine.notEquals=" + DEFAULT_BEFORE_FINE);

        // Get all the finePaymentHistoryList where beforeFine not equals to UPDATED_BEFORE_FINE
        defaultFinePaymentHistoryShouldBeFound("beforeFine.notEquals=" + UPDATED_BEFORE_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByBeforeFineIsInShouldWork() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where beforeFine in DEFAULT_BEFORE_FINE or UPDATED_BEFORE_FINE
        defaultFinePaymentHistoryShouldBeFound("beforeFine.in=" + DEFAULT_BEFORE_FINE + "," + UPDATED_BEFORE_FINE);

        // Get all the finePaymentHistoryList where beforeFine equals to UPDATED_BEFORE_FINE
        defaultFinePaymentHistoryShouldNotBeFound("beforeFine.in=" + UPDATED_BEFORE_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByBeforeFineIsNullOrNotNull() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where beforeFine is not null
        defaultFinePaymentHistoryShouldBeFound("beforeFine.specified=true");

        // Get all the finePaymentHistoryList where beforeFine is null
        defaultFinePaymentHistoryShouldNotBeFound("beforeFine.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByBeforeFineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where beforeFine is greater than or equal to DEFAULT_BEFORE_FINE
        defaultFinePaymentHistoryShouldBeFound("beforeFine.greaterThanOrEqual=" + DEFAULT_BEFORE_FINE);

        // Get all the finePaymentHistoryList where beforeFine is greater than or equal to UPDATED_BEFORE_FINE
        defaultFinePaymentHistoryShouldNotBeFound("beforeFine.greaterThanOrEqual=" + UPDATED_BEFORE_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByBeforeFineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where beforeFine is less than or equal to DEFAULT_BEFORE_FINE
        defaultFinePaymentHistoryShouldBeFound("beforeFine.lessThanOrEqual=" + DEFAULT_BEFORE_FINE);

        // Get all the finePaymentHistoryList where beforeFine is less than or equal to SMALLER_BEFORE_FINE
        defaultFinePaymentHistoryShouldNotBeFound("beforeFine.lessThanOrEqual=" + SMALLER_BEFORE_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByBeforeFineIsLessThanSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where beforeFine is less than DEFAULT_BEFORE_FINE
        defaultFinePaymentHistoryShouldNotBeFound("beforeFine.lessThan=" + DEFAULT_BEFORE_FINE);

        // Get all the finePaymentHistoryList where beforeFine is less than UPDATED_BEFORE_FINE
        defaultFinePaymentHistoryShouldBeFound("beforeFine.lessThan=" + UPDATED_BEFORE_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByBeforeFineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where beforeFine is greater than DEFAULT_BEFORE_FINE
        defaultFinePaymentHistoryShouldNotBeFound("beforeFine.greaterThan=" + DEFAULT_BEFORE_FINE);

        // Get all the finePaymentHistoryList where beforeFine is greater than SMALLER_BEFORE_FINE
        defaultFinePaymentHistoryShouldBeFound("beforeFine.greaterThan=" + SMALLER_BEFORE_FINE);
    }


    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAfterFineIsEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where afterFine equals to DEFAULT_AFTER_FINE
        defaultFinePaymentHistoryShouldBeFound("afterFine.equals=" + DEFAULT_AFTER_FINE);

        // Get all the finePaymentHistoryList where afterFine equals to UPDATED_AFTER_FINE
        defaultFinePaymentHistoryShouldNotBeFound("afterFine.equals=" + UPDATED_AFTER_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAfterFineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where afterFine not equals to DEFAULT_AFTER_FINE
        defaultFinePaymentHistoryShouldNotBeFound("afterFine.notEquals=" + DEFAULT_AFTER_FINE);

        // Get all the finePaymentHistoryList where afterFine not equals to UPDATED_AFTER_FINE
        defaultFinePaymentHistoryShouldBeFound("afterFine.notEquals=" + UPDATED_AFTER_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAfterFineIsInShouldWork() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where afterFine in DEFAULT_AFTER_FINE or UPDATED_AFTER_FINE
        defaultFinePaymentHistoryShouldBeFound("afterFine.in=" + DEFAULT_AFTER_FINE + "," + UPDATED_AFTER_FINE);

        // Get all the finePaymentHistoryList where afterFine equals to UPDATED_AFTER_FINE
        defaultFinePaymentHistoryShouldNotBeFound("afterFine.in=" + UPDATED_AFTER_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAfterFineIsNullOrNotNull() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where afterFine is not null
        defaultFinePaymentHistoryShouldBeFound("afterFine.specified=true");

        // Get all the finePaymentHistoryList where afterFine is null
        defaultFinePaymentHistoryShouldNotBeFound("afterFine.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAfterFineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where afterFine is greater than or equal to DEFAULT_AFTER_FINE
        defaultFinePaymentHistoryShouldBeFound("afterFine.greaterThanOrEqual=" + DEFAULT_AFTER_FINE);

        // Get all the finePaymentHistoryList where afterFine is greater than or equal to UPDATED_AFTER_FINE
        defaultFinePaymentHistoryShouldNotBeFound("afterFine.greaterThanOrEqual=" + UPDATED_AFTER_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAfterFineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where afterFine is less than or equal to DEFAULT_AFTER_FINE
        defaultFinePaymentHistoryShouldBeFound("afterFine.lessThanOrEqual=" + DEFAULT_AFTER_FINE);

        // Get all the finePaymentHistoryList where afterFine is less than or equal to SMALLER_AFTER_FINE
        defaultFinePaymentHistoryShouldNotBeFound("afterFine.lessThanOrEqual=" + SMALLER_AFTER_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAfterFineIsLessThanSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where afterFine is less than DEFAULT_AFTER_FINE
        defaultFinePaymentHistoryShouldNotBeFound("afterFine.lessThan=" + DEFAULT_AFTER_FINE);

        // Get all the finePaymentHistoryList where afterFine is less than UPDATED_AFTER_FINE
        defaultFinePaymentHistoryShouldBeFound("afterFine.lessThan=" + UPDATED_AFTER_FINE);
    }

    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByAfterFineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);

        // Get all the finePaymentHistoryList where afterFine is greater than DEFAULT_AFTER_FINE
        defaultFinePaymentHistoryShouldNotBeFound("afterFine.greaterThan=" + DEFAULT_AFTER_FINE);

        // Get all the finePaymentHistoryList where afterFine is greater than SMALLER_AFTER_FINE
        defaultFinePaymentHistoryShouldBeFound("afterFine.greaterThan=" + SMALLER_AFTER_FINE);
    }


    @Test
    @Transactional
    public void getAllFinePaymentHistoriesByFineIsEqualToSomething() throws Exception {
        // Initialize the database
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);
        Fine fine = FineResourceIT.createEntity(em);
        em.persist(fine);
        em.flush();
        finePaymentHistory.setFine(fine);
        finePaymentHistoryRepository.saveAndFlush(finePaymentHistory);
        Long fineId = fine.getId();

        // Get all the finePaymentHistoryList where fine equals to fineId
        defaultFinePaymentHistoryShouldBeFound("fineId.equals=" + fineId);

        // Get all the finePaymentHistoryList where fine equals to fineId + 1
        defaultFinePaymentHistoryShouldNotBeFound("fineId.equals=" + (fineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFinePaymentHistoryShouldBeFound(String filter) throws Exception {
        restFinePaymentHistoryMockMvc.perform(get("/api/fine-payment-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finePaymentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].beforeFine").value(hasItem(DEFAULT_BEFORE_FINE.intValue())))
            .andExpect(jsonPath("$.[*].afterFine").value(hasItem(DEFAULT_AFTER_FINE.intValue())));

        // Check, that the count call also returns 1
        restFinePaymentHistoryMockMvc.perform(get("/api/fine-payment-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFinePaymentHistoryShouldNotBeFound(String filter) throws Exception {
        restFinePaymentHistoryMockMvc.perform(get("/api/fine-payment-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFinePaymentHistoryMockMvc.perform(get("/api/fine-payment-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFinePaymentHistory() throws Exception {
        // Get the finePaymentHistory
        restFinePaymentHistoryMockMvc.perform(get("/api/fine-payment-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinePaymentHistory() throws Exception {
        // Initialize the database
        finePaymentHistoryService.save(finePaymentHistory);

        int databaseSizeBeforeUpdate = finePaymentHistoryRepository.findAll().size();

        // Update the finePaymentHistory
        FinePaymentHistory updatedFinePaymentHistory = finePaymentHistoryRepository.findById(finePaymentHistory.getId()).get();
        // Disconnect from session so that the updates on updatedFinePaymentHistory are not directly saved in db
        em.detach(updatedFinePaymentHistory);
        updatedFinePaymentHistory
            .year(UPDATED_YEAR)
            .monthType(UPDATED_MONTH_TYPE)
            .amount(UPDATED_AMOUNT)
            .beforeFine(UPDATED_BEFORE_FINE)
            .afterFine(UPDATED_AFTER_FINE);

        restFinePaymentHistoryMockMvc.perform(put("/api/fine-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinePaymentHistory)))
            .andExpect(status().isOk());

        // Validate the FinePaymentHistory in the database
        List<FinePaymentHistory> finePaymentHistoryList = finePaymentHistoryRepository.findAll();
        assertThat(finePaymentHistoryList).hasSize(databaseSizeBeforeUpdate);
        FinePaymentHistory testFinePaymentHistory = finePaymentHistoryList.get(finePaymentHistoryList.size() - 1);
        assertThat(testFinePaymentHistory.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testFinePaymentHistory.getMonthType()).isEqualTo(UPDATED_MONTH_TYPE);
        assertThat(testFinePaymentHistory.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFinePaymentHistory.getBeforeFine()).isEqualTo(UPDATED_BEFORE_FINE);
        assertThat(testFinePaymentHistory.getAfterFine()).isEqualTo(UPDATED_AFTER_FINE);
    }

    @Test
    @Transactional
    public void updateNonExistingFinePaymentHistory() throws Exception {
        int databaseSizeBeforeUpdate = finePaymentHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinePaymentHistoryMockMvc.perform(put("/api/fine-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finePaymentHistory)))
            .andExpect(status().isBadRequest());

        // Validate the FinePaymentHistory in the database
        List<FinePaymentHistory> finePaymentHistoryList = finePaymentHistoryRepository.findAll();
        assertThat(finePaymentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFinePaymentHistory() throws Exception {
        // Initialize the database
        finePaymentHistoryService.save(finePaymentHistory);

        int databaseSizeBeforeDelete = finePaymentHistoryRepository.findAll().size();

        // Delete the finePaymentHistory
        restFinePaymentHistoryMockMvc.perform(delete("/api/fine-payment-histories/{id}", finePaymentHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FinePaymentHistory> finePaymentHistoryList = finePaymentHistoryRepository.findAll();
        assertThat(finePaymentHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
