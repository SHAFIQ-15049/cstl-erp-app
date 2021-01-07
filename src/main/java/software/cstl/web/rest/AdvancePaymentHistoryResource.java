package software.cstl.web.rest;

import software.cstl.domain.AdvancePaymentHistory;
import software.cstl.service.AdvancePaymentHistoryService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.AdvancePaymentHistoryCriteria;
import software.cstl.service.AdvancePaymentHistoryQueryService;

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
 * REST controller for managing {@link software.cstl.domain.AdvancePaymentHistory}.
 */
@RestController
@RequestMapping("/api")
public class AdvancePaymentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(AdvancePaymentHistoryResource.class);

    private static final String ENTITY_NAME = "advancePaymentHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvancePaymentHistoryService advancePaymentHistoryService;

    private final AdvancePaymentHistoryQueryService advancePaymentHistoryQueryService;

    public AdvancePaymentHistoryResource(AdvancePaymentHistoryService advancePaymentHistoryService, AdvancePaymentHistoryQueryService advancePaymentHistoryQueryService) {
        this.advancePaymentHistoryService = advancePaymentHistoryService;
        this.advancePaymentHistoryQueryService = advancePaymentHistoryQueryService;
    }

    /**
     * {@code POST  /advance-payment-histories} : Create a new advancePaymentHistory.
     *
     * @param advancePaymentHistory the advancePaymentHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advancePaymentHistory, or with status {@code 400 (Bad Request)} if the advancePaymentHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advance-payment-histories")
    public ResponseEntity<AdvancePaymentHistory> createAdvancePaymentHistory(@RequestBody AdvancePaymentHistory advancePaymentHistory) throws URISyntaxException {
        log.debug("REST request to save AdvancePaymentHistory : {}", advancePaymentHistory);
        if (advancePaymentHistory.getId() != null) {
            throw new BadRequestAlertException("A new advancePaymentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvancePaymentHistory result = advancePaymentHistoryService.save(advancePaymentHistory);
        return ResponseEntity.created(new URI("/api/advance-payment-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advance-payment-histories} : Updates an existing advancePaymentHistory.
     *
     * @param advancePaymentHistory the advancePaymentHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advancePaymentHistory,
     * or with status {@code 400 (Bad Request)} if the advancePaymentHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advancePaymentHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advance-payment-histories")
    public ResponseEntity<AdvancePaymentHistory> updateAdvancePaymentHistory(@RequestBody AdvancePaymentHistory advancePaymentHistory) throws URISyntaxException {
        log.debug("REST request to update AdvancePaymentHistory : {}", advancePaymentHistory);
        if (advancePaymentHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvancePaymentHistory result = advancePaymentHistoryService.save(advancePaymentHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, advancePaymentHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advance-payment-histories} : get all the advancePaymentHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advancePaymentHistories in body.
     */
    @GetMapping("/advance-payment-histories")
    public ResponseEntity<List<AdvancePaymentHistory>> getAllAdvancePaymentHistories(AdvancePaymentHistoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdvancePaymentHistories by criteria: {}", criteria);
        Page<AdvancePaymentHistory> page = advancePaymentHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /advance-payment-histories/count} : count all the advancePaymentHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/advance-payment-histories/count")
    public ResponseEntity<Long> countAdvancePaymentHistories(AdvancePaymentHistoryCriteria criteria) {
        log.debug("REST request to count AdvancePaymentHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(advancePaymentHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /advance-payment-histories/:id} : get the "id" advancePaymentHistory.
     *
     * @param id the id of the advancePaymentHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advancePaymentHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advance-payment-histories/{id}")
    public ResponseEntity<AdvancePaymentHistory> getAdvancePaymentHistory(@PathVariable Long id) {
        log.debug("REST request to get AdvancePaymentHistory : {}", id);
        Optional<AdvancePaymentHistory> advancePaymentHistory = advancePaymentHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advancePaymentHistory);
    }

    /**
     * {@code DELETE  /advance-payment-histories/:id} : delete the "id" advancePaymentHistory.
     *
     * @param id the id of the advancePaymentHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advance-payment-histories/{id}")
    public ResponseEntity<Void> deleteAdvancePaymentHistory(@PathVariable Long id) {
        log.debug("REST request to delete AdvancePaymentHistory : {}", id);
        advancePaymentHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
