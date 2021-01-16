package software.cstl.web.rest;

import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import software.cstl.domain.Attendance;
import software.cstl.service.AttendanceQueryService;
import software.cstl.service.AttendanceService;
import software.cstl.service.dto.AttendanceCriteria;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.Attendance}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceResource.class);

    private final AttendanceService attendanceService;

    private final AttendanceQueryService attendanceQueryService;

    public AttendanceResource(AttendanceService attendanceService, AttendanceQueryService attendanceQueryService) {
        this.attendanceService = attendanceService;
        this.attendanceQueryService = attendanceQueryService;
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
}
