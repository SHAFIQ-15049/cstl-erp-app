package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.EducationalInfo;
import software.cstl.domain.Employee;
import software.cstl.repository.EducationalInfoRepository;
import software.cstl.service.EducationalInfoService;
import software.cstl.service.dto.EducationalInfoCriteria;
import software.cstl.service.EducationalInfoQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EducationalInfoResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EducationalInfoResourceIT {

    private static final Integer DEFAULT_SERIAL = 1;
    private static final Integer UPDATED_SERIAL = 2;
    private static final Integer SMALLER_SERIAL = 1 - 1;

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTITUTION = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PASSING_YEAR = 1;
    private static final Integer UPDATED_PASSING_YEAR = 2;
    private static final Integer SMALLER_PASSING_YEAR = 1 - 1;

    private static final Integer DEFAULT_COURSE_DURATION = 1;
    private static final Integer UPDATED_COURSE_DURATION = 2;
    private static final Integer SMALLER_COURSE_DURATION = 1 - 1;

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    @Autowired
    private EducationalInfoRepository educationalInfoRepository;

    @Autowired
    private EducationalInfoService educationalInfoService;

    @Autowired
    private EducationalInfoQueryService educationalInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducationalInfoMockMvc;

    private EducationalInfo educationalInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducationalInfo createEntity(EntityManager em) {
        EducationalInfo educationalInfo = new EducationalInfo()
            .serial(DEFAULT_SERIAL)
            .degree(DEFAULT_DEGREE)
            .institution(DEFAULT_INSTITUTION)
            .passingYear(DEFAULT_PASSING_YEAR)
            .courseDuration(DEFAULT_COURSE_DURATION)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        return educationalInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducationalInfo createUpdatedEntity(EntityManager em) {
        EducationalInfo educationalInfo = new EducationalInfo()
            .serial(UPDATED_SERIAL)
            .degree(UPDATED_DEGREE)
            .institution(UPDATED_INSTITUTION)
            .passingYear(UPDATED_PASSING_YEAR)
            .courseDuration(UPDATED_COURSE_DURATION)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);
        return educationalInfo;
    }

    @BeforeEach
    public void initTest() {
        educationalInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducationalInfo() throws Exception {
        int databaseSizeBeforeCreate = educationalInfoRepository.findAll().size();
        // Create the EducationalInfo
        restEducationalInfoMockMvc.perform(post("/api/educational-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationalInfo)))
            .andExpect(status().isCreated());

        // Validate the EducationalInfo in the database
        List<EducationalInfo> educationalInfoList = educationalInfoRepository.findAll();
        assertThat(educationalInfoList).hasSize(databaseSizeBeforeCreate + 1);
        EducationalInfo testEducationalInfo = educationalInfoList.get(educationalInfoList.size() - 1);
        assertThat(testEducationalInfo.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testEducationalInfo.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testEducationalInfo.getInstitution()).isEqualTo(DEFAULT_INSTITUTION);
        assertThat(testEducationalInfo.getPassingYear()).isEqualTo(DEFAULT_PASSING_YEAR);
        assertThat(testEducationalInfo.getCourseDuration()).isEqualTo(DEFAULT_COURSE_DURATION);
        assertThat(testEducationalInfo.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testEducationalInfo.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createEducationalInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationalInfoRepository.findAll().size();

        // Create the EducationalInfo with an existing ID
        educationalInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationalInfoMockMvc.perform(post("/api/educational-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationalInfo)))
            .andExpect(status().isBadRequest());

        // Validate the EducationalInfo in the database
        List<EducationalInfo> educationalInfoList = educationalInfoRepository.findAll();
        assertThat(educationalInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSerialIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalInfoRepository.findAll().size();
        // set the field null
        educationalInfo.setSerial(null);

        // Create the EducationalInfo, which fails.


        restEducationalInfoMockMvc.perform(post("/api/educational-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationalInfo)))
            .andExpect(status().isBadRequest());

        List<EducationalInfo> educationalInfoList = educationalInfoRepository.findAll();
        assertThat(educationalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDegreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalInfoRepository.findAll().size();
        // set the field null
        educationalInfo.setDegree(null);

        // Create the EducationalInfo, which fails.


        restEducationalInfoMockMvc.perform(post("/api/educational-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationalInfo)))
            .andExpect(status().isBadRequest());

        List<EducationalInfo> educationalInfoList = educationalInfoRepository.findAll();
        assertThat(educationalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstitutionIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalInfoRepository.findAll().size();
        // set the field null
        educationalInfo.setInstitution(null);

        // Create the EducationalInfo, which fails.


        restEducationalInfoMockMvc.perform(post("/api/educational-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationalInfo)))
            .andExpect(status().isBadRequest());

        List<EducationalInfo> educationalInfoList = educationalInfoRepository.findAll();
        assertThat(educationalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassingYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalInfoRepository.findAll().size();
        // set the field null
        educationalInfo.setPassingYear(null);

        // Create the EducationalInfo, which fails.


        restEducationalInfoMockMvc.perform(post("/api/educational-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationalInfo)))
            .andExpect(status().isBadRequest());

        List<EducationalInfo> educationalInfoList = educationalInfoRepository.findAll();
        assertThat(educationalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEducationalInfos() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList
        restEducationalInfoMockMvc.perform(get("/api/educational-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL)))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].institution").value(hasItem(DEFAULT_INSTITUTION)))
            .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR)))
            .andExpect(jsonPath("$.[*].courseDuration").value(hasItem(DEFAULT_COURSE_DURATION)))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))));
    }
    
    @Test
    @Transactional
    public void getEducationalInfo() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get the educationalInfo
        restEducationalInfoMockMvc.perform(get("/api/educational-infos/{id}", educationalInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(educationalInfo.getId().intValue()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE))
            .andExpect(jsonPath("$.institution").value(DEFAULT_INSTITUTION))
            .andExpect(jsonPath("$.passingYear").value(DEFAULT_PASSING_YEAR))
            .andExpect(jsonPath("$.courseDuration").value(DEFAULT_COURSE_DURATION))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)));
    }


    @Test
    @Transactional
    public void getEducationalInfosByIdFiltering() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        Long id = educationalInfo.getId();

        defaultEducationalInfoShouldBeFound("id.equals=" + id);
        defaultEducationalInfoShouldNotBeFound("id.notEquals=" + id);

        defaultEducationalInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEducationalInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultEducationalInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEducationalInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEducationalInfosBySerialIsEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where serial equals to DEFAULT_SERIAL
        defaultEducationalInfoShouldBeFound("serial.equals=" + DEFAULT_SERIAL);

        // Get all the educationalInfoList where serial equals to UPDATED_SERIAL
        defaultEducationalInfoShouldNotBeFound("serial.equals=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosBySerialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where serial not equals to DEFAULT_SERIAL
        defaultEducationalInfoShouldNotBeFound("serial.notEquals=" + DEFAULT_SERIAL);

        // Get all the educationalInfoList where serial not equals to UPDATED_SERIAL
        defaultEducationalInfoShouldBeFound("serial.notEquals=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosBySerialIsInShouldWork() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where serial in DEFAULT_SERIAL or UPDATED_SERIAL
        defaultEducationalInfoShouldBeFound("serial.in=" + DEFAULT_SERIAL + "," + UPDATED_SERIAL);

        // Get all the educationalInfoList where serial equals to UPDATED_SERIAL
        defaultEducationalInfoShouldNotBeFound("serial.in=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosBySerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where serial is not null
        defaultEducationalInfoShouldBeFound("serial.specified=true");

        // Get all the educationalInfoList where serial is null
        defaultEducationalInfoShouldNotBeFound("serial.specified=false");
    }

    @Test
    @Transactional
    public void getAllEducationalInfosBySerialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where serial is greater than or equal to DEFAULT_SERIAL
        defaultEducationalInfoShouldBeFound("serial.greaterThanOrEqual=" + DEFAULT_SERIAL);

        // Get all the educationalInfoList where serial is greater than or equal to UPDATED_SERIAL
        defaultEducationalInfoShouldNotBeFound("serial.greaterThanOrEqual=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosBySerialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where serial is less than or equal to DEFAULT_SERIAL
        defaultEducationalInfoShouldBeFound("serial.lessThanOrEqual=" + DEFAULT_SERIAL);

        // Get all the educationalInfoList where serial is less than or equal to SMALLER_SERIAL
        defaultEducationalInfoShouldNotBeFound("serial.lessThanOrEqual=" + SMALLER_SERIAL);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosBySerialIsLessThanSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where serial is less than DEFAULT_SERIAL
        defaultEducationalInfoShouldNotBeFound("serial.lessThan=" + DEFAULT_SERIAL);

        // Get all the educationalInfoList where serial is less than UPDATED_SERIAL
        defaultEducationalInfoShouldBeFound("serial.lessThan=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosBySerialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where serial is greater than DEFAULT_SERIAL
        defaultEducationalInfoShouldNotBeFound("serial.greaterThan=" + DEFAULT_SERIAL);

        // Get all the educationalInfoList where serial is greater than SMALLER_SERIAL
        defaultEducationalInfoShouldBeFound("serial.greaterThan=" + SMALLER_SERIAL);
    }


    @Test
    @Transactional
    public void getAllEducationalInfosByDegreeIsEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where degree equals to DEFAULT_DEGREE
        defaultEducationalInfoShouldBeFound("degree.equals=" + DEFAULT_DEGREE);

        // Get all the educationalInfoList where degree equals to UPDATED_DEGREE
        defaultEducationalInfoShouldNotBeFound("degree.equals=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByDegreeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where degree not equals to DEFAULT_DEGREE
        defaultEducationalInfoShouldNotBeFound("degree.notEquals=" + DEFAULT_DEGREE);

        // Get all the educationalInfoList where degree not equals to UPDATED_DEGREE
        defaultEducationalInfoShouldBeFound("degree.notEquals=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByDegreeIsInShouldWork() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where degree in DEFAULT_DEGREE or UPDATED_DEGREE
        defaultEducationalInfoShouldBeFound("degree.in=" + DEFAULT_DEGREE + "," + UPDATED_DEGREE);

        // Get all the educationalInfoList where degree equals to UPDATED_DEGREE
        defaultEducationalInfoShouldNotBeFound("degree.in=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByDegreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where degree is not null
        defaultEducationalInfoShouldBeFound("degree.specified=true");

        // Get all the educationalInfoList where degree is null
        defaultEducationalInfoShouldNotBeFound("degree.specified=false");
    }
                @Test
    @Transactional
    public void getAllEducationalInfosByDegreeContainsSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where degree contains DEFAULT_DEGREE
        defaultEducationalInfoShouldBeFound("degree.contains=" + DEFAULT_DEGREE);

        // Get all the educationalInfoList where degree contains UPDATED_DEGREE
        defaultEducationalInfoShouldNotBeFound("degree.contains=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByDegreeNotContainsSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where degree does not contain DEFAULT_DEGREE
        defaultEducationalInfoShouldNotBeFound("degree.doesNotContain=" + DEFAULT_DEGREE);

        // Get all the educationalInfoList where degree does not contain UPDATED_DEGREE
        defaultEducationalInfoShouldBeFound("degree.doesNotContain=" + UPDATED_DEGREE);
    }


    @Test
    @Transactional
    public void getAllEducationalInfosByInstitutionIsEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where institution equals to DEFAULT_INSTITUTION
        defaultEducationalInfoShouldBeFound("institution.equals=" + DEFAULT_INSTITUTION);

        // Get all the educationalInfoList where institution equals to UPDATED_INSTITUTION
        defaultEducationalInfoShouldNotBeFound("institution.equals=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByInstitutionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where institution not equals to DEFAULT_INSTITUTION
        defaultEducationalInfoShouldNotBeFound("institution.notEquals=" + DEFAULT_INSTITUTION);

        // Get all the educationalInfoList where institution not equals to UPDATED_INSTITUTION
        defaultEducationalInfoShouldBeFound("institution.notEquals=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByInstitutionIsInShouldWork() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where institution in DEFAULT_INSTITUTION or UPDATED_INSTITUTION
        defaultEducationalInfoShouldBeFound("institution.in=" + DEFAULT_INSTITUTION + "," + UPDATED_INSTITUTION);

        // Get all the educationalInfoList where institution equals to UPDATED_INSTITUTION
        defaultEducationalInfoShouldNotBeFound("institution.in=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByInstitutionIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where institution is not null
        defaultEducationalInfoShouldBeFound("institution.specified=true");

        // Get all the educationalInfoList where institution is null
        defaultEducationalInfoShouldNotBeFound("institution.specified=false");
    }
                @Test
    @Transactional
    public void getAllEducationalInfosByInstitutionContainsSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where institution contains DEFAULT_INSTITUTION
        defaultEducationalInfoShouldBeFound("institution.contains=" + DEFAULT_INSTITUTION);

        // Get all the educationalInfoList where institution contains UPDATED_INSTITUTION
        defaultEducationalInfoShouldNotBeFound("institution.contains=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByInstitutionNotContainsSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where institution does not contain DEFAULT_INSTITUTION
        defaultEducationalInfoShouldNotBeFound("institution.doesNotContain=" + DEFAULT_INSTITUTION);

        // Get all the educationalInfoList where institution does not contain UPDATED_INSTITUTION
        defaultEducationalInfoShouldBeFound("institution.doesNotContain=" + UPDATED_INSTITUTION);
    }


    @Test
    @Transactional
    public void getAllEducationalInfosByPassingYearIsEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where passingYear equals to DEFAULT_PASSING_YEAR
        defaultEducationalInfoShouldBeFound("passingYear.equals=" + DEFAULT_PASSING_YEAR);

        // Get all the educationalInfoList where passingYear equals to UPDATED_PASSING_YEAR
        defaultEducationalInfoShouldNotBeFound("passingYear.equals=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByPassingYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where passingYear not equals to DEFAULT_PASSING_YEAR
        defaultEducationalInfoShouldNotBeFound("passingYear.notEquals=" + DEFAULT_PASSING_YEAR);

        // Get all the educationalInfoList where passingYear not equals to UPDATED_PASSING_YEAR
        defaultEducationalInfoShouldBeFound("passingYear.notEquals=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByPassingYearIsInShouldWork() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where passingYear in DEFAULT_PASSING_YEAR or UPDATED_PASSING_YEAR
        defaultEducationalInfoShouldBeFound("passingYear.in=" + DEFAULT_PASSING_YEAR + "," + UPDATED_PASSING_YEAR);

        // Get all the educationalInfoList where passingYear equals to UPDATED_PASSING_YEAR
        defaultEducationalInfoShouldNotBeFound("passingYear.in=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByPassingYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where passingYear is not null
        defaultEducationalInfoShouldBeFound("passingYear.specified=true");

        // Get all the educationalInfoList where passingYear is null
        defaultEducationalInfoShouldNotBeFound("passingYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByPassingYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where passingYear is greater than or equal to DEFAULT_PASSING_YEAR
        defaultEducationalInfoShouldBeFound("passingYear.greaterThanOrEqual=" + DEFAULT_PASSING_YEAR);

        // Get all the educationalInfoList where passingYear is greater than or equal to UPDATED_PASSING_YEAR
        defaultEducationalInfoShouldNotBeFound("passingYear.greaterThanOrEqual=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByPassingYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where passingYear is less than or equal to DEFAULT_PASSING_YEAR
        defaultEducationalInfoShouldBeFound("passingYear.lessThanOrEqual=" + DEFAULT_PASSING_YEAR);

        // Get all the educationalInfoList where passingYear is less than or equal to SMALLER_PASSING_YEAR
        defaultEducationalInfoShouldNotBeFound("passingYear.lessThanOrEqual=" + SMALLER_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByPassingYearIsLessThanSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where passingYear is less than DEFAULT_PASSING_YEAR
        defaultEducationalInfoShouldNotBeFound("passingYear.lessThan=" + DEFAULT_PASSING_YEAR);

        // Get all the educationalInfoList where passingYear is less than UPDATED_PASSING_YEAR
        defaultEducationalInfoShouldBeFound("passingYear.lessThan=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByPassingYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where passingYear is greater than DEFAULT_PASSING_YEAR
        defaultEducationalInfoShouldNotBeFound("passingYear.greaterThan=" + DEFAULT_PASSING_YEAR);

        // Get all the educationalInfoList where passingYear is greater than SMALLER_PASSING_YEAR
        defaultEducationalInfoShouldBeFound("passingYear.greaterThan=" + SMALLER_PASSING_YEAR);
    }


    @Test
    @Transactional
    public void getAllEducationalInfosByCourseDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where courseDuration equals to DEFAULT_COURSE_DURATION
        defaultEducationalInfoShouldBeFound("courseDuration.equals=" + DEFAULT_COURSE_DURATION);

        // Get all the educationalInfoList where courseDuration equals to UPDATED_COURSE_DURATION
        defaultEducationalInfoShouldNotBeFound("courseDuration.equals=" + UPDATED_COURSE_DURATION);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByCourseDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where courseDuration not equals to DEFAULT_COURSE_DURATION
        defaultEducationalInfoShouldNotBeFound("courseDuration.notEquals=" + DEFAULT_COURSE_DURATION);

        // Get all the educationalInfoList where courseDuration not equals to UPDATED_COURSE_DURATION
        defaultEducationalInfoShouldBeFound("courseDuration.notEquals=" + UPDATED_COURSE_DURATION);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByCourseDurationIsInShouldWork() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where courseDuration in DEFAULT_COURSE_DURATION or UPDATED_COURSE_DURATION
        defaultEducationalInfoShouldBeFound("courseDuration.in=" + DEFAULT_COURSE_DURATION + "," + UPDATED_COURSE_DURATION);

        // Get all the educationalInfoList where courseDuration equals to UPDATED_COURSE_DURATION
        defaultEducationalInfoShouldNotBeFound("courseDuration.in=" + UPDATED_COURSE_DURATION);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByCourseDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where courseDuration is not null
        defaultEducationalInfoShouldBeFound("courseDuration.specified=true");

        // Get all the educationalInfoList where courseDuration is null
        defaultEducationalInfoShouldNotBeFound("courseDuration.specified=false");
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByCourseDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where courseDuration is greater than or equal to DEFAULT_COURSE_DURATION
        defaultEducationalInfoShouldBeFound("courseDuration.greaterThanOrEqual=" + DEFAULT_COURSE_DURATION);

        // Get all the educationalInfoList where courseDuration is greater than or equal to UPDATED_COURSE_DURATION
        defaultEducationalInfoShouldNotBeFound("courseDuration.greaterThanOrEqual=" + UPDATED_COURSE_DURATION);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByCourseDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where courseDuration is less than or equal to DEFAULT_COURSE_DURATION
        defaultEducationalInfoShouldBeFound("courseDuration.lessThanOrEqual=" + DEFAULT_COURSE_DURATION);

        // Get all the educationalInfoList where courseDuration is less than or equal to SMALLER_COURSE_DURATION
        defaultEducationalInfoShouldNotBeFound("courseDuration.lessThanOrEqual=" + SMALLER_COURSE_DURATION);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByCourseDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where courseDuration is less than DEFAULT_COURSE_DURATION
        defaultEducationalInfoShouldNotBeFound("courseDuration.lessThan=" + DEFAULT_COURSE_DURATION);

        // Get all the educationalInfoList where courseDuration is less than UPDATED_COURSE_DURATION
        defaultEducationalInfoShouldBeFound("courseDuration.lessThan=" + UPDATED_COURSE_DURATION);
    }

    @Test
    @Transactional
    public void getAllEducationalInfosByCourseDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);

        // Get all the educationalInfoList where courseDuration is greater than DEFAULT_COURSE_DURATION
        defaultEducationalInfoShouldNotBeFound("courseDuration.greaterThan=" + DEFAULT_COURSE_DURATION);

        // Get all the educationalInfoList where courseDuration is greater than SMALLER_COURSE_DURATION
        defaultEducationalInfoShouldBeFound("courseDuration.greaterThan=" + SMALLER_COURSE_DURATION);
    }


    @Test
    @Transactional
    public void getAllEducationalInfosByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        educationalInfoRepository.saveAndFlush(educationalInfo);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        educationalInfo.setEmployee(employee);
        educationalInfoRepository.saveAndFlush(educationalInfo);
        Long employeeId = employee.getId();

        // Get all the educationalInfoList where employee equals to employeeId
        defaultEducationalInfoShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the educationalInfoList where employee equals to employeeId + 1
        defaultEducationalInfoShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEducationalInfoShouldBeFound(String filter) throws Exception {
        restEducationalInfoMockMvc.perform(get("/api/educational-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL)))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].institution").value(hasItem(DEFAULT_INSTITUTION)))
            .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR)))
            .andExpect(jsonPath("$.[*].courseDuration").value(hasItem(DEFAULT_COURSE_DURATION)))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))));

        // Check, that the count call also returns 1
        restEducationalInfoMockMvc.perform(get("/api/educational-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEducationalInfoShouldNotBeFound(String filter) throws Exception {
        restEducationalInfoMockMvc.perform(get("/api/educational-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEducationalInfoMockMvc.perform(get("/api/educational-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEducationalInfo() throws Exception {
        // Get the educationalInfo
        restEducationalInfoMockMvc.perform(get("/api/educational-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationalInfo() throws Exception {
        // Initialize the database
        educationalInfoService.save(educationalInfo);

        int databaseSizeBeforeUpdate = educationalInfoRepository.findAll().size();

        // Update the educationalInfo
        EducationalInfo updatedEducationalInfo = educationalInfoRepository.findById(educationalInfo.getId()).get();
        // Disconnect from session so that the updates on updatedEducationalInfo are not directly saved in db
        em.detach(updatedEducationalInfo);
        updatedEducationalInfo
            .serial(UPDATED_SERIAL)
            .degree(UPDATED_DEGREE)
            .institution(UPDATED_INSTITUTION)
            .passingYear(UPDATED_PASSING_YEAR)
            .courseDuration(UPDATED_COURSE_DURATION)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);

        restEducationalInfoMockMvc.perform(put("/api/educational-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEducationalInfo)))
            .andExpect(status().isOk());

        // Validate the EducationalInfo in the database
        List<EducationalInfo> educationalInfoList = educationalInfoRepository.findAll();
        assertThat(educationalInfoList).hasSize(databaseSizeBeforeUpdate);
        EducationalInfo testEducationalInfo = educationalInfoList.get(educationalInfoList.size() - 1);
        assertThat(testEducationalInfo.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testEducationalInfo.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEducationalInfo.getInstitution()).isEqualTo(UPDATED_INSTITUTION);
        assertThat(testEducationalInfo.getPassingYear()).isEqualTo(UPDATED_PASSING_YEAR);
        assertThat(testEducationalInfo.getCourseDuration()).isEqualTo(UPDATED_COURSE_DURATION);
        assertThat(testEducationalInfo.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testEducationalInfo.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEducationalInfo() throws Exception {
        int databaseSizeBeforeUpdate = educationalInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationalInfoMockMvc.perform(put("/api/educational-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationalInfo)))
            .andExpect(status().isBadRequest());

        // Validate the EducationalInfo in the database
        List<EducationalInfo> educationalInfoList = educationalInfoRepository.findAll();
        assertThat(educationalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEducationalInfo() throws Exception {
        // Initialize the database
        educationalInfoService.save(educationalInfo);

        int databaseSizeBeforeDelete = educationalInfoRepository.findAll().size();

        // Delete the educationalInfo
        restEducationalInfoMockMvc.perform(delete("/api/educational-infos/{id}", educationalInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EducationalInfo> educationalInfoList = educationalInfoRepository.findAll();
        assertThat(educationalInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
