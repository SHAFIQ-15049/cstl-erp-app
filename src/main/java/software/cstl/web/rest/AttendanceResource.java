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
import software.cstl.domain.Attendance;
import software.cstl.security.AuthoritiesConstants;
import software.cstl.service.AttendanceQueryService;
import software.cstl.service.AttendanceService;
import software.cstl.service.dto.AttendanceCriteria;
import software.cstl.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.Attendance}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceResource.class);

    private static final String ENTITY_NAME = "attendance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceService attendanceService;

    private final AttendanceQueryService attendanceQueryService;

    public AttendanceResource(AttendanceService attendanceService, AttendanceQueryService attendanceQueryService) {
        this.attendanceService = attendanceService;
        this.attendanceQueryService = attendanceQueryService;
    }

    /**
     * {@code POST  /attendances} : Create a new attendance.
     *
     * @param attendance the attendance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendance, or with status {@code 400 (Bad Request)} if the attendance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendances")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")" +
        " || hasAuthority(\"" + AuthoritiesConstants.ATTENDANCE_ADMIN + "\")" +
        " || hasAuthority(\"" + AuthoritiesConstants.ATTENDANCE_MANAGER + "\")")
    public ResponseEntity<Attendance> createAttendance(@Valid @RequestBody Attendance attendance) throws URISyntaxException {
        log.debug("REST request to save Attendance : {}", attendance);
        if (attendance.getId() != null) {
            throw new BadRequestAlertException("A new attendance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Attendance result = attendanceService.manualSave(attendance);
        return ResponseEntity.created(new URI("/api/attendances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendances} : Updates an existing attendance.
     *
     * @param attendance the attendance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendance,
     * or with status {@code 400 (Bad Request)} if the attendance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendances")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")" +
        " || hasAuthority(\"" + AuthoritiesConstants.ATTENDANCE_ADMIN + "\")" +
        " || hasAuthority(\"" + AuthoritiesConstants.ATTENDANCE_MANAGER + "\")")
    public ResponseEntity<Attendance> updateAttendance(@Valid @RequestBody Attendance attendance) throws URISyntaxException {
        log.debug("REST request to update Attendance : {}", attendance);
        if (attendance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Attendance result = attendanceService.save(attendance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attendance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attendances} : get all the attendances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendances in body.
     */
    @GetMapping("/attendances")
    public ResponseEntity<List<Attendance>> getAllAttendances(AttendanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Attendances by criteria: {}", criteria);
        Page<Attendance> page = attendanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attendances/count} : count all the attendances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/attendances/count")
    public ResponseEntity<Long> countAttendances(AttendanceCriteria criteria) {
        log.debug("REST request to count Attendances by criteria: {}", criteria);
        return ResponseEntity.ok().body(attendanceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /attendances/:id} : get the "id" attendance.
     *
     * @param id the id of the attendance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendances/{id}")
    public ResponseEntity<Attendance> getAttendance(@PathVariable Long id) {
        log.debug("REST request to get Attendance : {}", id);
        Optional<Attendance> attendance = attendanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendance);
    }

    /**
     * {@code DELETE  /attendances/:id} : delete the "id" attendance.
     *
     * @param id the id of the attendance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendances/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        log.debug("REST request to delete Attendance : {}", id);
        attendanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
