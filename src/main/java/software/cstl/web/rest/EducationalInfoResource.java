package software.cstl.web.rest;

import software.cstl.domain.EducationalInfo;
import software.cstl.service.EducationalInfoService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.EducationalInfoCriteria;
import software.cstl.service.EducationalInfoQueryService;

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
 * REST controller for managing {@link software.cstl.domain.EducationalInfo}.
 */
@RestController
@RequestMapping("/api")
public class EducationalInfoResource {

    private final Logger log = LoggerFactory.getLogger(EducationalInfoResource.class);

    private static final String ENTITY_NAME = "educationalInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducationalInfoService educationalInfoService;

    private final EducationalInfoQueryService educationalInfoQueryService;

    public EducationalInfoResource(EducationalInfoService educationalInfoService, EducationalInfoQueryService educationalInfoQueryService) {
        this.educationalInfoService = educationalInfoService;
        this.educationalInfoQueryService = educationalInfoQueryService;
    }

    /**
     * {@code POST  /educational-infos} : Create a new educationalInfo.
     *
     * @param educationalInfo the educationalInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educationalInfo, or with status {@code 400 (Bad Request)} if the educationalInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/educational-infos")
    public ResponseEntity<EducationalInfo> createEducationalInfo(@Valid @RequestBody EducationalInfo educationalInfo) throws URISyntaxException {
        log.debug("REST request to save EducationalInfo : {}", educationalInfo);
        if (educationalInfo.getId() != null) {
            throw new BadRequestAlertException("A new educationalInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducationalInfo result = educationalInfoService.save(educationalInfo);
        return ResponseEntity.created(new URI("/api/educational-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /educational-infos} : Updates an existing educationalInfo.
     *
     * @param educationalInfo the educationalInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationalInfo,
     * or with status {@code 400 (Bad Request)} if the educationalInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educationalInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/educational-infos")
    public ResponseEntity<EducationalInfo> updateEducationalInfo(@Valid @RequestBody EducationalInfo educationalInfo) throws URISyntaxException {
        log.debug("REST request to update EducationalInfo : {}", educationalInfo);
        if (educationalInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EducationalInfo result = educationalInfoService.save(educationalInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, educationalInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /educational-infos} : get all the educationalInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educationalInfos in body.
     */
    @GetMapping("/educational-infos")
    public ResponseEntity<List<EducationalInfo>> getAllEducationalInfos(EducationalInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EducationalInfos by criteria: {}", criteria);
        Page<EducationalInfo> page = educationalInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /educational-infos/count} : count all the educationalInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/educational-infos/count")
    public ResponseEntity<Long> countEducationalInfos(EducationalInfoCriteria criteria) {
        log.debug("REST request to count EducationalInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(educationalInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /educational-infos/:id} : get the "id" educationalInfo.
     *
     * @param id the id of the educationalInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educationalInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/educational-infos/{id}")
    public ResponseEntity<EducationalInfo> getEducationalInfo(@PathVariable Long id) {
        log.debug("REST request to get EducationalInfo : {}", id);
        Optional<EducationalInfo> educationalInfo = educationalInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(educationalInfo);
    }

    /**
     * {@code DELETE  /educational-infos/:id} : delete the "id" educationalInfo.
     *
     * @param id the id of the educationalInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/educational-infos/{id}")
    public ResponseEntity<Void> deleteEducationalInfo(@PathVariable Long id) {
        log.debug("REST request to delete EducationalInfo : {}", id);
        educationalInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
