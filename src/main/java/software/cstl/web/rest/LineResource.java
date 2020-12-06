package software.cstl.web.rest;

import software.cstl.domain.Line;
import software.cstl.service.LineService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.LineCriteria;
import software.cstl.service.LineQueryService;

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
 * REST controller for managing {@link software.cstl.domain.Line}.
 */
@RestController
@RequestMapping("/api")
public class LineResource {

    private final Logger log = LoggerFactory.getLogger(LineResource.class);

    private static final String ENTITY_NAME = "line";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LineService lineService;

    private final LineQueryService lineQueryService;

    public LineResource(LineService lineService, LineQueryService lineQueryService) {
        this.lineService = lineService;
        this.lineQueryService = lineQueryService;
    }

    /**
     * {@code POST  /lines} : Create a new line.
     *
     * @param line the line to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new line, or with status {@code 400 (Bad Request)} if the line has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lines")
    public ResponseEntity<Line> createLine(@Valid @RequestBody Line line) throws URISyntaxException {
        log.debug("REST request to save Line : {}", line);
        if (line.getId() != null) {
            throw new BadRequestAlertException("A new line cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Line result = lineService.save(line);
        return ResponseEntity.created(new URI("/api/lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lines} : Updates an existing line.
     *
     * @param line the line to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated line,
     * or with status {@code 400 (Bad Request)} if the line is not valid,
     * or with status {@code 500 (Internal Server Error)} if the line couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lines")
    public ResponseEntity<Line> updateLine(@Valid @RequestBody Line line) throws URISyntaxException {
        log.debug("REST request to update Line : {}", line);
        if (line.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Line result = lineService.save(line);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, line.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lines} : get all the lines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lines in body.
     */
    @GetMapping("/lines")
    public ResponseEntity<List<Line>> getAllLines(LineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Lines by criteria: {}", criteria);
        Page<Line> page = lineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lines/count} : count all the lines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/lines/count")
    public ResponseEntity<Long> countLines(LineCriteria criteria) {
        log.debug("REST request to count Lines by criteria: {}", criteria);
        return ResponseEntity.ok().body(lineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lines/:id} : get the "id" line.
     *
     * @param id the id of the line to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the line, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lines/{id}")
    public ResponseEntity<Line> getLine(@PathVariable Long id) {
        log.debug("REST request to get Line : {}", id);
        Optional<Line> line = lineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(line);
    }

    /**
     * {@code DELETE  /lines/:id} : delete the "id" line.
     *
     * @param id the id of the line to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lines/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long id) {
        log.debug("REST request to delete Line : {}", id);
        lineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
