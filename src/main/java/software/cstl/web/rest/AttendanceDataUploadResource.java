package software.cstl.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.cstl.service.AttendanceDataUploadService;

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
}
