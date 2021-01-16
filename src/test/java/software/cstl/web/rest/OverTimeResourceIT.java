package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.OverTime;
import software.cstl.repository.OverTimeRepository;

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

    private static final MonthType DEFAULT_MONTH = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH = MonthType.FEBRUARY;

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TOTAL_OVER_TIME = 1D;
    private static final Double UPDATED_TOTAL_OVER_TIME = 2D;

    private static final Double DEFAULT_OFFICIAL_OVER_TIME = 1D;
    private static final Double UPDATED_OFFICIAL_OVER_TIME = 2D;

    private static final Double DEFAULT_EXTRA_OVER_TIME = 1D;
    private static final Double UPDATED_EXTRA_OVER_TIME = 2D;

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OFFICIAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_OFFICIAL_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXTRA_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTRA_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXECUTED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXECUTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_EXECUTED_BY = "BBBBBBBBBB";

    @Autowired
    private OverTimeRepository overTimeRepository;

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
    public void getNonExistingOverTime() throws Exception {
        // Get the overTime
        restOverTimeMockMvc.perform(get("/api/over-times/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOverTime() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

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
        overTimeRepository.saveAndFlush(overTime);

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
