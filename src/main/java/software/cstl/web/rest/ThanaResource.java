package software.cstl.web.rest;

import software.cstl.domain.Thana;
import software.cstl.service.ThanaService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.ThanaCriteria;
import software.cstl.service.ThanaQueryService;

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
 * REST controller for managing {@link software.cstl.domain.Thana}.
 */
@RestController
@RequestMapping("/api")
public class ThanaResource {

    private final Logger log = LoggerFactory.getLogger(ThanaResource.class);

    private static final String ENTITY_NAME = "thana";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThanaService thanaService;

    private final ThanaQueryService thanaQueryService;

    public ThanaResource(ThanaService thanaService, ThanaQueryService thanaQueryService) {
        this.thanaService = thanaService;
        this.thanaQueryService = thanaQueryService;
    }

    /**
     * {@code POST  /thanas} : Create a new thana.
     *
     * @param thana the thana to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thana, or with status {@code 400 (Bad Request)} if the thana has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thanas")
    public ResponseEntity<Thana> createThana(@Valid @RequestBody Thana thana) throws URISyntaxException {
        log.debug("REST request to save Thana : {}", thana);
        if (thana.getId() != null) {
            throw new BadRequestAlertException("A new thana cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Thana result = thanaService.save(thana);
        return ResponseEntity.created(new URI("/api/thanas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thanas} : Updates an existing thana.
     *
     * @param thana the thana to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thana,
     * or with status {@code 400 (Bad Request)} if the thana is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thana couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thanas")
    public ResponseEntity<Thana> updateThana(@Valid @RequestBody Thana thana) throws URISyntaxException {
        log.debug("REST request to update Thana : {}", thana);
        if (thana.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Thana result = thanaService.save(thana);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thana.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /thanas} : get all the thanas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thanas in body.
     */
    @GetMapping("/thanas")
    public ResponseEntity<List<Thana>> getAllThanas(ThanaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Thanas by criteria: {}", criteria);
        Page<Thana> page = thanaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /thanas/count} : count all the thanas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/thanas/count")
    public ResponseEntity<Long> countThanas(ThanaCriteria criteria) {
        log.debug("REST request to count Thanas by criteria: {}", criteria);
        return ResponseEntity.ok().body(thanaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /thanas/:id} : get the "id" thana.
     *
     * @param id the id of the thana to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thana, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thanas/{id}")
    public ResponseEntity<Thana> getThana(@PathVariable Long id) {
        log.debug("REST request to get Thana : {}", id);
        Optional<Thana> thana = thanaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thana);
    }

    /**
     * {@code DELETE  /thanas/:id} : delete the "id" thana.
     *
     * @param id the id of the thana to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thanas/{id}")
    public ResponseEntity<Void> deleteThana(@PathVariable Long id) {
        log.debug("REST request to delete Thana : {}", id);
        thanaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
