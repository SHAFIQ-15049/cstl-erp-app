package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Training;
import software.cstl.domain.Employee;
import software.cstl.repository.TrainingRepository;
import software.cstl.service.TrainingService;
import software.cstl.service.dto.TrainingCriteria;
import software.cstl.service.TrainingQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TrainingResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TrainingResourceIT {

    private static final Integer DEFAULT_SERIAL = 1;
    private static final Integer UPDATED_SERIAL = 2;
    private static final Integer SMALLER_SERIAL = 1 - 1;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRAINING_INSTITUTE = "AAAAAAAAAA";
    private static final String UPDATED_TRAINING_INSTITUTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RECEIVED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVED_ON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_RECEIVED_ON = LocalDate.ofEpochDay(-1L);

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingQueryService trainingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingMockMvc;

    private Training training;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createEntity(EntityManager em) {
        Training training = new Training()
            .serial(DEFAULT_SERIAL)
            .name(DEFAULT_NAME)
            .trainingInstitute(DEFAULT_TRAINING_INSTITUTE)
            .receivedOn(DEFAULT_RECEIVED_ON)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        return training;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createUpdatedEntity(EntityManager em) {
        Training training = new Training()
            .serial(UPDATED_SERIAL)
            .name(UPDATED_NAME)
            .trainingInstitute(UPDATED_TRAINING_INSTITUTE)
            .receivedOn(UPDATED_RECEIVED_ON)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);
        return training;
    }

    @BeforeEach
    public void initTest() {
        training = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraining() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();
        // Create the Training
        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isCreated());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate + 1);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testTraining.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraining.getTrainingInstitute()).isEqualTo(DEFAULT_TRAINING_INSTITUTE);
        assertThat(testTraining.getReceivedOn()).isEqualTo(DEFAULT_RECEIVED_ON);
        assertThat(testTraining.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testTraining.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTrainingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // Create the Training with an existing ID
        training.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSerialIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setSerial(null);

        // Create the Training, which fails.


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setName(null);

        // Create the Training, which fails.


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrainingInstituteIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setTrainingInstitute(null);

        // Create the Training, which fails.


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrainings() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList
        restTrainingMockMvc.perform(get("/api/trainings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].trainingInstitute").value(hasItem(DEFAULT_TRAINING_INSTITUTE)))
            .andExpect(jsonPath("$.[*].receivedOn").value(hasItem(DEFAULT_RECEIVED_ON.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))));
    }
    
    @Test
    @Transactional
    public void getTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(training.getId().intValue()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.trainingInstitute").value(DEFAULT_TRAINING_INSTITUTE))
            .andExpect(jsonPath("$.receivedOn").value(DEFAULT_RECEIVED_ON.toString()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)));
    }


    @Test
    @Transactional
    public void getTrainingsByIdFiltering() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        Long id = training.getId();

        defaultTrainingShouldBeFound("id.equals=" + id);
        defaultTrainingShouldNotBeFound("id.notEquals=" + id);

        defaultTrainingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrainingShouldNotBeFound("id.greaterThan=" + id);

        defaultTrainingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrainingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTrainingsBySerialIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where serial equals to DEFAULT_SERIAL
        defaultTrainingShouldBeFound("serial.equals=" + DEFAULT_SERIAL);

        // Get all the trainingList where serial equals to UPDATED_SERIAL
        defaultTrainingShouldNotBeFound("serial.equals=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllTrainingsBySerialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where serial not equals to DEFAULT_SERIAL
        defaultTrainingShouldNotBeFound("serial.notEquals=" + DEFAULT_SERIAL);

        // Get all the trainingList where serial not equals to UPDATED_SERIAL
        defaultTrainingShouldBeFound("serial.notEquals=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllTrainingsBySerialIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where serial in DEFAULT_SERIAL or UPDATED_SERIAL
        defaultTrainingShouldBeFound("serial.in=" + DEFAULT_SERIAL + "," + UPDATED_SERIAL);

        // Get all the trainingList where serial equals to UPDATED_SERIAL
        defaultTrainingShouldNotBeFound("serial.in=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllTrainingsBySerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where serial is not null
        defaultTrainingShouldBeFound("serial.specified=true");

        // Get all the trainingList where serial is null
        defaultTrainingShouldNotBeFound("serial.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrainingsBySerialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where serial is greater than or equal to DEFAULT_SERIAL
        defaultTrainingShouldBeFound("serial.greaterThanOrEqual=" + DEFAULT_SERIAL);

        // Get all the trainingList where serial is greater than or equal to UPDATED_SERIAL
        defaultTrainingShouldNotBeFound("serial.greaterThanOrEqual=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllTrainingsBySerialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where serial is less than or equal to DEFAULT_SERIAL
        defaultTrainingShouldBeFound("serial.lessThanOrEqual=" + DEFAULT_SERIAL);

        // Get all the trainingList where serial is less than or equal to SMALLER_SERIAL
        defaultTrainingShouldNotBeFound("serial.lessThanOrEqual=" + SMALLER_SERIAL);
    }

    @Test
    @Transactional
    public void getAllTrainingsBySerialIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where serial is less than DEFAULT_SERIAL
        defaultTrainingShouldNotBeFound("serial.lessThan=" + DEFAULT_SERIAL);

        // Get all the trainingList where serial is less than UPDATED_SERIAL
        defaultTrainingShouldBeFound("serial.lessThan=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllTrainingsBySerialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where serial is greater than DEFAULT_SERIAL
        defaultTrainingShouldNotBeFound("serial.greaterThan=" + DEFAULT_SERIAL);

        // Get all the trainingList where serial is greater than SMALLER_SERIAL
        defaultTrainingShouldBeFound("serial.greaterThan=" + SMALLER_SERIAL);
    }


    @Test
    @Transactional
    public void getAllTrainingsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where name equals to DEFAULT_NAME
        defaultTrainingShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the trainingList where name equals to UPDATED_NAME
        defaultTrainingShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTrainingsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where name not equals to DEFAULT_NAME
        defaultTrainingShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the trainingList where name not equals to UPDATED_NAME
        defaultTrainingShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTrainingsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTrainingShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the trainingList where name equals to UPDATED_NAME
        defaultTrainingShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTrainingsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where name is not null
        defaultTrainingShouldBeFound("name.specified=true");

        // Get all the trainingList where name is null
        defaultTrainingShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrainingsByNameContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where name contains DEFAULT_NAME
        defaultTrainingShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the trainingList where name contains UPDATED_NAME
        defaultTrainingShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTrainingsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where name does not contain DEFAULT_NAME
        defaultTrainingShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the trainingList where name does not contain UPDATED_NAME
        defaultTrainingShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTrainingsByTrainingInstituteIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingInstitute equals to DEFAULT_TRAINING_INSTITUTE
        defaultTrainingShouldBeFound("trainingInstitute.equals=" + DEFAULT_TRAINING_INSTITUTE);

        // Get all the trainingList where trainingInstitute equals to UPDATED_TRAINING_INSTITUTE
        defaultTrainingShouldNotBeFound("trainingInstitute.equals=" + UPDATED_TRAINING_INSTITUTE);
    }

    @Test
    @Transactional
    public void getAllTrainingsByTrainingInstituteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingInstitute not equals to DEFAULT_TRAINING_INSTITUTE
        defaultTrainingShouldNotBeFound("trainingInstitute.notEquals=" + DEFAULT_TRAINING_INSTITUTE);

        // Get all the trainingList where trainingInstitute not equals to UPDATED_TRAINING_INSTITUTE
        defaultTrainingShouldBeFound("trainingInstitute.notEquals=" + UPDATED_TRAINING_INSTITUTE);
    }

    @Test
    @Transactional
    public void getAllTrainingsByTrainingInstituteIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingInstitute in DEFAULT_TRAINING_INSTITUTE or UPDATED_TRAINING_INSTITUTE
        defaultTrainingShouldBeFound("trainingInstitute.in=" + DEFAULT_TRAINING_INSTITUTE + "," + UPDATED_TRAINING_INSTITUTE);

        // Get all the trainingList where trainingInstitute equals to UPDATED_TRAINING_INSTITUTE
        defaultTrainingShouldNotBeFound("trainingInstitute.in=" + UPDATED_TRAINING_INSTITUTE);
    }

    @Test
    @Transactional
    public void getAllTrainingsByTrainingInstituteIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingInstitute is not null
        defaultTrainingShouldBeFound("trainingInstitute.specified=true");

        // Get all the trainingList where trainingInstitute is null
        defaultTrainingShouldNotBeFound("trainingInstitute.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrainingsByTrainingInstituteContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingInstitute contains DEFAULT_TRAINING_INSTITUTE
        defaultTrainingShouldBeFound("trainingInstitute.contains=" + DEFAULT_TRAINING_INSTITUTE);

        // Get all the trainingList where trainingInstitute contains UPDATED_TRAINING_INSTITUTE
        defaultTrainingShouldNotBeFound("trainingInstitute.contains=" + UPDATED_TRAINING_INSTITUTE);
    }

    @Test
    @Transactional
    public void getAllTrainingsByTrainingInstituteNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingInstitute does not contain DEFAULT_TRAINING_INSTITUTE
        defaultTrainingShouldNotBeFound("trainingInstitute.doesNotContain=" + DEFAULT_TRAINING_INSTITUTE);

        // Get all the trainingList where trainingInstitute does not contain UPDATED_TRAINING_INSTITUTE
        defaultTrainingShouldBeFound("trainingInstitute.doesNotContain=" + UPDATED_TRAINING_INSTITUTE);
    }


    @Test
    @Transactional
    public void getAllTrainingsByReceivedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where receivedOn equals to DEFAULT_RECEIVED_ON
        defaultTrainingShouldBeFound("receivedOn.equals=" + DEFAULT_RECEIVED_ON);

        // Get all the trainingList where receivedOn equals to UPDATED_RECEIVED_ON
        defaultTrainingShouldNotBeFound("receivedOn.equals=" + UPDATED_RECEIVED_ON);
    }

    @Test
    @Transactional
    public void getAllTrainingsByReceivedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where receivedOn not equals to DEFAULT_RECEIVED_ON
        defaultTrainingShouldNotBeFound("receivedOn.notEquals=" + DEFAULT_RECEIVED_ON);

        // Get all the trainingList where receivedOn not equals to UPDATED_RECEIVED_ON
        defaultTrainingShouldBeFound("receivedOn.notEquals=" + UPDATED_RECEIVED_ON);
    }

    @Test
    @Transactional
    public void getAllTrainingsByReceivedOnIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where receivedOn in DEFAULT_RECEIVED_ON or UPDATED_RECEIVED_ON
        defaultTrainingShouldBeFound("receivedOn.in=" + DEFAULT_RECEIVED_ON + "," + UPDATED_RECEIVED_ON);

        // Get all the trainingList where receivedOn equals to UPDATED_RECEIVED_ON
        defaultTrainingShouldNotBeFound("receivedOn.in=" + UPDATED_RECEIVED_ON);
    }

    @Test
    @Transactional
    public void getAllTrainingsByReceivedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where receivedOn is not null
        defaultTrainingShouldBeFound("receivedOn.specified=true");

        // Get all the trainingList where receivedOn is null
        defaultTrainingShouldNotBeFound("receivedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrainingsByReceivedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where receivedOn is greater than or equal to DEFAULT_RECEIVED_ON
        defaultTrainingShouldBeFound("receivedOn.greaterThanOrEqual=" + DEFAULT_RECEIVED_ON);

        // Get all the trainingList where receivedOn is greater than or equal to UPDATED_RECEIVED_ON
        defaultTrainingShouldNotBeFound("receivedOn.greaterThanOrEqual=" + UPDATED_RECEIVED_ON);
    }

    @Test
    @Transactional
    public void getAllTrainingsByReceivedOnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where receivedOn is less than or equal to DEFAULT_RECEIVED_ON
        defaultTrainingShouldBeFound("receivedOn.lessThanOrEqual=" + DEFAULT_RECEIVED_ON);

        // Get all the trainingList where receivedOn is less than or equal to SMALLER_RECEIVED_ON
        defaultTrainingShouldNotBeFound("receivedOn.lessThanOrEqual=" + SMALLER_RECEIVED_ON);
    }

    @Test
    @Transactional
    public void getAllTrainingsByReceivedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where receivedOn is less than DEFAULT_RECEIVED_ON
        defaultTrainingShouldNotBeFound("receivedOn.lessThan=" + DEFAULT_RECEIVED_ON);

        // Get all the trainingList where receivedOn is less than UPDATED_RECEIVED_ON
        defaultTrainingShouldBeFound("receivedOn.lessThan=" + UPDATED_RECEIVED_ON);
    }

    @Test
    @Transactional
    public void getAllTrainingsByReceivedOnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where receivedOn is greater than DEFAULT_RECEIVED_ON
        defaultTrainingShouldNotBeFound("receivedOn.greaterThan=" + DEFAULT_RECEIVED_ON);

        // Get all the trainingList where receivedOn is greater than SMALLER_RECEIVED_ON
        defaultTrainingShouldBeFound("receivedOn.greaterThan=" + SMALLER_RECEIVED_ON);
    }


    @Test
    @Transactional
    public void getAllTrainingsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        training.setEmployee(employee);
        trainingRepository.saveAndFlush(training);
        Long employeeId = employee.getId();

        // Get all the trainingList where employee equals to employeeId
        defaultTrainingShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the trainingList where employee equals to employeeId + 1
        defaultTrainingShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrainingShouldBeFound(String filter) throws Exception {
        restTrainingMockMvc.perform(get("/api/trainings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].trainingInstitute").value(hasItem(DEFAULT_TRAINING_INSTITUTE)))
            .andExpect(jsonPath("$.[*].receivedOn").value(hasItem(DEFAULT_RECEIVED_ON.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))));

        // Check, that the count call also returns 1
        restTrainingMockMvc.perform(get("/api/trainings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrainingShouldNotBeFound(String filter) throws Exception {
        restTrainingMockMvc.perform(get("/api/trainings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrainingMockMvc.perform(get("/api/trainings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTraining() throws Exception {
        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraining() throws Exception {
        // Initialize the database
        trainingService.save(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training
        Training updatedTraining = trainingRepository.findById(training.getId()).get();
        // Disconnect from session so that the updates on updatedTraining are not directly saved in db
        em.detach(updatedTraining);
        updatedTraining
            .serial(UPDATED_SERIAL)
            .name(UPDATED_NAME)
            .trainingInstitute(UPDATED_TRAINING_INSTITUTE)
            .receivedOn(UPDATED_RECEIVED_ON)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);

        restTrainingMockMvc.perform(put("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTraining)))
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testTraining.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraining.getTrainingInstitute()).isEqualTo(UPDATED_TRAINING_INSTITUTE);
        assertThat(testTraining.getReceivedOn()).isEqualTo(UPDATED_RECEIVED_ON);
        assertThat(testTraining.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testTraining.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingMockMvc.perform(put("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTraining() throws Exception {
        // Initialize the database
        trainingService.save(training);

        int databaseSizeBeforeDelete = trainingRepository.findAll().size();

        // Delete the training
        restTrainingMockMvc.perform(delete("/api/trainings/{id}", training.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
