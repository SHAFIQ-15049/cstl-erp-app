package software.cstl.web.rest;

import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.service.MonthlySalaryDtlService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.MonthlySalaryDtlCriteria;
import software.cstl.service.MonthlySalaryDtlQueryService;

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
 * REST controller for managing {@link software.cstl.domain.MonthlySalaryDtl}.
 */
@RestController
@RequestMapping("/api")
public class MonthlySalaryDtlResource {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryDtlResource.class);

    private static final String ENTITY_NAME = "monthlySalaryDtl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonthlySalaryDtlService monthlySalaryDtlService;

    private final MonthlySalaryDtlQueryService monthlySalaryDtlQueryService;

    public MonthlySalaryDtlResource(MonthlySalaryDtlService monthlySalaryDtlService, MonthlySalaryDtlQueryService monthlySalaryDtlQueryService) {
        this.monthlySalaryDtlService = monthlySalaryDtlService;
        this.monthlySalaryDtlQueryService = monthlySalaryDtlQueryService;
    }

    /**
     * {@code POST  /monthly-salary-dtls} : Create a new monthlySalaryDtl.
     *
     * @param monthlySalaryDtl the monthlySalaryDtl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monthlySalaryDtl, or with status {@code 400 (Bad Request)} if the monthlySalaryDtl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monthly-salary-dtls")
    public ResponseEntity<MonthlySalaryDtl> createMonthlySalaryDtl(@RequestBody MonthlySalaryDtl monthlySalaryDtl) throws URISyntaxException {
        log.debug("REST request to save MonthlySalaryDtl : {}", monthlySalaryDtl);
        if (monthlySalaryDtl.getId() != null) {
            throw new BadRequestAlertException("A new monthlySalaryDtl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonthlySalaryDtl result = monthlySalaryDtlService.save(monthlySalaryDtl);
        return ResponseEntity.created(new URI("/api/monthly-salary-dtls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /monthly-salary-dtls} : Updates an existing monthlySalaryDtl.
     *
     * @param monthlySalaryDtl the monthlySalaryDtl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlySalaryDtl,
     * or with status {@code 400 (Bad Request)} if the monthlySalaryDtl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monthlySalaryDtl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monthly-salary-dtls")
    public ResponseEntity<MonthlySalaryDtl> updateMonthlySalaryDtl(@RequestBody MonthlySalaryDtl monthlySalaryDtl) throws URISyntaxException {
        log.debug("REST request to update MonthlySalaryDtl : {}", monthlySalaryDtl);
        if (monthlySalaryDtl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MonthlySalaryDtl result = monthlySalaryDtlService.save(monthlySalaryDtl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, monthlySalaryDtl.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /monthly-salary-dtls} : get all the monthlySalaryDtls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monthlySalaryDtls in body.
     */
    @GetMapping("/monthly-salary-dtls")
    public ResponseEntity<List<MonthlySalaryDtl>> getAllMonthlySalaryDtls(MonthlySalaryDtlCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MonthlySalaryDtls by criteria: {}", criteria);
        Page<MonthlySalaryDtl> page = monthlySalaryDtlQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /monthly-salary-dtls/count} : count all the monthlySalaryDtls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/monthly-salary-dtls/count")
    public ResponseEntity<Long> countMonthlySalaryDtls(MonthlySalaryDtlCriteria criteria) {
        log.debug("REST request to count MonthlySalaryDtls by criteria: {}", criteria);
        return ResponseEntity.ok().body(monthlySalaryDtlQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /monthly-salary-dtls/:id} : get the "id" monthlySalaryDtl.
     *
     * @param id the id of the monthlySalaryDtl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monthlySalaryDtl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monthly-salary-dtls/{id}")
    public ResponseEntity<MonthlySalaryDtl> getMonthlySalaryDtl(@PathVariable Long id) {
        log.debug("REST request to get MonthlySalaryDtl : {}", id);
        Optional<MonthlySalaryDtl> monthlySalaryDtl = monthlySalaryDtlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monthlySalaryDtl);
    }

    /**
     * {@code DELETE  /monthly-salary-dtls/:id} : delete the "id" monthlySalaryDtl.
     *
     * @param id the id of the monthlySalaryDtl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monthly-salary-dtls/{id}")
    public ResponseEntity<Void> deleteMonthlySalaryDtl(@PathVariable Long id) {
        log.debug("REST request to delete MonthlySalaryDtl : {}", id);
        monthlySalaryDtlService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
