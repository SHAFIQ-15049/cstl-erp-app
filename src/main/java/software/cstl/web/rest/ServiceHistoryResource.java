package software.cstl.web.rest;

import software.cstl.domain.ServiceHistory;
import software.cstl.service.ServiceHistoryService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.ServiceHistoryCriteria;
import software.cstl.service.ServiceHistoryQueryService;

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
 * REST controller for managing {@link software.cstl.domain.ServiceHistory}.
 */
@RestController
@RequestMapping("/api")
public class ServiceHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ServiceHistoryResource.class);

    private static final String ENTITY_NAME = "serviceHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceHistoryService serviceHistoryService;

    private final ServiceHistoryQueryService serviceHistoryQueryService;

    public ServiceHistoryResource(ServiceHistoryService serviceHistoryService, ServiceHistoryQueryService serviceHistoryQueryService) {
        this.serviceHistoryService = serviceHistoryService;
        this.serviceHistoryQueryService = serviceHistoryQueryService;
    }

    /**
     * {@code POST  /service-histories} : Create a new serviceHistory.
     *
     * @param serviceHistory the serviceHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceHistory, or with status {@code 400 (Bad Request)} if the serviceHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-histories")
    public ResponseEntity<ServiceHistory> createServiceHistory(@RequestBody ServiceHistory serviceHistory) throws URISyntaxException {
        log.debug("REST request to save ServiceHistory : {}", serviceHistory);
        if (serviceHistory.getId() != null) {
            throw new BadRequestAlertException("A new serviceHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceHistory result = serviceHistoryService.save(serviceHistory);
        return ResponseEntity.created(new URI("/api/service-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-histories} : Updates an existing serviceHistory.
     *
     * @param serviceHistory the serviceHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceHistory,
     * or with status {@code 400 (Bad Request)} if the serviceHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-histories")
    public ResponseEntity<ServiceHistory> updateServiceHistory(@RequestBody ServiceHistory serviceHistory) throws URISyntaxException {
        log.debug("REST request to update ServiceHistory : {}", serviceHistory);
        if (serviceHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceHistory result = serviceHistoryService.save(serviceHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-histories} : get all the serviceHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceHistories in body.
     */
    @GetMapping("/service-histories")
    public ResponseEntity<List<ServiceHistory>> getAllServiceHistories(ServiceHistoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceHistories by criteria: {}", criteria);
        Page<ServiceHistory> page = serviceHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-histories/count} : count all the serviceHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-histories/count")
    public ResponseEntity<Long> countServiceHistories(ServiceHistoryCriteria criteria) {
        log.debug("REST request to count ServiceHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-histories/:id} : get the "id" serviceHistory.
     *
     * @param id the id of the serviceHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-histories/{id}")
    public ResponseEntity<ServiceHistory> getServiceHistory(@PathVariable Long id) {
        log.debug("REST request to get ServiceHistory : {}", id);
        Optional<ServiceHistory> serviceHistory = serviceHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceHistory);
    }

    /**
     * {@code DELETE  /service-histories/:id} : delete the "id" serviceHistory.
     *
     * @param id the id of the serviceHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-histories/{id}")
    public ResponseEntity<Void> deleteServiceHistory(@PathVariable Long id) {
        log.debug("REST request to delete ServiceHistory : {}", id);
        serviceHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
