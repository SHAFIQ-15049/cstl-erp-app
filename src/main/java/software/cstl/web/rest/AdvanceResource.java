package software.cstl.web.rest;

import software.cstl.domain.Advance;
import software.cstl.service.AdvanceService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.AdvanceCriteria;
import software.cstl.service.AdvanceQueryService;

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
 * REST controller for managing {@link software.cstl.domain.Advance}.
 */
@RestController
@RequestMapping("/api")
public class AdvanceResource {

    private final Logger log = LoggerFactory.getLogger(AdvanceResource.class);

    private static final String ENTITY_NAME = "advance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvanceService advanceService;

    private final AdvanceQueryService advanceQueryService;

    public AdvanceResource(AdvanceService advanceService, AdvanceQueryService advanceQueryService) {
        this.advanceService = advanceService;
        this.advanceQueryService = advanceQueryService;
    }

    /**
     * {@code POST  /advances} : Create a new advance.
     *
     * @param advance the advance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advance, or with status {@code 400 (Bad Request)} if the advance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advances")
    public ResponseEntity<Advance> createAdvance(@Valid @RequestBody Advance advance) throws URISyntaxException {
        log.debug("REST request to save Advance : {}", advance);
        if (advance.getId() != null) {
            throw new BadRequestAlertException("A new advance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Advance result = advanceService.save(advance);
        return ResponseEntity.created(new URI("/api/advances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advances} : Updates an existing advance.
     *
     * @param advance the advance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advance,
     * or with status {@code 400 (Bad Request)} if the advance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advances")
    public ResponseEntity<Advance> updateAdvance(@Valid @RequestBody Advance advance) throws URISyntaxException {
        log.debug("REST request to update Advance : {}", advance);
        if (advance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Advance result = advanceService.save(advance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, advance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advances} : get all the advances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advances in body.
     */
    @GetMapping("/advances")
    public ResponseEntity<List<Advance>> getAllAdvances(AdvanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Advances by criteria: {}", criteria);
        Page<Advance> page = advanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /advances/count} : count all the advances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/advances/count")
    public ResponseEntity<Long> countAdvances(AdvanceCriteria criteria) {
        log.debug("REST request to count Advances by criteria: {}", criteria);
        return ResponseEntity.ok().body(advanceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /advances/:id} : get the "id" advance.
     *
     * @param id the id of the advance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advances/{id}")
    public ResponseEntity<Advance> getAdvance(@PathVariable Long id) {
        log.debug("REST request to get Advance : {}", id);
        Optional<Advance> advance = advanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advance);
    }

    /**
     * {@code DELETE  /advances/:id} : delete the "id" advance.
     *
     * @param id the id of the advance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advances/{id}")
    public ResponseEntity<Void> deleteAdvance(@PathVariable Long id) {
        log.debug("REST request to delete Advance : {}", id);
        advanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
