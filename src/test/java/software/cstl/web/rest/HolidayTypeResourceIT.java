package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.HolidayType;
import software.cstl.repository.HolidayTypeRepository;
import software.cstl.service.HolidayTypeService;
import software.cstl.service.dto.HolidayTypeCriteria;
import software.cstl.service.HolidayTypeQueryService;

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

/**
 * Integration tests for the {@link HolidayTypeResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HolidayTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private HolidayTypeRepository holidayTypeRepository;

    @Autowired
    private HolidayTypeService holidayTypeService;

    @Autowired
    private HolidayTypeQueryService holidayTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHolidayTypeMockMvc;

    private HolidayType holidayType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HolidayType createEntity(EntityManager em) {
        HolidayType holidayType = new HolidayType()
            .name(DEFAULT_NAME);
        return holidayType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HolidayType createUpdatedEntity(EntityManager em) {
        HolidayType holidayType = new HolidayType()
            .name(UPDATED_NAME);
        return holidayType;
    }

    @BeforeEach
    public void initTest() {
        holidayType = createEntity(em);
    }

    @Test
    @Transactional
    public void createHolidayType() throws Exception {
        int databaseSizeBeforeCreate = holidayTypeRepository.findAll().size();
        // Create the HolidayType
        restHolidayTypeMockMvc.perform(post("/api/holiday-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holidayType)))
            .andExpect(status().isCreated());

        // Validate the HolidayType in the database
        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeCreate + 1);
        HolidayType testHolidayType = holidayTypeList.get(holidayTypeList.size() - 1);
        assertThat(testHolidayType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createHolidayTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = holidayTypeRepository.findAll().size();

        // Create the HolidayType with an existing ID
        holidayType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHolidayTypeMockMvc.perform(post("/api/holiday-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holidayType)))
            .andExpect(status().isBadRequest());

        // Validate the HolidayType in the database
        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayTypeRepository.findAll().size();
        // set the field null
        holidayType.setName(null);

        // Create the HolidayType, which fails.


        restHolidayTypeMockMvc.perform(post("/api/holiday-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holidayType)))
            .andExpect(status().isBadRequest());

        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHolidayTypes() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList
        restHolidayTypeMockMvc.perform(get("/api/holiday-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holidayType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getHolidayType() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get the holidayType
        restHolidayTypeMockMvc.perform(get("/api/holiday-types/{id}", holidayType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(holidayType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getHolidayTypesByIdFiltering() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        Long id = holidayType.getId();

        defaultHolidayTypeShouldBeFound("id.equals=" + id);
        defaultHolidayTypeShouldNotBeFound("id.notEquals=" + id);

        defaultHolidayTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHolidayTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultHolidayTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHolidayTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHolidayTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where name equals to DEFAULT_NAME
        defaultHolidayTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the holidayTypeList where name equals to UPDATED_NAME
        defaultHolidayTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where name not equals to DEFAULT_NAME
        defaultHolidayTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the holidayTypeList where name not equals to UPDATED_NAME
        defaultHolidayTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultHolidayTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the holidayTypeList where name equals to UPDATED_NAME
        defaultHolidayTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where name is not null
        defaultHolidayTypeShouldBeFound("name.specified=true");

        // Get all the holidayTypeList where name is null
        defaultHolidayTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllHolidayTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where name contains DEFAULT_NAME
        defaultHolidayTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the holidayTypeList where name contains UPDATED_NAME
        defaultHolidayTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where name does not contain DEFAULT_NAME
        defaultHolidayTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the holidayTypeList where name does not contain UPDATED_NAME
        defaultHolidayTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHolidayTypeShouldBeFound(String filter) throws Exception {
        restHolidayTypeMockMvc.perform(get("/api/holiday-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holidayType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restHolidayTypeMockMvc.perform(get("/api/holiday-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHolidayTypeShouldNotBeFound(String filter) throws Exception {
        restHolidayTypeMockMvc.perform(get("/api/holiday-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHolidayTypeMockMvc.perform(get("/api/holiday-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingHolidayType() throws Exception {
        // Get the holidayType
        restHolidayTypeMockMvc.perform(get("/api/holiday-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHolidayType() throws Exception {
        // Initialize the database
        holidayTypeService.save(holidayType);

        int databaseSizeBeforeUpdate = holidayTypeRepository.findAll().size();

        // Update the holidayType
        HolidayType updatedHolidayType = holidayTypeRepository.findById(holidayType.getId()).get();
        // Disconnect from session so that the updates on updatedHolidayType are not directly saved in db
        em.detach(updatedHolidayType);
        updatedHolidayType
            .name(UPDATED_NAME);

        restHolidayTypeMockMvc.perform(put("/api/holiday-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHolidayType)))
            .andExpect(status().isOk());

        // Validate the HolidayType in the database
        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeUpdate);
        HolidayType testHolidayType = holidayTypeList.get(holidayTypeList.size() - 1);
        assertThat(testHolidayType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingHolidayType() throws Exception {
        int databaseSizeBeforeUpdate = holidayTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolidayTypeMockMvc.perform(put("/api/holiday-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(holidayType)))
            .andExpect(status().isBadRequest());

        // Validate the HolidayType in the database
        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHolidayType() throws Exception {
        // Initialize the database
        holidayTypeService.save(holidayType);

        int databaseSizeBeforeDelete = holidayTypeRepository.findAll().size();

        // Delete the holidayType
        restHolidayTypeMockMvc.perform(delete("/api/holiday-types/{id}", holidayType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
