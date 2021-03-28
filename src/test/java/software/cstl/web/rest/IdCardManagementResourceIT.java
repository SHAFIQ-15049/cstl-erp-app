package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.IdCardManagement;
import software.cstl.domain.Employee;
import software.cstl.repository.IdCardManagementRepository;
import software.cstl.service.IdCardManagementService;
import software.cstl.service.dto.IdCardManagementCriteria;
import software.cstl.service.IdCardManagementQueryService;

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
 * Integration tests for the {@link IdCardManagementResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IdCardManagementResourceIT {

    private static final String DEFAULT_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ISSUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TICKET_NO = "AAAAAAAAAA";
    private static final String UPDATED_TICKET_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_TILL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TILL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_TILL = LocalDate.ofEpochDay(-1L);

    @Autowired
    private IdCardManagementRepository idCardManagementRepository;

    @Autowired
    private IdCardManagementService idCardManagementService;

    @Autowired
    private IdCardManagementQueryService idCardManagementQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdCardManagementMockMvc;

    private IdCardManagement idCardManagement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdCardManagement createEntity(EntityManager em) {
        IdCardManagement idCardManagement = new IdCardManagement()
            .cardNo(DEFAULT_CARD_NO)
            .issueDate(DEFAULT_ISSUE_DATE)
            .ticketNo(DEFAULT_TICKET_NO)
            .validTill(DEFAULT_VALID_TILL);
        return idCardManagement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdCardManagement createUpdatedEntity(EntityManager em) {
        IdCardManagement idCardManagement = new IdCardManagement()
            .cardNo(UPDATED_CARD_NO)
            .issueDate(UPDATED_ISSUE_DATE)
            .ticketNo(UPDATED_TICKET_NO)
            .validTill(UPDATED_VALID_TILL);
        return idCardManagement;
    }

    @BeforeEach
    public void initTest() {
        idCardManagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdCardManagement() throws Exception {
        int databaseSizeBeforeCreate = idCardManagementRepository.findAll().size();
        // Create the IdCardManagement
        restIdCardManagementMockMvc.perform(post("/api/id-card-managements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idCardManagement)))
            .andExpect(status().isCreated());

        // Validate the IdCardManagement in the database
        List<IdCardManagement> idCardManagementList = idCardManagementRepository.findAll();
        assertThat(idCardManagementList).hasSize(databaseSizeBeforeCreate + 1);
        IdCardManagement testIdCardManagement = idCardManagementList.get(idCardManagementList.size() - 1);
        assertThat(testIdCardManagement.getCardNo()).isEqualTo(DEFAULT_CARD_NO);
        assertThat(testIdCardManagement.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testIdCardManagement.getTicketNo()).isEqualTo(DEFAULT_TICKET_NO);
        assertThat(testIdCardManagement.getValidTill()).isEqualTo(DEFAULT_VALID_TILL);
    }

    @Test
    @Transactional
    public void createIdCardManagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = idCardManagementRepository.findAll().size();

        // Create the IdCardManagement with an existing ID
        idCardManagement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdCardManagementMockMvc.perform(post("/api/id-card-managements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idCardManagement)))
            .andExpect(status().isBadRequest());

        // Validate the IdCardManagement in the database
        List<IdCardManagement> idCardManagementList = idCardManagementRepository.findAll();
        assertThat(idCardManagementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIdCardManagements() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList
        restIdCardManagementMockMvc.perform(get("/api/id-card-managements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idCardManagement.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardNo").value(hasItem(DEFAULT_CARD_NO)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].ticketNo").value(hasItem(DEFAULT_TICKET_NO)))
            .andExpect(jsonPath("$.[*].validTill").value(hasItem(DEFAULT_VALID_TILL.toString())));
    }
    
    @Test
    @Transactional
    public void getIdCardManagement() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get the idCardManagement
        restIdCardManagementMockMvc.perform(get("/api/id-card-managements/{id}", idCardManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(idCardManagement.getId().intValue()))
            .andExpect(jsonPath("$.cardNo").value(DEFAULT_CARD_NO))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.ticketNo").value(DEFAULT_TICKET_NO))
            .andExpect(jsonPath("$.validTill").value(DEFAULT_VALID_TILL.toString()));
    }


    @Test
    @Transactional
    public void getIdCardManagementsByIdFiltering() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        Long id = idCardManagement.getId();

        defaultIdCardManagementShouldBeFound("id.equals=" + id);
        defaultIdCardManagementShouldNotBeFound("id.notEquals=" + id);

        defaultIdCardManagementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIdCardManagementShouldNotBeFound("id.greaterThan=" + id);

        defaultIdCardManagementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIdCardManagementShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllIdCardManagementsByCardNoIsEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where cardNo equals to DEFAULT_CARD_NO
        defaultIdCardManagementShouldBeFound("cardNo.equals=" + DEFAULT_CARD_NO);

        // Get all the idCardManagementList where cardNo equals to UPDATED_CARD_NO
        defaultIdCardManagementShouldNotBeFound("cardNo.equals=" + UPDATED_CARD_NO);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByCardNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where cardNo not equals to DEFAULT_CARD_NO
        defaultIdCardManagementShouldNotBeFound("cardNo.notEquals=" + DEFAULT_CARD_NO);

        // Get all the idCardManagementList where cardNo not equals to UPDATED_CARD_NO
        defaultIdCardManagementShouldBeFound("cardNo.notEquals=" + UPDATED_CARD_NO);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByCardNoIsInShouldWork() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where cardNo in DEFAULT_CARD_NO or UPDATED_CARD_NO
        defaultIdCardManagementShouldBeFound("cardNo.in=" + DEFAULT_CARD_NO + "," + UPDATED_CARD_NO);

        // Get all the idCardManagementList where cardNo equals to UPDATED_CARD_NO
        defaultIdCardManagementShouldNotBeFound("cardNo.in=" + UPDATED_CARD_NO);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByCardNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where cardNo is not null
        defaultIdCardManagementShouldBeFound("cardNo.specified=true");

        // Get all the idCardManagementList where cardNo is null
        defaultIdCardManagementShouldNotBeFound("cardNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllIdCardManagementsByCardNoContainsSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where cardNo contains DEFAULT_CARD_NO
        defaultIdCardManagementShouldBeFound("cardNo.contains=" + DEFAULT_CARD_NO);

        // Get all the idCardManagementList where cardNo contains UPDATED_CARD_NO
        defaultIdCardManagementShouldNotBeFound("cardNo.contains=" + UPDATED_CARD_NO);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByCardNoNotContainsSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where cardNo does not contain DEFAULT_CARD_NO
        defaultIdCardManagementShouldNotBeFound("cardNo.doesNotContain=" + DEFAULT_CARD_NO);

        // Get all the idCardManagementList where cardNo does not contain UPDATED_CARD_NO
        defaultIdCardManagementShouldBeFound("cardNo.doesNotContain=" + UPDATED_CARD_NO);
    }


    @Test
    @Transactional
    public void getAllIdCardManagementsByIssueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where issueDate equals to DEFAULT_ISSUE_DATE
        defaultIdCardManagementShouldBeFound("issueDate.equals=" + DEFAULT_ISSUE_DATE);

        // Get all the idCardManagementList where issueDate equals to UPDATED_ISSUE_DATE
        defaultIdCardManagementShouldNotBeFound("issueDate.equals=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByIssueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where issueDate not equals to DEFAULT_ISSUE_DATE
        defaultIdCardManagementShouldNotBeFound("issueDate.notEquals=" + DEFAULT_ISSUE_DATE);

        // Get all the idCardManagementList where issueDate not equals to UPDATED_ISSUE_DATE
        defaultIdCardManagementShouldBeFound("issueDate.notEquals=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByIssueDateIsInShouldWork() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where issueDate in DEFAULT_ISSUE_DATE or UPDATED_ISSUE_DATE
        defaultIdCardManagementShouldBeFound("issueDate.in=" + DEFAULT_ISSUE_DATE + "," + UPDATED_ISSUE_DATE);

        // Get all the idCardManagementList where issueDate equals to UPDATED_ISSUE_DATE
        defaultIdCardManagementShouldNotBeFound("issueDate.in=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByIssueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where issueDate is not null
        defaultIdCardManagementShouldBeFound("issueDate.specified=true");

        // Get all the idCardManagementList where issueDate is null
        defaultIdCardManagementShouldNotBeFound("issueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByIssueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where issueDate is greater than or equal to DEFAULT_ISSUE_DATE
        defaultIdCardManagementShouldBeFound("issueDate.greaterThanOrEqual=" + DEFAULT_ISSUE_DATE);

        // Get all the idCardManagementList where issueDate is greater than or equal to UPDATED_ISSUE_DATE
        defaultIdCardManagementShouldNotBeFound("issueDate.greaterThanOrEqual=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByIssueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where issueDate is less than or equal to DEFAULT_ISSUE_DATE
        defaultIdCardManagementShouldBeFound("issueDate.lessThanOrEqual=" + DEFAULT_ISSUE_DATE);

        // Get all the idCardManagementList where issueDate is less than or equal to SMALLER_ISSUE_DATE
        defaultIdCardManagementShouldNotBeFound("issueDate.lessThanOrEqual=" + SMALLER_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByIssueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where issueDate is less than DEFAULT_ISSUE_DATE
        defaultIdCardManagementShouldNotBeFound("issueDate.lessThan=" + DEFAULT_ISSUE_DATE);

        // Get all the idCardManagementList where issueDate is less than UPDATED_ISSUE_DATE
        defaultIdCardManagementShouldBeFound("issueDate.lessThan=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByIssueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where issueDate is greater than DEFAULT_ISSUE_DATE
        defaultIdCardManagementShouldNotBeFound("issueDate.greaterThan=" + DEFAULT_ISSUE_DATE);

        // Get all the idCardManagementList where issueDate is greater than SMALLER_ISSUE_DATE
        defaultIdCardManagementShouldBeFound("issueDate.greaterThan=" + SMALLER_ISSUE_DATE);
    }


    @Test
    @Transactional
    public void getAllIdCardManagementsByTicketNoIsEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where ticketNo equals to DEFAULT_TICKET_NO
        defaultIdCardManagementShouldBeFound("ticketNo.equals=" + DEFAULT_TICKET_NO);

        // Get all the idCardManagementList where ticketNo equals to UPDATED_TICKET_NO
        defaultIdCardManagementShouldNotBeFound("ticketNo.equals=" + UPDATED_TICKET_NO);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByTicketNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where ticketNo not equals to DEFAULT_TICKET_NO
        defaultIdCardManagementShouldNotBeFound("ticketNo.notEquals=" + DEFAULT_TICKET_NO);

        // Get all the idCardManagementList where ticketNo not equals to UPDATED_TICKET_NO
        defaultIdCardManagementShouldBeFound("ticketNo.notEquals=" + UPDATED_TICKET_NO);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByTicketNoIsInShouldWork() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where ticketNo in DEFAULT_TICKET_NO or UPDATED_TICKET_NO
        defaultIdCardManagementShouldBeFound("ticketNo.in=" + DEFAULT_TICKET_NO + "," + UPDATED_TICKET_NO);

        // Get all the idCardManagementList where ticketNo equals to UPDATED_TICKET_NO
        defaultIdCardManagementShouldNotBeFound("ticketNo.in=" + UPDATED_TICKET_NO);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByTicketNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where ticketNo is not null
        defaultIdCardManagementShouldBeFound("ticketNo.specified=true");

        // Get all the idCardManagementList where ticketNo is null
        defaultIdCardManagementShouldNotBeFound("ticketNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllIdCardManagementsByTicketNoContainsSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where ticketNo contains DEFAULT_TICKET_NO
        defaultIdCardManagementShouldBeFound("ticketNo.contains=" + DEFAULT_TICKET_NO);

        // Get all the idCardManagementList where ticketNo contains UPDATED_TICKET_NO
        defaultIdCardManagementShouldNotBeFound("ticketNo.contains=" + UPDATED_TICKET_NO);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByTicketNoNotContainsSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where ticketNo does not contain DEFAULT_TICKET_NO
        defaultIdCardManagementShouldNotBeFound("ticketNo.doesNotContain=" + DEFAULT_TICKET_NO);

        // Get all the idCardManagementList where ticketNo does not contain UPDATED_TICKET_NO
        defaultIdCardManagementShouldBeFound("ticketNo.doesNotContain=" + UPDATED_TICKET_NO);
    }


    @Test
    @Transactional
    public void getAllIdCardManagementsByValidTillIsEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where validTill equals to DEFAULT_VALID_TILL
        defaultIdCardManagementShouldBeFound("validTill.equals=" + DEFAULT_VALID_TILL);

        // Get all the idCardManagementList where validTill equals to UPDATED_VALID_TILL
        defaultIdCardManagementShouldNotBeFound("validTill.equals=" + UPDATED_VALID_TILL);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByValidTillIsNotEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where validTill not equals to DEFAULT_VALID_TILL
        defaultIdCardManagementShouldNotBeFound("validTill.notEquals=" + DEFAULT_VALID_TILL);

        // Get all the idCardManagementList where validTill not equals to UPDATED_VALID_TILL
        defaultIdCardManagementShouldBeFound("validTill.notEquals=" + UPDATED_VALID_TILL);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByValidTillIsInShouldWork() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where validTill in DEFAULT_VALID_TILL or UPDATED_VALID_TILL
        defaultIdCardManagementShouldBeFound("validTill.in=" + DEFAULT_VALID_TILL + "," + UPDATED_VALID_TILL);

        // Get all the idCardManagementList where validTill equals to UPDATED_VALID_TILL
        defaultIdCardManagementShouldNotBeFound("validTill.in=" + UPDATED_VALID_TILL);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByValidTillIsNullOrNotNull() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where validTill is not null
        defaultIdCardManagementShouldBeFound("validTill.specified=true");

        // Get all the idCardManagementList where validTill is null
        defaultIdCardManagementShouldNotBeFound("validTill.specified=false");
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByValidTillIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where validTill is greater than or equal to DEFAULT_VALID_TILL
        defaultIdCardManagementShouldBeFound("validTill.greaterThanOrEqual=" + DEFAULT_VALID_TILL);

        // Get all the idCardManagementList where validTill is greater than or equal to UPDATED_VALID_TILL
        defaultIdCardManagementShouldNotBeFound("validTill.greaterThanOrEqual=" + UPDATED_VALID_TILL);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByValidTillIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where validTill is less than or equal to DEFAULT_VALID_TILL
        defaultIdCardManagementShouldBeFound("validTill.lessThanOrEqual=" + DEFAULT_VALID_TILL);

        // Get all the idCardManagementList where validTill is less than or equal to SMALLER_VALID_TILL
        defaultIdCardManagementShouldNotBeFound("validTill.lessThanOrEqual=" + SMALLER_VALID_TILL);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByValidTillIsLessThanSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where validTill is less than DEFAULT_VALID_TILL
        defaultIdCardManagementShouldNotBeFound("validTill.lessThan=" + DEFAULT_VALID_TILL);

        // Get all the idCardManagementList where validTill is less than UPDATED_VALID_TILL
        defaultIdCardManagementShouldBeFound("validTill.lessThan=" + UPDATED_VALID_TILL);
    }

    @Test
    @Transactional
    public void getAllIdCardManagementsByValidTillIsGreaterThanSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);

        // Get all the idCardManagementList where validTill is greater than DEFAULT_VALID_TILL
        defaultIdCardManagementShouldNotBeFound("validTill.greaterThan=" + DEFAULT_VALID_TILL);

        // Get all the idCardManagementList where validTill is greater than SMALLER_VALID_TILL
        defaultIdCardManagementShouldBeFound("validTill.greaterThan=" + SMALLER_VALID_TILL);
    }


    @Test
    @Transactional
    public void getAllIdCardManagementsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        idCardManagementRepository.saveAndFlush(idCardManagement);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        idCardManagement.setEmployee(employee);
        idCardManagementRepository.saveAndFlush(idCardManagement);
        Long employeeId = employee.getId();

        // Get all the idCardManagementList where employee equals to employeeId
        defaultIdCardManagementShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the idCardManagementList where employee equals to employeeId + 1
        defaultIdCardManagementShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIdCardManagementShouldBeFound(String filter) throws Exception {
        restIdCardManagementMockMvc.perform(get("/api/id-card-managements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idCardManagement.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardNo").value(hasItem(DEFAULT_CARD_NO)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].ticketNo").value(hasItem(DEFAULT_TICKET_NO)))
            .andExpect(jsonPath("$.[*].validTill").value(hasItem(DEFAULT_VALID_TILL.toString())));

        // Check, that the count call also returns 1
        restIdCardManagementMockMvc.perform(get("/api/id-card-managements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIdCardManagementShouldNotBeFound(String filter) throws Exception {
        restIdCardManagementMockMvc.perform(get("/api/id-card-managements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIdCardManagementMockMvc.perform(get("/api/id-card-managements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingIdCardManagement() throws Exception {
        // Get the idCardManagement
        restIdCardManagementMockMvc.perform(get("/api/id-card-managements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdCardManagement() throws Exception {
        // Initialize the database
        idCardManagementService.save(idCardManagement);

        int databaseSizeBeforeUpdate = idCardManagementRepository.findAll().size();

        // Update the idCardManagement
        IdCardManagement updatedIdCardManagement = idCardManagementRepository.findById(idCardManagement.getId()).get();
        // Disconnect from session so that the updates on updatedIdCardManagement are not directly saved in db
        em.detach(updatedIdCardManagement);
        updatedIdCardManagement
            .cardNo(UPDATED_CARD_NO)
            .issueDate(UPDATED_ISSUE_DATE)
            .ticketNo(UPDATED_TICKET_NO)
            .validTill(UPDATED_VALID_TILL);

        restIdCardManagementMockMvc.perform(put("/api/id-card-managements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdCardManagement)))
            .andExpect(status().isOk());

        // Validate the IdCardManagement in the database
        List<IdCardManagement> idCardManagementList = idCardManagementRepository.findAll();
        assertThat(idCardManagementList).hasSize(databaseSizeBeforeUpdate);
        IdCardManagement testIdCardManagement = idCardManagementList.get(idCardManagementList.size() - 1);
        assertThat(testIdCardManagement.getCardNo()).isEqualTo(UPDATED_CARD_NO);
        assertThat(testIdCardManagement.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testIdCardManagement.getTicketNo()).isEqualTo(UPDATED_TICKET_NO);
        assertThat(testIdCardManagement.getValidTill()).isEqualTo(UPDATED_VALID_TILL);
    }

    @Test
    @Transactional
    public void updateNonExistingIdCardManagement() throws Exception {
        int databaseSizeBeforeUpdate = idCardManagementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdCardManagementMockMvc.perform(put("/api/id-card-managements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(idCardManagement)))
            .andExpect(status().isBadRequest());

        // Validate the IdCardManagement in the database
        List<IdCardManagement> idCardManagementList = idCardManagementRepository.findAll();
        assertThat(idCardManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdCardManagement() throws Exception {
        // Initialize the database
        idCardManagementService.save(idCardManagement);

        int databaseSizeBeforeDelete = idCardManagementRepository.findAll().size();

        // Delete the idCardManagement
        restIdCardManagementMockMvc.perform(delete("/api/id-card-managements/{id}", idCardManagement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IdCardManagement> idCardManagementList = idCardManagementRepository.findAll();
        assertThat(idCardManagementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
