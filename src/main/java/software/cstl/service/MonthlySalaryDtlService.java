package software.cstl.service;

import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.repository.MonthlySalaryDtlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MonthlySalaryDtl}.
 */
@Service
@Transactional
public class MonthlySalaryDtlService {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryDtlService.class);

    private final MonthlySalaryDtlRepository monthlySalaryDtlRepository;

    public MonthlySalaryDtlService(MonthlySalaryDtlRepository monthlySalaryDtlRepository) {
        this.monthlySalaryDtlRepository = monthlySalaryDtlRepository;
    }

    /**
     * Save a monthlySalaryDtl.
     *
     * @param monthlySalaryDtl the entity to save.
     * @return the persisted entity.
     */
    public MonthlySalaryDtl save(MonthlySalaryDtl monthlySalaryDtl) {
        log.debug("Request to save MonthlySalaryDtl : {}", monthlySalaryDtl);
        return monthlySalaryDtlRepository.save(monthlySalaryDtl);
    }

    /**
     * Get all the monthlySalaryDtls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MonthlySalaryDtl> findAll(Pageable pageable) {
        log.debug("Request to get all MonthlySalaryDtls");
        return monthlySalaryDtlRepository.findAll(pageable);
    }


    /**
     * Get one monthlySalaryDtl by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MonthlySalaryDtl> findOne(Long id) {
        log.debug("Request to get MonthlySalaryDtl : {}", id);
        return monthlySalaryDtlRepository.findById(id);
    }

    /**
     * Delete the monthlySalaryDtl by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MonthlySalaryDtl : {}", id);
        monthlySalaryDtlRepository.deleteById(id);
    }
}
