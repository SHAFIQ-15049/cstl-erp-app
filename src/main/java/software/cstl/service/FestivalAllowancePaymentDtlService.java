package software.cstl.service;

import software.cstl.domain.FestivalAllowancePaymentDtl;
import software.cstl.repository.FestivalAllowancePaymentDtlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FestivalAllowancePaymentDtl}.
 */
@Service
@Transactional
public class FestivalAllowancePaymentDtlService {

    private final Logger log = LoggerFactory.getLogger(FestivalAllowancePaymentDtlService.class);

    private final FestivalAllowancePaymentDtlRepository festivalAllowancePaymentDtlRepository;

    public FestivalAllowancePaymentDtlService(FestivalAllowancePaymentDtlRepository festivalAllowancePaymentDtlRepository) {
        this.festivalAllowancePaymentDtlRepository = festivalAllowancePaymentDtlRepository;
    }

    /**
     * Save a festivalAllowancePaymentDtl.
     *
     * @param festivalAllowancePaymentDtl the entity to save.
     * @return the persisted entity.
     */
    public FestivalAllowancePaymentDtl save(FestivalAllowancePaymentDtl festivalAllowancePaymentDtl) {
        log.debug("Request to save FestivalAllowancePaymentDtl : {}", festivalAllowancePaymentDtl);
        return festivalAllowancePaymentDtlRepository.save(festivalAllowancePaymentDtl);
    }

    /**
     * Get all the festivalAllowancePaymentDtls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FestivalAllowancePaymentDtl> findAll(Pageable pageable) {
        log.debug("Request to get all FestivalAllowancePaymentDtls");
        return festivalAllowancePaymentDtlRepository.findAll(pageable);
    }


    /**
     * Get one festivalAllowancePaymentDtl by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FestivalAllowancePaymentDtl> findOne(Long id) {
        log.debug("Request to get FestivalAllowancePaymentDtl : {}", id);
        return festivalAllowancePaymentDtlRepository.findById(id);
    }

    /**
     * Delete the festivalAllowancePaymentDtl by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FestivalAllowancePaymentDtl : {}", id);
        festivalAllowancePaymentDtlRepository.deleteById(id);
    }
}
