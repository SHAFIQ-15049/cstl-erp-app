package software.cstl.service;

import software.cstl.domain.Fine;
import software.cstl.repository.FineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fine}.
 */
@Service
@Transactional
public class FineService {

    private final Logger log = LoggerFactory.getLogger(FineService.class);

    private final FineRepository fineRepository;

    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    /**
     * Save a fine.
     *
     * @param fine the entity to save.
     * @return the persisted entity.
     */
    public Fine save(Fine fine) {
        log.debug("Request to save Fine : {}", fine);
        return fineRepository.save(fine);
    }

    /**
     * Get all the fines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Fine> findAll(Pageable pageable) {
        log.debug("Request to get all Fines");
        return fineRepository.findAll(pageable);
    }


    /**
     * Get one fine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Fine> findOne(Long id) {
        log.debug("Request to get Fine : {}", id);
        return fineRepository.findById(id);
    }

    /**
     * Delete the fine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fine : {}", id);
        fineRepository.deleteById(id);
    }
}
