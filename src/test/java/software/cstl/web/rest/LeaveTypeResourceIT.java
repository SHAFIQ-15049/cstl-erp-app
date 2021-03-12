package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.LeaveType;
import software.cstl.repository.LeaveTypeRepository;
import software.cstl.service.LeaveTypeService;
import software.cstl.service.dto.LeaveTypeCriteria;
import software.cstl.service.LeaveTypeQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.LeaveTypeName;
/**
 * Integration tests for the {@link LeaveTypeResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LeaveTypeResourceIT {

    private static final LeaveTypeName DEFAULT_NAME = LeaveTypeName.CASUAL_LEAVE;
    private static final LeaveTypeName UPDATED_NAME = LeaveTypeName.MEDICAL_LEAVE;

    private static final BigDecimal DEFAULT_TOTAL_DAYS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_DAYS = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_DAYS = new BigDecimal(1 - 1);

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @Autowired
    private LeaveTypeQueryService leaveTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveTypeMockMvc;

    private LeaveType leaveType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveType createEntity(EntityManager em) {
        LeaveType leaveType = new LeaveType()
            .name(DEFAULT_NAME)
            .totalDays(DEFAULT_TOTAL_DAYS);
        return leaveType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveType createUpdatedEntity(EntityManager em) {
        LeaveType leaveType = new LeaveType()
            .name(UPDATED_NAME)
            .totalDays(UPDATED_TOTAL_DAYS);
        return leaveType;
    }

    @BeforeEach
    public void initTest() {
        leaveType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveType() throws Exception {
        int databaseSizeBeforeCreate = leaveTypeRepository.findAll().size();
        // Create the LeaveType
        restLeaveTypeMockMvc.perform(post("/api/leave-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveType)))
            .andExpect(status().isCreated());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeaveType.getTotalDays()).isEqualTo(DEFAULT_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void createLeaveTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveTypeRepository.findAll().size();

        // Create the LeaveType with an existing ID
        leaveType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTypeMockMvc.perform(post("/api/leave-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveType)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeRepository.findAll().size();
        // set the field null
        leaveType.setName(null);

        // Create the LeaveType, which fails.


        restLeaveTypeMockMvc.perform(post("/api/leave-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveType)))
            .andExpect(status().isBadRequest());

        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeRepository.findAll().size();
        // set the field null
        leaveType.setTotalDays(null);

        // Create the LeaveType, which fails.


        restLeaveTypeMockMvc.perform(post("/api/leave-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveType)))
            .andExpect(status().isBadRequest());

        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeaveTypes() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList
        restLeaveTypeMockMvc.perform(get("/api/leave-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS.intValue())));
    }
    
    @Test
    @Transactional
    public void getLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get the leaveType
        restLeaveTypeMockMvc.perform(get("/api/leave-types/{id}", leaveType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.totalDays").value(DEFAULT_TOTAL_DAYS.intValue()));
    }


    @Test
    @Transactional
    public void getLeaveTypesByIdFiltering() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        Long id = leaveType.getId();

        defaultLeaveTypeShouldBeFound("id.equals=" + id);
        defaultLeaveTypeShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLeaveTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where name equals to DEFAULT_NAME
        defaultLeaveTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the leaveTypeList where name equals to UPDATED_NAME
        defaultLeaveTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where name not equals to DEFAULT_NAME
        defaultLeaveTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the leaveTypeList where name not equals to UPDATED_NAME
        defaultLeaveTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLeaveTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the leaveTypeList where name equals to UPDATED_NAME
        defaultLeaveTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where name is not null
        defaultLeaveTypeShouldBeFound("name.specified=true");

        // Get all the leaveTypeList where name is null
        defaultLeaveTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByTotalDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where totalDays equals to DEFAULT_TOTAL_DAYS
        defaultLeaveTypeShouldBeFound("totalDays.equals=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveTypeList where totalDays equals to UPDATED_TOTAL_DAYS
        defaultLeaveTypeShouldNotBeFound("totalDays.equals=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByTotalDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where totalDays not equals to DEFAULT_TOTAL_DAYS
        defaultLeaveTypeShouldNotBeFound("totalDays.notEquals=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveTypeList where totalDays not equals to UPDATED_TOTAL_DAYS
        defaultLeaveTypeShouldBeFound("totalDays.notEquals=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByTotalDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where totalDays in DEFAULT_TOTAL_DAYS or UPDATED_TOTAL_DAYS
        defaultLeaveTypeShouldBeFound("totalDays.in=" + DEFAULT_TOTAL_DAYS + "," + UPDATED_TOTAL_DAYS);

        // Get all the leaveTypeList where totalDays equals to UPDATED_TOTAL_DAYS
        defaultLeaveTypeShouldNotBeFound("totalDays.in=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByTotalDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where totalDays is not null
        defaultLeaveTypeShouldBeFound("totalDays.specified=true");

        // Get all the leaveTypeList where totalDays is null
        defaultLeaveTypeShouldNotBeFound("totalDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByTotalDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where totalDays is greater than or equal to DEFAULT_TOTAL_DAYS
        defaultLeaveTypeShouldBeFound("totalDays.greaterThanOrEqual=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveTypeList where totalDays is greater than or equal to UPDATED_TOTAL_DAYS
        defaultLeaveTypeShouldNotBeFound("totalDays.greaterThanOrEqual=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByTotalDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where totalDays is less than or equal to DEFAULT_TOTAL_DAYS
        defaultLeaveTypeShouldBeFound("totalDays.lessThanOrEqual=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveTypeList where totalDays is less than or equal to SMALLER_TOTAL_DAYS
        defaultLeaveTypeShouldNotBeFound("totalDays.lessThanOrEqual=" + SMALLER_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByTotalDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where totalDays is less than DEFAULT_TOTAL_DAYS
        defaultLeaveTypeShouldNotBeFound("totalDays.lessThan=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveTypeList where totalDays is less than UPDATED_TOTAL_DAYS
        defaultLeaveTypeShouldBeFound("totalDays.lessThan=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByTotalDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where totalDays is greater than DEFAULT_TOTAL_DAYS
        defaultLeaveTypeShouldNotBeFound("totalDays.greaterThan=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveTypeList where totalDays is greater than SMALLER_TOTAL_DAYS
        defaultLeaveTypeShouldBeFound("totalDays.greaterThan=" + SMALLER_TOTAL_DAYS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveTypeShouldBeFound(String filter) throws Exception {
        restLeaveTypeMockMvc.perform(get("/api/leave-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS.intValue())));

        // Check, that the count call also returns 1
        restLeaveTypeMockMvc.perform(get("/api/leave-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveTypeShouldNotBeFound(String filter) throws Exception {
        restLeaveTypeMockMvc.perform(get("/api/leave-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTypeMockMvc.perform(get("/api/leave-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLeaveType() throws Exception {
        // Get the leaveType
        restLeaveTypeMockMvc.perform(get("/api/leave-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveType() throws Exception {
        // Initialize the database
        leaveTypeService.save(leaveType);

        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();

        // Update the leaveType
        LeaveType updatedLeaveType = leaveTypeRepository.findById(leaveType.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveType are not directly saved in db
        em.detach(updatedLeaveType);
        updatedLeaveType
            .name(UPDATED_NAME)
            .totalDays(UPDATED_TOTAL_DAYS);

        restLeaveTypeMockMvc.perform(put("/api/leave-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLeaveType)))
            .andExpect(status().isOk());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveType.getTotalDays()).isEqualTo(UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc.perform(put("/api/leave-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveType)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeaveType() throws Exception {
        // Initialize the database
        leaveTypeService.save(leaveType);

        int databaseSizeBeforeDelete = leaveTypeRepository.findAll().size();

        // Delete the leaveType
        restLeaveTypeMockMvc.perform(delete("/api/leave-types/{id}", leaveType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
