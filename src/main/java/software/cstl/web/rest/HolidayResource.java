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
import software.cstl.domain.Holiday;
import software.cstl.service.HolidayQueryService;
import software.cstl.service.HolidayService;
import software.cstl.service.dto.HolidayCriteria;
import software.cstl.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.Holiday}.
 */
@RestController
@RequestMapping("/api")
public class HolidayResource {

    private final Logger log = LoggerFactory.getLogger(HolidayResource.class);

    private static final String ENTITY_NAME = "holiday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HolidayService holidayService;

    private final HolidayQueryService holidayQueryService;

    public HolidayResource(HolidayService holidayService, HolidayQueryService holidayQueryService) {
        this.holidayService = holidayService;
        this.holidayQueryService = holidayQueryService;
    }

    /**
     * {@code POST  /holidays} : Create a new holiday.
     *
     * @param holiday the holiday to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holiday, or with status {@code 400 (Bad Request)} if the holiday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/holidays")
    public ResponseEntity<Holiday> createHoliday(@Valid @RequestBody Holiday holiday) throws URISyntaxException {
        log.debug("REST request to save Holiday : {}", holiday);
        if (holiday.getId() != null) {
            throw new BadRequestAlertException("A new holiday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Holiday result = holidayService.save(holiday);
        return ResponseEntity.created(new URI("/api/holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /holidays} : Updates an existing holiday.
     *
     * @param holiday the holiday to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holiday,
     * or with status {@code 400 (Bad Request)} if the holiday is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holiday couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/holidays")
    public ResponseEntity<Holiday> updateHoliday(@Valid @RequestBody Holiday holiday) throws URISyntaxException {
        log.debug("REST request to update Holiday : {}", holiday);
        if (holiday.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Holiday result = holidayService.save(holiday);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, holiday.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /holidays} : get all the holidays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holidays in body.
     */
    @GetMapping("/holidays")
    public ResponseEntity<List<Holiday>> getAllHolidays(HolidayCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Holidays by criteria: {}", criteria);
        Page<Holiday> page = holidayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /holidays/count} : count all the holidays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/holidays/count")
    public ResponseEntity<Long> countHolidays(HolidayCriteria criteria) {
        log.debug("REST request to count Holidays by criteria: {}", criteria);
        return ResponseEntity.ok().body(holidayQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /holidays/:id} : get the "id" holiday.
     *
     * @param id the id of the holiday to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holiday, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/holidays/{id}")
    public ResponseEntity<Holiday> getHoliday(@PathVariable Long id) {
        log.debug("REST request to get Holiday : {}", id);
        Optional<Holiday> holiday = holidayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holiday);
    }

    /**
     * {@code DELETE  /holidays/:id} : delete the "id" holiday.
     *
     * @param id the id of the holiday to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/holidays/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        log.debug("REST request to delete Holiday : {}", id);
        holidayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
