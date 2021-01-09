package software.cstl.service;

import software.cstl.domain.DefaultAllowance;
import software.cstl.repository.DefaultAllowanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DefaultAllowance}.
 */
@Service
@Transactional
public class DefaultAllowanceService {

    private final Logger log = LoggerFactory.getLogger(DefaultAllowanceService.class);

    private final DefaultAllowanceRepository defaultAllowanceRepository;

    public DefaultAllowanceService(DefaultAllowanceRepository defaultAllowanceRepository) {
        this.defaultAllowanceRepository = defaultAllowanceRepository;
    }

    /**
     * Save a defaultAllowance.
     *
     * @param defaultAllowance the entity to save.
     * @return the persisted entity.
     */
    public DefaultAllowance save(DefaultAllowance defaultAllowance) {
        log.debug("Request to save DefaultAllowance : {}", defaultAllowance);
        return defaultAllowanceRepository.save(defaultAllowance);
    }

    /**
     * Get all the defaultAllowances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DefaultAllowance> findAll(Pageable pageable) {
        log.debug("Request to get all DefaultAllowances");
        return defaultAllowanceRepository.findAll(pageable);
    }


    /**
     * Get one defaultAllowance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DefaultAllowance> findOne(Long id) {
        log.debug("Request to get DefaultAllowance : {}", id);
        return defaultAllowanceRepository.findById(id);
    }

    /**
     * Delete the defaultAllowance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DefaultAllowance : {}", id);
        defaultAllowanceRepository.deleteById(id);
    }
}
