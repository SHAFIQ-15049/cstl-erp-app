package software.cstl.service;

import software.cstl.domain.MonthlySalary;
import software.cstl.repository.MonthlySalaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MonthlySalary}.
 */
@Service
@Transactional
public class MonthlySalaryService {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryService.class);

    private final MonthlySalaryRepository monthlySalaryRepository;

    public MonthlySalaryService(MonthlySalaryRepository monthlySalaryRepository) {
        this.monthlySalaryRepository = monthlySalaryRepository;
    }

    /**
     * Save a monthlySalary.
     *
     * @param monthlySalary the entity to save.
     * @return the persisted entity.
     */
    public MonthlySalary save(MonthlySalary monthlySalary) {
        log.debug("Request to save MonthlySalary : {}", monthlySalary);
        return monthlySalaryRepository.save(monthlySalary);
    }

    /**
     * Get all the monthlySalaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MonthlySalary> findAll(Pageable pageable) {
        log.debug("Request to get all MonthlySalaries");
        return monthlySalaryRepository.findAll(pageable);
    }


    /**
     * Get one monthlySalary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MonthlySalary> findOne(Long id) {
        log.debug("Request to get MonthlySalary : {}", id);
        return monthlySalaryRepository.findById(id);
    }

    /**
     * Delete the monthlySalary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MonthlySalary : {}", id);
        monthlySalaryRepository.deleteById(id);
    }
}
