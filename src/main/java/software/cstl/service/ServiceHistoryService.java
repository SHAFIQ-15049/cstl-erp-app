package software.cstl.service;

import software.cstl.domain.ServiceHistory;
import software.cstl.repository.ServiceHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceHistory}.
 */
@Service
@Transactional
public class ServiceHistoryService {

    private final Logger log = LoggerFactory.getLogger(ServiceHistoryService.class);

    private final ServiceHistoryRepository serviceHistoryRepository;

    public ServiceHistoryService(ServiceHistoryRepository serviceHistoryRepository) {
        this.serviceHistoryRepository = serviceHistoryRepository;
    }

    /**
     * Save a serviceHistory.
     *
     * @param serviceHistory the entity to save.
     * @return the persisted entity.
     */
    public ServiceHistory save(ServiceHistory serviceHistory) {
        log.debug("Request to save ServiceHistory : {}", serviceHistory);
        return serviceHistoryRepository.save(serviceHistory);
    }

    /**
     * Get all the serviceHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceHistory> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceHistories");
        return serviceHistoryRepository.findAll(pageable);
    }


    /**
     * Get one serviceHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceHistory> findOne(Long id) {
        log.debug("Request to get ServiceHistory : {}", id);
        return serviceHistoryRepository.findById(id);
    }

    /**
     * Delete the serviceHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceHistory : {}", id);
        serviceHistoryRepository.deleteById(id);
    }
}
