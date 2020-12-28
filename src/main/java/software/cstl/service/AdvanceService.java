package software.cstl.service;

import software.cstl.domain.Advance;
import software.cstl.repository.AdvanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Advance}.
 */
@Service
@Transactional
public class AdvanceService {

    private final Logger log = LoggerFactory.getLogger(AdvanceService.class);

    private final AdvanceRepository advanceRepository;

    public AdvanceService(AdvanceRepository advanceRepository) {
        this.advanceRepository = advanceRepository;
    }

    /**
     * Save a advance.
     *
     * @param advance the entity to save.
     * @return the persisted entity.
     */
    public Advance save(Advance advance) {
        log.debug("Request to save Advance : {}", advance);
        return advanceRepository.save(advance);
    }

    /**
     * Get all the advances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Advance> findAll(Pageable pageable) {
        log.debug("Request to get all Advances");
        return advanceRepository.findAll(pageable);
    }


    /**
     * Get one advance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Advance> findOne(Long id) {
        log.debug("Request to get Advance : {}", id);
        return advanceRepository.findById(id);
    }

    /**
     * Delete the advance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Advance : {}", id);
        advanceRepository.deleteById(id);
    }
}
