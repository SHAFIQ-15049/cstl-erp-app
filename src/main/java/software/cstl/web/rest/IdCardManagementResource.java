package software.cstl.web.rest;

import software.cstl.domain.IdCardManagement;
import software.cstl.service.IdCardManagementService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.IdCardManagementCriteria;
import software.cstl.service.IdCardManagementQueryService;

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
 * REST controller for managing {@link software.cstl.domain.IdCardManagement}.
 */
@RestController
@RequestMapping("/api")
public class IdCardManagementResource {

    private final Logger log = LoggerFactory.getLogger(IdCardManagementResource.class);

    private static final String ENTITY_NAME = "idCardManagement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdCardManagementService idCardManagementService;

    private final IdCardManagementQueryService idCardManagementQueryService;

    public IdCardManagementResource(IdCardManagementService idCardManagementService, IdCardManagementQueryService idCardManagementQueryService) {
        this.idCardManagementService = idCardManagementService;
        this.idCardManagementQueryService = idCardManagementQueryService;
    }

    /**
     * {@code POST  /id-card-managements} : Create a new idCardManagement.
     *
     * @param idCardManagement the idCardManagement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new idCardManagement, or with status {@code 400 (Bad Request)} if the idCardManagement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/id-card-managements")
    public ResponseEntity<IdCardManagement> createIdCardManagement(@RequestBody IdCardManagement idCardManagement) throws URISyntaxException {
        log.debug("REST request to save IdCardManagement : {}", idCardManagement);
        if (idCardManagement.getId() != null) {
            throw new BadRequestAlertException("A new idCardManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdCardManagement result = idCardManagementService.save(idCardManagement);
        return ResponseEntity.created(new URI("/api/id-card-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /id-card-managements} : Updates an existing idCardManagement.
     *
     * @param idCardManagement the idCardManagement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idCardManagement,
     * or with status {@code 400 (Bad Request)} if the idCardManagement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the idCardManagement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/id-card-managements")
    public ResponseEntity<IdCardManagement> updateIdCardManagement(@RequestBody IdCardManagement idCardManagement) throws URISyntaxException {
        log.debug("REST request to update IdCardManagement : {}", idCardManagement);
        if (idCardManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IdCardManagement result = idCardManagementService.save(idCardManagement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, idCardManagement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /id-card-managements} : get all the idCardManagements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of idCardManagements in body.
     */
    @GetMapping("/id-card-managements")
    public ResponseEntity<List<IdCardManagement>> getAllIdCardManagements(IdCardManagementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get IdCardManagements by criteria: {}", criteria);
        Page<IdCardManagement> page = idCardManagementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /id-card-managements/count} : count all the idCardManagements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/id-card-managements/count")
    public ResponseEntity<Long> countIdCardManagements(IdCardManagementCriteria criteria) {
        log.debug("REST request to count IdCardManagements by criteria: {}", criteria);
        return ResponseEntity.ok().body(idCardManagementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /id-card-managements/:id} : get the "id" idCardManagement.
     *
     * @param id the id of the idCardManagement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the idCardManagement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/id-card-managements/{id}")
    public ResponseEntity<IdCardManagement> getIdCardManagement(@PathVariable Long id) {
        log.debug("REST request to get IdCardManagement : {}", id);
        Optional<IdCardManagement> idCardManagement = idCardManagementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(idCardManagement);
    }

    /**
     * {@code DELETE  /id-card-managements/:id} : delete the "id" idCardManagement.
     *
     * @param id the id of the idCardManagement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/id-card-managements/{id}")
    public ResponseEntity<Void> deleteIdCardManagement(@PathVariable Long id) {
        log.debug("REST request to delete IdCardManagement : {}", id);
        idCardManagementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
