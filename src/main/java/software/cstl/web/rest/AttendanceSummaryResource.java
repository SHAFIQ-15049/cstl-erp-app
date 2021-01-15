package software.cstl.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.dto.AttendanceSummaryDTO;

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

    public AttendanceSummaryResource(AttendanceSummaryService attendanceSummaryService) {
        this.attendanceSummaryService = attendanceSummaryService;
    }

    @GetMapping("/attendance-summaries/employeeId/{employeeId}/fromDate/{fromDate}/toDate/{toDate}")
    public List<AttendanceSummaryDTO> getAllAttendanceSummaries(@PathVariable Long employeeId, @PathVariable LocalDate fromDate, @PathVariable LocalDate toDate) {
        log.debug("REST request to get all AttendanceSummaries");
        return attendanceSummaryService.findAll(employeeId, fromDate, toDate);
    }

    @GetMapping("/attendance-summaries/fromDate/{fromDate}/toDate/{toDate}")
    public List<AttendanceSummaryDTO> getAllAttendanceSummaries(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate) {
        log.debug("REST request to get all AttendanceSummaries");
        return attendanceSummaryService.findAll(fromDate, toDate);
    }
}
