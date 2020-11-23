package software.cstl.service;

import software.cstl.domain.Designation;
import software.cstl.repository.DesignationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Designation}.
 */
@Service
@Transactional
public class DesignationService {

    private final Logger log = LoggerFactory.getLogger(DesignationService.class);

    private final DesignationRepository designationRepository;

    public DesignationService(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    /**
     * Save a designation.
     *
     * @param designation the entity to save.
     * @return the persisted entity.
     */
    public Designation save(Designation designation) {
        log.debug("Request to save Designation : {}", designation);
        return designationRepository.save(designation);
    }

    /**
     * Get all the designations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Designation> findAll(Pageable pageable) {
        log.debug("Request to get all Designations");
        return designationRepository.findAll(pageable);
    }


    /**
     * Get one designation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Designation> findOne(Long id) {
        log.debug("Request to get Designation : {}", id);
        return designationRepository.findById(id);
    }

    /**
     * Delete the designation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Designation : {}", id);
        designationRepository.deleteById(id);
    }
}
