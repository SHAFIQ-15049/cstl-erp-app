package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.ServiceHistory;
import software.cstl.domain.Department;
import software.cstl.domain.Designation;
import software.cstl.domain.Grade;
import software.cstl.domain.Employee;
import software.cstl.repository.ServiceHistoryRepository;
import software.cstl.service.ServiceHistoryService;
import software.cstl.service.dto.ServiceHistoryCriteria;
import software.cstl.service.ServiceHistoryQueryService;

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

import software.cstl.domain.enumeration.EmployeeType;
import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.ServiceStatus;
/**
 * Integration tests for the {@link ServiceHistoryResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceHistoryResourceIT {

    private static final EmployeeType DEFAULT_EMPLOYEE_TYPE = EmployeeType.PERMANENT;
    private static final EmployeeType UPDATED_EMPLOYEE_TYPE = EmployeeType.TEMPORARY;

    private static final EmployeeCategory DEFAULT_CATEGORY = EmployeeCategory.TOP_LEVEL;
    private static final EmployeeCategory UPDATED_CATEGORY = EmployeeCategory.MID_LEVEL;

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO = LocalDate.ofEpochDay(-1L);

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final ServiceStatus DEFAULT_STATUS = ServiceStatus.ACTIVE;
    private static final ServiceStatus UPDATED_STATUS = ServiceStatus.NOT_ACTIVE;

    @Autowired
    private ServiceHistoryRepository serviceHistoryRepository;

    @Autowired
    private ServiceHistoryService serviceHistoryService;

    @Autowired
    private ServiceHistoryQueryService serviceHistoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceHistoryMockMvc;

    private ServiceHistory serviceHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceHistory createEntity(EntityManager em) {
        ServiceHistory serviceHistory = new ServiceHistory()
            .employeeType(DEFAULT_EMPLOYEE_TYPE)
            .category(DEFAULT_CATEGORY)
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE)
            .status(DEFAULT_STATUS);
        return serviceHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceHistory createUpdatedEntity(EntityManager em) {
        ServiceHistory serviceHistory = new ServiceHistory()
            .employeeType(UPDATED_EMPLOYEE_TYPE)
            .category(UPDATED_CATEGORY)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .status(UPDATED_STATUS);
        return serviceHistory;
    }

    @BeforeEach
    public void initTest() {
        serviceHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceHistory() throws Exception {
        int databaseSizeBeforeCreate = serviceHistoryRepository.findAll().size();
        // Create the ServiceHistory
        restServiceHistoryMockMvc.perform(post("/api/service-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceHistory)))
            .andExpect(status().isCreated());

        // Validate the ServiceHistory in the database
        List<ServiceHistory> serviceHistoryList = serviceHistoryRepository.findAll();
        assertThat(serviceHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceHistory testServiceHistory = serviceHistoryList.get(serviceHistoryList.size() - 1);
        assertThat(testServiceHistory.getEmployeeType()).isEqualTo(DEFAULT_EMPLOYEE_TYPE);
        assertThat(testServiceHistory.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testServiceHistory.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testServiceHistory.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testServiceHistory.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testServiceHistory.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testServiceHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createServiceHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceHistoryRepository.findAll().size();

        // Create the ServiceHistory with an existing ID
        serviceHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceHistoryMockMvc.perform(post("/api/service-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceHistory in the database
        List<ServiceHistory> serviceHistoryList = serviceHistoryRepository.findAll();
        assertThat(serviceHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceHistories() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList
        restServiceHistoryMockMvc.perform(get("/api/service-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeType").value(hasItem(DEFAULT_EMPLOYEE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceHistory() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get the serviceHistory
        restServiceHistoryMockMvc.perform(get("/api/service-histories/{id}", serviceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceHistory.getId().intValue()))
            .andExpect(jsonPath("$.employeeType").value(DEFAULT_EMPLOYEE_TYPE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getServiceHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        Long id = serviceHistory.getId();

        defaultServiceHistoryShouldBeFound("id.equals=" + id);
        defaultServiceHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultServiceHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceHistoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceHistoriesByEmployeeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where employeeType equals to DEFAULT_EMPLOYEE_TYPE
        defaultServiceHistoryShouldBeFound("employeeType.equals=" + DEFAULT_EMPLOYEE_TYPE);

        // Get all the serviceHistoryList where employeeType equals to UPDATED_EMPLOYEE_TYPE
        defaultServiceHistoryShouldNotBeFound("employeeType.equals=" + UPDATED_EMPLOYEE_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByEmployeeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where employeeType not equals to DEFAULT_EMPLOYEE_TYPE
        defaultServiceHistoryShouldNotBeFound("employeeType.notEquals=" + DEFAULT_EMPLOYEE_TYPE);

        // Get all the serviceHistoryList where employeeType not equals to UPDATED_EMPLOYEE_TYPE
        defaultServiceHistoryShouldBeFound("employeeType.notEquals=" + UPDATED_EMPLOYEE_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByEmployeeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where employeeType in DEFAULT_EMPLOYEE_TYPE or UPDATED_EMPLOYEE_TYPE
        defaultServiceHistoryShouldBeFound("employeeType.in=" + DEFAULT_EMPLOYEE_TYPE + "," + UPDATED_EMPLOYEE_TYPE);

        // Get all the serviceHistoryList where employeeType equals to UPDATED_EMPLOYEE_TYPE
        defaultServiceHistoryShouldNotBeFound("employeeType.in=" + UPDATED_EMPLOYEE_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByEmployeeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where employeeType is not null
        defaultServiceHistoryShouldBeFound("employeeType.specified=true");

        // Get all the serviceHistoryList where employeeType is null
        defaultServiceHistoryShouldNotBeFound("employeeType.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where category equals to DEFAULT_CATEGORY
        defaultServiceHistoryShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the serviceHistoryList where category equals to UPDATED_CATEGORY
        defaultServiceHistoryShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where category not equals to DEFAULT_CATEGORY
        defaultServiceHistoryShouldNotBeFound("category.notEquals=" + DEFAULT_CATEGORY);

        // Get all the serviceHistoryList where category not equals to UPDATED_CATEGORY
        defaultServiceHistoryShouldBeFound("category.notEquals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultServiceHistoryShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the serviceHistoryList where category equals to UPDATED_CATEGORY
        defaultServiceHistoryShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where category is not null
        defaultServiceHistoryShouldBeFound("category.specified=true");

        // Get all the serviceHistoryList where category is null
        defaultServiceHistoryShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByFromIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where from equals to DEFAULT_FROM
        defaultServiceHistoryShouldBeFound("from.equals=" + DEFAULT_FROM);

        // Get all the serviceHistoryList where from equals to UPDATED_FROM
        defaultServiceHistoryShouldNotBeFound("from.equals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where from not equals to DEFAULT_FROM
        defaultServiceHistoryShouldNotBeFound("from.notEquals=" + DEFAULT_FROM);

        // Get all the serviceHistoryList where from not equals to UPDATED_FROM
        defaultServiceHistoryShouldBeFound("from.notEquals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByFromIsInShouldWork() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where from in DEFAULT_FROM or UPDATED_FROM
        defaultServiceHistoryShouldBeFound("from.in=" + DEFAULT_FROM + "," + UPDATED_FROM);

        // Get all the serviceHistoryList where from equals to UPDATED_FROM
        defaultServiceHistoryShouldNotBeFound("from.in=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where from is not null
        defaultServiceHistoryShouldBeFound("from.specified=true");

        // Get all the serviceHistoryList where from is null
        defaultServiceHistoryShouldNotBeFound("from.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where from is greater than or equal to DEFAULT_FROM
        defaultServiceHistoryShouldBeFound("from.greaterThanOrEqual=" + DEFAULT_FROM);

        // Get all the serviceHistoryList where from is greater than or equal to UPDATED_FROM
        defaultServiceHistoryShouldNotBeFound("from.greaterThanOrEqual=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where from is less than or equal to DEFAULT_FROM
        defaultServiceHistoryShouldBeFound("from.lessThanOrEqual=" + DEFAULT_FROM);

        // Get all the serviceHistoryList where from is less than or equal to SMALLER_FROM
        defaultServiceHistoryShouldNotBeFound("from.lessThanOrEqual=" + SMALLER_FROM);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByFromIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where from is less than DEFAULT_FROM
        defaultServiceHistoryShouldNotBeFound("from.lessThan=" + DEFAULT_FROM);

        // Get all the serviceHistoryList where from is less than UPDATED_FROM
        defaultServiceHistoryShouldBeFound("from.lessThan=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where from is greater than DEFAULT_FROM
        defaultServiceHistoryShouldNotBeFound("from.greaterThan=" + DEFAULT_FROM);

        // Get all the serviceHistoryList where from is greater than SMALLER_FROM
        defaultServiceHistoryShouldBeFound("from.greaterThan=" + SMALLER_FROM);
    }


    @Test
    @Transactional
    public void getAllServiceHistoriesByToIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where to equals to DEFAULT_TO
        defaultServiceHistoryShouldBeFound("to.equals=" + DEFAULT_TO);

        // Get all the serviceHistoryList where to equals to UPDATED_TO
        defaultServiceHistoryShouldNotBeFound("to.equals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where to not equals to DEFAULT_TO
        defaultServiceHistoryShouldNotBeFound("to.notEquals=" + DEFAULT_TO);

        // Get all the serviceHistoryList where to not equals to UPDATED_TO
        defaultServiceHistoryShouldBeFound("to.notEquals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByToIsInShouldWork() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where to in DEFAULT_TO or UPDATED_TO
        defaultServiceHistoryShouldBeFound("to.in=" + DEFAULT_TO + "," + UPDATED_TO);

        // Get all the serviceHistoryList where to equals to UPDATED_TO
        defaultServiceHistoryShouldNotBeFound("to.in=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByToIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where to is not null
        defaultServiceHistoryShouldBeFound("to.specified=true");

        // Get all the serviceHistoryList where to is null
        defaultServiceHistoryShouldNotBeFound("to.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where to is greater than or equal to DEFAULT_TO
        defaultServiceHistoryShouldBeFound("to.greaterThanOrEqual=" + DEFAULT_TO);

        // Get all the serviceHistoryList where to is greater than or equal to UPDATED_TO
        defaultServiceHistoryShouldNotBeFound("to.greaterThanOrEqual=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where to is less than or equal to DEFAULT_TO
        defaultServiceHistoryShouldBeFound("to.lessThanOrEqual=" + DEFAULT_TO);

        // Get all the serviceHistoryList where to is less than or equal to SMALLER_TO
        defaultServiceHistoryShouldNotBeFound("to.lessThanOrEqual=" + SMALLER_TO);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByToIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where to is less than DEFAULT_TO
        defaultServiceHistoryShouldNotBeFound("to.lessThan=" + DEFAULT_TO);

        // Get all the serviceHistoryList where to is less than UPDATED_TO
        defaultServiceHistoryShouldBeFound("to.lessThan=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where to is greater than DEFAULT_TO
        defaultServiceHistoryShouldNotBeFound("to.greaterThan=" + DEFAULT_TO);

        // Get all the serviceHistoryList where to is greater than SMALLER_TO
        defaultServiceHistoryShouldBeFound("to.greaterThan=" + SMALLER_TO);
    }


    @Test
    @Transactional
    public void getAllServiceHistoriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where status equals to DEFAULT_STATUS
        defaultServiceHistoryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the serviceHistoryList where status equals to UPDATED_STATUS
        defaultServiceHistoryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where status not equals to DEFAULT_STATUS
        defaultServiceHistoryShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the serviceHistoryList where status not equals to UPDATED_STATUS
        defaultServiceHistoryShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultServiceHistoryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the serviceHistoryList where status equals to UPDATED_STATUS
        defaultServiceHistoryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);

        // Get all the serviceHistoryList where status is not null
        defaultServiceHistoryShouldBeFound("status.specified=true");

        // Get all the serviceHistoryList where status is null
        defaultServiceHistoryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceHistoriesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        serviceHistory.setDepartment(department);
        serviceHistoryRepository.saveAndFlush(serviceHistory);
        Long departmentId = department.getId();

        // Get all the serviceHistoryList where department equals to departmentId
        defaultServiceHistoryShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the serviceHistoryList where department equals to departmentId + 1
        defaultServiceHistoryShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceHistoriesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);
        Designation designation = DesignationResourceIT.createEntity(em);
        em.persist(designation);
        em.flush();
        serviceHistory.setDesignation(designation);
        serviceHistoryRepository.saveAndFlush(serviceHistory);
        Long designationId = designation.getId();

        // Get all the serviceHistoryList where designation equals to designationId
        defaultServiceHistoryShouldBeFound("designationId.equals=" + designationId);

        // Get all the serviceHistoryList where designation equals to designationId + 1
        defaultServiceHistoryShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceHistoriesByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);
        Grade grade = GradeResourceIT.createEntity(em);
        em.persist(grade);
        em.flush();
        serviceHistory.setGrade(grade);
        serviceHistoryRepository.saveAndFlush(serviceHistory);
        Long gradeId = grade.getId();

        // Get all the serviceHistoryList where grade equals to gradeId
        defaultServiceHistoryShouldBeFound("gradeId.equals=" + gradeId);

        // Get all the serviceHistoryList where grade equals to gradeId + 1
        defaultServiceHistoryShouldNotBeFound("gradeId.equals=" + (gradeId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceHistoriesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceHistoryRepository.saveAndFlush(serviceHistory);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        serviceHistory.setEmployee(employee);
        serviceHistoryRepository.saveAndFlush(serviceHistory);
        Long employeeId = employee.getId();

        // Get all the serviceHistoryList where employee equals to employeeId
        defaultServiceHistoryShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the serviceHistoryList where employee equals to employeeId + 1
        defaultServiceHistoryShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceHistoryShouldBeFound(String filter) throws Exception {
        restServiceHistoryMockMvc.perform(get("/api/service-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeType").value(hasItem(DEFAULT_EMPLOYEE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restServiceHistoryMockMvc.perform(get("/api/service-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceHistoryShouldNotBeFound(String filter) throws Exception {
        restServiceHistoryMockMvc.perform(get("/api/service-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceHistoryMockMvc.perform(get("/api/service-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingServiceHistory() throws Exception {
        // Get the serviceHistory
        restServiceHistoryMockMvc.perform(get("/api/service-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceHistory() throws Exception {
        // Initialize the database
        serviceHistoryService.save(serviceHistory);

        int databaseSizeBeforeUpdate = serviceHistoryRepository.findAll().size();

        // Update the serviceHistory
        ServiceHistory updatedServiceHistory = serviceHistoryRepository.findById(serviceHistory.getId()).get();
        // Disconnect from session so that the updates on updatedServiceHistory are not directly saved in db
        em.detach(updatedServiceHistory);
        updatedServiceHistory
            .employeeType(UPDATED_EMPLOYEE_TYPE)
            .category(UPDATED_CATEGORY)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .status(UPDATED_STATUS);

        restServiceHistoryMockMvc.perform(put("/api/service-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceHistory)))
            .andExpect(status().isOk());

        // Validate the ServiceHistory in the database
        List<ServiceHistory> serviceHistoryList = serviceHistoryRepository.findAll();
        assertThat(serviceHistoryList).hasSize(databaseSizeBeforeUpdate);
        ServiceHistory testServiceHistory = serviceHistoryList.get(serviceHistoryList.size() - 1);
        assertThat(testServiceHistory.getEmployeeType()).isEqualTo(UPDATED_EMPLOYEE_TYPE);
        assertThat(testServiceHistory.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testServiceHistory.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testServiceHistory.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testServiceHistory.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testServiceHistory.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testServiceHistory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceHistory() throws Exception {
        int databaseSizeBeforeUpdate = serviceHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceHistoryMockMvc.perform(put("/api/service-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceHistory in the database
        List<ServiceHistory> serviceHistoryList = serviceHistoryRepository.findAll();
        assertThat(serviceHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceHistory() throws Exception {
        // Initialize the database
        serviceHistoryService.save(serviceHistory);

        int databaseSizeBeforeDelete = serviceHistoryRepository.findAll().size();

        // Delete the serviceHistory
        restServiceHistoryMockMvc.perform(delete("/api/service-histories/{id}", serviceHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceHistory> serviceHistoryList = serviceHistoryRepository.findAll();
        assertThat(serviceHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
