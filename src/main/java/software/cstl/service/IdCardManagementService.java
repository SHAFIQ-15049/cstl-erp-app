package software.cstl.service;

import software.cstl.domain.IdCardManagement;
import software.cstl.repository.IdCardManagementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IdCardManagement}.
 */
@Service
@Transactional
public class IdCardManagementService {

    private final Logger log = LoggerFactory.getLogger(IdCardManagementService.class);

    private final IdCardManagementRepository idCardManagementRepository;

    public IdCardManagementService(IdCardManagementRepository idCardManagementRepository) {
        this.idCardManagementRepository = idCardManagementRepository;
    }

    /**
     * Save a idCardManagement.
     *
     * @param idCardManagement the entity to save.
     * @return the persisted entity.
     */
    public IdCardManagement save(IdCardManagement idCardManagement) {
        log.debug("Request to save IdCardManagement : {}", idCardManagement);
        return idCardManagementRepository.save(idCardManagement);
    }

    /**
     * Get all the idCardManagements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IdCardManagement> findAll(Pageable pageable) {
        log.debug("Request to get all IdCardManagements");
        return idCardManagementRepository.findAll(pageable);
    }


    /**
     * Get one idCardManagement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IdCardManagement> findOne(Long id) {
        log.debug("Request to get IdCardManagement : {}", id);
        return idCardManagementRepository.findById(id);
    }

    /**
     * Delete the idCardManagement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IdCardManagement : {}", id);
        idCardManagementRepository.deleteById(id);
    }
}
