package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Designation;
import software.cstl.repository.DesignationRepository;
import software.cstl.repository.search.DesignationSearchRepository;
import software.cstl.service.DesignationService;
import software.cstl.service.dto.DesignationCriteria;
import software.cstl.service.DesignationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DesignationResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DesignationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_IN_BANGLA = "AAAAAAAAAA";
    private static final String UPDATED_NAME_IN_BANGLA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DesignationService designationService;

    /**
     * This repository is mocked in the software.cstl.repository.search test package.
     *
     * @see software.cstl.repository.search.DesignationSearchRepositoryMockConfiguration
     */
    @Autowired
    private DesignationSearchRepository mockDesignationSearchRepository;

    @Autowired
    private DesignationQueryService designationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDesignationMockMvc;

    private Designation designation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Designation createEntity(EntityManager em) {
        Designation designation = new Designation()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .nameInBangla(DEFAULT_NAME_IN_BANGLA)
            .description(DEFAULT_DESCRIPTION);
        return designation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Designation createUpdatedEntity(EntityManager em) {
        Designation designation = new Designation()
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .nameInBangla(UPDATED_NAME_IN_BANGLA)
            .description(UPDATED_DESCRIPTION);
        return designation;
    }

    @BeforeEach
    public void initTest() {
        designation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignation() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();
        // Create the Designation
        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(designation)))
            .andExpect(status().isCreated());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate + 1);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDesignation.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testDesignation.getNameInBangla()).isEqualTo(DEFAULT_NAME_IN_BANGLA);
        assertThat(testDesignation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Designation in Elasticsearch
        verify(mockDesignationSearchRepository, times(1)).save(testDesignation);
    }

    @Test
    @Transactional
    public void createDesignationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();

        // Create the Designation with an existing ID
        designation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(designation)))
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Designation in Elasticsearch
        verify(mockDesignationSearchRepository, times(0)).save(designation);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationRepository.findAll().size();
        // set the field null
        designation.setName(null);

        // Create the Designation, which fails.


        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(designation)))
            .andExpect(status().isBadRequest());

        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignations() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList
        restDesignationMockMvc.perform(get("/api/designations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].nameInBangla").value(hasItem(DEFAULT_NAME_IN_BANGLA)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get the designation
        restDesignationMockMvc.perform(get("/api/designations/{id}", designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(designation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.nameInBangla").value(DEFAULT_NAME_IN_BANGLA))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }


    @Test
    @Transactional
    public void getDesignationsByIdFiltering() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        Long id = designation.getId();

        defaultDesignationShouldBeFound("id.equals=" + id);
        defaultDesignationShouldNotBeFound("id.notEquals=" + id);

        defaultDesignationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDesignationShouldNotBeFound("id.greaterThan=" + id);

        defaultDesignationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDesignationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDesignationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name equals to DEFAULT_NAME
        defaultDesignationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the designationList where name equals to UPDATED_NAME
        defaultDesignationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name not equals to DEFAULT_NAME
        defaultDesignationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the designationList where name not equals to UPDATED_NAME
        defaultDesignationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDesignationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the designationList where name equals to UPDATED_NAME
        defaultDesignationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name is not null
        defaultDesignationShouldBeFound("name.specified=true");

        // Get all the designationList where name is null
        defaultDesignationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDesignationsByNameContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name contains DEFAULT_NAME
        defaultDesignationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the designationList where name contains UPDATED_NAME
        defaultDesignationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name does not contain DEFAULT_NAME
        defaultDesignationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the designationList where name does not contain UPDATED_NAME
        defaultDesignationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDesignationsByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where shortName equals to DEFAULT_SHORT_NAME
        defaultDesignationShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the designationList where shortName equals to UPDATED_SHORT_NAME
        defaultDesignationShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByShortNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where shortName not equals to DEFAULT_SHORT_NAME
        defaultDesignationShouldNotBeFound("shortName.notEquals=" + DEFAULT_SHORT_NAME);

        // Get all the designationList where shortName not equals to UPDATED_SHORT_NAME
        defaultDesignationShouldBeFound("shortName.notEquals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultDesignationShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the designationList where shortName equals to UPDATED_SHORT_NAME
        defaultDesignationShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where shortName is not null
        defaultDesignationShouldBeFound("shortName.specified=true");

        // Get all the designationList where shortName is null
        defaultDesignationShouldNotBeFound("shortName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDesignationsByShortNameContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where shortName contains DEFAULT_SHORT_NAME
        defaultDesignationShouldBeFound("shortName.contains=" + DEFAULT_SHORT_NAME);

        // Get all the designationList where shortName contains UPDATED_SHORT_NAME
        defaultDesignationShouldNotBeFound("shortName.contains=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByShortNameNotContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where shortName does not contain DEFAULT_SHORT_NAME
        defaultDesignationShouldNotBeFound("shortName.doesNotContain=" + DEFAULT_SHORT_NAME);

        // Get all the designationList where shortName does not contain UPDATED_SHORT_NAME
        defaultDesignationShouldBeFound("shortName.doesNotContain=" + UPDATED_SHORT_NAME);
    }


    @Test
    @Transactional
    public void getAllDesignationsByNameInBanglaIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where nameInBangla equals to DEFAULT_NAME_IN_BANGLA
        defaultDesignationShouldBeFound("nameInBangla.equals=" + DEFAULT_NAME_IN_BANGLA);

        // Get all the designationList where nameInBangla equals to UPDATED_NAME_IN_BANGLA
        defaultDesignationShouldNotBeFound("nameInBangla.equals=" + UPDATED_NAME_IN_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameInBanglaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where nameInBangla not equals to DEFAULT_NAME_IN_BANGLA
        defaultDesignationShouldNotBeFound("nameInBangla.notEquals=" + DEFAULT_NAME_IN_BANGLA);

        // Get all the designationList where nameInBangla not equals to UPDATED_NAME_IN_BANGLA
        defaultDesignationShouldBeFound("nameInBangla.notEquals=" + UPDATED_NAME_IN_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameInBanglaIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where nameInBangla in DEFAULT_NAME_IN_BANGLA or UPDATED_NAME_IN_BANGLA
        defaultDesignationShouldBeFound("nameInBangla.in=" + DEFAULT_NAME_IN_BANGLA + "," + UPDATED_NAME_IN_BANGLA);

        // Get all the designationList where nameInBangla equals to UPDATED_NAME_IN_BANGLA
        defaultDesignationShouldNotBeFound("nameInBangla.in=" + UPDATED_NAME_IN_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameInBanglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where nameInBangla is not null
        defaultDesignationShouldBeFound("nameInBangla.specified=true");

        // Get all the designationList where nameInBangla is null
        defaultDesignationShouldNotBeFound("nameInBangla.specified=false");
    }
                @Test
    @Transactional
    public void getAllDesignationsByNameInBanglaContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where nameInBangla contains DEFAULT_NAME_IN_BANGLA
        defaultDesignationShouldBeFound("nameInBangla.contains=" + DEFAULT_NAME_IN_BANGLA);

        // Get all the designationList where nameInBangla contains UPDATED_NAME_IN_BANGLA
        defaultDesignationShouldNotBeFound("nameInBangla.contains=" + UPDATED_NAME_IN_BANGLA);
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameInBanglaNotContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where nameInBangla does not contain DEFAULT_NAME_IN_BANGLA
        defaultDesignationShouldNotBeFound("nameInBangla.doesNotContain=" + DEFAULT_NAME_IN_BANGLA);

        // Get all the designationList where nameInBangla does not contain UPDATED_NAME_IN_BANGLA
        defaultDesignationShouldBeFound("nameInBangla.doesNotContain=" + UPDATED_NAME_IN_BANGLA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDesignationShouldBeFound(String filter) throws Exception {
        restDesignationMockMvc.perform(get("/api/designations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].nameInBangla").value(hasItem(DEFAULT_NAME_IN_BANGLA)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restDesignationMockMvc.perform(get("/api/designations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDesignationShouldNotBeFound(String filter) throws Exception {
        restDesignationMockMvc.perform(get("/api/designations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDesignationMockMvc.perform(get("/api/designations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDesignation() throws Exception {
        // Get the designation
        restDesignationMockMvc.perform(get("/api/designations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignation() throws Exception {
        // Initialize the database
        designationService.save(designation);

        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation
        Designation updatedDesignation = designationRepository.findById(designation.getId()).get();
        // Disconnect from session so that the updates on updatedDesignation are not directly saved in db
        em.detach(updatedDesignation);
        updatedDesignation
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .nameInBangla(UPDATED_NAME_IN_BANGLA)
            .description(UPDATED_DESCRIPTION);

        restDesignationMockMvc.perform(put("/api/designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDesignation)))
            .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDesignation.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testDesignation.getNameInBangla()).isEqualTo(UPDATED_NAME_IN_BANGLA);
        assertThat(testDesignation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Designation in Elasticsearch
        verify(mockDesignationSearchRepository, times(2)).save(testDesignation);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationMockMvc.perform(put("/api/designations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(designation)))
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Designation in Elasticsearch
        verify(mockDesignationSearchRepository, times(0)).save(designation);
    }

    @Test
    @Transactional
    public void deleteDesignation() throws Exception {
        // Initialize the database
        designationService.save(designation);

        int databaseSizeBeforeDelete = designationRepository.findAll().size();

        // Delete the designation
        restDesignationMockMvc.perform(delete("/api/designations/{id}", designation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Designation in Elasticsearch
        verify(mockDesignationSearchRepository, times(1)).deleteById(designation.getId());
    }

    @Test
    @Transactional
    public void searchDesignation() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        designationService.save(designation);
        when(mockDesignationSearchRepository.search(queryStringQuery("id:" + designation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(designation), PageRequest.of(0, 1), 1));

        // Search the designation
        restDesignationMockMvc.perform(get("/api/_search/designations?query=id:" + designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].nameInBangla").value(hasItem(DEFAULT_NAME_IN_BANGLA)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
}
