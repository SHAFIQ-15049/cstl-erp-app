package software.cstl.web.rest;

import software.cstl.domain.Line;
import software.cstl.repository.LineRepository;
import software.cstl.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class LineResource {

    private final Logger log = LoggerFactory.getLogger(LineResource.class);

    private static final String ENTITY_NAME = "line";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LineRepository lineRepository;

    public LineResource(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
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
        Line result = lineRepository.save(line);
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
        Line result = lineRepository.save(line);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, line.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lines} : get all the lines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lines in body.
     */
    @GetMapping("/lines")
    public List<Line> getAllLines() {
        log.debug("REST request to get all Lines");
        return lineRepository.findAll();
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
        Optional<Line> line = lineRepository.findById(id);
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
        lineRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
