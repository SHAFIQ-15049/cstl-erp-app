package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Holiday;
import software.cstl.domain.HolidayType;
import software.cstl.repository.HolidayRepository;
import software.cstl.service.HolidayService;
import software.cstl.service.dto.HolidayCriteria;
import software.cstl.service.HolidayQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HolidayResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HolidayResourceIT {

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_TOTAL_DAYS = 1;
    private static final Integer UPDATED_TOTAL_DAYS = 2;
    private static final Integer SMALLER_TOTAL_DAYS = 1 - 1;

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private HolidayQueryService holidayQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHolidayMockMvc;

    private Holiday holiday;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Holiday createEntity(EntityManager em) {
        Holiday holiday = new Holiday()
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .totalDays(DEFAULT_TOTAL_DAYS);
        // Add required entity
        HolidayType holidayType;
        if (TestUtil.findAll(em, HolidayType.class).isEmpty()) {
            holidayType = HolidayTypeResourceIT.createEntity(em);
            em.persist(holidayType);
            em.flush();
        } else {
            holidayType = TestUtil.findAll(em, HolidayType.class).get(0);
        }
        holiday.setHolidayType(holidayType);
        return holiday;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Holiday createUpdatedEntity(EntityManager em) {
        Holiday holiday = new Holiday()
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .totalDays(UPDATED_TOTAL_DAYS);
        // Add required entity
        HolidayType holidayType;
        if (TestUtil.findAll(em, HolidayType.class).isEmpty()) {
            holidayType = HolidayTypeResourceIT.createUpdatedEntity(em);
            em.persist(holidayType);
            em.flush();
        } else {
            holidayType = TestUtil.findAll(em, HolidayType.class).get(0);
        }
        holiday.setHolidayType(holidayType);
        return holiday;
    }

    @BeforeEach
    public void initTest() {
        holiday = createEntity(em);
    }

    @Test
    @Transactional
    public void createHoliday() throws Exception {
        int databaseSizeBeforeCreate = holidayRepository.findAll().size();
        // Create the Holiday
        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holiday)))
            .andExpect(status().isCreated());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeCreate + 1);
        Holiday testHoliday = holidayList.get(holidayList.size() - 1);
        assertThat(testHoliday.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testHoliday.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testHoliday.getTotalDays()).isEqualTo(DEFAULT_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void createHolidayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = holidayRepository.findAll().size();

        // Create the Holiday with an existing ID
        holiday.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holiday)))
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayRepository.findAll().size();
        // set the field null
        holiday.setFrom(null);

        // Create the Holiday, which fails.


        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holiday)))
            .andExpect(status().isBadRequest());

        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayRepository.findAll().size();
        // set the field null
        holiday.setTo(null);

        // Create the Holiday, which fails.


        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holiday)))
            .andExpect(status().isBadRequest());

        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayRepository.findAll().size();
        // set the field null
        holiday.setTotalDays(null);

        // Create the Holiday, which fails.


        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holiday)))
            .andExpect(status().isBadRequest());

        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHolidays() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList
        restHolidayMockMvc.perform(get("/api/holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holiday.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS)));
    }
    
    @Test
    @Transactional
    public void getHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get the holiday
        restHolidayMockMvc.perform(get("/api/holidays/{id}", holiday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(holiday.getId().intValue()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.totalDays").value(DEFAULT_TOTAL_DAYS));
    }


    @Test
    @Transactional
    public void getHolidaysByIdFiltering() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        Long id = holiday.getId();

        defaultHolidayShouldBeFound("id.equals=" + id);
        defaultHolidayShouldNotBeFound("id.notEquals=" + id);

        defaultHolidayShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHolidayShouldNotBeFound("id.greaterThan=" + id);

        defaultHolidayShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHolidayShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHolidaysByFromIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where from equals to DEFAULT_FROM
        defaultHolidayShouldBeFound("from.equals=" + DEFAULT_FROM);

        // Get all the holidayList where from equals to UPDATED_FROM
        defaultHolidayShouldNotBeFound("from.equals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where from not equals to DEFAULT_FROM
        defaultHolidayShouldNotBeFound("from.notEquals=" + DEFAULT_FROM);

        // Get all the holidayList where from not equals to UPDATED_FROM
        defaultHolidayShouldBeFound("from.notEquals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where from in DEFAULT_FROM or UPDATED_FROM
        defaultHolidayShouldBeFound("from.in=" + DEFAULT_FROM + "," + UPDATED_FROM);

        // Get all the holidayList where from equals to UPDATED_FROM
        defaultHolidayShouldNotBeFound("from.in=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where from is not null
        defaultHolidayShouldBeFound("from.specified=true");

        // Get all the holidayList where from is null
        defaultHolidayShouldNotBeFound("from.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where from is greater than or equal to DEFAULT_FROM
        defaultHolidayShouldBeFound("from.greaterThanOrEqual=" + DEFAULT_FROM);

        // Get all the holidayList where from is greater than or equal to UPDATED_FROM
        defaultHolidayShouldNotBeFound("from.greaterThanOrEqual=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where from is less than or equal to DEFAULT_FROM
        defaultHolidayShouldBeFound("from.lessThanOrEqual=" + DEFAULT_FROM);

        // Get all the holidayList where from is less than or equal to SMALLER_FROM
        defaultHolidayShouldNotBeFound("from.lessThanOrEqual=" + SMALLER_FROM);
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromIsLessThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where from is less than DEFAULT_FROM
        defaultHolidayShouldNotBeFound("from.lessThan=" + DEFAULT_FROM);

        // Get all the holidayList where from is less than UPDATED_FROM
        defaultHolidayShouldBeFound("from.lessThan=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where from is greater than DEFAULT_FROM
        defaultHolidayShouldNotBeFound("from.greaterThan=" + DEFAULT_FROM);

        // Get all the holidayList where from is greater than SMALLER_FROM
        defaultHolidayShouldBeFound("from.greaterThan=" + SMALLER_FROM);
    }


    @Test
    @Transactional
    public void getAllHolidaysByToIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where to equals to DEFAULT_TO
        defaultHolidayShouldBeFound("to.equals=" + DEFAULT_TO);

        // Get all the holidayList where to equals to UPDATED_TO
        defaultHolidayShouldNotBeFound("to.equals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllHolidaysByToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where to not equals to DEFAULT_TO
        defaultHolidayShouldNotBeFound("to.notEquals=" + DEFAULT_TO);

        // Get all the holidayList where to not equals to UPDATED_TO
        defaultHolidayShouldBeFound("to.notEquals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllHolidaysByToIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where to in DEFAULT_TO or UPDATED_TO
        defaultHolidayShouldBeFound("to.in=" + DEFAULT_TO + "," + UPDATED_TO);

        // Get all the holidayList where to equals to UPDATED_TO
        defaultHolidayShouldNotBeFound("to.in=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllHolidaysByToIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where to is not null
        defaultHolidayShouldBeFound("to.specified=true");

        // Get all the holidayList where to is null
        defaultHolidayShouldNotBeFound("to.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where to is greater than or equal to DEFAULT_TO
        defaultHolidayShouldBeFound("to.greaterThanOrEqual=" + DEFAULT_TO);

        // Get all the holidayList where to is greater than or equal to UPDATED_TO
        defaultHolidayShouldNotBeFound("to.greaterThanOrEqual=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllHolidaysByToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where to is less than or equal to DEFAULT_TO
        defaultHolidayShouldBeFound("to.lessThanOrEqual=" + DEFAULT_TO);

        // Get all the holidayList where to is less than or equal to SMALLER_TO
        defaultHolidayShouldNotBeFound("to.lessThanOrEqual=" + SMALLER_TO);
    }

    @Test
    @Transactional
    public void getAllHolidaysByToIsLessThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where to is less than DEFAULT_TO
        defaultHolidayShouldNotBeFound("to.lessThan=" + DEFAULT_TO);

        // Get all the holidayList where to is less than UPDATED_TO
        defaultHolidayShouldBeFound("to.lessThan=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllHolidaysByToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where to is greater than DEFAULT_TO
        defaultHolidayShouldNotBeFound("to.greaterThan=" + DEFAULT_TO);

        // Get all the holidayList where to is greater than SMALLER_TO
        defaultHolidayShouldBeFound("to.greaterThan=" + SMALLER_TO);
    }


    @Test
    @Transactional
    public void getAllHolidaysByTotalDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where totalDays equals to DEFAULT_TOTAL_DAYS
        defaultHolidayShouldBeFound("totalDays.equals=" + DEFAULT_TOTAL_DAYS);

        // Get all the holidayList where totalDays equals to UPDATED_TOTAL_DAYS
        defaultHolidayShouldNotBeFound("totalDays.equals=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllHolidaysByTotalDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where totalDays not equals to DEFAULT_TOTAL_DAYS
        defaultHolidayShouldNotBeFound("totalDays.notEquals=" + DEFAULT_TOTAL_DAYS);

        // Get all the holidayList where totalDays not equals to UPDATED_TOTAL_DAYS
        defaultHolidayShouldBeFound("totalDays.notEquals=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllHolidaysByTotalDaysIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where totalDays in DEFAULT_TOTAL_DAYS or UPDATED_TOTAL_DAYS
        defaultHolidayShouldBeFound("totalDays.in=" + DEFAULT_TOTAL_DAYS + "," + UPDATED_TOTAL_DAYS);

        // Get all the holidayList where totalDays equals to UPDATED_TOTAL_DAYS
        defaultHolidayShouldNotBeFound("totalDays.in=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllHolidaysByTotalDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where totalDays is not null
        defaultHolidayShouldBeFound("totalDays.specified=true");

        // Get all the holidayList where totalDays is null
        defaultHolidayShouldNotBeFound("totalDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByTotalDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where totalDays is greater than or equal to DEFAULT_TOTAL_DAYS
        defaultHolidayShouldBeFound("totalDays.greaterThanOrEqual=" + DEFAULT_TOTAL_DAYS);

        // Get all the holidayList where totalDays is greater than or equal to UPDATED_TOTAL_DAYS
        defaultHolidayShouldNotBeFound("totalDays.greaterThanOrEqual=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllHolidaysByTotalDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where totalDays is less than or equal to DEFAULT_TOTAL_DAYS
        defaultHolidayShouldBeFound("totalDays.lessThanOrEqual=" + DEFAULT_TOTAL_DAYS);

        // Get all the holidayList where totalDays is less than or equal to SMALLER_TOTAL_DAYS
        defaultHolidayShouldNotBeFound("totalDays.lessThanOrEqual=" + SMALLER_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllHolidaysByTotalDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where totalDays is less than DEFAULT_TOTAL_DAYS
        defaultHolidayShouldNotBeFound("totalDays.lessThan=" + DEFAULT_TOTAL_DAYS);

        // Get all the holidayList where totalDays is less than UPDATED_TOTAL_DAYS
        defaultHolidayShouldBeFound("totalDays.lessThan=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllHolidaysByTotalDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where totalDays is greater than DEFAULT_TOTAL_DAYS
        defaultHolidayShouldNotBeFound("totalDays.greaterThan=" + DEFAULT_TOTAL_DAYS);

        // Get all the holidayList where totalDays is greater than SMALLER_TOTAL_DAYS
        defaultHolidayShouldBeFound("totalDays.greaterThan=" + SMALLER_TOTAL_DAYS);
    }


    @Test
    @Transactional
    public void getAllHolidaysByHolidayTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        HolidayType holidayType = holiday.getHolidayType();
        holidayRepository.saveAndFlush(holiday);
        Long holidayTypeId = holidayType.getId();

        // Get all the holidayList where holidayType equals to holidayTypeId
        defaultHolidayShouldBeFound("holidayTypeId.equals=" + holidayTypeId);

        // Get all the holidayList where holidayType equals to holidayTypeId + 1
        defaultHolidayShouldNotBeFound("holidayTypeId.equals=" + (holidayTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHolidayShouldBeFound(String filter) throws Exception {
        restHolidayMockMvc.perform(get("/api/holidays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holiday.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS)));

        // Check, that the count call also returns 1
        restHolidayMockMvc.perform(get("/api/holidays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHolidayShouldNotBeFound(String filter) throws Exception {
        restHolidayMockMvc.perform(get("/api/holidays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHolidayMockMvc.perform(get("/api/holidays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingHoliday() throws Exception {
        // Get the holiday
        restHolidayMockMvc.perform(get("/api/holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoliday() throws Exception {
        // Initialize the database
        holidayService.save(holiday);

        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();

        // Update the holiday
        Holiday updatedHoliday = holidayRepository.findById(holiday.getId()).get();
        // Disconnect from session so that the updates on updatedHoliday are not directly saved in db
        em.detach(updatedHoliday);
        updatedHoliday
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .totalDays(UPDATED_TOTAL_DAYS);

        restHolidayMockMvc.perform(put("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHoliday)))
            .andExpect(status().isOk());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
        Holiday testHoliday = holidayList.get(holidayList.size() - 1);
        assertThat(testHoliday.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testHoliday.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testHoliday.getTotalDays()).isEqualTo(UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingHoliday() throws Exception {
        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolidayMockMvc.perform(put("/api/holidays")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holiday)))
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHoliday() throws Exception {
        // Initialize the database
        holidayService.save(holiday);

        int databaseSizeBeforeDelete = holidayRepository.findAll().size();

        // Delete the holiday
        restHolidayMockMvc.perform(delete("/api/holidays/{id}", holiday.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
