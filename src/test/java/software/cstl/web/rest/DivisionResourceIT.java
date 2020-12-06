package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Division;
import software.cstl.repository.DivisionRepository;
import software.cstl.service.DivisionService;
import software.cstl.service.dto.DivisionCriteria;
import software.cstl.service.DivisionQueryService;

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
 * Integration tests for the {@link DivisionResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DivisionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANGLA = "AAAAAAAAAA";
    private static final String UPDATED_BANGLA = "BBBBBBBBBB";

    private static final String DEFAULT_WEB = "AAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBB";

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private DivisionQueryService divisionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDivisionMockMvc;

    private Division division;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Division createEntity(EntityManager em) {
        Division division = new Division()
            .name(DEFAULT_NAME)
            .bangla(DEFAULT_BANGLA)
            .web(DEFAULT_WEB);
        return division;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Division createUpdatedEntity(EntityManager em) {
        Division division = new Division()
            .name(UPDATED_NAME)
            .bangla(UPDATED_BANGLA)
            .web(UPDATED_WEB);
        return division;
    }

    @BeforeEach
    public void initTest() {
        division = createEntity(em);
    }

    @Test
    @Transactional
    public void createDivision() throws Exception {
        int databaseSizeBeforeCreate = divisionRepository.findAll().size();
        // Create the Division
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(division)))
            .andExpect(status().isCreated());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate + 1);
        Division testDivision = divisionList.get(divisionList.size() - 1);
        assertThat(testDivision.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDivision.getBangla()).isEqualTo(DEFAULT_BANGLA);
        assertThat(testDivision.getWeb()).isEqualTo(DEFAULT_WEB);
    }

    @Test
    @Transactional
    public void createDivisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = divisionRepository.findAll().size();

        // Create the Division with an existing ID
        division.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(division)))
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionRepository.findAll().size();
        // set the field null
        division.setName(null);

        // Create the Division, which fails.


        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(division)))
            .andExpect(status().isBadRequest());

        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBanglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionRepository.findAll().size();
        // set the field null
        division.setBangla(null);

        // Create the Division, which fails.


        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(division)))
            .andExpect(status().isBadRequest());

        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDivisions() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(division.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bangla").value(hasItem(DEFAULT_BANGLA)))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB)));
    }
    
    @Test
    @Transactional
    public void getDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", division.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(division.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bangla").value(DEFAULT_BANGLA))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB));
    }


    @Test
    @Transactional
    public void getDivisionsByIdFiltering() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        Long id = division.getId();

        defaultDivisionShouldBeFound("id.equals=" + id);
        defaultDivisionShouldNotBeFound("id.notEquals=" + id);

        defaultDivisionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDivisionShouldNotBeFound("id.greaterThan=" + id);

        defaultDivisionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDivisionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDivisionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name equals to DEFAULT_NAME
        defaultDivisionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the divisionList where name equals to UPDATED_NAME
        defaultDivisionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name not equals to DEFAULT_NAME
        defaultDivisionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the divisionList where name not equals to UPDATED_NAME
        defaultDivisionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDivisionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the divisionList where name equals to UPDATED_NAME
        defaultDivisionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name is not null
        defaultDivisionShouldBeFound("name.specified=true");

        // Get all the divisionList where name is null
        defaultDivisionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDivisionsByNameContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name contains DEFAULT_NAME
        defaultDivisionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the divisionList where name contains UPDATED_NAME
        defaultDivisionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where name does not contain DEFAULT_NAME
        defaultDivisionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the divisionList where name does not contain UPDATED_NAME
        defaultDivisionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDivisionsByBanglaIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where bangla equals to DEFAULT_BANGLA
        defaultDivisionShouldBeFound("bangla.equals=" + DEFAULT_BANGLA);

        // Get all the divisionList where bangla equals to UPDATED_BANGLA
        defaultDivisionShouldNotBeFound("bangla.equals=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDivisionsByBanglaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where bangla not equals to DEFAULT_BANGLA
        defaultDivisionShouldNotBeFound("bangla.notEquals=" + DEFAULT_BANGLA);

        // Get all the divisionList where bangla not equals to UPDATED_BANGLA
        defaultDivisionShouldBeFound("bangla.notEquals=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDivisionsByBanglaIsInShouldWork() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where bangla in DEFAULT_BANGLA or UPDATED_BANGLA
        defaultDivisionShouldBeFound("bangla.in=" + DEFAULT_BANGLA + "," + UPDATED_BANGLA);

        // Get all the divisionList where bangla equals to UPDATED_BANGLA
        defaultDivisionShouldNotBeFound("bangla.in=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDivisionsByBanglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where bangla is not null
        defaultDivisionShouldBeFound("bangla.specified=true");

        // Get all the divisionList where bangla is null
        defaultDivisionShouldNotBeFound("bangla.specified=false");
    }
                @Test
    @Transactional
    public void getAllDivisionsByBanglaContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where bangla contains DEFAULT_BANGLA
        defaultDivisionShouldBeFound("bangla.contains=" + DEFAULT_BANGLA);

        // Get all the divisionList where bangla contains UPDATED_BANGLA
        defaultDivisionShouldNotBeFound("bangla.contains=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDivisionsByBanglaNotContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where bangla does not contain DEFAULT_BANGLA
        defaultDivisionShouldNotBeFound("bangla.doesNotContain=" + DEFAULT_BANGLA);

        // Get all the divisionList where bangla does not contain UPDATED_BANGLA
        defaultDivisionShouldBeFound("bangla.doesNotContain=" + UPDATED_BANGLA);
    }


    @Test
    @Transactional
    public void getAllDivisionsByWebIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where web equals to DEFAULT_WEB
        defaultDivisionShouldBeFound("web.equals=" + DEFAULT_WEB);

        // Get all the divisionList where web equals to UPDATED_WEB
        defaultDivisionShouldNotBeFound("web.equals=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllDivisionsByWebIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where web not equals to DEFAULT_WEB
        defaultDivisionShouldNotBeFound("web.notEquals=" + DEFAULT_WEB);

        // Get all the divisionList where web not equals to UPDATED_WEB
        defaultDivisionShouldBeFound("web.notEquals=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllDivisionsByWebIsInShouldWork() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where web in DEFAULT_WEB or UPDATED_WEB
        defaultDivisionShouldBeFound("web.in=" + DEFAULT_WEB + "," + UPDATED_WEB);

        // Get all the divisionList where web equals to UPDATED_WEB
        defaultDivisionShouldNotBeFound("web.in=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllDivisionsByWebIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where web is not null
        defaultDivisionShouldBeFound("web.specified=true");

        // Get all the divisionList where web is null
        defaultDivisionShouldNotBeFound("web.specified=false");
    }
                @Test
    @Transactional
    public void getAllDivisionsByWebContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where web contains DEFAULT_WEB
        defaultDivisionShouldBeFound("web.contains=" + DEFAULT_WEB);

        // Get all the divisionList where web contains UPDATED_WEB
        defaultDivisionShouldNotBeFound("web.contains=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllDivisionsByWebNotContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where web does not contain DEFAULT_WEB
        defaultDivisionShouldNotBeFound("web.doesNotContain=" + DEFAULT_WEB);

        // Get all the divisionList where web does not contain UPDATED_WEB
        defaultDivisionShouldBeFound("web.doesNotContain=" + UPDATED_WEB);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDivisionShouldBeFound(String filter) throws Exception {
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(division.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bangla").value(hasItem(DEFAULT_BANGLA)))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB)));

        // Check, that the count call also returns 1
        restDivisionMockMvc.perform(get("/api/divisions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDivisionShouldNotBeFound(String filter) throws Exception {
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDivisionMockMvc.perform(get("/api/divisions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDivision() throws Exception {
        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDivision() throws Exception {
        // Initialize the database
        divisionService.save(division);

        int databaseSizeBeforeUpdate = divisionRepository.findAll().size();

        // Update the division
        Division updatedDivision = divisionRepository.findById(division.getId()).get();
        // Disconnect from session so that the updates on updatedDivision are not directly saved in db
        em.detach(updatedDivision);
        updatedDivision
            .name(UPDATED_NAME)
            .bangla(UPDATED_BANGLA)
            .web(UPDATED_WEB);

        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDivision)))
            .andExpect(status().isOk());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate);
        Division testDivision = divisionList.get(divisionList.size() - 1);
        assertThat(testDivision.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDivision.getBangla()).isEqualTo(UPDATED_BANGLA);
        assertThat(testDivision.getWeb()).isEqualTo(UPDATED_WEB);
    }

    @Test
    @Transactional
    public void updateNonExistingDivision() throws Exception {
        int databaseSizeBeforeUpdate = divisionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(division)))
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDivision() throws Exception {
        // Initialize the database
        divisionService.save(division);

        int databaseSizeBeforeDelete = divisionRepository.findAll().size();

        // Delete the division
        restDivisionMockMvc.perform(delete("/api/divisions/{id}", division.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
