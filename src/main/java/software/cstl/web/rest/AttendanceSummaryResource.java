package software.cstl.web.rest;

import com.itextpdf.text.DocumentException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.security.AuthoritiesConstants;
import software.cstl.service.AttendanceSummaryPersonalStatusService;
import software.cstl.service.AttendanceSummaryReportService;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.dto.AttendanceSummaryDTO;
import software.cstl.web.rest.errors.BadRequestAlertException;

import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * REST controller for managing {@link AttendanceSummaryDTO}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceSummaryResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryResource.class);

    private final AttendanceSummaryService attendanceSummaryService;

    private final AttendanceSummaryReportService attendanceSummaryReportService;


    private  AttendanceSummaryPersonalStatusService attendanceSummaryPersonalStatusService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public AttendanceSummaryResource(AttendanceSummaryService attendanceSummaryService, AttendanceSummaryReportService attendanceSummaryReportService,AttendanceSummaryPersonalStatusService attendanceSummaryPersonalStatusService) {
        this.attendanceSummaryService = attendanceSummaryService;
        this.attendanceSummaryReportService = attendanceSummaryReportService;
        this.attendanceSummaryPersonalStatusService = attendanceSummaryPersonalStatusService;
    }

    @PutMapping("/attendance-summaries/marked-as")
    public ResponseEntity<List<AttendanceSummaryDTO>> updateAttendanceSummaries(@RequestBody  List<AttendanceSummaryDTO> attendanceSummaryDTOs) throws URISyntaxException {
        log.debug("REST request to update attendance summaries : {}", attendanceSummaryDTOs);
        List<AttendanceSummaryDTO> results = attendanceSummaryService.update(attendanceSummaryDTOs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, "attendance-summary", results.get(0).getSerialNo().toString()))
            .body(results);
    }

    @GetMapping("/attendance-summaries/departmentId/{departmentId}/empId/{empId}/employeeId/{employeeId}/fromDate/{fromDate}/toDate/{toDate}/markedAs/{markedAs}")
    public List<AttendanceSummaryDTO> getAllAttendanceSummaries(@PathVariable Long departmentId, @PathVariable String empId, @PathVariable Long employeeId, @PathVariable LocalDate fromDate, @PathVariable LocalDate toDate, @PathVariable String markedAs) {
        log.debug("REST request to get all AttendanceSummaries");
        return attendanceSummaryService.findAll(departmentId, empId, employeeId, fromDate, toDate, AttendanceMarkedAs.lookup(markedAs));
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

    @GetMapping(value = "/attendance-summaries/report/departmentId/{departmentId}/empId/{empId}/employeeId/{employeeId}/fromDate/{fromDate}/toDate/{toDate}/markedAs/{markedAs}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAttendanceReport(@PathVariable Long departmentId, @PathVariable String empId, @PathVariable Long employeeId, @PathVariable LocalDate fromDate, @PathVariable LocalDate toDate, @PathVariable String markedAs) throws Exception, DocumentException {
        log.debug("REST request to download attendance report");
        ByteArrayInputStream byteArrayInputStream = attendanceSummaryReportService.download(departmentId, empId, employeeId, fromDate, toDate, AttendanceMarkedAs.lookup(markedAs));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/attendance-summaries/report/departmentId/{departmentId}/empId/{empId}/employeeId/{employeeId}/fromDate/{fromDate}/toDate/{toDate}/markedAs/{markedAs}");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/attendance-summaries/personal-attendance-status/departmentId/{departmentId}/empId/{empId}/employeeId/{employeeId}/fromDate/{fromDate}/toDate/{toDate}/markedAs/{markedAs}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getPersonalAttendanceStatus(@PathVariable Long departmentId, @PathVariable String empId, @PathVariable Long employeeId, @PathVariable LocalDate fromDate, @PathVariable LocalDate toDate, @PathVariable String markedAs) throws Exception, DocumentException {
        log.debug("REST request to download attendance report");
        ByteArrayInputStream byteArrayInputStream = attendanceSummaryPersonalStatusService.download(departmentId, empId, employeeId, fromDate, toDate, AttendanceMarkedAs.lookup(markedAs));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/attendance-summaries/personal-attendance-status/departmentId/{departmentId}/empId/{empId}/employeeId/{employeeId}/fromDate/{fromDate}/toDate/{toDate}/markedAs/{markedAs}");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }


}
