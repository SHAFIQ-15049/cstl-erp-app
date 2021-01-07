package software.cstl.service;

import software.cstl.domain.AdvancePaymentHistory;
import software.cstl.repository.AdvancePaymentHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdvancePaymentHistory}.
 */
@Service
@Transactional
public class AdvancePaymentHistoryService {

    private final Logger log = LoggerFactory.getLogger(AdvancePaymentHistoryService.class);

    private final AdvancePaymentHistoryRepository advancePaymentHistoryRepository;

    public AdvancePaymentHistoryService(AdvancePaymentHistoryRepository advancePaymentHistoryRepository) {
        this.advancePaymentHistoryRepository = advancePaymentHistoryRepository;
    }

    /**
     * Save a advancePaymentHistory.
     *
     * @param advancePaymentHistory the entity to save.
     * @return the persisted entity.
     */
    public AdvancePaymentHistory save(AdvancePaymentHistory advancePaymentHistory) {
        log.debug("Request to save AdvancePaymentHistory : {}", advancePaymentHistory);
        return advancePaymentHistoryRepository.save(advancePaymentHistory);
    }

    /**
     * Get all the advancePaymentHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdvancePaymentHistory> findAll(Pageable pageable) {
        log.debug("Request to get all AdvancePaymentHistories");
        return advancePaymentHistoryRepository.findAll(pageable);
    }


    /**
     * Get one advancePaymentHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdvancePaymentHistory> findOne(Long id) {
        log.debug("Request to get AdvancePaymentHistory : {}", id);
        return advancePaymentHistoryRepository.findById(id);
    }

    /**
     * Delete the advancePaymentHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdvancePaymentHistory : {}", id);
        advancePaymentHistoryRepository.deleteById(id);
    }
}
