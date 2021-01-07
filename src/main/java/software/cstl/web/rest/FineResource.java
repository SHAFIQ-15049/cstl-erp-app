package software.cstl.web.rest;

import software.cstl.domain.Fine;
import software.cstl.service.FineService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.FineCriteria;
import software.cstl.service.FineQueryService;

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
 * REST controller for managing {@link software.cstl.domain.Fine}.
 */
@RestController
@RequestMapping("/api")
public class FineResource {

    private final Logger log = LoggerFactory.getLogger(FineResource.class);

    private static final String ENTITY_NAME = "fine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FineService fineService;

    private final FineQueryService fineQueryService;

    public FineResource(FineService fineService, FineQueryService fineQueryService) {
        this.fineService = fineService;
        this.fineQueryService = fineQueryService;
    }

    /**
     * {@code POST  /fines} : Create a new fine.
     *
     * @param fine the fine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fine, or with status {@code 400 (Bad Request)} if the fine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fines")
    public ResponseEntity<Fine> createFine(@Valid @RequestBody Fine fine) throws URISyntaxException {
        log.debug("REST request to save Fine : {}", fine);
        if (fine.getId() != null) {
            throw new BadRequestAlertException("A new fine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fine result = fineService.save(fine);
        return ResponseEntity.created(new URI("/api/fines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fines} : Updates an existing fine.
     *
     * @param fine the fine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fine,
     * or with status {@code 400 (Bad Request)} if the fine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fines")
    public ResponseEntity<Fine> updateFine(@Valid @RequestBody Fine fine) throws URISyntaxException {
        log.debug("REST request to update Fine : {}", fine);
        if (fine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fine result = fineService.save(fine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fines} : get all the fines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fines in body.
     */
    @GetMapping("/fines")
    public ResponseEntity<List<Fine>> getAllFines(FineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Fines by criteria: {}", criteria);
        Page<Fine> page = fineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fines/count} : count all the fines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fines/count")
    public ResponseEntity<Long> countFines(FineCriteria criteria) {
        log.debug("REST request to count Fines by criteria: {}", criteria);
        return ResponseEntity.ok().body(fineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fines/:id} : get the "id" fine.
     *
     * @param id the id of the fine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fines/{id}")
    public ResponseEntity<Fine> getFine(@PathVariable Long id) {
        log.debug("REST request to get Fine : {}", id);
        Optional<Fine> fine = fineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fine);
    }

    /**
     * {@code DELETE  /fines/:id} : delete the "id" fine.
     *
     * @param id the id of the fine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fines/{id}")
    public ResponseEntity<Void> deleteFine(@PathVariable Long id) {
        log.debug("REST request to delete Fine : {}", id);
        fineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
