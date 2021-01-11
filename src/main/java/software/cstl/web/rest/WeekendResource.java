package software.cstl.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import software.cstl.domain.Weekend;
import software.cstl.service.WeekendQueryService;
import software.cstl.service.WeekendService;
import software.cstl.service.dto.WeekendCriteria;
import software.cstl.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.Weekend}.
 */
@RestController
@RequestMapping("/api")
public class WeekendResource {

    private final Logger log = LoggerFactory.getLogger(WeekendResource.class);

    private static final String ENTITY_NAME = "weekend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeekendService weekendService;

    private final WeekendQueryService weekendQueryService;

    public WeekendResource(WeekendService weekendService, WeekendQueryService weekendQueryService) {
        this.weekendService = weekendService;
        this.weekendQueryService = weekendQueryService;
    }

    /**
     * {@code POST  /weekends} : Create a new weekend.
     *
     * @param weekend the weekend to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weekend, or with status {@code 400 (Bad Request)} if the weekend has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/weekends")
    public ResponseEntity<Weekend> createWeekend(@Valid @RequestBody Weekend weekend) throws URISyntaxException {
        log.debug("REST request to save Weekend : {}", weekend);
        if (weekend.getId() != null) {
            throw new BadRequestAlertException("A new weekend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Weekend result = weekendService.save(weekend);
        return ResponseEntity.created(new URI("/api/weekends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /weekends} : Updates an existing weekend.
     *
     * @param weekend the weekend to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weekend,
     * or with status {@code 400 (Bad Request)} if the weekend is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weekend couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/weekends")
    public ResponseEntity<Weekend> updateWeekend(@Valid @RequestBody Weekend weekend) throws URISyntaxException {
        log.debug("REST request to update Weekend : {}", weekend);
        if (weekend.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Weekend result = weekendService.save(weekend);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, weekend.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /weekends} : get all the weekends.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weekends in body.
     */
    @GetMapping("/weekends")
    public ResponseEntity<List<Weekend>> getAllWeekends(WeekendCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Weekends by criteria: {}", criteria);
        Page<Weekend> page = weekendQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /weekends/count} : count all the weekends.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/weekends/count")
    public ResponseEntity<Long> countWeekends(WeekendCriteria criteria) {
        log.debug("REST request to count Weekends by criteria: {}", criteria);
        return ResponseEntity.ok().body(weekendQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /weekends/:id} : get the "id" weekend.
     *
     * @param id the id of the weekend to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weekend, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/weekends/{id}")
    public ResponseEntity<Weekend> getWeekend(@PathVariable Long id) {
        log.debug("REST request to get Weekend : {}", id);
        Optional<Weekend> weekend = weekendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weekend);
    }

    /**
     * {@code DELETE  /weekends/:id} : delete the "id" weekend.
     *
     * @param id the id of the weekend to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/weekends/{id}")
    public ResponseEntity<Void> deleteWeekend(@PathVariable Long id) {
        log.debug("REST request to delete Weekend : {}", id);
        weekendService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
