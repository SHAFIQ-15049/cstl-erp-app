package software.cstl.web.rest;

import software.cstl.domain.LeaveType;
import software.cstl.service.LeaveTypeService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.LeaveTypeCriteria;
import software.cstl.service.LeaveTypeQueryService;

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
 * REST controller for managing {@link software.cstl.domain.LeaveType}.
 */
@RestController
@RequestMapping("/api")
public class LeaveTypeResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeResource.class);

    private static final String ENTITY_NAME = "leaveType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveTypeService leaveTypeService;

    private final LeaveTypeQueryService leaveTypeQueryService;

    public LeaveTypeResource(LeaveTypeService leaveTypeService, LeaveTypeQueryService leaveTypeQueryService) {
        this.leaveTypeService = leaveTypeService;
        this.leaveTypeQueryService = leaveTypeQueryService;
    }

    /**
     * {@code POST  /leave-types} : Create a new leaveType.
     *
     * @param leaveType the leaveType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveType, or with status {@code 400 (Bad Request)} if the leaveType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-types")
    public ResponseEntity<LeaveType> createLeaveType(@Valid @RequestBody LeaveType leaveType) throws URISyntaxException {
        log.debug("REST request to save LeaveType : {}", leaveType);
        if (leaveType.getId() != null) {
            throw new BadRequestAlertException("A new leaveType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveType result = leaveTypeService.save(leaveType);
        return ResponseEntity.created(new URI("/api/leave-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-types} : Updates an existing leaveType.
     *
     * @param leaveType the leaveType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveType,
     * or with status {@code 400 (Bad Request)} if the leaveType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-types")
    public ResponseEntity<LeaveType> updateLeaveType(@Valid @RequestBody LeaveType leaveType) throws URISyntaxException {
        log.debug("REST request to update LeaveType : {}", leaveType);
        if (leaveType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveType result = leaveTypeService.save(leaveType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /leave-types} : get all the leaveTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveTypes in body.
     */
    @GetMapping("/leave-types")
    public ResponseEntity<List<LeaveType>> getAllLeaveTypes(LeaveTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeaveTypes by criteria: {}", criteria);
        Page<LeaveType> page = leaveTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-types/count} : count all the leaveTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-types/count")
    public ResponseEntity<Long> countLeaveTypes(LeaveTypeCriteria criteria) {
        log.debug("REST request to count LeaveTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-types/:id} : get the "id" leaveType.
     *
     * @param id the id of the leaveType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-types/{id}")
    public ResponseEntity<LeaveType> getLeaveType(@PathVariable Long id) {
        log.debug("REST request to get LeaveType : {}", id);
        Optional<LeaveType> leaveType = leaveTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveType);
    }

    /**
     * {@code DELETE  /leave-types/:id} : delete the "id" leaveType.
     *
     * @param id the id of the leaveType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-types/{id}")
    public ResponseEntity<Void> deleteLeaveType(@PathVariable Long id) {
        log.debug("REST request to delete LeaveType : {}", id);
        leaveTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
