package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Attendance;
import software.cstl.service.dto.AttendanceDataUploadDTO;

import java.util.List;

@Service
@Transactional
public class AttendanceDataUploadService {

    private final Logger log = LoggerFactory.getLogger(AttendanceDataUploadService.class);

    private final AttendanceService attendanceService;

    public AttendanceDataUploadService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * Save a attendanceDataUpload.
     *
     * @param attendanceDataUploadDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public AttendanceDataUploadDTO save(AttendanceDataUploadDTO attendanceDataUploadDTO) {
        log.debug("Request to save AttendanceDataUpload : {}", attendanceDataUploadDTO);
        List<Attendance> attendances = attendanceService.bulkSave(attendanceDataUploadDTO);
        attendanceDataUploadDTO.setId(1L);
        return attendances.size() > 0 ? attendanceDataUploadDTO : null;
    }
}
