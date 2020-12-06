package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Line;
import software.cstl.domain.Department;
import software.cstl.repository.LineRepository;
import software.cstl.service.LineService;
import software.cstl.service.dto.LineCriteria;
import software.cstl.service.LineQueryService;

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
 * Integration tests for the {@link LineResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private LineService lineService;

    @Autowired
    private LineQueryService lineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLineMockMvc;

    private Line line;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Line createEntity(EntityManager em) {
        Line line = new Line()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return line;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Line createUpdatedEntity(EntityManager em) {
        Line line = new Line()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return line;
    }

    @BeforeEach
    public void initTest() {
        line = createEntity(em);
    }

    @Test
    @Transactional
    public void createLine() throws Exception {
        int databaseSizeBeforeCreate = lineRepository.findAll().size();
        // Create the Line
        restLineMockMvc.perform(post("/api/lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(line)))
            .andExpect(status().isCreated());

        // Validate the Line in the database
        List<Line> lineList = lineRepository.findAll();
        assertThat(lineList).hasSize(databaseSizeBeforeCreate + 1);
        Line testLine = lineList.get(lineList.size() - 1);
        assertThat(testLine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lineRepository.findAll().size();

        // Create the Line with an existing ID
        line.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLineMockMvc.perform(post("/api/lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(line)))
            .andExpect(status().isBadRequest());

        // Validate the Line in the database
        List<Line> lineList = lineRepository.findAll();
        assertThat(lineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lineRepository.findAll().size();
        // set the field null
        line.setName(null);

        // Create the Line, which fails.


        restLineMockMvc.perform(post("/api/lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(line)))
            .andExpect(status().isBadRequest());

        List<Line> lineList = lineRepository.findAll();
        assertThat(lineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLines() throws Exception {
        // Initialize the database
        lineRepository.saveAndFlush(line);

        // Get all the lineList
        restLineMockMvc.perform(get("/api/lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(line.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getLine() throws Exception {
        // Initialize the database
        lineRepository.saveAndFlush(line);

        // Get the line
        restLineMockMvc.perform(get("/api/lines/{id}", line.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(line.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }


    @Test
    @Transactional
    public void getLinesByIdFiltering() throws Exception {
        // Initialize the database
        lineRepository.saveAndFlush(line);

        Long id = line.getId();

        defaultLineShouldBeFound("id.equals=" + id);
        defaultLineShouldNotBeFound("id.notEquals=" + id);

        defaultLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLineShouldNotBeFound("id.greaterThan=" + id);

        defaultLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLinesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        lineRepository.saveAndFlush(line);

        // Get all the lineList where name equals to DEFAULT_NAME
        defaultLineShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the lineList where name equals to UPDATED_NAME
        defaultLineShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLinesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lineRepository.saveAndFlush(line);

        // Get all the lineList where name not equals to DEFAULT_NAME
        defaultLineShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the lineList where name not equals to UPDATED_NAME
        defaultLineShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLinesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        lineRepository.saveAndFlush(line);

        // Get all the lineList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLineShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the lineList where name equals to UPDATED_NAME
        defaultLineShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLinesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        lineRepository.saveAndFlush(line);

        // Get all the lineList where name is not null
        defaultLineShouldBeFound("name.specified=true");

        // Get all the lineList where name is null
        defaultLineShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllLinesByNameContainsSomething() throws Exception {
        // Initialize the database
        lineRepository.saveAndFlush(line);

        // Get all the lineList where name contains DEFAULT_NAME
        defaultLineShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the lineList where name contains UPDATED_NAME
        defaultLineShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLinesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        lineRepository.saveAndFlush(line);

        // Get all the lineList where name does not contain DEFAULT_NAME
        defaultLineShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the lineList where name does not contain UPDATED_NAME
        defaultLineShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllLinesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        lineRepository.saveAndFlush(line);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        line.setDepartment(department);
        lineRepository.saveAndFlush(line);
        Long departmentId = department.getId();

        // Get all the lineList where department equals to departmentId
        defaultLineShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the lineList where department equals to departmentId + 1
        defaultLineShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLineShouldBeFound(String filter) throws Exception {
        restLineMockMvc.perform(get("/api/lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(line.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restLineMockMvc.perform(get("/api/lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLineShouldNotBeFound(String filter) throws Exception {
        restLineMockMvc.perform(get("/api/lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLineMockMvc.perform(get("/api/lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLine() throws Exception {
        // Get the line
        restLineMockMvc.perform(get("/api/lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLine() throws Exception {
        // Initialize the database
        lineService.save(line);

        int databaseSizeBeforeUpdate = lineRepository.findAll().size();

        // Update the line
        Line updatedLine = lineRepository.findById(line.getId()).get();
        // Disconnect from session so that the updates on updatedLine are not directly saved in db
        em.detach(updatedLine);
        updatedLine
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restLineMockMvc.perform(put("/api/lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLine)))
            .andExpect(status().isOk());

        // Validate the Line in the database
        List<Line> lineList = lineRepository.findAll();
        assertThat(lineList).hasSize(databaseSizeBeforeUpdate);
        Line testLine = lineList.get(lineList.size() - 1);
        assertThat(testLine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLine() throws Exception {
        int databaseSizeBeforeUpdate = lineRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLineMockMvc.perform(put("/api/lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(line)))
            .andExpect(status().isBadRequest());

        // Validate the Line in the database
        List<Line> lineList = lineRepository.findAll();
        assertThat(lineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLine() throws Exception {
        // Initialize the database
        lineService.save(line);

        int databaseSizeBeforeDelete = lineRepository.findAll().size();

        // Delete the line
        restLineMockMvc.perform(delete("/api/lines/{id}", line.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Line> lineList = lineRepository.findAll();
        assertThat(lineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
