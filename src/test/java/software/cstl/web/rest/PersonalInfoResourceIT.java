package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.PersonalInfo;
import software.cstl.domain.Employee;
import software.cstl.repository.PersonalInfoRepository;
import software.cstl.service.PersonalInfoService;
import software.cstl.service.dto.PersonalInfoCriteria;
import software.cstl.service.PersonalInfoQueryService;

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

import software.cstl.domain.enumeration.MaritalStatus;
import software.cstl.domain.enumeration.GenderType;
import software.cstl.domain.enumeration.ReligionType;
import software.cstl.domain.enumeration.BloodGroupType;
/**
 * Integration tests for the {@link PersonalInfoResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PersonalInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANGLA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANGLA_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PHOTO_ID = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME_BANGLA = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME_BANGLA = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME_BANGLA = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME_BANGLA = "BBBBBBBBBB";

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.SINGLE;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.MARRIED;

    private static final String DEFAULT_SPOUSE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SPOUSE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SPOUSE_NAME_BANGLA = "AAAAAAAAAA";
    private static final String UPDATED_SPOUSE_NAME_BANGLA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBB";

    private static final byte[] DEFAULT_NATIONAL_ID_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NATIONAL_ID_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_NATIONAL_ID_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NATIONAL_ID_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NATIONAL_ID_ATTACHMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID_ATTACHMENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTH_REGISTRATION = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_REGISTRATION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BIRTH_REGISTRATION_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BIRTH_REGISTRATION_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;
    private static final Double SMALLER_HEIGHT = 1D - 1D;

    private static final GenderType DEFAULT_GENDER = GenderType.MALE;
    private static final GenderType UPDATED_GENDER = GenderType.FEMALE;

    private static final ReligionType DEFAULT_RELIGION = ReligionType.ISLAM;
    private static final ReligionType UPDATED_RELIGION = ReligionType.HINDU;

    private static final BloodGroupType DEFAULT_BLOOD_GROUP = BloodGroupType.A_POSITIVE;
    private static final BloodGroupType UPDATED_BLOOD_GROUP = BloodGroupType.A_NEGATIVE;

    private static final String DEFAULT_EMERGENCY_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_EMERGENCY_CONTACT = "BBBBBBBBBB";

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private PersonalInfoService personalInfoService;

    @Autowired
    private PersonalInfoQueryService personalInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonalInfoMockMvc;

    private PersonalInfo personalInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalInfo createEntity(EntityManager em) {
        PersonalInfo personalInfo = new PersonalInfo()
            .name(DEFAULT_NAME)
            .banglaName(DEFAULT_BANGLA_NAME)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .photoId(DEFAULT_PHOTO_ID)
            .fatherName(DEFAULT_FATHER_NAME)
            .fatherNameBangla(DEFAULT_FATHER_NAME_BANGLA)
            .motherName(DEFAULT_MOTHER_NAME)
            .motherNameBangla(DEFAULT_MOTHER_NAME_BANGLA)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .spouseName(DEFAULT_SPOUSE_NAME)
            .spouseNameBangla(DEFAULT_SPOUSE_NAME_BANGLA)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .nationalId(DEFAULT_NATIONAL_ID)
            .nationalIdAttachment(DEFAULT_NATIONAL_ID_ATTACHMENT)
            .nationalIdAttachmentContentType(DEFAULT_NATIONAL_ID_ATTACHMENT_CONTENT_TYPE)
            .nationalIdAttachmentId(DEFAULT_NATIONAL_ID_ATTACHMENT_ID)
            .birthRegistration(DEFAULT_BIRTH_REGISTRATION)
            .birthRegistrationAttachment(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT)
            .birthRegistrationAttachmentContentType(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE)
            .birthRegistrationAttachmentId(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID)
            .height(DEFAULT_HEIGHT)
            .gender(DEFAULT_GENDER)
            .religion(DEFAULT_RELIGION)
            .bloodGroup(DEFAULT_BLOOD_GROUP)
            .emergencyContact(DEFAULT_EMERGENCY_CONTACT);
        return personalInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalInfo createUpdatedEntity(EntityManager em) {
        PersonalInfo personalInfo = new PersonalInfo()
            .name(UPDATED_NAME)
            .banglaName(UPDATED_BANGLA_NAME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .photoId(UPDATED_PHOTO_ID)
            .fatherName(UPDATED_FATHER_NAME)
            .fatherNameBangla(UPDATED_FATHER_NAME_BANGLA)
            .motherName(UPDATED_MOTHER_NAME)
            .motherNameBangla(UPDATED_MOTHER_NAME_BANGLA)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .spouseName(UPDATED_SPOUSE_NAME)
            .spouseNameBangla(UPDATED_SPOUSE_NAME_BANGLA)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .nationalId(UPDATED_NATIONAL_ID)
            .nationalIdAttachment(UPDATED_NATIONAL_ID_ATTACHMENT)
            .nationalIdAttachmentContentType(UPDATED_NATIONAL_ID_ATTACHMENT_CONTENT_TYPE)
            .nationalIdAttachmentId(UPDATED_NATIONAL_ID_ATTACHMENT_ID)
            .birthRegistration(UPDATED_BIRTH_REGISTRATION)
            .birthRegistrationAttachment(UPDATED_BIRTH_REGISTRATION_ATTACHMENT)
            .birthRegistrationAttachmentContentType(UPDATED_BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE)
            .birthRegistrationAttachmentId(UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID)
            .height(UPDATED_HEIGHT)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .emergencyContact(UPDATED_EMERGENCY_CONTACT);
        return personalInfo;
    }

    @BeforeEach
    public void initTest() {
        personalInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonalInfo() throws Exception {
        int databaseSizeBeforeCreate = personalInfoRepository.findAll().size();
        // Create the PersonalInfo
        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personalInfo)))
            .andExpect(status().isCreated());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PersonalInfo testPersonalInfo = personalInfoList.get(personalInfoList.size() - 1);
        assertThat(testPersonalInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonalInfo.getBanglaName()).isEqualTo(DEFAULT_BANGLA_NAME);
        assertThat(testPersonalInfo.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPersonalInfo.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPersonalInfo.getPhotoId()).isEqualTo(DEFAULT_PHOTO_ID);
        assertThat(testPersonalInfo.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testPersonalInfo.getFatherNameBangla()).isEqualTo(DEFAULT_FATHER_NAME_BANGLA);
        assertThat(testPersonalInfo.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testPersonalInfo.getMotherNameBangla()).isEqualTo(DEFAULT_MOTHER_NAME_BANGLA);
        assertThat(testPersonalInfo.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testPersonalInfo.getSpouseName()).isEqualTo(DEFAULT_SPOUSE_NAME);
        assertThat(testPersonalInfo.getSpouseNameBangla()).isEqualTo(DEFAULT_SPOUSE_NAME_BANGLA);
        assertThat(testPersonalInfo.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPersonalInfo.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testPersonalInfo.getNationalIdAttachment()).isEqualTo(DEFAULT_NATIONAL_ID_ATTACHMENT);
        assertThat(testPersonalInfo.getNationalIdAttachmentContentType()).isEqualTo(DEFAULT_NATIONAL_ID_ATTACHMENT_CONTENT_TYPE);
        assertThat(testPersonalInfo.getNationalIdAttachmentId()).isEqualTo(DEFAULT_NATIONAL_ID_ATTACHMENT_ID);
        assertThat(testPersonalInfo.getBirthRegistration()).isEqualTo(DEFAULT_BIRTH_REGISTRATION);
        assertThat(testPersonalInfo.getBirthRegistrationAttachment()).isEqualTo(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT);
        assertThat(testPersonalInfo.getBirthRegistrationAttachmentContentType()).isEqualTo(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE);
        assertThat(testPersonalInfo.getBirthRegistrationAttachmentId()).isEqualTo(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID);
        assertThat(testPersonalInfo.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testPersonalInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPersonalInfo.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testPersonalInfo.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testPersonalInfo.getEmergencyContact()).isEqualTo(DEFAULT_EMERGENCY_CONTACT);
    }

    @Test
    @Transactional
    public void createPersonalInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalInfoRepository.findAll().size();

        // Create the PersonalInfo with an existing ID
        personalInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personalInfo)))
            .andExpect(status().isBadRequest());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setName(null);

        // Create the PersonalInfo, which fails.


        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personalInfo)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBanglaNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setBanglaName(null);

        // Create the PersonalInfo, which fails.


        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personalInfo)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFatherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setFatherName(null);

        // Create the PersonalInfo, which fails.


        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personalInfo)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setMotherName(null);

        // Create the PersonalInfo, which fails.


        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personalInfo)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonalInfos() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList
        restPersonalInfoMockMvc.perform(get("/api/personal-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].banglaName").value(hasItem(DEFAULT_BANGLA_NAME)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].photoId").value(hasItem(DEFAULT_PHOTO_ID)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].fatherNameBangla").value(hasItem(DEFAULT_FATHER_NAME_BANGLA)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].motherNameBangla").value(hasItem(DEFAULT_MOTHER_NAME_BANGLA)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].spouseName").value(hasItem(DEFAULT_SPOUSE_NAME)))
            .andExpect(jsonPath("$.[*].spouseNameBangla").value(hasItem(DEFAULT_SPOUSE_NAME_BANGLA)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID)))
            .andExpect(jsonPath("$.[*].nationalIdAttachmentContentType").value(hasItem(DEFAULT_NATIONAL_ID_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].nationalIdAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_NATIONAL_ID_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].nationalIdAttachmentId").value(hasItem(DEFAULT_NATIONAL_ID_ATTACHMENT_ID)))
            .andExpect(jsonPath("$.[*].birthRegistration").value(hasItem(DEFAULT_BIRTH_REGISTRATION)))
            .andExpect(jsonPath("$.[*].birthRegistrationAttachmentContentType").value(hasItem(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].birthRegistrationAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].birthRegistrationAttachmentId").value(hasItem(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].emergencyContact").value(hasItem(DEFAULT_EMERGENCY_CONTACT)));
    }
    
    @Test
    @Transactional
    public void getPersonalInfo() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get the personalInfo
        restPersonalInfoMockMvc.perform(get("/api/personal-infos/{id}", personalInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personalInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.banglaName").value(DEFAULT_BANGLA_NAME))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.photoId").value(DEFAULT_PHOTO_ID))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.fatherNameBangla").value(DEFAULT_FATHER_NAME_BANGLA))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME))
            .andExpect(jsonPath("$.motherNameBangla").value(DEFAULT_MOTHER_NAME_BANGLA))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.spouseName").value(DEFAULT_SPOUSE_NAME))
            .andExpect(jsonPath("$.spouseNameBangla").value(DEFAULT_SPOUSE_NAME_BANGLA))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID))
            .andExpect(jsonPath("$.nationalIdAttachmentContentType").value(DEFAULT_NATIONAL_ID_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.nationalIdAttachment").value(Base64Utils.encodeToString(DEFAULT_NATIONAL_ID_ATTACHMENT)))
            .andExpect(jsonPath("$.nationalIdAttachmentId").value(DEFAULT_NATIONAL_ID_ATTACHMENT_ID))
            .andExpect(jsonPath("$.birthRegistration").value(DEFAULT_BIRTH_REGISTRATION))
            .andExpect(jsonPath("$.birthRegistrationAttachmentContentType").value(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.birthRegistrationAttachment").value(Base64Utils.encodeToString(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT)))
            .andExpect(jsonPath("$.birthRegistrationAttachmentId").value(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.emergencyContact").value(DEFAULT_EMERGENCY_CONTACT));
    }


    @Test
    @Transactional
    public void getPersonalInfosByIdFiltering() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        Long id = personalInfo.getId();

        defaultPersonalInfoShouldBeFound("id.equals=" + id);
        defaultPersonalInfoShouldNotBeFound("id.notEquals=" + id);

        defaultPersonalInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPersonalInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultPersonalInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPersonalInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name equals to DEFAULT_NAME
        defaultPersonalInfoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the personalInfoList where name equals to UPDATED_NAME
        defaultPersonalInfoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name not equals to DEFAULT_NAME
        defaultPersonalInfoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the personalInfoList where name not equals to UPDATED_NAME
        defaultPersonalInfoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPersonalInfoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the personalInfoList where name equals to UPDATED_NAME
        defaultPersonalInfoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name is not null
        defaultPersonalInfoShouldBeFound("name.specified=true");

        // Get all the personalInfoList where name is null
        defaultPersonalInfoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByNameContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name contains DEFAULT_NAME
        defaultPersonalInfoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the personalInfoList where name contains UPDATED_NAME
        defaultPersonalInfoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where name does not contain DEFAULT_NAME
        defaultPersonalInfoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the personalInfoList where name does not contain UPDATED_NAME
        defaultPersonalInfoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByBanglaNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where banglaName equals to DEFAULT_BANGLA_NAME
        defaultPersonalInfoShouldBeFound("banglaName.equals=" + DEFAULT_BANGLA_NAME);

        // Get all the personalInfoList where banglaName equals to UPDATED_BANGLA_NAME
        defaultPersonalInfoShouldNotBeFound("banglaName.equals=" + UPDATED_BANGLA_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBanglaNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where banglaName not equals to DEFAULT_BANGLA_NAME
        defaultPersonalInfoShouldNotBeFound("banglaName.notEquals=" + DEFAULT_BANGLA_NAME);

        // Get all the personalInfoList where banglaName not equals to UPDATED_BANGLA_NAME
        defaultPersonalInfoShouldBeFound("banglaName.notEquals=" + UPDATED_BANGLA_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBanglaNameIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where banglaName in DEFAULT_BANGLA_NAME or UPDATED_BANGLA_NAME
        defaultPersonalInfoShouldBeFound("banglaName.in=" + DEFAULT_BANGLA_NAME + "," + UPDATED_BANGLA_NAME);

        // Get all the personalInfoList where banglaName equals to UPDATED_BANGLA_NAME
        defaultPersonalInfoShouldNotBeFound("banglaName.in=" + UPDATED_BANGLA_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBanglaNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where banglaName is not null
        defaultPersonalInfoShouldBeFound("banglaName.specified=true");

        // Get all the personalInfoList where banglaName is null
        defaultPersonalInfoShouldNotBeFound("banglaName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByBanglaNameContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where banglaName contains DEFAULT_BANGLA_NAME
        defaultPersonalInfoShouldBeFound("banglaName.contains=" + DEFAULT_BANGLA_NAME);

        // Get all the personalInfoList where banglaName contains UPDATED_BANGLA_NAME
        defaultPersonalInfoShouldNotBeFound("banglaName.contains=" + UPDATED_BANGLA_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBanglaNameNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where banglaName does not contain DEFAULT_BANGLA_NAME
        defaultPersonalInfoShouldNotBeFound("banglaName.doesNotContain=" + DEFAULT_BANGLA_NAME);

        // Get all the personalInfoList where banglaName does not contain UPDATED_BANGLA_NAME
        defaultPersonalInfoShouldBeFound("banglaName.doesNotContain=" + UPDATED_BANGLA_NAME);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByPhotoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where photoId equals to DEFAULT_PHOTO_ID
        defaultPersonalInfoShouldBeFound("photoId.equals=" + DEFAULT_PHOTO_ID);

        // Get all the personalInfoList where photoId equals to UPDATED_PHOTO_ID
        defaultPersonalInfoShouldNotBeFound("photoId.equals=" + UPDATED_PHOTO_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByPhotoIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where photoId not equals to DEFAULT_PHOTO_ID
        defaultPersonalInfoShouldNotBeFound("photoId.notEquals=" + DEFAULT_PHOTO_ID);

        // Get all the personalInfoList where photoId not equals to UPDATED_PHOTO_ID
        defaultPersonalInfoShouldBeFound("photoId.notEquals=" + UPDATED_PHOTO_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByPhotoIdIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where photoId in DEFAULT_PHOTO_ID or UPDATED_PHOTO_ID
        defaultPersonalInfoShouldBeFound("photoId.in=" + DEFAULT_PHOTO_ID + "," + UPDATED_PHOTO_ID);

        // Get all the personalInfoList where photoId equals to UPDATED_PHOTO_ID
        defaultPersonalInfoShouldNotBeFound("photoId.in=" + UPDATED_PHOTO_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByPhotoIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where photoId is not null
        defaultPersonalInfoShouldBeFound("photoId.specified=true");

        // Get all the personalInfoList where photoId is null
        defaultPersonalInfoShouldNotBeFound("photoId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByPhotoIdContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where photoId contains DEFAULT_PHOTO_ID
        defaultPersonalInfoShouldBeFound("photoId.contains=" + DEFAULT_PHOTO_ID);

        // Get all the personalInfoList where photoId contains UPDATED_PHOTO_ID
        defaultPersonalInfoShouldNotBeFound("photoId.contains=" + UPDATED_PHOTO_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByPhotoIdNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where photoId does not contain DEFAULT_PHOTO_ID
        defaultPersonalInfoShouldNotBeFound("photoId.doesNotContain=" + DEFAULT_PHOTO_ID);

        // Get all the personalInfoList where photoId does not contain UPDATED_PHOTO_ID
        defaultPersonalInfoShouldBeFound("photoId.doesNotContain=" + UPDATED_PHOTO_ID);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherName equals to DEFAULT_FATHER_NAME
        defaultPersonalInfoShouldBeFound("fatherName.equals=" + DEFAULT_FATHER_NAME);

        // Get all the personalInfoList where fatherName equals to UPDATED_FATHER_NAME
        defaultPersonalInfoShouldNotBeFound("fatherName.equals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherName not equals to DEFAULT_FATHER_NAME
        defaultPersonalInfoShouldNotBeFound("fatherName.notEquals=" + DEFAULT_FATHER_NAME);

        // Get all the personalInfoList where fatherName not equals to UPDATED_FATHER_NAME
        defaultPersonalInfoShouldBeFound("fatherName.notEquals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherName in DEFAULT_FATHER_NAME or UPDATED_FATHER_NAME
        defaultPersonalInfoShouldBeFound("fatherName.in=" + DEFAULT_FATHER_NAME + "," + UPDATED_FATHER_NAME);

        // Get all the personalInfoList where fatherName equals to UPDATED_FATHER_NAME
        defaultPersonalInfoShouldNotBeFound("fatherName.in=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherName is not null
        defaultPersonalInfoShouldBeFound("fatherName.specified=true");

        // Get all the personalInfoList where fatherName is null
        defaultPersonalInfoShouldNotBeFound("fatherName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherName contains DEFAULT_FATHER_NAME
        defaultPersonalInfoShouldBeFound("fatherName.contains=" + DEFAULT_FATHER_NAME);

        // Get all the personalInfoList where fatherName contains UPDATED_FATHER_NAME
        defaultPersonalInfoShouldNotBeFound("fatherName.contains=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherName does not contain DEFAULT_FATHER_NAME
        defaultPersonalInfoShouldNotBeFound("fatherName.doesNotContain=" + DEFAULT_FATHER_NAME);

        // Get all the personalInfoList where fatherName does not contain UPDATED_FATHER_NAME
        defaultPersonalInfoShouldBeFound("fatherName.doesNotContain=" + UPDATED_FATHER_NAME);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameBanglaIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherNameBangla equals to DEFAULT_FATHER_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("fatherNameBangla.equals=" + DEFAULT_FATHER_NAME_BANGLA);

        // Get all the personalInfoList where fatherNameBangla equals to UPDATED_FATHER_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("fatherNameBangla.equals=" + UPDATED_FATHER_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameBanglaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherNameBangla not equals to DEFAULT_FATHER_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("fatherNameBangla.notEquals=" + DEFAULT_FATHER_NAME_BANGLA);

        // Get all the personalInfoList where fatherNameBangla not equals to UPDATED_FATHER_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("fatherNameBangla.notEquals=" + UPDATED_FATHER_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameBanglaIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherNameBangla in DEFAULT_FATHER_NAME_BANGLA or UPDATED_FATHER_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("fatherNameBangla.in=" + DEFAULT_FATHER_NAME_BANGLA + "," + UPDATED_FATHER_NAME_BANGLA);

        // Get all the personalInfoList where fatherNameBangla equals to UPDATED_FATHER_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("fatherNameBangla.in=" + UPDATED_FATHER_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameBanglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherNameBangla is not null
        defaultPersonalInfoShouldBeFound("fatherNameBangla.specified=true");

        // Get all the personalInfoList where fatherNameBangla is null
        defaultPersonalInfoShouldNotBeFound("fatherNameBangla.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameBanglaContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherNameBangla contains DEFAULT_FATHER_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("fatherNameBangla.contains=" + DEFAULT_FATHER_NAME_BANGLA);

        // Get all the personalInfoList where fatherNameBangla contains UPDATED_FATHER_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("fatherNameBangla.contains=" + UPDATED_FATHER_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByFatherNameBanglaNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where fatherNameBangla does not contain DEFAULT_FATHER_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("fatherNameBangla.doesNotContain=" + DEFAULT_FATHER_NAME_BANGLA);

        // Get all the personalInfoList where fatherNameBangla does not contain UPDATED_FATHER_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("fatherNameBangla.doesNotContain=" + UPDATED_FATHER_NAME_BANGLA);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherName equals to DEFAULT_MOTHER_NAME
        defaultPersonalInfoShouldBeFound("motherName.equals=" + DEFAULT_MOTHER_NAME);

        // Get all the personalInfoList where motherName equals to UPDATED_MOTHER_NAME
        defaultPersonalInfoShouldNotBeFound("motherName.equals=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherName not equals to DEFAULT_MOTHER_NAME
        defaultPersonalInfoShouldNotBeFound("motherName.notEquals=" + DEFAULT_MOTHER_NAME);

        // Get all the personalInfoList where motherName not equals to UPDATED_MOTHER_NAME
        defaultPersonalInfoShouldBeFound("motherName.notEquals=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherName in DEFAULT_MOTHER_NAME or UPDATED_MOTHER_NAME
        defaultPersonalInfoShouldBeFound("motherName.in=" + DEFAULT_MOTHER_NAME + "," + UPDATED_MOTHER_NAME);

        // Get all the personalInfoList where motherName equals to UPDATED_MOTHER_NAME
        defaultPersonalInfoShouldNotBeFound("motherName.in=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherName is not null
        defaultPersonalInfoShouldBeFound("motherName.specified=true");

        // Get all the personalInfoList where motherName is null
        defaultPersonalInfoShouldNotBeFound("motherName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherName contains DEFAULT_MOTHER_NAME
        defaultPersonalInfoShouldBeFound("motherName.contains=" + DEFAULT_MOTHER_NAME);

        // Get all the personalInfoList where motherName contains UPDATED_MOTHER_NAME
        defaultPersonalInfoShouldNotBeFound("motherName.contains=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherName does not contain DEFAULT_MOTHER_NAME
        defaultPersonalInfoShouldNotBeFound("motherName.doesNotContain=" + DEFAULT_MOTHER_NAME);

        // Get all the personalInfoList where motherName does not contain UPDATED_MOTHER_NAME
        defaultPersonalInfoShouldBeFound("motherName.doesNotContain=" + UPDATED_MOTHER_NAME);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameBanglaIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherNameBangla equals to DEFAULT_MOTHER_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("motherNameBangla.equals=" + DEFAULT_MOTHER_NAME_BANGLA);

        // Get all the personalInfoList where motherNameBangla equals to UPDATED_MOTHER_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("motherNameBangla.equals=" + UPDATED_MOTHER_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameBanglaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherNameBangla not equals to DEFAULT_MOTHER_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("motherNameBangla.notEquals=" + DEFAULT_MOTHER_NAME_BANGLA);

        // Get all the personalInfoList where motherNameBangla not equals to UPDATED_MOTHER_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("motherNameBangla.notEquals=" + UPDATED_MOTHER_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameBanglaIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherNameBangla in DEFAULT_MOTHER_NAME_BANGLA or UPDATED_MOTHER_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("motherNameBangla.in=" + DEFAULT_MOTHER_NAME_BANGLA + "," + UPDATED_MOTHER_NAME_BANGLA);

        // Get all the personalInfoList where motherNameBangla equals to UPDATED_MOTHER_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("motherNameBangla.in=" + UPDATED_MOTHER_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameBanglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherNameBangla is not null
        defaultPersonalInfoShouldBeFound("motherNameBangla.specified=true");

        // Get all the personalInfoList where motherNameBangla is null
        defaultPersonalInfoShouldNotBeFound("motherNameBangla.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameBanglaContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherNameBangla contains DEFAULT_MOTHER_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("motherNameBangla.contains=" + DEFAULT_MOTHER_NAME_BANGLA);

        // Get all the personalInfoList where motherNameBangla contains UPDATED_MOTHER_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("motherNameBangla.contains=" + UPDATED_MOTHER_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMotherNameBanglaNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where motherNameBangla does not contain DEFAULT_MOTHER_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("motherNameBangla.doesNotContain=" + DEFAULT_MOTHER_NAME_BANGLA);

        // Get all the personalInfoList where motherNameBangla does not contain UPDATED_MOTHER_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("motherNameBangla.doesNotContain=" + UPDATED_MOTHER_NAME_BANGLA);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultPersonalInfoShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the personalInfoList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultPersonalInfoShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMaritalStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where maritalStatus not equals to DEFAULT_MARITAL_STATUS
        defaultPersonalInfoShouldNotBeFound("maritalStatus.notEquals=" + DEFAULT_MARITAL_STATUS);

        // Get all the personalInfoList where maritalStatus not equals to UPDATED_MARITAL_STATUS
        defaultPersonalInfoShouldBeFound("maritalStatus.notEquals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultPersonalInfoShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the personalInfoList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultPersonalInfoShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where maritalStatus is not null
        defaultPersonalInfoShouldBeFound("maritalStatus.specified=true");

        // Get all the personalInfoList where maritalStatus is null
        defaultPersonalInfoShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseName equals to DEFAULT_SPOUSE_NAME
        defaultPersonalInfoShouldBeFound("spouseName.equals=" + DEFAULT_SPOUSE_NAME);

        // Get all the personalInfoList where spouseName equals to UPDATED_SPOUSE_NAME
        defaultPersonalInfoShouldNotBeFound("spouseName.equals=" + UPDATED_SPOUSE_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseName not equals to DEFAULT_SPOUSE_NAME
        defaultPersonalInfoShouldNotBeFound("spouseName.notEquals=" + DEFAULT_SPOUSE_NAME);

        // Get all the personalInfoList where spouseName not equals to UPDATED_SPOUSE_NAME
        defaultPersonalInfoShouldBeFound("spouseName.notEquals=" + UPDATED_SPOUSE_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseName in DEFAULT_SPOUSE_NAME or UPDATED_SPOUSE_NAME
        defaultPersonalInfoShouldBeFound("spouseName.in=" + DEFAULT_SPOUSE_NAME + "," + UPDATED_SPOUSE_NAME);

        // Get all the personalInfoList where spouseName equals to UPDATED_SPOUSE_NAME
        defaultPersonalInfoShouldNotBeFound("spouseName.in=" + UPDATED_SPOUSE_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseName is not null
        defaultPersonalInfoShouldBeFound("spouseName.specified=true");

        // Get all the personalInfoList where spouseName is null
        defaultPersonalInfoShouldNotBeFound("spouseName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseName contains DEFAULT_SPOUSE_NAME
        defaultPersonalInfoShouldBeFound("spouseName.contains=" + DEFAULT_SPOUSE_NAME);

        // Get all the personalInfoList where spouseName contains UPDATED_SPOUSE_NAME
        defaultPersonalInfoShouldNotBeFound("spouseName.contains=" + UPDATED_SPOUSE_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseName does not contain DEFAULT_SPOUSE_NAME
        defaultPersonalInfoShouldNotBeFound("spouseName.doesNotContain=" + DEFAULT_SPOUSE_NAME);

        // Get all the personalInfoList where spouseName does not contain UPDATED_SPOUSE_NAME
        defaultPersonalInfoShouldBeFound("spouseName.doesNotContain=" + UPDATED_SPOUSE_NAME);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameBanglaIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseNameBangla equals to DEFAULT_SPOUSE_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("spouseNameBangla.equals=" + DEFAULT_SPOUSE_NAME_BANGLA);

        // Get all the personalInfoList where spouseNameBangla equals to UPDATED_SPOUSE_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("spouseNameBangla.equals=" + UPDATED_SPOUSE_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameBanglaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseNameBangla not equals to DEFAULT_SPOUSE_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("spouseNameBangla.notEquals=" + DEFAULT_SPOUSE_NAME_BANGLA);

        // Get all the personalInfoList where spouseNameBangla not equals to UPDATED_SPOUSE_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("spouseNameBangla.notEquals=" + UPDATED_SPOUSE_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameBanglaIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseNameBangla in DEFAULT_SPOUSE_NAME_BANGLA or UPDATED_SPOUSE_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("spouseNameBangla.in=" + DEFAULT_SPOUSE_NAME_BANGLA + "," + UPDATED_SPOUSE_NAME_BANGLA);

        // Get all the personalInfoList where spouseNameBangla equals to UPDATED_SPOUSE_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("spouseNameBangla.in=" + UPDATED_SPOUSE_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameBanglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseNameBangla is not null
        defaultPersonalInfoShouldBeFound("spouseNameBangla.specified=true");

        // Get all the personalInfoList where spouseNameBangla is null
        defaultPersonalInfoShouldNotBeFound("spouseNameBangla.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameBanglaContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseNameBangla contains DEFAULT_SPOUSE_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("spouseNameBangla.contains=" + DEFAULT_SPOUSE_NAME_BANGLA);

        // Get all the personalInfoList where spouseNameBangla contains UPDATED_SPOUSE_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("spouseNameBangla.contains=" + UPDATED_SPOUSE_NAME_BANGLA);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosBySpouseNameBanglaNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where spouseNameBangla does not contain DEFAULT_SPOUSE_NAME_BANGLA
        defaultPersonalInfoShouldNotBeFound("spouseNameBangla.doesNotContain=" + DEFAULT_SPOUSE_NAME_BANGLA);

        // Get all the personalInfoList where spouseNameBangla does not contain UPDATED_SPOUSE_NAME_BANGLA
        defaultPersonalInfoShouldBeFound("spouseNameBangla.doesNotContain=" + UPDATED_SPOUSE_NAME_BANGLA);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultPersonalInfoShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the personalInfoList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultPersonalInfoShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultPersonalInfoShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the personalInfoList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultPersonalInfoShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultPersonalInfoShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the personalInfoList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultPersonalInfoShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where dateOfBirth is not null
        defaultPersonalInfoShouldBeFound("dateOfBirth.specified=true");

        // Get all the personalInfoList where dateOfBirth is null
        defaultPersonalInfoShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultPersonalInfoShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the personalInfoList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultPersonalInfoShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultPersonalInfoShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the personalInfoList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultPersonalInfoShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultPersonalInfoShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the personalInfoList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultPersonalInfoShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultPersonalInfoShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the personalInfoList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultPersonalInfoShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalId equals to DEFAULT_NATIONAL_ID
        defaultPersonalInfoShouldBeFound("nationalId.equals=" + DEFAULT_NATIONAL_ID);

        // Get all the personalInfoList where nationalId equals to UPDATED_NATIONAL_ID
        defaultPersonalInfoShouldNotBeFound("nationalId.equals=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalId not equals to DEFAULT_NATIONAL_ID
        defaultPersonalInfoShouldNotBeFound("nationalId.notEquals=" + DEFAULT_NATIONAL_ID);

        // Get all the personalInfoList where nationalId not equals to UPDATED_NATIONAL_ID
        defaultPersonalInfoShouldBeFound("nationalId.notEquals=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalId in DEFAULT_NATIONAL_ID or UPDATED_NATIONAL_ID
        defaultPersonalInfoShouldBeFound("nationalId.in=" + DEFAULT_NATIONAL_ID + "," + UPDATED_NATIONAL_ID);

        // Get all the personalInfoList where nationalId equals to UPDATED_NATIONAL_ID
        defaultPersonalInfoShouldNotBeFound("nationalId.in=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalId is not null
        defaultPersonalInfoShouldBeFound("nationalId.specified=true");

        // Get all the personalInfoList where nationalId is null
        defaultPersonalInfoShouldNotBeFound("nationalId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalId contains DEFAULT_NATIONAL_ID
        defaultPersonalInfoShouldBeFound("nationalId.contains=" + DEFAULT_NATIONAL_ID);

        // Get all the personalInfoList where nationalId contains UPDATED_NATIONAL_ID
        defaultPersonalInfoShouldNotBeFound("nationalId.contains=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalId does not contain DEFAULT_NATIONAL_ID
        defaultPersonalInfoShouldNotBeFound("nationalId.doesNotContain=" + DEFAULT_NATIONAL_ID);

        // Get all the personalInfoList where nationalId does not contain UPDATED_NATIONAL_ID
        defaultPersonalInfoShouldBeFound("nationalId.doesNotContain=" + UPDATED_NATIONAL_ID);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdAttachmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalIdAttachmentId equals to DEFAULT_NATIONAL_ID_ATTACHMENT_ID
        defaultPersonalInfoShouldBeFound("nationalIdAttachmentId.equals=" + DEFAULT_NATIONAL_ID_ATTACHMENT_ID);

        // Get all the personalInfoList where nationalIdAttachmentId equals to UPDATED_NATIONAL_ID_ATTACHMENT_ID
        defaultPersonalInfoShouldNotBeFound("nationalIdAttachmentId.equals=" + UPDATED_NATIONAL_ID_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdAttachmentIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalIdAttachmentId not equals to DEFAULT_NATIONAL_ID_ATTACHMENT_ID
        defaultPersonalInfoShouldNotBeFound("nationalIdAttachmentId.notEquals=" + DEFAULT_NATIONAL_ID_ATTACHMENT_ID);

        // Get all the personalInfoList where nationalIdAttachmentId not equals to UPDATED_NATIONAL_ID_ATTACHMENT_ID
        defaultPersonalInfoShouldBeFound("nationalIdAttachmentId.notEquals=" + UPDATED_NATIONAL_ID_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdAttachmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalIdAttachmentId in DEFAULT_NATIONAL_ID_ATTACHMENT_ID or UPDATED_NATIONAL_ID_ATTACHMENT_ID
        defaultPersonalInfoShouldBeFound("nationalIdAttachmentId.in=" + DEFAULT_NATIONAL_ID_ATTACHMENT_ID + "," + UPDATED_NATIONAL_ID_ATTACHMENT_ID);

        // Get all the personalInfoList where nationalIdAttachmentId equals to UPDATED_NATIONAL_ID_ATTACHMENT_ID
        defaultPersonalInfoShouldNotBeFound("nationalIdAttachmentId.in=" + UPDATED_NATIONAL_ID_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdAttachmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalIdAttachmentId is not null
        defaultPersonalInfoShouldBeFound("nationalIdAttachmentId.specified=true");

        // Get all the personalInfoList where nationalIdAttachmentId is null
        defaultPersonalInfoShouldNotBeFound("nationalIdAttachmentId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdAttachmentIdContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalIdAttachmentId contains DEFAULT_NATIONAL_ID_ATTACHMENT_ID
        defaultPersonalInfoShouldBeFound("nationalIdAttachmentId.contains=" + DEFAULT_NATIONAL_ID_ATTACHMENT_ID);

        // Get all the personalInfoList where nationalIdAttachmentId contains UPDATED_NATIONAL_ID_ATTACHMENT_ID
        defaultPersonalInfoShouldNotBeFound("nationalIdAttachmentId.contains=" + UPDATED_NATIONAL_ID_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByNationalIdAttachmentIdNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where nationalIdAttachmentId does not contain DEFAULT_NATIONAL_ID_ATTACHMENT_ID
        defaultPersonalInfoShouldNotBeFound("nationalIdAttachmentId.doesNotContain=" + DEFAULT_NATIONAL_ID_ATTACHMENT_ID);

        // Get all the personalInfoList where nationalIdAttachmentId does not contain UPDATED_NATIONAL_ID_ATTACHMENT_ID
        defaultPersonalInfoShouldBeFound("nationalIdAttachmentId.doesNotContain=" + UPDATED_NATIONAL_ID_ATTACHMENT_ID);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistration equals to DEFAULT_BIRTH_REGISTRATION
        defaultPersonalInfoShouldBeFound("birthRegistration.equals=" + DEFAULT_BIRTH_REGISTRATION);

        // Get all the personalInfoList where birthRegistration equals to UPDATED_BIRTH_REGISTRATION
        defaultPersonalInfoShouldNotBeFound("birthRegistration.equals=" + UPDATED_BIRTH_REGISTRATION);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistration not equals to DEFAULT_BIRTH_REGISTRATION
        defaultPersonalInfoShouldNotBeFound("birthRegistration.notEquals=" + DEFAULT_BIRTH_REGISTRATION);

        // Get all the personalInfoList where birthRegistration not equals to UPDATED_BIRTH_REGISTRATION
        defaultPersonalInfoShouldBeFound("birthRegistration.notEquals=" + UPDATED_BIRTH_REGISTRATION);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistration in DEFAULT_BIRTH_REGISTRATION or UPDATED_BIRTH_REGISTRATION
        defaultPersonalInfoShouldBeFound("birthRegistration.in=" + DEFAULT_BIRTH_REGISTRATION + "," + UPDATED_BIRTH_REGISTRATION);

        // Get all the personalInfoList where birthRegistration equals to UPDATED_BIRTH_REGISTRATION
        defaultPersonalInfoShouldNotBeFound("birthRegistration.in=" + UPDATED_BIRTH_REGISTRATION);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistration is not null
        defaultPersonalInfoShouldBeFound("birthRegistration.specified=true");

        // Get all the personalInfoList where birthRegistration is null
        defaultPersonalInfoShouldNotBeFound("birthRegistration.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistration contains DEFAULT_BIRTH_REGISTRATION
        defaultPersonalInfoShouldBeFound("birthRegistration.contains=" + DEFAULT_BIRTH_REGISTRATION);

        // Get all the personalInfoList where birthRegistration contains UPDATED_BIRTH_REGISTRATION
        defaultPersonalInfoShouldNotBeFound("birthRegistration.contains=" + UPDATED_BIRTH_REGISTRATION);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistration does not contain DEFAULT_BIRTH_REGISTRATION
        defaultPersonalInfoShouldNotBeFound("birthRegistration.doesNotContain=" + DEFAULT_BIRTH_REGISTRATION);

        // Get all the personalInfoList where birthRegistration does not contain UPDATED_BIRTH_REGISTRATION
        defaultPersonalInfoShouldBeFound("birthRegistration.doesNotContain=" + UPDATED_BIRTH_REGISTRATION);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationAttachmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistrationAttachmentId equals to DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID
        defaultPersonalInfoShouldBeFound("birthRegistrationAttachmentId.equals=" + DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID);

        // Get all the personalInfoList where birthRegistrationAttachmentId equals to UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID
        defaultPersonalInfoShouldNotBeFound("birthRegistrationAttachmentId.equals=" + UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationAttachmentIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistrationAttachmentId not equals to DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID
        defaultPersonalInfoShouldNotBeFound("birthRegistrationAttachmentId.notEquals=" + DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID);

        // Get all the personalInfoList where birthRegistrationAttachmentId not equals to UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID
        defaultPersonalInfoShouldBeFound("birthRegistrationAttachmentId.notEquals=" + UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationAttachmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistrationAttachmentId in DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID or UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID
        defaultPersonalInfoShouldBeFound("birthRegistrationAttachmentId.in=" + DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID + "," + UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID);

        // Get all the personalInfoList where birthRegistrationAttachmentId equals to UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID
        defaultPersonalInfoShouldNotBeFound("birthRegistrationAttachmentId.in=" + UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationAttachmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistrationAttachmentId is not null
        defaultPersonalInfoShouldBeFound("birthRegistrationAttachmentId.specified=true");

        // Get all the personalInfoList where birthRegistrationAttachmentId is null
        defaultPersonalInfoShouldNotBeFound("birthRegistrationAttachmentId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationAttachmentIdContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistrationAttachmentId contains DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID
        defaultPersonalInfoShouldBeFound("birthRegistrationAttachmentId.contains=" + DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID);

        // Get all the personalInfoList where birthRegistrationAttachmentId contains UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID
        defaultPersonalInfoShouldNotBeFound("birthRegistrationAttachmentId.contains=" + UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBirthRegistrationAttachmentIdNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where birthRegistrationAttachmentId does not contain DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID
        defaultPersonalInfoShouldNotBeFound("birthRegistrationAttachmentId.doesNotContain=" + DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID);

        // Get all the personalInfoList where birthRegistrationAttachmentId does not contain UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID
        defaultPersonalInfoShouldBeFound("birthRegistrationAttachmentId.doesNotContain=" + UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where height equals to DEFAULT_HEIGHT
        defaultPersonalInfoShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the personalInfoList where height equals to UPDATED_HEIGHT
        defaultPersonalInfoShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where height not equals to DEFAULT_HEIGHT
        defaultPersonalInfoShouldNotBeFound("height.notEquals=" + DEFAULT_HEIGHT);

        // Get all the personalInfoList where height not equals to UPDATED_HEIGHT
        defaultPersonalInfoShouldBeFound("height.notEquals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultPersonalInfoShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the personalInfoList where height equals to UPDATED_HEIGHT
        defaultPersonalInfoShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where height is not null
        defaultPersonalInfoShouldBeFound("height.specified=true");

        // Get all the personalInfoList where height is null
        defaultPersonalInfoShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where height is greater than or equal to DEFAULT_HEIGHT
        defaultPersonalInfoShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the personalInfoList where height is greater than or equal to UPDATED_HEIGHT
        defaultPersonalInfoShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where height is less than or equal to DEFAULT_HEIGHT
        defaultPersonalInfoShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the personalInfoList where height is less than or equal to SMALLER_HEIGHT
        defaultPersonalInfoShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where height is less than DEFAULT_HEIGHT
        defaultPersonalInfoShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the personalInfoList where height is less than UPDATED_HEIGHT
        defaultPersonalInfoShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where height is greater than DEFAULT_HEIGHT
        defaultPersonalInfoShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the personalInfoList where height is greater than SMALLER_HEIGHT
        defaultPersonalInfoShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where gender equals to DEFAULT_GENDER
        defaultPersonalInfoShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the personalInfoList where gender equals to UPDATED_GENDER
        defaultPersonalInfoShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where gender not equals to DEFAULT_GENDER
        defaultPersonalInfoShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the personalInfoList where gender not equals to UPDATED_GENDER
        defaultPersonalInfoShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultPersonalInfoShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the personalInfoList where gender equals to UPDATED_GENDER
        defaultPersonalInfoShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where gender is not null
        defaultPersonalInfoShouldBeFound("gender.specified=true");

        // Get all the personalInfoList where gender is null
        defaultPersonalInfoShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where religion equals to DEFAULT_RELIGION
        defaultPersonalInfoShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the personalInfoList where religion equals to UPDATED_RELIGION
        defaultPersonalInfoShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByReligionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where religion not equals to DEFAULT_RELIGION
        defaultPersonalInfoShouldNotBeFound("religion.notEquals=" + DEFAULT_RELIGION);

        // Get all the personalInfoList where religion not equals to UPDATED_RELIGION
        defaultPersonalInfoShouldBeFound("religion.notEquals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultPersonalInfoShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the personalInfoList where religion equals to UPDATED_RELIGION
        defaultPersonalInfoShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where religion is not null
        defaultPersonalInfoShouldBeFound("religion.specified=true");

        // Get all the personalInfoList where religion is null
        defaultPersonalInfoShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBloodGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where bloodGroup equals to DEFAULT_BLOOD_GROUP
        defaultPersonalInfoShouldBeFound("bloodGroup.equals=" + DEFAULT_BLOOD_GROUP);

        // Get all the personalInfoList where bloodGroup equals to UPDATED_BLOOD_GROUP
        defaultPersonalInfoShouldNotBeFound("bloodGroup.equals=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBloodGroupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where bloodGroup not equals to DEFAULT_BLOOD_GROUP
        defaultPersonalInfoShouldNotBeFound("bloodGroup.notEquals=" + DEFAULT_BLOOD_GROUP);

        // Get all the personalInfoList where bloodGroup not equals to UPDATED_BLOOD_GROUP
        defaultPersonalInfoShouldBeFound("bloodGroup.notEquals=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBloodGroupIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where bloodGroup in DEFAULT_BLOOD_GROUP or UPDATED_BLOOD_GROUP
        defaultPersonalInfoShouldBeFound("bloodGroup.in=" + DEFAULT_BLOOD_GROUP + "," + UPDATED_BLOOD_GROUP);

        // Get all the personalInfoList where bloodGroup equals to UPDATED_BLOOD_GROUP
        defaultPersonalInfoShouldNotBeFound("bloodGroup.in=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByBloodGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where bloodGroup is not null
        defaultPersonalInfoShouldBeFound("bloodGroup.specified=true");

        // Get all the personalInfoList where bloodGroup is null
        defaultPersonalInfoShouldNotBeFound("bloodGroup.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByEmergencyContactIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where emergencyContact equals to DEFAULT_EMERGENCY_CONTACT
        defaultPersonalInfoShouldBeFound("emergencyContact.equals=" + DEFAULT_EMERGENCY_CONTACT);

        // Get all the personalInfoList where emergencyContact equals to UPDATED_EMERGENCY_CONTACT
        defaultPersonalInfoShouldNotBeFound("emergencyContact.equals=" + UPDATED_EMERGENCY_CONTACT);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByEmergencyContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where emergencyContact not equals to DEFAULT_EMERGENCY_CONTACT
        defaultPersonalInfoShouldNotBeFound("emergencyContact.notEquals=" + DEFAULT_EMERGENCY_CONTACT);

        // Get all the personalInfoList where emergencyContact not equals to UPDATED_EMERGENCY_CONTACT
        defaultPersonalInfoShouldBeFound("emergencyContact.notEquals=" + UPDATED_EMERGENCY_CONTACT);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByEmergencyContactIsInShouldWork() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where emergencyContact in DEFAULT_EMERGENCY_CONTACT or UPDATED_EMERGENCY_CONTACT
        defaultPersonalInfoShouldBeFound("emergencyContact.in=" + DEFAULT_EMERGENCY_CONTACT + "," + UPDATED_EMERGENCY_CONTACT);

        // Get all the personalInfoList where emergencyContact equals to UPDATED_EMERGENCY_CONTACT
        defaultPersonalInfoShouldNotBeFound("emergencyContact.in=" + UPDATED_EMERGENCY_CONTACT);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByEmergencyContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where emergencyContact is not null
        defaultPersonalInfoShouldBeFound("emergencyContact.specified=true");

        // Get all the personalInfoList where emergencyContact is null
        defaultPersonalInfoShouldNotBeFound("emergencyContact.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonalInfosByEmergencyContactContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where emergencyContact contains DEFAULT_EMERGENCY_CONTACT
        defaultPersonalInfoShouldBeFound("emergencyContact.contains=" + DEFAULT_EMERGENCY_CONTACT);

        // Get all the personalInfoList where emergencyContact contains UPDATED_EMERGENCY_CONTACT
        defaultPersonalInfoShouldNotBeFound("emergencyContact.contains=" + UPDATED_EMERGENCY_CONTACT);
    }

    @Test
    @Transactional
    public void getAllPersonalInfosByEmergencyContactNotContainsSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList where emergencyContact does not contain DEFAULT_EMERGENCY_CONTACT
        defaultPersonalInfoShouldNotBeFound("emergencyContact.doesNotContain=" + DEFAULT_EMERGENCY_CONTACT);

        // Get all the personalInfoList where emergencyContact does not contain UPDATED_EMERGENCY_CONTACT
        defaultPersonalInfoShouldBeFound("emergencyContact.doesNotContain=" + UPDATED_EMERGENCY_CONTACT);
    }


    @Test
    @Transactional
    public void getAllPersonalInfosByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        personalInfo.setEmployee(employee);
        personalInfoRepository.saveAndFlush(personalInfo);
        Long employeeId = employee.getId();

        // Get all the personalInfoList where employee equals to employeeId
        defaultPersonalInfoShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the personalInfoList where employee equals to employeeId + 1
        defaultPersonalInfoShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPersonalInfoShouldBeFound(String filter) throws Exception {
        restPersonalInfoMockMvc.perform(get("/api/personal-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].banglaName").value(hasItem(DEFAULT_BANGLA_NAME)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].photoId").value(hasItem(DEFAULT_PHOTO_ID)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].fatherNameBangla").value(hasItem(DEFAULT_FATHER_NAME_BANGLA)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].motherNameBangla").value(hasItem(DEFAULT_MOTHER_NAME_BANGLA)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].spouseName").value(hasItem(DEFAULT_SPOUSE_NAME)))
            .andExpect(jsonPath("$.[*].spouseNameBangla").value(hasItem(DEFAULT_SPOUSE_NAME_BANGLA)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID)))
            .andExpect(jsonPath("$.[*].nationalIdAttachmentContentType").value(hasItem(DEFAULT_NATIONAL_ID_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].nationalIdAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_NATIONAL_ID_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].nationalIdAttachmentId").value(hasItem(DEFAULT_NATIONAL_ID_ATTACHMENT_ID)))
            .andExpect(jsonPath("$.[*].birthRegistration").value(hasItem(DEFAULT_BIRTH_REGISTRATION)))
            .andExpect(jsonPath("$.[*].birthRegistrationAttachmentContentType").value(hasItem(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].birthRegistrationAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].birthRegistrationAttachmentId").value(hasItem(DEFAULT_BIRTH_REGISTRATION_ATTACHMENT_ID)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].emergencyContact").value(hasItem(DEFAULT_EMERGENCY_CONTACT)));

        // Check, that the count call also returns 1
        restPersonalInfoMockMvc.perform(get("/api/personal-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPersonalInfoShouldNotBeFound(String filter) throws Exception {
        restPersonalInfoMockMvc.perform(get("/api/personal-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonalInfoMockMvc.perform(get("/api/personal-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPersonalInfo() throws Exception {
        // Get the personalInfo
        restPersonalInfoMockMvc.perform(get("/api/personal-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonalInfo() throws Exception {
        // Initialize the database
        personalInfoService.save(personalInfo);

        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();

        // Update the personalInfo
        PersonalInfo updatedPersonalInfo = personalInfoRepository.findById(personalInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPersonalInfo are not directly saved in db
        em.detach(updatedPersonalInfo);
        updatedPersonalInfo
            .name(UPDATED_NAME)
            .banglaName(UPDATED_BANGLA_NAME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .photoId(UPDATED_PHOTO_ID)
            .fatherName(UPDATED_FATHER_NAME)
            .fatherNameBangla(UPDATED_FATHER_NAME_BANGLA)
            .motherName(UPDATED_MOTHER_NAME)
            .motherNameBangla(UPDATED_MOTHER_NAME_BANGLA)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .spouseName(UPDATED_SPOUSE_NAME)
            .spouseNameBangla(UPDATED_SPOUSE_NAME_BANGLA)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .nationalId(UPDATED_NATIONAL_ID)
            .nationalIdAttachment(UPDATED_NATIONAL_ID_ATTACHMENT)
            .nationalIdAttachmentContentType(UPDATED_NATIONAL_ID_ATTACHMENT_CONTENT_TYPE)
            .nationalIdAttachmentId(UPDATED_NATIONAL_ID_ATTACHMENT_ID)
            .birthRegistration(UPDATED_BIRTH_REGISTRATION)
            .birthRegistrationAttachment(UPDATED_BIRTH_REGISTRATION_ATTACHMENT)
            .birthRegistrationAttachmentContentType(UPDATED_BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE)
            .birthRegistrationAttachmentId(UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID)
            .height(UPDATED_HEIGHT)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .emergencyContact(UPDATED_EMERGENCY_CONTACT);

        restPersonalInfoMockMvc.perform(put("/api/personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonalInfo)))
            .andExpect(status().isOk());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
        PersonalInfo testPersonalInfo = personalInfoList.get(personalInfoList.size() - 1);
        assertThat(testPersonalInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonalInfo.getBanglaName()).isEqualTo(UPDATED_BANGLA_NAME);
        assertThat(testPersonalInfo.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPersonalInfo.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPersonalInfo.getPhotoId()).isEqualTo(UPDATED_PHOTO_ID);
        assertThat(testPersonalInfo.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testPersonalInfo.getFatherNameBangla()).isEqualTo(UPDATED_FATHER_NAME_BANGLA);
        assertThat(testPersonalInfo.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testPersonalInfo.getMotherNameBangla()).isEqualTo(UPDATED_MOTHER_NAME_BANGLA);
        assertThat(testPersonalInfo.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testPersonalInfo.getSpouseName()).isEqualTo(UPDATED_SPOUSE_NAME);
        assertThat(testPersonalInfo.getSpouseNameBangla()).isEqualTo(UPDATED_SPOUSE_NAME_BANGLA);
        assertThat(testPersonalInfo.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPersonalInfo.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testPersonalInfo.getNationalIdAttachment()).isEqualTo(UPDATED_NATIONAL_ID_ATTACHMENT);
        assertThat(testPersonalInfo.getNationalIdAttachmentContentType()).isEqualTo(UPDATED_NATIONAL_ID_ATTACHMENT_CONTENT_TYPE);
        assertThat(testPersonalInfo.getNationalIdAttachmentId()).isEqualTo(UPDATED_NATIONAL_ID_ATTACHMENT_ID);
        assertThat(testPersonalInfo.getBirthRegistration()).isEqualTo(UPDATED_BIRTH_REGISTRATION);
        assertThat(testPersonalInfo.getBirthRegistrationAttachment()).isEqualTo(UPDATED_BIRTH_REGISTRATION_ATTACHMENT);
        assertThat(testPersonalInfo.getBirthRegistrationAttachmentContentType()).isEqualTo(UPDATED_BIRTH_REGISTRATION_ATTACHMENT_CONTENT_TYPE);
        assertThat(testPersonalInfo.getBirthRegistrationAttachmentId()).isEqualTo(UPDATED_BIRTH_REGISTRATION_ATTACHMENT_ID);
        assertThat(testPersonalInfo.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testPersonalInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPersonalInfo.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testPersonalInfo.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testPersonalInfo.getEmergencyContact()).isEqualTo(UPDATED_EMERGENCY_CONTACT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonalInfo() throws Exception {
        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalInfoMockMvc.perform(put("/api/personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personalInfo)))
            .andExpect(status().isBadRequest());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonalInfo() throws Exception {
        // Initialize the database
        personalInfoService.save(personalInfo);

        int databaseSizeBeforeDelete = personalInfoRepository.findAll().size();

        // Delete the personalInfo
        restPersonalInfoMockMvc.perform(delete("/api/personal-infos/{id}", personalInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
