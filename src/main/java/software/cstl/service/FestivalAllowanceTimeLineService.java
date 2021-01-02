package software.cstl.service;

import software.cstl.domain.FestivalAllowanceTimeLine;
import software.cstl.repository.FestivalAllowanceTimeLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FestivalAllowanceTimeLine}.
 */
@Service
@Transactional
public class FestivalAllowanceTimeLineService {

    private final Logger log = LoggerFactory.getLogger(FestivalAllowanceTimeLineService.class);

    private final FestivalAllowanceTimeLineRepository festivalAllowanceTimeLineRepository;

    public FestivalAllowanceTimeLineService(FestivalAllowanceTimeLineRepository festivalAllowanceTimeLineRepository) {
        this.festivalAllowanceTimeLineRepository = festivalAllowanceTimeLineRepository;
    }

    /**
     * Save a festivalAllowanceTimeLine.
     *
     * @param festivalAllowanceTimeLine the entity to save.
     * @return the persisted entity.
     */
    public FestivalAllowanceTimeLine save(FestivalAllowanceTimeLine festivalAllowanceTimeLine) {
        log.debug("Request to save FestivalAllowanceTimeLine : {}", festivalAllowanceTimeLine);
        return festivalAllowanceTimeLineRepository.save(festivalAllowanceTimeLine);
    }

    /**
     * Get all the festivalAllowanceTimeLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FestivalAllowanceTimeLine> findAll(Pageable pageable) {
        log.debug("Request to get all FestivalAllowanceTimeLines");
        return festivalAllowanceTimeLineRepository.findAll(pageable);
    }


    /**
     * Get one festivalAllowanceTimeLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FestivalAllowanceTimeLine> findOne(Long id) {
        log.debug("Request to get FestivalAllowanceTimeLine : {}", id);
        return festivalAllowanceTimeLineRepository.findById(id);
    }

    /**
     * Delete the festivalAllowanceTimeLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FestivalAllowanceTimeLine : {}", id);
        festivalAllowanceTimeLineRepository.deleteById(id);
    }
}
