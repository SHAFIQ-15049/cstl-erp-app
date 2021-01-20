package software.cstl.web.rest;

import software.cstl.domain.FestivalAllowancePayment;
import software.cstl.service.FestivalAllowancePaymentService;
import software.cstl.service.mediators.FestivalAllowancePaymentGenerationService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.FestivalAllowancePaymentCriteria;
import software.cstl.service.FestivalAllowancePaymentQueryService;

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
 * REST controller for managing {@link software.cstl.domain.FestivalAllowancePayment}.
 */
@RestController
@RequestMapping("/api")
public class FestivalAllowancePaymentResource {

    private final Logger log = LoggerFactory.getLogger(FestivalAllowancePaymentResource.class);

    private static final String ENTITY_NAME = "festivalAllowancePayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FestivalAllowancePaymentService festivalAllowancePaymentService;

    private final FestivalAllowancePaymentQueryService festivalAllowancePaymentQueryService;

    private final FestivalAllowancePaymentGenerationService festivalAllowancePaymentGenerationService;

    public FestivalAllowancePaymentResource(FestivalAllowancePaymentService festivalAllowancePaymentService, FestivalAllowancePaymentQueryService festivalAllowancePaymentQueryService, FestivalAllowancePaymentGenerationService festivalAllowancePaymentGenerationService) {
        this.festivalAllowancePaymentService = festivalAllowancePaymentService;
        this.festivalAllowancePaymentQueryService = festivalAllowancePaymentQueryService;
        this.festivalAllowancePaymentGenerationService = festivalAllowancePaymentGenerationService;
    }

    /**
     * {@code POST  /festival-allowance-payments} : Create a new festivalAllowancePayment.
     *
     * @param festivalAllowancePayment the festivalAllowancePayment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new festivalAllowancePayment, or with status {@code 400 (Bad Request)} if the festivalAllowancePayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/festival-allowance-payments")
    public ResponseEntity<FestivalAllowancePayment> createFestivalAllowancePayment(@RequestBody FestivalAllowancePayment festivalAllowancePayment) throws URISyntaxException {
        log.debug("REST request to save FestivalAllowancePayment : {}", festivalAllowancePayment);
        if (festivalAllowancePayment.getId() != null) {
            throw new BadRequestAlertException("A new festivalAllowancePayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FestivalAllowancePayment result = festivalAllowancePaymentGenerationService.generateFestivalAllowancePayment(festivalAllowancePayment);
        return ResponseEntity.created(new URI("/api/festival-allowance-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/festival-allowance-payments/regenerate")
    public ResponseEntity<FestivalAllowancePayment> regenerateFestivalAllowancePayment(@RequestBody FestivalAllowancePayment festivalAllowancePayment) throws URISyntaxException {
        log.debug("REST request to save FestivalAllowancePayment : {}", festivalAllowancePayment);
        if (festivalAllowancePayment.getId() == null) {
            throw new BadRequestAlertException("Regeneration should be done on existing records", ENTITY_NAME, "idexists");
        }
        FestivalAllowancePayment result = festivalAllowancePaymentGenerationService.reGenerateFestivalAllowancePayment(festivalAllowancePayment);
        return ResponseEntity.created(new URI("/api/festival-allowance-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /festival-allowance-payments} : Updates an existing festivalAllowancePayment.
     *
     * @param festivalAllowancePayment the festivalAllowancePayment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated festivalAllowancePayment,
     * or with status {@code 400 (Bad Request)} if the festivalAllowancePayment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the festivalAllowancePayment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/festival-allowance-payments")
    public ResponseEntity<FestivalAllowancePayment> updateFestivalAllowancePayment(@RequestBody FestivalAllowancePayment festivalAllowancePayment) throws URISyntaxException {
        log.debug("REST request to update FestivalAllowancePayment : {}", festivalAllowancePayment);
        if (festivalAllowancePayment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FestivalAllowancePayment result = festivalAllowancePaymentService.save(festivalAllowancePayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, festivalAllowancePayment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /festival-allowance-payments} : get all the festivalAllowancePayments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of festivalAllowancePayments in body.
     */
    @GetMapping("/festival-allowance-payments")
    public ResponseEntity<List<FestivalAllowancePayment>> getAllFestivalAllowancePayments(FestivalAllowancePaymentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FestivalAllowancePayments by criteria: {}", criteria);
        Page<FestivalAllowancePayment> page = festivalAllowancePaymentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /festival-allowance-payments/count} : count all the festivalAllowancePayments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/festival-allowance-payments/count")
    public ResponseEntity<Long> countFestivalAllowancePayments(FestivalAllowancePaymentCriteria criteria) {
        log.debug("REST request to count FestivalAllowancePayments by criteria: {}", criteria);
        return ResponseEntity.ok().body(festivalAllowancePaymentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /festival-allowance-payments/:id} : get the "id" festivalAllowancePayment.
     *
     * @param id the id of the festivalAllowancePayment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the festivalAllowancePayment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/festival-allowance-payments/{id}")
    public ResponseEntity<FestivalAllowancePayment> getFestivalAllowancePayment(@PathVariable Long id) {
        log.debug("REST request to get FestivalAllowancePayment : {}", id);
        Optional<FestivalAllowancePayment> festivalAllowancePayment = festivalAllowancePaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(festivalAllowancePayment);
    }

    /**
     * {@code DELETE  /festival-allowance-payments/:id} : delete the "id" festivalAllowancePayment.
     *
     * @param id the id of the festivalAllowancePayment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/festival-allowance-payments/{id}")
    public ResponseEntity<Void> deleteFestivalAllowancePayment(@PathVariable Long id) {
        log.debug("REST request to delete FestivalAllowancePayment : {}", id);
        festivalAllowancePaymentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
