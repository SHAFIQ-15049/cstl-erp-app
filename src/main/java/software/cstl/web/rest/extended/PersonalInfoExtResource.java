package software.cstl.web.rest.extended;

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
import software.cstl.domain.PersonalInfo;
import software.cstl.service.PersonalInfoQueryService;
import software.cstl.service.PersonalInfoService;
import software.cstl.service.dto.PersonalInfoCriteria;
import software.cstl.service.extended.PersonalInfoExtService;
import software.cstl.web.rest.PersonalInfoResource;
import software.cstl.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ext")
public class PersonalInfoExtResource extends PersonalInfoResource {
    private final Logger log = LoggerFactory.getLogger(PersonalInfoExtResource.class);

    private static final String ENTITY_NAME = "personalInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalInfoExtService personalInfoService;

    private final PersonalInfoQueryService personalInfoQueryService;

    public PersonalInfoExtResource(PersonalInfoService personalInfoService, PersonalInfoQueryService personalInfoQueryService, PersonalInfoExtService personalInfoService1, PersonalInfoQueryService personalInfoQueryService1) {
        super(personalInfoService, personalInfoQueryService);
        this.personalInfoService = personalInfoService1;
        this.personalInfoQueryService = personalInfoQueryService1;
    }

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

    @GetMapping("/personal-infos")
    public ResponseEntity<List<PersonalInfo>> getAllPersonalInfos(PersonalInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PersonalInfos by criteria: {}", criteria);
        Page<PersonalInfo> page = personalInfoQueryService.findByCriteria(criteria, pageable);
        page = personalInfoService.attachAttachmentsToPageables(page);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/personal-infos/{id}")
    public ResponseEntity<PersonalInfo> getPersonalInfo(@PathVariable Long id) {
        log.debug("REST request to get PersonalInfo : {}", id);
        Optional<PersonalInfo> personalInfo = personalInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalInfo);
    }
}
