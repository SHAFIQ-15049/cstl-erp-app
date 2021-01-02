package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.FestivalAllowanceTimeLine;
import software.cstl.repository.FestivalAllowanceTimeLineRepository;
import software.cstl.service.FestivalAllowanceTimeLineService;
import software.cstl.service.dto.FestivalAllowanceTimeLineCriteria;
import software.cstl.service.FestivalAllowanceTimeLineQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.MonthType;
/**
 * Integration tests for the {@link FestivalAllowanceTimeLineResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FestivalAllowanceTimeLineResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final MonthType DEFAULT_MONTH = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH = MonthType.FEBRUARY;

    @Autowired
    private FestivalAllowanceTimeLineRepository festivalAllowanceTimeLineRepository;

    @Autowired
    private FestivalAllowanceTimeLineService festivalAllowanceTimeLineService;

    @Autowired
    private FestivalAllowanceTimeLineQueryService festivalAllowanceTimeLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFestivalAllowanceTimeLineMockMvc;

    private FestivalAllowanceTimeLine festivalAllowanceTimeLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FestivalAllowanceTimeLine createEntity(EntityManager em) {
        FestivalAllowanceTimeLine festivalAllowanceTimeLine = new FestivalAllowanceTimeLine()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH);
        return festivalAllowanceTimeLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FestivalAllowanceTimeLine createUpdatedEntity(EntityManager em) {
        FestivalAllowanceTimeLine festivalAllowanceTimeLine = new FestivalAllowanceTimeLine()
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH);
        return festivalAllowanceTimeLine;
    }

    @BeforeEach
    public void initTest() {
        festivalAllowanceTimeLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createFestivalAllowanceTimeLine() throws Exception {
        int databaseSizeBeforeCreate = festivalAllowanceTimeLineRepository.findAll().size();
        // Create the FestivalAllowanceTimeLine
        restFestivalAllowanceTimeLineMockMvc.perform(post("/api/festival-allowance-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(festivalAllowanceTimeLine)))
            .andExpect(status().isCreated());

        // Validate the FestivalAllowanceTimeLine in the database
        List<FestivalAllowanceTimeLine> festivalAllowanceTimeLineList = festivalAllowanceTimeLineRepository.findAll();
        assertThat(festivalAllowanceTimeLineList).hasSize(databaseSizeBeforeCreate + 1);
        FestivalAllowanceTimeLine testFestivalAllowanceTimeLine = festivalAllowanceTimeLineList.get(festivalAllowanceTimeLineList.size() - 1);
        assertThat(testFestivalAllowanceTimeLine.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testFestivalAllowanceTimeLine.getMonth()).isEqualTo(DEFAULT_MONTH);
    }

    @Test
    @Transactional
    public void createFestivalAllowanceTimeLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = festivalAllowanceTimeLineRepository.findAll().size();

        // Create the FestivalAllowanceTimeLine with an existing ID
        festivalAllowanceTimeLine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFestivalAllowanceTimeLineMockMvc.perform(post("/api/festival-allowance-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(festivalAllowanceTimeLine)))
            .andExpect(status().isBadRequest());

        // Validate the FestivalAllowanceTimeLine in the database
        List<FestivalAllowanceTimeLine> festivalAllowanceTimeLineList = festivalAllowanceTimeLineRepository.findAll();
        assertThat(festivalAllowanceTimeLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLines() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList
        restFestivalAllowanceTimeLineMockMvc.perform(get("/api/festival-allowance-time-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(festivalAllowanceTimeLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())));
    }
    
    @Test
    @Transactional
    public void getFestivalAllowanceTimeLine() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get the festivalAllowanceTimeLine
        restFestivalAllowanceTimeLineMockMvc.perform(get("/api/festival-allowance-time-lines/{id}", festivalAllowanceTimeLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(festivalAllowanceTimeLine.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()));
    }


    @Test
    @Transactional
    public void getFestivalAllowanceTimeLinesByIdFiltering() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        Long id = festivalAllowanceTimeLine.getId();

        defaultFestivalAllowanceTimeLineShouldBeFound("id.equals=" + id);
        defaultFestivalAllowanceTimeLineShouldNotBeFound("id.notEquals=" + id);

        defaultFestivalAllowanceTimeLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFestivalAllowanceTimeLineShouldNotBeFound("id.greaterThan=" + id);

        defaultFestivalAllowanceTimeLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFestivalAllowanceTimeLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where year equals to DEFAULT_YEAR
        defaultFestivalAllowanceTimeLineShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the festivalAllowanceTimeLineList where year equals to UPDATED_YEAR
        defaultFestivalAllowanceTimeLineShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where year not equals to DEFAULT_YEAR
        defaultFestivalAllowanceTimeLineShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the festivalAllowanceTimeLineList where year not equals to UPDATED_YEAR
        defaultFestivalAllowanceTimeLineShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultFestivalAllowanceTimeLineShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the festivalAllowanceTimeLineList where year equals to UPDATED_YEAR
        defaultFestivalAllowanceTimeLineShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where year is not null
        defaultFestivalAllowanceTimeLineShouldBeFound("year.specified=true");

        // Get all the festivalAllowanceTimeLineList where year is null
        defaultFestivalAllowanceTimeLineShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where year is greater than or equal to DEFAULT_YEAR
        defaultFestivalAllowanceTimeLineShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the festivalAllowanceTimeLineList where year is greater than or equal to UPDATED_YEAR
        defaultFestivalAllowanceTimeLineShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where year is less than or equal to DEFAULT_YEAR
        defaultFestivalAllowanceTimeLineShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the festivalAllowanceTimeLineList where year is less than or equal to SMALLER_YEAR
        defaultFestivalAllowanceTimeLineShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where year is less than DEFAULT_YEAR
        defaultFestivalAllowanceTimeLineShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the festivalAllowanceTimeLineList where year is less than UPDATED_YEAR
        defaultFestivalAllowanceTimeLineShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where year is greater than DEFAULT_YEAR
        defaultFestivalAllowanceTimeLineShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the festivalAllowanceTimeLineList where year is greater than SMALLER_YEAR
        defaultFestivalAllowanceTimeLineShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }


    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where month equals to DEFAULT_MONTH
        defaultFestivalAllowanceTimeLineShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the festivalAllowanceTimeLineList where month equals to UPDATED_MONTH
        defaultFestivalAllowanceTimeLineShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where month not equals to DEFAULT_MONTH
        defaultFestivalAllowanceTimeLineShouldNotBeFound("month.notEquals=" + DEFAULT_MONTH);

        // Get all the festivalAllowanceTimeLineList where month not equals to UPDATED_MONTH
        defaultFestivalAllowanceTimeLineShouldBeFound("month.notEquals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultFestivalAllowanceTimeLineShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the festivalAllowanceTimeLineList where month equals to UPDATED_MONTH
        defaultFestivalAllowanceTimeLineShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllFestivalAllowanceTimeLinesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineRepository.saveAndFlush(festivalAllowanceTimeLine);

        // Get all the festivalAllowanceTimeLineList where month is not null
        defaultFestivalAllowanceTimeLineShouldBeFound("month.specified=true");

        // Get all the festivalAllowanceTimeLineList where month is null
        defaultFestivalAllowanceTimeLineShouldNotBeFound("month.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFestivalAllowanceTimeLineShouldBeFound(String filter) throws Exception {
        restFestivalAllowanceTimeLineMockMvc.perform(get("/api/festival-allowance-time-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(festivalAllowanceTimeLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())));

        // Check, that the count call also returns 1
        restFestivalAllowanceTimeLineMockMvc.perform(get("/api/festival-allowance-time-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFestivalAllowanceTimeLineShouldNotBeFound(String filter) throws Exception {
        restFestivalAllowanceTimeLineMockMvc.perform(get("/api/festival-allowance-time-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFestivalAllowanceTimeLineMockMvc.perform(get("/api/festival-allowance-time-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFestivalAllowanceTimeLine() throws Exception {
        // Get the festivalAllowanceTimeLine
        restFestivalAllowanceTimeLineMockMvc.perform(get("/api/festival-allowance-time-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFestivalAllowanceTimeLine() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineService.save(festivalAllowanceTimeLine);

        int databaseSizeBeforeUpdate = festivalAllowanceTimeLineRepository.findAll().size();

        // Update the festivalAllowanceTimeLine
        FestivalAllowanceTimeLine updatedFestivalAllowanceTimeLine = festivalAllowanceTimeLineRepository.findById(festivalAllowanceTimeLine.getId()).get();
        // Disconnect from session so that the updates on updatedFestivalAllowanceTimeLine are not directly saved in db
        em.detach(updatedFestivalAllowanceTimeLine);
        updatedFestivalAllowanceTimeLine
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH);

        restFestivalAllowanceTimeLineMockMvc.perform(put("/api/festival-allowance-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFestivalAllowanceTimeLine)))
            .andExpect(status().isOk());

        // Validate the FestivalAllowanceTimeLine in the database
        List<FestivalAllowanceTimeLine> festivalAllowanceTimeLineList = festivalAllowanceTimeLineRepository.findAll();
        assertThat(festivalAllowanceTimeLineList).hasSize(databaseSizeBeforeUpdate);
        FestivalAllowanceTimeLine testFestivalAllowanceTimeLine = festivalAllowanceTimeLineList.get(festivalAllowanceTimeLineList.size() - 1);
        assertThat(testFestivalAllowanceTimeLine.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testFestivalAllowanceTimeLine.getMonth()).isEqualTo(UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void updateNonExistingFestivalAllowanceTimeLine() throws Exception {
        int databaseSizeBeforeUpdate = festivalAllowanceTimeLineRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFestivalAllowanceTimeLineMockMvc.perform(put("/api/festival-allowance-time-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(festivalAllowanceTimeLine)))
            .andExpect(status().isBadRequest());

        // Validate the FestivalAllowanceTimeLine in the database
        List<FestivalAllowanceTimeLine> festivalAllowanceTimeLineList = festivalAllowanceTimeLineRepository.findAll();
        assertThat(festivalAllowanceTimeLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFestivalAllowanceTimeLine() throws Exception {
        // Initialize the database
        festivalAllowanceTimeLineService.save(festivalAllowanceTimeLine);

        int databaseSizeBeforeDelete = festivalAllowanceTimeLineRepository.findAll().size();

        // Delete the festivalAllowanceTimeLine
        restFestivalAllowanceTimeLineMockMvc.perform(delete("/api/festival-allowance-time-lines/{id}", festivalAllowanceTimeLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FestivalAllowanceTimeLine> festivalAllowanceTimeLineList = festivalAllowanceTimeLineRepository.findAll();
        assertThat(festivalAllowanceTimeLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
