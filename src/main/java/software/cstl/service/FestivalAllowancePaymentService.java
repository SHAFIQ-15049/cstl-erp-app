package software.cstl.service;

import software.cstl.domain.FestivalAllowancePayment;
import software.cstl.repository.FestivalAllowancePaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FestivalAllowancePayment}.
 */
@Service
@Transactional
public class FestivalAllowancePaymentService {

    private final Logger log = LoggerFactory.getLogger(FestivalAllowancePaymentService.class);

    private final FestivalAllowancePaymentRepository festivalAllowancePaymentRepository;

    public FestivalAllowancePaymentService(FestivalAllowancePaymentRepository festivalAllowancePaymentRepository) {
        this.festivalAllowancePaymentRepository = festivalAllowancePaymentRepository;
    }

    /**
     * Save a festivalAllowancePayment.
     *
     * @param festivalAllowancePayment the entity to save.
     * @return the persisted entity.
     */
    public FestivalAllowancePayment save(FestivalAllowancePayment festivalAllowancePayment) {
        log.debug("Request to save FestivalAllowancePayment : {}", festivalAllowancePayment);
        return festivalAllowancePaymentRepository.save(festivalAllowancePayment);
    }

    /**
     * Get all the festivalAllowancePayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FestivalAllowancePayment> findAll(Pageable pageable) {
        log.debug("Request to get all FestivalAllowancePayments");
        return festivalAllowancePaymentRepository.findAll(pageable);
    }


    /**
     * Get one festivalAllowancePayment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FestivalAllowancePayment> findOne(Long id) {
        log.debug("Request to get FestivalAllowancePayment : {}", id);
        return festivalAllowancePaymentRepository.findById(id);
    }

    /**
     * Delete the festivalAllowancePayment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FestivalAllowancePayment : {}", id);
        festivalAllowancePaymentRepository.deleteById(id);
    }
}
