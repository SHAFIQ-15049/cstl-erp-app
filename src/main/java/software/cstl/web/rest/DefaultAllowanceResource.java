package software.cstl.web.rest;

import software.cstl.domain.DefaultAllowance;
import software.cstl.service.DefaultAllowanceService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.DefaultAllowanceCriteria;
import software.cstl.service.DefaultAllowanceQueryService;

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
 * REST controller for managing {@link software.cstl.domain.DefaultAllowance}.
 */
@RestController
@RequestMapping("/api")
public class DefaultAllowanceResource {

    private final Logger log = LoggerFactory.getLogger(DefaultAllowanceResource.class);

    private static final String ENTITY_NAME = "defaultAllowance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DefaultAllowanceService defaultAllowanceService;

    private final DefaultAllowanceQueryService defaultAllowanceQueryService;

    public DefaultAllowanceResource(DefaultAllowanceService defaultAllowanceService, DefaultAllowanceQueryService defaultAllowanceQueryService) {
        this.defaultAllowanceService = defaultAllowanceService;
        this.defaultAllowanceQueryService = defaultAllowanceQueryService;
    }

    /**
     * {@code POST  /default-allowances} : Create a new defaultAllowance.
     *
     * @param defaultAllowance the defaultAllowance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new defaultAllowance, or with status {@code 400 (Bad Request)} if the defaultAllowance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/default-allowances")
    public ResponseEntity<DefaultAllowance> createDefaultAllowance(@Valid @RequestBody DefaultAllowance defaultAllowance) throws URISyntaxException {
        log.debug("REST request to save DefaultAllowance : {}", defaultAllowance);
        if (defaultAllowance.getId() != null) {
            throw new BadRequestAlertException("A new defaultAllowance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DefaultAllowance result = defaultAllowanceService.save(defaultAllowance);
        return ResponseEntity.created(new URI("/api/default-allowances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /default-allowances} : Updates an existing defaultAllowance.
     *
     * @param defaultAllowance the defaultAllowance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated defaultAllowance,
     * or with status {@code 400 (Bad Request)} if the defaultAllowance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the defaultAllowance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/default-allowances")
    public ResponseEntity<DefaultAllowance> updateDefaultAllowance(@Valid @RequestBody DefaultAllowance defaultAllowance) throws URISyntaxException {
        log.debug("REST request to update DefaultAllowance : {}", defaultAllowance);
        if (defaultAllowance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DefaultAllowance result = defaultAllowanceService.save(defaultAllowance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, defaultAllowance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /default-allowances} : get all the defaultAllowances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of defaultAllowances in body.
     */
    @GetMapping("/default-allowances")
    public ResponseEntity<List<DefaultAllowance>> getAllDefaultAllowances(DefaultAllowanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DefaultAllowances by criteria: {}", criteria);
        Page<DefaultAllowance> page = defaultAllowanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /default-allowances/count} : count all the defaultAllowances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/default-allowances/count")
    public ResponseEntity<Long> countDefaultAllowances(DefaultAllowanceCriteria criteria) {
        log.debug("REST request to count DefaultAllowances by criteria: {}", criteria);
        return ResponseEntity.ok().body(defaultAllowanceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /default-allowances/:id} : get the "id" defaultAllowance.
     *
     * @param id the id of the defaultAllowance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the defaultAllowance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/default-allowances/{id}")
    public ResponseEntity<DefaultAllowance> getDefaultAllowance(@PathVariable Long id) {
        log.debug("REST request to get DefaultAllowance : {}", id);
        Optional<DefaultAllowance> defaultAllowance = defaultAllowanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(defaultAllowance);
    }

    /**
     * {@code DELETE  /default-allowances/:id} : delete the "id" defaultAllowance.
     *
     * @param id the id of the defaultAllowance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/default-allowances/{id}")
    public ResponseEntity<Void> deleteDefaultAllowance(@PathVariable Long id) {
        log.debug("REST request to delete DefaultAllowance : {}", id);
        defaultAllowanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
