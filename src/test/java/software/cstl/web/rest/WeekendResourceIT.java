package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Weekend;
import software.cstl.repository.WeekendRepository;
import software.cstl.service.WeekendService;
import software.cstl.service.dto.WeekendCriteria;
import software.cstl.service.WeekendQueryService;

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

import software.cstl.domain.enumeration.WeekDay;
import software.cstl.domain.enumeration.WeekendStatus;
/**
 * Integration tests for the {@link WeekendResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WeekendResourceIT {

    private static final WeekDay DEFAULT_DAY = WeekDay.SUNDAY;
    private static final WeekDay UPDATED_DAY = WeekDay.MONDAY;

    private static final WeekendStatus DEFAULT_STATUS = WeekendStatus.ACTIVE;
    private static final WeekendStatus UPDATED_STATUS = WeekendStatus.INACTIVE;

    @Autowired
    private WeekendRepository weekendRepository;

    @Autowired
    private WeekendService weekendService;

    @Autowired
    private WeekendQueryService weekendQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWeekendMockMvc;

    private Weekend weekend;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Weekend createEntity(EntityManager em) {
        Weekend weekend = new Weekend()
            .day(DEFAULT_DAY)
            .status(DEFAULT_STATUS);
        return weekend;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Weekend createUpdatedEntity(EntityManager em) {
        Weekend weekend = new Weekend()
            .day(UPDATED_DAY)
            .status(UPDATED_STATUS);
        return weekend;
    }

    @BeforeEach
    public void initTest() {
        weekend = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeekend() throws Exception {
        int databaseSizeBeforeCreate = weekendRepository.findAll().size();
        // Create the Weekend
        restWeekendMockMvc.perform(post("/api/weekends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekend)))
            .andExpect(status().isCreated());

        // Validate the Weekend in the database
        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeCreate + 1);
        Weekend testWeekend = weekendList.get(weekendList.size() - 1);
        assertThat(testWeekend.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testWeekend.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createWeekendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weekendRepository.findAll().size();

        // Create the Weekend with an existing ID
        weekend.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeekendMockMvc.perform(post("/api/weekends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekend)))
            .andExpect(status().isBadRequest());

        // Validate the Weekend in the database
        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = weekendRepository.findAll().size();
        // set the field null
        weekend.setDay(null);

        // Create the Weekend, which fails.


        restWeekendMockMvc.perform(post("/api/weekends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekend)))
            .andExpect(status().isBadRequest());

        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = weekendRepository.findAll().size();
        // set the field null
        weekend.setStatus(null);

        // Create the Weekend, which fails.


        restWeekendMockMvc.perform(post("/api/weekends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekend)))
            .andExpect(status().isBadRequest());

        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWeekends() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList
        restWeekendMockMvc.perform(get("/api/weekends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weekend.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getWeekend() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get the weekend
        restWeekendMockMvc.perform(get("/api/weekends/{id}", weekend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weekend.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getWeekendsByIdFiltering() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        Long id = weekend.getId();

        defaultWeekendShouldBeFound("id.equals=" + id);
        defaultWeekendShouldNotBeFound("id.notEquals=" + id);

        defaultWeekendShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWeekendShouldNotBeFound("id.greaterThan=" + id);

        defaultWeekendShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWeekendShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWeekendsByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day equals to DEFAULT_DAY
        defaultWeekendShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the weekendList where day equals to UPDATED_DAY
        defaultWeekendShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllWeekendsByDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day not equals to DEFAULT_DAY
        defaultWeekendShouldNotBeFound("day.notEquals=" + DEFAULT_DAY);

        // Get all the weekendList where day not equals to UPDATED_DAY
        defaultWeekendShouldBeFound("day.notEquals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllWeekendsByDayIsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day in DEFAULT_DAY or UPDATED_DAY
        defaultWeekendShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the weekendList where day equals to UPDATED_DAY
        defaultWeekendShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllWeekendsByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day is not null
        defaultWeekendShouldBeFound("day.specified=true");

        // Get all the weekendList where day is null
        defaultWeekendShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where status equals to DEFAULT_STATUS
        defaultWeekendShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the weekendList where status equals to UPDATED_STATUS
        defaultWeekendShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllWeekendsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where status not equals to DEFAULT_STATUS
        defaultWeekendShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the weekendList where status not equals to UPDATED_STATUS
        defaultWeekendShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllWeekendsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultWeekendShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the weekendList where status equals to UPDATED_STATUS
        defaultWeekendShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllWeekendsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where status is not null
        defaultWeekendShouldBeFound("status.specified=true");

        // Get all the weekendList where status is null
        defaultWeekendShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWeekendShouldBeFound(String filter) throws Exception {
        restWeekendMockMvc.perform(get("/api/weekends?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weekend.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restWeekendMockMvc.perform(get("/api/weekends/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWeekendShouldNotBeFound(String filter) throws Exception {
        restWeekendMockMvc.perform(get("/api/weekends?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWeekendMockMvc.perform(get("/api/weekends/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWeekend() throws Exception {
        // Get the weekend
        restWeekendMockMvc.perform(get("/api/weekends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeekend() throws Exception {
        // Initialize the database
        weekendService.save(weekend);

        int databaseSizeBeforeUpdate = weekendRepository.findAll().size();

        // Update the weekend
        Weekend updatedWeekend = weekendRepository.findById(weekend.getId()).get();
        // Disconnect from session so that the updates on updatedWeekend are not directly saved in db
        em.detach(updatedWeekend);
        updatedWeekend
            .day(UPDATED_DAY)
            .status(UPDATED_STATUS);

        restWeekendMockMvc.perform(put("/api/weekends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeekend)))
            .andExpect(status().isOk());

        // Validate the Weekend in the database
        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeUpdate);
        Weekend testWeekend = weekendList.get(weekendList.size() - 1);
        assertThat(testWeekend.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testWeekend.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingWeekend() throws Exception {
        int databaseSizeBeforeUpdate = weekendRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeekendMockMvc.perform(put("/api/weekends")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekend)))
            .andExpect(status().isBadRequest());

        // Validate the Weekend in the database
        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWeekend() throws Exception {
        // Initialize the database
        weekendService.save(weekend);

        int databaseSizeBeforeDelete = weekendRepository.findAll().size();

        // Delete the weekend
        restWeekendMockMvc.perform(delete("/api/weekends/{id}", weekend.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
