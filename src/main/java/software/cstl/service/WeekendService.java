package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Weekend;
import software.cstl.domain.enumeration.WeekendStatus;
import software.cstl.repository.WeekendRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Weekend}.
 */
@Service
@Transactional
public class WeekendService {

    private final Logger log = LoggerFactory.getLogger(WeekendService.class);

    private final WeekendRepository weekendRepository;

    public WeekendService(WeekendRepository weekendRepository) {
        this.weekendRepository = weekendRepository;
    }

    /**
     * Save a weekend.
     *
     * @param weekend the entity to save.
     * @return the persisted entity.
     */
    public Weekend save(Weekend weekend) {
        log.debug("Request to save Weekend : {}", weekend);
        return weekendRepository.save(weekend);
    }

    /**
     * Get all the weekends.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Weekend> findAll(Pageable pageable) {
        log.debug("Request to get all Weekends");
        return weekendRepository.findAll(pageable);
    }


    /**
     * Get one weekend by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Weekend> findOne(Long id) {
        log.debug("Request to get Weekend : {}", id);
        return weekendRepository.findById(id);
    }

    /**
     * Delete the weekend by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Weekend : {}", id);
        weekendRepository.deleteById(id);
    }

    public List<Weekend> getActiveWeekends() {
        return weekendRepository.findAll().stream().filter(weekend -> weekend.getStatus().equals(WeekendStatus.ACTIVE)).collect(Collectors.toList());
    }
}
