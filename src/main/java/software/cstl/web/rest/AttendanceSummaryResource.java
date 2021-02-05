package software.cstl.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.security.AuthoritiesConstants;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.dto.AttendanceSummaryDTO;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing {@link AttendanceSummaryDTO}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceSummaryResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryResource.class);

    private final AttendanceSummaryService attendanceSummaryService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public AttendanceSummaryResource(AttendanceSummaryService attendanceSummaryService) {
        this.attendanceSummaryService = attendanceSummaryService;
    }

    @PutMapping("/attendance-summaries/marked-as")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")" +
        " || hasAuthority(\"" + AuthoritiesConstants.ATTENDANCE_ADMIN + "\")" +
        " || hasAuthority(\"" + AuthoritiesConstants.ATTENDANCE_MANAGER + "\")")
    public ResponseEntity<List<AttendanceSummaryDTO>> updateAttendanceSummaries(@RequestBody  List<AttendanceSummaryDTO> attendanceSummaryDTOs) throws URISyntaxException {
        log.debug("REST request to update attendance summaries : {}", attendanceSummaryDTOs);
        List<AttendanceSummaryDTO> results = attendanceSummaryService.update(attendanceSummaryDTOs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, "attendance-summary", results.get(0).getSerialNo().toString()))
            .body(results);
    }

    @GetMapping("/attendance-summaries/departmentId/{departmentId}/employeeId/{employeeId}/fromDate/{fromDate}/toDate/{toDate}/markedAs/{markedAs}")
    public List<AttendanceSummaryDTO> getAllAttendanceSummaries(@PathVariable Long departmentId, @PathVariable Long employeeId, @PathVariable LocalDate fromDate, @PathVariable LocalDate toDate, @PathVariable String markedAs) {
        log.debug("REST request to get all AttendanceSummaries");
        return attendanceSummaryService.findAll(departmentId, employeeId, fromDate, toDate, AttendanceMarkedAs.lookup(markedAs));
    }

    @GetMapping("/attendance-summaries/fromDate/{fromDate}/toDate/{toDate}")
    public List<AttendanceSummaryDTO> getAllAttendanceSummaries(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate) {
        log.debug("REST request to get all AttendanceSummaries");
        return attendanceSummaryService.findAll(fromDate, toDate);
    }

    @GetMapping("/attendance-summaries/duty-leave")
    public List<AttendanceSummaryDTO> getAllAttendanceSummaries() {
        log.debug("REST request to get all AttendanceSummaries");
        return attendanceSummaryService.findAllWhoWillGetDutyLeave();
    }
}
