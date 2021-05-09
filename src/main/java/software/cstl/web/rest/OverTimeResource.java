package software.cstl.web.rest;

import software.cstl.domain.OverTime;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.service.OverTimeService;
import software.cstl.service.mediators.OverTimeGenerationService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.OverTimeCriteria;
import software.cstl.service.OverTimeQueryService;

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
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.OverTime}.
 */
@RestController
@RequestMapping("/api")
public class OverTimeResource {

    private final Logger log = LoggerFactory.getLogger(OverTimeResource.class);

    private static final String ENTITY_NAME = "overTime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverTimeService overTimeService;

    private final OverTimeQueryService overTimeQueryService;

    private final OverTimeGenerationService overTimeGenerationService;

    public OverTimeResource(OverTimeService overTimeService, OverTimeQueryService overTimeQueryService, OverTimeGenerationService overTimeGenerationService) {
        this.overTimeService = overTimeService;
        this.overTimeQueryService = overTimeQueryService;
        this.overTimeGenerationService = overTimeGenerationService;
    }

    /**
     * {@code POST  /over-times} : Create a new overTime.
     *
     * @param overTime the overTime to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overTime, or with status {@code 400 (Bad Request)} if the overTime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/over-times")
    public ResponseEntity<OverTime> createOverTime(@RequestBody OverTime overTime) throws URISyntaxException {
        log.debug("REST request to save OverTime : {}", overTime);
        if (overTime.getId() != null) {
            throw new BadRequestAlertException("A new overTime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OverTime result = overTimeService.save(overTime);
        return ResponseEntity.created(new URI("/api/over-times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /over-times} : Updates an existing overTime.
     *
     * @param overTime the overTime to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overTime,
     * or with status {@code 400 (Bad Request)} if the overTime is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overTime couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/over-times")
    public ResponseEntity<OverTime> updateOverTime(@RequestBody OverTime overTime) throws URISyntaxException {
        log.debug("REST request to update OverTime : {}", overTime);
        if (overTime.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OverTime result = overTimeService.save(overTime);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overTime.getId().toString()))
            .body(result);
    }

    @GetMapping("/over-times/generate-over-time/{year}/{month}")
    public ResponseEntity<List<OverTime>> generateOverTimes(@PathVariable Integer year, @PathVariable MonthType month){
        log.debug("REST request to generate over time for year: {}, month: {}", year, month);
        List<OverTime> overTimes = overTimeGenerationService.generateOverTime(year, month);
        return  ResponseEntity.ok()
            .body(overTimes);
    }

    @GetMapping("/over-times/regenerate-over-time/{year}/{month}")
    public ResponseEntity<List<OverTime>> regenerateOverTimes(@PathVariable Integer year, @PathVariable MonthType month){
        log.debug("REST request to generate over time for year: {}, month: {}", year, month);
        List<OverTime> overTimes = overTimeGenerationService.regenerateOverTime(year, month);
        return  ResponseEntity.ok()
            .body(overTimes);
    }

    @GetMapping("/over-times/regenerate-employee-over-time/{overTimeId}")
    public ResponseEntity<OverTime> regenerateEmployeeOverTime(@PathVariable Long overTimeId){
        OverTime overTime = overTimeGenerationService.regenerateEmployeeOverTime(overTimeId);
        return ResponseEntity.ok()
            .body(overTime);
    }

    /**
     * {@code GET  /over-times} : get all the overTimes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overTimes in body.
     */
    @GetMapping("/over-times")
    public ResponseEntity<List<OverTime>> getAllOverTimes(OverTimeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OverTimes by criteria: {}", criteria);
        Page<OverTime> page = overTimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /over-times/count} : count all the overTimes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/over-times/count")
    public ResponseEntity<Long> countOverTimes(OverTimeCriteria criteria) {
        log.debug("REST request to count OverTimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(overTimeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /over-times/:id} : get the "id" overTime.
     *
     * @param id the id of the overTime to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overTime, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/over-times/{id}")
    public ResponseEntity<OverTime> getOverTime(@PathVariable Long id) {
        log.debug("REST request to get OverTime : {}", id);
        Optional<OverTime> overTime = overTimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(overTime);
    }

    /**
     * {@code DELETE  /over-times/:id} : delete the "id" overTime.
     *
     * @param id the id of the overTime to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/over-times/{id}")
    public ResponseEntity<Void> deleteOverTime(@PathVariable Long id) {
        log.debug("REST request to delete OverTime : {}", id);
        overTimeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
