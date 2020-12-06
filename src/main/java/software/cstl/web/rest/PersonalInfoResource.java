package software.cstl.web.rest;

import software.cstl.domain.PersonalInfo;
import software.cstl.service.PersonalInfoService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.PersonalInfoCriteria;
import software.cstl.service.PersonalInfoQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link software.cstl.domain.PersonalInfo}.
 */
@RestController
@RequestMapping("/api")
public class PersonalInfoResource {

    private final Logger log = LoggerFactory.getLogger(PersonalInfoResource.class);

    private static final String ENTITY_NAME = "personalInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalInfoService personalInfoService;

    private final PersonalInfoQueryService personalInfoQueryService;

    public PersonalInfoResource(PersonalInfoService personalInfoService, PersonalInfoQueryService personalInfoQueryService) {
        this.personalInfoService = personalInfoService;
        this.personalInfoQueryService = personalInfoQueryService;
    }

    /**
     * {@code POST  /personal-infos} : Create a new personalInfo.
     *
     * @param personalInfo the personalInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personalInfo, or with status {@code 400 (Bad Request)} if the personalInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personal-infos")
    public ResponseEntity<PersonalInfo> createPersonalInfo(@Valid @RequestBody PersonalInfo personalInfo) throws URISyntaxException {
        log.debug("REST request to save PersonalInfo : {}", personalInfo);
        if (personalInfo.getId() != null) {
            throw new BadRequestAlertException("A new personalInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalInfo result = personalInfoService.save(personalInfo);
        return ResponseEntity.created(new URI("/api/personal-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personal-infos} : Updates an existing personalInfo.
     *
     * @param personalInfo the personalInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalInfo,
     * or with status {@code 400 (Bad Request)} if the personalInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personalInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personal-infos")
    public ResponseEntity<PersonalInfo> updatePersonalInfo(@Valid @RequestBody PersonalInfo personalInfo) throws URISyntaxException {
        log.debug("REST request to update PersonalInfo : {}", personalInfo);
        if (personalInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonalInfo result = personalInfoService.save(personalInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personalInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /personal-infos} : get all the personalInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personalInfos in body.
     */
    @GetMapping("/personal-infos")
    public ResponseEntity<List<PersonalInfo>> getAllPersonalInfos(PersonalInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PersonalInfos by criteria: {}", criteria);
        Page<PersonalInfo> page = personalInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personal-infos/count} : count all the personalInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/personal-infos/count")
    public ResponseEntity<Long> countPersonalInfos(PersonalInfoCriteria criteria) {
        log.debug("REST request to count PersonalInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(personalInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /personal-infos/:id} : get the "id" personalInfo.
     *
     * @param id the id of the personalInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personalInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personal-infos/{id}")
    public ResponseEntity<PersonalInfo> getPersonalInfo(@PathVariable Long id) {
        log.debug("REST request to get PersonalInfo : {}", id);
        Optional<PersonalInfo> personalInfo = personalInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalInfo);
    }

    /**
     * {@code DELETE  /personal-infos/:id} : delete the "id" personalInfo.
     *
     * @param id the id of the personalInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personal-infos/{id}")
    public ResponseEntity<Void> deletePersonalInfo(@PathVariable Long id) {
        log.debug("REST request to delete PersonalInfo : {}", id);
        personalInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
