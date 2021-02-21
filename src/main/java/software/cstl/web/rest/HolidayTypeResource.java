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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import software.cstl.domain.HolidayType;
import software.cstl.security.AuthoritiesConstants;
import software.cstl.service.HolidayTypeQueryService;
import software.cstl.service.HolidayTypeService;
import software.cstl.service.dto.HolidayTypeCriteria;
import software.cstl.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.HolidayType}.
 */
@RestController
@RequestMapping("/api")
public class HolidayTypeResource {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeResource.class);

    private static final String ENTITY_NAME = "holidayType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HolidayTypeService holidayTypeService;

    private final HolidayTypeQueryService holidayTypeQueryService;

    public HolidayTypeResource(HolidayTypeService holidayTypeService, HolidayTypeQueryService holidayTypeQueryService) {
        this.holidayTypeService = holidayTypeService;
        this.holidayTypeQueryService = holidayTypeQueryService;
    }

    /**
     * {@code POST  /holiday-types} : Create a new holidayType.
     *
     * @param holidayType the holidayType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holidayType, or with status {@code 400 (Bad Request)} if the holidayType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/holiday-types")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")" +
        "|| hasAuthority(\"" + AuthoritiesConstants.HOLIDAY_ADMIN + "\")")
    public ResponseEntity<HolidayType> createHolidayType(@Valid @RequestBody HolidayType holidayType) throws URISyntaxException {
        log.debug("REST request to save HolidayType : {}", holidayType);
        if (holidayType.getId() != null) {
            throw new BadRequestAlertException("A new holidayType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HolidayType result = holidayTypeService.save(holidayType);
        return ResponseEntity.created(new URI("/api/holiday-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /holiday-types} : Updates an existing holidayType.
     *
     * @param holidayType the holidayType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holidayType,
     * or with status {@code 400 (Bad Request)} if the holidayType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holidayType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/holiday-types")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")" +
        "|| hasAuthority(\"" + AuthoritiesConstants.HOLIDAY_ADMIN + "\")")
    public ResponseEntity<HolidayType> updateHolidayType(@Valid @RequestBody HolidayType holidayType) throws URISyntaxException {
        log.debug("REST request to update HolidayType : {}", holidayType);
        if (holidayType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HolidayType result = holidayTypeService.save(holidayType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, holidayType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /holiday-types} : get all the holidayTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holidayTypes in body.
     */
    @GetMapping("/holiday-types")
    public ResponseEntity<List<HolidayType>> getAllHolidayTypes(HolidayTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get HolidayTypes by criteria: {}", criteria);
        Page<HolidayType> page = holidayTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /holiday-types/count} : count all the holidayTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/holiday-types/count")
    public ResponseEntity<Long> countHolidayTypes(HolidayTypeCriteria criteria) {
        log.debug("REST request to count HolidayTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(holidayTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /holiday-types/:id} : get the "id" holidayType.
     *
     * @param id the id of the holidayType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holidayType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/holiday-types/{id}")
    public ResponseEntity<HolidayType> getHolidayType(@PathVariable Long id) {
        log.debug("REST request to get HolidayType : {}", id);
        Optional<HolidayType> holidayType = holidayTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holidayType);
    }

    /**
     * {@code DELETE  /holiday-types/:id} : delete the "id" holidayType.
     *
     * @param id the id of the holidayType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/holiday-types/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteHolidayType(@PathVariable Long id) {
        log.debug("REST request to delete HolidayType : {}", id);
        holidayTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
