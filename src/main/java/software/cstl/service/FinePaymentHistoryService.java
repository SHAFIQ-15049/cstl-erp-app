package software.cstl.service;

import software.cstl.domain.FinePaymentHistory;
import software.cstl.repository.FinePaymentHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FinePaymentHistory}.
 */
@Service
@Transactional
public class FinePaymentHistoryService {

    private final Logger log = LoggerFactory.getLogger(FinePaymentHistoryService.class);

    private final FinePaymentHistoryRepository finePaymentHistoryRepository;

    public FinePaymentHistoryService(FinePaymentHistoryRepository finePaymentHistoryRepository) {
        this.finePaymentHistoryRepository = finePaymentHistoryRepository;
    }

    /**
     * Save a finePaymentHistory.
     *
     * @param finePaymentHistory the entity to save.
     * @return the persisted entity.
     */
    public FinePaymentHistory save(FinePaymentHistory finePaymentHistory) {
        log.debug("Request to save FinePaymentHistory : {}", finePaymentHistory);
        return finePaymentHistoryRepository.save(finePaymentHistory);
    }

    /**
     * Get all the finePaymentHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FinePaymentHistory> findAll(Pageable pageable) {
        log.debug("Request to get all FinePaymentHistories");
        return finePaymentHistoryRepository.findAll(pageable);
    }


    /**
     * Get one finePaymentHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FinePaymentHistory> findOne(Long id) {
        log.debug("Request to get FinePaymentHistory : {}", id);
        return finePaymentHistoryRepository.findById(id);
    }

    /**
     * Delete the finePaymentHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FinePaymentHistory : {}", id);
        finePaymentHistoryRepository.deleteById(id);
    }
}
