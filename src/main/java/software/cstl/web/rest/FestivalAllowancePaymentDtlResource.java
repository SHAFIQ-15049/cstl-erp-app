package software.cstl.web.rest;

import software.cstl.domain.FestivalAllowancePaymentDtl;
import software.cstl.service.FestivalAllowancePaymentDtlService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.FestivalAllowancePaymentDtlCriteria;
import software.cstl.service.FestivalAllowancePaymentDtlQueryService;

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
 * REST controller for managing {@link software.cstl.domain.FestivalAllowancePaymentDtl}.
 */
@RestController
@RequestMapping("/api")
public class FestivalAllowancePaymentDtlResource {

    private final Logger log = LoggerFactory.getLogger(FestivalAllowancePaymentDtlResource.class);

    private static final String ENTITY_NAME = "festivalAllowancePaymentDtl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FestivalAllowancePaymentDtlService festivalAllowancePaymentDtlService;

    private final FestivalAllowancePaymentDtlQueryService festivalAllowancePaymentDtlQueryService;

    public FestivalAllowancePaymentDtlResource(FestivalAllowancePaymentDtlService festivalAllowancePaymentDtlService, FestivalAllowancePaymentDtlQueryService festivalAllowancePaymentDtlQueryService) {
        this.festivalAllowancePaymentDtlService = festivalAllowancePaymentDtlService;
        this.festivalAllowancePaymentDtlQueryService = festivalAllowancePaymentDtlQueryService;
    }

    /**
     * {@code POST  /festival-allowance-payment-dtls} : Create a new festivalAllowancePaymentDtl.
     *
     * @param festivalAllowancePaymentDtl the festivalAllowancePaymentDtl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new festivalAllowancePaymentDtl, or with status {@code 400 (Bad Request)} if the festivalAllowancePaymentDtl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/festival-allowance-payment-dtls")
    public ResponseEntity<FestivalAllowancePaymentDtl> createFestivalAllowancePaymentDtl(@RequestBody FestivalAllowancePaymentDtl festivalAllowancePaymentDtl) throws URISyntaxException {
        log.debug("REST request to save FestivalAllowancePaymentDtl : {}", festivalAllowancePaymentDtl);
        if (festivalAllowancePaymentDtl.getId() != null) {
            throw new BadRequestAlertException("A new festivalAllowancePaymentDtl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FestivalAllowancePaymentDtl result = festivalAllowancePaymentDtlService.save(festivalAllowancePaymentDtl);
        return ResponseEntity.created(new URI("/api/festival-allowance-payment-dtls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /festival-allowance-payment-dtls} : Updates an existing festivalAllowancePaymentDtl.
     *
     * @param festivalAllowancePaymentDtl the festivalAllowancePaymentDtl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated festivalAllowancePaymentDtl,
     * or with status {@code 400 (Bad Request)} if the festivalAllowancePaymentDtl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the festivalAllowancePaymentDtl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/festival-allowance-payment-dtls")
    public ResponseEntity<FestivalAllowancePaymentDtl> updateFestivalAllowancePaymentDtl(@RequestBody FestivalAllowancePaymentDtl festivalAllowancePaymentDtl) throws URISyntaxException {
        log.debug("REST request to update FestivalAllowancePaymentDtl : {}", festivalAllowancePaymentDtl);
        if (festivalAllowancePaymentDtl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FestivalAllowancePaymentDtl result = festivalAllowancePaymentDtlService.save(festivalAllowancePaymentDtl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, festivalAllowancePaymentDtl.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /festival-allowance-payment-dtls} : get all the festivalAllowancePaymentDtls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of festivalAllowancePaymentDtls in body.
     */
    @GetMapping("/festival-allowance-payment-dtls")
    public ResponseEntity<List<FestivalAllowancePaymentDtl>> getAllFestivalAllowancePaymentDtls(FestivalAllowancePaymentDtlCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FestivalAllowancePaymentDtls by criteria: {}", criteria);
        Page<FestivalAllowancePaymentDtl> page = festivalAllowancePaymentDtlQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /festival-allowance-payment-dtls/count} : count all the festivalAllowancePaymentDtls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/festival-allowance-payment-dtls/count")
    public ResponseEntity<Long> countFestivalAllowancePaymentDtls(FestivalAllowancePaymentDtlCriteria criteria) {
        log.debug("REST request to count FestivalAllowancePaymentDtls by criteria: {}", criteria);
        return ResponseEntity.ok().body(festivalAllowancePaymentDtlQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /festival-allowance-payment-dtls/:id} : get the "id" festivalAllowancePaymentDtl.
     *
     * @param id the id of the festivalAllowancePaymentDtl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the festivalAllowancePaymentDtl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/festival-allowance-payment-dtls/{id}")
    public ResponseEntity<FestivalAllowancePaymentDtl> getFestivalAllowancePaymentDtl(@PathVariable Long id) {
        log.debug("REST request to get FestivalAllowancePaymentDtl : {}", id);
        Optional<FestivalAllowancePaymentDtl> festivalAllowancePaymentDtl = festivalAllowancePaymentDtlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(festivalAllowancePaymentDtl);
    }

    /**
     * {@code DELETE  /festival-allowance-payment-dtls/:id} : delete the "id" festivalAllowancePaymentDtl.
     *
     * @param id the id of the festivalAllowancePaymentDtl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/festival-allowance-payment-dtls/{id}")
    public ResponseEntity<Void> deleteFestivalAllowancePaymentDtl(@PathVariable Long id) {
        log.debug("REST request to delete FestivalAllowancePaymentDtl : {}", id);
        festivalAllowancePaymentDtlService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
