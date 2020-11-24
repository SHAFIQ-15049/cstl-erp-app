package software.cstl.web.rest;

import software.cstl.domain.Grade;
import software.cstl.service.GradeService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.GradeCriteria;
import software.cstl.service.GradeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.Grade}.
 */
@RestController
@RequestMapping("/api")
public class GradeResource {

    private final Logger log = LoggerFactory.getLogger(GradeResource.class);

    private static final String ENTITY_NAME = "grade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GradeService gradeService;

    private final GradeQueryService gradeQueryService;

    public GradeResource(GradeService gradeService, GradeQueryService gradeQueryService) {
        this.gradeService = gradeService;
        this.gradeQueryService = gradeQueryService;
    }

    /**
     * {@code POST  /grades} : Create a new grade.
     *
     * @param grade the grade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grade, or with status {@code 400 (Bad Request)} if the grade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grades")
    public ResponseEntity<Grade> createGrade(@Valid @RequestBody Grade grade) throws URISyntaxException {
        log.debug("REST request to save Grade : {}", grade);
        if (grade.getId() != null) {
            throw new BadRequestAlertException("A new grade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Grade result = gradeService.save(grade);
        return ResponseEntity.created(new URI("/api/grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grades} : Updates an existing grade.
     *
     * @param grade the grade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grade,
     * or with status {@code 400 (Bad Request)} if the grade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grades")
    public ResponseEntity<Grade> updateGrade(@Valid @RequestBody Grade grade) throws URISyntaxException {
        log.debug("REST request to update Grade : {}", grade);
        if (grade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Grade result = gradeService.save(grade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grade.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grades} : get all the grades.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grades in body.
     */
    @GetMapping("/grades")
    public ResponseEntity<List<Grade>> getAllGrades(GradeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Grades by criteria: {}", criteria);
        Page<Grade> page = gradeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grades/count} : count all the grades.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/grades/count")
    public ResponseEntity<Long> countGrades(GradeCriteria criteria) {
        log.debug("REST request to count Grades by criteria: {}", criteria);
        return ResponseEntity.ok().body(gradeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /grades/:id} : get the "id" grade.
     *
     * @param id the id of the grade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grades/{id}")
    public ResponseEntity<Grade> getGrade(@PathVariable Long id) {
        log.debug("REST request to get Grade : {}", id);
        Optional<Grade> grade = gradeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grade);
    }

    /**
     * {@code DELETE  /grades/:id} : delete the "id" grade.
     *
     * @param id the id of the grade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grades/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        log.debug("REST request to delete Grade : {}", id);
        gradeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
