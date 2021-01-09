package software.cstl.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.CodeNodeErpApp;
import software.cstl.domain.LeaveApplication;
import software.cstl.domain.LeaveType;
import software.cstl.domain.User;
import software.cstl.domain.enumeration.LeaveApplicationStatus;
import software.cstl.repository.LeaveApplicationRepository;
import software.cstl.service.LeaveApplicationQueryService;
import software.cstl.service.LeaveApplicationService;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link LeaveApplicationResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LeaveApplicationResourceIT {

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_TOTAL_DAYS = 1;
    private static final Integer UPDATED_TOTAL_DAYS = 2;
    private static final Integer SMALLER_TOTAL_DAYS = 1 - 1;

    private static final LeaveApplicationStatus DEFAULT_STATUS = LeaveApplicationStatus.APPLIED;
    private static final LeaveApplicationStatus UPDATED_STATUS = LeaveApplicationStatus.ACCEPTED_BY_FIRST_AUTHORITY;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @Autowired
    private LeaveApplicationQueryService leaveApplicationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveApplicationMockMvc;

    private LeaveApplication leaveApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveApplication createEntity(EntityManager em) {
        LeaveApplication leaveApplication = new LeaveApplication()
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .totalDays(DEFAULT_TOTAL_DAYS)
            .status(DEFAULT_STATUS)
            .reason(DEFAULT_REASON);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        leaveApplication.setAppliedBy(user);
        // Add required entity
        LeaveType leaveType;
        if (TestUtil.findAll(em, LeaveType.class).isEmpty()) {
            leaveType = LeaveTypeResourceIT.createEntity(em);
            em.persist(leaveType);
            em.flush();
        } else {
            leaveType = TestUtil.findAll(em, LeaveType.class).get(0);
        }
        leaveApplication.setLeaveType(leaveType);
        return leaveApplication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveApplication createUpdatedEntity(EntityManager em) {
        LeaveApplication leaveApplication = new LeaveApplication()
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .totalDays(UPDATED_TOTAL_DAYS)
            .status(UPDATED_STATUS)
            .reason(UPDATED_REASON);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        leaveApplication.setAppliedBy(user);
        // Add required entity
        LeaveType leaveType;
        if (TestUtil.findAll(em, LeaveType.class).isEmpty()) {
            leaveType = LeaveTypeResourceIT.createUpdatedEntity(em);
            em.persist(leaveType);
            em.flush();
        } else {
            leaveType = TestUtil.findAll(em, LeaveType.class).get(0);
        }
        leaveApplication.setLeaveType(leaveType);
        return leaveApplication;
    }

    @BeforeEach
    public void initTest() {
        leaveApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveApplication() throws Exception {
        int databaseSizeBeforeCreate = leaveApplicationRepository.findAll().size();
        // Create the LeaveApplication
        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isCreated());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveApplication testLeaveApplication = leaveApplicationList.get(leaveApplicationList.size() - 1);
        assertThat(testLeaveApplication.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testLeaveApplication.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testLeaveApplication.getTotalDays()).isEqualTo(DEFAULT_TOTAL_DAYS);
        assertThat(testLeaveApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeaveApplication.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createLeaveApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveApplicationRepository.findAll().size();

        // Create the LeaveApplication with an existing ID
        leaveApplication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setFrom(null);

        // Create the LeaveApplication, which fails.


        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setTo(null);

        // Create the LeaveApplication, which fails.


        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setTotalDays(null);

        // Create the LeaveApplication, which fails.


        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setStatus(null);

        // Create the LeaveApplication, which fails.


        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setReason(null);

        // Create the LeaveApplication, which fails.


        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeaveApplications() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));
    }

    @Test
    @Transactional
    public void getLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get the leaveApplication
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/{id}", leaveApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveApplication.getId().intValue()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.totalDays").value(DEFAULT_TOTAL_DAYS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON));
    }


    @Test
    @Transactional
    public void getLeaveApplicationsByIdFiltering() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        Long id = leaveApplication.getId();

        defaultLeaveApplicationShouldBeFound("id.equals=" + id);
        defaultLeaveApplicationShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveApplicationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveApplicationShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveApplicationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveApplicationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from equals to DEFAULT_FROM
        defaultLeaveApplicationShouldBeFound("from.equals=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from equals to UPDATED_FROM
        defaultLeaveApplicationShouldNotBeFound("from.equals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from not equals to DEFAULT_FROM
        defaultLeaveApplicationShouldNotBeFound("from.notEquals=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from not equals to UPDATED_FROM
        defaultLeaveApplicationShouldBeFound("from.notEquals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from in DEFAULT_FROM or UPDATED_FROM
        defaultLeaveApplicationShouldBeFound("from.in=" + DEFAULT_FROM + "," + UPDATED_FROM);

        // Get all the leaveApplicationList where from equals to UPDATED_FROM
        defaultLeaveApplicationShouldNotBeFound("from.in=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from is not null
        defaultLeaveApplicationShouldBeFound("from.specified=true");

        // Get all the leaveApplicationList where from is null
        defaultLeaveApplicationShouldNotBeFound("from.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from is greater than or equal to DEFAULT_FROM
        defaultLeaveApplicationShouldBeFound("from.greaterThanOrEqual=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from is greater than or equal to UPDATED_FROM
        defaultLeaveApplicationShouldNotBeFound("from.greaterThanOrEqual=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from is less than or equal to DEFAULT_FROM
        defaultLeaveApplicationShouldBeFound("from.lessThanOrEqual=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from is less than or equal to SMALLER_FROM
        defaultLeaveApplicationShouldNotBeFound("from.lessThanOrEqual=" + SMALLER_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from is less than DEFAULT_FROM
        defaultLeaveApplicationShouldNotBeFound("from.lessThan=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from is less than UPDATED_FROM
        defaultLeaveApplicationShouldBeFound("from.lessThan=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from is greater than DEFAULT_FROM
        defaultLeaveApplicationShouldNotBeFound("from.greaterThan=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from is greater than SMALLER_FROM
        defaultLeaveApplicationShouldBeFound("from.greaterThan=" + SMALLER_FROM);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to equals to DEFAULT_TO
        defaultLeaveApplicationShouldBeFound("to.equals=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to equals to UPDATED_TO
        defaultLeaveApplicationShouldNotBeFound("to.equals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to not equals to DEFAULT_TO
        defaultLeaveApplicationShouldNotBeFound("to.notEquals=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to not equals to UPDATED_TO
        defaultLeaveApplicationShouldBeFound("to.notEquals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to in DEFAULT_TO or UPDATED_TO
        defaultLeaveApplicationShouldBeFound("to.in=" + DEFAULT_TO + "," + UPDATED_TO);

        // Get all the leaveApplicationList where to equals to UPDATED_TO
        defaultLeaveApplicationShouldNotBeFound("to.in=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to is not null
        defaultLeaveApplicationShouldBeFound("to.specified=true");

        // Get all the leaveApplicationList where to is null
        defaultLeaveApplicationShouldNotBeFound("to.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to is greater than or equal to DEFAULT_TO
        defaultLeaveApplicationShouldBeFound("to.greaterThanOrEqual=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to is greater than or equal to UPDATED_TO
        defaultLeaveApplicationShouldNotBeFound("to.greaterThanOrEqual=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to is less than or equal to DEFAULT_TO
        defaultLeaveApplicationShouldBeFound("to.lessThanOrEqual=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to is less than or equal to SMALLER_TO
        defaultLeaveApplicationShouldNotBeFound("to.lessThanOrEqual=" + SMALLER_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to is less than DEFAULT_TO
        defaultLeaveApplicationShouldNotBeFound("to.lessThan=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to is less than UPDATED_TO
        defaultLeaveApplicationShouldBeFound("to.lessThan=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to is greater than DEFAULT_TO
        defaultLeaveApplicationShouldNotBeFound("to.greaterThan=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to is greater than SMALLER_TO
        defaultLeaveApplicationShouldBeFound("to.greaterThan=" + SMALLER_TO);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays equals to DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.equals=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays equals to UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.equals=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays not equals to DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.notEquals=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays not equals to UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.notEquals=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays in DEFAULT_TOTAL_DAYS or UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.in=" + DEFAULT_TOTAL_DAYS + "," + UPDATED_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays equals to UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.in=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays is not null
        defaultLeaveApplicationShouldBeFound("totalDays.specified=true");

        // Get all the leaveApplicationList where totalDays is null
        defaultLeaveApplicationShouldNotBeFound("totalDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays is greater than or equal to DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.greaterThanOrEqual=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays is greater than or equal to UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.greaterThanOrEqual=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays is less than or equal to DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.lessThanOrEqual=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays is less than or equal to SMALLER_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.lessThanOrEqual=" + SMALLER_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays is less than DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.lessThan=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays is less than UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.lessThan=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays is greater than DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.greaterThan=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays is greater than SMALLER_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.greaterThan=" + SMALLER_TOTAL_DAYS);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status equals to DEFAULT_STATUS
        defaultLeaveApplicationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the leaveApplicationList where status equals to UPDATED_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status not equals to DEFAULT_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the leaveApplicationList where status not equals to UPDATED_STATUS
        defaultLeaveApplicationShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLeaveApplicationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the leaveApplicationList where status equals to UPDATED_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status is not null
        defaultLeaveApplicationShouldBeFound("status.specified=true");

        // Get all the leaveApplicationList where status is null
        defaultLeaveApplicationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason equals to DEFAULT_REASON
        defaultLeaveApplicationShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the leaveApplicationList where reason equals to UPDATED_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason not equals to DEFAULT_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the leaveApplicationList where reason not equals to UPDATED_REASON
        defaultLeaveApplicationShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultLeaveApplicationShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the leaveApplicationList where reason equals to UPDATED_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason is not null
        defaultLeaveApplicationShouldBeFound("reason.specified=true");

        // Get all the leaveApplicationList where reason is null
        defaultLeaveApplicationShouldNotBeFound("reason.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeaveApplicationsByReasonContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason contains DEFAULT_REASON
        defaultLeaveApplicationShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the leaveApplicationList where reason contains UPDATED_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason does not contain DEFAULT_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the leaveApplicationList where reason does not contain UPDATED_REASON
        defaultLeaveApplicationShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        User appliedBy = leaveApplication.getAppliedBy();
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        Long appliedById = appliedBy.getId();

        // Get all the leaveApplicationList where appliedBy equals to appliedById
        defaultLeaveApplicationShouldBeFound("appliedById.equals=" + appliedById);

        // Get all the leaveApplicationList where appliedBy equals to appliedById + 1
        defaultLeaveApplicationShouldNotBeFound("appliedById.equals=" + (appliedById + 1));
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        User actionTakenBy = UserResourceIT.createEntity(em);
        em.persist(actionTakenBy);
        em.flush();
        leaveApplication.setActionTakenBy(actionTakenBy);
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        Long actionTakenById = actionTakenBy.getId();

        // Get all the leaveApplicationList where actionTakenBy equals to actionTakenById
        defaultLeaveApplicationShouldBeFound("actionTakenById.equals=" + actionTakenById);

        // Get all the leaveApplicationList where actionTakenBy equals to actionTakenById + 1
        defaultLeaveApplicationShouldNotBeFound("actionTakenById.equals=" + (actionTakenById + 1));
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByLeaveTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        LeaveType leaveType = leaveApplication.getLeaveType();
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        Long leaveTypeId = leaveType.getId();

        // Get all the leaveApplicationList where leaveType equals to leaveTypeId
        defaultLeaveApplicationShouldBeFound("leaveTypeId.equals=" + leaveTypeId);

        // Get all the leaveApplicationList where leaveType equals to leaveTypeId + 1
        defaultLeaveApplicationShouldNotBeFound("leaveTypeId.equals=" + (leaveTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveApplicationShouldBeFound(String filter) throws Exception {
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));

        // Check, that the count call also returns 1
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveApplicationShouldNotBeFound(String filter) throws Exception {
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLeaveApplication() throws Exception {
        // Get the leaveApplication
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationService.save(leaveApplication);

        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();

        // Update the leaveApplication
        LeaveApplication updatedLeaveApplication = leaveApplicationRepository.findById(leaveApplication.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveApplication are not directly saved in db
        em.detach(updatedLeaveApplication);
        updatedLeaveApplication
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .totalDays(UPDATED_TOTAL_DAYS)
            .status(UPDATED_STATUS)
            .reason(UPDATED_REASON);

        restLeaveApplicationMockMvc.perform(put("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLeaveApplication)))
            .andExpect(status().isOk());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
        LeaveApplication testLeaveApplication = leaveApplicationList.get(leaveApplicationList.size() - 1);
        assertThat(testLeaveApplication.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testLeaveApplication.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testLeaveApplication.getTotalDays()).isEqualTo(UPDATED_TOTAL_DAYS);
        assertThat(testLeaveApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveApplication.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveApplication() throws Exception {
        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveApplicationMockMvc.perform(put("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationService.save(leaveApplication);

        int databaseSizeBeforeDelete = leaveApplicationRepository.findAll().size();

        // Delete the leaveApplication
        restLeaveApplicationMockMvc.perform(delete("/api/leave-applications/{id}", leaveApplication.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
