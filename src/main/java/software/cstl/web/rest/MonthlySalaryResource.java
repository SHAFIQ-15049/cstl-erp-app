package software.cstl.web.rest;

import software.cstl.domain.MonthlySalary;
import software.cstl.service.MonthlySalaryService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.MonthlySalaryCriteria;
import software.cstl.service.MonthlySalaryQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.MonthlySalary}.
 */
@RestController
@RequestMapping("/api")
public class MonthlySalaryResource {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryResource.class);

    private static final String ENTITY_NAME = "monthlySalary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonthlySalaryService monthlySalaryService;

    private final MonthlySalaryQueryService monthlySalaryQueryService;

    public MonthlySalaryResource(MonthlySalaryService monthlySalaryService, MonthlySalaryQueryService monthlySalaryQueryService) {
        this.monthlySalaryService = monthlySalaryService;
        this.monthlySalaryQueryService = monthlySalaryQueryService;
    }

    /**
     * {@code POST  /monthly-salaries} : Create a new monthlySalary.
     *
     * @param monthlySalary the monthlySalary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monthlySalary, or with status {@code 400 (Bad Request)} if the monthlySalary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monthly-salaries")
    public ResponseEntity<MonthlySalary> createMonthlySalary(@RequestBody MonthlySalary monthlySalary) throws URISyntaxException {
        log.debug("REST request to save MonthlySalary : {}", monthlySalary);
        if (monthlySalary.getId() != null) {
            throw new BadRequestAlertException("A new monthlySalary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonthlySalary result = monthlySalaryService.save(monthlySalary);
        return ResponseEntity.created(new URI("/api/monthly-salaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /monthly-salaries} : Updates an existing monthlySalary.
     *
     * @param monthlySalary the monthlySalary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlySalary,
     * or with status {@code 400 (Bad Request)} if the monthlySalary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monthlySalary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monthly-salaries")
    public ResponseEntity<MonthlySalary> updateMonthlySalary(@RequestBody MonthlySalary monthlySalary) throws URISyntaxException {
        log.debug("REST request to update MonthlySalary : {}", monthlySalary);
        if (monthlySalary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MonthlySalary result = monthlySalaryService.save(monthlySalary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, monthlySalary.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /monthly-salaries} : get all the monthlySalaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monthlySalaries in body.
     */
    @GetMapping("/monthly-salaries")
    public ResponseEntity<List<MonthlySalary>> getAllMonthlySalaries(MonthlySalaryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MonthlySalaries by criteria: {}", criteria);
        Page<MonthlySalary> page = monthlySalaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /monthly-salaries/count} : count all the monthlySalaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/monthly-salaries/count")
    public ResponseEntity<Long> countMonthlySalaries(MonthlySalaryCriteria criteria) {
        log.debug("REST request to count MonthlySalaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(monthlySalaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /monthly-salaries/:id} : get the "id" monthlySalary.
     *
     * @param id the id of the monthlySalary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monthlySalary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monthly-salaries/{id}")
    public ResponseEntity<MonthlySalary> getMonthlySalary(@PathVariable Long id) {
        log.debug("REST request to get MonthlySalary : {}", id);
        Optional<MonthlySalary> monthlySalary = monthlySalaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monthlySalary);
    }

    /**
     * {@code DELETE  /monthly-salaries/:id} : delete the "id" monthlySalary.
     *
     * @param id the id of the monthlySalary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monthly-salaries/{id}")
    public ResponseEntity<Void> deleteMonthlySalary(@PathVariable Long id) {
        log.debug("REST request to delete MonthlySalary : {}", id);
        monthlySalaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
