package software.cstl.service;

import software.cstl.domain.OverTime;
import software.cstl.repository.OverTimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OverTime}.
 */
@Service
@Transactional
public class OverTimeService {

    private final Logger log = LoggerFactory.getLogger(OverTimeService.class);

    private final OverTimeRepository overTimeRepository;

    public OverTimeService(OverTimeRepository overTimeRepository) {
        this.overTimeRepository = overTimeRepository;
    }

    /**
     * Save a overTime.
     *
     * @param overTime the entity to save.
     * @return the persisted entity.
     */
    public OverTime save(OverTime overTime) {
        log.debug("Request to save OverTime : {}", overTime);
        return overTimeRepository.save(overTime);
    }

    /**
     * Get all the overTimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OverTime> findAll(Pageable pageable) {
        log.debug("Request to get all OverTimes");
        return overTimeRepository.findAll(pageable);
    }


    /**
     * Get one overTime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OverTime> findOne(Long id) {
        log.debug("Request to get OverTime : {}", id);
        return overTimeRepository.findById(id);
    }

    /**
     * Delete the overTime by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OverTime : {}", id);
        overTimeRepository.deleteById(id);
    }
}
