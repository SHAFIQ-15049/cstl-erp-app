package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.District;
import software.cstl.domain.Division;
import software.cstl.repository.DistrictRepository;
import software.cstl.service.DistrictService;
import software.cstl.service.dto.DistrictCriteria;
import software.cstl.service.DistrictQueryService;

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
 * Integration tests for the {@link DistrictResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DistrictResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANGLA = "AAAAAAAAAA";
    private static final String UPDATED_BANGLA = "BBBBBBBBBB";

    private static final String DEFAULT_WEB = "AAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBB";

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private DistrictQueryService districtQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictMockMvc;

    private District district;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createEntity(EntityManager em) {
        District district = new District()
            .name(DEFAULT_NAME)
            .bangla(DEFAULT_BANGLA)
            .web(DEFAULT_WEB);
        return district;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createUpdatedEntity(EntityManager em) {
        District district = new District()
            .name(UPDATED_NAME)
            .bangla(UPDATED_BANGLA)
            .web(UPDATED_WEB);
        return district;
    }

    @BeforeEach
    public void initTest() {
        district = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistrict() throws Exception {
        int databaseSizeBeforeCreate = districtRepository.findAll().size();
        // Create the District
        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(district)))
            .andExpect(status().isCreated());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeCreate + 1);
        District testDistrict = districtList.get(districtList.size() - 1);
        assertThat(testDistrict.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDistrict.getBangla()).isEqualTo(DEFAULT_BANGLA);
        assertThat(testDistrict.getWeb()).isEqualTo(DEFAULT_WEB);
    }

    @Test
    @Transactional
    public void createDistrictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = districtRepository.findAll().size();

        // Create the District with an existing ID
        district.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(district)))
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtRepository.findAll().size();
        // set the field null
        district.setName(null);

        // Create the District, which fails.


        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(district)))
            .andExpect(status().isBadRequest());

        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBanglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtRepository.findAll().size();
        // set the field null
        district.setBangla(null);

        // Create the District, which fails.


        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(district)))
            .andExpect(status().isBadRequest());

        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDistricts() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList
        restDistrictMockMvc.perform(get("/api/districts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(district.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bangla").value(hasItem(DEFAULT_BANGLA)))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB)));
    }
    
    @Test
    @Transactional
    public void getDistrict() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get the district
        restDistrictMockMvc.perform(get("/api/districts/{id}", district.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(district.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bangla").value(DEFAULT_BANGLA))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB));
    }


    @Test
    @Transactional
    public void getDistrictsByIdFiltering() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        Long id = district.getId();

        defaultDistrictShouldBeFound("id.equals=" + id);
        defaultDistrictShouldNotBeFound("id.notEquals=" + id);

        defaultDistrictShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDistrictShouldNotBeFound("id.greaterThan=" + id);

        defaultDistrictShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDistrictShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDistrictsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where name equals to DEFAULT_NAME
        defaultDistrictShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the districtList where name equals to UPDATED_NAME
        defaultDistrictShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where name not equals to DEFAULT_NAME
        defaultDistrictShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the districtList where name not equals to UPDATED_NAME
        defaultDistrictShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDistrictShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the districtList where name equals to UPDATED_NAME
        defaultDistrictShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where name is not null
        defaultDistrictShouldBeFound("name.specified=true");

        // Get all the districtList where name is null
        defaultDistrictShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByNameContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where name contains DEFAULT_NAME
        defaultDistrictShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the districtList where name contains UPDATED_NAME
        defaultDistrictShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where name does not contain DEFAULT_NAME
        defaultDistrictShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the districtList where name does not contain UPDATED_NAME
        defaultDistrictShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDistrictsByBanglaIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where bangla equals to DEFAULT_BANGLA
        defaultDistrictShouldBeFound("bangla.equals=" + DEFAULT_BANGLA);

        // Get all the districtList where bangla equals to UPDATED_BANGLA
        defaultDistrictShouldNotBeFound("bangla.equals=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDistrictsByBanglaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where bangla not equals to DEFAULT_BANGLA
        defaultDistrictShouldNotBeFound("bangla.notEquals=" + DEFAULT_BANGLA);

        // Get all the districtList where bangla not equals to UPDATED_BANGLA
        defaultDistrictShouldBeFound("bangla.notEquals=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDistrictsByBanglaIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where bangla in DEFAULT_BANGLA or UPDATED_BANGLA
        defaultDistrictShouldBeFound("bangla.in=" + DEFAULT_BANGLA + "," + UPDATED_BANGLA);

        // Get all the districtList where bangla equals to UPDATED_BANGLA
        defaultDistrictShouldNotBeFound("bangla.in=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDistrictsByBanglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where bangla is not null
        defaultDistrictShouldBeFound("bangla.specified=true");

        // Get all the districtList where bangla is null
        defaultDistrictShouldNotBeFound("bangla.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByBanglaContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where bangla contains DEFAULT_BANGLA
        defaultDistrictShouldBeFound("bangla.contains=" + DEFAULT_BANGLA);

        // Get all the districtList where bangla contains UPDATED_BANGLA
        defaultDistrictShouldNotBeFound("bangla.contains=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDistrictsByBanglaNotContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where bangla does not contain DEFAULT_BANGLA
        defaultDistrictShouldNotBeFound("bangla.doesNotContain=" + DEFAULT_BANGLA);

        // Get all the districtList where bangla does not contain UPDATED_BANGLA
        defaultDistrictShouldBeFound("bangla.doesNotContain=" + UPDATED_BANGLA);
    }


    @Test
    @Transactional
    public void getAllDistrictsByWebIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where web equals to DEFAULT_WEB
        defaultDistrictShouldBeFound("web.equals=" + DEFAULT_WEB);

        // Get all the districtList where web equals to UPDATED_WEB
        defaultDistrictShouldNotBeFound("web.equals=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllDistrictsByWebIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where web not equals to DEFAULT_WEB
        defaultDistrictShouldNotBeFound("web.notEquals=" + DEFAULT_WEB);

        // Get all the districtList where web not equals to UPDATED_WEB
        defaultDistrictShouldBeFound("web.notEquals=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllDistrictsByWebIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where web in DEFAULT_WEB or UPDATED_WEB
        defaultDistrictShouldBeFound("web.in=" + DEFAULT_WEB + "," + UPDATED_WEB);

        // Get all the districtList where web equals to UPDATED_WEB
        defaultDistrictShouldNotBeFound("web.in=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllDistrictsByWebIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where web is not null
        defaultDistrictShouldBeFound("web.specified=true");

        // Get all the districtList where web is null
        defaultDistrictShouldNotBeFound("web.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByWebContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where web contains DEFAULT_WEB
        defaultDistrictShouldBeFound("web.contains=" + DEFAULT_WEB);

        // Get all the districtList where web contains UPDATED_WEB
        defaultDistrictShouldNotBeFound("web.contains=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllDistrictsByWebNotContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where web does not contain DEFAULT_WEB
        defaultDistrictShouldNotBeFound("web.doesNotContain=" + DEFAULT_WEB);

        // Get all the districtList where web does not contain UPDATED_WEB
        defaultDistrictShouldBeFound("web.doesNotContain=" + UPDATED_WEB);
    }


    @Test
    @Transactional
    public void getAllDistrictsByDivisionIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);
        Division division = DivisionResourceIT.createEntity(em);
        em.persist(division);
        em.flush();
        district.setDivision(division);
        districtRepository.saveAndFlush(district);
        Long divisionId = division.getId();

        // Get all the districtList where division equals to divisionId
        defaultDistrictShouldBeFound("divisionId.equals=" + divisionId);

        // Get all the districtList where division equals to divisionId + 1
        defaultDistrictShouldNotBeFound("divisionId.equals=" + (divisionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDistrictShouldBeFound(String filter) throws Exception {
        restDistrictMockMvc.perform(get("/api/districts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(district.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bangla").value(hasItem(DEFAULT_BANGLA)))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB)));

        // Check, that the count call also returns 1
        restDistrictMockMvc.perform(get("/api/districts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDistrictShouldNotBeFound(String filter) throws Exception {
        restDistrictMockMvc.perform(get("/api/districts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDistrictMockMvc.perform(get("/api/districts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDistrict() throws Exception {
        // Get the district
        restDistrictMockMvc.perform(get("/api/districts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistrict() throws Exception {
        // Initialize the database
        districtService.save(district);

        int databaseSizeBeforeUpdate = districtRepository.findAll().size();

        // Update the district
        District updatedDistrict = districtRepository.findById(district.getId()).get();
        // Disconnect from session so that the updates on updatedDistrict are not directly saved in db
        em.detach(updatedDistrict);
        updatedDistrict
            .name(UPDATED_NAME)
            .bangla(UPDATED_BANGLA)
            .web(UPDATED_WEB);

        restDistrictMockMvc.perform(put("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDistrict)))
            .andExpect(status().isOk());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
        District testDistrict = districtList.get(districtList.size() - 1);
        assertThat(testDistrict.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistrict.getBangla()).isEqualTo(UPDATED_BANGLA);
        assertThat(testDistrict.getWeb()).isEqualTo(UPDATED_WEB);
    }

    @Test
    @Transactional
    public void updateNonExistingDistrict() throws Exception {
        int databaseSizeBeforeUpdate = districtRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMockMvc.perform(put("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(district)))
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDistrict() throws Exception {
        // Initialize the database
        districtService.save(district);

        int databaseSizeBeforeDelete = districtRepository.findAll().size();

        // Delete the district
        restDistrictMockMvc.perform(delete("/api/districts/{id}", district.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
