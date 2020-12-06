package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Thana;
import software.cstl.domain.District;
import software.cstl.repository.ThanaRepository;
import software.cstl.service.ThanaService;
import software.cstl.service.dto.ThanaCriteria;
import software.cstl.service.ThanaQueryService;

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
 * Integration tests for the {@link ThanaResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ThanaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANGLA = "AAAAAAAAAA";
    private static final String UPDATED_BANGLA = "BBBBBBBBBB";

    private static final String DEFAULT_WEB = "AAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBB";

    @Autowired
    private ThanaRepository thanaRepository;

    @Autowired
    private ThanaService thanaService;

    @Autowired
    private ThanaQueryService thanaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThanaMockMvc;

    private Thana thana;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thana createEntity(EntityManager em) {
        Thana thana = new Thana()
            .name(DEFAULT_NAME)
            .bangla(DEFAULT_BANGLA)
            .web(DEFAULT_WEB);
        return thana;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thana createUpdatedEntity(EntityManager em) {
        Thana thana = new Thana()
            .name(UPDATED_NAME)
            .bangla(UPDATED_BANGLA)
            .web(UPDATED_WEB);
        return thana;
    }

    @BeforeEach
    public void initTest() {
        thana = createEntity(em);
    }

    @Test
    @Transactional
    public void createThana() throws Exception {
        int databaseSizeBeforeCreate = thanaRepository.findAll().size();
        // Create the Thana
        restThanaMockMvc.perform(post("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thana)))
            .andExpect(status().isCreated());

        // Validate the Thana in the database
        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeCreate + 1);
        Thana testThana = thanaList.get(thanaList.size() - 1);
        assertThat(testThana.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThana.getBangla()).isEqualTo(DEFAULT_BANGLA);
        assertThat(testThana.getWeb()).isEqualTo(DEFAULT_WEB);
    }

    @Test
    @Transactional
    public void createThanaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thanaRepository.findAll().size();

        // Create the Thana with an existing ID
        thana.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThanaMockMvc.perform(post("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thana)))
            .andExpect(status().isBadRequest());

        // Validate the Thana in the database
        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = thanaRepository.findAll().size();
        // set the field null
        thana.setName(null);

        // Create the Thana, which fails.


        restThanaMockMvc.perform(post("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thana)))
            .andExpect(status().isBadRequest());

        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBanglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = thanaRepository.findAll().size();
        // set the field null
        thana.setBangla(null);

        // Create the Thana, which fails.


        restThanaMockMvc.perform(post("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thana)))
            .andExpect(status().isBadRequest());

        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThanas() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList
        restThanaMockMvc.perform(get("/api/thanas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thana.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bangla").value(hasItem(DEFAULT_BANGLA)))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB)));
    }
    
    @Test
    @Transactional
    public void getThana() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get the thana
        restThanaMockMvc.perform(get("/api/thanas/{id}", thana.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thana.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bangla").value(DEFAULT_BANGLA))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB));
    }


    @Test
    @Transactional
    public void getThanasByIdFiltering() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        Long id = thana.getId();

        defaultThanaShouldBeFound("id.equals=" + id);
        defaultThanaShouldNotBeFound("id.notEquals=" + id);

        defaultThanaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultThanaShouldNotBeFound("id.greaterThan=" + id);

        defaultThanaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultThanaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllThanasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where name equals to DEFAULT_NAME
        defaultThanaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the thanaList where name equals to UPDATED_NAME
        defaultThanaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllThanasByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where name not equals to DEFAULT_NAME
        defaultThanaShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the thanaList where name not equals to UPDATED_NAME
        defaultThanaShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllThanasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultThanaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the thanaList where name equals to UPDATED_NAME
        defaultThanaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllThanasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where name is not null
        defaultThanaShouldBeFound("name.specified=true");

        // Get all the thanaList where name is null
        defaultThanaShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllThanasByNameContainsSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where name contains DEFAULT_NAME
        defaultThanaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the thanaList where name contains UPDATED_NAME
        defaultThanaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllThanasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where name does not contain DEFAULT_NAME
        defaultThanaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the thanaList where name does not contain UPDATED_NAME
        defaultThanaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllThanasByBanglaIsEqualToSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where bangla equals to DEFAULT_BANGLA
        defaultThanaShouldBeFound("bangla.equals=" + DEFAULT_BANGLA);

        // Get all the thanaList where bangla equals to UPDATED_BANGLA
        defaultThanaShouldNotBeFound("bangla.equals=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllThanasByBanglaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where bangla not equals to DEFAULT_BANGLA
        defaultThanaShouldNotBeFound("bangla.notEquals=" + DEFAULT_BANGLA);

        // Get all the thanaList where bangla not equals to UPDATED_BANGLA
        defaultThanaShouldBeFound("bangla.notEquals=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllThanasByBanglaIsInShouldWork() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where bangla in DEFAULT_BANGLA or UPDATED_BANGLA
        defaultThanaShouldBeFound("bangla.in=" + DEFAULT_BANGLA + "," + UPDATED_BANGLA);

        // Get all the thanaList where bangla equals to UPDATED_BANGLA
        defaultThanaShouldNotBeFound("bangla.in=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllThanasByBanglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where bangla is not null
        defaultThanaShouldBeFound("bangla.specified=true");

        // Get all the thanaList where bangla is null
        defaultThanaShouldNotBeFound("bangla.specified=false");
    }
                @Test
    @Transactional
    public void getAllThanasByBanglaContainsSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where bangla contains DEFAULT_BANGLA
        defaultThanaShouldBeFound("bangla.contains=" + DEFAULT_BANGLA);

        // Get all the thanaList where bangla contains UPDATED_BANGLA
        defaultThanaShouldNotBeFound("bangla.contains=" + UPDATED_BANGLA);
    }

    @Test
    @Transactional
    public void getAllThanasByBanglaNotContainsSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where bangla does not contain DEFAULT_BANGLA
        defaultThanaShouldNotBeFound("bangla.doesNotContain=" + DEFAULT_BANGLA);

        // Get all the thanaList where bangla does not contain UPDATED_BANGLA
        defaultThanaShouldBeFound("bangla.doesNotContain=" + UPDATED_BANGLA);
    }


    @Test
    @Transactional
    public void getAllThanasByWebIsEqualToSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where web equals to DEFAULT_WEB
        defaultThanaShouldBeFound("web.equals=" + DEFAULT_WEB);

        // Get all the thanaList where web equals to UPDATED_WEB
        defaultThanaShouldNotBeFound("web.equals=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllThanasByWebIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where web not equals to DEFAULT_WEB
        defaultThanaShouldNotBeFound("web.notEquals=" + DEFAULT_WEB);

        // Get all the thanaList where web not equals to UPDATED_WEB
        defaultThanaShouldBeFound("web.notEquals=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllThanasByWebIsInShouldWork() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where web in DEFAULT_WEB or UPDATED_WEB
        defaultThanaShouldBeFound("web.in=" + DEFAULT_WEB + "," + UPDATED_WEB);

        // Get all the thanaList where web equals to UPDATED_WEB
        defaultThanaShouldNotBeFound("web.in=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllThanasByWebIsNullOrNotNull() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where web is not null
        defaultThanaShouldBeFound("web.specified=true");

        // Get all the thanaList where web is null
        defaultThanaShouldNotBeFound("web.specified=false");
    }
                @Test
    @Transactional
    public void getAllThanasByWebContainsSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where web contains DEFAULT_WEB
        defaultThanaShouldBeFound("web.contains=" + DEFAULT_WEB);

        // Get all the thanaList where web contains UPDATED_WEB
        defaultThanaShouldNotBeFound("web.contains=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllThanasByWebNotContainsSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where web does not contain DEFAULT_WEB
        defaultThanaShouldNotBeFound("web.doesNotContain=" + DEFAULT_WEB);

        // Get all the thanaList where web does not contain UPDATED_WEB
        defaultThanaShouldBeFound("web.doesNotContain=" + UPDATED_WEB);
    }


    @Test
    @Transactional
    public void getAllThanasByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);
        District district = DistrictResourceIT.createEntity(em);
        em.persist(district);
        em.flush();
        thana.setDistrict(district);
        thanaRepository.saveAndFlush(thana);
        Long districtId = district.getId();

        // Get all the thanaList where district equals to districtId
        defaultThanaShouldBeFound("districtId.equals=" + districtId);

        // Get all the thanaList where district equals to districtId + 1
        defaultThanaShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultThanaShouldBeFound(String filter) throws Exception {
        restThanaMockMvc.perform(get("/api/thanas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thana.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bangla").value(hasItem(DEFAULT_BANGLA)))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB)));

        // Check, that the count call also returns 1
        restThanaMockMvc.perform(get("/api/thanas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultThanaShouldNotBeFound(String filter) throws Exception {
        restThanaMockMvc.perform(get("/api/thanas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restThanaMockMvc.perform(get("/api/thanas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingThana() throws Exception {
        // Get the thana
        restThanaMockMvc.perform(get("/api/thanas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThana() throws Exception {
        // Initialize the database
        thanaService.save(thana);

        int databaseSizeBeforeUpdate = thanaRepository.findAll().size();

        // Update the thana
        Thana updatedThana = thanaRepository.findById(thana.getId()).get();
        // Disconnect from session so that the updates on updatedThana are not directly saved in db
        em.detach(updatedThana);
        updatedThana
            .name(UPDATED_NAME)
            .bangla(UPDATED_BANGLA)
            .web(UPDATED_WEB);

        restThanaMockMvc.perform(put("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedThana)))
            .andExpect(status().isOk());

        // Validate the Thana in the database
        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeUpdate);
        Thana testThana = thanaList.get(thanaList.size() - 1);
        assertThat(testThana.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThana.getBangla()).isEqualTo(UPDATED_BANGLA);
        assertThat(testThana.getWeb()).isEqualTo(UPDATED_WEB);
    }

    @Test
    @Transactional
    public void updateNonExistingThana() throws Exception {
        int databaseSizeBeforeUpdate = thanaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThanaMockMvc.perform(put("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thana)))
            .andExpect(status().isBadRequest());

        // Validate the Thana in the database
        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThana() throws Exception {
        // Initialize the database
        thanaService.save(thana);

        int databaseSizeBeforeDelete = thanaRepository.findAll().size();

        // Delete the thana
        restThanaMockMvc.perform(delete("/api/thanas/{id}", thana.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
