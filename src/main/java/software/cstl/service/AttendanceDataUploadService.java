package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.AttendanceDataUpload;
import software.cstl.repository.AttendanceDataUploadRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AttendanceDataUpload}.
 */
@Service
@Transactional
public class AttendanceDataUploadService {

    private final Logger log = LoggerFactory.getLogger(AttendanceDataUploadService.class);

    private final AttendanceDataUploadRepository attendanceDataUploadRepository;

    private final AttendanceService attendanceService;

    private final CommonService commonService;

    public AttendanceDataUploadService(AttendanceDataUploadRepository attendanceDataUploadRepository, AttendanceService attendanceService, CommonService commonService) {
        this.attendanceDataUploadRepository = attendanceDataUploadRepository;
        this.attendanceService = attendanceService;
        this.commonService = commonService;
    }

    /**
     * Save a attendanceDataUpload.
     *
     * @param attendanceDataUpload the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public AttendanceDataUpload save(AttendanceDataUpload attendanceDataUpload) {
        log.debug("Request to save AttendanceDataUpload : {}", attendanceDataUpload);
        AttendanceDataUpload uploadedData = attendanceDataUploadRepository.save(attendanceDataUpload);
        attendanceService.bulkSave(uploadedData);
        return uploadedData;
    }

    /**
     * Get all the attendanceDataUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceDataUpload> findAll(Pageable pageable) {
        log.debug("Request to get all AttendanceDataUploads");
        return attendanceDataUploadRepository.findAll(pageable);
    }


    /**
     * Get one attendanceDataUpload by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttendanceDataUpload> findOne(Long id) {
        log.debug("Request to get AttendanceDataUpload : {}", id);
        return attendanceDataUploadRepository.findById(id);
    }

    /**
     * Delete the attendanceDataUpload by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AttendanceDataUpload : {}", id);
        attendanceDataUploadRepository.deleteById(id);
    }
}
