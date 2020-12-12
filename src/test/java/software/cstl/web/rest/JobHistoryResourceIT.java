package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.JobHistory;
import software.cstl.domain.Employee;
import software.cstl.repository.JobHistoryRepository;
import software.cstl.service.JobHistoryService;
import software.cstl.service.dto.JobHistoryCriteria;
import software.cstl.service.JobHistoryQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JobHistoryResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobHistoryResourceIT {

    private static final Integer DEFAULT_SERIAL = 1;
    private static final Integer UPDATED_SERIAL = 2;
    private static final Integer SMALLER_SERIAL = 1 - 1;

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_PAY_SCALE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAY_SCALE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PAY_SCALE = new BigDecimal(1 - 1);

    private static final Double DEFAULT_TOTAL_EXPERIENCE = 1D;
    private static final Double UPDATED_TOTAL_EXPERIENCE = 2D;
    private static final Double SMALLER_TOTAL_EXPERIENCE = 1D - 1D;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Autowired
    private JobHistoryService jobHistoryService;

    @Autowired
    private JobHistoryQueryService jobHistoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobHistoryMockMvc;

    private JobHistory jobHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobHistory createEntity(EntityManager em) {
        JobHistory jobHistory = new JobHistory()
            .serial(DEFAULT_SERIAL)
            .organization(DEFAULT_ORGANIZATION)
            .designation(DEFAULT_DESIGNATION)
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .payScale(DEFAULT_PAY_SCALE)
            .totalExperience(DEFAULT_TOTAL_EXPERIENCE);
        return jobHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobHistory createUpdatedEntity(EntityManager em) {
        JobHistory jobHistory = new JobHistory()
            .serial(UPDATED_SERIAL)
            .organization(UPDATED_ORGANIZATION)
            .designation(UPDATED_DESIGNATION)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .payScale(UPDATED_PAY_SCALE)
            .totalExperience(UPDATED_TOTAL_EXPERIENCE);
        return jobHistory;
    }

    @BeforeEach
    public void initTest() {
        jobHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobHistory() throws Exception {
        int databaseSizeBeforeCreate = jobHistoryRepository.findAll().size();
        // Create the JobHistory
        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistory)))
            .andExpect(status().isCreated());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        JobHistory testJobHistory = jobHistoryList.get(jobHistoryList.size() - 1);
        assertThat(testJobHistory.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testJobHistory.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testJobHistory.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testJobHistory.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testJobHistory.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testJobHistory.getPayScale()).isEqualTo(DEFAULT_PAY_SCALE);
        assertThat(testJobHistory.getTotalExperience()).isEqualTo(DEFAULT_TOTAL_EXPERIENCE);
    }

    @Test
    @Transactional
    public void createJobHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobHistoryRepository.findAll().size();

        // Create the JobHistory with an existing ID
        jobHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistory)))
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSerialIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobHistoryRepository.findAll().size();
        // set the field null
        jobHistory.setSerial(null);

        // Create the JobHistory, which fails.


        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistory)))
            .andExpect(status().isBadRequest());

        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrganizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobHistoryRepository.findAll().size();
        // set the field null
        jobHistory.setOrganization(null);

        // Create the JobHistory, which fails.


        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistory)))
            .andExpect(status().isBadRequest());

        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobHistoryRepository.findAll().size();
        // set the field null
        jobHistory.setDesignation(null);

        // Create the JobHistory, which fails.


        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistory)))
            .andExpect(status().isBadRequest());

        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobHistories() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList
        restJobHistoryMockMvc.perform(get("/api/job-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].payScale").value(hasItem(DEFAULT_PAY_SCALE.intValue())))
            .andExpect(jsonPath("$.[*].totalExperience").value(hasItem(DEFAULT_TOTAL_EXPERIENCE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get the jobHistory
        restJobHistoryMockMvc.perform(get("/api/job-histories/{id}", jobHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobHistory.getId().intValue()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.payScale").value(DEFAULT_PAY_SCALE.intValue()))
            .andExpect(jsonPath("$.totalExperience").value(DEFAULT_TOTAL_EXPERIENCE.doubleValue()));
    }


    @Test
    @Transactional
    public void getJobHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        Long id = jobHistory.getId();

        defaultJobHistoryShouldBeFound("id.equals=" + id);
        defaultJobHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultJobHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJobHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultJobHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJobHistoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllJobHistoriesBySerialIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where serial equals to DEFAULT_SERIAL
        defaultJobHistoryShouldBeFound("serial.equals=" + DEFAULT_SERIAL);

        // Get all the jobHistoryList where serial equals to UPDATED_SERIAL
        defaultJobHistoryShouldNotBeFound("serial.equals=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesBySerialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where serial not equals to DEFAULT_SERIAL
        defaultJobHistoryShouldNotBeFound("serial.notEquals=" + DEFAULT_SERIAL);

        // Get all the jobHistoryList where serial not equals to UPDATED_SERIAL
        defaultJobHistoryShouldBeFound("serial.notEquals=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesBySerialIsInShouldWork() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where serial in DEFAULT_SERIAL or UPDATED_SERIAL
        defaultJobHistoryShouldBeFound("serial.in=" + DEFAULT_SERIAL + "," + UPDATED_SERIAL);

        // Get all the jobHistoryList where serial equals to UPDATED_SERIAL
        defaultJobHistoryShouldNotBeFound("serial.in=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesBySerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where serial is not null
        defaultJobHistoryShouldBeFound("serial.specified=true");

        // Get all the jobHistoryList where serial is null
        defaultJobHistoryShouldNotBeFound("serial.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobHistoriesBySerialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where serial is greater than or equal to DEFAULT_SERIAL
        defaultJobHistoryShouldBeFound("serial.greaterThanOrEqual=" + DEFAULT_SERIAL);

        // Get all the jobHistoryList where serial is greater than or equal to UPDATED_SERIAL
        defaultJobHistoryShouldNotBeFound("serial.greaterThanOrEqual=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesBySerialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where serial is less than or equal to DEFAULT_SERIAL
        defaultJobHistoryShouldBeFound("serial.lessThanOrEqual=" + DEFAULT_SERIAL);

        // Get all the jobHistoryList where serial is less than or equal to SMALLER_SERIAL
        defaultJobHistoryShouldNotBeFound("serial.lessThanOrEqual=" + SMALLER_SERIAL);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesBySerialIsLessThanSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where serial is less than DEFAULT_SERIAL
        defaultJobHistoryShouldNotBeFound("serial.lessThan=" + DEFAULT_SERIAL);

        // Get all the jobHistoryList where serial is less than UPDATED_SERIAL
        defaultJobHistoryShouldBeFound("serial.lessThan=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesBySerialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where serial is greater than DEFAULT_SERIAL
        defaultJobHistoryShouldNotBeFound("serial.greaterThan=" + DEFAULT_SERIAL);

        // Get all the jobHistoryList where serial is greater than SMALLER_SERIAL
        defaultJobHistoryShouldBeFound("serial.greaterThan=" + SMALLER_SERIAL);
    }


    @Test
    @Transactional
    public void getAllJobHistoriesByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where organization equals to DEFAULT_ORGANIZATION
        defaultJobHistoryShouldBeFound("organization.equals=" + DEFAULT_ORGANIZATION);

        // Get all the jobHistoryList where organization equals to UPDATED_ORGANIZATION
        defaultJobHistoryShouldNotBeFound("organization.equals=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByOrganizationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where organization not equals to DEFAULT_ORGANIZATION
        defaultJobHistoryShouldNotBeFound("organization.notEquals=" + DEFAULT_ORGANIZATION);

        // Get all the jobHistoryList where organization not equals to UPDATED_ORGANIZATION
        defaultJobHistoryShouldBeFound("organization.notEquals=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByOrganizationIsInShouldWork() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where organization in DEFAULT_ORGANIZATION or UPDATED_ORGANIZATION
        defaultJobHistoryShouldBeFound("organization.in=" + DEFAULT_ORGANIZATION + "," + UPDATED_ORGANIZATION);

        // Get all the jobHistoryList where organization equals to UPDATED_ORGANIZATION
        defaultJobHistoryShouldNotBeFound("organization.in=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByOrganizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where organization is not null
        defaultJobHistoryShouldBeFound("organization.specified=true");

        // Get all the jobHistoryList where organization is null
        defaultJobHistoryShouldNotBeFound("organization.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobHistoriesByOrganizationContainsSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where organization contains DEFAULT_ORGANIZATION
        defaultJobHistoryShouldBeFound("organization.contains=" + DEFAULT_ORGANIZATION);

        // Get all the jobHistoryList where organization contains UPDATED_ORGANIZATION
        defaultJobHistoryShouldNotBeFound("organization.contains=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByOrganizationNotContainsSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where organization does not contain DEFAULT_ORGANIZATION
        defaultJobHistoryShouldNotBeFound("organization.doesNotContain=" + DEFAULT_ORGANIZATION);

        // Get all the jobHistoryList where organization does not contain UPDATED_ORGANIZATION
        defaultJobHistoryShouldBeFound("organization.doesNotContain=" + UPDATED_ORGANIZATION);
    }


    @Test
    @Transactional
    public void getAllJobHistoriesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where designation equals to DEFAULT_DESIGNATION
        defaultJobHistoryShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the jobHistoryList where designation equals to UPDATED_DESIGNATION
        defaultJobHistoryShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where designation not equals to DEFAULT_DESIGNATION
        defaultJobHistoryShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the jobHistoryList where designation not equals to UPDATED_DESIGNATION
        defaultJobHistoryShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultJobHistoryShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the jobHistoryList where designation equals to UPDATED_DESIGNATION
        defaultJobHistoryShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where designation is not null
        defaultJobHistoryShouldBeFound("designation.specified=true");

        // Get all the jobHistoryList where designation is null
        defaultJobHistoryShouldNotBeFound("designation.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobHistoriesByDesignationContainsSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where designation contains DEFAULT_DESIGNATION
        defaultJobHistoryShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the jobHistoryList where designation contains UPDATED_DESIGNATION
        defaultJobHistoryShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where designation does not contain DEFAULT_DESIGNATION
        defaultJobHistoryShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the jobHistoryList where designation does not contain UPDATED_DESIGNATION
        defaultJobHistoryShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }


    @Test
    @Transactional
    public void getAllJobHistoriesByFromIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where from equals to DEFAULT_FROM
        defaultJobHistoryShouldBeFound("from.equals=" + DEFAULT_FROM);

        // Get all the jobHistoryList where from equals to UPDATED_FROM
        defaultJobHistoryShouldNotBeFound("from.equals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where from not equals to DEFAULT_FROM
        defaultJobHistoryShouldNotBeFound("from.notEquals=" + DEFAULT_FROM);

        // Get all the jobHistoryList where from not equals to UPDATED_FROM
        defaultJobHistoryShouldBeFound("from.notEquals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByFromIsInShouldWork() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where from in DEFAULT_FROM or UPDATED_FROM
        defaultJobHistoryShouldBeFound("from.in=" + DEFAULT_FROM + "," + UPDATED_FROM);

        // Get all the jobHistoryList where from equals to UPDATED_FROM
        defaultJobHistoryShouldNotBeFound("from.in=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where from is not null
        defaultJobHistoryShouldBeFound("from.specified=true");

        // Get all the jobHistoryList where from is null
        defaultJobHistoryShouldNotBeFound("from.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where from is greater than or equal to DEFAULT_FROM
        defaultJobHistoryShouldBeFound("from.greaterThanOrEqual=" + DEFAULT_FROM);

        // Get all the jobHistoryList where from is greater than or equal to UPDATED_FROM
        defaultJobHistoryShouldNotBeFound("from.greaterThanOrEqual=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where from is less than or equal to DEFAULT_FROM
        defaultJobHistoryShouldBeFound("from.lessThanOrEqual=" + DEFAULT_FROM);

        // Get all the jobHistoryList where from is less than or equal to SMALLER_FROM
        defaultJobHistoryShouldNotBeFound("from.lessThanOrEqual=" + SMALLER_FROM);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByFromIsLessThanSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where from is less than DEFAULT_FROM
        defaultJobHistoryShouldNotBeFound("from.lessThan=" + DEFAULT_FROM);

        // Get all the jobHistoryList where from is less than UPDATED_FROM
        defaultJobHistoryShouldBeFound("from.lessThan=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where from is greater than DEFAULT_FROM
        defaultJobHistoryShouldNotBeFound("from.greaterThan=" + DEFAULT_FROM);

        // Get all the jobHistoryList where from is greater than SMALLER_FROM
        defaultJobHistoryShouldBeFound("from.greaterThan=" + SMALLER_FROM);
    }


    @Test
    @Transactional
    public void getAllJobHistoriesByToIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where to equals to DEFAULT_TO
        defaultJobHistoryShouldBeFound("to.equals=" + DEFAULT_TO);

        // Get all the jobHistoryList where to equals to UPDATED_TO
        defaultJobHistoryShouldNotBeFound("to.equals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where to not equals to DEFAULT_TO
        defaultJobHistoryShouldNotBeFound("to.notEquals=" + DEFAULT_TO);

        // Get all the jobHistoryList where to not equals to UPDATED_TO
        defaultJobHistoryShouldBeFound("to.notEquals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByToIsInShouldWork() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where to in DEFAULT_TO or UPDATED_TO
        defaultJobHistoryShouldBeFound("to.in=" + DEFAULT_TO + "," + UPDATED_TO);

        // Get all the jobHistoryList where to equals to UPDATED_TO
        defaultJobHistoryShouldNotBeFound("to.in=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByToIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where to is not null
        defaultJobHistoryShouldBeFound("to.specified=true");

        // Get all the jobHistoryList where to is null
        defaultJobHistoryShouldNotBeFound("to.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where to is greater than or equal to DEFAULT_TO
        defaultJobHistoryShouldBeFound("to.greaterThanOrEqual=" + DEFAULT_TO);

        // Get all the jobHistoryList where to is greater than or equal to UPDATED_TO
        defaultJobHistoryShouldNotBeFound("to.greaterThanOrEqual=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where to is less than or equal to DEFAULT_TO
        defaultJobHistoryShouldBeFound("to.lessThanOrEqual=" + DEFAULT_TO);

        // Get all the jobHistoryList where to is less than or equal to SMALLER_TO
        defaultJobHistoryShouldNotBeFound("to.lessThanOrEqual=" + SMALLER_TO);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByToIsLessThanSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where to is less than DEFAULT_TO
        defaultJobHistoryShouldNotBeFound("to.lessThan=" + DEFAULT_TO);

        // Get all the jobHistoryList where to is less than UPDATED_TO
        defaultJobHistoryShouldBeFound("to.lessThan=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where to is greater than DEFAULT_TO
        defaultJobHistoryShouldNotBeFound("to.greaterThan=" + DEFAULT_TO);

        // Get all the jobHistoryList where to is greater than SMALLER_TO
        defaultJobHistoryShouldBeFound("to.greaterThan=" + SMALLER_TO);
    }


    @Test
    @Transactional
    public void getAllJobHistoriesByPayScaleIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where payScale equals to DEFAULT_PAY_SCALE
        defaultJobHistoryShouldBeFound("payScale.equals=" + DEFAULT_PAY_SCALE);

        // Get all the jobHistoryList where payScale equals to UPDATED_PAY_SCALE
        defaultJobHistoryShouldNotBeFound("payScale.equals=" + UPDATED_PAY_SCALE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByPayScaleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where payScale not equals to DEFAULT_PAY_SCALE
        defaultJobHistoryShouldNotBeFound("payScale.notEquals=" + DEFAULT_PAY_SCALE);

        // Get all the jobHistoryList where payScale not equals to UPDATED_PAY_SCALE
        defaultJobHistoryShouldBeFound("payScale.notEquals=" + UPDATED_PAY_SCALE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByPayScaleIsInShouldWork() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where payScale in DEFAULT_PAY_SCALE or UPDATED_PAY_SCALE
        defaultJobHistoryShouldBeFound("payScale.in=" + DEFAULT_PAY_SCALE + "," + UPDATED_PAY_SCALE);

        // Get all the jobHistoryList where payScale equals to UPDATED_PAY_SCALE
        defaultJobHistoryShouldNotBeFound("payScale.in=" + UPDATED_PAY_SCALE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByPayScaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where payScale is not null
        defaultJobHistoryShouldBeFound("payScale.specified=true");

        // Get all the jobHistoryList where payScale is null
        defaultJobHistoryShouldNotBeFound("payScale.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByPayScaleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where payScale is greater than or equal to DEFAULT_PAY_SCALE
        defaultJobHistoryShouldBeFound("payScale.greaterThanOrEqual=" + DEFAULT_PAY_SCALE);

        // Get all the jobHistoryList where payScale is greater than or equal to UPDATED_PAY_SCALE
        defaultJobHistoryShouldNotBeFound("payScale.greaterThanOrEqual=" + UPDATED_PAY_SCALE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByPayScaleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where payScale is less than or equal to DEFAULT_PAY_SCALE
        defaultJobHistoryShouldBeFound("payScale.lessThanOrEqual=" + DEFAULT_PAY_SCALE);

        // Get all the jobHistoryList where payScale is less than or equal to SMALLER_PAY_SCALE
        defaultJobHistoryShouldNotBeFound("payScale.lessThanOrEqual=" + SMALLER_PAY_SCALE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByPayScaleIsLessThanSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where payScale is less than DEFAULT_PAY_SCALE
        defaultJobHistoryShouldNotBeFound("payScale.lessThan=" + DEFAULT_PAY_SCALE);

        // Get all the jobHistoryList where payScale is less than UPDATED_PAY_SCALE
        defaultJobHistoryShouldBeFound("payScale.lessThan=" + UPDATED_PAY_SCALE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByPayScaleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where payScale is greater than DEFAULT_PAY_SCALE
        defaultJobHistoryShouldNotBeFound("payScale.greaterThan=" + DEFAULT_PAY_SCALE);

        // Get all the jobHistoryList where payScale is greater than SMALLER_PAY_SCALE
        defaultJobHistoryShouldBeFound("payScale.greaterThan=" + SMALLER_PAY_SCALE);
    }


    @Test
    @Transactional
    public void getAllJobHistoriesByTotalExperienceIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where totalExperience equals to DEFAULT_TOTAL_EXPERIENCE
        defaultJobHistoryShouldBeFound("totalExperience.equals=" + DEFAULT_TOTAL_EXPERIENCE);

        // Get all the jobHistoryList where totalExperience equals to UPDATED_TOTAL_EXPERIENCE
        defaultJobHistoryShouldNotBeFound("totalExperience.equals=" + UPDATED_TOTAL_EXPERIENCE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByTotalExperienceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where totalExperience not equals to DEFAULT_TOTAL_EXPERIENCE
        defaultJobHistoryShouldNotBeFound("totalExperience.notEquals=" + DEFAULT_TOTAL_EXPERIENCE);

        // Get all the jobHistoryList where totalExperience not equals to UPDATED_TOTAL_EXPERIENCE
        defaultJobHistoryShouldBeFound("totalExperience.notEquals=" + UPDATED_TOTAL_EXPERIENCE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByTotalExperienceIsInShouldWork() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where totalExperience in DEFAULT_TOTAL_EXPERIENCE or UPDATED_TOTAL_EXPERIENCE
        defaultJobHistoryShouldBeFound("totalExperience.in=" + DEFAULT_TOTAL_EXPERIENCE + "," + UPDATED_TOTAL_EXPERIENCE);

        // Get all the jobHistoryList where totalExperience equals to UPDATED_TOTAL_EXPERIENCE
        defaultJobHistoryShouldNotBeFound("totalExperience.in=" + UPDATED_TOTAL_EXPERIENCE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByTotalExperienceIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where totalExperience is not null
        defaultJobHistoryShouldBeFound("totalExperience.specified=true");

        // Get all the jobHistoryList where totalExperience is null
        defaultJobHistoryShouldNotBeFound("totalExperience.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByTotalExperienceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where totalExperience is greater than or equal to DEFAULT_TOTAL_EXPERIENCE
        defaultJobHistoryShouldBeFound("totalExperience.greaterThanOrEqual=" + DEFAULT_TOTAL_EXPERIENCE);

        // Get all the jobHistoryList where totalExperience is greater than or equal to UPDATED_TOTAL_EXPERIENCE
        defaultJobHistoryShouldNotBeFound("totalExperience.greaterThanOrEqual=" + UPDATED_TOTAL_EXPERIENCE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByTotalExperienceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where totalExperience is less than or equal to DEFAULT_TOTAL_EXPERIENCE
        defaultJobHistoryShouldBeFound("totalExperience.lessThanOrEqual=" + DEFAULT_TOTAL_EXPERIENCE);

        // Get all the jobHistoryList where totalExperience is less than or equal to SMALLER_TOTAL_EXPERIENCE
        defaultJobHistoryShouldNotBeFound("totalExperience.lessThanOrEqual=" + SMALLER_TOTAL_EXPERIENCE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByTotalExperienceIsLessThanSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where totalExperience is less than DEFAULT_TOTAL_EXPERIENCE
        defaultJobHistoryShouldNotBeFound("totalExperience.lessThan=" + DEFAULT_TOTAL_EXPERIENCE);

        // Get all the jobHistoryList where totalExperience is less than UPDATED_TOTAL_EXPERIENCE
        defaultJobHistoryShouldBeFound("totalExperience.lessThan=" + UPDATED_TOTAL_EXPERIENCE);
    }

    @Test
    @Transactional
    public void getAllJobHistoriesByTotalExperienceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList where totalExperience is greater than DEFAULT_TOTAL_EXPERIENCE
        defaultJobHistoryShouldNotBeFound("totalExperience.greaterThan=" + DEFAULT_TOTAL_EXPERIENCE);

        // Get all the jobHistoryList where totalExperience is greater than SMALLER_TOTAL_EXPERIENCE
        defaultJobHistoryShouldBeFound("totalExperience.greaterThan=" + SMALLER_TOTAL_EXPERIENCE);
    }


    @Test
    @Transactional
    public void getAllJobHistoriesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        jobHistory.setEmployee(employee);
        jobHistoryRepository.saveAndFlush(jobHistory);
        Long employeeId = employee.getId();

        // Get all the jobHistoryList where employee equals to employeeId
        defaultJobHistoryShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the jobHistoryList where employee equals to employeeId + 1
        defaultJobHistoryShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJobHistoryShouldBeFound(String filter) throws Exception {
        restJobHistoryMockMvc.perform(get("/api/job-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].payScale").value(hasItem(DEFAULT_PAY_SCALE.intValue())))
            .andExpect(jsonPath("$.[*].totalExperience").value(hasItem(DEFAULT_TOTAL_EXPERIENCE.doubleValue())));

        // Check, that the count call also returns 1
        restJobHistoryMockMvc.perform(get("/api/job-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJobHistoryShouldNotBeFound(String filter) throws Exception {
        restJobHistoryMockMvc.perform(get("/api/job-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobHistoryMockMvc.perform(get("/api/job-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingJobHistory() throws Exception {
        // Get the jobHistory
        restJobHistoryMockMvc.perform(get("/api/job-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobHistory() throws Exception {
        // Initialize the database
        jobHistoryService.save(jobHistory);

        int databaseSizeBeforeUpdate = jobHistoryRepository.findAll().size();

        // Update the jobHistory
        JobHistory updatedJobHistory = jobHistoryRepository.findById(jobHistory.getId()).get();
        // Disconnect from session so that the updates on updatedJobHistory are not directly saved in db
        em.detach(updatedJobHistory);
        updatedJobHistory
            .serial(UPDATED_SERIAL)
            .organization(UPDATED_ORGANIZATION)
            .designation(UPDATED_DESIGNATION)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .payScale(UPDATED_PAY_SCALE)
            .totalExperience(UPDATED_TOTAL_EXPERIENCE);

        restJobHistoryMockMvc.perform(put("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobHistory)))
            .andExpect(status().isOk());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeUpdate);
        JobHistory testJobHistory = jobHistoryList.get(jobHistoryList.size() - 1);
        assertThat(testJobHistory.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testJobHistory.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testJobHistory.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testJobHistory.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testJobHistory.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testJobHistory.getPayScale()).isEqualTo(UPDATED_PAY_SCALE);
        assertThat(testJobHistory.getTotalExperience()).isEqualTo(UPDATED_TOTAL_EXPERIENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingJobHistory() throws Exception {
        int databaseSizeBeforeUpdate = jobHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobHistoryMockMvc.perform(put("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistory)))
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobHistory() throws Exception {
        // Initialize the database
        jobHistoryService.save(jobHistory);

        int databaseSizeBeforeDelete = jobHistoryRepository.findAll().size();

        // Delete the jobHistory
        restJobHistoryMockMvc.perform(delete("/api/job-histories/{id}", jobHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
