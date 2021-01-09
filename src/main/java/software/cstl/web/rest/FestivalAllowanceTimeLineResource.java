package software.cstl.web.rest;

import software.cstl.domain.FestivalAllowanceTimeLine;
import software.cstl.service.FestivalAllowanceTimeLineService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.FestivalAllowanceTimeLineCriteria;
import software.cstl.service.FestivalAllowanceTimeLineQueryService;

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
 * REST controller for managing {@link software.cstl.domain.FestivalAllowanceTimeLine}.
 */
@RestController
@RequestMapping("/api")
public class FestivalAllowanceTimeLineResource {

    private final Logger log = LoggerFactory.getLogger(FestivalAllowanceTimeLineResource.class);

    private static final String ENTITY_NAME = "festivalAllowanceTimeLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FestivalAllowanceTimeLineService festivalAllowanceTimeLineService;

    private final FestivalAllowanceTimeLineQueryService festivalAllowanceTimeLineQueryService;

    public FestivalAllowanceTimeLineResource(FestivalAllowanceTimeLineService festivalAllowanceTimeLineService, FestivalAllowanceTimeLineQueryService festivalAllowanceTimeLineQueryService) {
        this.festivalAllowanceTimeLineService = festivalAllowanceTimeLineService;
        this.festivalAllowanceTimeLineQueryService = festivalAllowanceTimeLineQueryService;
    }

    /**
     * {@code POST  /festival-allowance-time-lines} : Create a new festivalAllowanceTimeLine.
     *
     * @param festivalAllowanceTimeLine the festivalAllowanceTimeLine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new festivalAllowanceTimeLine, or with status {@code 400 (Bad Request)} if the festivalAllowanceTimeLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/festival-allowance-time-lines")
    public ResponseEntity<FestivalAllowanceTimeLine> createFestivalAllowanceTimeLine(@RequestBody FestivalAllowanceTimeLine festivalAllowanceTimeLine) throws URISyntaxException {
        log.debug("REST request to save FestivalAllowanceTimeLine : {}", festivalAllowanceTimeLine);
        if (festivalAllowanceTimeLine.getId() != null) {
            throw new BadRequestAlertException("A new festivalAllowanceTimeLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FestivalAllowanceTimeLine result = festivalAllowanceTimeLineService.save(festivalAllowanceTimeLine);
        return ResponseEntity.created(new URI("/api/festival-allowance-time-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /festival-allowance-time-lines} : Updates an existing festivalAllowanceTimeLine.
     *
     * @param festivalAllowanceTimeLine the festivalAllowanceTimeLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated festivalAllowanceTimeLine,
     * or with status {@code 400 (Bad Request)} if the festivalAllowanceTimeLine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the festivalAllowanceTimeLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/festival-allowance-time-lines")
    public ResponseEntity<FestivalAllowanceTimeLine> updateFestivalAllowanceTimeLine(@RequestBody FestivalAllowanceTimeLine festivalAllowanceTimeLine) throws URISyntaxException {
        log.debug("REST request to update FestivalAllowanceTimeLine : {}", festivalAllowanceTimeLine);
        if (festivalAllowanceTimeLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FestivalAllowanceTimeLine result = festivalAllowanceTimeLineService.save(festivalAllowanceTimeLine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, festivalAllowanceTimeLine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /festival-allowance-time-lines} : get all the festivalAllowanceTimeLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of festivalAllowanceTimeLines in body.
     */
    @GetMapping("/festival-allowance-time-lines")
    public ResponseEntity<List<FestivalAllowanceTimeLine>> getAllFestivalAllowanceTimeLines(FestivalAllowanceTimeLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FestivalAllowanceTimeLines by criteria: {}", criteria);
        Page<FestivalAllowanceTimeLine> page = festivalAllowanceTimeLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /festival-allowance-time-lines/count} : count all the festivalAllowanceTimeLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/festival-allowance-time-lines/count")
    public ResponseEntity<Long> countFestivalAllowanceTimeLines(FestivalAllowanceTimeLineCriteria criteria) {
        log.debug("REST request to count FestivalAllowanceTimeLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(festivalAllowanceTimeLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /festival-allowance-time-lines/:id} : get the "id" festivalAllowanceTimeLine.
     *
     * @param id the id of the festivalAllowanceTimeLine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the festivalAllowanceTimeLine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/festival-allowance-time-lines/{id}")
    public ResponseEntity<FestivalAllowanceTimeLine> getFestivalAllowanceTimeLine(@PathVariable Long id) {
        log.debug("REST request to get FestivalAllowanceTimeLine : {}", id);
        Optional<FestivalAllowanceTimeLine> festivalAllowanceTimeLine = festivalAllowanceTimeLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(festivalAllowanceTimeLine);
    }

    /**
     * {@code DELETE  /festival-allowance-time-lines/:id} : delete the "id" festivalAllowanceTimeLine.
     *
     * @param id the id of the festivalAllowanceTimeLine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/festival-allowance-time-lines/{id}")
    public ResponseEntity<Void> deleteFestivalAllowanceTimeLine(@PathVariable Long id) {
        log.debug("REST request to delete FestivalAllowanceTimeLine : {}", id);
        festivalAllowanceTimeLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
