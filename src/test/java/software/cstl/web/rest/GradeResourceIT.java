package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Grade;
import software.cstl.repository.GradeRepository;
import software.cstl.service.GradeService;
import software.cstl.service.dto.GradeCriteria;
import software.cstl.service.GradeQueryService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.EmployeeCategory;
/**
 * Integration tests for the {@link GradeResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GradeResourceIT {

    private static final EmployeeCategory DEFAULT_CATEGORY = EmployeeCategory.TOP_LEVEL;
    private static final EmployeeCategory UPDATED_CATEGORY = EmployeeCategory.MID_LEVEL;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_INITIAL_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_INITIAL_SALARY = new BigDecimal(2);
    private static final BigDecimal SMALLER_INITIAL_SALARY = new BigDecimal(1 - 1);

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private GradeQueryService gradeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGradeMockMvc;

    private Grade grade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grade createEntity(EntityManager em) {
        Grade grade = new Grade()
            .category(DEFAULT_CATEGORY)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .initialSalary(DEFAULT_INITIAL_SALARY);
        return grade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grade createUpdatedEntity(EntityManager em) {
        Grade grade = new Grade()
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .initialSalary(UPDATED_INITIAL_SALARY);
        return grade;
    }

    @BeforeEach
    public void initTest() {
        grade = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrade() throws Exception {
        int databaseSizeBeforeCreate = gradeRepository.findAll().size();
        // Create the Grade
        restGradeMockMvc.perform(post("/api/grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grade)))
            .andExpect(status().isCreated());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeCreate + 1);
        Grade testGrade = gradeList.get(gradeList.size() - 1);
        assertThat(testGrade.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testGrade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGrade.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGrade.getInitialSalary()).isEqualTo(DEFAULT_INITIAL_SALARY);
    }

    @Test
    @Transactional
    public void createGradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gradeRepository.findAll().size();

        // Create the Grade with an existing ID
        grade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradeMockMvc.perform(post("/api/grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grade)))
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeRepository.findAll().size();
        // set the field null
        grade.setName(null);

        // Create the Grade, which fails.


        restGradeMockMvc.perform(post("/api/grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grade)))
            .andExpect(status().isBadRequest());

        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrades() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList
        restGradeMockMvc.perform(get("/api/grades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grade.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].initialSalary").value(hasItem(DEFAULT_INITIAL_SALARY.intValue())));
    }
    
    @Test
    @Transactional
    public void getGrade() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get the grade
        restGradeMockMvc.perform(get("/api/grades/{id}", grade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grade.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.initialSalary").value(DEFAULT_INITIAL_SALARY.intValue()));
    }


    @Test
    @Transactional
    public void getGradesByIdFiltering() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        Long id = grade.getId();

        defaultGradeShouldBeFound("id.equals=" + id);
        defaultGradeShouldNotBeFound("id.notEquals=" + id);

        defaultGradeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGradeShouldNotBeFound("id.greaterThan=" + id);

        defaultGradeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGradeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGradesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where category equals to DEFAULT_CATEGORY
        defaultGradeShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the gradeList where category equals to UPDATED_CATEGORY
        defaultGradeShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllGradesByCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where category not equals to DEFAULT_CATEGORY
        defaultGradeShouldNotBeFound("category.notEquals=" + DEFAULT_CATEGORY);

        // Get all the gradeList where category not equals to UPDATED_CATEGORY
        defaultGradeShouldBeFound("category.notEquals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllGradesByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultGradeShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the gradeList where category equals to UPDATED_CATEGORY
        defaultGradeShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllGradesByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where category is not null
        defaultGradeShouldBeFound("category.specified=true");

        // Get all the gradeList where category is null
        defaultGradeShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    public void getAllGradesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where name equals to DEFAULT_NAME
        defaultGradeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the gradeList where name equals to UPDATED_NAME
        defaultGradeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGradesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where name not equals to DEFAULT_NAME
        defaultGradeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the gradeList where name not equals to UPDATED_NAME
        defaultGradeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGradesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGradeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the gradeList where name equals to UPDATED_NAME
        defaultGradeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGradesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where name is not null
        defaultGradeShouldBeFound("name.specified=true");

        // Get all the gradeList where name is null
        defaultGradeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllGradesByNameContainsSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where name contains DEFAULT_NAME
        defaultGradeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the gradeList where name contains UPDATED_NAME
        defaultGradeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGradesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where name does not contain DEFAULT_NAME
        defaultGradeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the gradeList where name does not contain UPDATED_NAME
        defaultGradeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllGradesByInitialSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where initialSalary equals to DEFAULT_INITIAL_SALARY
        defaultGradeShouldBeFound("initialSalary.equals=" + DEFAULT_INITIAL_SALARY);

        // Get all the gradeList where initialSalary equals to UPDATED_INITIAL_SALARY
        defaultGradeShouldNotBeFound("initialSalary.equals=" + UPDATED_INITIAL_SALARY);
    }

    @Test
    @Transactional
    public void getAllGradesByInitialSalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where initialSalary not equals to DEFAULT_INITIAL_SALARY
        defaultGradeShouldNotBeFound("initialSalary.notEquals=" + DEFAULT_INITIAL_SALARY);

        // Get all the gradeList where initialSalary not equals to UPDATED_INITIAL_SALARY
        defaultGradeShouldBeFound("initialSalary.notEquals=" + UPDATED_INITIAL_SALARY);
    }

    @Test
    @Transactional
    public void getAllGradesByInitialSalaryIsInShouldWork() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where initialSalary in DEFAULT_INITIAL_SALARY or UPDATED_INITIAL_SALARY
        defaultGradeShouldBeFound("initialSalary.in=" + DEFAULT_INITIAL_SALARY + "," + UPDATED_INITIAL_SALARY);

        // Get all the gradeList where initialSalary equals to UPDATED_INITIAL_SALARY
        defaultGradeShouldNotBeFound("initialSalary.in=" + UPDATED_INITIAL_SALARY);
    }

    @Test
    @Transactional
    public void getAllGradesByInitialSalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where initialSalary is not null
        defaultGradeShouldBeFound("initialSalary.specified=true");

        // Get all the gradeList where initialSalary is null
        defaultGradeShouldNotBeFound("initialSalary.specified=false");
    }

    @Test
    @Transactional
    public void getAllGradesByInitialSalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where initialSalary is greater than or equal to DEFAULT_INITIAL_SALARY
        defaultGradeShouldBeFound("initialSalary.greaterThanOrEqual=" + DEFAULT_INITIAL_SALARY);

        // Get all the gradeList where initialSalary is greater than or equal to UPDATED_INITIAL_SALARY
        defaultGradeShouldNotBeFound("initialSalary.greaterThanOrEqual=" + UPDATED_INITIAL_SALARY);
    }

    @Test
    @Transactional
    public void getAllGradesByInitialSalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where initialSalary is less than or equal to DEFAULT_INITIAL_SALARY
        defaultGradeShouldBeFound("initialSalary.lessThanOrEqual=" + DEFAULT_INITIAL_SALARY);

        // Get all the gradeList where initialSalary is less than or equal to SMALLER_INITIAL_SALARY
        defaultGradeShouldNotBeFound("initialSalary.lessThanOrEqual=" + SMALLER_INITIAL_SALARY);
    }

    @Test
    @Transactional
    public void getAllGradesByInitialSalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where initialSalary is less than DEFAULT_INITIAL_SALARY
        defaultGradeShouldNotBeFound("initialSalary.lessThan=" + DEFAULT_INITIAL_SALARY);

        // Get all the gradeList where initialSalary is less than UPDATED_INITIAL_SALARY
        defaultGradeShouldBeFound("initialSalary.lessThan=" + UPDATED_INITIAL_SALARY);
    }

    @Test
    @Transactional
    public void getAllGradesByInitialSalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList where initialSalary is greater than DEFAULT_INITIAL_SALARY
        defaultGradeShouldNotBeFound("initialSalary.greaterThan=" + DEFAULT_INITIAL_SALARY);

        // Get all the gradeList where initialSalary is greater than SMALLER_INITIAL_SALARY
        defaultGradeShouldBeFound("initialSalary.greaterThan=" + SMALLER_INITIAL_SALARY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGradeShouldBeFound(String filter) throws Exception {
        restGradeMockMvc.perform(get("/api/grades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grade.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].initialSalary").value(hasItem(DEFAULT_INITIAL_SALARY.intValue())));

        // Check, that the count call also returns 1
        restGradeMockMvc.perform(get("/api/grades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGradeShouldNotBeFound(String filter) throws Exception {
        restGradeMockMvc.perform(get("/api/grades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGradeMockMvc.perform(get("/api/grades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGrade() throws Exception {
        // Get the grade
        restGradeMockMvc.perform(get("/api/grades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrade() throws Exception {
        // Initialize the database
        gradeService.save(grade);

        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();

        // Update the grade
        Grade updatedGrade = gradeRepository.findById(grade.getId()).get();
        // Disconnect from session so that the updates on updatedGrade are not directly saved in db
        em.detach(updatedGrade);
        updatedGrade
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .initialSalary(UPDATED_INITIAL_SALARY);

        restGradeMockMvc.perform(put("/api/grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrade)))
            .andExpect(status().isOk());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
        Grade testGrade = gradeList.get(gradeList.size() - 1);
        assertThat(testGrade.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testGrade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGrade.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGrade.getInitialSalary()).isEqualTo(UPDATED_INITIAL_SALARY);
    }

    @Test
    @Transactional
    public void updateNonExistingGrade() throws Exception {
        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradeMockMvc.perform(put("/api/grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grade)))
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrade() throws Exception {
        // Initialize the database
        gradeService.save(grade);

        int databaseSizeBeforeDelete = gradeRepository.findAll().size();

        // Delete the grade
        restGradeMockMvc.perform(delete("/api/grades/{id}", grade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
