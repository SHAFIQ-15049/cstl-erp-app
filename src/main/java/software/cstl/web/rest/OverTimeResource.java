package software.cstl.web.rest;

import software.cstl.domain.OverTime;
import software.cstl.repository.OverTimeRepository;
import software.cstl.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.OverTime}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OverTimeResource {

    private final Logger log = LoggerFactory.getLogger(OverTimeResource.class);

    private static final String ENTITY_NAME = "overTime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverTimeRepository overTimeRepository;

    public OverTimeResource(OverTimeRepository overTimeRepository) {
        this.overTimeRepository = overTimeRepository;
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
        OverTime result = overTimeRepository.save(overTime);
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
        OverTime result = overTimeRepository.save(overTime);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overTime.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /over-times} : get all the overTimes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overTimes in body.
     */
    @GetMapping("/over-times")
    public List<OverTime> getAllOverTimes() {
        log.debug("REST request to get all OverTimes");
        return overTimeRepository.findAll();
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
        Optional<OverTime> overTime = overTimeRepository.findById(id);
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
        overTimeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
