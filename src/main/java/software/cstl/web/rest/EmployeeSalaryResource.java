package software.cstl.web.rest;

import software.cstl.domain.EmployeeSalary;
import software.cstl.service.EmployeeSalaryService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.EmployeeSalaryCriteria;
import software.cstl.service.EmployeeSalaryQueryService;

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
 * REST controller for managing {@link software.cstl.domain.EmployeeSalary}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeSalaryResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeSalaryResource.class);

    private static final String ENTITY_NAME = "employeeSalary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeSalaryService employeeSalaryService;

    private final EmployeeSalaryQueryService employeeSalaryQueryService;

    public EmployeeSalaryResource(EmployeeSalaryService employeeSalaryService, EmployeeSalaryQueryService employeeSalaryQueryService) {
        this.employeeSalaryService = employeeSalaryService;
        this.employeeSalaryQueryService = employeeSalaryQueryService;
    }

    /**
     * {@code POST  /employee-salaries} : Create a new employeeSalary.
     *
     * @param employeeSalary the employeeSalary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeSalary, or with status {@code 400 (Bad Request)} if the employeeSalary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-salaries")
    public ResponseEntity<EmployeeSalary> createEmployeeSalary(@Valid @RequestBody EmployeeSalary employeeSalary) throws URISyntaxException {
        log.debug("REST request to save EmployeeSalary : {}", employeeSalary);
        if (employeeSalary.getId() != null) {
            throw new BadRequestAlertException("A new employeeSalary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeSalary result = employeeSalaryService.save(employeeSalary);
        return ResponseEntity.created(new URI("/api/employee-salaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-salaries} : Updates an existing employeeSalary.
     *
     * @param employeeSalary the employeeSalary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeSalary,
     * or with status {@code 400 (Bad Request)} if the employeeSalary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeSalary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-salaries")
    public ResponseEntity<EmployeeSalary> updateEmployeeSalary(@Valid @RequestBody EmployeeSalary employeeSalary) throws URISyntaxException {
        log.debug("REST request to update EmployeeSalary : {}", employeeSalary);
        if (employeeSalary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeSalary result = employeeSalaryService.save(employeeSalary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeSalary.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-salaries} : get all the employeeSalaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeSalaries in body.
     */
    @GetMapping("/employee-salaries")
    public ResponseEntity<List<EmployeeSalary>> getAllEmployeeSalaries(EmployeeSalaryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmployeeSalaries by criteria: {}", criteria);
        Page<EmployeeSalary> page = employeeSalaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-salaries/count} : count all the employeeSalaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-salaries/count")
    public ResponseEntity<Long> countEmployeeSalaries(EmployeeSalaryCriteria criteria) {
        log.debug("REST request to count EmployeeSalaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeSalaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-salaries/:id} : get the "id" employeeSalary.
     *
     * @param id the id of the employeeSalary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeSalary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-salaries/{id}")
    public ResponseEntity<EmployeeSalary> getEmployeeSalary(@PathVariable Long id) {
        log.debug("REST request to get EmployeeSalary : {}", id);
        Optional<EmployeeSalary> employeeSalary = employeeSalaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeSalary);
    }

    /**
     * {@code DELETE  /employee-salaries/:id} : delete the "id" employeeSalary.
     *
     * @param id the id of the employeeSalary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-salaries/{id}")
    public ResponseEntity<Void> deleteEmployeeSalary(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeSalary : {}", id);
        employeeSalaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
