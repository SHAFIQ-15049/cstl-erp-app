package software.cstl.service;

import software.cstl.domain.Thana;
import software.cstl.repository.ThanaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Thana}.
 */
@Service
@Transactional
public class ThanaService {

    private final Logger log = LoggerFactory.getLogger(ThanaService.class);

    private final ThanaRepository thanaRepository;

    public ThanaService(ThanaRepository thanaRepository) {
        this.thanaRepository = thanaRepository;
    }

    /**
     * Save a thana.
     *
     * @param thana the entity to save.
     * @return the persisted entity.
     */
    public Thana save(Thana thana) {
        log.debug("Request to save Thana : {}", thana);
        return thanaRepository.save(thana);
    }

    /**
     * Get all the thanas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Thana> findAll(Pageable pageable) {
        log.debug("Request to get all Thanas");
        return thanaRepository.findAll(pageable);
    }


    /**
     * Get one thana by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Thana> findOne(Long id) {
        log.debug("Request to get Thana : {}", id);
        return thanaRepository.findById(id);
    }

    /**
     * Delete the thana by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Thana : {}", id);
        thanaRepository.deleteById(id);
    }
}
