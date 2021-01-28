package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.service.dto.DutyLeaveDTO;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class DutyLeaveService {

    private final Logger log = LoggerFactory.getLogger(DutyLeaveService.class);

    private final LeaveApplicationService leaveApplicationService;

    public DutyLeaveService(LeaveApplicationService leaveApplicationService) {
        this.leaveApplicationService = leaveApplicationService;
    }

    /**
     * Save a dutyLeave.
     *
     * @param dutyLeaveDTO the entity to save.
     * @return the persisted entity.
     */
    public DutyLeaveDTO save(DutyLeaveDTO dutyLeaveDTO) {
        log.debug("Request to save DutyLeave : {}", dutyLeaveDTO);
        return null;
    }

    public List<DutyLeaveDTO> save(List<DutyLeaveDTO> dutyLeaveDTOs) {
        log.debug("Request to save DutyLeave : {}", dutyLeaveDTOs);
        return dutyLeaveDTOs;
    }

    /**
     * Get all the dutyLeaves.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DutyLeaveDTO> findAll() {
        log.debug("Request to get all DutyLeaves");
        return null;
    }


    /**
     * Get one dutyLeave by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DutyLeaveDTO> findOne(Long id) {
        log.debug("Request to get DutyLeave : {}", id);
        return null;
    }
}
