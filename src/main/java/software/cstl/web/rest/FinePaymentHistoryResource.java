package software.cstl.web.rest;

import software.cstl.domain.FinePaymentHistory;
import software.cstl.service.FinePaymentHistoryService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.FinePaymentHistoryCriteria;
import software.cstl.service.FinePaymentHistoryQueryService;

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
 * REST controller for managing {@link software.cstl.domain.FinePaymentHistory}.
 */
@RestController
@RequestMapping("/api")
public class FinePaymentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(FinePaymentHistoryResource.class);

    private static final String ENTITY_NAME = "finePaymentHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinePaymentHistoryService finePaymentHistoryService;

    private final FinePaymentHistoryQueryService finePaymentHistoryQueryService;

    public FinePaymentHistoryResource(FinePaymentHistoryService finePaymentHistoryService, FinePaymentHistoryQueryService finePaymentHistoryQueryService) {
        this.finePaymentHistoryService = finePaymentHistoryService;
        this.finePaymentHistoryQueryService = finePaymentHistoryQueryService;
    }

    /**
     * {@code POST  /fine-payment-histories} : Create a new finePaymentHistory.
     *
     * @param finePaymentHistory the finePaymentHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new finePaymentHistory, or with status {@code 400 (Bad Request)} if the finePaymentHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fine-payment-histories")
    public ResponseEntity<FinePaymentHistory> createFinePaymentHistory(@RequestBody FinePaymentHistory finePaymentHistory) throws URISyntaxException {
        log.debug("REST request to save FinePaymentHistory : {}", finePaymentHistory);
        if (finePaymentHistory.getId() != null) {
            throw new BadRequestAlertException("A new finePaymentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinePaymentHistory result = finePaymentHistoryService.save(finePaymentHistory);
        return ResponseEntity.created(new URI("/api/fine-payment-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fine-payment-histories} : Updates an existing finePaymentHistory.
     *
     * @param finePaymentHistory the finePaymentHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated finePaymentHistory,
     * or with status {@code 400 (Bad Request)} if the finePaymentHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the finePaymentHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fine-payment-histories")
    public ResponseEntity<FinePaymentHistory> updateFinePaymentHistory(@RequestBody FinePaymentHistory finePaymentHistory) throws URISyntaxException {
        log.debug("REST request to update FinePaymentHistory : {}", finePaymentHistory);
        if (finePaymentHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FinePaymentHistory result = finePaymentHistoryService.save(finePaymentHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, finePaymentHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fine-payment-histories} : get all the finePaymentHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of finePaymentHistories in body.
     */
    @GetMapping("/fine-payment-histories")
    public ResponseEntity<List<FinePaymentHistory>> getAllFinePaymentHistories(FinePaymentHistoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FinePaymentHistories by criteria: {}", criteria);
        Page<FinePaymentHistory> page = finePaymentHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fine-payment-histories/count} : count all the finePaymentHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fine-payment-histories/count")
    public ResponseEntity<Long> countFinePaymentHistories(FinePaymentHistoryCriteria criteria) {
        log.debug("REST request to count FinePaymentHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(finePaymentHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fine-payment-histories/:id} : get the "id" finePaymentHistory.
     *
     * @param id the id of the finePaymentHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the finePaymentHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fine-payment-histories/{id}")
    public ResponseEntity<FinePaymentHistory> getFinePaymentHistory(@PathVariable Long id) {
        log.debug("REST request to get FinePaymentHistory : {}", id);
        Optional<FinePaymentHistory> finePaymentHistory = finePaymentHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(finePaymentHistory);
    }

    /**
     * {@code DELETE  /fine-payment-histories/:id} : delete the "id" finePaymentHistory.
     *
     * @param id the id of the finePaymentHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fine-payment-histories/{id}")
    public ResponseEntity<Void> deleteFinePaymentHistory(@PathVariable Long id) {
        log.debug("REST request to delete FinePaymentHistory : {}", id);
        finePaymentHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
