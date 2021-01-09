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
import software.cstl.domain.AttendanceDataUpload;
import software.cstl.service.AttendanceDataUploadService;
import software.cstl.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.AttendanceDataUpload}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceDataUploadResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceDataUploadResource.class);

    private static final String ENTITY_NAME = "attendanceDataUpload";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceDataUploadService attendanceDataUploadService;

    public AttendanceDataUploadResource(AttendanceDataUploadService attendanceDataUploadService) {
        this.attendanceDataUploadService = attendanceDataUploadService;
    }

    /**
     * {@code POST  /attendance-data-uploads} : Create a new attendanceDataUpload.
     *
     * @param attendanceDataUpload the attendanceDataUpload to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendanceDataUpload, or with status {@code 400 (Bad Request)} if the attendanceDataUpload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendance-data-uploads")
    public ResponseEntity<AttendanceDataUpload> createAttendanceDataUpload(@Valid @RequestBody AttendanceDataUpload attendanceDataUpload) throws URISyntaxException {
        log.debug("REST request to save AttendanceDataUpload : {}", attendanceDataUpload);
        if (attendanceDataUpload.getId() != null) {
            throw new BadRequestAlertException("A new attendanceDataUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceDataUpload result = attendanceDataUploadService.save(attendanceDataUpload);
        return ResponseEntity.created(new URI("/api/attendance-data-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendance-data-uploads} : Updates an existing attendanceDataUpload.
     *
     * @param attendanceDataUpload the attendanceDataUpload to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceDataUpload,
     * or with status {@code 400 (Bad Request)} if the attendanceDataUpload is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendanceDataUpload couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendance-data-uploads")
    public ResponseEntity<AttendanceDataUpload> updateAttendanceDataUpload(@Valid @RequestBody AttendanceDataUpload attendanceDataUpload) throws URISyntaxException {
        log.debug("REST request to update AttendanceDataUpload : {}", attendanceDataUpload);
        if (attendanceDataUpload.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttendanceDataUpload result = attendanceDataUploadService.save(attendanceDataUpload);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attendanceDataUpload.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attendance-data-uploads} : get all the attendanceDataUploads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendanceDataUploads in body.
     */
    @GetMapping("/attendance-data-uploads")
    public ResponseEntity<List<AttendanceDataUpload>> getAllAttendanceDataUploads(Pageable pageable) {
        log.debug("REST request to get a page of AttendanceDataUploads");
        Page<AttendanceDataUpload> page = attendanceDataUploadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attendance-data-uploads/:id} : get the "id" attendanceDataUpload.
     *
     * @param id the id of the attendanceDataUpload to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendanceDataUpload, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendance-data-uploads/{id}")
    public ResponseEntity<AttendanceDataUpload> getAttendanceDataUpload(@PathVariable Long id) {
        log.debug("REST request to get AttendanceDataUpload : {}", id);
        Optional<AttendanceDataUpload> attendanceDataUpload = attendanceDataUploadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendanceDataUpload);
    }

    /**
     * {@code DELETE  /attendance-data-uploads/:id} : delete the "id" attendanceDataUpload.
     *
     * @param id the id of the attendanceDataUpload to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendance-data-uploads/{id}")
    public ResponseEntity<Void> deleteAttendanceDataUpload(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceDataUpload : {}", id);
        attendanceDataUploadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
