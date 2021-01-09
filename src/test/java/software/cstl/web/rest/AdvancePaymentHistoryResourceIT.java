package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.AdvancePaymentHistory;
import software.cstl.domain.Advance;
import software.cstl.repository.AdvancePaymentHistoryRepository;
import software.cstl.service.AdvancePaymentHistoryService;
import software.cstl.service.dto.AdvancePaymentHistoryCriteria;
import software.cstl.service.AdvancePaymentHistoryQueryService;

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
 * Integration tests for the {@link AdvancePaymentHistoryResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdvancePaymentHistoryResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final MonthType DEFAULT_MONTH_TYPE = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH_TYPE = MonthType.FEBRUARY;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_BEFORE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEFORE = new BigDecimal(2);
    private static final BigDecimal SMALLER_BEFORE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AFTER = new BigDecimal(1);
    private static final BigDecimal UPDATED_AFTER = new BigDecimal(2);
    private static final BigDecimal SMALLER_AFTER = new BigDecimal(1 - 1);

    @Autowired
    private AdvancePaymentHistoryRepository advancePaymentHistoryRepository;

    @Autowired
    private AdvancePaymentHistoryService advancePaymentHistoryService;

    @Autowired
    private AdvancePaymentHistoryQueryService advancePaymentHistoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdvancePaymentHistoryMockMvc;

    private AdvancePaymentHistory advancePaymentHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdvancePaymentHistory createEntity(EntityManager em) {
        AdvancePaymentHistory advancePaymentHistory = new AdvancePaymentHistory()
            .year(DEFAULT_YEAR)
            .monthType(DEFAULT_MONTH_TYPE)
            .amount(DEFAULT_AMOUNT)
            .before(DEFAULT_BEFORE)
            .after(DEFAULT_AFTER);
        return advancePaymentHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdvancePaymentHistory createUpdatedEntity(EntityManager em) {
        AdvancePaymentHistory advancePaymentHistory = new AdvancePaymentHistory()
            .year(UPDATED_YEAR)
            .monthType(UPDATED_MONTH_TYPE)
            .amount(UPDATED_AMOUNT)
            .before(UPDATED_BEFORE)
            .after(UPDATED_AFTER);
        return advancePaymentHistory;
    }

    @BeforeEach
    public void initTest() {
        advancePaymentHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdvancePaymentHistory() throws Exception {
        int databaseSizeBeforeCreate = advancePaymentHistoryRepository.findAll().size();
        // Create the AdvancePaymentHistory
        restAdvancePaymentHistoryMockMvc.perform(post("/api/advance-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advancePaymentHistory)))
            .andExpect(status().isCreated());

        // Validate the AdvancePaymentHistory in the database
        List<AdvancePaymentHistory> advancePaymentHistoryList = advancePaymentHistoryRepository.findAll();
        assertThat(advancePaymentHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        AdvancePaymentHistory testAdvancePaymentHistory = advancePaymentHistoryList.get(advancePaymentHistoryList.size() - 1);
        assertThat(testAdvancePaymentHistory.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAdvancePaymentHistory.getMonthType()).isEqualTo(DEFAULT_MONTH_TYPE);
        assertThat(testAdvancePaymentHistory.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAdvancePaymentHistory.getBefore()).isEqualTo(DEFAULT_BEFORE);
        assertThat(testAdvancePaymentHistory.getAfter()).isEqualTo(DEFAULT_AFTER);
    }

    @Test
    @Transactional
    public void createAdvancePaymentHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = advancePaymentHistoryRepository.findAll().size();

        // Create the AdvancePaymentHistory with an existing ID
        advancePaymentHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdvancePaymentHistoryMockMvc.perform(post("/api/advance-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advancePaymentHistory)))
            .andExpect(status().isBadRequest());

        // Validate the AdvancePaymentHistory in the database
        List<AdvancePaymentHistory> advancePaymentHistoryList = advancePaymentHistoryRepository.findAll();
        assertThat(advancePaymentHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdvancePaymentHistories() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList
        restAdvancePaymentHistoryMockMvc.perform(get("/api/advance-payment-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advancePaymentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].before").value(hasItem(DEFAULT_BEFORE.intValue())))
            .andExpect(jsonPath("$.[*].after").value(hasItem(DEFAULT_AFTER.intValue())));
    }
    
    @Test
    @Transactional
    public void getAdvancePaymentHistory() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get the advancePaymentHistory
        restAdvancePaymentHistoryMockMvc.perform(get("/api/advance-payment-histories/{id}", advancePaymentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(advancePaymentHistory.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.monthType").value(DEFAULT_MONTH_TYPE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.before").value(DEFAULT_BEFORE.intValue()))
            .andExpect(jsonPath("$.after").value(DEFAULT_AFTER.intValue()));
    }


    @Test
    @Transactional
    public void getAdvancePaymentHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        Long id = advancePaymentHistory.getId();

        defaultAdvancePaymentHistoryShouldBeFound("id.equals=" + id);
        defaultAdvancePaymentHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultAdvancePaymentHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdvancePaymentHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultAdvancePaymentHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdvancePaymentHistoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where year equals to DEFAULT_YEAR
        defaultAdvancePaymentHistoryShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the advancePaymentHistoryList where year equals to UPDATED_YEAR
        defaultAdvancePaymentHistoryShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where year not equals to DEFAULT_YEAR
        defaultAdvancePaymentHistoryShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the advancePaymentHistoryList where year not equals to UPDATED_YEAR
        defaultAdvancePaymentHistoryShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultAdvancePaymentHistoryShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the advancePaymentHistoryList where year equals to UPDATED_YEAR
        defaultAdvancePaymentHistoryShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where year is not null
        defaultAdvancePaymentHistoryShouldBeFound("year.specified=true");

        // Get all the advancePaymentHistoryList where year is null
        defaultAdvancePaymentHistoryShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where year is greater than or equal to DEFAULT_YEAR
        defaultAdvancePaymentHistoryShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the advancePaymentHistoryList where year is greater than or equal to UPDATED_YEAR
        defaultAdvancePaymentHistoryShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where year is less than or equal to DEFAULT_YEAR
        defaultAdvancePaymentHistoryShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the advancePaymentHistoryList where year is less than or equal to SMALLER_YEAR
        defaultAdvancePaymentHistoryShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where year is less than DEFAULT_YEAR
        defaultAdvancePaymentHistoryShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the advancePaymentHistoryList where year is less than UPDATED_YEAR
        defaultAdvancePaymentHistoryShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where year is greater than DEFAULT_YEAR
        defaultAdvancePaymentHistoryShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the advancePaymentHistoryList where year is greater than SMALLER_YEAR
        defaultAdvancePaymentHistoryShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }


    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByMonthTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where monthType equals to DEFAULT_MONTH_TYPE
        defaultAdvancePaymentHistoryShouldBeFound("monthType.equals=" + DEFAULT_MONTH_TYPE);

        // Get all the advancePaymentHistoryList where monthType equals to UPDATED_MONTH_TYPE
        defaultAdvancePaymentHistoryShouldNotBeFound("monthType.equals=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByMonthTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where monthType not equals to DEFAULT_MONTH_TYPE
        defaultAdvancePaymentHistoryShouldNotBeFound("monthType.notEquals=" + DEFAULT_MONTH_TYPE);

        // Get all the advancePaymentHistoryList where monthType not equals to UPDATED_MONTH_TYPE
        defaultAdvancePaymentHistoryShouldBeFound("monthType.notEquals=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByMonthTypeIsInShouldWork() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where monthType in DEFAULT_MONTH_TYPE or UPDATED_MONTH_TYPE
        defaultAdvancePaymentHistoryShouldBeFound("monthType.in=" + DEFAULT_MONTH_TYPE + "," + UPDATED_MONTH_TYPE);

        // Get all the advancePaymentHistoryList where monthType equals to UPDATED_MONTH_TYPE
        defaultAdvancePaymentHistoryShouldNotBeFound("monthType.in=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByMonthTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where monthType is not null
        defaultAdvancePaymentHistoryShouldBeFound("monthType.specified=true");

        // Get all the advancePaymentHistoryList where monthType is null
        defaultAdvancePaymentHistoryShouldNotBeFound("monthType.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where amount equals to DEFAULT_AMOUNT
        defaultAdvancePaymentHistoryShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the advancePaymentHistoryList where amount equals to UPDATED_AMOUNT
        defaultAdvancePaymentHistoryShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where amount not equals to DEFAULT_AMOUNT
        defaultAdvancePaymentHistoryShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the advancePaymentHistoryList where amount not equals to UPDATED_AMOUNT
        defaultAdvancePaymentHistoryShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultAdvancePaymentHistoryShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the advancePaymentHistoryList where amount equals to UPDATED_AMOUNT
        defaultAdvancePaymentHistoryShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where amount is not null
        defaultAdvancePaymentHistoryShouldBeFound("amount.specified=true");

        // Get all the advancePaymentHistoryList where amount is null
        defaultAdvancePaymentHistoryShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultAdvancePaymentHistoryShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the advancePaymentHistoryList where amount is greater than or equal to UPDATED_AMOUNT
        defaultAdvancePaymentHistoryShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where amount is less than or equal to DEFAULT_AMOUNT
        defaultAdvancePaymentHistoryShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the advancePaymentHistoryList where amount is less than or equal to SMALLER_AMOUNT
        defaultAdvancePaymentHistoryShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where amount is less than DEFAULT_AMOUNT
        defaultAdvancePaymentHistoryShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the advancePaymentHistoryList where amount is less than UPDATED_AMOUNT
        defaultAdvancePaymentHistoryShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where amount is greater than DEFAULT_AMOUNT
        defaultAdvancePaymentHistoryShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the advancePaymentHistoryList where amount is greater than SMALLER_AMOUNT
        defaultAdvancePaymentHistoryShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByBeforeIsEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where before equals to DEFAULT_BEFORE
        defaultAdvancePaymentHistoryShouldBeFound("before.equals=" + DEFAULT_BEFORE);

        // Get all the advancePaymentHistoryList where before equals to UPDATED_BEFORE
        defaultAdvancePaymentHistoryShouldNotBeFound("before.equals=" + UPDATED_BEFORE);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByBeforeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where before not equals to DEFAULT_BEFORE
        defaultAdvancePaymentHistoryShouldNotBeFound("before.notEquals=" + DEFAULT_BEFORE);

        // Get all the advancePaymentHistoryList where before not equals to UPDATED_BEFORE
        defaultAdvancePaymentHistoryShouldBeFound("before.notEquals=" + UPDATED_BEFORE);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByBeforeIsInShouldWork() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where before in DEFAULT_BEFORE or UPDATED_BEFORE
        defaultAdvancePaymentHistoryShouldBeFound("before.in=" + DEFAULT_BEFORE + "," + UPDATED_BEFORE);

        // Get all the advancePaymentHistoryList where before equals to UPDATED_BEFORE
        defaultAdvancePaymentHistoryShouldNotBeFound("before.in=" + UPDATED_BEFORE);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByBeforeIsNullOrNotNull() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where before is not null
        defaultAdvancePaymentHistoryShouldBeFound("before.specified=true");

        // Get all the advancePaymentHistoryList where before is null
        defaultAdvancePaymentHistoryShouldNotBeFound("before.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByBeforeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where before is greater than or equal to DEFAULT_BEFORE
        defaultAdvancePaymentHistoryShouldBeFound("before.greaterThanOrEqual=" + DEFAULT_BEFORE);

        // Get all the advancePaymentHistoryList where before is greater than or equal to UPDATED_BEFORE
        defaultAdvancePaymentHistoryShouldNotBeFound("before.greaterThanOrEqual=" + UPDATED_BEFORE);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByBeforeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where before is less than or equal to DEFAULT_BEFORE
        defaultAdvancePaymentHistoryShouldBeFound("before.lessThanOrEqual=" + DEFAULT_BEFORE);

        // Get all the advancePaymentHistoryList where before is less than or equal to SMALLER_BEFORE
        defaultAdvancePaymentHistoryShouldNotBeFound("before.lessThanOrEqual=" + SMALLER_BEFORE);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByBeforeIsLessThanSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where before is less than DEFAULT_BEFORE
        defaultAdvancePaymentHistoryShouldNotBeFound("before.lessThan=" + DEFAULT_BEFORE);

        // Get all the advancePaymentHistoryList where before is less than UPDATED_BEFORE
        defaultAdvancePaymentHistoryShouldBeFound("before.lessThan=" + UPDATED_BEFORE);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByBeforeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where before is greater than DEFAULT_BEFORE
        defaultAdvancePaymentHistoryShouldNotBeFound("before.greaterThan=" + DEFAULT_BEFORE);

        // Get all the advancePaymentHistoryList where before is greater than SMALLER_BEFORE
        defaultAdvancePaymentHistoryShouldBeFound("before.greaterThan=" + SMALLER_BEFORE);
    }


    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAfterIsEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where after equals to DEFAULT_AFTER
        defaultAdvancePaymentHistoryShouldBeFound("after.equals=" + DEFAULT_AFTER);

        // Get all the advancePaymentHistoryList where after equals to UPDATED_AFTER
        defaultAdvancePaymentHistoryShouldNotBeFound("after.equals=" + UPDATED_AFTER);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAfterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where after not equals to DEFAULT_AFTER
        defaultAdvancePaymentHistoryShouldNotBeFound("after.notEquals=" + DEFAULT_AFTER);

        // Get all the advancePaymentHistoryList where after not equals to UPDATED_AFTER
        defaultAdvancePaymentHistoryShouldBeFound("after.notEquals=" + UPDATED_AFTER);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAfterIsInShouldWork() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where after in DEFAULT_AFTER or UPDATED_AFTER
        defaultAdvancePaymentHistoryShouldBeFound("after.in=" + DEFAULT_AFTER + "," + UPDATED_AFTER);

        // Get all the advancePaymentHistoryList where after equals to UPDATED_AFTER
        defaultAdvancePaymentHistoryShouldNotBeFound("after.in=" + UPDATED_AFTER);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAfterIsNullOrNotNull() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where after is not null
        defaultAdvancePaymentHistoryShouldBeFound("after.specified=true");

        // Get all the advancePaymentHistoryList where after is null
        defaultAdvancePaymentHistoryShouldNotBeFound("after.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAfterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where after is greater than or equal to DEFAULT_AFTER
        defaultAdvancePaymentHistoryShouldBeFound("after.greaterThanOrEqual=" + DEFAULT_AFTER);

        // Get all the advancePaymentHistoryList where after is greater than or equal to UPDATED_AFTER
        defaultAdvancePaymentHistoryShouldNotBeFound("after.greaterThanOrEqual=" + UPDATED_AFTER);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAfterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where after is less than or equal to DEFAULT_AFTER
        defaultAdvancePaymentHistoryShouldBeFound("after.lessThanOrEqual=" + DEFAULT_AFTER);

        // Get all the advancePaymentHistoryList where after is less than or equal to SMALLER_AFTER
        defaultAdvancePaymentHistoryShouldNotBeFound("after.lessThanOrEqual=" + SMALLER_AFTER);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAfterIsLessThanSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where after is less than DEFAULT_AFTER
        defaultAdvancePaymentHistoryShouldNotBeFound("after.lessThan=" + DEFAULT_AFTER);

        // Get all the advancePaymentHistoryList where after is less than UPDATED_AFTER
        defaultAdvancePaymentHistoryShouldBeFound("after.lessThan=" + UPDATED_AFTER);
    }

    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAfterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);

        // Get all the advancePaymentHistoryList where after is greater than DEFAULT_AFTER
        defaultAdvancePaymentHistoryShouldNotBeFound("after.greaterThan=" + DEFAULT_AFTER);

        // Get all the advancePaymentHistoryList where after is greater than SMALLER_AFTER
        defaultAdvancePaymentHistoryShouldBeFound("after.greaterThan=" + SMALLER_AFTER);
    }


    @Test
    @Transactional
    public void getAllAdvancePaymentHistoriesByAdvanceIsEqualToSomething() throws Exception {
        // Initialize the database
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);
        Advance advance = AdvanceResourceIT.createEntity(em);
        em.persist(advance);
        em.flush();
        advancePaymentHistory.setAdvance(advance);
        advancePaymentHistoryRepository.saveAndFlush(advancePaymentHistory);
        Long advanceId = advance.getId();

        // Get all the advancePaymentHistoryList where advance equals to advanceId
        defaultAdvancePaymentHistoryShouldBeFound("advanceId.equals=" + advanceId);

        // Get all the advancePaymentHistoryList where advance equals to advanceId + 1
        defaultAdvancePaymentHistoryShouldNotBeFound("advanceId.equals=" + (advanceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdvancePaymentHistoryShouldBeFound(String filter) throws Exception {
        restAdvancePaymentHistoryMockMvc.perform(get("/api/advance-payment-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advancePaymentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].before").value(hasItem(DEFAULT_BEFORE.intValue())))
            .andExpect(jsonPath("$.[*].after").value(hasItem(DEFAULT_AFTER.intValue())));

        // Check, that the count call also returns 1
        restAdvancePaymentHistoryMockMvc.perform(get("/api/advance-payment-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdvancePaymentHistoryShouldNotBeFound(String filter) throws Exception {
        restAdvancePaymentHistoryMockMvc.perform(get("/api/advance-payment-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdvancePaymentHistoryMockMvc.perform(get("/api/advance-payment-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAdvancePaymentHistory() throws Exception {
        // Get the advancePaymentHistory
        restAdvancePaymentHistoryMockMvc.perform(get("/api/advance-payment-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdvancePaymentHistory() throws Exception {
        // Initialize the database
        advancePaymentHistoryService.save(advancePaymentHistory);

        int databaseSizeBeforeUpdate = advancePaymentHistoryRepository.findAll().size();

        // Update the advancePaymentHistory
        AdvancePaymentHistory updatedAdvancePaymentHistory = advancePaymentHistoryRepository.findById(advancePaymentHistory.getId()).get();
        // Disconnect from session so that the updates on updatedAdvancePaymentHistory are not directly saved in db
        em.detach(updatedAdvancePaymentHistory);
        updatedAdvancePaymentHistory
            .year(UPDATED_YEAR)
            .monthType(UPDATED_MONTH_TYPE)
            .amount(UPDATED_AMOUNT)
            .before(UPDATED_BEFORE)
            .after(UPDATED_AFTER);

        restAdvancePaymentHistoryMockMvc.perform(put("/api/advance-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdvancePaymentHistory)))
            .andExpect(status().isOk());

        // Validate the AdvancePaymentHistory in the database
        List<AdvancePaymentHistory> advancePaymentHistoryList = advancePaymentHistoryRepository.findAll();
        assertThat(advancePaymentHistoryList).hasSize(databaseSizeBeforeUpdate);
        AdvancePaymentHistory testAdvancePaymentHistory = advancePaymentHistoryList.get(advancePaymentHistoryList.size() - 1);
        assertThat(testAdvancePaymentHistory.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAdvancePaymentHistory.getMonthType()).isEqualTo(UPDATED_MONTH_TYPE);
        assertThat(testAdvancePaymentHistory.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAdvancePaymentHistory.getBefore()).isEqualTo(UPDATED_BEFORE);
        assertThat(testAdvancePaymentHistory.getAfter()).isEqualTo(UPDATED_AFTER);
    }

    @Test
    @Transactional
    public void updateNonExistingAdvancePaymentHistory() throws Exception {
        int databaseSizeBeforeUpdate = advancePaymentHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdvancePaymentHistoryMockMvc.perform(put("/api/advance-payment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(advancePaymentHistory)))
            .andExpect(status().isBadRequest());

        // Validate the AdvancePaymentHistory in the database
        List<AdvancePaymentHistory> advancePaymentHistoryList = advancePaymentHistoryRepository.findAll();
        assertThat(advancePaymentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdvancePaymentHistory() throws Exception {
        // Initialize the database
        advancePaymentHistoryService.save(advancePaymentHistory);

        int databaseSizeBeforeDelete = advancePaymentHistoryRepository.findAll().size();

        // Delete the advancePaymentHistory
        restAdvancePaymentHistoryMockMvc.perform(delete("/api/advance-payment-histories/{id}", advancePaymentHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdvancePaymentHistory> advancePaymentHistoryList = advancePaymentHistoryRepository.findAll();
        assertThat(advancePaymentHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
