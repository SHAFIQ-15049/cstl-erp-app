package software.cstl.service;

import software.cstl.domain.Designation;
import software.cstl.repository.DesignationRepository;
import software.cstl.repository.search.DesignationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Designation}.
 */
@Service
@Transactional
public class DesignationService {

    private final Logger log = LoggerFactory.getLogger(DesignationService.class);

    private final DesignationRepository designationRepository;

    private final DesignationSearchRepository designationSearchRepository;

    public DesignationService(DesignationRepository designationRepository, DesignationSearchRepository designationSearchRepository) {
        this.designationRepository = designationRepository;
        this.designationSearchRepository = designationSearchRepository;
    }

    /**
     * Save a designation.
     *
     * @param designation the entity to save.
     * @return the persisted entity.
     */
    public Designation save(Designation designation) {
        log.debug("Request to save Designation : {}", designation);
        Designation result = designationRepository.save(designation);
        designationSearchRepository.save(result);
        return result;
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
        designationSearchRepository.deleteById(id);
    }

    /**
     * Search for the designation corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Designation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Designations for query {}", query);
        return designationSearchRepository.search(queryStringQuery(query), pageable);    }
}
