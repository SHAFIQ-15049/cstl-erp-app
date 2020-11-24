package software.cstl.web.rest;

import software.cstl.domain.Designation;
import software.cstl.service.DesignationService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.DesignationCriteria;
import software.cstl.service.DesignationQueryService;

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
 * REST controller for managing {@link software.cstl.domain.Designation}.
 */
@RestController
@RequestMapping("/api")
public class DesignationResource {

    private final Logger log = LoggerFactory.getLogger(DesignationResource.class);

    private static final String ENTITY_NAME = "designation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DesignationService designationService;

    private final DesignationQueryService designationQueryService;

    public DesignationResource(DesignationService designationService, DesignationQueryService designationQueryService) {
        this.designationService = designationService;
        this.designationQueryService = designationQueryService;
    }

    /**
     * {@code POST  /designations} : Create a new designation.
     *
     * @param designation the designation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new designation, or with status {@code 400 (Bad Request)} if the designation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/designations")
    public ResponseEntity<Designation> createDesignation(@Valid @RequestBody Designation designation) throws URISyntaxException {
        log.debug("REST request to save Designation : {}", designation);
        if (designation.getId() != null) {
            throw new BadRequestAlertException("A new designation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Designation result = designationService.save(designation);
        return ResponseEntity.created(new URI("/api/designations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /designations} : Updates an existing designation.
     *
     * @param designation the designation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated designation,
     * or with status {@code 400 (Bad Request)} if the designation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the designation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/designations")
    public ResponseEntity<Designation> updateDesignation(@Valid @RequestBody Designation designation) throws URISyntaxException {
        log.debug("REST request to update Designation : {}", designation);
        if (designation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Designation result = designationService.save(designation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, designation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /designations} : get all the designations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of designations in body.
     */
    @GetMapping("/designations")
    public ResponseEntity<List<Designation>> getAllDesignations(DesignationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Designations by criteria: {}", criteria);
        Page<Designation> page = designationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /designations/count} : count all the designations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/designations/count")
    public ResponseEntity<Long> countDesignations(DesignationCriteria criteria) {
        log.debug("REST request to count Designations by criteria: {}", criteria);
        return ResponseEntity.ok().body(designationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /designations/:id} : get the "id" designation.
     *
     * @param id the id of the designation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the designation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/designations/{id}")
    public ResponseEntity<Designation> getDesignation(@PathVariable Long id) {
        log.debug("REST request to get Designation : {}", id);
        Optional<Designation> designation = designationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(designation);
    }

    /**
     * {@code DELETE  /designations/:id} : delete the "id" designation.
     *
     * @param id the id of the designation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/designations/{id}")
    public ResponseEntity<Void> deleteDesignation(@PathVariable Long id) {
        log.debug("REST request to delete Designation : {}", id);
        designationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
