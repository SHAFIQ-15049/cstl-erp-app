package software.cstl.service;

import software.cstl.domain.Division;
import software.cstl.repository.DivisionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Division}.
 */
@Service
@Transactional
public class DivisionService {

    private final Logger log = LoggerFactory.getLogger(DivisionService.class);

    private final DivisionRepository divisionRepository;

    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    /**
     * Save a division.
     *
     * @param division the entity to save.
     * @return the persisted entity.
     */
    public Division save(Division division) {
        log.debug("Request to save Division : {}", division);
        return divisionRepository.save(division);
    }

    /**
     * Get all the divisions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Division> findAll(Pageable pageable) {
        log.debug("Request to get all Divisions");
        return divisionRepository.findAll(pageable);
    }


    /**
     * Get one division by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Division> findOne(Long id) {
        log.debug("Request to get Division : {}", id);
        return divisionRepository.findById(id);
    }

    /**
     * Delete the division by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Division : {}", id);
        divisionRepository.deleteById(id);
    }
}
