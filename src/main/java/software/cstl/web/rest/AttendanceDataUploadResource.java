package software.cstl.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.cstl.security.AuthoritiesConstants;
import software.cstl.service.AttendanceDataUploadService;
import software.cstl.service.dto.AttendanceDataUploadDTO;
import software.cstl.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


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
     * @param attendanceDataUploadDTO the attendanceDataUploadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendanceDataUploadDTO, or with status {@code 400 (Bad Request)} if the attendanceDataUpload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendance-data-uploads")
    public ResponseEntity<AttendanceDataUploadDTO> createAttendanceDataUpload(@Valid @RequestBody AttendanceDataUploadDTO attendanceDataUploadDTO) throws URISyntaxException {
        log.debug("REST request to save AttendanceDataUpload : {}", attendanceDataUploadDTO);
        if (attendanceDataUploadDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendanceDataUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceDataUploadDTO result = attendanceDataUploadService.save(attendanceDataUploadDTO);
        return ResponseEntity.created(new URI("/api/attendance-data-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
